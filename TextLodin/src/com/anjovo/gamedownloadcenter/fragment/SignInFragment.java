package com.anjovo.gamedownloadcenter.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.MainActivity;
import com.anjovo.gamedownloadcenter.bean.UserNameMessageBean;
import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.fragment.base.TitleFragmentBase;
import com.anjovo.gamedownloadcenter.utils.AnalysisUserMessage;
import com.anjovo.gamedownloadcenter.utils.TimeUntil;
import com.anjovo.gamedownloadcenter.utils.UserNameLoginUtils;
import com.anjovo.gamedownloadcenter.utils.SharedPreferencesUtil;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.squareup.picasso.Picasso;

/**
 * @author Administrator 签到页面
 */
public class SignInFragment extends TitleFragmentBase {
	private Button btSignIn;
	private EditText etLeaveMessage;
	private boolean isLogin;
	private TextView tvUserName;
	private ImageView userPic;
	private TextView tvOverView;
	private String userid;
	private boolean isClicked = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_sign_in, container,
				false);

		return mContentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		tvOverView = (TextView) mContentView
				.findViewById(R.id.tv_user_is_not_login);
		userPic = (ImageView) mContentView.findViewById(R.id.userpic);
		tvUserName = (TextView) mContentView.findViewById(R.id.user_name);
		TextView tvIntegral = (TextView) mContentView
				.findViewById(R.id.present_integral_all);
		etLeaveMessage = (EditText) mContentView
				.findViewById(R.id.et_leave_a_message);
		btSignIn = (Button) mContentView.findViewById(R.id.bt_sign_in);

		btSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!isClicked) {
					String sayText = etLeaveMessage.getText().toString();
					sendSignInMessage(sayText, userid);
					isClicked = true;
				} else {
					Toast.makeText(getActivity(), "今天已经签到过了!!!", 1).show();
				}

			}
		});
	}

	private void fillInView() {
		if (isLogin) {
			btSignIn.setEnabled(true);
			etLeaveMessage.setEnabled(true);
			tvOverView.setVisibility(View.GONE);
			UserNameMessageBean bean = AnalysisUserMessage
					.getUserMessageBean(getActivity());
			userid = bean.getUserid();
			tvUserName.setText(bean.getUsername());
			Picasso.with(getActivity())
					.load(Constant.HOSTNAME + bean.getUserpic())
					.placeholder(R.drawable.apk).into(userPic);
		} else {
			tvOverView.setVisibility(View.VISIBLE);
			btSignIn.setEnabled(false);
			etLeaveMessage.setEnabled(false);
		}
	}

	private void sendSignInMessage(String sayText, String userid) {
		new HttpUtils().send(HttpMethod.GET, Constant.SIGNIN_URL + userid,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(getActivity(), "签到失败!!!", 1).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String result = arg0.result;
						try {
							JSONObject object = new JSONObject(result);
							int code = object.getInt("code");
							String message = object.getString("message");
							if (code == 0) {
								Toast.makeText(getActivity(), "签到成功!!!", 1)
										.show();
							} else {
								Toast.makeText(getActivity(),
										"签到失败;可能的原因" + message, 1).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

	}

	@Override
	public void onTitleBackClick() {
	}

	@Override
	public void onTitleRightImgClick() {
	}

	@Override
	public void onTitleRightTwoImgClick(int img) {
	}

	@Override
	public void onTitleLeftImgClick() {
		((MainActivity) getActivity()).getResideMenu().OpenMenu();
	}

	@Override
	protected void initTitle() {
		setUpTitleLeftImg(R.drawable.home_big_title_left_persion);
		setUpTitleCentreText("签到");
	}

	@Override
	public void onResume() {
		super.onResume();
		isLogin = UserNameLoginUtils.IsUserNameLogin(getActivity());
		fillInView();
		if (!UserNameLoginUtils.IsUserNameLogin(getActivity())) {
			((MainActivity) getActivity())
					.setTabSelection(((MainActivity) getActivity())
							.getItemHome());
		}
	}
}
