package com.example.cricket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.cricket.Adapters.BattingScorecardEntryAdapter;
import com.example.cricketutil.CricketUtil;
import com.example.webutil.Webutil;

import DataModel.PlayerInfo;
import DataModel.BattingScorecardEntryInfo;
import DataModel.ScorecardMatchInfo;
import DataModel.TeamInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class FullScorecardActivity extends TabActivity{

	private TabHost mTabHost;
	
	private void createTabs(){
		
		mTabHost.setup();
		try{
			JSONObject l_joScorecard = ScorecardMatchInfo.getOnlyInstance().getScorecardJSON();
			JSONArray jaInningsList = l_joScorecard.getJSONArray("past_ings");
			int inningsCount = jaInningsList.length();
			for(int i=0; i<inningsCount;++i){
				Intent intent = new Intent().setClass(this, ViewPagerActivity.class);
				String inningId = jaInningsList.getJSONObject(i).getJSONObject("s").getString("i");
				intent.putExtra("InningId", inningId);
				TabSpec spec = mTabHost.newTabSpec(inningId)
				.setIndicator("Inning" + inningId)
				.setContent(intent);
				
				mTabHost.addTab(spec);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*private void setInfoInCurrentTab(){
		try{
			JSONArray jaInningsList = m_joScorecard.getJSONArray("past_ings");
			ArrayList<ScorecardEntryInfo> scorecardEntryInfoList = new ArrayList<ScorecardEntryInfo>();

			int inningsCount = jaInningsList.length();

			for(int i=0; i<inningsCount;++i){

				JSONObject joInningSummery = jaInningsList.getJSONObject(i).getJSONObject("s");
				String inningId = joInningSummery.getString("i");

				if(inningId.equals(mTabHost.getCurrentTabTag())){
					String teamId = joInningSummery.getJSONObject("a").getString("i");

					TextView tv = (TextView)mTabHost.getCurrentView().findViewById(R.id.textTeamName);
					tv.setText(mTeamsInfo.get(teamId).mTeamFullName);
					tv = (TextView)mTabHost.getCurrentView().findViewById(R.id.textTotalValue);
					tv.setText(joInningSummery.getJSONObject("a").getString("r"));

					//Fill scorecardentry info
					//Batting detail array
					JSONArray jaBattingDetails = jaInningsList.getJSONObject(i).getJSONObject("d").getJSONObject("a").getJSONArray("t");
					int battingDetailSize = jaBattingDetails.length();
					for (int j =0;j<battingDetailSize;++j){
						String battingPlayerId = jaBattingDetails.getJSONObject(j).getString("i");
						
						ScorecardEntryInfo scorecardEntryInfo = new ScorecardEntryInfo();
						
						scorecardEntryInfo.setPlayerName(mTeamsInfo.get(teamId).mSquad.get(battingPlayerId).mPlayerName);
						scorecardEntryInfo.setPlayerImageURL(mTeamsInfo.get(teamId).mSquad.get(battingPlayerId).mPlayerImageURL);
						scorecardEntryInfo.setRunsScored(jaBattingDetails.getJSONObject(j).getString("r"));
						scorecardEntryInfo.setBallsFaced(jaBattingDetails.getJSONObject(j).getString("b"));
						scorecardEntryInfo.setOutInfo(jaBattingDetails.getJSONObject(j).getString("c"));
						scorecardEntryInfoList.add(scorecardEntryInfo);
					}
					//Set list view data
					ListView listView = (ListView) mTabHost.getCurrentView().findViewById(R.id.listScorecard);
					listView.setAdapter(new MyCustomScorecardEntryAdapter(this, scorecardEntryInfoList));
					
					break;
				}
			}
			
			
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_scorecard_main);
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);

		Intent intent = getIntent();
		String result = intent.getStringExtra(ScoreSummaryActivity.EXTRA_MESSAGE);
		
		try {
			//Prepare current scorecard match data
			JSONObject jsonObj = new JSONObject(result);
			jsonObj = jsonObj.getJSONObject("query").getJSONObject("results").getJSONObject("Scorecard");
			ScorecardMatchInfo.getOnlyInstance().resetData();
			ScorecardMatchInfo.getOnlyInstance().setScorecardJSON(jsonObj);
			ScorecardMatchInfo.getOnlyInstance().initialize();

			createTabs();

					
		}
		catch (Exception e) {
			// TODO: handle exception
		}


	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_full_scorecard_main, menu);
		return true;
	}

}
