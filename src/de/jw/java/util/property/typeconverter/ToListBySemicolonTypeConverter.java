package de.jw.java.util.property.typeconverter;

import de.jw.java.util.property.typeconverter.internal.SplitByStringTypeConverter;

public class ToListBySemicolonTypeConverter extends SplitByStringTypeConverter{

	public ToListBySemicolonTypeConverter() {
		super(";");
	}

}
