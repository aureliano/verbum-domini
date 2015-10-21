package com.github.aureliano.verbum_domini.core.web;

import java.util.ArrayList;
import java.util.List;

public class UserRequestTracking {

	private String id;
	private Short activeThreadsCounter;
	private List<Long> requests;
	
	public UserRequestTracking(String id) {
		this.id = id;
		this.activeThreadsCounter = 0;
		this.requests = new ArrayList<>();
	}
	
	public void addRequestTracking(Long timeMillis) {
		this.requests.add(timeMillis);
	}
	
	public void removeFirstRequestTracking() {
		this.requests.remove(0);
	}
	
	public void incrementUserThreadCounter() {
		this.activeThreadsCounter++;
	}
	
	public void decrementUserThreadCounter() {
		if (this.activeThreadsCounter > 0) {
			this.activeThreadsCounter--;
		}
	}
	
	public String getId() {
		return this.id;
	}
	
	public Short getActiveThreadsCounter() {
		return this.activeThreadsCounter;
	}
	
	public Integer getNumberOfRequests() {
		return this.requests.size();
	}
	
	public Long getFirstRequestOcurrence() {
		if (this.requests.isEmpty()) {
			return null;
		}
		
		return this.requests.get(0);
	}
	
	public Long getLastRequestOcurrence() {
		if (this.requests.isEmpty()) {
			return null;
		}
		
		int lastOcurrenceIndex = (this.requests.size() - 1);
		return this.requests.get(lastOcurrenceIndex);
	}
	
	public Long getLastButOneRequestOcurrence() {
		if (this.requests.size() < 2) {
			return null;
		}
		
		int lastButOneOcurrenceIndex = (this.requests.size() - 2);
		return this.requests.get(lastButOneOcurrenceIndex);
	}
}