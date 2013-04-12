package de.jw.java.util.property.typeconverter;

public class DoNothingTypeConverter implements TypeConverter<String>{

	@Override
	public String apply(String fromString) {
		return fromString;
	}

}
