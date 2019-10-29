package Client;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import Client.chatClient.chatClientThread;

import java.awt.Color;
import javax.swing.JLabel;

public class chatBox {
	String chatID;
	private JFrame frame;
	//history for showing chat history
	JTextArea history;
	//input for input
	private JTextField input;
	chatClientThread thread;
	private JLabel lblEditorList;
	JTextArea textArea;
	/**
	 * Create the application.
	 */
	public chatBox(String chatID, chatClientThread cct) {
		this.chatID = chatID;
		this.thread = cct;
		initialize();
	}
	public void addToHistory(String str) {
		history.append(str + "\n");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(chatID);
		frame.setBounds(900, 100, 625, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton send = new JButton("send");
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				history.append(chatID + ": "+input.getText()+ "\n");
				try {
					thread.sendChat(chatID + ": " +input.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				input.setText("");
			}
		});
		send.setBounds(281, 452, 57, 29);
		frame.getContentPane().add(send);
		
		input = new JTextField();
		input.setText("input");
		input.setBounds(6, 434, 280, 44);
		frame.getContentPane().add(input);
		frame.setVisible(true);
		input.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setBounds(6, 6, 332, 422);
		frame.getContentPane().add(scrollPane);
		
		history = new JTextArea();
		history.setEditable(false);
		history.setRows(26);
		history.setColumns(27);
		history.setLineWrap(true);
		//history.setWrapStyleWord(true);
		scrollPane.setColumnHeaderView(history);
		
		lblEditorList = new JLabel("editor list:");
		lblEditorList.setBounds(369, 8, 97, 16);
		frame.getContentPane().add(lblEditorList);
		
		textArea = new JTextArea();
		textArea.setBounds(369, 35, 234, 430);
		frame.getContentPane().add(textArea);
	}
}
