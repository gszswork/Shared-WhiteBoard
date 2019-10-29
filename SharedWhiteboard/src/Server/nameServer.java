package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Server.chatServer.chatThread;
import Server.chatServer.serverThread;

public class nameServer {
	private int port = 2200;
	ServerSocket serversocket = null;
	chatBox chatbox;
	String chatID = "Manager";
	
	ArrayList<String> usrNames = new ArrayList<String>();
	ArrayList<nameThread> clientNameConnection = new ArrayList<nameThread>();
	
	public nameServer(String str, chatBox chatbox) {
		this.chatID = str;
		this.chatbox = chatbox;
		serverThread st = new serverThread();
		st.start();
	}
	
	public String getString() {
		int length = clientNameConnection.size();
		String res = "";
		for(int i=0; i< length; i++) {
			res += clientNameConnection.get(i).index + ": "+ clientNameConnection.get(i).str+"\n";
		}
		return res;
	}
	
	public void Update() {
		int length = clientNameConnection.size();
		String str = this.getString();
		for(int i=0; i<length; i++) {
			clientNameConnection.get(i).sendUsrName(str);
		}
	}
	
	class serverThread extends Thread{
		
		public serverThread() {
			try {
				serversocket = new ServerSocket(port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run() {
			int clientIndex = 0;
			try {
				while(true) {
					Socket chatSocket = serversocket.accept();
					DataInputStream input = new DataInputStream(chatSocket.getInputStream());
					String str = input.readUTF();
					//System.out.println(str);
					nameThread ct = new nameThread(clientIndex,str, chatSocket);
					ct.start();
					clientNameConnection.add(ct);
					clientIndex ++;
					Update();
					chatbox.textArea.setText(getString());
				}
			}catch(IOException e) {
				
			}
		}
	}
	
	
	class nameThread extends Thread{
		int index;
		String str;
		//String usrName;
		Socket socket;
		DataOutputStream output;
		DataInputStream input;
		public nameThread(int index, String str, Socket socket) {
			try {
			this.index = index;
			this.str = str;
			this.socket = socket;
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		public void sendUsrName(String info) {
			try {
				output.writeUTF(info);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void run() {
			try {
				while(true) {
					String str = input.readUTF();
					chatbox.addToHistory(str);
				}
			}catch(IOException e) {
				//e.printStackTrace();
				clientNameConnection.remove(this);
				chatbox.textArea.setText(getString());
				Update();
				//如果用户离开了应该在这里会发生IOEXception，我们可以用这个来解决用户离去后的更新
			}
		}
	}
	
	class struct{
		public int index;
		public int name;
	}
}
