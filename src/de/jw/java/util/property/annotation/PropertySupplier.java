package de.jw.java.util.property.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertySupplier {
	public static final String DEFAULT_CONSTRUCTOR_ARG = "";
	Class<? extends  de.jw.java.util.property.supplier.PropertySupplier> type();
	String constructorArg() default DEFAULT_CONSTRUCTOR_ARG;
}
