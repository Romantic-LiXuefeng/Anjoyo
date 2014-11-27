package com.anjovo.gamedownloadcenter.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.anjovo.gamedownloadcenter.MainActivity;
import com.anjovo.gamedownloadcenter.activity.HuntActivity;
import com.anjovo.gamedownloadcenter.adapter.MyPagerAdapter;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.gamedownloadcenter.fragment.main.Fragment1;
import com.anjovo.gamedownloadcenter.fragment.main.Fragment2;
import com.anjovo.gamedownloadcenter.fragment.main.FragmentCategory;
import com.anjovo.gamedownloadcenter.fragment.main.FragmentCommunity;
import com.anjovo.gamedownloadcenter.fragment.main.FragmentSpecial;
import com.anjovo.textlodin.R;

/**
 * @author Administrator 次fragment 用于主界面切换用
 */
public class HomeFragment extends TitleFragmentBase {
	/** 指示器的View **/
	private View indicator;
	private ViewPager viewPager;
	/** 屏幕的宽 **/
	private int screenWidth;
	/** 每个标签的宽 **/
	private int tabWidth;
	/** 标签的数组 **/
	private TextView[] tabs;
	/** 标签的id数组 **/
	private int[] tabIds = { R.id.tabRecommend_fragment_home, R.id.tabRankRow_fragment_home, R.id.tabClassify_fragment_home,
			R.id.tabDissertation_fragment_home, R.id.tabCommunity_fragment_home};
	private MyPagerAdapter pagerAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_home, container,
				false);
		return mContentView;
	}

	@Override
	protected void initTitle() {
		initView();
		setUpTitleLeftImg(R.drawable.home_big_title_left_persion);
		setUpTitleCentreText("菜鸟游戏");
		setUpTitleRightImg(R.drawable.home_big_title_right_search);
	}

	private void initView() {
		initIndicator();
		initViewPager();
		initTabs();
		selectTab(0);
//		toast = Toast.makeText(getActivity(), "", 0);
	}

	 /**初始化标签Tab**/
    private void initTabs() {
    	tabs = new TextView[tabIds.length];
    	for (int i = 0; i < tabs.length; i++) {
			tabs[i] = (TextView) mContentView.findViewById(tabIds[i]);
			tabs[i].setOnClickListener(onClickListener);;
		}
	}
    /**单击监听器**/
    private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			
			default:
				//获取点击的是第几个Tab
				for (int i = 0; i < tabIds.length; i++) {
					if(v.getId() == tabIds[i]){
						//讲viewPager切换到第i页,第二个参数true代表切换的时候有个滚动效果
						selectTab(i);
						break;
					}
				}
				break;
			}
		}
	};

	/**选择第index页**/
	private void selectTab(int index){
		//讲viewPager切换到第i页,第二个参数true代表切换的时候有个滚动效果
		viewPager.setCurrentItem(index, false);
		for (int i = 0; i < tabs.length; i++) {
			if(i == index){
				tabs[i].setTextColor(0xffFFFFFF);
			}else{
				tabs[i].setTextColor(0xff444444);
			}
		}
	}
	/**初始化ViewPager**/
	private void initViewPager() {
    	viewPager = (ViewPager) mContentView.findViewById(R.id.viewPager);
    	List<Fragment> fragments = new ArrayList<Fragment>();
    	fragments.add(new Fragment1());
    	fragments.add(new Fragment2());
    	fragments.add(new FragmentCategory());
    	fragments.add(new FragmentSpecial());
    	fragments.add(new FragmentCommunity());
    	pagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
    	viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(pagerChangeListener);
	}
//	private Toast toast;
	private int currentPage;
	int leftLayout;
	int rightLayout;
	/**为ViewPager设置的页面改变的监听器**/
	private OnPageChangeListener pagerChangeListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			selectTab(arg0);
			currentPage = arg0;
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
//			toast.setText(arg0+","+arg1+","+arg2);
//			toast.show();
			//(ViewPager往右移动的时候arg0为当前页-1，往左移的时候arg0值为当前页)，arg2代表移动的距离
			if(arg0 == currentPage){
				// ViewPage在往左移动，说明indicator应该向右移动
				leftLayout = currentPage*tabWidth+arg2/5;
				rightLayout = leftLayout+tabWidth;
			}else{
				// ViewPage在往右移动，说明indicator应该向左移动
				leftLayout = currentPage*tabWidth-(screenWidth-arg2)/5;
				rightLayout = leftLayout+tabWidth;
			}
			indicator.layout(leftLayout, indicator.getTop(), rightLayout, indicator.getBottom());
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}
	};

	/**初始化指示器**/
	private void initIndicator() {
    	DisplayMetrics dm = new DisplayMetrics();
    	getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
    	screenWidth = dm.widthPixels;
    	tabWidth = screenWidth/5;
		indicator = mContentView.findViewById(R.id.indicator);
		LayoutParams params = (LayoutParams) indicator.getLayoutParams();
		params.width = tabWidth;
		indicator.setLayoutParams(params);
	}
	
	@Override
	public void onTitleBackClick() {
	}

	@Override
	public void onTitleRightImgClick() {
		getActivity().startActivity(new Intent(getActivity(), HuntActivity.class));
	}

	@Override
	public void onTitleRightTwoImgClick(int img) {
	}

	@Override
	public void onTitleLeftImgClick() {
		((MainActivity) getActivity()).getResideMenu().OpenMenu();
	}
}
