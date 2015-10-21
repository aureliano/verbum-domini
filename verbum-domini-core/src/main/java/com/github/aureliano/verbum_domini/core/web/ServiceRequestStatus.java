package com.github.aureliano.verbum_domini.core.web;

public enum ServiceRequestStatus {

	MAX_REQUESTS_PER_HOUR_REACHED,
	INTERVAL_BETWEEN_REQUEST_DISOBEYED,
	MAX_THREADS_PER_USER_REACHED,
	SUCCESS
}