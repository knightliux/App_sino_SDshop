package com.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;






import com.model.Model_App;
import com.model.Model_LeftMenu;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sino.appstore.R;

@SuppressLint("ResourceAsColor")
public class SearchKeyGridAdapter extends BaseAdapter {

	private Context mContext;
	private DisplayImageOptions mOptions;

	private String[] mlist;
	public SearchKeyGridAdapter(Context context, String[] list) {
		mlist = list;
		mContext=context;
		mOptions = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.pic_loading)
//		.showImageForEmptyUri(R.drawable.pic_loading)
//		.showImageOnFail(R.drawable.pic_loading).cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		// .displayer(new RoundedBitmapDisplayer(20))
		.build();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Holder holder = null;
	
		if (null == convertView) {
			holder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_searchkey_item, null);
			//holder.image = (ImageView) convertView.findViewById(R.id.index_img);
			holder.text=(TextView) convertView.findViewById(R.id.search_item_text);
			convertView.setTag(holder);

			//
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.text.setText(mlist[position]);

		return convertView;

	}

	class Holder {
	
		TextView text;

	}



}
