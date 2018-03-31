package com.linuxnet.jpcsc_wrap.sc.engine;

import com.linuxnet.jpcsc_wrap.sc.exceptions.InvalidResponse;
import com.linuxnet.jpcsc_wrap.sc.interfaces.SmartCardResponseInterface;
import com.linuxnet.jpcsc_wrap.sc.util.StatusWords;

public final class SmartCardResponse implements SmartCardResponseInterface {

	private byte[] data = null;

	private byte[] statusWords = null;

	public SmartCardResponse(byte[] res) throws InvalidResponse {
		if (res == null)
			throw new InvalidResponse("SmartCardResponse should not be null");
		if (res.length < 2)
			throw new InvalidResponse(
					"SmartCardResponse should consist of at least two bytes");
		data = new byte[res.length - 2];
		
		System.arraycopy(res, 0, data, 0, res.length - 2);

		statusWords = new byte[2];
		System.arraycopy(res, res.length - 2, statusWords, 0, 2);
	}

	public byte[] getData() {
		return data;
	}

	public String getCommandStatus() throws InvalidResponse {
		return getCommandStatus(statusWords);
	}

	public String getCommandStatus(byte[] sw) throws InvalidResponse {
		return StatusWords.getCommandStatus(sw);
	}
}