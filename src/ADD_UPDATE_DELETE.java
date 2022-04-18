import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import org.testng.Assert;

import files.convertingBacktoJson;
import files.payLoad;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ADD_UPDATE_DELETE {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ADD PLACE
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String givenresponse=
		given().log().all().queryParam("key","qaclick123" ).header("Content-Type","application/json")
		.body(payLoad.returnningJsonOfGivenRequest()).when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("Server",equalTo("Apache/2.4.18 (Ubuntu)")).extract().response().asString();

		System.out.println(givenresponse);
		JsonPath js=convertingBacktoJson.convertingToJson(givenresponse);
		String place_Id=js.getString("place_id");
		System.out.println(place_Id);
		//UPDATE PLACE
		String newAdress="70 Summer walk, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body("{\r\n"
				+ "\"place_id\":\""+place_Id+"\",\r\n"
				+ "\"address\":\""+newAdress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").when().put("/maps/api/place/update/json").then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		//GET PLACE
		String getResponse=
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_Id).when().get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js11=convertingBacktoJson.convertingToJson(getResponse);
		String getAdress=js11.getString("address");
		System.out.println(getAdress);
		Assert.assertEquals(getAdress,newAdress );
		
		//example on understanding response and request USING UPDATE PLACE
		
		RequestSpecification requestUrl=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		RequestSpecification finalRequest=given().log().all().spec(requestUrl).body("{\r\n"
				+ "\"place_id\":\""+place_Id+"\",\r\n"
				+ "\"address\":\""+newAdress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "");
		ResponseSpecification response=new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(200).build();
		Response Finalresponse=finalRequest.when().put("/maps/api/place/update/json").then().spec(response).extract().response();
		
	}


}
