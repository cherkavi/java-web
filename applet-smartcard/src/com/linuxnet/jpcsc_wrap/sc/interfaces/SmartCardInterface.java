package com.linuxnet.jpcsc_wrap.sc.interfaces;

import java.io.Serializable;

import com.linuxnet.jpcsc_wrap.sc.exceptions.*;

import com.linuxnet.jpcsc.PCSCException;

public interface SmartCardInterface extends Serializable{
	public int type = 0;

	public byte[] getRandom(int length) throws PCSCException, Exception;

	public void verifyPin(String pinvalue) throws PCSCException,
			UnknownCardException, SmartCardReaderException, InvalidPinException;

	public void changePin(String currentPin, String newPin,
			String newPinConfirmation) throws PCSCException,
			UnknownCardException, SmartCardReaderException, InvalidPinException;

	public byte[] readBinaryFile(int fileSelectionCommand)
			throws UnknownCardException, SmartCardReaderException,
			InvalidResponse;

	public byte[] sendCommand(byte[] command) throws PCSCException;

	public void open() throws UnknownCardException, SmartCardReaderException;

	public String getSmartCardReaderName();

	public void close();

	public byte[] fetchATR();

}