package com.linuxnet.jpcsc_wrap.sc.interfaces;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

import com.linuxnet.jpcsc_wrap.sc.engine.SmartCardReader;
import com.linuxnet.jpcsc_wrap.sc.exceptions.InvalidPinException;
import com.linuxnet.jpcsc_wrap.sc.exceptions.InvalidResponse;
import com.linuxnet.jpcsc_wrap.sc.exceptions.NoReadersAvailable;
import com.linuxnet.jpcsc_wrap.sc.exceptions.PinException;
import com.linuxnet.jpcsc_wrap.sc.exceptions.SignatureGenerationException;
import com.linuxnet.jpcsc_wrap.sc.exceptions.SmartCardReaderException;
import com.linuxnet.jpcsc_wrap.sc.exceptions.NoSuchFeature;
import com.linuxnet.jpcsc_wrap.sc.exceptions.UnknownCardException;

import com.linuxnet.jpcsc.PCSCException;

public interface EidCardInterface {
	public final static String defaultPreferredSmartCardReader = "VASCO";

	public final static int minNofPinDigits = 4;

	public final static int maxNofPinDigits = 6;

	public final static byte acceptedPinLengths = minNofPinDigits * 16
			+ maxNofPinDigits;

	public final static int defaultTimeoutInMilliSecondsBeforeTryingAnotherReader = 250;

	public final static String eid = "electronic identity card";

	public final static String purse = "electronic purse";

	public final static int UNKOWN_CARD = 0;

	public final static int GENERIC_EID_CARD = 1;

	public final static int BELPIC_CARD = 2;

	public final static int ESTEID_CARD = 3;

	public final static int FINEID_CARD = 4;

	public final static int EMV_CARD = 5;

	public final static int PROTON_CARD = 6;

	public final static String blanc = " ";

	public void changePin() throws NoSuchFeature, NoReadersAvailable, InvalidPinException, PinException, InvalidResponse;

	public final static String[] cardName = { "Generic" + blanc + eid,
			"Belpic" + blanc + eid, "Estonian" + blanc + eid,
			"Finnish" + blanc + eid, "EMV" + blanc + purse,
			"Proton" + blanc + purse };

	public SmartCardReader getSmartCardReader() throws InvalidResponse,
			NoReadersAvailable;

	public int returnMyType();

	public int type = UNKOWN_CARD;

	public RSAPublicKey getPublicAuthenticationKey() throws NoSuchFeature,
			InvalidResponse, SmartCardReaderException;

	public RSAPublicKey getPublicNonRepudiationKey() throws NoSuchFeature,
			InvalidResponse;

	public X509Certificate getAuthenticationCertificate() throws NoSuchFeature,
			InvalidResponse;

	public X509Certificate getNonRepudiationCertificate() throws NoSuchFeature,
			InvalidResponse;

	public X509Certificate getCertificationAuthorityCertificate()
			throws NoSuchFeature, InvalidResponse;

	public X509Certificate getRootCertificationAuthorityCertificate()
			throws NoSuchFeature;

	public byte[] selectFile(int fileid) throws UnknownCardException,
			NoReadersAvailable, NoSuchFeature, SmartCardReaderException,
			InvalidResponse;

	public byte[] getAuthCertificateBytes() throws UnknownCardException,
			NoSuchFeature, SmartCardReaderException, InvalidResponse;

	public byte[] getNonRepCertificateBytes() throws UnknownCardException,
			NoSuchFeature, SmartCardReaderException, InvalidResponse;

	public byte[] readCACertificateBytes() throws UnknownCardException,
			NoSuchFeature, SmartCardReaderException, InvalidResponse;

	public byte[] readIdentityFileSignatureBytes() throws UnknownCardException,
			NoSuchFeature, SmartCardReaderException, InvalidResponse;

	public byte[] readAddressFileSignatureBytes() throws UnknownCardException,
			NoSuchFeature, SmartCardReaderException, InvalidResponse;

	public byte[] readRRNCertificateBytes() throws UnknownCardException,
			NoReadersAvailable, NoSuchFeature, SmartCardReaderException,
			InvalidResponse;

	public byte[] readRootCACertificateBytes() throws UnknownCardException,
			NoReadersAvailable, NoSuchFeature, SmartCardReaderException,
			InvalidResponse;

	public byte[] readCitizenIdentityDataBytes() throws UnknownCardException,
			NoReadersAvailable, NoSuchFeature, SmartCardReaderException,
			InvalidResponse;

	public byte[] readCitizenAddressBytes() throws UnknownCardException,
			NoReadersAvailable, NoSuchFeature, SmartCardReaderException,
			InvalidResponse;

	public byte[] readCitizenPhotoBytes() throws NoReadersAvailable,
			NoSuchFeature, InvalidResponse;

	public byte[] generateAuthenticationSignature(byte[] datahash)
			throws NoReadersAvailable,SignatureGenerationException,NoSuchFeature,InvalidResponse;

	public byte[] generateNonRepudiationSignature(byte[] datahash)
			throws PCSCException, NoReadersAvailable,SignatureGenerationException,InvalidResponse,NoReadersAvailable, Exception;

	public byte[] retrieveSignatureBytes() throws NoReadersAvailable,InvalidResponse,
			NoSuchFeature;

	public void reActivate(String puk1,String puk2) throws InvalidPinException,
			InvalidResponse, NoSuchFeature, NoReadersAvailable;

	public void setPin(String pinvalue) throws PCSCException,
			InvalidResponse, NoSuchFeature, NoReadersAvailable,
			UnknownCardException, SmartCardReaderException, InvalidPinException;

	public String nameOfActiveReader() throws NoReadersAvailable;

	public byte[] fetchATR() throws NoReadersAvailable;

	public void clearCache();
}