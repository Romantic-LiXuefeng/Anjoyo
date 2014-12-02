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
 * @author Administrator 找回密码页面
 *         向邮箱发送验证码:http://www.gamept.cn/yx_pd.php?email=1195063924@qq.com
 *         改密码接口:
 *         http://www.gamept.cn/yx_reset_pd.php?code=319452&new_pwd=1234&email
 *         =1195063924@qq.com
 */
@ContentView(R.layout.activity_register_password)
public class BackPasswordActivity extends Activity {

	@ViewInject(R.id.ET_newPassword_activity_register_password)
	private EditText mPasswordET;// 密码
	@ViewInject(R.id.ET_Email_activity_register_password)
	private EditText mEmailET;// 邮箱
	@ViewInject(R.id.ET_Password_activity_register_password)
	private EditText mModificationPasswordET;// 确认密码
	@ViewInject(R.id.ET_VerificationCode_activity_register_password)
	private EditText mVerificationCodeET;// 验证码
	@ViewInject(R.id.ET_Commit_activity_register_password)
	private Button mCommitBT;// 提交
	@ViewInject(R.id.ET_ModificationPassword_activity_register_password)
	private Button mModificationPasswordBT;// 确认修改密码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
	}

	@OnClick({R.id.ET_Commit_activity_register_password,R.id.ET_ModificationPassword_activity_register_password})
	public void OnClickBackPassword(View v){
		if(v == mCommitBT){
			Toast.makeText(BackPasswordActivity.this, "提交成功,请注意邮箱查收...", Toast.LENGTH_LONG)
			.show();
			new HttpUtils().send(HttpMethod.GET, Constant.VERIFICATIONCODE+mEmailET.getText().toString(), new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(BackPasswordActivity.this, "找回失败!请检查网络连接!", Toast.LENGTH_LONG)
					.show();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					String result = arg0.result;
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(result);
						if(jsonObject.getString("code").equals("1")){
							Toast.makeText(BackPasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG)
							.show();
						}else{
							Toast.makeText(BackPasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG)
							.show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		}else if(v == mModificationPasswordBT){
			Toast.makeText(BackPasswordActivity.this, "正在修改密码,请稍候...", Toast.LENGTH_LONG)
			.show();
			if(mPasswordET.getText().toString().equals(mModificationPasswordET.getText().toString())){
				new HttpUtils().send(HttpMethod.GET, Constant.MODIFICATION_PASSWORD+"code="
						+ mVerificationCodeET.getText().toString()
						+ "&new_pwd=" + mPasswordET.getText().toString()
						+ "&email="+mEmailET.getText().toString(),new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(BackPasswordActivity.this, "修改密码失败!请稍候重试...", Toast.LENGTH_LONG)
						.show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String result = arg0.result;
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(result);
							if(jsonObject.getString("code").equals("1")){
								Toast.makeText(BackPasswordActivity.this, "修改密码成功!!!", Toast.LENGTH_LONG)
								.show();
								BackPasswrodSuccessful(result);
							}else{
								Toast.makeText(BackPasswordActivity.this, "修改密码失败,请稍候重试...", Toast.LENGTH_LONG)
								.show();
								BackPasswrodError(result);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}
	}

	/**
	 * 更改密码失败
	 * @param result
	 */
	protected void BackPasswrodError(String result) {
		
	}

	/**
	 * 更改密码成功
	 * @param result
	 */
	protected void BackPasswrodSuccessful(String result) {
		
	}
}
