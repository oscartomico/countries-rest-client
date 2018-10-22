package com.restclient.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * Write and Read operations
 * @author otm
 *
 */

@Component
public class ReadWrite {
	private App app;
	
	/**
	 * This method read file content
	 * @return the file content
	 * @throws IOException
	 */
	public String readFile() throws IOException {
		String fileContent = "";
		String line = "";
		FileReader f = new FileReader(app.getFileName());
		BufferedReader b = new BufferedReader(f);
		while ((line = b.readLine()) != null) {
			fileContent += line + "\n";
		}
		b.close();
		return fileContent;
	}
	
	public String readFileWithoutSeparator(String text) {
		return text.replace(app.getSeparator(), "|");
	}
	
	/**
	 * Export any text to a file
	 * @param text to export
	 * @return true
	 * @throws IOException
	 */
	public String exportToFile(String text) throws IOException {
		FileWriter file = null;
		text = prepareArrayJSONDataToWrite(text);
		file = new FileWriter(app.getFileName());
		file.write(text);
		file.close();
		return "{\"result\": true}";
	}

	// This method prepare a string json array in a text to save
	private String prepareArrayJSONDataToWrite(String text) {
		String textToSave = "";
		String json = "{\"countries\":" + text + "}";
		JSONObject jsonObject = new JSONObject(json);
		JSONArray jsonArray = jsonObject.getJSONArray("countries");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonCountry = new JSONObject(jsonArray.get(i).toString());
			textToSave += jsonCountry.getLong("id") + app.getSeparator() + jsonCountry.getString("name")
					+ app.getSeparator() + jsonCountry.getInt("population") + "\n";
		}
		return textToSave;
	}

	// This method prepare a string json array in a text to save
	private String prepareCountryJSONDataToWrite(String textJSON) {
		JSONObject jsonCountry = new JSONObject(textJSON);
		return jsonCountry.getLong("id") + app.getSeparator() + jsonCountry.getString("name") + app.getSeparator()
				+ jsonCountry.getInt("population");
	}
	
	/**
	 * Asign the app type
	 * @param appType to procesing files
	 * @return true, false
	 */
	public boolean setAppType(String appType) {
		switch (appType) {
		case "appA":
			this.app = new AppA();
			return true;
		case "appB":
			this.app = new AppB();
			return true;
		}
		return false;
	}
}
