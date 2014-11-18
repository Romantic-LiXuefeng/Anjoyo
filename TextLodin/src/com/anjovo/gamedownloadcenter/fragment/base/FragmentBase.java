package com.anjovo.gamedownloadcenter.fragment.base;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * @author litangfei 
 *
 */
public abstract class FragmentBase extends Fragment{

	/**上下文  和view關聯的**/
	protected View mContentView;
	
	/**
	 * 此方法目的是避免tab导航界面的重叠效果
	 */

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null){
			this.getView().setVisibility
			(menuVisible ? View.VISIBLE : View.GONE);
		}
	}
}
