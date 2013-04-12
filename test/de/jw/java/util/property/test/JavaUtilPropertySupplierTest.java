package de.jw.java.util.property.test;

import java.util.LinkedList;
import java.util.List;

import de.jw.java.util.property.annotation.Property;
import de.jw.java.util.property.annotation.PropertyFieldInitializer;
import de.jw.java.util.property.annotation.PropertyNonStringTypeConverter;
import de.jw.java.util.property.annotation.PropertyOverrideIfNull;
import de.jw.java.util.property.annotation.PropertyResolverStrategy;
import de.jw.java.util.property.annotation.PropertySupplier;
import de.jw.java.util.property.supplier.ClasspathPropertySupplier;
import de.jw.java.util.property.supplier.EnvironmentPropertySupplier;
import de.jw.java.util.property.supplier.SystemPropertySupplier;
import de.jw.java.util.property.supplier.UrlPropertiesSupplier;
import de.jw.java.util.property.typeconverter.ToListByCommaTypeConverter;

public class JavaUtilPropertySupplierTest {

	@PropertyResolverStrategy(resolver = //
		{ /*  */@PropertySupplier(type = SystemPropertySupplier.class),//
			@PropertySupplier(type = EnvironmentPropertySupplier.class),//
			@PropertySupplier(type = UrlPropertiesSupplier.class, constructorArg = "file://${user.dir}/resources/path/to/file.properties"), //
			@PropertySupplier(type = ClasspathPropertySupplier.class, constructorArg = "path/to/classpathfile.properties") //
		})
	@PropertyOverrideIfNull(force = true)
	public static class ClassWithPropertyFields {
		@Property(key = "property.key.1")
		@PropertyOverrideIfNull(force = true)
		private String propertyValue = "default value";

		@Property(key = "property.key.2")
//		@PropertyOverrideIfNull(force = false)
		private String propertyValue2 = "default value";

		@Property(key = "property.key.3")
		@PropertyOverrideIfNull(force = false)
		private String propertyValue3 = "default value";
		
		@Property(key = "property.key.4")
		@PropertyNonStringTypeConverter(type=ToListByCommaTypeConverter.class)
		private List<String> propertyValue4 = new LinkedList<String>();

	}

	public static void main(String[] args) {
		System.setProperty("property.key.4", "test");

		ClassWithPropertyFields instance = PropertyFieldInitializer.create(new ClassWithPropertyFields()).init();
		System.out.println(instance.propertyValue);
		System.out.println(instance.propertyValue2);
		System.out.println(instance.propertyValue3);
		System.out.println(instance.propertyValue4);
	}

}
