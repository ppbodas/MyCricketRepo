package com.example.cricket.Adapters;

import java.util.ArrayList;

import com.example.cricket.R;
import com.example.cricket.R.drawable;
import com.example.cricket.R.id;
import com.example.cricket.R.layout;

import DataModel.PastMatchInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCustomBaseAdapter extends BaseAdapter {
	 private static ArrayList<PastMatchInfo> pastMatchInfoList;
	 
	 private LayoutInflater mInflater;

	 public MyCustomBaseAdapter(Context context, ArrayList<PastMatchInfo> results) {
	  pastMatchInfoList = results;
	  mInflater = LayoutInflater.from(context);
	 }

	 public int getCount() {
	  return pastMatchInfoList.size();
	 }

	 public Object getItem(int position) {
	  return pastMatchInfoList.get(position);
	 }

	 public long getItemId(int position) {
	  return position;
	 }

	 public View getView(int position, View convertView, ViewGroup parent) {
	  ViewHolder holder;
	  if (convertView == null) {
	   convertView = mInflater.inflate(R.layout.past_matches_custom_row, null);
	   holder = new ViewHolder();
	   holder.txtMatchTitle = (TextView) convertView.findViewById(R.id.match_title);
	   holder.txtMatchSubTitle = (TextView) convertView.findViewById(R.id.match_subtitle);
	   holder.txtMatchResult = (TextView) convertView.findViewById(R.id.match_result);
	   holder.imgView = (ImageView) convertView.findViewById(R.id.match_type_icon);
	   convertView.setTag(holder);
	  } else {
	   holder = (ViewHolder) convertView.getTag();
	  }
	  
	  holder.txtMatchTitle.setText(pastMatchInfoList.get(position).getMatchTitle());
	  holder.txtMatchSubTitle.setText(pastMatchInfoList.get(position).getMatchSubTitle());
	  holder.txtMatchResult.setText(pastMatchInfoList.get(position).getMatchResult());
	  if(pastMatchInfoList.get(position).getMatchType().equals("T20")){
		  holder.imgView.setImageResource(R.drawable.t20);  
	  }
	  else if(pastMatchInfoList.get(position).getMatchType().equals("ODI")){
		  holder.imgView.setImageResource(R.drawable.odi1);  
	  }
	  else if(pastMatchInfoList.get(position).getMatchType().equals("Test")){
		  holder.imgView.setImageResource(R.drawable.testmatch);  
	  }
	  

	  return convertView;
	 }

	 static class ViewHolder {
	  TextView txtMatchTitle;
	  TextView txtMatchSubTitle;
	  TextView txtMatchResult;
	  ImageView imgView;
	 }
	}