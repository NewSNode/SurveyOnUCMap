package cn.creable.surveyOnUCMap;

import java.io.IOException;

import org.jeo.json.parser.ContentHandler;
import org.jeo.json.parser.ParseException;

public class GeocoderJSONHandler implements ContentHandler {
	
	private String key;
	public String text;

	@Override
	public boolean endArray() throws ParseException, IOException {
		return true;
	}

	@Override
	public void endJSON() throws ParseException, IOException {
		
	}

	@Override
	public boolean endObject() throws ParseException, IOException {
		return true;
	}

	@Override
	public boolean endObjectEntry() throws ParseException, IOException {
		return true;
	}

	@Override
	public boolean primitive(Object value) throws ParseException, IOException {
		if (key.equals("formatted_address"))
			text=(String) value;
		return true;
	}

	@Override
	public boolean startArray() throws ParseException, IOException {
		return true;
	}

	@Override
	public void startJSON() throws ParseException, IOException {
		
	}

	@Override
	public boolean startObject() throws ParseException, IOException {
		return true;
	}

	@Override
	public boolean startObjectEntry(String key) throws ParseException, IOException {
		this.key=key;
		return true;
	}

}
