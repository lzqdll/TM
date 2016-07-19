package com.tm.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AttendType implements Serializable {
	private static final long serialVersionUID = 48L;

	// 标识属性
	private Integer id;
	// 出勤类型的名称
	private String name;

	private String description;
	// 此类出勤对应的罚款
	private double amerce;

	// 无参数的构造器
	public AttendType() {
	}

	// 初始化全部属性的构造器
	public AttendType(Integer id, String name, double amerce) {
		this.id = id;
		this.name = name;
		this.amerce = amerce;
	}

	// id属性的setter和getter方法
	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@GeneratedValue
	public Integer getId() {
		return this.id;
	}

	// name属性的setter和getter方法
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	// amerce属性的setter和getter方法
	public void setAmerce(double amerce) {
		this.amerce = amerce;
	}

	public double getAmerce() {
		return this.amerce;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}