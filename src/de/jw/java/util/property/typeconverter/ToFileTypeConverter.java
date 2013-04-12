package de.jw.java.util.property.typeconverter;

import java.io.File;

public class ToFileTypeConverter implements TypeConverter<File>{

	@Override
	public File apply(String fromString) {
		if(fromString == null)
			return null;
		
		return new File(fromString);
	}

}
