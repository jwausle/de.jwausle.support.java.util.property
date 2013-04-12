package de.jw.java.util.property.annotation;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class PropertyOverrideIfNulls {

	public static boolean extractDefaultForceOverrideOrFalse(Object object) {
		boolean existPropertyOverrideAnnotation = object.getClass().isAnnotationPresent(PropertyOverrideIfNull.class);
		if(!existPropertyOverrideAnnotation)
			return false;
		
		PropertyOverrideIfNull propertyOverrideAnnotation = object.getClass().getAnnotation(PropertyOverrideIfNull.class);
		boolean force = propertyOverrideAnnotation.force();
		return force;
	}

	public static List<Boolean> extractOverrides(List<Field> propertyFields, boolean defaultValue) {
		LinkedList<Boolean> overrideList = new LinkedList<Boolean>();
	
		for (Field field : propertyFields) {
			boolean existOverrideAnnotation = field.isAnnotationPresent(PropertyOverrideIfNull.class);
	
			if (!existOverrideAnnotation) {
				overrideList.add(defaultValue);
				continue;
			}
	
			PropertyOverrideIfNull overrideAnnotation = field.getAnnotation(PropertyOverrideIfNull.class);
			boolean trueToForceOverride = overrideAnnotation.force();
			overrideList.add(trueToForceOverride);
		}
	
		return overrideList;
	}

}
