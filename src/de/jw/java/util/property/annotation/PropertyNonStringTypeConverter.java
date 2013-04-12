package de.jw.java.util.property.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.jw.java.util.property.typeconverter.DoNothingTypeConverter;
import de.jw.java.util.property.typeconverter.TypeConverter;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface  PropertyNonStringTypeConverter {
	Class<? extends TypeConverter<?>> type() default DoNothingTypeConverter.class;
}
