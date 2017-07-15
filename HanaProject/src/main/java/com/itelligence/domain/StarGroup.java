package com.itelligence.domain;

public class StarGroup {
	
	private String starGroupName;
	private int starGroupId;
	
	public StarGroup() {
		super();
	}

	public StarGroup(String starGroupName, int starGroupId) {
		super();
		this.starGroupName = starGroupName;
		this.starGroupId = starGroupId;
	}

	public String getStarGroupName() {
		return starGroupName;
	}

	public void setStarGroupName(String starGroupName) {
		this.starGroupName = starGroupName;
	}

	public int getStarGroupId() {
		return starGroupId;
	}

	public void setStarGroupId(int starGroupId) {
		this.starGroupId = starGroupId;
	}

	@Override
	public String toString() {
		return "StarGroup [starGroupName=" + starGroupName + ", starGroupId=" + starGroupId + "]";
	}

}
