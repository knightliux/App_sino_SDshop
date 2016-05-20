package com.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;





import com.model.Model_LeftMenu;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sino.appstore.R;

public class LeftMenuAdapter extends BaseAdapter {

	private Context mContext;
	private DisplayImageOptions mOptions;
	private List<Model_LeftMenu> mlist;
    public int nowClick=-1;
	public LeftMenuAdapter(Context context, List<Model_LeftMenu> list) {
		mlist = list;
		mContext=context;
	

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
    public void setClickPos(int pos){
    	this.nowClick=pos;
    	notifyDataSetChanged();
    }
	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Holder holder = null;
		Model_LeftMenu item = mlist.get(position);
		if (null == convertView) {
			holder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_leftmenu_item, null);
			//holder.image = (ImageView) convertView.findViewById(R.id.index_img);
			holder.name=(TextView) convertView.findViewById(R.id.leftmenu_name);
			

			convertView.setTag(holder);

			//
		} else {
			holder = (Holder) convertView.getTag();
		}
		 if(nowClick!=-1){
			 if(nowClick==position){
				 holder.name.setTextColor(mContext.getResources().getColor(R.color.leftmenu_click_color));
				 
			 }else{
				 holder.name.setTextColor((ColorStateList) mContext.getResources().getColorStateList(R.drawable.text_color_selector) );
			 }
		 }
		//holder.country.setText(item.getCountry());
         holder.name.setText(item.getName());
	
		

		return convertView;

	}
      
	class Holder {
	
		TextView name;
	}



}
