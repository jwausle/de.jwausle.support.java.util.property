package de.jw.java.util.property.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class StringSubsituder {
	/** {@link #VARIABLE_PREFIX_PATTERN} = {@value #VARIABLE_PREFIX_PATTERN} */
	public static final String VARIABLE_PREFIX_PATTERN = "\\$\\{";
	private static final char VARIBLE_SUFFIX = '}';
	/** {@link #VARIABLE_SUFFIX_PATTERN} = {@value #VARIABLE_SUFFIX_PATTERN} */
	public static final String VARIABLE_SUFFIX_PATTERN = "\\" + VARIBLE_SUFFIX;

	/**
	 * Extract all ${variables}. And try to replace valid property-values. <br />
	 * - property.contains(variable.key) and value == null trigger value = TRUE <br />
	 * - others replaceAll ${variable.key} -> variable.value <br />
	 * - others ignore <br />
	 * <br />
	 * So variable.key without value in properties still exist return string.
	 * 
	 * @param properties
	 * @param string
	 * @return string where all existing variables is replaced.
	 */
	public static String subsitude(Properties properties, String string) {
		if (string == null)
			return null;

		if (properties == null || properties.isEmpty())
			return string;

		List<String> variableList = extractVariables(string);
		Map<String, String> variableMap = toVariablesWithValueMap(properties, variableList);

		String subsitudeUrl = string;
		Set<String> keySet = variableMap.keySet();
		for (String key : keySet) {
			String value = variableMap.get(key);

			if (value == null)
				continue;

			subsitudeUrl = subsitudeUrl.replaceAll(asVariable(key), value);
		}
		return subsitudeUrl;
	}

	private static String asVariable(String key) {
		return VARIABLE_PREFIX_PATTERN + key + VARIABLE_SUFFIX_PATTERN;
	}

	private static Map<String, String> toVariablesWithValueMap(Properties properties, List<String> variableList) {
		Map<String, String> variableMap = new LinkedHashMap<String, String>();
		for (String variable : variableList) {
			boolean containsKey = properties.containsKey(variable);
			String object = properties.getProperty(variable);

			if (containsKey && object == null) {
				variableMap.put(variable, Boolean.TRUE.toString());
				continue;
			}

			if (containsKey) {
				variableMap.put(variable, object);
				continue;
			}
			// ignore not existing keys
		}
		return variableMap;
	}

	private static List<String> extractVariables(String url) {
		if (url == null)
			return Collections.emptyList();

		String splitConstant = VARIABLE_PREFIX_PATTERN;

		List<String> variables = new LinkedList<String>();
		List<String> split = Arrays.asList(url.split(splitConstant));
		for (String string : split) {
			int indexOf = string.indexOf(VARIBLE_SUFFIX);
			if (indexOf == -1) {
				continue;
			}
			String substring = string.substring(0, indexOf);
			variables.add(substring);
		}

		return variables;
	}

}
