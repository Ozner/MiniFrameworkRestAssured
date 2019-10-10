package com.empresa.framework;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;

import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseRestAssuredTest {
	
	private  RequestSpecification reqSpec;
	private  ResponseSpecification resSpec;
	private  String endpoint;
	
	
	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public void setReqSpec(RequestSpecification reqSpec) {
		this.reqSpec = reqSpec;
	}
		
	public RequestSpecification getReqSpec() {
		return reqSpec;
	}

	public void setResSpec(ResponseSpecification resSpec) {
		this.resSpec = resSpec;
	}
	
	public ResponseSpecification getResSpec() {
		return resSpec;
	}
	
	//constructor 
	public BaseRestAssuredTest(String endpoint) {
		this.reqSpec = this.initReqSpec();
		this.resSpec = this.initResSpec();
		this.endpoint = endpoint;
	}

	/**
	 * Load the base URI in to restassured's instance
	 * @param baseURI
	 */
	public RestAssured setBaseURI (String baseURI){
		System.out.println("LA URI BASE " + baseURI);
		RestAssured r = new RestAssured();
		r.baseURI = baseURI;
			
		return  r;
	}

	/**
	 * Reset the base URI (por si se necesita)
	 */
	public static void resetBaseURI (){
		RestAssured.baseURI = null;
	}
	
	/**
	 * Generate and return a base request specificaction (only JSON format content type)
	 * 
	 * @return reqSpec
	 */
	public RequestSpecification initReqSpec() {
		RequestSpecification reqSpec = new RequestSpecBuilder().setAccept(ContentType.JSON).build();
		return reqSpec;
	}
	
	/**
	 * Generate and return a base response specificaction (only JSON format content type)
	 * 
	 * @return reqSpec
	 */
	public ResponseSpecification initResSpec() {
		ResponseSpecification resSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
		return resSpec;
	}
	
	/**
	 * Add query params to the request spec passed as a parameter
	 * @param reqSpec
	 * @param Map<String, Object> queryParams
	 */
	public void  addMultipleQueryParams(Map<String,Object> queryParams) {
		this.reqSpec.queryParams(queryParams);
	}
	
	/**
	 * Add a query param to the request spec passed as a parameter
	 * @param reqSpec
	 * @param String key: the query param's key's name
	 * @param String value: the query param's value
	 */
	public static void  addQueryParam(RequestSpecification reqSpec, String key, String value) {
		reqSpec.queryParams(key, value);    
	}
	
	/**
	 * Add path params to the request spec passed as a parameter
	 * @param reqSpec
	 * @param Map<String, Object> pathParams
	 */
	public void  addMultiplePathParams(Map< String,Object> pathParams) {
		this.reqSpec.pathParams(pathParams);   
	}
	
	/**
	 * Add a path param to the request spec passed as a parameter
	 * @param reqSpec
	 * @param String key: the path param's key's name
	 * @param String value: the path param's value
	 */
	public void  addPathParam(String key, String value) {
		this.reqSpec.pathParam(key, value);   
	}
	
	/**
	 * Generic HTTPT request
	 * @param RequestSpecification reqSpec 
	 * @param ResponseSpecification resSpec
	 * @param String endpoint
	 * @return
	 */
	public Response getOperation(RequestSpecification reqSpec, ResponseSpecification resSpec, String endpoint) {
		Response response = 
				given()
				.spec(reqSpec)
				.log().all()
				.when()
				.get(endpoint)
				.then()
				.log().body()
				.spec(resSpec)
				.and()
				.extract()
				.response();

		return response;
	}
	
	
	public Response getOperation() {
		Response response = 
				given()
				.spec(this.getReqSpec())
				.log().all()
				.when()
				.get(this.endpoint)
				.then()
				.log().body()
				.spec(this.getResSpec())
				.and()
				.extract()
				.response();

		return response;
	}
	
	/**
	 * Generic HTTP POST request
	 * @param RequestSpecification reqSpec 
	 * @param ResponseSpecification resSpec
	 * @param String endpoint
	 * @return
	 */
	public Response postOperation(RequestSpecification reqSpec, ResponseSpecification resSpec, String endpoint) {
		Response response = 
				given()
				.spec(reqSpec)
				.log().all()
				.when()
				.post(endpoint)
				.then()
				.log().body()
				.spec(resSpec)
				.and()
				.extract()
				.response();

		return response;
	}

	/**
	 * Generic HTTP PUT request
	 * @param RequestSpecification reqSpec 
	 * @param ResponseSpecification resSpec
	 * @param String endpoint
	 * @return
	 */
	public Response putOperation(RequestSpecification reqSpec, ResponseSpecification resSpec, String endpoint) {
		Response response = 
				given()
				.spec(reqSpec)
				.log().all()
				.when()
				.put(endpoint)
				.then()
				.log().body()
				.spec(resSpec)
				.and()
				.extract()
				.response();

		return response;
	}

	/**
	 * Generic HTTP DELETE request
	 * @param RequestSpecification reqSpec 
	 * @param ResponseSpecification resSpec
	 * @param String endpoint
	 * @return
	 */
	public Response deleteOperation(RequestSpecification reqSpec, ResponseSpecification resSpec, String endpoint) {
		Response response = 
				given()
				.spec(reqSpec)
				.log().all()
				.when()
				.delete(endpoint)
				.then()
				.log().body()
				.spec(resSpec)
				.and()
				.extract()
				.response();

		return response;
	}
}
