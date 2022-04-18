package files;

import io.restassured.path.json.JsonPath;

public class convertingBacktoJson {
	public static JsonPath convertingToJson(String responseString) {
		JsonPath js=new JsonPath(responseString);
		return js;
	}
}
