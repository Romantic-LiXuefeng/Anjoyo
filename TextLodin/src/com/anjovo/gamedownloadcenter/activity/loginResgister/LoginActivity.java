package com.anjovo.gamedownloadcenter.activity.loginResgister;

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

import com.anjovo.gamedownloadcenter.constant.Constant;
import com.anjovo.gamedownloadcenter.utils.SharedPreferencesUtil;
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
 * 登录页面
 */
@ContentView(R.layout.activity_login_register_backpassword_thirdparty)
public class LoginActivity extends Activity {

	@ViewInject(R.id.ET_nickname_activity_login_register_backPassword_thirdparty)
	private EditText mEmailET;//注册邮箱
	@ViewInject(R.id.ET_nickname_activity_login_register_backPassword_thirdparty)
	private EditText mPasswordET;//注册密码
	@ViewInject(R.id.BT_login__activity_login_register_backPassword_thirdparty)
	private Button mLoginBT;//登陆按钮
	@ViewInject(R.id.BT_Resgister__activity_login_register_backPassword_thirdparty)
	private Button mResgisterBT;//注册按钮
	@ViewInject(R.id.IB_ThirdpartyQQ__activity_login_register_backPassword_thirdparty)
	private ImageButton mThirdpartyQQIB;//第三方登陆   QQ
	@ViewInject(R.id.IB_ThirdpartySina__activity_login_register_backPassword_thirdparty)
	private ImageButton mThirdpartySinaIB;//第三方登陆   新浪
	@ViewInject(R.id.Tv_forget_password_activity_login_register_backPassword_thirdparty)
	private TextView mForgetPassword;//忘记密码
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);

		initView();
	}

	private void initView() {

	}
	
	@OnClick({R.id.BT_Resgister__activity_login_register_backPassword_thirdparty,R.id.BT_Resgister__activity_login_register_backPassword_thirdparty
		,R.id.IB_ThirdpartyQQ__activity_login_register_backPassword_thirdparty,R.id.IB_ThirdpartySina__activity_login_register_backPassword_thirdparty
		,R.id.Tv_forget_password_activity_login_register_backPassword_thirdparty})
	public void OnClickChange(View v){
		if(v == mLoginBT){//登陆
			String EmailText = mEmailET.getText().toString();
			String PasswordText = mPasswordET.getText().toString();
			new HttpUtils().send(HttpMethod.GET, Constant.Login+"email="+EmailText+"&password="+PasswordText, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(LoginActivity.this, "登陆失败!请检查网络连接!", Toast.LENGTH_LONG)
					.show();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					String result = arg0.result;
					try {
						JSONObject jsonObject = new JSONObject(result);
						String code = jsonObject.getString("code");
						if(code.equals("0")){
							Toast.makeText(LoginActivity.this, "登陆成功!", Toast.LENGTH_LONG)
							.show();
							LoginSuccessful(result);
						}else{
							Toast.makeText(LoginActivity.this, "登陆失败!"+jsonObject.getString("message"), Toast.LENGTH_LONG)
							.show();
							UserNameOrPasswordError(code,jsonObject.getString("message"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if(result.equals("")){
						
					}
				}
			});
		}else if(v == mResgisterBT){
			startActivity(new Intent(this, ResgisterActivity.class));
		}else if(v == mThirdpartyQQIB){
		
		}else if(v == mThirdpartySinaIB){
	
		}else if(v == mForgetPassword){
			startActivity(new Intent(this, ResgisterActivity.class));
		}
	}
	
	/**
	 * The user name or password error
	 * @param code 1, error, 0, normal
	 * @param message The server returns the result
	 */
	private void UserNameOrPasswordError(String code , String message){
		SharedPreferencesUtil.saveSharedPreferencesBooleanUtil(this, "Log in successfully", Context.MODE_PRIVATE, false);
	}
	
	/**
	 * Log in successfully
	 * @param successContent Login successful Json string returned by the server
	 */
	private void LoginSuccessful(String successContent){
		SharedPreferencesUtil.saveSharedPreferencesBooleanUtil(this, "Log in successfully", Context.MODE_PRIVATE, true);
	}
}
