package com.linuxnet.jpcsc_wrap.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

	public static InputStream bytesToStream(byte[] ba) throws IOException {
		ByteArrayInputStream fis = new ByteArrayInputStream(ba);
		DataInputStream dis = new DataInputStream(fis);
		byte[] bytes = new byte[dis.available()];
		dis.readFully(bytes);
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return bais;
	}

	public static InputStream fileToStream(String filename)
		throws IOException {
		FileInputStream fis = new FileInputStream(filename);
		DataInputStream dis = new DataInputStream(fis);
		byte[] bytes = new byte[dis.available()];
		dis.readFully(bytes);
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return bais;
	}

	public static byte[] readFile(String filename)
		throws FileNotFoundException, IOException {
		FileInputStream file = new FileInputStream(filename);
		byte[] data = new byte[(int) file.available()];
		file.read(data);
		file.close();
		return data;
	}

	public static void writeFile(String filename, byte[] data)
		throws FileNotFoundException, IOException {
		FileOutputStream file = new FileOutputStream(filename);
		file.write(data);
		file.close();
	}

	public static void writeBytesToFile(String filename, byte[] data)
		throws FileNotFoundException, IOException {
		FileOutputStream file = new FileOutputStream(filename);
		file.write(data);
		file.close();
	}


}