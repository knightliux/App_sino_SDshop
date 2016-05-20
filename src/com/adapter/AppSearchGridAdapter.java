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
public class AppSearchGridAdapter extends BaseAdapter {

	private Context mContext;
	private DisplayImageOptions mOptions;
	private List<Model_App> mlist;
     
	public AppSearchGridAdapter(Context context, List<Model_App> list) {
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
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist.get(arg0);
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
		Model_App item = mlist.get(position);
		if (null == convertView) {
			holder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_appsearch_item, null);
			//holder.image = (ImageView) convertView.findViewById(R.id.index_img);
			holder.name=(TextView) convertView.findViewById(R.id.app_grid_name);
		
			convertView.setTag(holder);

			//
		} else {
			holder = (Holder) convertView.getTag();
		}
		
	
		//holder.country.setText(item.getCountry());
         holder.name.setText((position+1)+"."+item.getName());
	
         convertView.setBackgroundResource(R.drawable.search_list_selector);

		return convertView;

	}

	class Holder {
	
		TextView name;
		ImageView logo;
	}



}
