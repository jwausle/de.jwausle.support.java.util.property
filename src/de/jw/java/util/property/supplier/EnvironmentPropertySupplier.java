package de.jw.java.util.property.supplier;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class EnvironmentPropertySupplier implements PropertySupplier{

	@Override
	public Properties get() {
		Properties properties = new Properties();
		
		Map<String, String> getenv = System.getenv();
		Set<String> keySet = getenv.keySet();
		for (String key : keySet) {
			String value = getenv.get(key);
			if(value == null)
				value = Boolean.TRUE.toString();
			properties.put(key, value);
		}
		return properties;
	}

}
