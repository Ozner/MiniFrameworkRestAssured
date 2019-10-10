package com.empresa.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.contains;
import org.junit.jupiter.api.Test;

import com.empresa.framework.BaseRestAssuredTest;
import com.empresa.framework.HelperMethods;
import io.restassured.response.Response;

public class ObtenerUsuarioTest {
	
	static BaseRestAssuredTest baseTest;
	static Properties obj_propertie = new Properties();
	
	@BeforeAll
	public static void setup() {	
		obj_propertie = HelperMethods.loadConfigPropertiesFile();
		baseTest = new BaseRestAssuredTest(obj_propertie.getProperty("GET_USER_BY_ID"));  
		baseTest.setBaseURI(obj_propertie.getProperty("BASE_HOST"));
		baseTest.setBasePath(obj_propertie.getProperty("BASE_PATH"));
	}
	
	@Test
	public void getUserById() {
		//1- inicializar request spec
		baseTest.initReqSpec();
		//2- seteo del request spec
		baseTest.addPathParam("userId", "2");	
		//3- inicializar response spec
		baseTest.initResSpec();
		//4- seteo del response spec, o sea, que quiero probar
		baseTest.getResSpec().statusCode(200);
		baseTest.getResSpec().body("data.id", is(2));
		//5-ejecucion de la operacion
		Response response = baseTest.getOperation();
	}		
	
	@AfterAll
	public static void teardown() {
		baseTest.resetBaseURI();
		baseTest.resetBasePath();
		System.out.println("BORRAR BASE DE DATOS Y TODO LO QUE SE QUIERA RESETEAR");
	}
}
