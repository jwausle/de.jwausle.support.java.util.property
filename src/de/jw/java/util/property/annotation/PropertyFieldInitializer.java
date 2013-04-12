package de.jw.java.util.property.annotation;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import de.jw.java.util.property.supplier.PropertySupplier;
import de.jw.java.util.property.typeconverter.TypeConverter;
import de.jw.java.util.property.typeconverter.TypeConverters;

public class PropertyFieldInitializer<T> {
	private static final Logger LOGGER = Logger.getLogger(PropertyFieldInitializer.class.getName());

	public static <T> PropertyFieldInitializer<T> create(T object) {
		return new PropertyFieldInitializer<T>(object);
	}

	private final T objectInstance;

	public PropertyFieldInitializer(T object) {
		this.objectInstance = object;
	}

	public T init() {
		T object = objectInstance;
		if (object == null)
			return null;

		List<Field> propertyFields = getAllPropertyFields(object);
		if(propertyFields.isEmpty())
			return object;

		List<String> propertyKeys = Propertys.extractPropertyKeys(propertyFields);
		assert propertyKeys.size() == propertyFields.size();

		List<Properties> propertiesList = extractAndInitPropertiesList(object, propertyFields);
		assert propertyFields.size() == propertiesList.size();

		List<Boolean> overrideList = extractOverrideIfNullList(object, propertyFields);
		assert propertyFields.size() == overrideList.size();
		
		List<TypeConverter<?>> typeConverterList = extractTypeConverterList(object,propertyFields);
		assert propertyFields.size() == typeConverterList.size();

		Iterator<Field> fieldIterator = propertyFields.iterator();
		Iterator<String> keyIterator = propertyKeys.iterator();
		Iterator<Properties> propertiesIterator = propertiesList.iterator();
		Iterator<Boolean> overrideIterator = overrideList.iterator();
		Iterator<TypeConverter<?>> typeConverters = typeConverterList.iterator();

		while (fieldIterator.hasNext()) {
			Field field = fieldIterator.next();
			String key = keyIterator.next();
			Properties properties = propertiesIterator.next();
			Boolean overrideIfNull = overrideIterator.next();
			TypeConverter<?> typeConverter = typeConverters.next();

			field.setAccessible(true);

			String maybeNullValue = properties.getProperty(key);
			Object maybeNullConvertedValue = typeConverter.apply(maybeNullValue);

			Fields.safeSetFieldValue(field, object, maybeNullConvertedValue, overrideIfNull);
		}

		return object;
	}

	private List<TypeConverter<?>> extractTypeConverterList(T object, List<Field> propertyFields) {
		LinkedList<TypeConverter<?>> typeConverters = new LinkedList<TypeConverter<?>>();

		for (Field field : propertyFields) {
			boolean existConverter = field.isAnnotationPresent(PropertyNonStringTypeConverter.class);
			
			if(!existConverter){
				typeConverters.add(TypeConverters.DEFAULT_TYPE_CONVERTER);
				continue;
			}
			
			PropertyNonStringTypeConverter nonStringTypeConverter = field.getAnnotation(PropertyNonStringTypeConverter.class);
			Class<? extends TypeConverter<?>> typConverterClass = nonStringTypeConverter.type();
			try {
				TypeConverter<?> newInstance = typConverterClass.newInstance();
				typeConverters.add(newInstance);
			} catch (InstantiationException | IllegalAccessException e) {
				LOGGER.warning(e.getMessage());
				typeConverters.add(TypeConverters.DEFAULT_TYPE_CONVERTER);
			}
		}
		return typeConverters;
	}

	private List<Boolean> extractOverrideIfNullList(T object, List<Field> propertyFields) {
		boolean defaultForceOverride = PropertyOverrideIfNulls.extractDefaultForceOverrideOrFalse(object);
		
		List<Boolean> overrideList = PropertyOverrideIfNulls.extractOverrides(propertyFields, defaultForceOverride);
		return overrideList;
	}

	private List<Field> getAllPropertyFields(T object) {
		List<Field> allFields = Fields.getAllFields(object);

		List<Field> propertyFields = Fields.getAllFieldsWithAnnotation(allFields, Property.class);
		return propertyFields;
	}

	private List<Properties> extractAndInitPropertiesList(T object, List<Field> propertyFields) {
		PropertyResolverStrategy defaultResolverStrategy = PropertySuppliers.maybeNullPropertyResolverStrategyFromClass(object);

		List<PropertyResolverStrategy> propertyResolverStrategies = PropertySuppliers.extractPropertyResolverStrategiesOrDefault(
				propertyFields, defaultResolverStrategy);
		assert propertyFields.size() == propertyResolverStrategies.size();

		List<List<PropertySupplier>> propertySupplierLists = PropertyResolverStrategys
				.initPropertySupplierListOfLists(propertyResolverStrategies);
		assert propertyFields.size() == propertySupplierLists.size();

		List<Properties> propertiesList = PropertySuppliers.toPropertiesList(propertySupplierLists);
		return propertiesList;
	}
}
