package de.jw.java.util.property.typeconverter;

import de.jw.java.util.property.typeconverter.internal.SplitByStringTypeConverter;

public class ToListBySpaceTypeConverter extends SplitByStringTypeConverter{

	public ToListBySpaceTypeConverter() {
		super(" ");
	}
}
