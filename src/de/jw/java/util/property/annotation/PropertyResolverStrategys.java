package de.jw.java.util.property.annotation;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.jw.java.util.property.supplier.PropertySupplier;

public class PropertyResolverStrategys {

	public static List<List<PropertySupplier>> initPropertySupplierListOfLists(List<PropertyResolverStrategy> propertyResolverStrategies) {
		List<List<PropertySupplier>> propertySupplierLists = new LinkedList<List<PropertySupplier>>();
		
		for (PropertyResolverStrategy propertyResolverStrategy : propertyResolverStrategies) {
			List<PropertySupplier> propertySuppliers = PropertySuppliers.DEFAULT_PROPERTY_SUPPLIER_LIST;
	
			if (propertyResolverStrategy != null) {
				propertySuppliers = PropertyResolverStrategys.initPropertySupplierList(propertyResolverStrategy);
			}
			propertySupplierLists.add(propertySuppliers);
	
		}
		return propertySupplierLists;
	}

	public static List<PropertySupplier> initPropertySupplierList(PropertyResolverStrategy propertyResolverStrategy) {
		if (propertyResolverStrategy == null)
			return Collections.emptyList();
	
		LinkedList<PropertySupplier> propertySupplierList = new LinkedList<PropertySupplier>();
		
		List<de.jw.java.util.property.annotation.PropertySupplier> propertySupplierAnnotationList = Arrays
				.asList(propertyResolverStrategy.resolver());
		
		for (de.jw.java.util.property.annotation.PropertySupplier propertySupplier : propertySupplierAnnotationList) {
	
			Class<? extends PropertySupplier> annotationType = propertySupplier.type();
			String maybeNullConstructorArg = propertySupplier.constructorArg();
	
			PropertySupplier initPropertySupplier = PropertySuppliers.initPropertySupplier(annotationType, maybeNullConstructorArg);
	
			propertySupplierList.add(initPropertySupplier);
		}
		return propertySupplierList;
	}

}
