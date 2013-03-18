package com.example.cricket;

public class ScorecardEntryInfo {
	private String mPlayerName;
	private String mPlayerImageURL;	
	private String mRunsScored;
	private String mBallsFaced;
	private String mOutInfo;
	
	public void setPlayerImageURL(String value){
		mPlayerImageURL = value;
	}
	public void setPlayerName(String value){
		mPlayerName = value;
	}
	public void setRunsScored(String value){
		mRunsScored = value;
	}
	public void setBallsFaced(String value){
		mBallsFaced = value;
	}
	public void setOutInfo(String value){
		mOutInfo = value;
	}
	
	public String getPlayerImageURL(){
		return mPlayerImageURL;
	}
	public String getPlayerName(){
		return mPlayerName;
	}
	public String getRunsScored(){
		if("0".equals(mRunsScored) && ("0".equals(mBallsFaced)))
			return "";
		return mRunsScored;
	}
	public String getBallsFaced(){
		if("0".equals(mRunsScored) && ("0".equals(mBallsFaced)))
			return "";
		return ("(" + mBallsFaced + ")");
	}
	public String getOutInfo(){
		return mOutInfo;
	}
}
