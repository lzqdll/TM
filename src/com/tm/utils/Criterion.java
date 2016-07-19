package com.tm.utils;

public class Criterion {
	private String field;
	private String op;
	private String data;

	public Criterion(String field, String op, String data) {
		this.setField(field);
		this.setOp(op);
		this.setData(data);
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
