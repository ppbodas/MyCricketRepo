package com.example.cricket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.cricketutil.CricketUtil;
import com.example.webutil.Webutil;

import android.os.Bundle;
import android.app.Activity;
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

public class FullScorecardMain extends Activity {

	private TabHost mTabHost;
	private JSONObject m_joScorecard;
	private HashMap<String, TeamInfo> mTeamsInfo = new HashMap<String, TeamInfo>();

	private void fillTeamInfo(){
		try{
			JSONArray jaTeams, jaSquad;
			jaTeams = m_joScorecard.getJSONArray("teams");
			TeamInfo team1 = new TeamInfo();
			team1.mTeamFullName = jaTeams.getJSONObject(0).getString("fn");
			team1.mTeamStdFlagURL = jaTeams.getJSONObject(0).getJSONObject("flag").getString("std");

			TeamInfo team2 = new TeamInfo();
			team2.mTeamFullName = jaTeams.getJSONObject(1).getString("fn");
			team2.mTeamStdFlagURL = jaTeams.getJSONObject(1).getJSONObject("flag").getString("std");

			mTeamsInfo.put(jaTeams.getJSONObject(0).getString("i"), team1);
			mTeamsInfo.put(jaTeams.getJSONObject(1).getString("i"), team2);

			//Now populate squad info in map
			int iTeamsSize = jaTeams.length();
			for (int i =0; i<iTeamsSize; ++i){
				jaSquad = jaTeams.getJSONObject(i).getJSONArray("squad");
				int iSquadSize = jaSquad.length();
				for(int j = 0; j<iSquadSize;++j){
					PlayerInfo playerInfo = new PlayerInfo();
					playerInfo.mPlayerName = jaSquad.getJSONObject(j).getString("full");
					playerInfo.mPlayerImageURL = jaSquad.getJSONObject(j).getString("photo");
					String playerId = jaSquad.getJSONObject(j).getString("i");
					System.out.println("Player Id: "+ playerId);
					mTeamsInfo.get(jaTeams.getJSONObject(i).getString("i")).mSquad.put(playerId, playerInfo);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createTabs(){
		Context context = this;
		LayoutInflater inflater = (LayoutInflater)context.getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);

		final RelativeLayout tabContent1 = (RelativeLayout) inflater.inflate(R.layout.full_score_tab_content, null);		

		mTabHost.setup();
		try{
			JSONArray jaInningsList = m_joScorecard.getJSONArray("past_ings");
			int inningsCount = jaInningsList.length();
			for(int i=0; i<inningsCount;++i){
				String inningId = jaInningsList.getJSONObject(i).getJSONObject("s").getString("i");
				TabSpec spec = mTabHost.newTabSpec(inningId);
				spec.setIndicator("Inning" + inningId).setContent(new TabHost.TabContentFactory() {					
					@Override
					public View createTabContent(String tag) {
						return tabContent1;
					}
				});				
				mTabHost.addTab(spec);
			}
			mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
				@Override
				public void onTabChanged(String tabID){
					setInfoInCurrentTab();
				}
			});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setInfoInCurrentTab(){
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
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_scorecard_main);
		mTabHost = (TabHost) findViewById(R.id.inningTabHost);

		Intent intent = getIntent();
		String result = intent.getStringExtra(ScoreSummary.EXTRA_MESSAGE);
		
		try {
			m_joScorecard = new JSONObject(result);
			m_joScorecard = m_joScorecard.getJSONObject("query").getJSONObject("results").getJSONObject("Scorecard");

			createTabs();

			//Fill team info
			fillTeamInfo();
			//Set info in current tab
			setInfoInCurrentTab();
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
