package de.jw.java.util.property.supplier;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

public class ClasspathPropertySupplier implements PropertySupplier{
	private static final Logger LOGGER = Logger.getLogger(ClasspathPropertySupplier.class.getName());

	private final String fileName;

	private final ClassLoader classLoader;

	public ClasspathPropertySupplier(String fileName) {
		this(ClasspathPropertySupplier.class.getClassLoader(),fileName);
	}

	public ClasspathPropertySupplier(ClassLoader classLoader, String fileName2) {
		this.classLoader = classLoader;
		this.fileName = fileName2;
	}

	@Override
	public Properties get() {
		try {
			URL resource = classLoader.getResource(fileName);
			InputStream inStream = resource.openStream();
			
			Properties properties = new Properties();
			
			properties.load(inStream);
			return properties;
		} catch (IOException e) {
			LOGGER.warning("Error while classpath property loading: " + e.getMessage() + ". Will return empty-properties. " + e.getMessage());
			return new Properties();
		}
	}

}
