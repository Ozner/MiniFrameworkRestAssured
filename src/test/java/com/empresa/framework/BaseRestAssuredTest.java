package com.empresa.framework;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;

import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
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
		this.endpoint = endpoint;
	}

	/**
	 * Load the base URI in to restassured's instance
	 * @param baseURI
	 */
	public void setBaseURI (String baseURI){
		RestAssured.baseURI = baseURI;
	}

	/**
	 * Reset the base URI (por si se necesita)
	 */
	public void resetBaseURI (){
		RestAssured.baseURI = null;
	}
	
	/**
	 * Load the basePath URI in to restassured's instance
	 * @param baseURI
	 */
	public void setBasePath (String basePath){
		RestAssured.basePath = basePath;
	}
	
	
	/**
	 * Reset the basePath (por si se necesita)
	 */
	public void resetBasePath (){
		RestAssured.basePath = null;
	}
	
	/**
	 * Generate and return a base request specification (only JSON format content type)
	 * 
	 * @return reqSpec
	 */
	public RequestSpecification initReqSpec() {
		RequestSpecification reqSpec = new RequestSpecBuilder().setAccept(ContentType.JSON).build();	
		this.setReqSpec(reqSpec);
		return reqSpec;
	}
	
	/**
	 * Generate and return a base response specification (only JSON format content type)
	 * 
	 * @return reqSpec
	 */
	public ResponseSpecification initResSpec() {
		ResponseSpecification resSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
		this.setResSpec(resSpec);
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
	 * @param String key: the query param's key's name
	 * @param String value: the query param's value
	 */
	public void  addQueryParam(String key, String value) {
		this.reqSpec.queryParams(key, value);    
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
	 * Generic HTTP GET request
	*/
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
	*/
	public Response postOperation() {
		Response response = 
				given()
				.spec(this.getReqSpec())
				.log().body()
				.when()
				.post(this.endpoint)
				.then()
				.log().body()
				.spec(this.getResSpec())
				.and()
				.extract()
				.response();

		return response;
	}
	
	
	/**
	 * Generic HTTP PUT request
	*/
	public Response putOperation() {
		Response response = 
				given()
				.spec(this.getReqSpec())
				.log().all()
				.when()
				.put(this.endpoint)
				.then()
				.log().body()
				.spec(this.getResSpec())
				.and()
				.extract()
				.response();

		return response;
	}
	
	
	
	/**
	 * Generic HTTP DELETE request
	*/
	public Response delteOperation() {
		Response response = 
				given()
				.spec(this.getReqSpec())
				.log().all()
				.when()
				.delete(this.endpoint)
				.then()
				.log().body()
				.spec(this.getResSpec())
				.and()
				.extract()
				.response();

		return response;
	}
	
	/**
	 * Return a jsonPath object from Rest Assured response passed as a parameter
	 * @param r
	 * @return Response r
	 */
	public JsonPath getJsonPathFromResponse(Response r) {
		return r.jsonPath();
	}
	
	/**
	 * Return a String pathElment's value obtained from JsonPath 
	 * @param r
	 * @param pathElement
	 * @return String 
	 */
	public String getStringfromJsonPath(Response r, String pathElement) {
		return getJsonPathFromResponse(r).getString(pathElement);
	}
	
	
	/**
	 * Return a List<String> pathElment's value obtained from JsonPath 
	 * @param r
	 * @param pathElement
	 * @return
	 */
	public List<String> getListfromJsonPath(Response r, List<String> pathElement) {
		//TODO
		return null;
	}
}
