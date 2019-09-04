package com.qyt;

import java.io.Serializable;

public class EasyUITreeNode implements Serializable{

	long id ;
	String text;
	String state;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "EasyUITreeNode [id=" + id + ", text=" + text + ", state=" + state + "]";
	}
	
}
