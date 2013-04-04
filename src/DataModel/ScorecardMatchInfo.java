package DataModel;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScorecardMatchInfo {
	private static ScorecardMatchInfo mScorecardMatchInfo= new ScorecardMatchInfo();
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
	
	private ScorecardMatchInfo(){		
	}
	public static ScorecardMatchInfo getOnlyInstance(){
		return mScorecardMatchInfo;
	}
	public void setScorecardJSON(JSONObject jsonObj){
		m_joScorecard = jsonObj;
	}
	public void initialize(){
		fillTeamInfo();
	}
	public HashMap<String, TeamInfo> getTeamsInfo(){
		return mTeamsInfo;
	}
	public JSONObject getScorecardJSON(){
		return m_joScorecard;
	}
	public void resetData(){
		mScorecardMatchInfo= new ScorecardMatchInfo();
		mTeamsInfo = new HashMap<String, TeamInfo>();
	}
}
