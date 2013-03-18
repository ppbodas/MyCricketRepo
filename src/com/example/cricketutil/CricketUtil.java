package com.example.cricketutil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CricketUtil {
	static String webLink = "http://query.yahooapis.com/v1/public/yql?q=";
	static String yahooCricketUniqueStr = "&format=json&env=store%3A%2F%2F0TxIGQMQbObzvU4Apia0V0";
	static String Quote = "\"";
	
	public static String getYahooQueryURL(String query){
		String url = webLink + query + yahooCricketUniqueStr;
		
		return url;
		
	}
	public static String getPastMatchesURL (int startIndex, int endIndex, Boolean bOnlyMainTeam){
		String temp = "select * from cricket.past_matches(" + String.valueOf(startIndex) + "," + String.valueOf(endIndex) + ")";
		/*if(bOnlyMainTeam) {
			temp = temp + " where ";
			for(int i =1; i<25;++i) {
				temp = temp + "team_id=" + Quote + String.valueOf(i) + Quote;
				if(i<24){
					temp = temp + " or ";
				}				
			}
				
		}*/
		try {
			temp = URLEncoder.encode(temp,"UTF-8");			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return getYahooQueryURL(temp);
	}
	public static String getScorcardSummaryURL (String matchId){
		String temp = "select * from cricket.scorecard.summary where match_id=" + matchId;
		try {
			temp = URLEncoder.encode(temp,"UTF-8");			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return getYahooQueryURL(temp);
	}
	public static String getFullScorcardURL (String matchId){
		String temp = "select * from cricket.scorecard where match_id=" + matchId;
		try {
			temp = URLEncoder.encode(temp,"UTF-8");			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return getYahooQueryURL(temp);
	}
	public static String stripYahooStringFromJson(String yahooJsonResult){
		int startIndex = yahooJsonResult.indexOf('{');
    	int lastIndex = yahooJsonResult.lastIndexOf('}');
    	
    	String result = yahooJsonResult.substring(startIndex, lastIndex);
    	result = yahooJsonResult.concat("};");
    	return result;
	}
}
