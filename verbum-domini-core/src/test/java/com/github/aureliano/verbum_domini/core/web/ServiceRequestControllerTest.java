package com.github.aureliano.verbum_domini.core.web;

import static com.github.aureliano.verbum_domini.core.web.ServiceRequestController.DEFAULT_INTERVAL_BETWEEN_REQUESTS;
import static com.github.aureliano.verbum_domini.core.web.ServiceRequestController.DEFAULT_MAX_REQUESTS_PER_HOUR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ServiceRequestControllerTest {

	@Test
	public void testInstance() {
		ServiceRequestController c1 = ServiceRequestController.instance();
		assertNotNull(c1);
		
		ServiceRequestController c2 = ServiceRequestController.instance();
		assertEquals(c1, c2);
		assertTrue(c1 == c2);
	}
	
	@Test
	public void testMinimumIntervalBetweenRequestsRespected() {
		ServiceRequestController c = ServiceRequestController.instance();
		Long requestTimeMillis = System.currentTimeMillis();
		Long lastRequestTimeMillis = null;
		
		boolean accepted = c.minimumIntervalBetweenRequestsRespected(requestTimeMillis, lastRequestTimeMillis);
		assertTrue(accepted);
		
		lastRequestTimeMillis = requestTimeMillis;
		accepted = c.minimumIntervalBetweenRequestsRespected(requestTimeMillis, lastRequestTimeMillis);
		assertTrue(accepted);
		
		lastRequestTimeMillis -= DEFAULT_INTERVAL_BETWEEN_REQUESTS;
		accepted = c.minimumIntervalBetweenRequestsRespected(requestTimeMillis, lastRequestTimeMillis);
		assertTrue(accepted);
		
		lastRequestTimeMillis += 2;
		accepted = c.minimumIntervalBetweenRequestsRespected(requestTimeMillis, lastRequestTimeMillis);
		assertFalse(accepted);
	}
	
	@Test
	public void testMaximumRequestsPerHourRespected() {
		UserRequestTracking track = new UserRequestTracking("max_req_per_hour");
		ServiceRequestController c = ServiceRequestController.instance();
		
		assertTrue(c.maximumRequestsPerHourRespected(track));
		
		track.addRequestTracking(System.currentTimeMillis());
		assertTrue(c.maximumRequestsPerHourRespected(track));
		
		for (short i = 0; i < DEFAULT_MAX_REQUESTS_PER_HOUR - 1; i++) {
			this.sleep(2);
			track.addRequestTracking(System.currentTimeMillis());
		}
		
		assertTrue(c.maximumRequestsPerHourRespected(track));
		
		this.sleep(2);
		track.addRequestTracking(System.currentTimeMillis());
		assertFalse(c.maximumRequestsPerHourRespected(track));
	}
	
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}
}