import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.ServerSocket;
import java.io.PrintWriter;

public class EchoServer {

    private static int getPortNumber(String[] args){
        if(args.length>0){
            return Integer.parseInt(args[0]);
        }else{
            return 10001;
        }        
    }

    public static void main(String[] args) throws Exception {
        // read number of the port from first argument
        int port = getPortNumber(args); 
        // create socket
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Started server on port " + port);
            while (true) {    
                // a "blocking" call which waits until a connection is requested
                try(Socket clientSocket = serverSocket.accept();PrintWriter clientSocketOut = new PrintWriter(clientSocket.getOutputStream())){
                    clientSocketOut.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
                }
            }
        }
    }
}
