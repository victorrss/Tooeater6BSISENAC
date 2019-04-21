package br.senac.backend.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.validator.routines.EmailValidator;


public class Util {

	public static boolean empty(final String s) {
		// Null-safe, short-circuit evaluation.
		return s == null || s.trim().isEmpty();
	}

	public static boolean isDate(final Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setLenient(false);
		cal.setTime(d);
		try {
			cal.getTime();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public static Date getDateNow(){
		Calendar c = new GregorianCalendar();
		Date now = new Date();
		c.setTime(now);
		return c.getTime();
	}

	public static boolean isValidEmailAddress(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

}
