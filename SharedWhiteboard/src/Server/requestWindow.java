package Server;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Server.Server.serverThread;

import java.awt.BorderLayout;
import javax.swing.JButton;

public class requestWindow {

	JFrame frame;
	private int index;
	public boolean accept = false;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 * @throws InterruptedException 
	 */


	public requestWindow(int clientIndex, serverThread st) {
		// TODO Auto-generated constructor stub
		initialize();
		frame.setVisible(true);
		this.index = index;
		try {
			st.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setBounds(600, 400, 392, 158);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewClientWith = new JLabel("New client with index "+ index+" requuest for joining in, accpet?");
		lblNewClientWith.setBounds(31, 21, 355, 16);
		frame.getContentPane().add(lblNewClientWith);
		
		JButton btnAccept = new JButton("accept");
		btnAccept.setBounds(38, 90, 117, 29);
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accept = true;
				frame.setVisible(false);
				notify();
			}
		});
		frame.getContentPane().add(btnAccept);
		
		JButton btnReject = new JButton("reject");
		btnReject.setBounds(227, 90, 117, 29);
		btnReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accept = false;
				frame.setVisible(false);
				notify();
			}
		});
		frame.getContentPane().add(btnReject);
		
	}
}
