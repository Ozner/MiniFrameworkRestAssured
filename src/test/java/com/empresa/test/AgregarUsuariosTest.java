package com.empresa.test;

public class AgregarUsuariosTest {
	
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
