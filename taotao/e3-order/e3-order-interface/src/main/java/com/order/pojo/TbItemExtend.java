package com.order.pojo;

import java.io.Serializable;

import com.qyt.pojo.TbItem;

public class TbItemExtend extends TbItem implements Serializable{
	
	private String images;

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "TbItemExtend [images=" + images + "]";
	}
}
