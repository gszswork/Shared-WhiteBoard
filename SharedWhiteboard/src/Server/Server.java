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
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Server.Server;
public class Server {
	//Server is the first client who create the carvas
	public int port;
	ServerSocket serversocket = null;
	MainMenu mainmenu;
	chatBox chatbox;
	//private ArrayList<connectionServer> connectionList = new ArrayList<connectionServer>();
	private ArrayList<connectionServer> connectionList = new ArrayList<connectionServer>();
	String ID;
	
	public Server(int port, String id) throws IOException {
		this.ID = id;
		//chatbox = new chatBox(this.ID);
		this.port = port;
		//serverThread st = new serverThread(port);
		mainmenu = new MainMenu(connectionList);
		mainmenu.setVisible(true);
		//st.start();
		new Thread(new serverThread(port)).start();
	}
	
	public void kickInPaint(int index) {
		int length = connectionList.size();
		for(int i=0;i<length;i++) {
			if(connectionList.get(i).ID == index) {
				connectionList.remove(i);
			}
		}
	}
	
	public void Update(int index) {
		//Update for update the painting info for all cliens 
		for(int i=0; i< connectionList.size(); i++) {
			if (connectionList.get(i).ID == index) {
				continue;
			}
			connectionList.get(i).sendPainting();
		}
	}
	

	
	public class serverThread implements Runnable{
		//We'd better to use a thread to handle connection request from other 
		//clients, then handle each connection in another thread.
		int port;
		public serverThread(int port) throws IOException {
			this.port = port;
			serversocket = new ServerSocket(port);
		}
		
		public void run() {
			//System.out.println("Server starts receiving requests.");
			int clientIndex = 0;
			try {
				while(true) {
					//System.out.println(connectionList.size());
					Socket socket = serversocket.accept();
					System.out.println("client "+clientIndex+" request for join in, accept(1)? reject(number not 1)?");
					Scanner scan = new Scanner(System.in);
					int judge = scan.nextInt();
					if(judge ==1) {
						connectionServer cs = new connectionServer(socket,clientIndex);
						connectionList.add(cs);
						cs.start();
						
						Update(-1);
					}else						
						socket.close();
					
					clientIndex ++;
				}

			}catch(Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} 
		}
	}

	
	public class connectionServer extends Thread{
		//This thread is used for handle painting transport beween users.
		String usrName;
		int ID;
		Socket socket;
	    DataInputStream inputFromClient;
	    DataOutputStream outputToClient;
	    
	    ObjectInputStream input;
	    ObjectOutputStream output;
	    //drawMessa: painting from other clients
		public connectionServer(Socket server, int index) throws IOException {
			this.ID = index;
			this.socket = server;			
		}
		public void sendPainting()  {
			int length = mainmenu.board.shapes.size();
			//System.out.println("Send painting, length:" + length);
			try {
				try {
				outputToClient = new DataOutputStream(socket.getOutputStream());
				output = new ObjectOutputStream(socket.getOutputStream());
				outputToClient.writeInt(length);
				}catch(Exception e) {
					
				}
				for (int i=0; i<length; i++) {
					output.flush();
					output.writeObject(mainmenu.board.shapes.get(i));
					output.writeObject(mainmenu.board.shapeFill.get(i));
					output.writeObject(mainmenu.board.shapeStroke.get(i));
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
		}
		public void run() {
			//System.out.println(connectionList.size());
			// In the connectionServer, we need to: 
			// while(true) to receive info from anyClient:
//			receivThread rt = new receivThread(this, inputFromClient,input);
//			rt.start();
			try {
				DataInputStream lengthInput ;
				ObjectInputStream input ;
			
				while(true) {
					lengthInput  = new DataInputStream(socket.getInputStream());
					input = new ObjectInputStream(socket.getInputStream());
					mainmenu.board.loadFromClient(lengthInput.readInt(), input);
					Update(this.ID);
				}
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
		
			
		}
	}
	
}