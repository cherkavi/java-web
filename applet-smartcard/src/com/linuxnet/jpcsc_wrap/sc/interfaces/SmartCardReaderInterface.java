package com.linuxnet.jpcsc_wrap.sc.interfaces;

import com.linuxnet.jpcsc_wrap.sc.exceptions.InvalidResponse;

public interface SmartCardReaderInterface {
	public final static int UNKNOWN = 0;

	public int type = UNKNOWN;

	abstract byte[] sendCommand(byte[] apdu) throws InvalidResponse;
}