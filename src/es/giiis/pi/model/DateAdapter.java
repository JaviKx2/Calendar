package es.giiis.pi.model;

import java.util.Date;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date>{
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat(
	        "yyyy-MM-dd");
	
	@Override
	public String marshal(Date arg0) throws Exception {		
		return formatter.format(arg0);
	}

	@Override
	public Date unmarshal(String arg0) throws Exception {
		return formatter.parse(arg0);
	}

}
