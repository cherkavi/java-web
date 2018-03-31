package com.linuxnet.jpcsc_wrap.sc.engine;

import java.io.Serializable;

import com.linuxnet.jpcsc_wrap.sc.exceptions.InvalidResponse;
import com.linuxnet.jpcsc_wrap.sc.interfaces.SmartCardCommandsInterface;
import com.linuxnet.jpcsc_wrap.util.MathUtils;

public class SmartCard implements SmartCardCommandsInterface,Serializable {

	// 
	private SmartCardReader myReader = null;

	public void setCardReader(SmartCardReader value){
		this.myReader=value;
	}
	public SmartCardReader getCardReader(){
		return this.myReader;
	}
	
	public final static byte[] readBinaryBlockCommand = { (byte) 0x00,
														  (byte) 0xB0, 
														  (byte) 0x00, 
														  (byte) 0x00, 
														  (byte) 0x00 };

	public byte[] readBinaryFile(byte[] selectFileCommand)
			throws InvalidResponse {
		int wordLength = 1;
		int blocklength = 0x60;
		wordLength = 1;
		blocklength = 0xf8;

		int len = 0;
		int enough = -5000;
		byte[] certificate = new byte[10000];

		byte[] keyBytes = myReader.sendCommand(selectFileCommand);
		while (enough < 0) {
			readBinaryBlockCommand[2] = (byte) (len / wordLength / 256);
			readBinaryBlockCommand[3] = (byte) (len / wordLength % 256);
			readBinaryBlockCommand[4] = (byte) blocklength;
			keyBytes = myReader.sendCommand(readBinaryBlockCommand);

			if ((keyBytes[keyBytes.length - 2] == (byte) 0x90)
					&& (keyBytes[keyBytes.length - 1] == 0)) {
				for (int j = 0; j < MathUtils.min(blocklength,
						keyBytes.length - 2); j++) {
					certificate[len + j] = keyBytes[j];
				}
				enough = -1;
				len += blocklength;
			} else {
				enough = 0;
				if (keyBytes[keyBytes.length - 2] == (byte) 0x6c) {
					blocklength = keyBytes[keyBytes.length - 1];
					readBinaryBlockCommand[2] = (byte) (len / wordLength / 256);
					readBinaryBlockCommand[3] = (byte) (len / wordLength % 256);
					readBinaryBlockCommand[4] = (byte) blocklength;
					keyBytes = myReader.sendCommand(readBinaryBlockCommand);
					if ((keyBytes[keyBytes.length - 2] == (byte) 0x90)
							&& (keyBytes[keyBytes.length - 1] == 0)) {
						for (int j = 0; j < keyBytes.length - 2; j++)
							certificate[len + j] = keyBytes[j];
						len += keyBytes.length - 2;
					} else {
						// problem reading last block
					}
				}
			}
		}
		len = MathUtils.unsignedInt(len);
		byte[] tmp = new byte[len];
		for (int k = 0; k < len; k++)
			tmp[k] = certificate[k];
		return tmp;
	}
}