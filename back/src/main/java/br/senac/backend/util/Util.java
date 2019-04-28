package br.senac.backend.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.validator.routines.EmailValidator;

public class Util {

	public static boolean empty(final String s) {
		// Null-safe, short-circuit evaluation.
		return s == null || s.trim().isEmpty();
	}

	public static boolean isDate(final Calendar d) {
		Calendar cal = Calendar.getInstance();
		cal.setLenient(false);
		cal.setTime(d.getTime());
		try {
			cal.getTime();
			return true;
		}
		catch (Exception e) {
			return false;
		}
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
		return  new Date();
	}

	public static boolean isValidEmailAddress(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

	public static String sha1(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString().toUpperCase();
	}
}
