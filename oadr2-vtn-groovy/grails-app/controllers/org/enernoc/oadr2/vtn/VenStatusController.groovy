package org.enernoc.oadr2.vtn

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.enernoc.oadr2.vtn.VenStatus;
import org.enernoc.oadr2.vtn.Event;



import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.enernoc.oadr2.vtn.VenStatus;
import org.enernoc.oadr2.vtn.Program;

import org.enernoc.open.oadr2.model.EiEvent;

//import play.db.jpa.JPA;
//import play.db.jpa.Transactional;
//import play.mvc.Controller;
//import play.mvc.Result;
//import service.XmppService;
//import service.oadr.EiEventService;

//import com.google.inject.Inject;

/**
 * Controller to handle the XMPP and HTTP services as well as the VENStatuses
 * 
 * @author Jeff LaJoie
 *
 */
public class VenStatusController {
    	
	//@Inject static EiEventService eiEventService;
	//@Inject static XmppService xmppService;
	
    /**
     * Base method called to access the default page for the VENStatuses controller
     *  
     * @return a redirect to the venStatuses() call as to render the default page
     */
	def index() {
        return redirect(action: "venStatuses");
    }
    
	/**
	 * Default method to render the page for the VENStatus table
	 * 
	 * @param program - The program specific to the Events to be displayed
	 * @return the default rendered page for VENStatus display and deletion
	 */
//	@SuppressWarnings("unchecked")
 //   @Transactional
	def venStatuses() {
	    //def eventList = Event.list()
		def venStatuses = VenStatus.findAllWhere(eventID: params.eventID) 
		def	eventList = Event.executeQuery("SELECT distinct e.eventID FROM Event e")
		[venStatusList: venStatuses, eventList: eventList, currentEventID: params.eventID]
	}
	
	/**
	 * Function called to have the display table rendered via AJAX calls
	 * 
	 * @param program - The program specific to the Events to be displayed
	 * @return the rendered page for the VENStatus based on the program
	 
	//@SuppressWarnings("unchecked")
   //@Transactional
	public static Result renderAjaxTable(String program){
	    def listStatusObjects;
	    if(program != null){
	        listStatusObjects = JPA.em().createQuery("SELECT status " +
	                "FROM VENStatus status WHERE status.eventID = :event")
	                .setParameter("event", program)
	                .getResultList();
	    }
        else{
           // listStatusObjects = JPA.em().createQuery("FROM VENStatus").getResultList();
		 listStatusObjects = VenStatus.listOrderByTime(order:"desc")
			}	    
	    /**
	     * Comparator to sort the list of VENStatuses based on last response time
	     * 
	     * @author Jeff LaJoie
	     *
	     
		
		Collections.sort(listStatusObjects, new StatusObjectComparator());		
	    return ok(views.html.venStatusTable.render(listStatusObjects, program));
	}
	
	def renderAjaxTable(String program){
		def listStatusObjects;
		if(program != null){
			listStatusObjects = VenStatus.findAll("from VenStatus as v where v.author=?", [program])
		}
		else{
			listStatusObjects = VenStatus.listOrderByTime(order:"desc")
		}
		/**
		 * Comparator to sort the list of VENStatuses based on last response time
		 *
		 * @author Jeff LaJoie
		 *
		 *
		listStatusObjects.sort
		render(view: "venStatusTable", model: [listStatus: listStatusObjects, program: program])
	}*/
	
	/**
	 * Deletes a VENStatus based on the active program and the ID of the VENStatus
	 * 
	 * @param program - the selected program
	 * @param id - the database ID of the VENStatus to be deleted
	 * @return a redirect to the VENStatus default page without the deleted VENStatus
	 */
//	@Transactional
	def deleteStatus(){
		def venStatus = VenStatus.get(params.id)
		venStatus.delete()
		redirect(action:"venStatuses", params:[program: params.program])
	  //  JPA.em().remove(JPA.em().find(VENStatus.class, id));
	   //return redirect(routes.VENStatuses.venStatuses(program));
	}
}
