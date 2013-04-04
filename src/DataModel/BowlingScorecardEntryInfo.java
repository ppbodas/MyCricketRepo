package DataModel;

public class BowlingScorecardEntryInfo {
	private String mPlayerName;
	private String mOvers;
	private String mRuns;
	private String mMaiden;
	private String mWickets;
	
	public void setPlayerName(String value){
		mPlayerName = value;
	}
	public void setOvers(String value){
		mOvers = value;
	}
	public void setRuns(String value){
		mRuns = value;
	}
	public void setMaiden(String value){
		mMaiden = value;
	}
	public void setWickets(String value){
		mWickets = value;
	}
	public String getPlayerName(){
		return mPlayerName;
	}
	public String getOvers(){
		return mOvers;
	}
	public String getRuns(){
		return mRuns;
	}
	public String getMaiden(){
		return mMaiden;
	}
	public String getWickets(){
		return mWickets;
	}
}
