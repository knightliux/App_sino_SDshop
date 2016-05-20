package com.appstore.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.runner.Version;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.graphics.AvoidXfermode.Mode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.adapter.AppGridAdapter;
import com.adapter.LeftMenuAdapter;
import com.appstore.util.AppUtils;
import com.appstore.util.VersionUtil;
import com.appstore.view.CustomProgressDialog;
import com.config.Configs;
import com.config.GlobalVar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.model.Model_App;
import com.model.Model_AppUp;
import com.model.Model_LeftMenu;
import com.sino.appstore.R;

public class MainActivity extends Activity {
	public ListView mListView;
	public List<Model_LeftMenu> mMenuList;
	public LeftMenuAdapter leftAdapter;
	public Button mBt_search;
	public GridView mAppGrid;
	public AppGridAdapter appAdapter;
	public List<Model_App> nowApplist;// 当前APP列表
	public HashMap<String, List<Model_App>> mAppList = new HashMap<String, List<Model_App>>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initwidget();
		init();
		checkUp();
		// new Download(mProgressbar);
	}

	private void checkUp() {
		// TODO Auto-generated method stub
		FinalHttp fh = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("ver", VersionUtil.getVersionName(MainActivity.this));
		fh.post(Configs.Url.AppUp(), params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				Gson g = new Gson();
				Model_AppUp m = null;
				try {
					m = g.fromJson(t.toString(), new TypeToken<Model_AppUp>() {
					}.getType());
				} catch (Exception e) {
					// TODO: handle exception
				}

				if (m != null) {
					if (m.getCode().equals("0") || m.getCode() == "0") {
						FinalHttp fd = new FinalHttp();
						final String downpath = GlobalVar.DownPath()
								+ "upapk.apk";
						fd.download(m.getUrl(), downpath, true,
								new AjaxCallBack<File>() {

									@Override
									public void onFailure(Throwable t,
											int errorNo, String strMsg) {
										// TODO Auto-generated method stub
										if (errorNo == 416) {

											AppUtils.install(downpath,
													MainActivity.this);
										}
									}

									@Override
									public void onSuccess(File t) {
										// TODO Auto-generated method stub
										AppUtils.install(downpath,
												MainActivity.this);
									}

								});
					}
				}

			}

		});
	}

	private void init() {
		// TODO Auto-generated method stub
		FinalHttp fh = new FinalHttp();
		fh.post(Configs.Url.LeftMenu(), new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "网络连接错误", Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				Log.d("leftMenu", t);
				Gson g = new Gson();
				try {
					mMenuList = g.fromJson(t,
							new TypeToken<List<Model_LeftMenu>>() {
							}.getType());
					leftAdapter = new LeftMenuAdapter(MainActivity.this,
							mMenuList);
					mListView.setAdapter(leftAdapter);
					GetAppById(mMenuList.get(0).getId());
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, "连接服务器失败",
							Toast.LENGTH_SHORT).show();
					// TODO: handle exception
				}

			}

		});
	}

	private void initwidget() {
		// TODO Auto-generated method stub
		mListView = (ListView) findViewById(R.id.left_menu);
		mAppGrid = (GridView) findViewById(R.id.app_grid);
		mListView.setOnItemClickListener(LeftMenuClick);
		mAppGrid.setOnItemClickListener(AppGirdClick);
		mBt_search = (Button) findViewById(R.id.app_bt_search);
		mBt_search.setOnClickListener(searchClick);
	}

	OnClickListener searchClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this, SearchActivity.class);

			startActivity(intent);
		}

	};

	public void GetAppFail() {
		Toast.makeText(MainActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
		appAdapter = new AppGridAdapter(MainActivity.this,
				new ArrayList<Model_App>());
		mAppGrid.setAdapter(appAdapter);
	
	}

	public void GetAppSuccess(List<Model_App> list) {
		nowApplist = list;
		appAdapter = new AppGridAdapter(MainActivity.this, list);
		mAppGrid.setAdapter(appAdapter);
	
	}

	public void GetAppById(final String id) {
		// TODO Auto-generated method stub
		List<Model_App> item = mAppList.get(id);
		if (item == null) {
			FinalHttp fn = new FinalHttp();
			AjaxParams params = new AjaxParams();
			params.put("id", id);
			final CustomProgressDialog dialog =new CustomProgressDialog(this, "Loding",R.anim.frame);
			dialog.show();
			fn.post(Configs.Url.AppMenu(), params, new AjaxCallBack<Object>() {
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this, "连接服务器失败",
							Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}

				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					
					Gson g = new Gson();
					dialog.dismiss();
					
					List<Model_App> m = null;
					try {
						m = g.fromJson(t.toString(),
								new TypeToken<List<Model_App>>() {
								}.getType());

					} catch (Exception e) {
						GetAppFail();
				
						// TODO: handle exception
					}
					if (m != null) {
						GetAppSuccess(m);
						mAppList.put(id, m);
					} else {
						GetAppFail();
					
					}
				}

			});
		} else {
			GetAppSuccess(item);
		}

	}

	OnItemClickListener AppGirdClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			// TODO Auto-generated method stub
			Model_App item = nowApplist.get(pos);
			// Log.d("dd",item.getId()+":"+item.getName());
			Intent intent = new Intent(MainActivity.this, AppInfoActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("appid", item.getId());
			bundle.putString("applogo", item.getLogoUrl());
			bundle.putString("appname", item.getName());
			intent.putExtras(bundle);
			startActivity(intent);
		}

	};
	OnItemClickListener LeftMenuClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			// TODO Auto-generated method stub
			Model_LeftMenu item = mMenuList.get(pos);
			leftAdapter.setClickPos(pos);
			GetAppById(item.getId());

		}

	};

}
