package com.example.cricket.Adapters;

import java.util.ArrayList;

import com.example.cricket.R;
import com.example.cricket.Adapters.MyCustomBaseAdapter.ViewHolder;
import com.example.cricket.R.id;
import com.example.cricket.R.layout;
import com.example.webutil.ImageLoader;
import com.example.webutil.Webutil;

import DataModel.BattingScorecardEntryInfo;
import DataModel.BowlingScorecardEntryInfo;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BowlingScorecardEntryAdapter extends BaseAdapter {
	private static ArrayList<BowlingScorecardEntryInfo> scorecardEntryInfoList;
	private ImageLoader mImageLoader;

	private LayoutInflater mInflater;

	public BowlingScorecardEntryAdapter(Context context, ArrayList<BowlingScorecardEntryInfo> entryInfos) {
		scorecardEntryInfoList = entryInfos;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return scorecardEntryInfoList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return scorecardEntryInfoList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {		
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  ViewHolder holder;
		  if (convertView == null) {
		   convertView = mInflater.inflate(R.layout.bowling_scorecard_entry_row, null);
		   holder = new ViewHolder();
		   holder.txtPlayerName = (TextView) convertView.findViewById(R.id.textPlayerName);
		   holder.txtOvers = (TextView) convertView.findViewById(R.id.textOvers);
		   holder.txtMaiden = (TextView) convertView.findViewById(R.id.textMaiden);
		   holder.txtRuns = (TextView) convertView.findViewById(R.id.textRuns);
		   holder.txtWicket = (TextView) convertView.findViewById(R.id.textWicket);
		   holder.imgView = (ImageView) convertView.findViewById(R.id.bowlerImage);
		   convertView.setTag(holder);
		  } else {
		   holder = (ViewHolder) convertView.getTag();
		  }
		  
		  holder.txtPlayerName.setText(scorecardEntryInfoList.get(position).getPlayerName());
		  holder.txtOvers.setText(scorecardEntryInfoList.get(position).getOvers());
		  holder.txtRuns.setText(scorecardEntryInfoList.get(position).getRuns());
		  holder.txtMaiden.setText(scorecardEntryInfoList.get(position).getMaiden());
		  holder.txtWicket.setText(scorecardEntryInfoList.get(position).getWickets());
		  ImageLoader.getOnlyInstance().fetchDrawableOnThread(scorecardEntryInfoList.get(position).getBowlerImageURL(), holder.imgView);
		  return convertView;
		 }
	static class ViewHolder {
		TextView txtPlayerName;
		TextView txtOvers;
		TextView txtRuns;
		TextView txtMaiden;
		TextView txtWicket;
		ImageView imgView;
	}

}
