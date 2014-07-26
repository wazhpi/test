package com.tiger.activitygroupdome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
/**
 * 模块的第1个界面
 * @author HuYang
 *
 */
public class Activity01_A extends Activity {
	
	ListView mlistView1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a01_a);
		Button button = (Button) findViewById(R.id.jump);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 得到管理界面,添加一个界面.!
				TabGroupActivity parentActivity1 = (TabGroupActivity) getParent();
				parentActivity1.startChildActivity("Activity01_B", new Intent(
						Activity01_A.this, Activity01_B.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
		
		mlistView1=(ListView)findViewById(R.id.listView1);
		
		List<Map<String, String>> data=new ArrayList<Map<String,String>>();
		for (int i = 0; i < 20; i++) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("id", "title="+i);
			map.put("name", "content="+i);
			data.add(map);
		}
		
		SimpleAdapter adapter=new SimpleAdapter(this, data, R.layout.item,new String[]{"id","name"},new int[]{R.id.title,R.id.context});
		mlistView1.setAdapter(adapter);
		Toast.makeText(this, "ok", Toast.LENGTH_LONG).show();
	}

}
