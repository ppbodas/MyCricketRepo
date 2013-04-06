package com.example.cricket;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.cricket.Adapters.BowlingScorecardEntryAdapter;

import DataModel.BowlingScorecardEntryInfo;
import DataModel.ScorecardMatchInfo;
import DataModel.TeamInfo;
import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class FullScorecardBowlingFragment extends Fragment {
	
	private String mInningId = new String();
	public FullScorecardBowlingFragment() {		
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Test", "hello");
        mInningId = getArguments().getString("InningId");
    }
 
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
 
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.full_score_bowling_content, container, false);
    	try{
    		JSONObject l_joScorecard = ScorecardMatchInfo.getOnlyInstance().getScorecardJSON();
    		HashMap<String, TeamInfo> l_TeamsInfo = ScorecardMatchInfo.getOnlyInstance().getTeamsInfo();
			JSONArray jaInningsList = l_joScorecard.getJSONArray("past_ings");
			ArrayList<BowlingScorecardEntryInfo> scorecardEntryInfoList = new ArrayList<BowlingScorecardEntryInfo>();

			int inningsCount = jaInningsList.length();

			for (int i = 0; i < inningsCount; ++i) {

				JSONObject joInningSummery = jaInningsList.getJSONObject(i)
						.getJSONObject("s");
				String inningId = joInningSummery.getString("i");

				if (inningId.equals(mInningId)) {
					String battingTeamId = joInningSummery.getJSONObject("a").getString("i");
					String bowlingTeamId = "";
					Iterator iter = l_TeamsInfo.keySet().iterator();
					while(iter.hasNext()) {
					String key = (String)iter.next();
					if(false == key.equals(battingTeamId)){
						bowlingTeamId = key;
						break;
					}
					}

					TextView tv = (TextView) view.findViewById(R.id.textTeamNameBowling);
					tv.setText(l_TeamsInfo.get(bowlingTeamId).mTeamFullName);

					// Fill scorecardentry info
					// bowling detail array
					JSONArray jaBowlingDetails = jaInningsList.getJSONObject(i).getJSONObject("d").getJSONObject("o").getJSONArray("t");
					int bowlingDetailSize = jaBowlingDetails.length();
					for (int j =0;j<bowlingDetailSize;++j){
						String bowlingPlayerId = jaBowlingDetails.getJSONObject(j).getString("i");
						
						BowlingScorecardEntryInfo scorecardEntryInfo = new BowlingScorecardEntryInfo();
						
						scorecardEntryInfo.setPlayerName(l_TeamsInfo.get(bowlingTeamId).mSquad.get(bowlingPlayerId).mPlayerName);
						scorecardEntryInfo.setOvers(jaBowlingDetails.getJSONObject(j).getString("o"));
						scorecardEntryInfo.setMaiden(jaBowlingDetails.getJSONObject(j).getString("mo"));
						scorecardEntryInfo.setRuns(jaBowlingDetails.getJSONObject(j).getString("r"));
						scorecardEntryInfo.setWickets(jaBowlingDetails.getJSONObject(j).getString("w"));
						scorecardEntryInfo.setBowlerImageURL(l_TeamsInfo.get(bowlingTeamId).mSquad.get(bowlingPlayerId).mPlayerImageURL);
						scorecardEntryInfoList.add(scorecardEntryInfo);
					}
					
					Context context = (Context) getActivity();
					// Set list view data
					ListView listView = (ListView) view
							.findViewById(R.id.listScorecardBowling);
					listView.setAdapter(new BowlingScorecardEntryAdapter(
							context, scorecardEntryInfoList));

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