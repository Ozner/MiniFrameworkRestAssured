package com.empresa.test;

import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.empresa.framework.BaseRestAssuredTest;
import com.empresa.framework.HelperMethods;
import com.google.gson.JsonObject;

public class AgregarUsuariosTest {

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
	public void postUsers() {
		//1- inicilizar un request specification
		baseTest.initReqSpec();
		//2- setear el request specification 
		//contruimos ese body usando un objeto de Gson (ver dependencia gson en el pom)... 
		JsonObject jsonBody = new JsonObject();
		jsonBody.addProperty("name", "NuevoNombre");
		jsonBody.addProperty("job", "NuevoJob");
		baseTest.getReqSpec().body(jsonBody.toString());
		//3- inicializar el response specification
		baseTest.initResSpec();
		//4- setear el res spec
		baseTest.getResSpec().statusCode(201);
		//5- ejecutar la operacion
		baseTest.postOperation();
	}
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
	public void postMultipleUsersFromDataProvider(String name, String job) {
		//1- inicilizar un request specification
		baseTest.initReqSpec();
		//2- setear el request specification 
		//contruimos ese body usando un objeto de Gson (ver dependencia gson en el pom)... 
		JsonObject jsonBody = new JsonObject();
		jsonBody.addProperty("name", name);
		jsonBody.addProperty("job", job);
		baseTest.getReqSpec().body(jsonBody.toString());
		//3- inicializar el response specification
		baseTest.initResSpec();
		//4- setear el res spec
		baseTest.getResSpec().statusCode(201);
		//5- ejecutar la operacion
		baseTest.postOperation();
	}
	
	@AfterAll
	public static void teardown() {
		baseTest.resetBaseURI();
		baseTest.resetBasePath();
		System.out.println("BORRAR BASE DE DATOS Y TODO LO QUE SE QUIERA RESETEAR");
	}
}
