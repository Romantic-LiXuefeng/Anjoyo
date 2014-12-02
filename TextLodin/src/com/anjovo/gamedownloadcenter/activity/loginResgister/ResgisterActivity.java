package com.anjovo.gamedownloadcenter.activity.loginResgister;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.textlodin.R;
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
 * @author Administrator
 * 注册页面
 * http://www.gamept.cn/yx_register.php?email=?&password=?&nickname=?&phone=?
 */
/**
 * @author Administrator
 *
 */
@ContentView(R.layout.activity_register1)
public class ResgisterActivity extends Activity{

	@ViewInject(R.id.ET_nickname_activity_register1)
	private EditText mNicknameET;//昵称
	@ViewInject(R.id.ET_newPassword_activity_register1)
	private EditText mNewPasswordET;//密码
	@ViewInject(R.id.ET_mailbox_activity_register1)
	private EditText mEmailET;//邮箱
	@ViewInject(R.id.ET_password_activity_register1)
	private EditText mPasswordET;//确认密码
	@ViewInject(R.id.ET_phone_activity_register1)
	private EditText mPhoneET;//电话
	@ViewInject(R.id.BT_Resgister_activity_register1)
	private Button mResgisterBT;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
	}
	
	@OnClick({R.id.BT_Resgister_activity_register1})
	public void OnClickResgister(View v){
		Toast.makeText(ResgisterActivity.this, "注册中...", Toast.LENGTH_LONG)
		.show();
		String EmailText = mEmailET.getText().toString();
		String PasswordText = mPasswordET.getText().toString();
		String NewPasswordText = mNewPasswordET.getText().toString();
		String NicknameText = mNicknameET.getText().toString();
		String PhoneText = mPhoneET.getText().toString();
		if(PasswordText.equals(NewPasswordText)){
			mResgisterBT.setBackgroundResource(R.drawable.selector_activity_login_buttom_bg);
			mResgisterBT.setClickable(true);
			new HttpUtils().send(HttpMethod.GET, Constant.RESGISTER+"email="
					+EmailText+ "&password="
							+ NewPasswordText+"&nickname="
									+ NicknameText+"&phone="
											+ PhoneText, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(ResgisterActivity.this, "注册失败!请检查网络连接!", Toast.LENGTH_LONG)
					.show();
					mResgisterBT.setClickable(true);
					mResgisterBT.setBackgroundResource(R.drawable.selector_activity_login_buttom_bg);
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {

					String result = arg0.result;
					try {
						JSONObject jsonObject = new JSONObject(result);
						String code = jsonObject.getString("code");
						if(code.equals("0")){
							Toast.makeText(ResgisterActivity.this, "登陆成功!", Toast.LENGTH_LONG)
							.show();
							ResgisterSuccessful(result);
						}else{
							Toast.makeText(ResgisterActivity.this, "登陆失败!"+jsonObject.getString("message"), Toast.LENGTH_LONG)
							.show();
							RestisgerError(code,jsonObject.getString("message"));
						}
						mResgisterBT.setClickable(true);
						mResgisterBT.setBackgroundResource(R.drawable.selector_activity_login_buttom_bg);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				
				}
			});
		}else{
			mResgisterBT.setBackgroundResource(R.drawable.login_button_click);
			mResgisterBT.setClickable(false);
		}
	}

	/**
	 * 注册失败
	 * @param code
	 * @param string
	 */
	protected void RestisgerError(String code, String string) {
		
	}

	/**
	 * 注册成功
	 * @param result
	 */
	protected void ResgisterSuccessful(String result) {
		finish();
	}
}
