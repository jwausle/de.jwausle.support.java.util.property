package de.jw.java.util.property.supplier;

import java.util.Properties;

public class SystemPropertySupplier implements PropertySupplier{

	@Override
	public Properties get() {
		return System.getProperties();
	}

}
