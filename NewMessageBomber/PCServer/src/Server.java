import java.io.*;
import java.net.*;

public class Server {
	public Server() {
		Data data = new Data();
		DataObject dao = new DataObject();
	    ServerSocket serverSocket = null;  
	    try {    
	        serverSocket = new ServerSocket(9999);
	        System.out.println("服务器开启");  
	        while(true){
	        	Socket socket = serverSocket.accept();
	        	System.out.println("连接客户端");  
	        	ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
	        	for(int i=0;i<data.js.length;i++){
	        		dao.setUrl(data.url[i]);
	        		dao.setJs(data.js[i]);
	        		oos.writeObject(dao);
	        		System.out.println("发送了"+dao.getUrl()+"网站的信息");
	        	}
	        }
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
	}
	public static void main(String[] args) {
		new Server();
	}
}