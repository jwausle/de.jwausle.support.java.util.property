package de.jw.java.util.property.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import de.jw.java.util.property.supplier.EnvironmentPropertySupplier;
import de.jw.java.util.property.supplier.PropertySupplier;
import de.jw.java.util.property.supplier.SystemPropertySupplier;

public class PropertySuppliers {

	public static PropertySupplier initPropertySupplier(Class<? extends PropertySupplier> propertySupplierClass,
			String maybeNullConstructorArg) {
		if (propertySupplierClass == null)
			return null;

		if (maybeNullConstructorArg == null
				|| de.jw.java.util.property.annotation.PropertySupplier.DEFAULT_CONSTRUCTOR_ARG.equals(maybeNullConstructorArg))
			try {
				PropertySupplier newInstance = propertySupplierClass.newInstance();
				return newInstance;
			} catch (InstantiationException | IllegalAccessException e1) {
				return null;
			}

		try {
			Constructor<? extends PropertySupplier> propertySupplierConstructor = propertySupplierClass
					.getConstructor(new Class[] { String.class });

			return propertySupplierConstructor.newInstance(maybeNullConstructorArg);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			return null;
		}
	}

	public static List<Properties> toPropertiesList(List<List<PropertySupplier>> propertySupplierLists) {
		List<Properties> propertiesList = new LinkedList<Properties>();
		for (List<PropertySupplier> propertySuppliers : propertySupplierLists) {

			Collections.reverse(propertySuppliers);

			Properties properties = PropertySuppliers.toProperties(propertySuppliers);

			propertiesList.add(properties);
		}
		return propertiesList;
	}

	static Properties toProperties(List<PropertySupplier> propertySuppliers) {
		Properties properties = new Properties();
		for (PropertySupplier propertySupplier : propertySuppliers) {

			Properties suppliedProperties = propertySupplier.get();

			if (suppliedProperties != null)
				properties.putAll(suppliedProperties);
		}
		return properties;
	}

	static final List<PropertySupplier> DEFAULT_PROPERTY_SUPPLIER_LIST = Arrays.asList(new SystemPropertySupplier(),
			new EnvironmentPropertySupplier());

	public static PropertyResolverStrategy maybeNullPropertyResolverStrategyFromClass(Object object) {
		PropertyResolverStrategy defaultResolverStrategy = null;
		boolean existClassPropertyResolverStrategy = object.getClass().isAnnotationPresent(
				PropertyResolverStrategy.class);
		if (existClassPropertyResolverStrategy)
			defaultResolverStrategy = object.getClass().getAnnotation(PropertyResolverStrategy.class);
		return defaultResolverStrategy;
	}

	public static List<PropertyResolverStrategy> extractPropertyResolverStrategiesOrDefault(List<Field> propertyFields,
			PropertyResolverStrategy defaultResolverStrategy) {
		List<PropertyResolverStrategy> propertyResolverStrategies = new LinkedList<PropertyResolverStrategy>();
		
		for (Field propertyField : propertyFields) {
			boolean isPropertyResolverStrategy = propertyField.isAnnotationPresent(PropertyResolverStrategy.class);
			if (isPropertyResolverStrategy) {
				PropertyResolverStrategy p = propertyField.getAnnotation(PropertyResolverStrategy.class);
				propertyResolverStrategies.add(p);
			} else {
				propertyResolverStrategies.add(defaultResolverStrategy);
			}
		}
		return propertyResolverStrategies;
	}

}
