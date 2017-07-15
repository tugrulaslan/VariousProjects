package com.itelligence.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.itelligence.domain.Hotel;
import com.itelligence.domain.Region;
import com.itelligence.domain.StarGroup;
import com.itelligence.domain.Town;
import com.itelligence.util.Utilities;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseDAOTest {
	
	private static DatabaseDAO dao;
	private final static String checkInDate = "2015-11-10";
	private final static String checkOutDate = "2015-11-17";
	private final static int townId = 869;
	private final static int starId = 9;
	private final static int adultFrom = 2;
	private final static int adultTo = 2;
	private final static int childFrom = 0;
	private final static int childTo = 0;
	private final static int hotelId = 1798;
	private final static int factor = 10;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dao = new DatabaseDAOImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void stage01_GetAllRegionsTest() {
		List<Region> list = new ArrayList<>();
		list=dao.getAllRegions();
		Region obj = new Region();
		assertNotNull(list);
		assertTrue(list.size()>0);		
	}
	
	@Test
	public void stage02_GetTownsByRegionId(){
		//Test Parameters
		//[Region [regionName=Sharm El-Sheikh, regionId=24], Region [regionName=Hurghada, regionId=39]]
		List<Town> list = new ArrayList<>();
		list=dao.getTownsByRegionId(39);
		assertNotNull(list);
		assertTrue(list.size() > 0);
	}
	
	@Test
	public void stage03_GetAllStarGroup(){
		List<StarGroup> list = new ArrayList<>();
		list = dao.getAllStarGroups();
		assertNotNull(list);
		assertTrue(list.size() > 0);
	}
	
	@Test
	public void stage04_GetHotelsByTownIdAndStarGiven(){
		List<Hotel> hotels = dao.getHotelsByParameters(Utilities.asIntegerList(townId), Utilities.asIntegerList(starId), checkInDate, checkOutDate, adultFrom, adultTo, childFrom, childTo);
		assertNotNull(hotels);
		assertTrue(hotels.size() > 0);
	}
	
	@Test
	public void stage05_GetHotelsByTownIdAndStarEmpty(){
		List<Hotel> hotels = dao.getHotelsByParameters(Utilities.asIntegerList(), Utilities.asIntegerList(), checkInDate, checkOutDate, adultFrom, adultTo, childFrom, childTo);
		assertNotNull(hotels);
		assertTrue(hotels.size() > 0);
	}

	@Test
	public void stage06_GetHotelsByTownIdAndStarNull(){
		List<Hotel> hotels = dao.getHotelsByParameters(Utilities.asIntegerList(null), Utilities.asIntegerList(null), checkInDate, checkOutDate, adultFrom, adultTo, childFrom, childTo);
		assertNotNull(hotels);
		assertTrue(hotels.size() > 0);
	}
	
	@Test
	public void stage07_ExecuteUpdateFlightTest(){
		StringBuilder stringBuilder = dao.executeUpdate(Utilities.asIntegerList(hotelId), checkInDate, checkOutDate, adultFrom, adultTo, childFrom, childTo, factor, 1);
		assertNotNull(stringBuilder);
	}
	
	@Test
	public void stage08_GetHotelsByStarIdsTestWithHotelIds(){
		List<Hotel> list = dao.getHotelsByStarIds(Utilities.asIntegerList(1, 8));
		assertNotNull(list);
		assertTrue(list.size() > 0);
	}
	
	@Test
	public void stage09_GetHotelsByStarIdsTestWithoutHotelIds(){
		List<Hotel> list = dao.getHotelsByStarIds(Utilities.asIntegerList(null));
		assertNotNull(list);
		assertTrue(list.size() == 0);
	}
	
}
