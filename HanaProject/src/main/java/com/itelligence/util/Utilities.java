package com.itelligence.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.management.StringValueExp;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.itelligence.dao.DatabaseDAOImpl;

public class Utilities {

	private final static Logger logger = Logger.getLogger(Utilities.class);
	private final static String dateFormat = "YYYY-MM-dd";

	public static String combinedQueryBuilder(final int... number) {
		StringBuilder stringBuilder = new StringBuilder();
		int temp = number.length - 1;
		for (int i = 0; i < number.length; i++) {
			stringBuilder.append(number[i]);
			if (i != temp)
				stringBuilder.append(",");
		}
		return stringBuilder.toString();
	}

	public static String combinedQueryBuilder(final List<Integer> number) {
		StringBuilder stringBuilder = new StringBuilder();
		int temp = number.size() - 1;
		for (int i = 0; i < number.size(); i++) {
			stringBuilder.append(number.get(i));
			if (i != temp)
				stringBuilder.append(",");
		}
		return stringBuilder.toString();
	}

	public static String convertUtilDateToString(final java.util.Date date) {
		DateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.format(date);
	}

	public static List<Integer> asIntegerList(Integer... integers) {
		if (integers == null) {
			return null;
		} else {
			List<Integer> numbers = new ArrayList<>();
			for (Integer obj : integers)
				numbers.add(obj);
			return numbers;
		}
	}

	public static boolean dateFormatValidator(Date date) {
		if (date == null)
			return false;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		simpleDateFormat.setLenient(false);

		try {
			date = simpleDateFormat.parse(convertUtilDateToString(date));
			logger.info("dateFormatValidator:  " + date.toString());
		} catch (ParseException e) {
			logger.error("Exception in dateFormatValidator:  " + date.toString() + e);
			return false;
		}
		return true;
	}

	public static boolean isInRange(Integer number) {
		if (number == null)
			return false;
		if (!NumberUtils.isNumber(String.valueOf(number)))
			return false;
		if ((number >= -100) && (number <= 100))
			return true;
		return false;
	}

}
