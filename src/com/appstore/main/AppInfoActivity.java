package com.appstore.main;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.core.AsyncTask.Status;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import net.tsz.afinal.http.HttpHandler;

import com.appstore.util.AppUtils;
import com.appstore.view.CustomProgressDialog;
import com.config.Configs;
import com.config.GlobalVar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.model.Model_AppInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sino.appstore.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Global;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AppInfoActivity extends Activity {
	public String Appid,AppLogo,AppName;
	private ImageView mPic, mLogo;
	private TextView mTv_Size, mTv_Ver, mTv_Time, mTv_name, mTv_detail;
	private Button mBt_down,mBt_open,mBt_Up,mBt_UnI;
	private DisplayImageOptions mOptions;
	private Model_AppInfo mAppinfo;
	private ProgressBar mPro;
	private Timer timer;
	private LinearLayout mBtGrop1, mBtGrop2;
	private final int DOWN_ING = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_info);
		Bundle bundle = this.getIntent().getExtras();
		Appid = (String) bundle.get("appid");
		AppLogo=(String) bundle.get("applogo");
		AppName=(String) bundle.get("appname");
		initwidget();
		initData();

	}

	private void initwidget() {
		// TODO Auto-generated method stub

		mOptions = new DisplayImageOptions.Builder().cacheOnDisk(true)
				.considerExifParams(true).build();
		mPic = (ImageView) findViewById(R.id.appinfo_pic);
		mLogo = (ImageView) findViewById(R.id.appinfo_logo);
		mTv_Size = (TextView) findViewById(R.id.appinfo_size);
		mTv_Ver = (TextView) findViewById(R.id.appinfo_ver);
		mTv_Time = (TextView) findViewById(R.id.appinfo_time);
		mTv_name = (TextView) findViewById(R.id.appinfo_name);
		mTv_detail = (TextView) findViewById(R.id.appinfo_detail);
		mPro = (ProgressBar) findViewById(R.id.appinfo_progreesbar);
		mBtGrop1 = (LinearLayout) findViewById(R.id.appinfo_btgrop_1);
		mBtGrop2 = (LinearLayout) findViewById(R.id.appinfo_btgrop_2);
		mBt_down = (Button) findViewById(R.id.appinfo_bt_down);
		mBt_open=(Button)findViewById(R.id.appinfo_bt_open);
		mBt_Up=(Button)findViewById(R.id.appinfo_bt_updata);
		mBt_UnI=(Button)findViewById(R.id.appinfo_bt_uninstall);
		mBt_open.setOnClickListener(openClick);
		mBt_Up.setOnClickListener(downClick);
		mBt_UnI.setOnClickListener(uniClick);
		mBt_down.setOnClickListener(downClick);

	}

	private void startDown() {
		// mPro.setVisibility(View.VISIBLE);
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				mhandler.sendEmptyMessage(DOWN_ING);
			}
		}, 1000, 1000);

	}

	Handler mhandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case DOWN_ING:
				int status = GlobalVar.Appinfo_All.get(Appid).getStatus();
				if (status == 1) {
					mPro.setVisibility(View.VISIBLE);
					Log.d("pro", GlobalVar.Appinfo_All.get(Appid).getPro()
							+ "%");
					mPro.setProgress(GlobalVar.Appinfo_All.get(Appid).getPro());
				}
				if (status == 2) {
					downover(2);
				}
				if (status == 0) {
					downover(0);
				}

				break;

			default:
				break;
			}
		}

	};

	private void downover(int status) {
		// TODO Auto-generated method stub
		try {
			timer.cancel();
		} catch (Exception e) {
			// TODO: handle exception
		}
		mPro.setVisibility(View.GONE);

	}
	
	private OnClickListener uniClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			AppUtils.uninstall(AppInfoActivity.this,mAppinfo.getPkg());
			
		}

	};
	private OnClickListener openClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			AppUtils.startAPP(AppInfoActivity.this,mAppinfo.getPkg());
			
		}

	};
	private OnClickListener downClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			downFile(mAppinfo.getDownUrl());
		}

	};

	private void downFile(String downUrl) {
		// TODO Auto-generated method stub
		FinalHttp fh = new FinalHttp();
		final String downpath = GlobalVar.DownPath() + mAppinfo.getPkg()
				+ ".apk";
		HttpHandler handler = fh.download(downUrl, downpath, true,
				new AjaxCallBack<File>() {

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						// TODO Auto-generated method stub
						super.onFailure(t, errorNo, strMsg);
						Log.d("error", strMsg + "---code:" + errorNo);

						if (errorNo == 416) {
							GlobalVar.Appinfo_All.get(Appid).setStatus(2);
							AppUtils.install(downpath, AppInfoActivity.this);
						} else {
							Toast.makeText(
									getApplicationContext(),
									"下载失败:"
											+ GlobalVar.Appinfo_All.get(Appid)
													.getName(),
									Toast.LENGTH_SHORT).show();
							GlobalVar.Appinfo_All.get(Appid).setStatus(0);
						}

					}

					@Override
					public void onLoading(long count, long current) {
						// TODO Auto-generated method stub
						// Log.d("size",current);
						super.onLoading(count, current);
						Log.d("info", "count:" + count + "--current:" + current);
						int progress = 0;
						if (current != count && current != 0) {
							progress = (int) (current / (float) count * 100);
						} else {
							progress = 100;
						}
						GlobalVar.Appinfo_All.get(Appid).setPro(progress);
						GlobalVar.Appinfo_All.get(Appid).setStatus(1);

						// mPro.setProgress(GlobalVar.Appinfo_All.get(Appid).getPro());

					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						startDown();
						GlobalVar.Appinfo_All.get(Appid).setStatus(1);
						Toast.makeText(getApplicationContext(), "开始下载",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(File t) {
						// TODO Auto-generated method stub
						super.onSuccess(t);
						Toast.makeText(getApplicationContext(), "下载完成",
								Toast.LENGTH_SHORT).show();
						GlobalVar.Appinfo_All.get(Appid).setStatus(2);
						AppUtils.install(downpath, AppInfoActivity.this);
					}

				});

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		try {
			timer.cancel();
		} catch (Exception e) {
			// TODO: handle exception
		}

		super.onDestroy();
	}

	private void DoFail() {
		// TODO Auto-generated method stub
		Toast.makeText(AppInfoActivity.this, "连接服务器失败", Toast.LENGTH_LONG)
				.show();
	}

	private void DoSuccess() {
		// TODO Auto-generated method stub
		mTv_Size.setText(mAppinfo.getSize());
		mTv_Ver.setText(mAppinfo.getVer());
		mTv_Time.setText(mAppinfo.getCtime());
		mTv_name.setText(AppName);
		mTv_detail.setText(mAppinfo.getDetail());
		ImageLoader.getInstance().displayImage(mAppinfo.getPicUrl(), mPic,
				mOptions);
		ImageLoader.getInstance().displayImage(AppLogo, mLogo,
				mOptions);
		if (AppUtils.ApkExist(AppInfoActivity.this, mAppinfo.getPkg())) {
			mBtGrop2.setVisibility(View.VISIBLE);
		} else {
			mBtGrop1.setVisibility(View.VISIBLE);
		}

	}

	private void initData() {
		// TODO Auto-generated method stub
		if (GlobalVar.Appinfo_All.get(Appid) == null) {
			FinalHttp fn = new FinalHttp();
			AjaxParams params = new AjaxParams();
			params.put("id", Appid);
			final CustomProgressDialog dialog =new CustomProgressDialog(this, "Loding",R.anim.frame);
			dialog.show();
			fn.post(Configs.Url.AppInfo(), params, new AjaxCallBack<Object>() {

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					// TODO Auto-generated method stub
					DoFail();
					dialog.dismiss();
					// DoSuccess();
				}

				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					super.onSuccess(t);
					dialog.dismiss();
					Log.d("t",t.toString());
					Gson g = new Gson();
					mAppinfo = null;
					try {
						mAppinfo = g.fromJson(t.toString(),
								new TypeToken<Model_AppInfo>() {
								}.getType());
					} catch (Exception e) {
						// TODO: handle exception
						//
						DoFail();
					}
					if (mAppinfo != null) {
						GlobalVar.Appinfo_All.put(Appid, mAppinfo);
						DoSuccess();
					}

				}
			});
		} else {
			mAppinfo = GlobalVar.Appinfo_All.get(Appid);
			if (GlobalVar.Appinfo_All.get(Appid).getStatus() == 1) {
				startDown();
			}
			DoSuccess();
		}

	}

}
