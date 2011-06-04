package uk.co.barbuzz;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class BaseFeedParser implements FeedParser {

	// names of the XML tags
	static final String CHANNEL = "channel";
	static final String PUB_DATE = "pubDate";
	static final  String GUID = "guid";
	static final  String LINK = "link";
	static final  String DESCRIPTION = "description";
	static final  String CATEGORY = "category";
	static final  String TITLE = "title";
	static final  String ITEM = "item";
	
	private final URL feedUrl;

	protected BaseFeedParser(String feedUrl){
		try {
			this.feedUrl = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	protected InputStream getInputStream() throws IOException {
		return feedUrl.openConnection().getInputStream();								
	}
}