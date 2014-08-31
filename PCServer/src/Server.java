import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Server extends Thread {
	public void run() {
		Data data = new Data();
		String sendString = "";
		ServerSocket serverSocket = null;
		int seed  = Secret.getSeed(0);
		String password = Integer.toString(Secret.password(seed));
		
		try {
			serverSocket = new ServerSocket(9999);
			System.out.println("服务器开启");
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("连接客户端");
				DataOutputStream dos = new DataOutputStream(
						socket.getOutputStream());
				
		/*		sendString = "finish";
				dos.writeUTF(sendString);
				System.out.println("结束");
			*/	
				for (int j = 0; j < 5; j++) {
					for (int i = 0; i < data.js.length; i++) {
						// ObjectOutputStream oos = new
						// ObjectOutputStream(socket.getOutputStream());
						sendString = "";
						sendString += data.url[i];
						sendString += "*";
						sendString += data.js[i];
						
						String finalString = "";
						try {
							finalString = Secret.enCrypto(sendString, password);
						} catch (InvalidKeyException | InvalidKeySpecException
								| NoSuchPaddingException
								| IllegalBlockSizeException
								| BadPaddingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						dos.writeUTF(finalString);
						System.out.println("发送了" + finalString + "网站的信息");
					}
				}
				sendString = "finish";
				dos.writeUTF(sendString);
				System.out.println("结束");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server().start();
	}
}