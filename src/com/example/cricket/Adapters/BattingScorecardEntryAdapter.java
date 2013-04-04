package com.example.cricket.Adapters;

import java.util.ArrayList;

import com.example.cricket.R;
import com.example.cricket.Adapters.MyCustomBaseAdapter.ViewHolder;
import com.example.cricket.R.id;
import com.example.cricket.R.layout;
import com.example.webutil.ImageLoader;
import com.example.webutil.Webutil;

import DataModel.BattingScorecardEntryInfo;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BattingScorecardEntryAdapter extends BaseAdapter {
	private static ArrayList<BattingScorecardEntryInfo> scorecardEntryInfoList;
	private ImageLoader mImageLoader;

	private LayoutInflater mInflater;

	public BattingScorecardEntryAdapter(Context context, ArrayList<BattingScorecardEntryInfo> entryInfos) {
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
		   convertView = mInflater.inflate(R.layout.batting_scorecard_entry_row, null);
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
