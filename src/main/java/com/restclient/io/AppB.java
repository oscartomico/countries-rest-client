package com.restclient.io;

/**
 * Implement B app type
 * @author otm
 *
 */
public class AppB implements App{
	private String fileName = "empresa2.txt";
	private String separator = "-";
	
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
