package com.itelligence.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.itelligence.domain.Hotel;
import com.itelligence.domain.Region;
import com.itelligence.domain.StarGroup;
import com.itelligence.domain.Town;
import com.itelligence.util.Utilities;
import com.sap.db.jdbc.Driver;

public class DatabaseDAOImpl implements DatabaseDAO {


	// private final static String dbDriver = "com.sap.db.jdbc.Driver";

	//private final static String dbDriver = "com.sap.db.jdbc.Driver";

	private final static Logger logger = Logger.getLogger(DatabaseDAOImpl.class);
	private final static String dbUsername = "HYBRISUSER";
	private final static String dbPassword = "Tac1976*";
	private final static String dbServer = "jdbc:sap://10.70.17.60:30015";

	private static Driver driver = new Driver();
	private static Connection connection = null;
	//private static PreparedStatement preparedStatement = null;

	private static Statement statement = null;
	private static ResultSet resultSet = null;

	@Override
	public List<Region> getAllRegions() {
		List<Region> list = new ArrayList<>();
		try {
			logger.info("Getting all regions from database");

			Driver driver = new Driver();

			DriverManager.registerDriver(driver);
			connection = DriverManager.getConnection(dbServer, dbUsername, dbPassword);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(
					"SELECT INC AS REGIONID, LNAME AS REGION FROM HYBRISUSER.REGION WHERE INC IN(24,39) ORDER BY 1");
			while (resultSet.next()) {
				logger.info("lname " + resultSet.getString("LNAME") + " regionid : " + resultSet.getInt("REGIONID"));
				Region region = new Region(resultSet.getString("LNAME"), resultSet.getInt("REGIONID"));
				list.add(region);
			}
		} catch (Exception e) {
			logger.error("Exception in getAllRegions: " + e);
		}
//		finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				logger.error("Exception in getAllRegions: " + e);
//			}
//		}
		return list;
	}

	@Override
	public List<Town> getTownsByRegionId(int... regionId) {
		List<Town> list = new ArrayList<>();
		String queryIn = "";
		try {
			logger.info("Getting towns by region ids from database regionid " + regionId);
			DriverManager.registerDriver(driver);
			connection = DriverManager.getConnection(dbServer, dbUsername, dbPassword);
			statement = connection.createStatement();
			if (regionId.length == 1) {
				// single selection
				queryIn = String.valueOf(regionId[0]);
			} else {
				queryIn = Utilities.combinedQueryBuilder(regionId);
			}

			String query = "SELECT DISTINCT B.LNAME AS TOWN, B.INC AS TOWNID, B.REGION FROM HYBRISUSER.REGION A INNER JOIN HYBRISUSER.TOWN B ON B.REGION=A.INC "
					+ "INNER JOIN HOTEL C ON C.TOWN=B.INC " + "INNER JOIN CAT_CLAIM D ON D.HOTEL=C.INC "
					+ "WHERE A.STATE=11 " + "AND B.REGION IN(" + queryIn + ") " + "ORDER BY 3,1 ";
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Town town = new Town(resultSet.getString("TOWN"), resultSet.getInt("TOWNID"));
				list.add(town);
			}
			
			logger.info("total selected towns "+ list.size());
		} catch (Exception e) {
			logger.error("Exception in getTownsByRegionId id: " + regionId.toString() + e);
		}
//		finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				logger.error("Exception in getTownsByRegionId id: " + regionId.toString() + e);
//			}
//		}
		return list;
	}

	@Override
	public List<StarGroup> getAllStarGroups() {
		List<StarGroup> list = new ArrayList<>();
		try {
			logger.info("Getting all regions from database");
			DriverManager.registerDriver(driver);
			connection = DriverManager.getConnection(dbServer, dbUsername, dbPassword);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(
					"SELECT INC, NAME FROM STAR_GROUP WHERE INC > 0 AND INC<=10");
			while (resultSet.next()) {
				StarGroup starGroup = new StarGroup(resultSet.getString("NAME"), resultSet.getInt("INC"));
				list.add(starGroup);
			}
		} catch (Exception e) {
			logger.error("Exception in getAllStarGroups(): " + e);
		}
//		finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				logger.error("Exception in getAllStarGroups(): " + e);
//			}
//		}
		return list;
	}

	@Override
	public List<Hotel> getHotelsByParameters(List<Integer> towns, List<Integer> stars, String checkIn, String checkOut,
			int adultFrom, int adultTo, int childFrom, int childTo) {
		List<Hotel> list = new ArrayList<>();
		String townIds = "";
		String starIds = "";
		try {
			logger.info("Getting hotels by parameters from database ");
			DriverManager.registerDriver(driver);
			connection = DriverManager.getConnection(dbServer, dbUsername, dbPassword);
			statement = connection.createStatement();

			// Town Selection
			if (towns == null)
				// single town selection
				townIds = "";
			else if (towns.size() == 0)
				// single town selection
				townIds = "";
			else
				// multiple town selection
				townIds = "AND A.INC IN (" + Utilities.combinedQueryBuilder(towns) + ")";

			// Star Selection
			if (stars == null)
				// single star selection
				starIds = "";
			else if (stars.size() == 0)
				// single star selection
				starIds = "";
			else
				// multiple star selection
				starIds = "AND G.INC IN (" + Utilities.combinedQueryBuilder(stars) + ")";

			String query = "SELECT DISTINCT B.LNAME AS HOTEL, A.LNAME AS TOWN, G.NAME AS STAR, B.INC AS HOTELID "
					+ "FROM HYBRISUSER.TOWN A " + "INNER JOIN HOTEL B ON A.INC=B.TOWN "
					+ "INNER JOIN CAT_CLAIM C ON C.HOTEL=B.INC " + "INNER JOIN REGION D ON D.INC=A.REGION "
					+ "INNER JOIN STAR E ON B.STAR=E.INC " + "INNER JOIN STAR_GROUP_REL F ON E.INC=F.STAR "
					+ "INNER JOIN STAR_GROUP G ON G.INC=F.GROUP_STAR " + "WHERE A.STATE=11 " + "" + townIds + " " + ""
					+ starIds + " " + "AND C.CHECKIN BETWEEN TO_DATE('" + checkIn + "', 'YYYY-MM-DD') AND TO_DATE('"
					+ checkOut + "', 'YYYY-MM-DD') " + "AND C.ADULT BETWEEN " + adultFrom + " AND " + adultTo + " "
					+ " AND C.CHILD BETWEEN " + childFrom + " AND " + childTo + " " + "ORDER BY 1";
			logger.info(query);
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Hotel hotel = new Hotel(resultSet.getInt("HOTELID"), resultSet.getString("HOTEL").trim(),
						resultSet.getString("TOWN").trim(), resultSet.getString("STAR").trim());
				list.add(hotel);
			}
			logger.info("Received hotel amount: " + list.size());
		} catch (Exception e) {
			logger.error("Exception in getHotelsByParameters " + e);
		}
//		finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				logger.error("Exception in getHotelsByParameters " + e);
//			}
//		}
		return list;
	}

	@Override
	public StringBuilder executeUpdate(List<Integer> hoteIds, String checkIn, String checkOut, int adultFrom,
			int adultTo, int childFrom, int childTo, int factor, int operationType) {

		StringBuilder stringBuilder = new StringBuilder();
		try {
			if (hoteIds == null) {
				logger.error("Hotel ids null!");
				throw new Exception("No Hotel ID is given!");
			}

			logger.info("Executing update query ");
			DriverManager.registerDriver(driver);
			connection = DriverManager.getConnection(dbServer, dbUsername, dbPassword);
			statement = connection.createStatement();
			String planeTicketsQuery = "UPDATE HYBRISUSER.CAT_CLAIM SET PFREIGHT = IFNULL((SELECT SUM(B.PRICE) FROM "
					+ "HYBRISUSER.CAT_FREIGHT B WHERE B.ROUTEINDEX=0 AND INC=B.CAT_CLAIM ),0) * " + "(1+(" + factor
					+ " /100)) , PREVISED= ROUND(PHOTEL + PSERVICE + "
					+ "PINSURANCE + (IFNULL((SELECT SUM(B.PRICE) FROM HYBRISUSER.CAT_FREIGHT B WHERE "
					+ "B.ROUTEINDEX=0 AND INC=B.CAT_CLAIM ),0) * (1+(" + factor
					+ "/100))),0) WHERE CHECKIN BETWEEN TO_DATE('" + checkIn + "', 'YYYY-MM-DD') " + "AND TO_DATE('"
					+ checkOut + "') AND " + "ADULT BETWEEN " + adultFrom + " AND " + adultTo + " AND "
					+ "CHILD BETWEEN " + childFrom + " AND " + childTo + " AND " + "HOTEL IN ("
					+ Utilities.combinedQueryBuilder(hoteIds) + ") ";

			String hotelPricesQuery = "UPDATE HYBRISUSER.CAT_CLAIM SET PHOTEL = IFNULL((SELECT SUM(A.PRICE) FROM "
					+ "HYBRISUSER.CAT_HOTEL A WHERE INC=A.CAT_CLAIM ),0) * (1+(" + factor
					+ "/100)) , PREVISED= ROUND(PFREIGHT +  PSERVICE + PINSURANCE + "
					+ "(IFNULL((SELECT SUM(A.PRICE) FROM HYBRISUSER.CAT_HOTEL A WHERE INC=A.CAT_CLAIM " + "),0) * (1+("
					+ factor + " /100))),0) WHERE  CHECKIN BETWEEN " + "TO_DATE('" + checkIn
					+ "', 'YYYY-MM-DD') AND TO_DATE('" + checkOut + " ', 'YYYY-MM-DD') " + "AND ADULT BETWEEN "
					+ adultFrom + " " + "AND " + adultTo + " AND CHILD BETWEEN " + childFrom + " " + "AND " + childTo
					+ " AND HOTEL IN (" + Utilities.combinedQueryBuilder(hoteIds) + ")";

			String query = "";

			/*
			 * Operation Type List 1. Corresponds to the Plane Ticket Update, 2.
			 * Corresponds to the Hotel Price Update
			 */

			switch (operationType) {
			case 1:
				query = planeTicketsQuery;
				break;

			case 2:
				query = hotelPricesQuery;
				break;

			default:
				logger.error("Error in executeUpdate wrong operation type has been selected!");
				break;
			}

			logger.info(query);
			long started = System.currentTimeMillis();
			int numberCount = statement.executeUpdate(query);
			String numberCountFormatted = String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(numberCount));
			stringBuilder.append(numberCountFormatted + " ");
			long finished = System.currentTimeMillis();
			stringBuilder.append("rows were affected in ");
			stringBuilder.append(finished - started);
			stringBuilder.append(" miliseconds.");

			logger.info(stringBuilder.toString());

		} catch (Exception e) {
			logger.error("Exception in executeUpdate " + e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Exception in executeUpdate " + e);
			}
		}
		return stringBuilder;
	}

	@Override
	public List<Hotel> getHotelsByStarIds(List<Integer> starIds) {
		List<Hotel> list = new ArrayList<>();
		String townIds = "";
		try {
			logger.info("Getting hotels  by starids from database");
			DriverManager.registerDriver(driver);
			connection = DriverManager.getConnection(dbServer, dbUsername, dbPassword);
			statement = connection.createStatement();

			String query = "SELECT H.TOWN, S.NAME AS HOTELSTAR, H.INC AS HOTELID, H.LNAME AS HOTELNAME, "
					+ "S.INC, T.INC, T.LNAME AS HOTELTOWN " + "FROM HYBRISUSER.HOTEL H, STAR_GROUP S, TOWN T WHERE "
					+ "H.STAR = S.INC AND H.TOWN = T.INC AND T.STATE=11 AND S.INC IN (" + Utilities.combinedQueryBuilder(starIds) + ")";
			logger.info(query);
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Hotel hotel = new Hotel(resultSet.getInt("HOTELID"), resultSet.getString("HOTELNAME").trim(),
						resultSet.getString("HOTELTOWN").trim(), resultSet.getString("HOTELSTAR").trim());
				list.add(hotel);
			}
			logger.info("found hotel size: " + list.size());
		} catch (Exception e) {
			logger.error("Getting hotels  by starids from database " + e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Getting hotels  by starids from database " + e);
			}
		}
		return list;
	}

	@Override
	public List<Hotel> getAllHotels() {
		List<Hotel> list = new ArrayList<>();
		try {
			logger.info("Getting hotels  by starids from database");
			DriverManager.registerDriver(driver);
			connection = DriverManager.getConnection(dbServer, dbUsername, dbPassword);
			statement = connection.createStatement();

			String query = "SELECT DISTINCT B.LNAME AS HOTEL, A.LNAME AS TOWN, G.NAME AS STAR, B.INC AS HOTELID " +
							"FROM HYBRISUSER.TOWN A " + 
							"INNER JOIN HOTEL B ON A.INC=B.TOWN " +
							"INNER JOIN CAT_CLAIM C ON C.HOTEL=B.INC  " +
							"INNER JOIN REGION D ON D.INC=A.REGION " +
							"INNER JOIN STAR E ON B.STAR=E.INC " + 
							"INNER JOIN STAR_GROUP_REL F ON E.INC=F.STAR " +
							"INNER JOIN STAR_GROUP G ON G.INC=F.GROUP_STAR  WHERE A.STATE=11 order by STAR ASC";
			
			logger.info("Getting all hotels query "+query);
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Hotel hotel = new Hotel(resultSet.getInt("HOTELID"), resultSet.getString("HOTEL").trim(),
						resultSet.getString("TOWN").trim(), resultSet.getString("STAR").trim());
				list.add(hotel);
			}
			logger.info("All hotel size: " + list.size());
		} catch (Exception e) {

			logger.error("Getting hotels " + e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("getAllHotels " + e);
			}
		}
		return list;
	}
}
