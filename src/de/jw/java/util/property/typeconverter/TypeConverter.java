package de.jw.java.util.property.typeconverter;

/**
 * Converter if the field type not String. <br />
 * - convert a String -> T <br />
 * - handle null case inside apply <br />
 * - ...<br />
 * 
 * @author winter
 * 
 * @param <T>
 */
public interface TypeConverter<T> {
	public static final String ERROR_MESSAGE_FORMAT = "TypeConverter error: {0}";

	public T apply(String fromString);
}
