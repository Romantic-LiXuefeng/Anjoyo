package com.anjovo.gamedownloadcenter.activity.loginResgister;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;

import com.anjovo.gamedownloadcenter.bean.UserNameMessageBean;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.utils.SharedPreferencesUtil;
import com.anjovo.textlodin.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @author Administrator 登录页面
 */
@ContentView(R.layout.activity_login_register_backpassword_thirdparty)
public class LoginActivity extends Activity implements PlatformActionListener {

	@ViewInject(R.id.ET_nickname_activity_login_register_backPassword_thirdparty)
	private EditText mEmailET;// 注册邮箱
	@ViewInject(R.id.ET_newPassword_activity_login_register_backPassword_thirdparty)
	private EditText mPasswordET;// 注册密码
	@ViewInject(R.id.BT_login__activity_login_register_backPassword_thirdparty)
	private Button mLoginBT;// 登陆按钮
	@ViewInject(R.id.BT_Resgister__activity_login_register_backPassword_thirdparty)
	private Button mResgisterBT;// 注册按钮
	@ViewInject(R.id.IB_ThirdpartyQQ__activity_login_register_backPassword_thirdparty)
	private ImageButton mThirdpartyQQIB;// 第三方登陆 QQ
	@ViewInject(R.id.IB_ThirdpartySina__activity_login_register_backPassword_thirdparty)
	private ImageButton mThirdpartySinaIB;// 第三方登陆 新浪
	@ViewInject(R.id.Tv_forget_password_activity_login_register_backPassword_thirdparty)
	private TextView mForgetPassword;// 忘记密码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSDK();
		ViewUtils.inject(this);
	}

	private void initSDK() {
		ShareSDK.initSDK(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK(this);
	}

	private String mType;

	@OnClick({
			R.id.BT_login__activity_login_register_backPassword_thirdparty,
			R.id.BT_Resgister__activity_login_register_backPassword_thirdparty,
			R.id.IB_ThirdpartyQQ__activity_login_register_backPassword_thirdparty,
			R.id.IB_ThirdpartySina__activity_login_register_backPassword_thirdparty,
			R.id.Tv_forget_password_activity_login_register_backPassword_thirdparty })
	public void OnClickLoginResgister(View v) {
		if (v == mLoginBT) {// 登陆
			Toast.makeText(LoginActivity.this, "登陆中...", Toast.LENGTH_LONG)
					.show();
			mLoginBT.setBackgroundResource(R.drawable.login_button_click);
			mLoginBT.setClickable(false);
			String EmailText = mEmailET.getText().toString();
			String PasswordText = mPasswordET.getText().toString();
			new HttpUtils().send(HttpMethod.GET, Constant.LOGIN + "email="
					+ EmailText + "&password=" + PasswordText,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							SharedPreferencesUtil
									.saveSharedPreferencesBooleanUtil(
											LoginActivity.this,
											"LogInSuccessfully",
											Context.MODE_PRIVATE, false);
							Toast.makeText(LoginActivity.this, "登陆失败!请检查网络连接!",
									Toast.LENGTH_LONG).show();
							mLoginBT.setClickable(true);
							mLoginBT.setBackgroundResource(R.drawable.selector_activity_login_buttom_bg);
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							String result = arg0.result;
							try {
								JSONObject jsonObject = new JSONObject(result);
								String code = jsonObject.getString("code");
								if (code.equals("0")) {
									Toast.makeText(LoginActivity.this, "登陆成功!",
											Toast.LENGTH_LONG).show();
									LoginSuccessful(result);
								} else {
									Toast.makeText(
											LoginActivity.this,
											"登陆失败!"
													+ jsonObject
															.getString("message"),
											Toast.LENGTH_LONG).show();
									UserNameOrPasswordError(code,
											jsonObject.getString("message"));
								}
								mLoginBT.setClickable(true);
								mLoginBT.setBackgroundResource(R.drawable.selector_activity_login_buttom_bg);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
		} else if (v == mResgisterBT) {
			startActivity(new Intent(this, ResgisterActivity.class));
		} else if (v == mThirdpartyQQIB) {
			mType = "QQ";
			Platform platform = ShareSDK.getPlatform(this, QZone.NAME);// 得到某个第三方平台
			platform.setPlatformActionListener(this);
			if (platform.isValid()) {// 是否已授权过 则直接登录
				String openId = platform.getDb().getUserId();
				String nickName = platform.getDb().getUserName();
				socialLogin(nickName, openId, "QQ");
				return;
			} else {
				platform.showUser(null);// 弹出授权登录窗口
			}
		} else if (v == mThirdpartySinaIB) {
			mType = "新浪";
			Platform platform1 = ShareSDK.getPlatform(this, SinaWeibo.NAME);// 得到某个第三方平台
			platform1.setPlatformActionListener(this);
			if (platform1.isValid()) {// 是否已授权过 则直接登录
				String openId = platform1.getDb().getUserId();
				String nickName = platform1.getDb().getUserName();
				socialLogin(nickName, openId, "新浪");
				return;
			} else {
				platform1.showUser(null);// 弹出授权登录窗口
			}
		} else if (v == mForgetPassword) {
			startActivity(new Intent(this, BackPasswordActivity.class));
		}
	}

	/**
	 * The user name or password error
	 * 
	 * @param code
	 *            1, error, 0, normal
	 * @param message
	 *            The server returns the result
	 */
	private void UserNameOrPasswordError(String code, String message) {
		SharedPreferencesUtil.saveSharedPreferencesBooleanUtil(this,
				"LogInSuccessfully", Context.MODE_PRIVATE, false);
	}

	/**
	 * Log in successfully
	 * 
	 * @param successContent
	 *            Login successful Json string returned by the server 保持内容: 错误
	 *            code 消息 message 用户名 username 用户Id userid 用户昵称 nickname 用户图片
	 *            userpic 用Gson保存用户登录成功返回的用户所有信息相关 用SharedPreferences保存
	 *            获得用户登录后返回的信息方法 String string =
	 *            SharedPreferencesUtil.getSharedPreferencestStringUtil(this,
	 *            "UserNameMesage", Context.MODE_PRIVATE,""); Gson gsone = new
	 *            Gson(); UserNameMessageBean info = gsone.fromJson(string,
	 *            UserNameMessageBean.class); if(!TextUtils.isEmpty(info)){
	 *            //此方法类向以下方式获得用户信息 String userid = info.getUserid();...... }
	 */
	private void LoginSuccessful(String successContent) throws JSONException {
		SharedPreferencesUtil.saveSharedPreferencesBooleanUtil(this,
				"LogInSuccessfully", Context.MODE_PRIVATE, true);
		JSONObject jsonObject = new JSONObject(successContent);
		UserNameMessageBean bean = new UserNameMessageBean(
				jsonObject.getString("code"), jsonObject.getString("message"),
				jsonObject.getString("username"),
				jsonObject.getString("userid"),
				jsonObject.getString("nickname"),
				jsonObject.getString("userpic"));
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(bean);
		SharedPreferencesUtil.saveSharedPreferencestStringUtil(this,
				"UserNameMesage", Context.MODE_PRIVATE, jsonMessage);
		finish();
	}

	@Override
	public void onCancel(Platform arg0, int arg1) {
		Toast.makeText(LoginActivity.this, arg0.getName() + "授权已取消!",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
		socialLogin(arg0.getDb().getUserName(), arg0.getDb().getUserId(), mType);
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		Toast.makeText(LoginActivity.this, arg0.getName() + "授权失败,请重试!",
				Toast.LENGTH_LONG).show();
	}

	/**
	 * 第三方登录成功后调用 取名为社交登录
	 * 
	 * @param nickName
	 *            对应平台昵称
	 * @param uid
	 *            对应平台uid
	 */
	private void socialLogin(String nickName, String uid, String type) {
		new HttpUtils().send(HttpMethod.GET, Constant.AUTHORIZATION_LOGIN
				+ "openid=" + uid + "&nickname=" + nickName + "&type=" + type,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						SharedPreferencesUtil.saveSharedPreferencesBooleanUtil(
								LoginActivity.this, "LogInSuccessfully",
								Context.MODE_PRIVATE, false);
						Toast.makeText(LoginActivity.this, "授权失败!请检查网络连接!",
								Toast.LENGTH_LONG).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String result = arg0.result;
						try {
							JSONObject jsonObject = new JSONObject(result);
							String code = jsonObject.getString("code");
							if (code.equals("0")) {
								Toast.makeText(LoginActivity.this, "登陆成功!",
										Toast.LENGTH_LONG).show();
								LoginSuccessful(result);
							} else {
								Toast.makeText(
										LoginActivity.this,
										"登陆失败!"
												+ jsonObject
														.getString("message"),
										Toast.LENGTH_LONG).show();
								UserNameOrPasswordError(code,
										jsonObject.getString("message"));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
}
