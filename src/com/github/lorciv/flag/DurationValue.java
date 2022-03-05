package com.github.lorciv.flag;

import java.time.Duration;
import java.time.format.DateTimeParseException;

public class DurationValue implements FlagValue {
	
	private Duration value;
	
	DurationValue(Duration def) {
		value = def;
	}
	
	public Duration getValue() {
		return value;
	}

	@Override
	public void set(String s) throws FlagException {
		try {
			value = Duration.parse(s);
		} catch (DateTimeParseException e) {
			throw new FlagException("Could not parse duration: " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

}
