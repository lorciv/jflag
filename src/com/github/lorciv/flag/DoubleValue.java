package com.github.lorciv.flag;

public class DoubleValue implements FlagValue {

	private double value;
	
	DoubleValue(double def) {
		this.value = def;
	}
	
	public double getValue() {
		return value;
	}
	
	@Override
	public void set(String s) throws FlagException {
		try {
			value = Double.parseDouble(s);
		} catch (NumberFormatException e) {
			throw new FlagException("Could not parse double: " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return "" + value;
	}

}
