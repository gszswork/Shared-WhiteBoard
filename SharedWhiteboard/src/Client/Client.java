package Client;

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
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Client {
	boolean exit_ = false;
	String ID;
	private int port;
	Socket socket;
	clientThread ct;
//	public ObjectInputStream input;
	MainMenu mainmenu;
	chatBox chatbox;
	public Client(Socket socket, String id) {
		this.ID = id;
		try {
			this.socket = socket;
			ct = new clientThread(socket);
			mainmenu = new MainMenu(ct);
			mainmenu.setVisible(true);
			ct.start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class clientThread extends Thread{
		ObjectInputStream input;
		Socket socket;
		DataInputStream length;
		DataOutputStream lengthOutput;
		ObjectOutputStream objOutput;
		public clientThread(Socket socket) throws UnknownHostException, IOException {
			this.socket = socket;
//			Socket socketForChat = new Socket("localhost", port);
//			chatConnection = new chatConnection(socketForChat, ID);
//			chatConnection.start();
		}
		public void sendPainting() {
			int len = mainmenu.board.shapes.size();
			try {
				lengthOutput = new DataOutputStream(socket.getOutputStream());
				objOutput = new ObjectOutputStream(socket.getOutputStream());
				lengthOutput.writeInt(len);
				for (int i=0; i<len; i++) {
					objOutput.flush();
					objOutput.writeObject(mainmenu.board.shapes.get(i));
					objOutput.writeObject(mainmenu.board.shapeFill.get(i));
					objOutput.writeObject(mainmenu.board.shapeStroke.get(i));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void run() {
			while(!exit_) {
				try {
					length = new DataInputStream(socket.getInputStream());
					input = new ObjectInputStream(socket.getInputStream());
					int lengthOfShapes = length.readInt();
					mainmenu.board.loadFromServer(lengthOfShapes, input);
					
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					exit_ = true;
					e.printStackTrace();
				}
			}
		}
	}
}
