package com.example.cricket;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.cricket.Adapters.BattingScorecardEntryAdapter;

import DataModel.BattingScorecardEntryInfo;
import DataModel.ScorecardMatchInfo;
import DataModel.TeamInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class FullScorecardBattingFragment extends Fragment {
	
	private String mInningId = new String();
	public FullScorecardBattingFragment(String inningId) {
		mInningId = inningId;		
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Test", "hello");
    }
 
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
 
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.full_score_batting_content, container, false);
    	try{
    		JSONObject l_joScorecard = ScorecardMatchInfo.getOnlyInstance().getScorecardJSON();
    		HashMap<String, TeamInfo> l_TeamsInfo = ScorecardMatchInfo.getOnlyInstance().getTeamsInfo();
			JSONArray jaInningsList = l_joScorecard.getJSONArray("past_ings");
			ArrayList<BattingScorecardEntryInfo> scorecardEntryInfoList = new ArrayList<BattingScorecardEntryInfo>();

			int inningsCount = jaInningsList.length();

			for(int i=0; i<inningsCount;++i){

				JSONObject joInningSummery = jaInningsList.getJSONObject(i).getJSONObject("s");
				String inningId = joInningSummery.getString("i");

				if(inningId.equals(mInningId)){
					String battingTeamId = joInningSummery.getJSONObject("a").getString("i");
					

			TextView tv = (TextView)view.findViewById(R.id.textTeamName);
					tv.setText(l_TeamsInfo.get(battingTeamId).mTeamFullName);
					tv = (TextView)view.findViewById(R.id.textTotalValue);
					tv.setText(joInningSummery.getJSONObject("a").getString("r"));

					//Fill scorecardentry info
					//Batting detail array
					JSONArray jaBattingDetails = jaInningsList.getJSONObject(i).getJSONObject("d").getJSONObject("a").getJSONArray("t");
					int battingDetailSize = jaBattingDetails.length();
					for (int j =0;j<battingDetailSize;++j){
						String battingPlayerId = jaBattingDetails.getJSONObject(j).getString("i");
						
						BattingScorecardEntryInfo scorecardEntryInfo = new BattingScorecardEntryInfo();
						
						scorecardEntryInfo.setPlayerName(l_TeamsInfo.get(battingTeamId).mSquad.get(battingPlayerId).mPlayerName);
						scorecardEntryInfo.setPlayerImageURL(l_TeamsInfo.get(battingTeamId).mSquad.get(battingPlayerId).mPlayerImageURL);
						scorecardEntryInfo.setRunsScored(jaBattingDetails.getJSONObject(j).getString("r"));
						scorecardEntryInfo.setBallsFaced(jaBattingDetails.getJSONObject(j).getString("b"));
						scorecardEntryInfo.setOutInfo(jaBattingDetails.getJSONObject(j).getString("c"));
						scorecardEntryInfoList.add(scorecardEntryInfo);
					}
					Context context = (Context) getActivity();
					//Set list view data
					ListView listView = (ListView) view.findViewById(R.id.listScorecard);
					listView.setAdapter(new BattingScorecardEntryAdapter(context, scorecardEntryInfoList));
					
					break;
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return view;
    }
}
