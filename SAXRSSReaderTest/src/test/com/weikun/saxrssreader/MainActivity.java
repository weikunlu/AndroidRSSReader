package test.com.weikun.saxrssreader;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.weikun.saxrssreader.RssFeed;
import com.weikun.saxrssreader.RssItem;
import com.weikun.saxrssreader.RssReader;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Thread thd = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				RssReader reader = new RssReader();
				
				try {
					RssFeed rssFeed = reader.parseWithUrlString("http://open.nat.gov.tw/OpenFront/rss_newgpn.jspx");
					
					for (RssItem rssItem : rssFeed.getRssItems()) {
						
						Log.i("debug", rssFeed.getTitle()+"  "+rssItem.getTitle()+"  "+rssItem.getLink() +" "+rssItem.getPubDate());
					}
					
					
					rssFeed = reader.parseWithUrlString("http://blog.csdn.net/xyz_lmn/rss/list");
					
					for (RssItem rssItem : rssFeed.getRssItems()) {
						
						Log.i("debug", rssFeed.getTitle()+"  "+rssItem.getTitle()+"  "+rssItem.getLink() +" "+rssItem.getPubDate());
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
