package com.anjovo.gamedownloadcenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.anjovo.gamedownloadcenter.activity.personalcenter.PersonalCenterEditorUerActivity;
import com.anjovo.gamedownloadcenter.activity.personalcenter.PersonalCenterFoucsGamesActivity;
import com.anjovo.gamedownloadcenter.activity.personalcenter.PersonalCenterFoucsProjectActivity;
import com.anjovo.gamedownloadcenter.activity.personalcenter.PersonalCenterFoucsUserActivity;
import com.anjovo.gamedownloadcenter.activity.personalcenter.PersonalCenterFoundFriendsActivity;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author Administrator 个人中心
 */

public class PersonalCenterFragment extends Fragment {

	@ViewInject(R.id.foucs_user)
	Button fcoususer;

	@ViewInject(R.id.focus_games)
	Button focusgames;

	@ViewInject(R.id.focus_projects)
	Button focusprojects;

	@ViewInject(R.id.found_friends)
	Button foundfriends;
	@ViewInject(R.id.edtor_user)
	Button edtoruser;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_personal_center,
				container, false);
		ViewUtils.inject(this, rootView);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		fcoususer.setOnClickListener(onClickListener);
		focusgames.setOnClickListener(onClickListener);
		focusprojects.setOnClickListener(onClickListener);
		foundfriends.setOnClickListener(onClickListener);
		edtoruser.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.foucs_user:
				startActivity(new Intent(getActivity(),
						PersonalCenterFoucsUserActivity.class));
				break;
			case R.id.focus_games:
				startActivity(new Intent(getActivity(),
						PersonalCenterFoucsGamesActivity.class));
				break;
			case R.id.focus_projects:
				startActivity(new Intent(getActivity(),
						PersonalCenterFoucsProjectActivity.class));
				break;
			case R.id.found_friends:
				startActivity(new Intent(getActivity(),
						PersonalCenterFoundFriendsActivity.class));
				break;
			case R.id.edtor_user:
				startActivity(new Intent(getActivity(),
						PersonalCenterEditorUerActivity.class));
				break;
			}
		}
	};

}
