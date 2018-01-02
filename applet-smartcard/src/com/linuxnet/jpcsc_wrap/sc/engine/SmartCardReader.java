package com.linuxnet.jpcsc_wrap.sc.engine;

import java.io.Serializable;

import com.linuxnet.jpcsc_wrap.sc.exceptions.InvalidResponse;
import com.linuxnet.jpcsc_wrap.sc.exceptions.NoReadersAvailable;
import com.linuxnet.jpcsc_wrap.sc.interfaces.SmartCardInterface;
import com.linuxnet.jpcsc_wrap.sc.interfaces.SmartCardReaderCommandsInterface;
import com.linuxnet.jpcsc_wrap.sc.interfaces.SmartCardReaderInterface;
import com.linuxnet.jpcsc_wrap.util.TextUtils;

import com.linuxnet.jpcsc.Card;
import com.linuxnet.jpcsc.Context;
import com.linuxnet.jpcsc.PCSC;
import com.linuxnet.jpcsc.State;

public class SmartCardReader implements SmartCardReaderCommandsInterface,
										SmartCardReaderInterface,
										Serializable{

	private void debug(String information){
		System.out.println("SmartCardReader DEBUG:"+information);
	}
	private void error(String information){
		System.out.println("SmartCardReader ERROR:"+information);
	}
	
	private SmartCardInterface smartcard;

	/** контекст в котором осуществляется общение со смарт картой*/
	private Context ctx;

	/** карта, к которой осуществляется соединение */
	private Card card;

	/** текущее состояние карты*/
	private State state;

	/** используемый Reader*/
	private int usingReaderNr = -1;

	/** имена всех Reader-ов либо же всех зарегистрированных в системе, либо же всех доступных*/
	private String[] readerNames;

	/** текущее имя самого Reader-а*/
	private String currentReaderName = null;

	/**
	 * Уровень отладки
	 * debugLevel = 0: no debug information; 1: minimal debug information
	 * (reader and card information); 2: maximal debug information (apdus)
	 */
	private int debugLevel = 0;

	/** установить уровень отладки */
	public void setDebugLevel(int dbg) {
		debugLevel = dbg;
	}

	/** послать команду в карту 
	 * @param command для посылки данных в карту 
	 * @return возвращает ответ устройства
	 */
	public byte[] sendCommand(byte[] command) throws InvalidResponse {
		// если карта не задана - задать карту
		// TODO: sendCommand - можно возобновить работу блока
/*		if (card == null) {
			card = connectToCard();
		}*/
		// служебная информация
		if (debugLevel > 1){
			System.err.println("Sending <" + TextUtils.hexDump(command)+ "> to card in reader <" + state.szReader + ">");
		}
		// послать команду и получить ответ 
		byte[] response = card.Transmit(command, 0, command.length);
		// служебная информация
		if (debugLevel > 1){
			System.err.println("Receiving data <" + TextUtils.hexDump(response)+ ">");
		}
		return response;
	}

	/** произвести соединение с картой */
	private Card connectToCard() {
		return ctx.Connect(state.szReader, 
						   PCSC.SHARE_SHARED, PCSC.PROTOCOL_T0| PCSC.PROTOCOL_T1);
	}

	/** получить атрибуты состояния */
	public byte[] getATR() {
		return state.rgbAtr;
	}


	/** получить текущее имя Reader'a */
	public String getMyName() {
		return currentReaderName;
	}
	
	/** получить список всех доступных Reader-ов, либо список подключенных Reader-ов*/
	public String[] getDevices(){
		if(this.readerNames==null){
			Context temp_context=new Context();
			temp_context.EstablishContext(PCSC.SCOPE_SYSTEM, null, null);
			this.readerNames=temp_context.ListReaders();
		}
		return this.readerNames;
	}
	/** получить все имена групп подключенных Reader-ов*/
	public String[] getDevicesGroup(){
		if(this.readerNames==null){
			Context temp_context=new Context();
			temp_context.EstablishContext(PCSC.SCOPE_SYSTEM, null, null);
			this.readerNames=temp_context.ListReaderGroups();
		}
		return this.readerNames;
	}
	
	/**
	 * Присоединение к устройству по его уникальному имени ( можно получить из @see getDevices())
	 * @param deviceName - уникальное имя устройства 
	 * @return - true, если соединение прошло успешно
	 * @throws NoReadersAvailable - если соединение не осуществлено
	 */
	public boolean connectToDeviceName(String deviceName) throws NoReadersAvailable{
		boolean return_value=false;
		debug("connectToDeviceName: Create context");
		ctx=new Context();
		debug("connectToDeviceName: connect to :"+deviceName);
		try{
			ctx.EstablishContext(PCSC.SCOPE_SYSTEM, null, null);
			this.card=ctx.Connect(deviceName);
		}catch(Exception ex){
			ctx=null;
			error("connectToDeviceName ERROR:"+ex.getMessage());
		}
		
		if(ctx==null){
			error("connectToDeviceName: Context is null");
			throw new NoReadersAvailable();
		}else{
			debug("connectToDeviceName: CONNECTED");
			return_value=true;
		}
		return return_value;
	}
	
	/**
	 * Ищет устройство в списке доступных и подключается к нему 
	 * точка подключения - (Context)ctx 
	 * @param preferredReader - имя Reader-а, которое необходимо будет искать в списке
	 * @param milliSecondsBeforeTryingAnotherReader  - время ожидания ответа для поиска следующего устройства в списке доступных устройств
	 */
	public void lookForSmartCard(String preferredReader,
								 int milliSecondsBeforeTryingAnotherReader)
				throws NoReadersAvailable {
		// получение контекста
		ctx = new Context();
		// установить доступными только те, которые подключены в данный момент
		ctx.EstablishContext(PCSC.SCOPE_SYSTEM, null, null);
		// получить список всех Reader-ов
		readerNames = ctx.ListReaders();
		
		// проверка на наличие Reader-ов в системе
		if (readerNames.length == 0){
			// устройства не зарегестрированы - выбрасываем исключение
			throw new NoReadersAvailable();
		}
		// Вывод служебной информации, если она нужна
		for (int i = 0; i < readerNames.length; i++){
			if (debugLevel > 0){
				System.err.println("Discovered smart card reader <"+ readerNames[i] + "> as reader <" + i + ">");
			}
		}
		// получить номер текущего Reader-а
		usingReaderNr = 0;
		
		preferredReader = preferredReader.toUpperCase();
		for (int i = 0; i < readerNames.length; i++){
			if (readerNames[i].toUpperCase().indexOf(preferredReader) >= 0){
				usingReaderNr = i;
			}
		}
		// вывести служебную информацию о текущем Reader-e
		if (debugLevel > 0){
			System.err.println("Using smart card reader <" + usingReaderNr+ ">, <" + readerNames[usingReaderNr]+ ">, preferred reader was <" + preferredReader + ">");
		}
		// получить состояние 
		state = null;
		do {
			if (debugLevel > 0){
				System.err.println("Waiting for a card to be inserted into smart card reader <"+ usingReaderNr+ ">, <"+ readerNames[usingReaderNr]+ ">, will timeout in <"+ milliSecondsBeforeTryingAnotherReader+ "> milliseconds");
			}
			State[] rsa = new State[1];
			rsa[0] = new State(readerNames[usingReaderNr]);
			ctx.GetStatusChange(250, rsa);
			if ((rsa[0].dwEventState & PCSC.STATE_PRESENT) == PCSC.STATE_PRESENT) {
				state = rsa[0];
			} else {
				state = null;
			}

			/*
			 * the following line works for jpcsc 0.8.0, but not for 0.7 //
			 * state =
			 * ctx.WaitForCard(readerNames[usingReaderNr],milliSecondsBeforeTryingAnotherReader);
			 */
			if (state == null) {
				usingReaderNr++;
				if (usingReaderNr >= readerNames.length){
					usingReaderNr = 0;
				}
				if (debugLevel > 0){
					System.err.println("Trying again with smart card reader <"+ usingReaderNr + ">, <"+ readerNames[usingReaderNr]+ "> as no card was detected within <"+ milliSecondsBeforeTryingAnotherReader+ "> milliseconds");
				}
			}
		} while (state == null);
		if (debugLevel > 0){
			System.err.println("Discovered card to <" + state.szReader + ">");
		}
		if (debugLevel > 0){
			System.err.println("Card ATR is <"+ TextUtils.hexDump(state.rgbAtr) + ">");
		}
		currentReaderName = readerNames[usingReaderNr];
	}
	/** освободить контекст*/
	public void powerOff() {
		ctx.ReleaseContext();
	}

	public void finalize() {
		/** освободить контекст*/
		powerOff();
	}

	/** получить группу байт, которые указывают на смену PIN */
	private byte[] _ChangePINCommand = {
		// change pin vasco dp850 -- begin
		 (byte) 0xf1, // cla: reader commands
		 (byte) 0x95, // ins: local pin change
		 (byte) 0x70, //  p1: local pin change
		 (byte) 0xf1, //  p2: full mask is present
		 (byte) 0x18, //  p3: length of data sent to the smartcard reader
		 (byte) 0x46, // between 4 and 6 digits are allowed
		 (byte) 0x00, // cla: change pin command
		 (byte) 0x24, // ins: change pin command
		 (byte) 0x00, //  p1: change pin command
		 (byte) 0x01, //  p2: change pin command
		 (byte) 0x10, //  p3: change pin command, 16 bytes will follow
		 (byte) 0x03, // pin is in bcd format
		 (byte) 0x00, // no watchdog
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

	/** получить группу байт, которая указывает на проверку PIN */
	private byte[] _VerifyPINCommand = {
		// verify pin vasco dp850 -- begin
		 (byte) 0xf1, // cla: verify pin for reader
		 (byte) 0x95, // ins: local verify pin
		 (byte) 0x30, //  p1: local pin entry
		 (byte) 0xf1, //  p2: full mask follows
		 (byte) 0x10, //  p3: 12 data bytes follow
		 (byte) 0x46, // between 4 and 6 digits are allowed
		 (byte) 0x00, // cla: verify pin for smartcard
		 (byte) 0x20, // ins: verify pin
		 (byte) 0x00, //  p1: verify pin
		 (byte) 0x01, //  p2: verify pin
		 (byte) 0x08, //  p3: 8 pin block bytes follow
		 (byte) 0x03, // pin is in bcd format
		 (byte) 0x00, // no watchdog
		 (byte) 0x24, // pin block byte 1
		 (byte) 0xFF, // pin block byte 2
		 (byte) 0xFF, // pin block byte 3
		 (byte) 0xFF, // pin block byte 4
		 (byte) 0xFF, // pin block byte 5
		 (byte) 0xFF, // pin block byte 6
		 (byte) 0xFF, // pin block byte 7
		 (byte) 0xFF, // pin block byte 8
	};

	private byte[] _PollingCommand = {
		// polling end of verify pin vasco dp850 -- end
		 (byte) 0xf1, (byte) 0x61, (byte) 0x00, (byte) 0x00, (byte) 0x04 };

	private byte[] _GetReaderResultCommand = {
		// result vasco dp850 reader command -- begin
		 (byte) 0xf1, (byte) 0x93, (byte) 0x30, (byte) 0x00, (byte) 0x02 };

	private byte[] _ResetReaderCommand =
		{(byte) 0xf1, (byte) 0x63, (byte) 0x00, (byte) 0x00, (byte) 0x04 };

	/** проверка PIN */
	public final void verifyPIN() {
		byte[] result = new byte[2];
		System.err.println("Secure PIN Entry -- Please enter your PIN on the Digipass 850 reader");
		boolean tryAgain = true;
		while (tryAgain)
			try {
				result = sendCommand(_VerifyPINCommand);

				byte[] pollResult = null;
				int timeoutCounter = 100;

				do {
					Thread.sleep(250);
					pollResult = sendCommand(_PollingCommand);
				} while (
					timeoutCounter-- > 0
						&& TextUtils.hexDump(pollResult).equals("3635B0009000"));
				// loop while we are in local mode or have timed out

				if (0 == timeoutCounter) {
					System.err.println(
						"Error -- The Digipass 850 reader did not respond to the PIN verify command within the predefined timeframe");
					sendCommand(_ResetReaderCommand);
					return;
				}

				if (!TextUtils.hexDump(pollResult).equals("363560009000")) {
					System.err.println(
						"Error -- The Digipass 850 reader did not respond to the PIN verify command within the predefined timeframe");
					sendCommand(_ResetReaderCommand);
					return;
				}
				result = sendCommand(this._GetReaderResultCommand);
				tryAgain = analyzePINVerifyResult(result);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}

	private boolean analyzePINVerifyResult(byte[] result) {
		boolean tryAgain = true;
		String caption = "Error Secure PIN verification";
		String text = "";
		//
		String resultString = TextUtils.hexDump(result, 0, 4);
		if (resultString.equals("63C29000")) {
			text = "You provided a WRONG PIN. You have 2 PIN tries left";
		} else if (resultString.equals("63C19000")) {
			text = "You provided a WRONG PIN. You have 1 PIN try left";
		} else if (resultString.equals("63C09000")) {
			text =
				"You provided a WRONG PIN. Your eID card has now been blocked.\n"
					+ "Call the eID Helpdesk (or go to your municipality) to get instructions on how to deblock your eID card.";
			tryAgain = false;
		} else if (resultString.equals("FFFF9000")) {
			text = "Secure PIN verification has been cancelled.";
			tryAgain = false;
		} else if (resultString.equals("90009000")) {
			text = "Secure PIN verification succeeded";
			caption = "Info";
			tryAgain = false;
		} else {
			text =
				"The eID card provided an unknown returncode ("
					+ resultString
					+ ") to the PIN verification command";
			tryAgain = false;
		}
		System.err.println(caption + " -- " + text);
		return tryAgain;
	}

	public final void changePIN() {
		byte[] result = new byte[2];
		System.err.println(
			"Secure PIN Change -- Please enter your current PIN on the Digipass 850 reader");
		boolean tryAgain = true;
		while (tryAgain)
			try {
				result = sendCommand(_ChangePINCommand);
System.err.println("result after change pin command <" +TextUtils.hexDump(result)+	">");
				byte[] pollResult = null;
				int timeoutCounter = 100;

				do {
					Thread.sleep(250);
					pollResult = sendCommand(_PollingCommand);
					System.err.println("polling result <" +TextUtils.hexDump(pollResult)+
						">");
				} while (
					timeoutCounter-- > 0
						&& TextUtils.hexDump(pollResult).equals("3635B0009000"));
				// loop while we are in local mode or have timed out

				System.err.println("result after polling <" +TextUtils.hexDump(result)+
					">");
				if (0 == timeoutCounter) {
					System.err.println(
						"Error -- The Digipass 850 reader did not respond to the PIN change command within the predefined timeframe");
result=					sendCommand(_ResetReaderCommand);
	System.err.println("result after reset reader, due to timeout counter = 0 <" +TextUtils.hexDump(result)+
">");
			return;
				}

				if (!TextUtils.hexDump(pollResult).equals("363560009000")) {
					System.err.println(
						"Error -- The Digipass 850 reader did not respond to the PIN change command within the predefined timeframe");
result=					sendCommand(_ResetReaderCommand);
	System.err.println("result after reset reader, due to timeout exceeded <" +TextUtils.hexDump(result)+
">");
			return;
				}
				result = sendCommand(this._GetReaderResultCommand);
				System.err.println("result after get reader result <" +TextUtils.hexDump(result)+
					">");
				tryAgain = analyzePINChangeResult(result);
				System.err.println("tryagain <" +tryAgain+
					">");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}

	private boolean analyzePINChangeResult(byte[] result) {
		boolean tryAgain = true;
		String caption = "Error in Secure PIN change";
		String text = "";
		//
		String resultString = TextUtils.hexDump(result, 0, 4);
		if (resultString.equals("63C29000")) {
			text = "You provided a WRONG PIN. You have 2 PIN tries left";
		} else if (resultString.equals("63C19000")) {
			text = "You provided a WRONG PIN. You have 1 PIN try left";
		} else if (resultString.equals("63C09000")) {
			text =
				"You provided a WRONG PIN. Your eID card has now been blocked.\n"
					+ "Call the eID Helpdesk (or go to your municipality) to get instructions on how to deblock your eID card.";
			tryAgain = false;
		} else if (resultString.equals("FFFF9000")) {
			text = "Secure PIN change has been cancelled.";
			tryAgain = false;
		} else if (resultString.equals("90009000")) {
			text = "Secure PIN change succeeded";
			caption = "Info";
			tryAgain = false;
		} else {
			text =
				"The eID card provided an unknown returncode ("
					+ resultString
					+ ") to the PIN change command";
			tryAgain = false;
		}
		System.err.println(caption + " -- " + text);
		return tryAgain;
	}

}
