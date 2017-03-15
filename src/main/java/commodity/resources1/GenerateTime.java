package main.java.commodity.resources1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.java.commodity.resources.Constant;
import main.java.commodity.resources.Random;


public class GenerateTime {
	
	private Calendar calendar;
	
	public GenerateTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(2017, 0, 1, 0, 0, 0);
		this.calendar=cal;
	}
	
	
	public List<Calendar> getDataTime() {
		SimpleDateFormat sdf_bed = new SimpleDateFormat("yy/MM/dd HH:mm:ss ");
		Calendar calEnd = Calendar.getInstance();
		calEnd.set(2017, 3, 1, 0, 0, 0);
		
		List<Calendar> timeList= new ArrayList<>();		
		
		while (calendar.before(calEnd)) {		
			int time = Random.rInt(Constant.Time);
			calendar.add(Calendar.SECOND, time);
			Calendar cal = (Calendar) calendar.clone();
			timeList.add(cal);
			
		}
		return timeList;
	}


}
