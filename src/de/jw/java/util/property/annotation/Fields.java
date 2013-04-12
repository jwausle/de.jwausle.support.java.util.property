package de.jw.java.util.property.annotation;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Fields {

	public static List<Field> getAllFields(Object object) {
		List<Field> allFields = new LinkedList<Field>();
		
		LinkedList<Field> fieldList = new LinkedList<Field>();
		Field[] fields = object.getClass().getFields();
		fieldList.addAll(Arrays.asList(fields));
		
		allFields.addAll(fieldList);
		
		LinkedList<Field> declaredFieldsList = new LinkedList<Field>();
		Field[] declaredFields = object.getClass().getDeclaredFields();
		declaredFieldsList.addAll(Arrays.asList(declaredFields));
		
		allFields.addAll(declaredFieldsList);
		return allFields;
	}

	public static List<Field> getAllFieldsWithAnnotation(List<Field> allFields, Class<Property> annotationClass) {
		List<Field> propertyFields = new LinkedList<Field>();
		for (Field field : allFields) {
			boolean isPropertyField = field.isAnnotationPresent(annotationClass);
			if(isPropertyField)
				propertyFields.add(field);
		}
		return propertyFields;
	}

	public static  void safeSetFieldValue(Field field, Object object, Object maybeNullValue, Boolean override) {
		try {
			if (maybeNullValue != null) {
				field.set(object, maybeNullValue);
				return;
			
			} else if (maybeNullValue == null && override) {
				field.set(object, maybeNullValue);
				return;
			} else{
				return;
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return;
		}
	}

}
