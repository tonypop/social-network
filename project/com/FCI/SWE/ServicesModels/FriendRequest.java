package com.FCI.SWE.ServicesModels;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class FriendRequest {
	private String Sender;
	private String Reciever;
	private boolean status ;
	public FriendRequest(String Send, String Recieve, Boolean f) {
		Sender=Send;
		Reciever=Recieve;
		setStatus(f);
	}
	public String getSender() {
		return Sender;
	}
	public void setSender(String sender) {
		Sender = sender;
	}
	public String getReciever() {
		return Reciever;
	}
	public void setReciever(String reciever) {
		Reciever = reciever;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Boolean saveRequest() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("FriendRequests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
	
		try {
		Entity employee = new Entity("FriendRequests", list.size() + 2);

		employee.setProperty("sender", this.Sender);
		employee.setProperty("Recieve", this.Reciever);
		employee.setProperty("Status", this.status);
		
		datastore.put(employee);
		txn.commit();
		}
		finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;

	}
	public static boolean  searchRequest(String sender,String reciever) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        boolean flag=false;
		Query gaeQuery = new Query("FriendRequests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("sender").toString().equals(sender)&&
					entity.getProperty("reciever").toString().equals(reciever)) 
			{
					flag=true;	
					break;
			}
			flag=false;
		}

	return flag;
	}
	
	
	
	
}
