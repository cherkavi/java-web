package com.linuxnet.jpcsc_wrap.sc.util;

import com.linuxnet.jpcsc_wrap.sc.exceptions.InvalidResponse;
import com.linuxnet.jpcsc_wrap.util.TextUtils;

public class StatusWords {
	public final static String separator = ": ";

	public static String getCommandStatus(byte[] sw) throws InvalidResponse {
		if (sw == null)
			throw new InvalidResponse("StatusWords should not be null...");
		if (sw.length != 2)
			throw new InvalidResponse(
					"StatusWords should consist of two bytes...");

		String sw1Hex = TextUtils.hexDump(sw, 0, 1);
		String sw2Hex = TextUtils.hexDump(sw, 1, 1);

		return sw1Hex+sw2Hex;
/*		
		String statusClass = "";
		statusClass = "Normal processing";
		if (sw1Hex == "90") {
			if (sw2Hex == "00")
				return statusClass + separator + "No further qualification";
			else
				return "Invalid status word <" + sw1Hex + sw2Hex + ">";
		} else if (sw1Hex == "61") {
			Integer sw2 = Integer.valueOf(sw2Hex);
			return statusClass + separator
					+ "Number of response bytes still available equals <" + sw2
					+ ">";
		} else {
			statusClass = "Warning processings";
			if (sw1Hex == "62") {
				if (sw2Hex == "00")
					return statusClass + separator + "No information given";
				else if (sw2Hex == "81")
					return statusClass + separator
							+ "Part of returned data may be corrupted";
				else if (sw2Hex == "82")
					return statusClass
							+ separator
							+ "End of file/record reached before reading Le bytes";
				else if (sw2Hex == "83")
					return statusClass + separator + "Selected file invalidated";
				else if (sw2Hex == "84")
					return statusClass + separator
							+ "FCI not formatted according to 1.1.5";
				return "Invalid status word <" + sw1Hex + sw2Hex + ">";
			} else if (sw1Hex == "63") {
				if (sw2Hex == "00")
					return "No information given";
				else if (sw2Hex == "81")
					return "File filled up by the last write";
				else if (sw2Hex.substring(0, 1) == "C")
					return "Counter provided by '0x"
							+ sw2Hex.substring(1, 1)
							+ "' (valued from 0 to 15) (exact meaning depending on the command)";
				return "Invalid status word <" + sw1Hex + sw2Hex + ">";
			} else {
				statusClass = "Execution errors";
				if (sw1Hex == "64") {
					if (sw2Hex == "00")
						return statusClass + separator
								+ "State of non-volatile memory unchanged";
					else
						return "Invalid status word <" + sw1Hex + sw2Hex + ">";
				} else if (sw1Hex == "65") {
					if (sw2Hex == "00")
						return "No information given";
					else if (sw2Hex == "81")
						return "Memory failure";
					return "Invalid status word <" + sw1Hex + sw2Hex + ">";
				} else if (sw1Hex == "66") {
					return "Status word <" + sw1Hex + sw2Hex
							+ "> reserved for security-related issues";
				} else {
					statusClass = "Checking errors";
					if (sw1Hex == "67") {
						if (sw2Hex == "00")
							return "Wrong length";
						return "Invalid status word <" + sw1Hex + sw2Hex + ">";
					} else if (sw1Hex == "68") {
						if (sw2Hex == "00")
							return "No information given";
						else if (sw2Hex == "81")
							return "Logical channel not supported";
						else if (sw2Hex == "82")
							return "Secure messaging not supported";
						return "Invalid status word <" + sw1Hex + sw2Hex + ">";
					} else if (sw1Hex == "69") {
						if (sw2Hex == "00")
							return "No information given";
						else if (sw2Hex == "81")
							return "Command incompatible with file structure";
						else if (sw2Hex == "82")
							return "Security status not satisfied";
						else if (sw2Hex == "83")
							return "Authentication method blocked";
						else if (sw2Hex == "84")
							return "Referenced data invalidated";
						else if (sw2Hex == "85")
							return "Conditions of use not satisfied";
						else if (sw2Hex == "86")
							return "Command not allowed (no current EF)";
						else if (sw2Hex == "87")
							return "Expected SM data objects missing";
						else if (sw2Hex == "88")
							return "SM data objects incorrect";
						return "Invalid status word <" + sw1Hex + sw2Hex + ">";
					} else if (sw1Hex == "6A") {
						if (sw2Hex == "00")
							return "No information given";
						else if (sw2Hex == "80")
							return "Incorrect parameters in the data field";
						else if (sw2Hex == "81")
							return "Function not supported";
						else if (sw2Hex == "82")
							return "File not found";
						else if (sw2Hex == "83")
							return "Record not found";
						else if (sw2Hex == "84")
							return "Not enough memory space in the file";
						else if (sw2Hex == "85")
							return "Lc inconsistent with TLV structure";
						else if (sw2Hex == "86")
							return "Incorrect parameters P1-P2";
						else if (sw2Hex == "87")
							return "Lc inconsistent with P1-P2";
						else if (sw2Hex == "88")
							return "Referenced data not found";
						return "Invalid status word <" + sw1Hex + sw2Hex + ">";
					} else if (sw1Hex == "6B") {
						if (sw2Hex == "00")
							return "Wrong parameter(s) P1-P2";
						return "Invalid status word <" + sw1Hex + sw2Hex + ">";
					} else if (sw1Hex == "6C") {
						Integer sw2 = Integer.valueOf(sw2Hex);
						return statusClass
								+ separator
								+ "Wrong length Le: Number of response bytes still available equals <"
								+ sw2 + ">";
					} else if (sw1Hex == "6D") {
						if (sw2Hex == "00")
							return "Instruction code not supported or invalid";
						return "Invalid status word <" + sw1Hex + sw2Hex + ">";
					} else if (sw1Hex == "6E") {
						if (sw2Hex == "00")
							return "Class not supported";
						return "Invalid status word <" + sw1Hex + sw2Hex + ">";
					} else if (sw1Hex == "6F") {
						if (sw2Hex == "00")
							return "No precise diagnosis";
						return "Invalid status word <" + sw1Hex + sw2Hex + ">";
					}
				}

			}

		}
		return "Status word <" + sw1Hex + sw2Hex + ">";
	*/}
}