package de.jw.java.util.property.typeconverter;

import java.text.MessageFormat;

public class TypeConverters {
	public static final DoNothingTypeConverter DEFAULT_TYPE_CONVERTER = new DoNothingTypeConverter();  

	public static String errorMessage(Exception e) {
		return MessageFormat.format(TypeConverter.ERROR_MESSAGE_FORMAT, e.getMessage());
	}

}
