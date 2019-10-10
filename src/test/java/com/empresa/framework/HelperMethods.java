package com.empresa.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class HelperMethods {
	
	/**
	 * Process the file config.properties and return  propertie object
	 * @return propertie
	 */
	public static Properties loadConfigPropertiesFile() {
		Properties prop = new Properties();
		System.out.println("Se cargan las configuraciones");
		try {
			FileInputStream input = new FileInputStream(new File("src/test/resources/config.properties"));
			prop.load(new InputStreamReader(input, Charset.forName("UTF-8")));
		} catch(IOException e) {
			System.out.println(e.toString());
		}

		return prop;	
	}
}
