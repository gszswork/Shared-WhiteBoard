package run;

import java.awt.EventQueue; 

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import Client.Client;
import Client.chatClient;
import Client.nameClient;
import Server.Server;
import Server.chatServer;
import Server.nameServer;
public class login {
	private int port;
	private JFrame frame;
	private JTextField textField;
	private JTextField txtID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField("1299");
		textField.setBounds(234, 82, 130, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPort = new JLabel("port:");
		lblPort.setBounds(89, 87, 61, 16);
		frame.getContentPane().add(lblPort);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(89, 51, 61, 16);
		frame.getContentPane().add(lblIp);
		
		JLabel lblLocalhost = new JLabel("localhost");
		lblLocalhost.setBounds(234, 51, 61, 16);
		frame.getContentPane().add(lblLocalhost);
		
		JButton btnStart = new JButton("create/join in");
		btnStart.addActionListener(new ActionListener() {
			//when start button clicked, try to made a connection request to 
			//the port and 'localhost', however, if conncectException happened,
			//which means no server exist, we will create a server based on port.
			public void actionPerformed(ActionEvent e) {
				port = Integer.parseInt(textField.getText());
				try {
					Socket socket = new Socket("localhost",port);
					Client client = new Client(socket, txtID.getText());
					frame.setVisible(false);
					chatClient chatclient = new chatClient(txtID.getText());
					nameClient nc = new nameClient(txtID.getText(),chatclient.chatbox);
				}catch(ConnectException e1) {
					//e1.printStackTrace();
					try {
						Server server = new Server(port, txtID.getText());
						frame.setVisible(false);
						chatServer chatserver = new chatServer(txtID.getText(),server);
						nameServer ns = new nameServer(txtID.getText(),chatserver.chatbox);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnStart.setBounds(247, 215, 117, 29);
		frame.getContentPane().add(btnStart);
		
		JLabel lblIfThePort = new JLabel("If the port exsit then join in, otherwise create");
		lblIfThePort.setBounds(21, 245, 423, 16);
		frame.getContentPane().add(lblIfThePort);
		
		JLabel lblUserNaMe = new JLabel("user name：");
		lblUserNaMe.setBounds(89, 129, 81, 16);
		frame.getContentPane().add(lblUserNaMe);
		
		txtID = new JTextField("");
		txtID.setColumns(10);
		txtID.setBounds(234, 124, 130, 26);
		frame.getContentPane().add(txtID);
	}
}
