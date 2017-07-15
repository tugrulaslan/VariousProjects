package com.itelligence.dao;

import java.util.List;

import com.itelligence.domain.Hotel;
import com.itelligence.domain.Region;
import com.itelligence.domain.StarGroup;
import com.itelligence.domain.Town;

public interface DatabaseDAO {

	List<Region> getAllRegions();

	List<Town> getTownsByRegionId(final int... regionId);

	List<StarGroup> getAllStarGroups();

	List<Hotel> getHotelsByParameters(final List<Integer> towns, final List<Integer> stars, String checkIn,
			String checkOut, int adultFrom, int adultTo, int childFrom, int childTo);

	StringBuilder executeUpdate(final List<Integer> hoteIds, String checkIn, String checkOut, int adultFrom,
			int adultTo, int childFrom, int childTo, int factor, int operationType);
	
	List<Hotel> getHotelsByStarIds(final List<Integer> starIds);
	
	List<Hotel> getAllHotels();
}
