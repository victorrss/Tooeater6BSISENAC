package br.senac.backend.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;

public class ImageUtil {

	public static String[] mimeTypesAllow = {"image/gif","image/jpeg","image/png"};

	private static void createFolder(String folderPath) {
		try {
			(new File(folderPath)).mkdirs();
		} catch (Exception e) { }
	}

	public static String read(String folderPath, String fileName) throws IOException {
		createFolder(folderPath);
		String base64Image = "";
		
		File file = new File(folderPath +"\\"+ fileName);
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			// read file and convert to base64 string
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			base64Image = java.util.Base64.getEncoder().encodeToString(imageData);	

			// get mimeType
			@SuppressWarnings("deprecation")
			URLConnection connection = file.toURL().openConnection();
			String mimeType = connection.getContentType();
			return "data:"+mimeType+";base64," + base64Image;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw ioe;
		}
	}

	public static boolean save(String base64String, String folderPath, String fileName) {
		createFolder(folderPath);
		String fileArr[] = base64String.split("[,]");
		String base64 = fileArr[1];
		try {
			String extension = getExtension(getMimeType(base64String));
			System.out.println(extension);
			FileOutputStream fos = new FileOutputStream(folderPath + fileName + extension);
			byte byteArray[] = Base64.getDecoder().decode(base64);
			fos.write(byteArray);
			fos.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getMimeType(String base64String) {
		String fileArr[] = base64String.split("[,]");
		String mimeType = fileArr[0].substring(fileArr[0].indexOf(':') + 1, fileArr[0].indexOf(';'));
		return mimeType;
	}

	public static String getExtension(String mimeType) throws Exception {
		switch (mimeType) {
		case "image/gif":
			return ".gif";
		case "image/png":
			return ".png";
		case "image/jpeg":
			return ".jpg";
		default:
			return "";
		}
	}

}
