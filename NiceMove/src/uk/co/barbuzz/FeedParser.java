package uk.co.barbuzz;
import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

public interface FeedParser {
	List<Message> parse() throws IOException, SAXException;
}
