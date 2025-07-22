package org.se.rub.github.monitor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ConfigurationManager {

	private static final Path location = Paths.get("config.json");

	public static int getFrequency() {
		return 60;
	}

	private static Map<String, String> loadConfig() {
		throw new UnsupportedOperationException();
	}

	public static class Configuration {
		int frequency;
	}
}
