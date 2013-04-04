package DataModel;

import java.util.HashMap;


public class TeamInfo {
	public String mTeamFullName;
	public String mTeamShortName;
	public String mTeamStdFlagURL;
	public HashMap<String, PlayerInfo> mSquad = new HashMap<String, PlayerInfo>();
}
