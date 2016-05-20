package com.appstore.main;

import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.adapter.AppGridAdapter;
import com.adapter.AppSearchGridAdapter;
import com.adapter.SearchKeyGridAdapter;
import com.appstore.view.CustomProgressDialog;
import com.config.Configs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.model.Model_App;
import com.sino.appstore.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	public static final String[] array = new String[] { "A", "B", "C", "D",
			"E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4",
			"5", "6", "7", "8", "9", "0" };
	public GridView mGv_key;

	public StringBuffer serchbuff;
	public SearchKeyGridAdapter keyAdapter;
	public TextView mTv_val;
	public Button mBt_clear, mBt_back, mBt_search;
	public ListView mListApp;
	public List<Model_App> model_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seach);
		initwidget();
//		SearhApp();
	}

	private void initwidget() {
		// TODO Auto-generated method stub
		mGv_key = (GridView) findViewById(R.id.search_keygrid);
		keyAdapter = new SearchKeyGridAdapter(SearchActivity.this, array);
		mGv_key.setAdapter(keyAdapter);
		mGv_key.setOnItemClickListener(keyItemClick);
		mTv_val = (TextView) findViewById(R.id.serch_val);
		mBt_back = (Button) findViewById(R.id.search_bt_back);
		mBt_clear = (Button) findViewById(R.id.search_bt_clear);
		mBt_search = (Button) findViewById(R.id.search_bt_search);
		mListApp = (ListView) findViewById(R.id.search_applist);
		serchbuff = new StringBuffer();
		mBt_search.setOnClickListener(SearchClick);
		mListApp.setOnItemClickListener(AppGirdClick);
		mBt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				backVal();
			}
		});
	
		mBt_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clearVal();
			}
		});
	}
	OnItemClickListener AppGirdClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			// TODO Auto-generated method stub
			Model_App item = model_list.get(pos);
			// Log.d("dd",item.getId()+":"+item.getName());
			Intent intent = new Intent(SearchActivity.this, AppInfoActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("appid", item.getId());
			bundle.putString("applogo", item.getLogoUrl());
			bundle.putString("appname", item.getName());
			intent.putExtras(bundle);
			startActivity(intent);
		}

	};
	OnClickListener SearchClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			SearhApp();
		}

	};

	private void SearhApp() {
		FinalHttp fn = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("key", serchbuff.toString());
//		params.put("key", "A");
		final CustomProgressDialog dialog =new CustomProgressDialog(this, "Loding",R.anim.frame);
		dialog.show();
		fn.post(Configs.Url.AppSearch(), params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				Toast.makeText(SearchActivity.this, "连接服务器失败",
						Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				Log.d("searh", t.toString());
				dialog.dismiss();
				Gson g = new Gson();
				List<Model_App> m = null;
				try {
					m = g.fromJson(t.toString(),
							new TypeToken<List<Model_App>>() {
							}.getType());

				} catch (Exception e) {

					// TODO: handle exception
				}
				if (m != null) {
					model_list = m;
					mListApp.setAdapter(new AppSearchGridAdapter(
							SearchActivity.this, model_list));
				} else {

				}
			}

		});
	}

	private void backVal() {
		serchbuff.deleteCharAt(serchbuff.length() - 1);
		mTv_val.setText(serchbuff.toString());
	}

	private void clearVal() {
		serchbuff.delete(0, serchbuff.length());
		mTv_val.setText(serchbuff.toString());
	}

	private void createVal(String val) {
		serchbuff.append(val);
		mTv_val.setText(serchbuff.toString());
	}

	OnItemClickListener keyItemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			// TODO Auto-generated method stub

			createVal(array[pos]);
		}

	};
}
