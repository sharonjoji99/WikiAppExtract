package com.sharon.WikiApi;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class WikiAppExtract {
	
	public JSONObject JsonObject(URL url) {
		JSONObject jobj = null;
		String inline = "";
		if(url==null) {
			throw new IllegalArgumentException("URL cannot be null");
		}
		try {
     
			HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
			conn.setRequestMethod("GET"); 
			conn.connect(); 
			Scanner sc = new Scanner(url.openStream());
			
			while(sc.hasNext()){
				inline += sc.nextLine();
			}
			
			JSONParser parse = new JSONParser(); 
			jobj = (JSONObject)parse.parse(inline); 
			
			
		}catch(Exception e) {
		  System.out.println(e);
		}
		
		
		return jobj;
	}
	
	public String PageId(JSONObject jobj) {
		if(jobj==null) {
			throw new IllegalArgumentException("URL cannot be null");
		}
		JSONObject jobj_1 = (JSONObject) jobj.get("query");
		JSONObject jobj_2 = (JSONObject) jobj_1.get("pages");
		ArrayList<String> list = new ArrayList<String>(jobj_2.keySet());
		String pageid = list.get(0);
		
		return pageid;
		
	}
	
	public List<String> SeeAlsoLinks(JSONObject jobj){
		if(jobj==null) {
			throw new IllegalArgumentException("URL cannot be null");
		}
		List<String> s2_match = new ArrayList<>(); 
		JSONObject jobj_1 = (JSONObject) jobj.get("query");
		JSONObject jobj_2 = (JSONObject) jobj_1.get("pages");	
		ArrayList<String> list = new ArrayList<String>(jobj_2.keySet());
		String pageid = list.get(0);	
		JSONObject jobj_3 = (JSONObject) jobj_2.get(pageid);
		JSONArray jobj_4 = (JSONArray) jobj_3.get("revisions");
		JSONObject jobj_5 = (JSONObject) jobj_4.get(0);
		String jobj_6_String = (String) jobj_5.get("*");
		Pattern pattern = Pattern.compile("==See also==\n.*\n.*\n\n");
		Matcher matcher = pattern.matcher(jobj_6_String);
		if (matcher.find())
		{
			
		    String s1 = matcher.group(0);
		    String[] words = s1.split("\n");
		    String[] wordsNew = new String[words.length-1];
		    for(int i =0 ;i<wordsNew.length;i++) {
		    wordsNew[i]=words[i+1];
		    }
		    String words_joined = String.join("\n", wordsNew);
		    Pattern pattern2 = Pattern.compile("\\[\\[(.*?)\\]\\]");
		    Matcher matcher2 = pattern2.matcher(words_joined);
		   
		    while(matcher2.find()) {
		    	s2_match.add(matcher2.group(1));
		    	
		     }
		    
		    }
		    
		return s2_match;
	}

	
	
}
