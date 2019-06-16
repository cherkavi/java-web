package jms.listener;

/** интерфейс прослушивания текстовых данных */
public interface ITextDataRecieve {
	
	/**  получено удаленное текстовое сообщение от сервера  
	 * @param remoteTextMessage - текстовое сообщение от удаленного сервера 
	 * */
	public void recieveTextData(String remoteTextMessage);
}
