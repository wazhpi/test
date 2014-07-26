package com.tiger.activitygroupdome;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import android.R.xml;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.*;
import com.tiger.adpter.MyListAdpter;
import com.tiger.bean.ListItemBean;
import com.tiger.xml.ListHandler;
import com.tiger.xml.XmlHandler;

/**
 * 模块2,不作掩饰!
 * 
 * @author HuYang
 * 
 */
public class Activity02_A extends Activity {

	private ListView myList;
	private MyListAdpter adpter=null;
	private List<ListItemBean> list;
	private AsyncHttpClient client;
	private XmlHandler xmlHandler=new XmlHandler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a02);
		
		findView();
		init();
	}
	
	private void init() {
		client=new AsyncHttpClient();
		loadData();

	}

	private void findView(){
		myList=(ListView)findViewById(R.id.mylist);
	}
	
	private void loadData(){
		String url="http://j.shenguang.com/licaikuaibao/dataAPI.aspx?channelId=38";
		client.get(url,new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO 成功
					String data=new String(arg2);
					
					ListHandler listHander=new ListHandler();
					listHander=(ListHandler)xmlHandler.XMLParse(data, listHander);
					adpter=new MyListAdpter(Activity02_A.this, listHander.list);
					myList.setAdapter(adpter);
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO 失败
				Log.e("loadData", "onFailure");
			}
		});
	}
}
