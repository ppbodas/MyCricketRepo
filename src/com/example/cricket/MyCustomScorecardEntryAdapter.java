package com.example.cricket;

import java.util.ArrayList;

import com.example.cricket.MyCustomBaseAdapter.ViewHolder;
import com.example.webutil.ImageLoader;
import com.example.webutil.Webutil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCustomScorecardEntryAdapter extends BaseAdapter {
	private static ArrayList<ScorecardEntryInfo> scorecardEntryInfoList;
	private ImageLoader mImageLoader;

	private LayoutInflater mInflater;

	public MyCustomScorecardEntryAdapter(Context context, ArrayList<ScorecardEntryInfo> entryInfos) {
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
		   convertView = mInflater.inflate(R.layout.scorecard_entry_row1, null);
		   holder = new ViewHolder();
		   holder.txtPlayerName = (TextView) convertView.findViewById(R.id.textPlayerName);
		   holder.txtRunsScored = (TextView) convertView.findViewById(R.id.textRunsScored);
		   holder.txtBallsFaced = (TextView) convertView.findViewById(R.id.textBallsFaced);
		   holder.txtOutInfo = (TextView) convertView.findViewById(R.id.textPlayerOutInfo);
		   holder.imgView = (ImageView) convertView.findViewById(R.id.playerImage);
		   convertView.setTag(holder);
		  } else {
		   holder = (ViewHolder) convertView.getTag();
		  }
		  
		  holder.txtPlayerName.setText(scorecardEntryInfoList.get(position).getPlayerName());
		  holder.txtRunsScored.setText(scorecardEntryInfoList.get(position).getRunsScored());
		  holder.txtBallsFaced.setText(scorecardEntryInfoList.get(position).getBallsFaced());
		  holder.txtOutInfo.setText(scorecardEntryInfoList.get(position).getOutInfo());
//		  Drawable image = Webutil.ImageOperations(scorecardEntryInfoList.get(position).getPlayerImageURL());
//		  holder.imgView.setImageDrawable(image);
		  ImageLoader.getOnlyInstance().fetchDrawableOnThread(scorecardEntryInfoList.get(position).getPlayerImageURL(), holder.imgView);
		  
		  
		  
		  
		  return convertView;
		 }
	static class ViewHolder {
		TextView txtPlayerName;
		TextView txtRunsScored;
		TextView txtBallsFaced;
		TextView txtOutInfo;
		ImageView imgView;
	}

}
