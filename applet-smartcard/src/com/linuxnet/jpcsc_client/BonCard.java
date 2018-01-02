package com.linuxnet.jpcsc_client;
import java.io.Serializable;

import com.linuxnet.jpcsc_wrap.sc.engine.SmartCard;
import com.linuxnet.jpcsc_wrap.sc.engine.SmartCardReader;
import com.linuxnet.jpcsc_wrap.sc.exceptions.InvalidResponse;
import com.linuxnet.jpcsc_wrap.sc.exceptions.NoReadersAvailable;
import com.linuxnet.jpcsc_wrap.sc.exceptions.NoSuchFeature;
import com.linuxnet.jpcsc_wrap.sc.exceptions.SmartCardReaderException;
import com.linuxnet.jpcsc_wrap.sc.exceptions.UnknownCardException;
import com.linuxnet.jpcsc_wrap.util.MathUtils;
/**
 * класс предназначен для удаленного управления устройством Reader Smart Card 
 * @author cherkashinv
 *
 */
public class BonCard implements Serializable{
	/** текущий SmartCardReader */
	private SmartCardReader field_current_card_reader;
	/** текущая SmartCard */
	private SmartCard field_current_smart_card;
	
	/** вывод отладочной информации*/
	private void debug(String information){
		System.out.println("BonCard DEBUG:"+information);
	}
	/** вывод ошибочной информации */
	private void error(String information){
		System.out.println("BonCard ERROR:"+information);
	}
	
	public BonCard(){
		this.field_current_card_reader=new SmartCardReader();
		this.field_current_smart_card=new SmartCard();
	}

	/** получить имена всех подключенных устройств */
	public String[] getReaders(){
		return field_current_card_reader.getDevices();
	}
	/** получить все имена групп подключенных Reader-ов*/
	public String[] getReadersGroup(){
		return field_current_card_reader.getDevicesGroup();
	}
	/** подключиться к устройству по его имени */
	public boolean connectToDeviceByName(String name){
		boolean return_value=false;
		try{
			debug("connectToDeviceByName: resetReader()");
			// необходимо делать Reset перед повторной инициализацией
			this.resetReader();
			debug("connectToDeviceByName:"+name);
			return_value=this.field_current_card_reader.connectToDeviceName(name);
			debug("set card reader");
			field_current_smart_card.setCardReader(this.field_current_card_reader);
		}catch(Exception ex){
			return_value=false;
			error("connectToDeviceByName:"+ex.getMessage());
		}
		return return_value;
	}
	
	public SmartCardReader getSmartCardReader(){
		return this.field_current_card_reader;
	}
	
	public SmartCard getSmartCard(){
		return this.field_current_smart_card;
	}
	
	public String getCurrentDeviceName(){
		return this.field_current_card_reader.getMyName();
	}

	/** отключиться от устройства*/
	public void disconnect(){
		try{
			this.field_current_card_reader.powerOff();
			this.field_current_card_reader.finalize();
			/*
			this.field_current_card_reader=null;			 
			Class.forName("Context").getClassLoader().clearAssertionStatus();
			Class.forName("Card").getClassLoader().clearAssertionStatus();
			Class.forName("State").getClassLoader().clearAssertionStatus();
			*/
		}catch(Exception ex){
			error("disconnect:"+ex.getMessage());
		}
	}
//	 kmbRESET_STATE Команда сбасывает состояние карты
	public byte[] kmbRESET_STATE() throws UnknownCardException, NoSuchFeature, SmartCardReaderException, InvalidResponse, NoReadersAvailable {
		return this.getSmartCardReader().sendCommand(kmbResetStateApdu);
	}
	
	/** сброс устройства */
	public byte[] resetReader(){
		byte[] return_value=null;
		try{
			if(this.getSmartCardReader().getMyName()!=null){
				// подключения к устройствам уже были, нужно сделать еще одно
				return_value=this.getSmartCardReader().sendCommand(this.field_current_card_reader.ResetReaderCommand);
			}else{
				// нет подключенного устройства - первый Connect к устройству
			}
		}catch(Exception ex){
			error("resetReader: Exception:"+ex.getMessage());
		}
		return return_value;
	}
	
	/** обязательное освобождение контекста, в случае аварийного завершения задачи */
	public void finilize(){
		try{
			disconnect();
		}catch(Exception ex){
			
		}
	}
	
	/** послать команду в устройство и получить ответ от устройства 
	 * @throws если произошла ошибка - выбрасываем исключение 
	 * */
	public byte[] sendCommand(byte[] data) throws Exception {
		byte[] return_value=null;
		try {
			return_value=this.field_current_card_reader.sendCommand(data);
		} catch (Exception ex) {
			error("sendCommand:"+ex.getMessage());
			throw ex;
			
		}
		return return_value;
	}

// основные операции обмена данными с картой 	
	/** readRecord читает запись из EF */
	public byte[] readRecord(int NR) throws UnknownCardException, NoSuchFeature, SmartCardReaderException, InvalidResponse, NoReadersAvailable {
		byte[] readRecordCardCommand = insertNumRecIntoApdu(readRecordApdu, NR);
		return this.getSmartCardReader().sendCommand(readRecordCardCommand);

	}

	/**	 readBinary читает запись длиной */
	public byte[] readBinary(int LR) throws UnknownCardException, NoSuchFeature, SmartCardReaderException, InvalidResponse, NoReadersAvailable {
		byte[] readRecordCardCommand = insertLenRecIntoApdu(readBinaryCommand, LR);
		return this.getSmartCardReader().sendCommand(readRecordCardCommand);
	}
	
	/**  getCardData Команда читает серийный номер, номер версии ОС, данные конфигурации чипа */
	public byte[] getCardData(int DataID) throws UnknownCardException, NoSuchFeature, SmartCardReaderException, InvalidResponse, NoReadersAvailable {
		byte[] selectGetCardData = insertDataSelectGetCardData(getCardDataApdu, DataID);
		return this.getSmartCardReader().sendCommand(selectGetCardData);
	}
	
	/** получить атрибуты состояния */	
	public byte[] fetchATR() throws NoReadersAvailable {
		return this.getSmartCardReader().getATR();
	}
	
	public final static byte[] readBinaryBlockCommand = { (byte) 0x00,
		(byte) 0xB0, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
	/** чтение данных из файла */
	public byte[] readBinaryFile(byte[] selectFileCommand) throws InvalidResponse {
		int wordLength = 1;
		int blocklength = 0x60;
		wordLength = 1;
		blocklength = 0xf8;

		int len = 0;
		int enough = -5000;
		byte[] certificate = new byte[10000];

		byte[] keyBytes = this.getSmartCardReader().sendCommand(selectFileCommand);
		while (enough < 0) {
			readBinaryBlockCommand[2] = (byte) (len / wordLength / 256);
			readBinaryBlockCommand[3] = (byte) (len / wordLength % 256);
			readBinaryBlockCommand[4] = (byte) blocklength;
			keyBytes = this.getSmartCardReader().sendCommand(readBinaryBlockCommand);

			if (  (keyBytes[keyBytes.length - 2] == (byte) 0x90)
				&&(keyBytes[keyBytes.length - 1] == 0)) {
				for (int j = 0; j < MathUtils.min(blocklength,keyBytes.length - 2); j++) {
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
					keyBytes = this.getSmartCardReader().sendCommand(readBinaryBlockCommand);
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
	
	/** Активизация существующего файла или уровня DF или MF */
	public final static byte[] selectFileApdu = { (byte) 0x00, (byte) 0xa4, // ins
			(byte) 0x00, // SBy
			(byte) 0x00, // P2 '00'
			(byte) 0x02, // Lc
			(byte) 0x00, // Data
			(byte) 0x00, //
			(byte) 0x00 };// Le
	
	/** selectFile активизирует файл 
	 * example  selectFile(0,"DFF1") <br>
	 * example selectFile(0,"0001")
	 */
	public byte[] selectFile(int sBy, String DataID) throws UnknownCardException, NoSuchFeature, SmartCardReaderException, InvalidResponse, NoReadersAvailable {
		byte[] selectFileCardCommand = insertDataSelectFileIntoApdu(selectFileApdu, sBy, DataID);
		return this.getSmartCardReader().sendCommand(selectFileCardCommand);
	}
	
	// --------UTILITY - method:begin
	private byte[] insertNumRecIntoApdu(byte[] apdu, int numrec) {
		String hnr = "";
		hnr = Integer.toString(numrec, 16);
		if (hnr.length() == 1)
			hnr = "0" + hnr;
		byte[] newApdu = new byte[apdu.length];
		for (int i = 0; i < newApdu.length; i++)
			newApdu[i] = apdu[i];
		newApdu[2] = (byte) Integer.parseInt(hnr, 16);
		return newApdu;
	}
	
	private byte[] insertLenRecIntoApdu(byte[] apdu, int lenrec) {
		String hnr = "";
		hnr = Integer.toString(lenrec, 16);
		if (hnr.length() == 1)
			hnr = "0" + hnr;
		byte[] newApdu = new byte[apdu.length];
		for (int i = 0; i < newApdu.length; i++)
			newApdu[i] = apdu[i];
		newApdu[4] = (byte) Integer.parseInt(hnr, 16);
		return newApdu;
	}
	
	private byte[] insertDataSelectGetCardData(byte[] apdu, int DataID) {
		String hnr = "";
		hnr = Integer.toString(DataID, 16);
		if (hnr.length() == 1)
			hnr = "0" + hnr;
		byte[] newApdu = new byte[apdu.length];
		for (int i = 0; i < newApdu.length; i++)
			newApdu[i] = apdu[i];
		newApdu[4] = (byte) Integer.parseInt(hnr, 16);
		return newApdu;
	}

	private byte[] insertDataSelectFileIntoApdu(byte[] apdu, int sBy, String DataID) {
		// не все sBy реализованы
		if (sBy == 3) {// Parent DF
			byte[] newApdu = { (byte) 0x00, (byte) 0xa4, // ins
					(byte) 0x03, // SBy
					(byte) 0x00, // P2 '00'
					(byte) 0x00, (byte) 0x00 }; // Lcnew
			return newApdu;
		} else if (sBy == 0 && DataID.length() <= 4) {// File ID
			for (int i = 0; i < (4 - DataID.length()); i++)
				DataID = "0" + DataID;
			int fidBlockOffset = 5;
			byte[] newApdu = new byte[apdu.length];
			for (int i = 0; i < newApdu.length; i++)
				newApdu[i] = apdu[i];
			for (int i = 0; i < DataID.length(); i += 2) {
				newApdu[fidBlockOffset++] = (byte) (Integer.parseInt(DataID.substring(i, i + 2), 16));
			}
			return newApdu;
		}
		return apdu;
	}
	// --------UTILITY - method:end
	

	
	
	// --------UTILITY - data ()
		/** UkrCosCommandsEngine.java */
	public final static byte[] readRecordApdu = { (byte) 0x00, (byte) 0xB2, (byte) 0x04, (byte) 0x04, (byte) 0x00 };
		/** UkrCosCommandsEngine.java */
	public final static byte[] readBinaryCommand = { (byte) 0x00, (byte) 0xB0, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
		/** UkrCosCommandsEngine.java */
		// Команда читает серийный номер, номер версии ОС, данные конфигурации чипа
	public final static byte[] getCardDataApdu = { (byte) 0x80, (byte) 0xF6, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
	   //	 Команда сбасывает состояние карты
	public final static byte[] kmbResetStateApdu = { (byte) 0x80, (byte) 0xE0, (byte) 0x02, (byte) 0x00};
	
	// --------UTILITY - data:end;
}















