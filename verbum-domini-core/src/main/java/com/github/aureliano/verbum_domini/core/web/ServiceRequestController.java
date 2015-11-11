package com.github.aureliano.verbum_domini.core.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.Environments;

public final class ServiceRequestController {

	private static final Logger logger = Logger.getLogger(ServiceRequestController.class);
	private static ServiceRequestController instance;
	
	protected static final int DEFAULT_MAX_REQUESTS_PER_HOUR = 500;
	protected static final int DEFAULT_INTERVAL_BETWEEN_REQUESTS = 1000;
	protected static final int DEFAULT_MAX_THREADS_PER_USER = 3;
	protected static final int ONE_HOUR_MILLIS = 60 * 60 * 1000;
	
	private int maxRequestsPerHour;
	private long intervalBetweenRequests;
	private int maxThreadsPerUser;
	
	private Map<String, UserRequestTracking> tracking;
	
	private ServiceRequestController() {
		this.tracking = new HashMap<>();
		
		this.configDefaultValues();
		this.loadAppConfiguration();
		
		this.scheduleCleanUserTracking();
	}
	
	public static ServiceRequestController instance() {
		if (instance == null) {
			instance = new ServiceRequestController();
		}
		
		return instance;
	}
	
	public ServiceRequestStatus track(String userId) {
		long timeMillis = System.currentTimeMillis();
		UserRequestTracking userTrack = this.tracking.get(userId);
		
		if (userTrack == null) {
			userTrack = new UserRequestTracking(userId);
		}
		
		userTrack.addRequestTracking(timeMillis);
		userTrack.incrementUserThreadCounter();
		
		if (!this.maximumRequestsPerHourRespected(userTrack)) {
			return ServiceRequestStatus.MAX_REQUESTS_PER_HOUR_REACHED;
		} else if (userTrack.getActiveThreadsCounter() > this.maxThreadsPerUser) {
			return ServiceRequestStatus.MAX_THREADS_PER_USER_REACHED;
		} else if (!this.minimumIntervalBetweenRequestsRespected(timeMillis, userTrack)) {
			return ServiceRequestStatus.INTERVAL_BETWEEN_REQUEST_DISOBEYED;
		}
		
		this.tracking.put(userId, userTrack);
		return ServiceRequestStatus.SUCCESS;
	}
	
	public void removeRequestThread(String userId) {
		UserRequestTracking userTrack = this.tracking.get(userId);
		if (userTrack == null) {
			return;
		}
		
		userTrack.decrementUserThreadCounter();
	}
	
	private void scheduleCleanUserTracking() {
		String name = "ServiceRequestController-UserTrackingCleaner";
		
		logger.info("Schedule task for cleaning user tracking.");
		logger.info("Scheduler name: " + name);
		logger.info("Scheduler delay: " + ONE_HOUR_MILLIS);
		logger.info("Scheduler period: " + ONE_HOUR_MILLIS);
		
		Timer timer = new Timer(name);
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				cleanUserTracking();
			}
		}, ONE_HOUR_MILLIS, ONE_HOUR_MILLIS);
	}
	
	protected void cleanUserTracking() {
		long timeMillis = System.currentTimeMillis();
		List<String> usersTrackingToRemove = new ArrayList<String>();
		
		logger.info("Execute user tracking cleaner.");
		logger.info("User tracking size before cleaning: " + this.tracking.size());
		
		for (String key : this.tracking.keySet()) {
			UserRequestTracking userTrack = this.tracking.get(key);
			Long lastUserRequestTime = userTrack.getLastRequestOcurrence();
			
			if (lastUserRequestTime == null) {
				continue;
			}
			
			long diff = timeMillis - lastUserRequestTime;
			if (diff > ONE_HOUR_MILLIS) {
				usersTrackingToRemove.add(key);
			}
		}
		
		for (String id : usersTrackingToRemove) {
			this.tracking.remove(id);
		}
		
		logger.info("User tracking size after cleaning: " + this.tracking.size());
		logger.info("Users tracking removed: " + usersTrackingToRemove.size());
	}
	
	public int getMaxRequestsPerHour() {
		return this.maxRequestsPerHour;
	}
	
	public long getDelayBetweenRequests() {
		return this.intervalBetweenRequests;
	}
	
	public int getMaxThreadsPerUser() {
		return this.maxThreadsPerUser;
	}
	
	protected boolean minimumIntervalBetweenRequestsRespected(Long requestTimeMillis, UserRequestTracking userTrack) {
		return this.minimumIntervalBetweenRequestsRespected(requestTimeMillis, userTrack.getLastButOneRequestOcurrence());
	}
	
	protected boolean minimumIntervalBetweenRequestsRespected(Long requestTimeMillis, Long lastRequestTimeMillis) {
		if (lastRequestTimeMillis == null) {
			return true;
		}
		
		long diff = requestTimeMillis - lastRequestTimeMillis;
		return ((diff == 0) || (diff >= this.intervalBetweenRequests));
	}
	
	protected boolean maximumRequestsPerHourRespected(UserRequestTracking userTrack) {
		Long firstRequest = userTrack.getFirstRequestOcurrence();
		if (firstRequest == null) {
			return true;
		}
		
		Long lastRequest = userTrack.getLastRequestOcurrence();
		if (lastRequest.equals(firstRequest)) {
			return true;
		}
		
		if (userTrack.getNumberOfRequests() <= this.maxRequestsPerHour) {
			return true;
		} else if ((userTrack.getNumberOfRequests() > this.maxRequestsPerHour) && ((lastRequest - firstRequest) >= ONE_HOUR_MILLIS)) {
			userTrack.removeFirstRequestTracking();
			return true;
		} else {
			return false;
		}
	}
	
	private void configDefaultValues() {
		this.maxRequestsPerHour = DEFAULT_MAX_REQUESTS_PER_HOUR;
		this.intervalBetweenRequests = DEFAULT_INTERVAL_BETWEEN_REQUESTS;
		this.maxThreadsPerUser = DEFAULT_MAX_THREADS_PER_USER;
	}
	
	private void loadAppConfiguration() {
		AppConfiguration configuration = AppConfiguration.instance();
		String value = configuration.getProperty("app.service.usage.max_requests_per_hour");
		
		if (value != null) {
			this.maxRequestsPerHour = Integer.parseInt(value);
		}
		
		value = configuration.getProperty("app.service.usage.interval_between_requests");
		if (value != null) {
			this.intervalBetweenRequests = Integer.parseInt(value);
		}
		
		value = configuration.getProperty("app.service.usage.max_threads_per_user");
		if (value != null) {
			this.maxThreadsPerUser = Integer.parseInt(value);
		}
		
		if (Environments.TEST.equals(AppConfiguration.instance().getEnvironment())) {
			this.intervalBetweenRequests = 10;
		}
	}
}