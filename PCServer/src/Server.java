import java.io.*;
import java.net.*;

public class Server extends Thread{
	public void run() {
		Data data = new Data();
		String sendString = "";
	    ServerSocket serverSocket = null;  
	    try {    
	        serverSocket = new ServerSocket(9999);
	        System.out.println("服务器开启");  
	        while(true){
	        	Socket socket = serverSocket.accept();
	        	System.out.println("连接客户端");  
	        	DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	        	for(int i=0;i<data.js.length;i++){
	        	//	ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
	        		sendString = "";
	        		sendString += data.url[i];
	        		sendString += "*";
	        		sendString += data.js[i];
	        		dos.writeUTF(sendString);
	        		System.out.println("发送了"+sendString+"网站的信息");        		
	        	}
	        	
	        	
	        	
	        }
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
	}
	public static void main(String[] args) {
		new Server().start();
	}
}