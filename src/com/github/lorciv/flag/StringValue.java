package com.github.lorciv.flag;

public class StringValue implements FlagValue {

	String value;
	
	StringValue(String def) {
		value = def;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public void set(String s) throws FlagException {
		value = s;
	}
	
	@Override
	public String toString() {
		return value;
	}

}
