package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class chatClient {
	private int port = 4396;
	Socket chatSocket;
	public chatBox chatbox;
	String chatID = "Client";
	chatClientThread cct;
	public chatClient(String chatID) {
		cct = new chatClientThread();
		this.chatID = chatID;
		chatbox = new chatBox(chatID,cct);
		cct.start();
		
	}
	class chatClientThread extends Thread{
		int index;
		Socket socket;
		DataOutputStream output;
		DataInputStream input;
		public chatClientThread() {
			try {
				socket = new Socket("localhost", port);
				output = new DataOutputStream(socket.getOutputStream());
				input = new DataInputStream(socket.getInputStream());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void sendChat(String str) throws IOException {
			output.writeUTF(str);
		}
		public void run() {
			while(true) {
				String str;
				try {
					str = input.readUTF();
					chatbox.addToHistory(str);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.print("Maneger clsoe the connection.");
					System.exit(0);
				}
				
			}
		}
	}

	
}


