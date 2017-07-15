package com.itelligence.domain;

public class Town {

	private String townName;
	private int townId;

	public Town() {
		super();
	}

	public Town(String townName, int townId) {
		super();
		this.townName = townName;
		this.townId = townId;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public int getTownId() {
		return townId;
	}

	public void setTownId(int townId) {
		this.townId = townId;
	}

	@Override
	public String toString() {
		return "Town [townName=" + townName + ", townId=" + townId + "]";
	}

}
