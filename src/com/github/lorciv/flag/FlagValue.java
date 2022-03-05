package com.github.lorciv.flag;

/**
 * A FlagValue is an object that contains the value of a flag.
 * 
 * Implementations of FlagValue must provide a set method that parses
 * the given string and sets the value. Method toString
 * should provide a string representation of the value. Implementatons
 * of FlagValue should also provide a type-specific way to retrieve the internal
 * value.
 * 
 * @author lorciv
 *
 */
public interface FlagValue {

	/**
	 * Set the value stored in a flag. Set is called once for each flag present in
	 * a FlagSet.
	 * 
	 * @param s the string to be parsed
	 * @throws FlagException in case of error while parsing the value
	 */
	void set(String s) throws FlagException;
	
}
