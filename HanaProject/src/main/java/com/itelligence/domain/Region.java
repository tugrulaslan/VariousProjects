package com.itelligence.domain;

public class Region {
	
	private String regionName;
	private int regionId;
	
	public Region() {
		super();
	}

	public Region(String regionName, int regionId) {
		super();
		this.regionName = regionName;
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	@Override
	public String toString() {
		return "Region [regionName=" + regionName + ", regionId=" + regionId + "]";
	}
}