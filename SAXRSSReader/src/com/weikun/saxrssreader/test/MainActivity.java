package com.weikun.saxrssreader.test;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.weikun.saxrssreader.R;
import com.weikun.saxrssreader.RssFeed;
import com.weikun.saxrssreader.RssItem;
import com.weikun.saxrssreader.RssReader;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Thread thd = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					/*
					RssFeed rssFeed = RssReader.parseWithUrlString("http://open.nat.gov.tw/OpenFront/rss_newgpn.jspx");
					
					for (RssItem rssItem : rssFeed.getRssItems()) {
						
						Log.i("debug", rssFeed.getTitle()+"  "+rssItem.getTitle()+"  "+rssItem.getLink() +" "+rssItem.getPubDate());
					}
					
					
					rssFeed = RssReader.parseWithUrlString("http://blog.csdn.net/xyz_lmn/rss/list");
					
					for (RssItem rssItem : rssFeed.getRssItems()) {
						
						Log.i("debug", rssFeed.getTitle()+"  "+rssItem.getTitle()+"  "+rssItem.getLink() +" "+rssItem.getPubDate());
					}
					*/
					
					RssFeed rssFeed2 = RssReader.parseWithUrlString("http://tw.news.yahoo.com/rss/technology");
					for (RssItem rssItem : rssFeed2.getRssItems()) {
						Log.i("debug", rssFeed2.getTitle()+"  "+rssItem.getTitle()+"  "+rssItem.getLink() +" "+rssItem.getPubDate());
					}
					
					
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		thd.start();
		
	}

}
