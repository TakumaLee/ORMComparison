package com.github.takumalee.simplefacebook.entities;

/**
 * Age range (min-max) of the user.
 * 
 */
public class AgeRange {

	private final String mMin;
	private final String mMax;

	public AgeRange(String min, String max) {
		mMin = min;
		mMax = max;
	}

	public String getMin() {
		return mMin;
	}

	public String getMax() {
		return mMax;
	}
}
