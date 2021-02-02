package com.sharon.WikiApi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WikiAppExtractTest2 {

WikiAppExtract wiki ;
	
	@Test
	public void testJsonObject()  throws IOException,ParseException, org.json.simple.parser.ParseException{
	wiki = new WikiAppExtract();
	String jsonString = "{\"query\":{\"pages\":{\"086359\":{\"revisions\":[{\"*\":\"==See also==\\n*[[Lua (programming language)]]\\n*[[Squirrel (programming language)]]\\n\\n\"}]}}} }";
	InputStream targetStream = new ByteArrayInputStream(jsonString.getBytes());
	URL url = Mockito.mock(URL.class);
	HttpURLConnection conn = Mockito.mock(HttpURLConnection.class);
	Mockito.when(url.openConnection()).thenReturn(conn);
	Mockito.when(url.openStream()).thenReturn(targetStream);
	JSONParser parser = new JSONParser();
	JSONObject jobj = (JSONObject) parser.parse(jsonString); 
	assertEquals(jobj,wiki.JsonObject(url));
	System.out.println(wiki.SeeAlsoLinks(jobj));
	assertEquals("086359",wiki.PageId(jobj));
	List<String> l1 = new ArrayList<String>();
	l1.add("Lua (programming language)");
	l1.add("Squirrel (programming language)");
	System.out.println(l1);
	assertEquals(l1,wiki.SeeAlsoLinks(jobj));
	
	
	}
  

//	@Test
//	public void testPageId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSeeAlsoLinks() {
//		fail("Not yet implemented");
//	}

}
