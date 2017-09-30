package es.giiis.pi.model;

import java.sql.Time;
import java.text.SimpleDateFormat;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimeAdapter extends XmlAdapter<String, Time>{
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat(
	        "HH:mm");
	
	@Override
	public String marshal(Time arg0) throws Exception {		
		return formatter.format(arg0);
	}

	@Override
	public Time unmarshal(String arg0) throws Exception {
		return new Time(formatter.parse(arg0).getTime());
	}

}
