package es.giiis.pi.model;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "event")
public class Event {
	@XmlElement(name = "id")
	private long id;
	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "calendar")
	private long calendar;
	@XmlElement(name = "startdate")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date startDate;
	@XmlElement(name = "starttime")
	@XmlJavaTypeAdapter(TimeAdapter.class)
	private Time startTime;
	@XmlElement(name = "endtime")
	@XmlJavaTypeAdapter(TimeAdapter.class)
	private Time endTime;
	@XmlElement(name = "enddate")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date endDate;

	private int day;
	private int hour;
	
	public Event(){
		Calendar calendar = new GregorianCalendar();
		startDate = calendar.getTime();
		startTime = new Time(calendar.getTimeInMillis());
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	
	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCalendar() {
		return calendar;
	}
	public void setCalendar(long calendar) {
		this.calendar = calendar;
	}
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getHour(){
		return this.hour;
	}

	public void setDayByDate(){
		this.day = getStartDay();
	}
	public void setHourByTime(){
		this.hour = getDayHour();
	} 
	
	public int getStartDay(){
		SimpleDateFormat formatter = new SimpleDateFormat("dd");	
		String day = formatter.format(startDate); 	
		return Integer.parseInt(day);
	}
	public int getDayHour(){
		SimpleDateFormat formatter = new SimpleDateFormat("HH");
		return Integer.parseInt(formatter.format(startTime));
	}

	@Override
	public String toString() {
		return name;
	}

	public boolean validate(Map<String, String> messages) {
		return true;
	}

}
