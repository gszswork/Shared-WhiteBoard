package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import Client.chatClient.chatClientThread;

public class nameClient {
	private int port = 2200;
	Socket chatSocket;
	chatBox chatbox;
	String chatID = "Client";
	chatClientThread cct;
	public nameClient(String str,chatBox chatbox) {
		nameClientThread nct = new nameClientThread(str);
		this.chatbox = chatbox;
		nct.start();
	}
	
	class nameClientThread extends Thread{
		String usrName ;
		Socket socket;
		DataInputStream input;
		DataOutputStream output;
		public nameClientThread(String name) {
			chatID = name;
			try {
				socket = new Socket("localhost", port);
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
				output.writeUTF(chatID);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run() {
			while(true) {
				try {
					String str = input.readUTF();
					System.out.println(str);
					chatbox.textArea.setText(str);
					//在这里读取了将要展示的字符串
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}


