package com.empresa.test;

import static io.restassured.RestAssured.given; 
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;

import com.google.gson.JsonObject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ApiTest {
	
	private static RequestSpecification requestSpec;
	private static ResponseSpecification responseSpec;

	//@BeforeAll
	public static void createRequestSpecification() {
		//requestSpec es un objeto del tipo RequestSpecification y sirve para setear configuraciones previas
		// que se necesitan en las peticiones. En este caso se setea la URI base que sera la misma para todos los test.
		requestSpec = new RequestSpecBuilder().setBaseUri("https://reqres.in").build();
		
		//responseSpec es un objeto del tipo ResponseSpecification y sirve para setear assertions posibles de las respuestas,
		//y cualquier cosa que corresponda con la respuesta como puede ser extraer algun valor especifico del json obtenido. 
		// En este caso se setea un assert expectStatusCode(200) y expectContentType(ContentType.JSON)
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	}
	
	/**
	 * Este primer ejemplo es lo mas basico
	 * Peticio GET a https://reqres.in/api/users?page=2&per_page=2
	 * 
	 * TEST
	 * 1- que el codigo de respuesta sea 200  - .statusCode(200)
	 * 2- que el contenido de la respuesta sea devuelto en formato json - .contentType("application/json")
	 * 3- que el primer usurio de la lista de usuarios (data) tenga un email que sea igual a george.bluth@reqres.in
	 */
	
	//@Test
	public static void getUsersExample1() {
			given()
				.queryParam("page", 1)
				.queryParam("per_page", 2)
			.when()
				.get("https://reqres.in/api/users")
			.then()
				.statusCode(200)
				.contentType("application/json")
				.assertThat().body("data[0].'email'", equalTo("george.bluth@reqres.in"));
			   		
	}
	
	
	/**
	 * Este segundo ejemplo hace uso de los spec seteados en el @BeforesClass
	 * Tambien se captura toda la respuesta en un objeto del tipo Response.
	 * El assertion se hace prosesando el response obtenido.
	 * 
	 * Peticio GET a https://reqres.in/api/users?page=2&per_page=2
	 * 
	 * TEST
	 * 1- que el codigo de respuesta sea 200  - .statusCode(200) (esto viene en el responseSpec)
	 * 2- que el contenido de la respuesta sea devuelto en formato json - .contentType("application/json") (esto viene en el responseSpec)
	 * 3- Validar todos los datos del primer usuario de la lista
	 */
	//@Test
	public static void getUsersExample2() {
			Response response = given()
				.queryParam("page", 1)
				.queryParam("per_page", 2)
				.spec(requestSpec)
			.when()
				.get("https://reqres.in/api/users")
			.then()
				.spec(responseSpec)
				.extract().response();
			
			
			//Procesando el response para general el assertion
			
			JsonPath jsonObtenido = response.jsonPath(); //obtenemos el json y creamos un JsonPath
			
			//el jsonPath permite que se pueda deserealizar y obtener cualquier dato que contenga.
			//como ejemplo vamos a obtener los datos del primer usuario y validar cada uno de ellos.
			
			//esto son asserts de testNG
			assertEquals(jsonObtenido.get("data[0].email"), "george.bluth@reqres.in");
			assertEquals(jsonObtenido.get("data[0].first_name"), "George");
			assertEquals(jsonObtenido.get("data[0].last_name"), "Bluth");
			assertEquals(jsonObtenido.get("data[0].avatar"), "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg");
			
		 	   		
	}
	
	//contrccuon de un data provider
	/*@DataProvider(name = "nuevosAbstracteros")
	public String[][] createuserTestData() {
	    return new String[][] {
	            {"Elon Musk", "aprendiz"},
	            {"Bill Gate", "aprendiz"},
	    };
	}*/
	
	/**
	 * Este ultimo ejemplo es un POST
	 * la idea es agregar dos usuarios nuevos y para eso se utiliza el data provider llamado nuevosAsbtracteros
	 * Los parametros name y job seran alimentados entonces por el data provider.
	 * @param name
	 * @param job
	 */
	/*@Test(dataProvider = "nuevosAbstracteros")
	public void postUsers(String name, String job) {
	        
		//la doc de la api dice que para agregar un nuevo usuario hay que pasar en el body de la peticion
		//un json con este formato
		/*{
		 * name: "nuevo nombre",
		 * job: "rol que ocupa"
		 *}
		 */
		//contruimos ese body usando un objeto de Gson (ver dependencia gson en el pom)... 
		/*JsonObject jsonBody = new JsonObject();
		jsonBody.addProperty("name", name);
		jsonBody.addProperty("job", job);
			Response response = given()
					.spec(requestSpec)
				.body(jsonBody.toString()) //se pasa el body construido convertido en string
			.when()
				.post("/api/users")
			.then()
				.statusCode(201) // se espera un 201 - create
				.contentType("application/json")
				.extract().response();
		
			System.out.println(response.getBody().asString()); //imprimimos la respuesta que devuelve la api cada vez que se ejecuta el post
	}*/
	
}