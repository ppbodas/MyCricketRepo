package com.example.cricket;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.cricket.R;
import com.example.cricket.Adapters.MyCustomBaseAdapter;
import com.example.cricketutil.CricketUtil;

import com.example.webutil.Webutil;

import DataModel.PastMatchInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class PastMatchesActivity extends Activity {
	String[] values = new String[] {"Android", "iPhone", "WindowsMobile", "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2" };
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	private ArrayList<PastMatchInfo> mPastMatchesInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_past_matches);
		ListView listView = (ListView) findViewById(R.id.matches_list);
		
		String result = Webutil.getUrlOutput(CricketUtil.getPastMatchesURL(0, 10, true));
				
		try {
        	result = CricketUtil.stripYahooStringFromJson(result);
			JSONObject jo = new JSONObject(result);
			
			JSONArray jaMatches, jaTeamsArray;
			jaMatches = jo.getJSONObject("query").getJSONObject("results").getJSONArray("Match");
			
			int matchCount = jaMatches.length();
			if (matchCount > 15){
				matchCount = 15;
			}
			mPastMatchesInfo = new ArrayList<PastMatchInfo>();


			for(int i =0; i<matchCount;++i){
				PastMatchInfo info = new PastMatchInfo();
				JSONObject matchInfo = jaMatches.getJSONObject(i);
				jaTeamsArray = matchInfo.getJSONArray("Team");
				String title = jaTeamsArray.getJSONObject(0).getString("Team") + " Vs " + jaTeamsArray.getJSONObject(1).getString("Team");
				String matchType = matchInfo.getString("mtype");
				if(0 == matchType.compareToIgnoreCase("t20")){
					matchType = "T20";
				}
				else if(0 == matchType.compareToIgnoreCase("odi")){
					matchType = "ODI";					
				}
				else if(0 == matchType.compareToIgnoreCase("test")){
					matchType = "Test";
				}
					
				//title = title + " (" + matchType + ")";
				
				String subTitle = matchInfo.getJSONObject("Venue").getString("content");
				info.setMatchTitle(title);
				info.setMatchSubTitle(subTitle);
				info.setMatchId(matchInfo.getString("matchid"));
				info.setMatchType(matchType);
				
				String winningTeamId = "";
				//Get Result
				JSONObject joResult = matchInfo.getJSONObject("Result");
				if("yes".equals(joResult.getJSONArray("Team").getJSONObject(0).getString("matchwon"))){
					winningTeamId = joResult.getJSONArray("Team").getJSONObject(0).getString("id");
				}
				else if("yes".equals(joResult.getJSONArray("Team").getJSONObject(1).getString("matchwon"))){
					winningTeamId = joResult.getJSONArray("Team").getJSONObject(1).getString("id");
				}
				
				if(false == "".equals(winningTeamId)){
					String winningTeamName = "";
					if(0 == jaTeamsArray.getJSONObject(0).getString("teamid").compareTo(winningTeamId)){
						winningTeamName = jaTeamsArray.getJSONObject(0).getString("Team");
					}
					else {
						winningTeamName = jaTeamsArray.getJSONObject(1).getString("Team");
					}
					if("innings".equals(joResult.getString("how"))){	//Special case for innings victory
						info.setMatchResult(winningTeamName + " won by " + joResult.getString("by") + " runs and " + joResult.getString("how"));							
					}			
					else{
						String sDocwordLewis = "";
						if("yes".equals(joResult.getString("isdl"))){
							sDocwordLewis = " (D/L)";
						}
					info.setMatchResult(winningTeamName + " won by " + joResult.getString("by") + " " + joResult.getString("how") + sDocwordLewis);
					}
				}
				else{
					if("abandoned".equals(joResult.getString("how"))){
						info.setMatchResult("Match Abandoned");
					}		
				}
				mPastMatchesInfo.add(info);
			}
			listView.setAdapter(new MyCustomBaseAdapter(this, mPastMatchesInfo));
			listView.setOnItemClickListener(new OnItemClickListener()
			{
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
				   Intent intent = new Intent(PastMatchesActivity.this, ScoreSummaryActivity.class);
			    	intent.putExtra(EXTRA_MESSAGE, mPastMatchesInfo.get(position).getMatchId());
			    	startActivity(intent);
			 }
			}); 
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_past_matches, menu);
		return true;
	}

}
