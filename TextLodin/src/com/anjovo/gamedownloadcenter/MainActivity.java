package com.anjovo.gamedownloadcenter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.downloadmannger.app.GameApplicationn;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.anjovo.gamedownloadcenter.activity.base.DoubleClickFinishActivity;
import com.anjovo.gamedownloadcenter.activity.loginResgister.LoginActivity;
import com.anjovo.gamedownloadcenter.fragment.HomeFragment;
import com.anjovo.gamedownloadcenter.fragment.IntegralFragment;
import com.anjovo.gamedownloadcenter.fragment.MannergerFragment;
import com.anjovo.gamedownloadcenter.fragment.MessageCenterFragment;
import com.anjovo.gamedownloadcenter.fragment.PersonalCenterFragment;
import com.anjovo.gamedownloadcenter.fragment.PhotoShareFragment;
import com.anjovo.gamedownloadcenter.fragment.SettingFragment;
import com.anjovo.gamedownloadcenter.fragment.SignInFragment;
import com.anjovo.gamedownloadcenter.fragment.SignInRecordFragment;
import com.anjovo.gamedownloadcenter.utils.SharedPreferencesUtil;
import com.anjovo.gamedownloadcenter.utils.UserNameLoginUtils;
import com.anjovo.gamedownloadcenter.utils.onCustomPrepareOptionsMenu;
import com.anjovo.gamedownloadcenter.utils.onCustomPrepareOptionsMenu.OnCustomClickChangeListener;
import com.anjovo.gamedownloadcenter.utils.onCustomPrepareOptionsMenu.OnCustomDismissListener;
import com.anjovo.gamedownloadcenter.views.sideslip.ResideMenu;
import com.anjovo.gamedownloadcenter.views.sideslip.ResideMenu.OnClickChangeListener;
import com.anjovo.gamedownloadcenter.views.sideslip.ResideMenuItem;
import com.anjovo.textlodin.R;

/**
 * @author Administrator 主页面 主题框架 功能 双击返回键则退出应用 有侧滑 有和所有fragment的框架
 */
public class MainActivity extends DoubleClickFinishActivity implements
		OnClickListener, OnClickChangeListener, Runnable{

	private ResideMenu resideMenu;
	private View mMenuView;
	private ResideMenuItem itemHome;// 主页

	public ResideMenuItem getItemHome() {
		return itemHome;
	}

	private ResideMenuItem itemPersonalCenter;// 个人中心
	private ResideMenuItem itemMessageCenter;// 消息中心
	private ResideMenuItem itemSignInRecord;// 签到记录
	private ResideMenuItem itemIntegral;// 积分换礼
	private ResideMenuItem itemPhotoShare;// 照片分享
	private ResideMenuItem itemMannerger;// 管理
	
	public ResideMenuItem getItemMannerger() {
		return itemMannerger;
	}

	private ResideMenuItem itemSetting;// 设置
	public ResideMenuItem itemSignIn;// 签到
	/** 显示签到fragment标记 **/
	public int sign = 0;
	private HomeFragment homeFragment;
	private SignInRecordFragment signinreCordFragment;
	private PersonalCenterFragment personalCenterFragment;
	private MessageCenterFragment messageCenterFragment;
	private IntegralFragment integralFragment;
	private PhotoShareFragment photoShareFragment;
	private MannergerFragment mannergerFragment;
	private SettingFragment settingFragment;
	private SignInFragment signInFragment;

	private FragmentManager fragmentManager;
	
	public FragmentManager getFragmentManager1() {
		return getSupportFragmentManager();
	}

	private GameApplicationn applicationn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		applicationn = (GameApplicationn) getApplication();
		applicationn.addActivity(MainActivity.this);
		Thread thread = new Thread(this);
        thread.start();
		initView();
		fragmentManager = getFragmentManager1();

		setTabSelection(itemHome);
	}

	@Override
	public void run() {
		installedPackages = getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES| PackageManager.GET_ACTIVITIES);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		applicationn.stopService();
		applicationn.exit();
	}
	
	public void initView() {
		SharedPreferencesUtil.saveSharedPreferencesBooleanUtil(this,
				"LogInSuccessfully", Context.MODE_PRIVATE, false);
		resideMenu = new ResideMenu(MainActivity.this);
		resideMenu.setBackground(R.drawable.main_bg);
		resideMenu.attachToActivity(this);
		resideMenu.setScaleValue(0.6f);
		resideMenu.setOnClickChangeListener(this);

		itemHome = new ResideMenuItem(this);
		itemPersonalCenter = new ResideMenuItem(this,
				R.drawable.personal_center, "个人中心");
		itemMessageCenter = new ResideMenuItem(this, R.drawable.message_center,
				"消息中心");
		itemSignInRecord = new ResideMenuItem(this, R.drawable.sign_in_record,
				"签到记录");

		itemIntegral = new ResideMenuItem(this, R.drawable.integral, "积分换礼");// 积分换礼
		itemPhotoShare = new ResideMenuItem(this, R.drawable.photo_share,
				"照片分享");// 照片分享
		itemMannerger = new ResideMenuItem(this, R.drawable.mannerger, "管理");// 管理
		itemSetting = new ResideMenuItem(this);// 设置
		itemSignIn = new ResideMenuItem(this);// 签到

		// resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemPersonalCenter, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemMessageCenter, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemSignInRecord, ResideMenu.DIRECTION_LEFT);

		resideMenu.addMenuItem(itemIntegral, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemPhotoShare, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemMannerger, ResideMenu.DIRECTION_LEFT);
		// resideMenu.addMenuItem(itemSetting, ResideMenu.DIRECTION_LEFT);
		// resideMenu.addMenuItem(itemSignIn, ResideMenu.DIRECTION_LEFT);

		itemPersonalCenter.setOnClickListener(this);
		itemMessageCenter.setOnClickListener(this);
		itemSignInRecord.setOnClickListener(this);

		itemIntegral.setOnClickListener(this);// 积分换礼
		itemPhotoShare.setOnClickListener(this);// 照片分享
		itemMannerger.setOnClickListener(this);// 管理
		initMenu();
	}

	private void initMenu() {
		// 菜单相关
		mMenuView = findViewById(R.id.menu_view);
		onCustomPrepareOptionsMenu.getInstance().initpop(this,
				onCustomPrepareOptionsMenu.MENU_SHOWOPTIONS_SETTING_EXIT);
		onCustomPrepareOptionsMenu.getInstance()
				.setOnCustomClickChangeListener(onCustomClickChangeListener);
		onCustomPrepareOptionsMenu.getInstance().setOnCustomDismissListener(
				onCustomDismissListener);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	public ResideMenuItem getItemPersonalCenter() {
		return itemPersonalCenter;
	}

	public ResideMenu getResideMenu() {
		return resideMenu;
	}

	@Override
	public void onChangeClick(View v) {
		if (v.getId() == R.id.homeIv_residemenu) {
			sign = 0;
			setTabSelection(itemHome);// 已登录过 在这个方法里编写登陆成功后
		} else if (v.getId() == R.id.settingIv_residemenu) {
			sign = 0;
			setTabSelection(itemSetting);
		} else if (v.getId() == R.id.sign_in_residemenu) {
			setTabSelection(itemSignIn);
			if (!UserNameLoginUtils.IsUserNameLogin(this)) {
				sign = 1;
				UserNameLoginUtils.LoginFailure(this, LoginActivity.class);// 未登陆过
			}
		}
		resideMenu.closeMenu();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (sign == 1) {
			setTabSelection(itemSignIn);
		}
	}

	@Override
	public void onClick(View arg0) {
		// if (arg0 == itemHome) {
		// setTabSelection(itemHome);
		// resideMenu.closeMenu();
		// } else
		if (arg0 == itemMannerger) {
				sign = 0;
				setTabSelection(itemMannerger);// 已登录过 在这个方法里编写登陆成功后
			}else if (!UserNameLoginUtils.IsUserNameLogin(this)) {
			UserNameLoginUtils.LoginFailure(this, LoginActivity.class);// 未登陆过
		}
		if (arg0 == itemPersonalCenter) {
			sign = 0;
			setTabSelection(itemPersonalCenter);// 已登录过 在这个方法里编写登陆成功后
		} else if (arg0 == itemMessageCenter) {
			sign = 0;
			setTabSelection(itemMessageCenter);// 已登录过 在这个方法里编写登陆成功后
		} else if (arg0 == itemSignInRecord) {
			sign = 0;
			setTabSelection(itemSignInRecord);// 已登录过 在这个方法里编写登陆成功后
		} else if (arg0 == itemIntegral) {
			sign = 0;
			setTabSelection(itemIntegral);// 已登录过 在这个方法里编写登陆成功后
		} else if (arg0 == itemPhotoShare) {
			sign = 0;
			setTabSelection(itemPhotoShare);// 已登录过 在这个方法里编写登陆成功后
		}
		// else if (arg0 == itemSetting) {
		// setTabSelection(itemSetting);
		// resideMenu.closeMenu();
		// }
		// else if (arg0 == itemSignIn) {
		// setTabSelection(itemSignIn);
		// resideMenu.closeMenu();
		// }
		// initView();
		resideMenu.closeMenu();
	}

	@SuppressLint("NewApi")
	public void setTabSelection(View view) {
		clearSelection();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		if (view == itemHome) {
			// itemHome.setBackgroundResource(getColor(R.color.white));
			if (homeFragment == null) {
				homeFragment = new HomeFragment();
				transaction.add(R.id.main_fragment, homeFragment);
			} else {
				transaction.show(homeFragment);
				if (personalCenterFragment != null) {
					transaction.hide(personalCenterFragment);
				}
				if (messageCenterFragment != null) {
					transaction.hide(messageCenterFragment);
				}
				if (signinreCordFragment != null) {
					transaction.hide(signinreCordFragment);
				}
				if (integralFragment != null) {
					transaction.hide(integralFragment);
				}
				if (photoShareFragment != null) {
					transaction.hide(photoShareFragment);
				}
				if (mannergerFragment != null) {
					transaction.hide(mannergerFragment);
				}
				if (settingFragment != null) {
					transaction.hide(settingFragment);
				}
				if (signInFragment != null) {
					transaction.hide(signInFragment);
				}
			}
		} else if (view == itemPersonalCenter) {
			itemPersonalCenter
					.setBackgroundResource(R.drawable.left_item_selected_bg);
			// itemNear.setIcon(R.drawable.personal_center_chick);
			itemPersonalCenter.setColor(getResources().getColor(R.color.white));
			if (personalCenterFragment == null) {
				personalCenterFragment = new PersonalCenterFragment();
				transaction.add(R.id.main_fragment, personalCenterFragment);
			} else {
				transaction.show(personalCenterFragment);
				if (homeFragment != null) {
					transaction.hide(homeFragment);
				}
				if (messageCenterFragment != null) {
					transaction.hide(messageCenterFragment);
				}
				if (signinreCordFragment != null) {
					transaction.hide(signinreCordFragment);
				}
				if (integralFragment != null) {
					transaction.hide(integralFragment);
				}
				if (photoShareFragment != null) {
					transaction.hide(photoShareFragment);
				}
				if (mannergerFragment != null) {
					transaction.hide(mannergerFragment);
				}
				if (settingFragment != null) {
					transaction.hide(settingFragment);
				}
				if (signInFragment != null) {
					transaction.hide(signInFragment);
				}
			}
		} else if (view == itemMessageCenter) {
			itemMessageCenter
					.setBackgroundResource(R.drawable.left_item_selected_bg);
			// itemMine.setIcon(R.drawable.message_center_chick);
			itemMessageCenter.setColor(getResources().getColor(R.color.white));
			if (messageCenterFragment == null) {
				messageCenterFragment = new MessageCenterFragment();
				transaction.add(R.id.main_fragment, messageCenterFragment);
			} else {
				transaction.show(messageCenterFragment);
				if (homeFragment != null) {
					transaction.hide(homeFragment);
				}
				if (personalCenterFragment != null) {
					transaction.hide(personalCenterFragment);
				}
				if (signinreCordFragment != null) {
					transaction.hide(signinreCordFragment);
				}
				if (integralFragment != null) {
					transaction.hide(integralFragment);
				}
				if (photoShareFragment != null) {
					transaction.hide(photoShareFragment);
				}
				if (mannergerFragment != null) {
					transaction.hide(mannergerFragment);
				}
				if (settingFragment != null) {
					transaction.hide(settingFragment);
				}
				if (signInFragment != null) {
					transaction.hide(signInFragment);
				}
			}
		} else if (view == itemSignInRecord) {
			itemSignInRecord
					.setBackgroundResource(R.drawable.left_item_selected_bg);
			// itemSetting.setIcon(R.drawable.sign_in_record_chick);
			itemSignInRecord.setColor(getResources().getColor(R.color.white));
			if (signinreCordFragment == null) {
				signinreCordFragment = new SignInRecordFragment();
				transaction.add(R.id.main_fragment, signinreCordFragment);
			} else {
				transaction.show(signinreCordFragment);
				if (homeFragment != null) {
					transaction.hide(homeFragment);
				}
				if (personalCenterFragment != null) {
					transaction.hide(personalCenterFragment);
				}
				if (messageCenterFragment != null) {
					transaction.hide(messageCenterFragment);
				}
				if (integralFragment != null) {
					transaction.hide(integralFragment);
				}
				if (photoShareFragment != null) {
					transaction.hide(photoShareFragment);
				}
				if (mannergerFragment != null) {
					transaction.hide(mannergerFragment);
				}
				if (settingFragment != null) {
					transaction.hide(settingFragment);
				}
				if (signInFragment != null) {
					transaction.hide(signInFragment);
				}
			}
		} else if (view == itemIntegral) {
			itemIntegral
					.setBackgroundResource(R.drawable.left_item_selected_bg);
			// itemSetting.setIcon(R.drawable.sign_in_record_chick);
			itemIntegral.setColor(getResources().getColor(R.color.white));
			if (integralFragment == null) {
				integralFragment = new IntegralFragment();
				transaction.add(R.id.main_fragment, integralFragment);
			} else {
				transaction.show(integralFragment);
				if (homeFragment != null) {
					transaction.hide(homeFragment);
				}
				if (personalCenterFragment != null) {
					transaction.hide(personalCenterFragment);
				}
				if (messageCenterFragment != null) {
					transaction.hide(messageCenterFragment);
				}
				if (signinreCordFragment != null) {
					transaction.hide(signinreCordFragment);
				}
				if (photoShareFragment != null) {
					transaction.hide(photoShareFragment);
				}
				if (mannergerFragment != null) {
					transaction.hide(mannergerFragment);
				}
				if (settingFragment != null) {
					transaction.hide(settingFragment);
				}
				if (signInFragment != null) {
					transaction.hide(signInFragment);
				}
			}
		} else if (view == itemPhotoShare) {
			itemPhotoShare
					.setBackgroundResource(R.drawable.left_item_selected_bg);
			// itemSetting.setIcon(R.drawable.sign_in_record_chick);
			itemPhotoShare.setColor(getResources().getColor(R.color.white));
			if (photoShareFragment == null) {
				photoShareFragment = new PhotoShareFragment();
				transaction.add(R.id.main_fragment, photoShareFragment);
			} else {
				transaction.show(photoShareFragment);
				if (homeFragment != null) {
					transaction.hide(homeFragment);
				}
				if (personalCenterFragment != null) {
					transaction.hide(personalCenterFragment);
				}
				if (messageCenterFragment != null) {
					transaction.hide(messageCenterFragment);
				}
				if (signinreCordFragment != null) {
					transaction.hide(signinreCordFragment);
				}
				if (integralFragment != null) {
					transaction.hide(integralFragment);
				}
				if (mannergerFragment != null) {
					transaction.hide(mannergerFragment);
				}
				if (settingFragment != null) {
					transaction.hide(settingFragment);
				}
				if (signInFragment != null) {
					transaction.hide(signInFragment);
				}
			}
		} else if (view == itemMannerger) {
			itemMannerger
					.setBackgroundResource(R.drawable.left_item_selected_bg);
			// itemSetting.setIcon(R.drawable.sign_in_record_chick);
			itemMannerger.setColor(getResources().getColor(R.color.white));
			if (mannergerFragment == null) {
				mannergerFragment = new MannergerFragment();
				transaction.add(R.id.main_fragment, mannergerFragment);
			} else {
				transaction.show(mannergerFragment);
				if (homeFragment != null) {
					transaction.hide(homeFragment);
				}
				if (personalCenterFragment != null) {
					transaction.hide(personalCenterFragment);
				}
				if (messageCenterFragment != null) {
					transaction.hide(messageCenterFragment);
				}
				if (signinreCordFragment != null) {
					transaction.hide(signinreCordFragment);
				}
				if (integralFragment != null) {
					transaction.hide(integralFragment);
				}
				if (photoShareFragment != null) {
					transaction.hide(photoShareFragment);
				}
				if (settingFragment != null) {
					transaction.hide(settingFragment);
				}
				if (signInFragment != null) {
					transaction.hide(signInFragment);
				}
			}
		} else if (view == itemSetting) {
			// itemSetting.setBackgroundResource(R.drawable.left_item_selected_bg);
			// itemSetting.setIcon(R.drawable.sign_in_record_chick);
			// itemSetting.setColor(getResources().getColor(R.color.white));
			if (settingFragment == null) {
				settingFragment = new SettingFragment();
				transaction.add(R.id.main_fragment, settingFragment);
			} else {
				transaction.show(settingFragment);
				if (homeFragment != null) {
					transaction.hide(homeFragment);
				}
				if (personalCenterFragment != null) {
					transaction.hide(personalCenterFragment);
				}
				if (messageCenterFragment != null) {
					transaction.hide(messageCenterFragment);
				}
				if (signinreCordFragment != null) {
					transaction.hide(signinreCordFragment);
				}
				if (integralFragment != null) {
					transaction.hide(integralFragment);
				}
				if (photoShareFragment != null) {
					transaction.hide(photoShareFragment);
				}
				if (mannergerFragment != null) {
					transaction.hide(mannergerFragment);
				}
				if (signInFragment != null) {
					transaction.hide(signInFragment);
				}
			}
		} else if (view == itemSignIn) {
			// itemSignIn.setBackgroundResource(R.drawable.left_item_selected_bg);
			// itemSetting.setIcon(R.drawable.sign_in_record_chick);
			// itemSignIn.setColor(getResources().getColor(R.color.white));
			if (signInFragment == null) {
				signInFragment = new SignInFragment();
				transaction.add(R.id.main_fragment, signInFragment);
			} else {
				transaction.show(signInFragment);
				if (homeFragment != null) {
					transaction.hide(homeFragment);
				}
				if (personalCenterFragment != null) {
					transaction.hide(personalCenterFragment);
				}
				if (messageCenterFragment != null) {
					transaction.hide(messageCenterFragment);
				}
				if (signinreCordFragment != null) {
					transaction.hide(signinreCordFragment);
				}
				if (integralFragment != null) {
					transaction.hide(integralFragment);
				}
				if (photoShareFragment != null) {
					transaction.hide(photoShareFragment);
				}
				if (mannergerFragment != null) {
					transaction.hide(mannergerFragment);
				}
				if (settingFragment != null) {
					transaction.hide(settingFragment);
				}
			}
		}
		transaction.commit();
	}

	private void clearSelection() {
		itemHome.setBackgroundResource(android.R.color.transparent);
		itemHome.setIcon(R.drawable.left_fragment_home_icon_normal);
		itemHome.setColor(getResources().getColor(
				R.color.left_item_color_normal));

		itemPersonalCenter.setBackgroundResource(android.R.color.transparent);
		itemPersonalCenter.setIcon(R.drawable.personal_center);
		itemPersonalCenter.setColor(getResources().getColor(
				R.color.left_item_color_normal));

		itemMessageCenter.setBackgroundResource(android.R.color.transparent);
		itemMessageCenter.setIcon(R.drawable.message_center);
		itemMessageCenter.setColor(getResources().getColor(
				R.color.left_item_color_normal));

		itemSignInRecord.setBackgroundResource(android.R.color.transparent);
		itemSignInRecord.setIcon(R.drawable.sign_in_record);
		itemSignInRecord.setColor(getResources().getColor(
				R.color.left_item_color_normal));

		itemIntegral.setBackgroundResource(android.R.color.transparent);
		itemIntegral.setIcon(R.drawable.integral);
		itemIntegral.setColor(getResources().getColor(
				R.color.left_item_color_normal));

		itemPhotoShare.setBackgroundResource(android.R.color.transparent);
		itemPhotoShare.setIcon(R.drawable.photo_share);
		itemPhotoShare.setColor(getResources().getColor(
				R.color.left_item_color_normal));

		itemMannerger.setBackgroundResource(android.R.color.transparent);
		itemMannerger.setIcon(R.drawable.mannerger);
		itemMannerger.setColor(getResources().getColor(
				R.color.left_item_color_normal));

		// itemSetting.setBackgroundResource(android.R.color.transparent);
		// itemSetting.setIcon(R.drawable.setting_chick);
		// itemSetting.setColor(getResources().getColor(R.color.left_item_color_normal));

		// itemSignIn.setBackgroundResource(android.R.color.transparent);
		// itemSignIn.setIcon(R.drawable.sign_in);
		// itemSignIn.setColor(getResources().getColor(R.color.left_item_color_normal));
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}
		if (personalCenterFragment != null) {
			transaction.hide(personalCenterFragment);
		}
		if (messageCenterFragment != null) {
			transaction.hide(messageCenterFragment);
		}
		if (signinreCordFragment != null) {
			transaction.hide(signinreCordFragment);
		}
		if (integralFragment != null) {
			transaction.hide(integralFragment);
		}
		if (photoShareFragment != null) {
			transaction.hide(photoShareFragment);
		}
		if (mannergerFragment != null) {
			transaction.hide(mannergerFragment);
		}
		if (settingFragment != null) {
			transaction.hide(settingFragment);
		}
		if (signInFragment != null) {
			transaction.hide(signInFragment);
		}
	}

	/** 可以通过此方法动态的改变菜单的状态，比如加载不同的菜单等 **/
	/* 必须重写，否则点击MENU无反应 为了让他不显示，下面onMenuOpened（）必须返回false */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return onCustomPrepareOptionsMenu.getInstance().onPrepareOptionsMenu(
				menu, super.onPrepareOptionsMenu(menu));
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		return onCustomPrepareOptionsMenu.getInstance().onMenuOpened(featureId,
				menu, false, mMenuView);
	}

	private OnCustomClickChangeListener onCustomClickChangeListener = new OnCustomClickChangeListener() {

		@Override
		public void onCustomClick(int who) {
			if (who == 2) {
				setTabSelection(itemSetting);
				resideMenu.closeMenu();
			} else if (who == 0) {
			} else if (who == 1) {
				onCustomPrepareOptionsMenu.getInstance().ShowExitDialog(
						MainActivity.this);
			} else if (who == 3) {
			}
		}
	};

	private OnCustomDismissListener onCustomDismissListener = new OnCustomDismissListener() {
		@Override
		public void onCustomDismiss() {

		}
	};
	public List<PackageInfo> installedPackages;

	public List<PackageInfo> getInstalledPackages() {
		return installedPackages;
	}

	public void loadDatas() {
		if(mannergerFragment != null){
			mannergerFragment.loadDatas();
		}
	}
}
