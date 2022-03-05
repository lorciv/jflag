package com.github.lorciv.flag;

/**
 * The value of an integer flag. It is returned by FlagSet.addInt, and
 * should be accessed after the FlagSet has been parsed.
 * 
 * @author lorciv
 *
 */
public class IntValue implements FlagValue {
	
	private int value;
	
	IntValue(int def) {
		value = def;
	}
	
	/**
	 * Get the value of the flag, or the default value if the flag has not been set.
	 * It should be called after the corresponding FlagSet has been parsed.
	 * 
	 * @return the value of the flag
	 */
	public int getValue() {
		return value;
	}

	@Override
	public void set(String s) throws FlagException {
		try {
			value = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new FlagException("Could not parse int: " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return "" + value;
	}

}
