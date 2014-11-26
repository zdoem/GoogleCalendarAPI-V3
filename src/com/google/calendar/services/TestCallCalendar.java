package com.google.calendar.services;
import java.io.IOException;
import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;


/**
 * @author Zdoem Pwong   go2doem@gmail.com
 */
public class TestCallCalendar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			CalendarService calendarService = new CalendarService();
			calendarService.init();
			//-----------------------For CALENDAR Manager------------------------------------------------------------------------
			/*********************************
			 * สำหรับ Test Calendar ก็สามารถทำได้ โดย Test ทีละ method ลอง TestInsert
			 *  แล้ว copy เอา CalendarId มาจัดการ  Get,Delete ได้ตามสะดวก
			 * ******************************/
			//1.Show Calendar 
			testShowCalendars(calendarService);
			
			//2.Insert Calendar
			//testInsertCalendar(calendarService);			
			/*result insert calendar
			calendarInsert idCalendar : v4tm3f07v9fl249d4dbvr8eqio@group.calendar.google.com
			ID: cirl6615lb4lmni7avos1rvlrg@group.calendar.google.com
			Summary: "ทดสอบ CALENDAR#333*/
			
			//3.Get Calendar
			//String idCalendar ="v4tm3f07v9fl249d4dbvr8eqio@group.calendar.google.com";
			//testGetCalendar(calendarService, idCalendar);
			
			//4.Delete Calendar 
			//String idCalendar = "cirl6615lb4lmni7avos1rvlrg@group.calendar.google.com";
			//testDeleteCalendar(calendarService, idCalendar);
			//-----------------------------------------------------------------------------------------------
			
			//--------------------------------------For Event Manager---------------------------------------------------------
			/**********************************
			 * -ให้ test ด้วยการเอา Remark ออกโดย Test Insert Event ก่อน หรือจะ Test List Event ก่อนได้
			 * แนะนำให้ Test ที่ละ method  แล้ว copy eventId มาจัดการ  Get,Update,Delete ต่อไป 
			 * ********************************/
			//For Event Manager 
			//1. Insert Event & First Error			
			String CALENDAR_ID = "ndcvrh763spspn7340lgagtd9k@group.calendar.google.com"; //Calendar ID: ndcvrh763spspn7340lgagtd9k@group.calendar.google.com)
			//testEventInsert(calendarService, CALENDAR_ID);
			
			//2. Get Event
			//String eventId = "uq2itlpmenbb05sfg9hv3p8vrs";
			//testEventGet(calendarService, CALENDAR_ID, eventId);
			
			//3. Update Event
			//String eventId = "uq2itlpmenbb05sfg9hv3p8vrs";
			//testEventUpdate(calendarService, CALENDAR_ID, eventId);
			
			//4.List Event
			//DateTime startDT = DateTime.parseRfc3339("2014-11-01T10:00:00.000+07:00");
			//testEventList(calendarService, CALENDAR_ID,startDT);
			
			//5. Delete Event
			String eventId = "1pkogmpiuha7cjmt725bil56i8";
			testEventDelete(calendarService, CALENDAR_ID, eventId);
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	private static void testInsertCalendar(CalendarService calendarService) throws Exception{
		View.header("Insert CALENDAR");
		Calendar cal = calendarService.calendarInsert("ทดสอบ CALENDAR#333");
		View.display(cal);
		
	}
	private static void testShowCalendars(CalendarService calendarService) throws IOException {
		
		   View.header("Show Calendars");
		   CalendarList feed = calendarService.ListFeedCalendar() ;
		   View.display(feed);
    }

	private static void testGetCalendar(CalendarService calendarService,String calendarIdGoogle) throws Exception{
		 View.header("Get Calendars");
		Calendar calendarGet = calendarService.calendarGet(calendarIdGoogle);
		View.display(calendarGet);
	}
	
	private static void testDeleteCalendar(CalendarService calendarService,String calendarIdGoogle) throws Exception{
		 View.header("Delete Calendars");
		 calendarService.calendarDelete(calendarIdGoogle);
		 System.out.println("Delete calendar successfully by id:"+calendarIdGoogle);
		 testShowCalendars(calendarService);
	}
	
	private static void testEventInsert(CalendarService calendarService,String calendarId) throws Exception{
		View.header("Create Event");
		Event event = new Event();
		event.setSummary("ทดสอบ-Event#1");
	   	event.setDescription("ทดสอบครับทดสอบ เมื่อรันเสร็จให้กลับไปดู Calendar ในหน้า browser Refesh ดู");
	   	  
	   	DateTime start2 = DateTime.parseRfc3339("2014-11-25T10:30:00.000+07:00");
	   	DateTime end2 = DateTime.parseRfc3339("2014-11-25T11:30:00.000+07:00");
	   	event.setStart(new EventDateTime().setDateTime(start2).setTimeZone("Asia/Bangkok"));
	   	event.setEnd(new EventDateTime().setDateTime(end2).setTimeZone("Asia/Bangkok"));

		Event objRet = calendarService.eventInsert(calendarId, event);
		View.display(objRet);
	}
	
	private static void testEventGet(CalendarService calendarService,String calendarId ,String eventId) throws Exception{
		Event event = calendarService.eventGet(calendarId, eventId);
		View.display(event);
	}
	
	private static void testEventUpdate(CalendarService calendarService,String calendarId,String eventId) throws Exception{

		Event event = calendarService.eventGet(calendarId, eventId);
		View.display(event);

		System.out.println("---->Start Update Event .");	
		String editSummary = "(Edit)"+event.getSummary();
		String editDesc = "(Edit)"+event.getDescription();
		event.setSummary(editSummary);
	   	event.setDescription(editDesc);
	   	  
	   	DateTime start2 = DateTime.parseRfc3339("2014-11-29T12:30:00.000+07:00");
	   	DateTime end2 = DateTime.parseRfc3339("2014-11-29T13:30:00.000+07:00");
	   	event.setStart(new EventDateTime().setDateTime(start2).setTimeZone("Asia/Bangkok"));
	   	event.setEnd(new EventDateTime().setDateTime(end2).setTimeZone("Asia/Bangkok"));

	   	calendarService.eventUpdate(calendarId, event);
	   	System.out.println("---->After Update Event .");	
	   	Event event2 = calendarService.eventGet(calendarId, eventId);
		View.display(event2);
	}
	
	private static void testEventDelete(CalendarService calendarService,String calendarId,String eventId) throws Exception{
		calendarService.eventDelete(calendarId, eventId);
		System.out.println("Delete Calendar OK..");
	}
	private static void testEventList(CalendarService calendarService,String calendarId,DateTime timeMin) throws Exception{
		
		List list = calendarService.eventsList(calendarId, timeMin);//timeMin
		
		if(list!=null ){
			Event event = null;
			for(int i=0;i<list.size();i++){
				event = (Event) list.get(i);
				View.display(event);
			}
		}
		//View.display(list);
	}
	
	
}
