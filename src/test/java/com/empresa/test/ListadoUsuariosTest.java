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

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ListadoUsuariosTest {
	
	static BaseRestAssuredTest baseTest;
	static Properties obj_propertie = new Properties();
	
	@BeforeAll
	public static void setup() {	
		obj_propertie = HelperMethods.loadConfigPropertiesFile();  
		baseTest = new BaseRestAssuredTest(obj_propertie.getProperty("GET_LIST_USERS"));
		baseTest.setBaseURI(obj_propertie.getProperty("BASE_HOST"));
		baseTest.setBasePath(obj_propertie.getProperty("BASE_PATH"));
	}	
		
	@Test
	public void getUsersList() {
		//1- inicializar un request specification
		baseTest.initReqSpec();
		
		//2- set de req spec
		Map<String, Object> qParams = new HashMap< String,Object>();
		qParams.put("page", new Integer(2));
		qParams.put("per_page", new Integer(3));
		
		baseTest.addMultipleQueryParams(qParams);
		
		//3- inicializar un response specification
		baseTest.initResSpec();
		//4- set response spec
		baseTest.getResSpec().body("data.id", contains(4, 5, 6));
		baseTest.getResSpec().body("page", is(2));
		baseTest.getResSpec().body("per_page", is(3));
		
		//5- ejecutar operacion 
		baseTest.getOperation();
	}	
	
	/**	
	 * Se puede devolver todo el response de la peticion y trabajar los assertion de JUnit o TestNG por separado
	 */
	@Test
	public void getUsersListWithResponse() {
		//1- inicializar un request specification
		baseTest.initReqSpec();
		
		//2- set de req spec
		Map<String, Object> qParams = new HashMap< String,Object>();
		qParams.put("page", new Integer(2));
		qParams.put("per_page", new Integer(6));
		baseTest.addMultipleQueryParams(qParams);
		
		//3- inicializar un response specification
		baseTest.initResSpec();
		//4- set response spec
		baseTest.getResSpec().body("data.id", contains(7, 8, 9, 10, 11, 12));
		baseTest.getResSpec().body("page", is(2));
		baseTest.getResSpec().body("per_page", is(6));
		
		//5- ejecutar operacion 
		Response response = baseTest.getOperation();
		
		assertEquals(baseTest.getStringfromJsonPath(response, "page"), "2");
	}
	

	
	@AfterAll
	public static void teardown() {
		baseTest.resetBaseURI();
		baseTest.resetBasePath();
		System.out.println("BORRAR BASE DE DATOS Y TODO LO QUE SE QUIERA RESETEAR");
	}
}
