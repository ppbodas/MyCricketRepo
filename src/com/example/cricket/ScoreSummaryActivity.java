package com.example.cricket;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.cricketutil.CricketUtil;
import com.example.webutil.ImageLoader;
import com.example.webutil.Webutil;

import DataModel.TeamInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreSummaryActivity extends Activity {
	private HashMap<String,TeamInfo> mTeams = new HashMap<String,TeamInfo>();
	public final static String EXTRA_MESSAGE = "com.example.summary.MESSAGE";
	private String mMatchId;
	private JSONObject mJOScorecard;

	private void fillTeamInfo(){
		try{
			JSONArray jaTeams;
			jaTeams = mJOScorecard.getJSONArray("teams");
			TeamInfo team1 = new TeamInfo();
			team1.mTeamFullName = jaTeams.getJSONObject(0).getString("fn");
			team1.mTeamStdFlagURL = jaTeams.getJSONObject(0).getJSONObject("flag").getString("std");

			TeamInfo team2 = new TeamInfo();
			team2.mTeamFullName = jaTeams.getJSONObject(1).getString("fn");
			team2.mTeamStdFlagURL = jaTeams.getJSONObject(1).getJSONObject("flag").getString("std");

			mTeams.put(jaTeams.getJSONObject(0).getString("i"), team1);
			mTeams.put(jaTeams.getJSONObject(1).getString("i"), team2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setMatchResult(){
		try{
			TextView textResult = (TextView) findViewById(R.id.textResult);			
			JSONObject joResult = mJOScorecard.getJSONObject("result");
			if("1".equals(joResult.getString("r"))){
				//Result case
				String winningTeamName = mTeams.get(joResult.getString("winner")).mTeamFullName;
				if("innings".equals(joResult.getString("how"))){	//Special case for innings victory
					textResult.setText(winningTeamName + " won by " + joResult.getString("by") + " runs and " + "innings");							
				}
				else{
					String sDocwordLewis = "";
					if("1".equals(joResult.getString("isdl"))){
						sDocwordLewis = " (D/L)";
					}
					textResult.setText(winningTeamName + " won by " + joResult.getString("by") + " " + joResult.getString("how") + sDocwordLewis);
				}
			}
			else if("2".equals(joResult.getString("r"))){
				textResult.setText("Match Draw");
			}
			else if("3".equals(joResult.getString("r"))){
				textResult.setText("Match Tie");
			}
			else if("4".equals(joResult.getString("r"))){
				textResult.setText("Match Abandoned");
			}
			else if("5".equals(joResult.getString("r"))){
				textResult.setText("Match Cancelled");
			}
			else if("6".equals(joResult.getString("r"))){
				textResult.setText("Match Postponed");
			}	

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setMatchTossResult(){
		try{
			TextView textToss = (TextView) findViewById(R.id.textToss);
			String sWinningTeamId = mJOScorecard.getJSONObject("toss").getString("win");
			if(mJOScorecard.getJSONObject("toss").getString("bat").equals("1")){
				textToss.setText(mTeams.get(sWinningTeamId).mTeamFullName +" won toss and elected to bat");	
			}
			else{
				textToss.setText(mTeams.get(sWinningTeamId).mTeamFullName +" won toss and elected to bowl");
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setMatchVenue(){
		try{
			TextView textVenue = (TextView) findViewById(R.id.textVenue);
			textVenue.setText(mJOScorecard.getJSONObject("place").getString("stadium"));		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setManOfTheMatch(){
		try{
			TextView textMoM = (TextView) findViewById(R.id.textManOfTheMatch);
			textMoM.setText("Man of the match: " + mJOScorecard.getJSONObject("result").getJSONObject("mom").getString("fn"));					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setMatchTitle(){
		try{
			JSONArray jaTeams;
			jaTeams = mJOScorecard.getJSONArray("teams");
			//Set Match Title
			TextView textMatchTitle = (TextView) findViewById(R.id.textMatchTitle);
			textMatchTitle.setText(jaTeams.getJSONObject(0).getString("fn") + " Vs " + jaTeams.getJSONObject(1).getString("fn"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setScoreBoardInfo(){
		try{
			JSONArray jaInningsList = mJOScorecard.getJSONArray("past_ings");
			int inningsCount = jaInningsList.length();
			for(int i=0; i<inningsCount;++i){
				JSONObject joInning = jaInningsList.getJSONObject(i).getJSONObject("s");
				String inningId = joInning.getString("i");
				int resId;
				TextView tv;
				ImageView iv;
				//Set Team Name
				resId = getResources().getIdentifier("textTeam" + "I" + inningId, "id", getPackageName());
				tv = (TextView) findViewById(resId);
				tv.setText(mTeams.get(joInning.getJSONObject("a").getString("i")).mTeamFullName);

				//Set Image
				resId = getResources().getIdentifier("textTeamFlag" + "I" + inningId, "id", getPackageName());
				iv = (ImageView)findViewById(resId);
				//				Drawable image = Webutil.ImageOperations(mTeams.get(joInning.getJSONObject("a").getString("i")).mTeamStdFlagURL);
				//				iv.setImageDrawable(image);
				ImageLoader.getOnlyInstance().fetchDrawableOnThread(mTeams.get(joInning.getJSONObject("a").getString("i")).mTeamStdFlagURL, iv);


				//Set Overs
				resId = getResources().getIdentifier("textOvers" + "I" + inningId, "id", getPackageName());
				tv = (TextView) findViewById(resId);
				tv.setText(joInning.getJSONObject("a").getString("o"));

				//Set Runs
				resId = getResources().getIdentifier("textRuns" + "I" + inningId, "id", getPackageName());
				tv = (TextView) findViewById(resId);
				String runs = joInning.getJSONObject("a").getString("r"); 
				if(false == "10".equals(joInning.getJSONObject("a").getString("w"))){
					runs = runs + "/" + joInning.getJSONObject("a").getString("w");
				}
				if("1".equals(joInning.getJSONObject("a").getString("l"))){
					runs = runs + " dec";
				}
				tv.setText(runs);
			}
			int maxInnigns = 4;
			if (false == "1".equals(mJOScorecard.getString("m"))) {
				maxInnigns = 2;
			}
			if(inningsCount<maxInnigns){
				for(int i=inningsCount+1;i<=maxInnigns;++i){
					int resId;
					TextView tv;
					//Set Team Name
					resId = getResources().getIdentifier("textTeam" + "I" + i, "id", getPackageName());
					tv = (TextView) findViewById(resId);
					tv.setText("---");

					//Set Overs
					resId = getResources().getIdentifier("textOvers" + "I" + i, "id", getPackageName());
					tv = (TextView) findViewById(resId);
					tv.setText("---");

					//Set Runs
					resId = getResources().getIdentifier("textRuns" + "I" + i, "id", getPackageName());
					tv = (TextView) findViewById(resId);					
					tv.setText("---");
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_score_summary1);
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		mMatchId = intent.getStringExtra(PastMatchesActivity.EXTRA_MESSAGE);
		//Toast.makeText(getApplicationContext(), matchId, Toast.LENGTH_SHORT).show();

		final String result = CricketUtil.stripYahooStringFromJson(Webutil.getUrlOutput(CricketUtil.getFullScorcardURL(mMatchId)));
		try {
			mJOScorecard = new JSONObject(result);
			mJOScorecard = mJOScorecard.getJSONObject("query").getJSONObject("results").getJSONObject("Scorecard");

			fillTeamInfo();

			//Update UI based on match type
			if (false == "1".equals(mJOScorecard.getString("m"))) {
				LinearLayout inn3Layout = (LinearLayout) findViewById(R.id.inn3);
				inn3Layout.setVisibility(View.GONE);
				LinearLayout inn4Layout = (LinearLayout) findViewById(R.id.inn4);
				inn4Layout.setVisibility(View.GONE);
				View inn4Border = findViewById(R.id.inn4Border);
				inn4Border.setVisibility(View.GONE);
				View inn3Border = findViewById(R.id.inn3Border);
				inn3Border.setVisibility(View.GONE);
			}

			//Set Match Title
			setMatchTitle();
			//Set Venue
			setMatchVenue();
			{
				Thread thread = new Thread() {
					@Override
					public void run() {
						//Set Toss Info
						setMatchTossResult();	
					}
				};
				thread.start();
			}

			//Set Scoreboard info
			setScoreBoardInfo();				
			//Set Result
			setMatchResult();
			//Set Man of the match
			setManOfTheMatch();


		} catch (JSONException e) {
			e.printStackTrace();
		}

		Button buttonFullScorecard = (Button) findViewById(R.id.buttonFullScorecard);
		buttonFullScorecard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ScoreSummaryActivity.this, FullScorecardActivity.class);
				intent.putExtra(EXTRA_MESSAGE,result);
				startActivity(intent);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_score_summary, menu);
		return true;
	}

}
