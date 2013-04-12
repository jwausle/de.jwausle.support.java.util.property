package de.jw.java.util.property.annotation;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class Propertys {

	public static List<String> extractPropertyKeys(List<Field> propertyFields) {
		List<String> propertyKeys = new LinkedList<String>();
		for (Field field : propertyFields) {
			Property property = field.getAnnotation(Property.class);
			
			String key = property.key();
			propertyKeys.add(key);		
		}
		return propertyKeys;
	}

}
