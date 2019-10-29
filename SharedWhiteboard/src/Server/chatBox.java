package Server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;

public class chatBox {
	String chatID;
	private JFrame frame;
	//history for showing chat history
	JTextArea history;
	//input for input
	private JTextField input;
	chatServer chatserver;
	private JLabel lblTeammate;
	private JTextField textField;
	JTextArea textArea;
	Server server;
	/**
	 * Create the application.
	 */
	public chatBox(String chatID, chatServer cs, Server server) {
		this.chatID = chatID;
		initialize();
		chatserver = cs;
		this.server = server;
	}
	public void addToHistory(String str) {
		history.append(str + "\n");
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(chatID);
		frame.setBounds(1200, 300, 566, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton send = new JButton("send");
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				history.append(chatID + ": "+input.getText()+ "\n");
				chatserver.Update(-1, chatID+": "+input.getText());
				input.setText("");
			}
		});
		send.setBounds(281, 452, 57, 29);
		frame.getContentPane().add(send);
		
		input = new JTextField();
		input.setText("input");
		input.setBounds(6, 440, 280, 38);
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
		
		lblTeammate = new JLabel("editors:");
		lblTeammate.setBounds(366, 8, 111, 16);
		frame.getContentPane().add(lblTeammate);
		
		textArea = new JTextArea();
		textArea.setBounds(366, 25, 179, 400);
		frame.getContentPane().add(textArea);
		//textField is the info to kick
		textField = new JTextField();
		textField.setBounds(366, 446, 130, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("K");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = Integer.parseInt(textField.getText());
				kickoff(index);
			}
		});
		btnNewButton.setBounds(493, 446, 52, 29);
		frame.getContentPane().add(btnNewButton);
	}
	public void kickoff(int index) {
		chatserver.kickInChat(index);
		server.kickInPaint(index);
	}
}
