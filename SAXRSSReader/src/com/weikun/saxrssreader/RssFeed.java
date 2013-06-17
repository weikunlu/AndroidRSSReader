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

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author weikunlu
 *
 */
public class RssFeed implements Parcelable {

	private String title;
	private String link;
	private String description;
	private String language;
	private ArrayList<RssItem> rssItems;
	
	public RssFeed() {
		rssItems = new ArrayList<RssItem>();
	}
	
	public static final Parcelable.Creator<RssFeed> CREATOR = new Parcelable.Creator<RssFeed>() {
		public RssFeed createFromParcel(Parcel data) {
			
			Bundle readBundle = data.readBundle();
			
			RssFeed feed = new RssFeed();
			
			feed.title = readBundle.getString("title");
			feed.link = readBundle.getString("link");
			feed.description = readBundle.getString("description");
			feed.language = readBundle.getString("language");
			feed.rssItems = readBundle.getParcelableArrayList("rssItems");
			
			return feed;
		}
		public RssFeed[] newArray(int size) {
			return new RssFeed[size];
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Bundle data = new Bundle();
		data.putString("title", title);
		data.putString("link", link);
		data.putString("description", description);
		data.putString("language", language);
		data.putParcelableArrayList("rssItems", rssItems);
		dest.writeBundle(data);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the rssItems
	 */
	public ArrayList<RssItem> getRssItems() {
		return rssItems;
	}

	/**
	 * @param rssItems the rssItems to set
	 */
	public void setRssItems(ArrayList<RssItem> rssItems) {
		this.rssItems = rssItems;
	}
	
	/**
	 * @param rssItem add element to rssItems
	 */
	public void addRssItems(RssItem rssItem){
		this.rssItems.add(rssItem);
	}
	
}
