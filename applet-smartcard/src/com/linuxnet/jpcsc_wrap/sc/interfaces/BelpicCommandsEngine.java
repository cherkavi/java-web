package com.linuxnet.jpcsc_wrap.sc.interfaces;

public interface BelpicCommandsEngine extends EidCardCommandsInterface {
	public final static byte[] selectFileCommand = { (byte) 0x00, (byte) 0xa4,
			(byte) 0x02, (byte) 0x00, (byte) 0x02, (byte) 0x3f, (byte) 0x00 };

	public final static byte[] selectCitizenIdentityDataCommand = {
			(byte) 0x00, (byte) 0xA4, (byte) 0x08, (byte) 0x0C, (byte) 0x06,
			(byte) 0x3F, (byte) 0x00, (byte) 0xDF, (byte) 0x01, (byte) 0x40,
			(byte) 0x31 };

	public final static byte[] selectCitizenAddressDataCommand = { (byte) 0x00,
			(byte) 0xA4, (byte) 0x08, (byte) 0x0C, (byte) 0x06, (byte) 0x3F,
			(byte) 0x00, (byte) 0xDF, (byte) 0x01, (byte) 0x40, (byte) 0x33 };

	public final static byte[] selectCitizenPhotoCommand = { (byte) 0x00,
			(byte) 0xA4, (byte) 0x08, (byte) 0x0C, (byte) 0x06, (byte) 0x3F,
			(byte) 0x00, (byte) 0xDF, (byte) 0x01, (byte) 0x40, (byte) 0x35 };

	public final static byte[] prepareForAuthenticationSignatureCommand = {
			(byte) 0x00, (byte) 0x22, (byte) 0x41, (byte) 0xB6, (byte) 0x05,
			(byte) 0x04, (byte) 0x80, (byte) 0x02, (byte) 0x84, (byte) 0x82 };

	public final static byte[] prepareForNonRepudiationSignatureCommand = {
			(byte) 0x00, (byte) 0x22, (byte) 0x41, (byte) 0xB6, (byte) 0x05,
			(byte) 0x04, (byte) 0x80, (byte) 0x02, (byte) 0x84, (byte) 0x83 };

	public final static byte[] unblockCardApdu = { (byte) 0x00, (byte) 0x2c,
			(byte) 0x00, (byte) 0x01, (byte) 0x08, (byte) 0x2c, (byte) 0xFF,
			(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
			(byte) 0xFF };

	public final static byte[] verifyPinApdu = { (byte) 0x00, // cla: change pin
			// command
			(byte) 0x20, // ins: change pin command
			(byte) 0x00, //  p1: change pin command
			(byte) 0x01, //  p2: change pin command
			(byte) 0x08, //  p3: change pin command, 8 pin block bytes follow
			(byte) 0x2f, // pin block header
			(byte) 0xff, // pin block byte 2
			(byte) 0xff, // pin block byte 3
			(byte) 0xFF, // pin block byte 4
			(byte) 0xFF, // pin block byte 5
			(byte) 0xFF, // pin block byte 6
			(byte) 0xFF, // pin block byte 7
			(byte) 0xFF, // pin block byte 8
	};

	public final static byte[] changePinApdu = { (byte) 0x00, // cla: change pin
			// command
			(byte) 0x24, // ins: change pin command
			(byte) 0x00, //  p1: change pin command
			(byte) 0x01, //  p2: change pin command
			(byte) 0x10, //  p3: change pin command, 16 pin block bytes follow
			(byte) 0x2f, // header of first pin block
			(byte) 0xff, // pin block byte 2
			(byte) 0xff, // pin block byte 3
			(byte) 0xFF, // pin block byte 4
			(byte) 0xFF, // pin block byte 5
			(byte) 0xFF, // pin block byte 6
			(byte) 0xFF, // pin block byte 7
			(byte) 0xFF, // pin block byte 8
			(byte) 0x2f, // header of second pin block
			(byte) 0xff, // pin block byte 2
			(byte) 0xff, // pin block byte 3
			(byte) 0xFF, // pin block byte 4
			(byte) 0xFF, // pin block byte 5
			(byte) 0xFF, // pin block byte 6
			(byte) 0xFF, // pin block byte 7
			(byte) 0xFF, // pin block byte 8
	};

	public final static byte[] generateSignatureCommand = { (byte) 0x00,
			(byte) 0x2A, (byte) 0x9E, (byte) 0x9A, (byte) 0x14, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };

	public final static byte[] retrieveSignatureCommand = { (byte) 0x00,
			(byte) 0xC0, (byte) 0x00, (byte) 0x00, (byte) 0x80 };

	public final static byte[] getResultCommand = { (byte) 0x00, (byte) 0xC0,
			(byte) 0x00, (byte) 0x00, (byte) 0x00 };

	public final static byte[] selectAuthenticationCertificateCommand = {
			(byte) 0x00, (byte) 0xA4, (byte) 0x08, (byte) 0x0C, (byte) 0x06,
			(byte) 0x3F, (byte) 0x00, (byte) 0xDF, (byte) 0x00, (byte) 0x50,
			(byte) 0x38 };

	public final static byte[] selectNonRepudiationCertificateCommand = {
			(byte) 0x00, (byte) 0xA4, (byte) 0x08, (byte) 0x0C, (byte) 0x06,
			(byte) 0x3F, (byte) 0x00, (byte) 0xDF, (byte) 0x00, (byte) 0x50,
			(byte) 0x39 };

	public final static byte[] selectCaCertificateCommand = { (byte) 0x00,
			(byte) 0xA4, (byte) 0x08, (byte) 0x0C, (byte) 0x06, (byte) 0x3F,
			(byte) 0x00, (byte) 0xDF, (byte) 0x00, (byte) 0x50, (byte) 0x3a };

	public final static byte[] selectRootCaCertificateCommand = { (byte) 0x00,
			(byte) 0xA4, (byte) 0x08, (byte) 0x0C, (byte) 0x06, (byte) 0x3F,
			(byte) 0x00, (byte) 0xDF, (byte) 0x00, (byte) 0x50, (byte) 0x3b };

	public final static byte[] selectRrnCertificateCommand = { (byte) 0x00,
			(byte) 0xA4, (byte) 0x08, (byte) 0x0C, (byte) 0x06, (byte) 0x3F,
			(byte) 0x00, (byte) 0xDF, (byte) 0x00, (byte) 0x50, (byte) 0x3c };

	public final static byte[] selectIdentityFileCommand = { (byte) 0x00,
			(byte) 0xA4, (byte) 0x08, (byte) 0x0C, (byte) 0x06, (byte) 0x3F,
			(byte) 0x00, (byte) 0xDF, (byte) 0x01, (byte) 0x40, (byte) 0x32 };

	public final static byte[] selectAddressFileCommand = { (byte) 0x00,
			(byte) 0xA4, (byte) 0x08, (byte) 0x0C, (byte) 0x06, (byte) 0x3F,
			(byte) 0x00, (byte) 0xDF, (byte) 0x01, (byte) 0x40, (byte) 0x34 };

	public final static byte[] selectIdentityFileSignatureCommand = {
			(byte) 0x00, (byte) 0xA4, (byte) 0x08, (byte) 0x0C, (byte) 0x06,
			(byte) 0x3F, (byte) 0x00, (byte) 0xDF, (byte) 0x01, (byte) 0x40,
			(byte) 0x32 };

	public final static byte[] selectAddressFileSignatureCommand = {
			(byte) 0x00, (byte) 0xA4, (byte) 0x08, (byte) 0x0C, (byte) 0x06,
			(byte) 0x3F, (byte) 0x00, (byte) 0xDF, (byte) 0x01, (byte) 0x40,
			(byte) 0x34 };

	public final static byte[] readBinaryBlockCommand = { (byte) 0x00,
			(byte) 0xB0, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
}