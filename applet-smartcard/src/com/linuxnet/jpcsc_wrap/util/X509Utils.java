package com.linuxnet.jpcsc_wrap.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class X509Utils {
	public static X509Certificate deriveCertificateFrom(InputStream inStream)
			throws IOException, CertificateException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf
				.generateCertificate(inStream);
		inStream.close();
		return cert;
	}

	public X509Certificate deriveCertificateFrom(byte[] bytes)
			throws IOException, CertificateException {
		return deriveCertificateFrom(new ByteArrayInputStream(bytes));
	}

	public static void dumpCertificateToFile(String filename,
			X509Certificate cert) throws CertificateEncodingException,
			FileNotFoundException, IOException {
		FileOutputStream file = new FileOutputStream(filename);
		file.write(cert.getEncoded());
		file.close();
	}

	public static X509Certificate importCertificateFrom(File file)
			throws CertificateException, IOException {
		return deriveCertificateFrom(new FileInputStream(file));
	}

	public static String convertCertificateToPEM(
			java.security.cert.Certificate cert)
			throws CertificateEncodingException {
		return "-----BEGIN CERTIFICATE-----\n"
				+ new sun.misc.BASE64Encoder()
						.encode(convertCertificateToDER(cert))
				+ "\n-----END CERTIFICATE-----\n";
	}

	public static byte[] convertCertificateToDER(
			java.security.cert.Certificate cert)
			throws CertificateEncodingException {
		return cert.getEncoded();
	}
}