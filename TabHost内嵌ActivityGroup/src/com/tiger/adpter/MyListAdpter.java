package com.tiger.adpter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tiger.activitygroupdome.R;
import com.tiger.bean.ListItemBean;
import com.tiger.util.LoadRemoteImage;

public class MyListAdpter extends BaseAdapter {

	private List<ListItemBean> list;
	private Context context;
	private LoadRemoteImage remoteImage;  

	public MyListAdpter(Context context, List<ListItemBean> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		remoteImage=new LoadRemoteImage();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ListItemView item = null;
		if (view == null) {
			item = new ListItemView();
			view = LayoutInflater.from(context).inflate(
					R.layout.m_listitemview, null);

			item.image = (ImageView) view.findViewById(R.id.item_image);
			item.title = (TextView) view.findViewById(R.id.item_title);

			view.setTag(item);

		} else {
			item = (ListItemView) view.getTag();
		}
		final ImageView icon=item.image;
		item.title.setText(list.get(position).getTitle());
		remoteImage.setRemoteImageListener(list.get(position).getImageUrl(), new LoadRemoteImage.OnRemoteImageListener() {  
            
            @Override  
            public void onError(String error) {  
                Toast.makeText(context, error, Toast.LENGTH_LONG).show();  
            }  

            @Override  
            public void onRemoteImage(Bitmap image) {  
            	icon.setImageBitmap(image);  
            }  
        });  

		return view;
	}

	class ListItemView {
		private ImageView image;
		private TextView title;
	}
}
