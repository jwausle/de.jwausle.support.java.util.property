package de.jw.java.util.property.typeconverter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class ToUrlTypeConverter implements TypeConverter<URL> {

	private static final Logger LOGGER = Logger.getLogger(ToUrlTypeConverter.class.getName());

	@Override
	public URL apply(String fromString) {
		if (fromString == null)
			return null;
		
		try {
			return new URL(fromString);
		} catch (MalformedURLException e) {
			LOGGER.warning(TypeConverters.errorMessage(e));
			return null;
		}
	}

}
