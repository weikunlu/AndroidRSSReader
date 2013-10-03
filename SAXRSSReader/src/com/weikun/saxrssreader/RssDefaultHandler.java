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

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.weikun.saxrssreader.RssReader.RssResultListener;

/**
 * @author weikunlu
 * 
 */
public class RssDefaultHandler extends DefaultHandler implements
		RssResultListener {

	private static final String TAG = RssDefaultHandler.class.getSimpleName();
	
	private StringBuilder sb = new StringBuilder(1024);
	private RssFeed rssFeed = new RssFeed();
	private RssItem tmpRssItem;

	private static Set<String> excludeTagSet = new HashSet<String>();
	
	static{
		excludeTagSet.add("item");
		excludeTagSet.add("channel");
		excludeTagSet.add("rss");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.weikun.saxrssreader.RssReader.RssResultListener#getResult()
	 */
	@Override
	public RssFeed getResult() {
		return rssFeed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (ch.length > 0) {
			sb.append(ch, start, length);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (rssFeed != null && qName.equals("item")) {
			tmpRssItem = new RssItem();
			rssFeed.addRssItems(tmpRssItem);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		try {
			if(excludeTagSet.contains(qName)){
				return;
			}
			
			if (tmpRssItem == null) {
				
				if (qName != null && qName.length() > 0) {
					String methodName = "set"
							+ qName.substring(0, 1).toUpperCase()
							+ qName.substring(1);
					Method method = rssFeed.getClass().getMethod(methodName, String.class);
					method.invoke(rssFeed, sb.toString().trim());
				}

			} else {
				if (qName.equals("content:encoded"))
					qName = "content";
				
				String methodName = "set"
						+ qName.substring(0, 1).toUpperCase()
						+ qName.substring(1);
				Method method = tmpRssItem.getClass().getMethod(methodName, String.class);
				method.invoke(tmpRssItem, sb.toString().trim());
			}
			
		} catch (Exception e) {
			Log.w(TAG, "fail with " + qName + ": " +e.getMessage());
		} finally {
			if (sb.length() > 0)
				sb.delete(0, sb.length());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

}
