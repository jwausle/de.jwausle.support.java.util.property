package de.jw.java.util.property.internal;

import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class UtilPropertyActivator implements BundleActivator {
	private final Logger log = Logger.getLogger(UtilPropertyActivator.class.getName());

	@Override
	public void start(BundleContext context) throws Exception {
		log.info("Starting " + context.getBundle().getSymbolicName() + " ... ");
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		log.info("Stopping " + context.getBundle().getSymbolicName() + " ... ");
	}

}
