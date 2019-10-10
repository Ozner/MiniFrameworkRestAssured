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

public class ListadoUsuariosTest {
	
	static BaseRestAssuredTest baseTest;
	static Properties obj_propertie = new Properties();
	
	@BeforeAll
	public static void setup() {	
		obj_propertie = HelperMethods.loadConfigPropertiesFile();
		baseTest = new BaseRestAssuredTest(obj_propertie.getProperty("GET_LIST_USERS"));  
		baseTest.getReqSpec().baseUri(obj_propertie.getProperty("BASE_HOST"));
		baseTest.getReqSpec().basePath(obj_propertie.getProperty("BASE_PATH"));
	}	
		
	@Test
	public void getUsersList() {
		//set de req spec
		Map<String, Object> qParams = new HashMap< String,Object>();
		qParams.put("page", new Integer(2));
		qParams.put("per_page", new Integer(3));
		
		baseTest.addMultipleQueryParams(qParams);
		//set response spec
		baseTest.getResSpec().body("data.id", contains(4, 5, 6));
		baseTest.getResSpec().body("page", is(2));
		baseTest.getResSpec().body("per_page", is(3));
		
		//ejecutar operacion
		baseTest.getOperation();
		
		/*given()
			.log().all()
			.spec(requestSpec)
		.when()
			.get(EndPoints.GET_LIST_USERS)
		.then()
			.log().body()
			.body("data.id",  contains(4, 5, 6))
			.body("page", is(2))
			.body("per_page", is(3));
			*/
	}	
	
	/**	
	 * Se puede devolver todo el response de la peticion y despues convertilo en json para hacer los assertion
	 */
	@Test
	public void getUsersListWithResponse() {
		requestSpec = new RequestSpecBuilder().build();
		requestSpec.queryParam("page", 2); 
		requestSpec.queryParam("per_page");
		
		Response response = 
		
		given()
			.log().all()
			.spec(requestSpec)
		.when()
			.get(EndPoints.GET_LIST_USERS)
		.then()
			.log().body()
			.extract().response();
		
		System.out.println(response.jsonPath().get("data.id"));
		assertEquals(response.jsonPath().get("data.id"),  contains(4, 5, 6));
	}
	

	
	@AfterAll
	public static void teardown() {
		System.out.println("BORRAR BASE DE DATOS");
	}
}
