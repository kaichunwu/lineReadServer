package com.example.demo.domain;

/**
 * This class is used to together return content and status code.
 * For success return lines:
 * content: return line; status: 200
 * For fail return:
 * content: fail reason; status: 413
 */
public class StatusString {

	private String content;
	private int status;
	
	public String getContent() {
		return content;
	}

	public int getStatus() {
		return status;
	}

	public StatusString(String content, int status) {
		super();
		this.content = content;
		this.status = status;
	}
	
}
