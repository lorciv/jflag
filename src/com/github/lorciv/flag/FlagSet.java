package com.github.lorciv.flag;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;

/**
 * <p>
 * A FlagSet represents a set of defined flags. Flags can be defined using
 * <code>add</code> methods, and then parsed with <code>parse</code>. After
 * parsing, the remaining non-flag arguments can be accessed with
 * <code>args</code>.
 * </p>
 * 
 * <p>
 * Add methods allow to specify the name, description and default value for the
 * flag. Each add method returns an object that can be used to access the parsed flag
 * value (or the default value) once the FlagSet to which it belongs has been
 * parsed. If multiple flags with the same name are added to a FlagSet, only the
 * last one will be considered.
 * </p>
 * 
 * @author lorciv
 *
 */
public class FlagSet {
	
	private String name;
	private HashMap<String, Flag> flags = new HashMap<String, Flag>();
	private String[] args;
	private boolean parsed;
	
	public FlagSet() {}
	
	public FlagSet(String name) {
		this.name = name;
	}
	
	/**
	 * Add a new flag of type int to the flag set.
	 * 
	 * @param name the name of the flag
	 * @param description the description of the flag
	 * @param def the default value
	 * @return the object where the flag value will be stored
	 */
	public IntValue addInt(String name, String description, int def) {
		IntValue v = new IntValue(def);
		addValue(name, description, v);
		return v;
	}
	
	/**
	 * Add a new flag of type double to the flag set.
	 * 
	 * @param name the name of the flag
	 * @param description the description of the flag
	 * @param def the default value
	 * @return the object where the flag value will be stored
	 */
	public DoubleValue addDouble(String name, String description, double def) {
		DoubleValue v = new DoubleValue(def);
		addValue(name, description, v);
		return v;
	}
	
	/**
	 * Add a new flag of type String to the flag set.
	 * 
	 * @param name the name of the flag
	 * @param description the description of the flag
	 * @param def the default value
	 * @return the object where the flag value will be stored
	 */
	public StringValue addString(String name, String description, String def) {
		StringValue v = new StringValue(def);
		addValue(name, description, v);
		return v;
	}
	
	/**
	 * Add a new flag of type boolean to the flag set.
	 * 
	 * @param name the name of the flag
	 * @param description the description of the flag (for usage message)
	 * @param def the default value
	 * @return the object where the flag value will be stored
	 */
	public BoolValue addBool(String name, String description, boolean def) {
		BoolValue v = new BoolValue(def);
		addValue(name, description, v);
		return v;
	}
	
	/**
	 * Add a new flag of type java.time.Duration to the flag set.
	 * 
	 * @param name the name of the flag
	 * @param description the description of the flag (for usage message)
	 * @param def the default duration
	 * @return the object where the flag value will be stored
	 */
	public DurationValue addDuration(String name, String description, Duration def) {
		DurationValue v = new DurationValue(def);
		addValue(name, description, v);
		return v;
	}
	
	/**
	 * Add a new flag to the flag set. The FlagValue object should be set to
	 * the default value.
	 * 
	 * @param name the name of the flag
	 * @param description the description of the flag (for usage message)
	 * @param value the object where the flag value will be stored
	 */
	public void addValue(String name, String description, FlagValue value) {
		Flag f = new Flag();
		f.name = name;
		f.description = description;
		f.value = value;
		f.defValue = value.toString();
		flags.put(name, f);
	}
	
	/**
	 * Parse the flags from the argument array. It must be called after all flags
	 * in the FlagSet are defined and before flag values are accessed by the program.
	 * 
	 * @param args the argument array
	 * @throws FlagException in case of errors during parsing
	 */
	public void parse(String[] args) throws FlagException {
		
		if (parsed) {
			throw new IllegalStateException("FlagSet already parsed");
		}
		parsed = true;
		
		int i;
		
		for (i = 0; i < args.length; i++) {
			
			String arg = args[i];
			
			if (!arg.startsWith("-")) {
				break;
			}
			arg = arg.substring(1);
			
			String[] split = arg.split("=");
			if (split.length != 2) {
				throw new FlagException("Could not parse flag: Invalid format");
			}
			
			if (!flags.containsKey(split[0])) {
				throw new FlagException("Unexpected flag: " + split[0]);
			}
			try {
				flags.get(split[0]).value.set(split[1]);
			} catch (FlagException e) {
				throw new FlagException("Flag " + split[0] + ": " + e.getMessage());
			}
			
		}
		
		this.args = Arrays.copyOfRange(args, i, args.length);
		
	}
	
	/**
	 * Return the non-flag arguments.
	 * 
	 * @return the non-flag arguments
	 */
	public String[] args() {
		return args;
	}
	
	/**
	 * Print a usage message for the FlagSet to standard error.
	 */
	public void printUsage() {
		System.err.print("Usage");
		if (name != null) {
			System.err.print(" of " + name);
		}
		System.err.println(":");
		for (Flag f : flags.values()) {
			System.err.println("\t" + f.usage());
		}
	}
	
	static class Flag {
		
		String name;
		String description;
		FlagValue value;
		String defValue;
		
		String usage() {
			return String.format("-%s\t %s (default: %s)", name, description, defValue);
		}
		
	}

}
