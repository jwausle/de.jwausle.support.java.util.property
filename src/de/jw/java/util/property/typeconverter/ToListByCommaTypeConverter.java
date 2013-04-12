package de.jw.java.util.property.typeconverter;

import de.jw.java.util.property.typeconverter.internal.SplitByStringTypeConverter;

public class ToListByCommaTypeConverter extends SplitByStringTypeConverter{

	public ToListByCommaTypeConverter() {
		super(",");
	}


}
