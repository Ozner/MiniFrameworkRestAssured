package com.empresa.test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.empresa.framework.*;

public class GoogleApiTest {
	
	static BaseRestAssuredTest baseTest;
	static Properties obj_propertie = new Properties();
	
	@BeforeAll
	public static void setup() {
		obj_propertie = HelperMethods.loadConfigPropertiesFile();  
		baseTest = new BaseRestAssuredTest(obj_propertie.getProperty("GET_BOOKS_BY_QUERY_PARAM"));
		baseTest.setBaseURI(obj_propertie.getProperty("BASE_HOST_GOOGLE_API"));
		baseTest.setBasePath("");
	}
	
	
	@Test
	public void getBooksByQueryParam() {
		//1- inicializar un request specification
		baseTest.initReqSpec();
		//2- set de req spec
		baseTest.addQueryParam("q", "tolkien");
		//3- inicializar un response specification
		baseTest.initResSpec();
		//4- set response spec
		baseTest.getResSpec().statusCode(200);
		//5- ejecutar operacion 
		baseTest.getOperation();
	}
}
