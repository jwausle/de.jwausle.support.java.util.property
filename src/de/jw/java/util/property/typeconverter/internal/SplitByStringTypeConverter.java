package de.jw.java.util.property.typeconverter.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.jw.java.util.property.typeconverter.TypeConverter;

public class SplitByStringTypeConverter implements TypeConverter<List<String>>{

	private String splitter;

	public SplitByStringTypeConverter(String splitter) {
		this.splitter = splitter;
	}

	@Override
	public List<String> apply(String fromString) {
		if(fromString == null)
			return Collections.emptyList();
		
		if(splitter == null)
			return Collections.emptyList();
		
		String[] split = fromString.split(splitter);
		return Arrays.asList(split);
	}

}
