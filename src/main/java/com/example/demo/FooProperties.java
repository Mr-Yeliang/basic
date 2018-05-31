package com.example.demo;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("foo")
public class FooProperties {

	private String name;

	private String sex;

	private String mobile;

	private Map<String, String> map;
	
	public FooProperties(String name, String sex, String mobile, Map<String, String> map) {
		super();
		this.name = name;
		this.sex = sex;
		this.mobile = mobile;
		this.map = map;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "FooProperties [name=" + name + ", sex=" + sex + ", mobile=" + mobile + ", map=" + map + "]";
	}

	

	
	
}
