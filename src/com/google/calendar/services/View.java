package com.google.calendar.services;

import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;


/**
 * @author Zdoem Pwong   
 */
public class View {

  static void header(String name) {
    System.out.println();
    System.out.println("============== " + name + " ==============");
    System.out.println();
  }


  static void display(CalendarList feed) {
	    if (feed.getItems() != null) {
	      for (CalendarListEntry entry : feed.getItems()) {
	        System.out.println();
	        System.out.println("-----------------------------------------------");
	        display(entry);
	      }
	    }
	  }
  static void display(Events feed) {
    if (feed.getItems() != null) {
      for (Event entry : feed.getItems()) {
        System.out.println();
        System.out.println("-----------------------------------------------");
        display(entry);
      }
    }
  }

  static void display(CalendarListEntry entry) {
    System.out.println("ID: " + entry.getId());
    System.out.println("Summary: " + entry.getSummary());
    if (entry.getDescription() != null) {
      System.out.println("Description: " + entry.getDescription());
    }
  }

  static void display(Calendar entry) {
    System.out.println("ID: " + entry.getId());
    System.out.println("Summary: " + entry.getSummary());
    if (entry.getDescription() != null) {
      System.out.println("Description: " + entry.getDescription());
    }
  }

 public static void display(Event event) {
	 System.out.println("==================================");
	if (event.getEtag() != null) {
	   System.out.println("getEtag: " + event.getEtag());
	} 
	
	if (event.getId()!= null) {
		   System.out.println("getId: " + event.getId());
		} 
	if (event.getICalUID() != null) {
		   System.out.println("getICalUID: " + event.getICalUID());
		}  
	if (event.getSummary()!= null) {
		   System.out.println("getSummary: " + event.getSummary());
		}  
	if (event.getDescription()!= null) {
		   System.out.println("getDescription: " + event.getDescription());
		}  
    if (event.getStart() != null) {
      System.out.println("Start Time: " + event.getStart());
    }
    if (event.getEnd() != null) {
      System.out.println("End Time: " + event.getEnd());
    }
    
  }
}
