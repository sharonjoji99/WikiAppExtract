package com.sharon.WikiApi;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mockito;

@RunWith(MockitoJUnitRunner.class)
public class WikiAppExtractTest {

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
	
	
	}
	@Test
	public void testNullUrl() {
		
		wiki = new WikiAppExtract();
		IllegalArgumentException ex=assertThrows(IllegalArgumentException.class,()->wiki.JsonObject(null));
		assertEquals("URL cannot be null",ex.getMessage());
	}
  

	@Test
	public void testPageId() throws IOException,ParseException,org.json.simple.parser.ParseException {
		wiki = new WikiAppExtract();
		String jsonString = "{\"query\":{\"pages\":{\"086359\":{\"revisions\":[{\"*\":\"==See also==\\n*[[Lua (programming language)]]\\n*[[Squirrel (programming language)]]\\n\\n\"}]}}} }";
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject) parser.parse(jsonString); 
		assertEquals("086359",wiki.PageId(jobj));
		
	
	}
	
	@Test
	public void testNullPageId() {
		
		wiki = new WikiAppExtract();
		IllegalArgumentException ex=assertThrows(IllegalArgumentException.class,()->wiki.PageId(null));
		assertEquals("URL cannot be null",ex.getMessage());
	}

	@Test
	public void testSeeAlsoLinks()  throws IOException,ParseException,org.json.simple.parser.ParseException {
		wiki = new WikiAppExtract();
		String jsonString = "{\"query\":{\"pages\":{\"086359\":{\"revisions\":[{\"*\":\"==See also==\\n*[[Lua (programming language)]]\\n*[[Squirrel (programming language)]]\\n\\n\"}]}}} }";
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject) parser.parse(jsonString);
		List<String> l1 = new ArrayList<String>();
		l1.add("Lua (programming language)");
		l1.add("Squirrel (programming language)");
		assertEquals(l1,wiki.SeeAlsoLinks(jobj));
	}
	
	@Test
	public void testNullSeeAlsoLinks() {
		
		wiki = new WikiAppExtract();
		IllegalArgumentException ex=assertThrows(IllegalArgumentException.class,()->wiki.SeeAlsoLinks(null));
		assertEquals("URL cannot be null",ex.getMessage());
	}

}
