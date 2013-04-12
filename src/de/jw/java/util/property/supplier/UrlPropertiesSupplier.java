package de.jw.java.util.property.supplier;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

import de.jw.java.util.property.internal.StringSubsituder;

public class UrlPropertiesSupplier implements PropertySupplier{
	private static final Logger LOGGER = Logger.getLogger(UrlPropertiesSupplier.class.getName());
	private String url;

	public UrlPropertiesSupplier(String url) {
		this.url = url;
	}

	@Override
	public Properties get() {
		Properties properties = new Properties();
		
		try {
			String substitudeUrl = StringSubsituder.subsitude(System.getProperties(),url); 
			InputStream inStream = new URL(substitudeUrl).openStream();
			properties.load(inStream);
		} catch (MalformedURLException e) {
			LOGGER.warning("Error while url property loading: " + e.getMessage() + ". Will return empty-properties.");
			return properties;
		} catch (IOException e) {
			LOGGER.warning("Error while url property loading: " + e.getMessage() + ". Will return empty-properties.");
			return properties;
		}
		return properties;
	}

}
