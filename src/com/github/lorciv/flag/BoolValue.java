package com.github.lorciv.flag;

public class BoolValue implements FlagValue {

	private boolean value;
	
	BoolValue(boolean def) {
		value = def;
	}
	
	public boolean getValue() {
		return value;
	}
	
	@Override
	public void set(String s) throws FlagException {
		value = Boolean.parseBoolean(s);
	}
	
	@Override
	public String toString() {
		return "" + value;
	}

}
