import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
/** класс который осуществляет отправку данных на сервлет и получение ответа от сервлета */
public class Sender {
	private static void debug(String value){
		System.out.println("Sender DEBUG:"+value);
	}
	private static void error(String value){
		System.out.println("Sender ERROR:"+value);
	}
	/**
	 * Метод, который передает объект на сервер
	 * @param path полный путь к сервлету
	 * @param transport объект, который нужно передать
	 * @return
	 */
	public static Transport sendTransoportGetAnswer(String path,Transport transport){
		Transport return_value=new Transport();
		try{
			/*
			// URLConnection не работает
			debug("создать URL");
			URL url=new URL(path);
			debug("create URLConnection");
			URLConnection connection=url.openConnection();
			connection.setDoOutput(true);
			debug("connection");
			OutputStream outputstream=connection.getOutputStream();
			debug("create object output");
			ObjectOutputStream object_output=new ObjectOutputStream(outputstream);
			debug("write object");
			object_output.writeObject(transport);
			object_output.flush();
			debug("object_output close");
			object_output.close();
			debug("outputstream close");
			outputstream.close();
			*/
			URL url=new URL(path);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			debug("connection");
			OutputStream outputstream=connection.getOutputStream();
			debug("create object output");
			ObjectOutputStream object_output=new ObjectOutputStream(outputstream);
			debug("write object");
			object_output.writeObject(transport);
			object_output.flush();
			debug("object_output close");
			object_output.close();
			debug("outputstream close");
			outputstream.close();
			debug("connection to URL");
			connection.connect();
			debug("get input stream");
			InputStream inputstream=connection.getInputStream();
			debug("get object input stream");
			ObjectInputStream object_input=new ObjectInputStream(inputstream);
			debug("read object");
			return_value=(Transport)object_input.readObject();
			inputstream.close();
			/*BufferedReader reader=new BufferedReader(new InputStreamReader(inputstream));
			String temp_string;
			while((temp_string=reader.readLine())!=null){
				System.out.println(temp_string);
			}*/
		}catch(IOException ex){
			error("sendTransportGetAnswer IOException:"+ex.getMessage());
		}catch(Exception ex){
			error("sendTransportGetAnswer   Exception:"+ex.getMessage());
		}
		return return_value;
	}
}
