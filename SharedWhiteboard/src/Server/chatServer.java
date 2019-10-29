package Server;
import java.awt.BasicStroke; 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class chatServer {
	private int port = 4396;
	ServerSocket serversocket = null;
	public chatBox chatbox;
	String chatID = "Manager";
	Server server;
	ArrayList<chatThread> chats = new ArrayList<chatThread>();
	//ArrayList<String> usrNames = new ArrayList<String>();
	public chatServer(String chatID,Server server) {
		this.chatID = chatID;
		this.server = server;
		chatbox = new chatBox(chatID, this,server);
		serverThread st = new serverThread();
		st.start();
	}
	
	public void kickInChat(int index) {
		int length = chats.size();
		for(int i=0;i<length;i++) {
			if(chats.get(i).index == index) {
				chats.get(i).sendChat("getOut");
				chats.remove(i);
			}
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
					chatThread ct = new chatThread(clientIndex, chatSocket);
					chats.add(ct);
					ct.start();
					clientIndex ++;
				}
			}catch(IOException e) {
				
			}
		}
	}
	
	public void Update(int index,String info) {
		for(int i=0; i<chats.size(); i++) {
			if(chats.get(i).index == index)
				continue;
			chats.get(i).sendChat(info);
		}
	}
	
	class chatThread extends Thread{
		int index;
		//String usrName;
		Socket socket;
		DataOutputStream output;
		DataInputStream input;
		public chatThread(int index, Socket socket) {
			try {
			this.index = index;
			this.socket = socket;
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		public void sendChat(String info) {
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
					Update(index,str);
				}
			}catch(IOException e) {
				//e.printStackTrace();
			}
		}
	}
	
}
