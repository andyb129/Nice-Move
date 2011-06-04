package uk.co.barbuzz;

import java.util.TreeMap;

public abstract class FeedParserFactory {
	
	static String feedUrlBase = "http://www.rightmove.co.uk/rss/property-for-sale/find.html?locationIdentifier=REGION^";
	
	static TreeMap<String, String> locations = new TreeMap<String, String>();
	static TreeMap<String, String> radius = new TreeMap<String, String>();	
	
	public static FeedParser getParser(TreeMap<String, String> searchData){
		String feedUrl = buildFeedURL(searchData);
		return new AndroidSaxFeedParser(feedUrl);
		//return getParser(ParserType.ANDROID_SAX);
	}

	private static String buildFeedURL(TreeMap<String, String> searchData) {
				
		//initialize url variable maps
		locations.put("Whitchurch, Bristol", "26697");
		locations.put("Kingswood, Bristol", "14169");
		locations.put("Southville, Bristol", "22658");
		locations.put("Longwell Green, Bristol", "16012");
		locations.put("Clifton, Bristol", "6574");
		locations.put("Bradley Stoke, Bristol", "4295");
		
		radius.put("Within 1/4 mile", "0.25");
		radius.put("Within 1/2 mile", "0.5");
		radius.put("Within 1 mile", "1");
		radius.put("Within 3 miles", "3");
		radius.put("Within 5 miles", "5");
		radius.put("Within 10 miles", "10");
		radius.put("Within 15 miles", "15");
		radius.put("Within 20 miles", "20");
		radius.put("Within 30 miles", "30");
		radius.put("Within 40 miles", "40");
		
		// add base URL
		String feedUrl = feedUrlBase;
		//add location code
		feedUrl += locations.get(searchData.get("location")) + "&insId=2";
		//add max price if selected
		if (!searchData.get("maxprice").equals("No max")) {
			feedUrl += "&maxPrice=" + searchData.get("maxprice").replace(",","").trim();
		}
		//add property type
		if (!searchData.get("propertytype").equals("Any")) {
			feedUrl += "&displayPropertyType=" + searchData.get("propertytype").toLowerCase();
			feedUrl += "&oldDisplayPropertyType=" + searchData.get("propertytype").toLowerCase();
		}		
		//add radius if selected
		if (!searchData.get("radius").equals("This area only")) {
			feedUrl += "&radius=" + radius.get(searchData.get("radius"));
		}
		
		return feedUrl;
	}
	
}
