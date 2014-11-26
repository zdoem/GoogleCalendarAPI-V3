package com.google.calendar.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes; 
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Zdoem Pwong   go2doem@gmail.com
 * project : calendar
 */
public class CalendarService {
	//private static final String APPLICATION_NAME = "google-calendar-massiveshare";
	//private JsonFactory jsonFactory;
	//private HttpTransport httpTransport;
	private Calendar client;
	private static Collection<String> scopes;
	private static String projectName ;

	 /** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	 /** Global instance of the JSON factory. */
	 private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	 static com.google.api.services.calendar.Calendar serviceCalendar;
	 //static Collection<String> scopes;
	 
	 private final static String PROXY_SET_NAME = "proxySet";
	 private final static String PROXY_SET = "true";
	 private final static String PROXY_HOST_NAME = "proxyHost";
	 private final static String PROXY_HOST = "132.146.1.15"; //production
	 //private final static String PROXY_HOST = "132.146.1.51";//test
	 private final static String PROXY_PORT_NAME = "proxyPort";
	 private final static String PROXY_PORT = "8080";

	static{
	   /*******************************************
	    * 
	    * กรณีที่ไม่ได้ใช้งาน Network ผ่าน proxy server ให้ remark ส่วนของ 	 Properties ทั้งหมด 
	    * 
	    ***********************************/
	   java.util.Properties props = System.getProperties(); 
	   System.getProperties().put(PROXY_SET_NAME,PROXY_SET); 
	   props.put(PROXY_HOST_NAME,PROXY_HOST);//proxy
	   props.put(PROXY_PORT_NAME,PROXY_PORT);//port
	   //System.setProperties(props); 
	   System.setProperties(props);	
	   //=======================================
	   scopes = new ArrayList<String>();
	   scopes.add(CalendarScopes.CALENDAR);
	   System.out.println("----->initialize the scopes");   
	   //authorize();
	}

	private Credential authorize(String accoutId,String pathKey12) throws Exception {

	    	GoogleCredential credential = new GoogleCredential.Builder()
		    .setTransport(httpTransport)
		    .setJsonFactory(JSON_FACTORY)
		    .setServiceAccountId(accoutId)//accoutId
		    .setServiceAccountPrivateKeyFromP12File(new File(pathKey12)) //"D:\\usr\\google_room24.zeed\\dev2-057a8d3ad0b6.p12"   pathKey12
		    .setServiceAccountScopes(scopes)
		   // .setServiceAccountScopes(Collections.singleton(scope))
		    .build();
	    	System.out.println("----->initialize the GoogleCredential.");
	    	return credential;

	}
	
	public void init() throws IOException {
		try {
			//Service Account
			//EMAIL ADDRESS
			String accoutId ="163550671930-r6rb70mqe49aausdpuc1qm8lnl8hpfr0@developer.gserviceaccount.com";

			String pathKey12 = "D:\\LHDEV\\git\\DevCalProject1-89dd7e566f0f.p12";// "/LHDEV/git/DevCalProject1-89dd7e566f0f.p12";

			//PROJECT NAME
			String projectName ="DevCalProject1";
								
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			Credential credential = authorize(accoutId,pathKey12);
			 
			serviceCalendar = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
             .setApplicationName(projectName).build();
			 System.out.println("----->initialize OK.");
		} catch (GeneralSecurityException e) {
			throw new IOException(e);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
	
	   /**
     * Subscribe ร  un calendrier
     *
     * Voir la documentation de google :
     * https://developers.google.com/google-apps/calendar/v3/reference/calendarList/insert?hl=fr
     *
     * @param calendarId
     * @return CalendarListEntry
     * @throws java.io.IOException
     */
    public CalendarListEntry calendarListInsert(String calendarId) throws IOException {
        CalendarListEntry calendarListEntry = new CalendarListEntry();
        calendarListEntry.setId(calendarId);
        return serviceCalendar.calendarList().insert(calendarListEntry).execute();
    }

    /**
     * Crรฉer un Calendrier
     *
     * Voir la documentation de google :
     * https://developers.google.com/google-apps/calendar/v3/reference/calendars/insert
     *
     * @param credential
     * @param nameOfCalendar
     * @return The new CalendarService
     * @throws java.io.IOException
     */
    public com.google.api.services.calendar.model.Calendar calendarInsert( String nameOfCalendar) throws IOException {
       // createServiceCalendar(credential);
        com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
        calendar.setSummary(nameOfCalendar);
        calendar.setTimeZone("Europe/Paris");

        com.google.api.services.calendar.model.Calendar createdCalendar = serviceCalendar.calendars().insert(calendar).execute();

       System.out.println("calendarInsert idCalendar : " + createdCalendar.getId());
        return createdCalendar;
    }
    
    public void calendarDelete( String idCalendar) throws IOException {
         serviceCalendar.calendars().delete(idCalendar).execute();
     }

    /**
     * Retourne un calendrier
     *
     * Voir la documentation de google :
     * https://developers.google.com/google-apps/calendar/v3/reference/calendars/get
     *
     * @param credential
     * @param calendarIdGoogle
     * @return Un calendrier ou Null si il existe pas
     * @throws java.io.IOException
     */
    public com.google.api.services.calendar.model.Calendar calendarGet(String calendarIdGoogle) throws IOException {
       // createServiceCalendar(credential);
        com.google.api.services.calendar.model.Calendar calendar = null;
        try {
            Calendar.Calendars.Get get = serviceCalendar.calendars().get(calendarIdGoogle);
            calendar = get.execute();
           System.out.println("calendarGet : " + calendar.getSummary());
            return calendar;
        } catch (com.google.api.client.googleapis.json.GoogleJsonResponseException e) {
            // Dans ce cas on ne doit pas arreter le script
           System.out.println("calendarGet : NULL");
           System.out.println("Warning (Le calendrier n'existe pas) : " + e);
            return null;
        }
    }

    /**
     * Returns entries on the user's calendar list
     *
     * Voir la documentation de google :
     * https://developers.google.com/google-apps/calendar/v3/reference/calendarList/list?hl=fr
     *
     * @param credential
     * @return CalendarListEntry
     * @throws IOException
     */
    public List<CalendarListEntry> calendarList() throws IOException {
        String pageToken = null;
        List<CalendarListEntry> items = new ArrayList<CalendarListEntry>();
        do {
            CalendarList calendarList = serviceCalendar.calendarList().list().setPageToken(pageToken).execute();
            List<CalendarListEntry> listEntry = calendarList.getItems();
            items.addAll(listEntry);
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);
        return items;
    }
    
    public CalendarList ListFeedCalendar() throws IOException{
    	  CalendarList feed = serviceCalendar.calendarList().list().execute();
    	  return feed;
    }


    /**
     * Returns events on the specified calendar
     *
     * Voir la documentation de google :
     * https://developers.google.com/google-apps/calendar/v3/reference/events/list
     *
     * @param credential
     * @param calendarIdGoogle
     * @param timeMin
     * @return A list of Event
     * @throws java.io.IOException
     */
    public List<Event> eventsList(String calendarIdGoogle, DateTime timeMin)
            throws IOException {
       // createServiceCalendar(credential);
        String pageToken = null;
        List<Event> listAllEvents = new ArrayList<Event>();

        do {
            Calendar.Events.List listEvents = serviceCalendar.events().list(calendarIdGoogle).setPageToken(pageToken);

            if (timeMin != null)
                listEvents.setTimeMin(timeMin);

            Events events = listEvents.execute();

            List<Event> items = events.getItems();
            for (Event event : items) {
                listAllEvents.add(event);
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
       System.out.println("nb Events : " + listAllEvents.size());
        return listAllEvents;
    }

    /**
     * Creates an event
     *
     * Voir la documentation de google :
     * https://developers.google.com/google-apps/calendar/v3/reference/events/insert
     *
     * @param credential
     * @param calendarIdGoogle
     * @param event
     * @return Event
     * @throws java.io.IOException
     */
    public Event eventInsert(String calendarIdGoogle, Event event)
            throws IOException {
        Event createdEvent = serviceCalendar.events().insert(calendarIdGoogle, event).execute();
       System.out.println("createdEvent : " + createdEvent.getId() + ", " + event.getSummary());
        return createdEvent;
    }

    /**
     * Updates an event
     *
     * Voir la documentation de google :
     * https://developers.google.com/google-apps/calendar/v3/reference/events/update
     *
     * @param credential
     * @param calendarIdGoogle
     * @param event
     * @throws java.io.IOException
     */
    public Event eventUpdate( String calendarIdGoogle, Event event)
            throws IOException {
        Event updatedEvent = serviceCalendar.events().update(calendarIdGoogle, event.getId(), event).execute();
       //System.out.println("eventUpdate updatedEvent : " + updatedEvent.toPrettyString());
       //System.out.println("createdUpdate : " + updatedEvent.getId() + ", " + event.getSummary());
        return updatedEvent;
    }

    /**
     * Returns an event
     *
     * Voir la documentation de google :
     * https://developers.google.com/google-apps/calendar/v3/reference/events/get
     *
     * @param credential
     * @param calendarIdGoogle
     * @param eventId
     * @return
     * @throws java.io.IOException
     */
    public Event eventGet(String calendarIdGoogle, String eventId)
            throws IOException {
       // createServiceCalendar(credential);
        Event event = serviceCalendar.events().get(calendarIdGoogle, eventId).execute();
        return event;

    }

    /**
     * Deletes an event
     *
     * Voir la documentation de google :
     * https://developers.google.com/google-apps/calendar/v3/reference/events/delete
     *
     * @param credential
     * @param calendarIdGoogle
     * @param eventId
     * @throws java.io.IOException
     */
    public void eventDelete(String calendarIdGoogle, String eventId)
            throws IOException {
        serviceCalendar.events().delete(calendarIdGoogle, eventId).execute();
    }

}

