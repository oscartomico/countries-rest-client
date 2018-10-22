package com.restclient.io;

/**
 * Implement A app type
 * @author otm
 *
 */
public class AppA implements App {
	private String fileName = "empresa1.txt";
	private String separator = "|";
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSeparator() {
		return separator;
	}
	public void setSeparator(String separator) {
		this.separator = separator;
	}
}
