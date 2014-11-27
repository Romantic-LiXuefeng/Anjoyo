package com.anjovo.gamedownloadcenter.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjovo.gamedownloadcenter.activity.base.TitleActivityBase;
import com.anjovo.textlodin.R;
import com.lidroid.xutils.view.annotation.ViewInject;

public class IntegrationDetailActivity extends TitleActivityBase {
	/*
	 * 积分兑换详情界面
	 */
	
	@ViewInject(R.id.iv_picture_integration_details)
	private ImageView mIvPicture;
	@ViewInject(R.id.tv_integration_details_test)
	private TextView mTvTest;
	@ViewInject(R.id.tv_integration_details_zero)
	private TextView mTvZero;
	@ViewInject(R.id.et_e_mail_table_information)
	private EditText mEtEmail;
	@ViewInject(R.id.et_mobile_no_table_information)
	private EditText mEtMobileNO;
	@ViewInject(R.id.et_qq_table_information)
	private EditText mEtQQ;
	@ViewInject(R.id.et_adress_table_information)
	private EditText mEtAdress;
	@ViewInject(R.id.btn_exchange_integration_details)
	private Button mBtnExchange;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_integration_details);
	}

	@Override
	public void onTitleBackClick() {
		finish();
	}

	@Override
	public void onTitleRightImgClick() {
	}

	@Override
	public void onTitleRightTwoImgClick(int img) {
	}

	@Override
	public void onTitleLeftImgClick() {
	}

	@Override
	protected void initTitle() {
		setUpTitleBack();
		setUpTitleCentreText("Redeem");
	}
}
