/*
 *   Copyright 2013 weikunlu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
**/
package com.weikun.saxrssreader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.text.TextUtils;

/**
 * @author weikunlu
 *
 */
public class RssReader {

	public interface RssResultListener{
		public RssFeed getResult();
	}
	
	private static XMLReader createXMLReader() throws SAXException{
		try {
			SAXParserFactory sfactory = SAXParserFactory.newInstance();
			SAXParser sparser = sfactory.newSAXParser();
			return sparser.getXMLReader();
		} catch (ParserConfigurationException e) {
			throw new SAXException("ParserConfigurationException", e);
		}
	}
	
	private static URL createUrl(String urlStr) throws IOException{
		
		if(TextUtils.isEmpty(urlStr) && !urlStr.startsWith("http")){
			throw new IllegalArgumentException();
		}
		
		urlStr = urlStr.replaceAll(" ", "%20");
		
		return new URL(urlStr);
	}
	
	public static RssFeed parseCustomHandler(DefaultHandler handler, String urlStr) throws SAXException, IOException {

		URL rssUrl = createUrl(urlStr);
		
		return parseCustomHandler(handler, rssUrl);
	}
	
	/**
	 * @param handler
	 * @param url
	 * @return Feed {@link RssFeed array list}}
	 * @throws SAXException
	 * @throws IOException
	 */
	public static RssFeed parseCustomHandler(DefaultHandler handler, URL url) throws SAXException, IOException {
		XMLReader rssReader = createXMLReader();
		
		InputSource input = new InputSource(url.openStream());
		
		rssReader.setContentHandler(handler);
		rssReader.parse(input);
		
		return ((RssResultListener)handler).getResult();
	}
	
	public static RssFeed parseWithUrlString(String urlStr) throws SAXException, IOException {
		
		URL rssUrl = createUrl(urlStr);
		
		return parseWithURL(rssUrl);
	}
	
	/**
	 * @param url RSS link
	 * @return Feed {@link RssFeed array list}}
	 * @throws SAXException
	 * @throws IOException
	 */
	public static RssFeed parseWithURL(URL url) throws SAXException, IOException {
		XMLReader rssReader = createXMLReader();
		
		RssDefaultHandler handler = new RssDefaultHandler();
		InputSource input = new InputSource(url.openStream());
		
		rssReader.setContentHandler(handler);
		rssReader.parse(input);
		
		return handler.getResult();
	}
	
}
