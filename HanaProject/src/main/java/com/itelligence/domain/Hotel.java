package com.itelligence.domain;

public class Hotel {
	private int hotelId;
	private String hotelName;
	private String townName;
	private String stars;

	public Hotel() {
		super();
	
	}

	public Hotel(int hotelId, String hotelName, String townName, String stars) {
		super();
		this.hotelId = hotelId; 
		this.hotelName = hotelName;
		this.townName = townName;
		this.stars = stars;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

	@Override
	public String toString() {
		return "Hotel [hotelId=" + hotelId + ", hotelName=" + hotelName + ", townName=" + townName + ", stars=" + stars
				+ "]";
	}

}
