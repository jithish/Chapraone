package connection;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorObject {

	String errorMessage = "";
	int errorID;

	public ErrorObject(String errorMessage, int errorID) {
		super();
		this.errorMessage = errorMessage;
		this.errorID = errorID;
	}

	public ErrorObject(JSONObject jsonObject) {
		JSONObject jsonError;
		try {
			jsonError = jsonObject.getJSONObject("error");

			this.errorID = jsonError.getInt("errorId");
			this.errorMessage = jsonError.getString("errorMessage");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean isError() {
		if (errorID == 0)
			return false;
		else
			return true;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public int getErrorID() {
		return this.errorID;
	}

	public static boolean isExpiredError(int errorID) {
		if ((errorID == 1025 || errorID == 1026 || errorID == 1065 || errorID == 1005)) {

			return true;
		} else
			return false;
	}

	public boolean isExpiredError() {
		

			return isExpiredError(errorID);
		
	}
}
