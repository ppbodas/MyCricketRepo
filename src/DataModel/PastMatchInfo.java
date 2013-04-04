package DataModel;

public class PastMatchInfo {
	private String matchTitle = "";
	private String matchSubTitle = "";
	private String matchId = "";
	private String matchResult = "";
	private String matchType = "";
	
	public void setMatchTitle(String title){
		matchTitle = title;
	}
	public void setMatchSubTitle(String subTitle){
		matchSubTitle = subTitle;
	}
	public String getMatchTitle(){
		return matchTitle;
	}
	public String getMatchSubTitle(){
		return matchSubTitle;
	}
	public void setMatchId(String id){
		matchId = id;
	}
	public String getMatchId(){
		return matchId;
	}
	public String getMatchResult(){
		return matchResult;
	}
	public void setMatchResult(String result){
		matchResult = result;
	}
	public void setMatchType(String type){
		matchType = type;
	}
	public String getMatchType(){
		return matchType;
	}
}
