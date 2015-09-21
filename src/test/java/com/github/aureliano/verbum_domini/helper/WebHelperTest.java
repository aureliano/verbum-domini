package com.github.aureliano.verbum_domini.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.web.ServiceRequestController;
import com.github.aureliano.verbum_domini.core.web.ServiceRequestStatus;

public class WebHelperTest {

	@Test
	public void testCheckIpAddress() {
		assertFalse(WebHelper.checkIpAddress(null));
		assertFalse(WebHelper.checkIpAddress(""));
		
		assertFalse(WebHelper.checkIpAddress("12345678912"));
		assertFalse(WebHelper.checkIpAddress("125.362.487"));
		
		assertTrue(WebHelper.checkIpAddress("124.365.268.964"));
	}
	
	@Test
	public void testBuildPreventingAbuseMessage() {
		ServiceRequestController controller = ServiceRequestController.instance();
		String expected = "Interval between requests not respected. Must be lesser ( < ) than " + controller.getDelayBetweenRequests();
		String actual = WebHelper.buildPreventingAbuseMessage(ServiceRequestStatus.INTERVAL_BETWEEN_REQUEST_DISOBEYED);
		
		assertEquals(expected, actual);
		
		expected = "Maximum requests per hour not respected. Must be lesser or equal ( <= ) to " + controller.getMaxRequestsPerHour();
		actual = WebHelper.buildPreventingAbuseMessage(ServiceRequestStatus.MAX_REQUESTS_PER_HOUR_REACHED);
		
		assertEquals(expected, actual);
		
		expected = "Maximum threads per user not respected. Must be lesser or equal ( <= ) to " + controller.getMaxThreadsPerUser();
		actual = WebHelper.buildPreventingAbuseMessage(ServiceRequestStatus.MAX_THREADS_PER_USER_REACHED);
		
		assertEquals(expected, actual);
	}
}