package Server;

import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.*;

import javax.swing.LayoutStyle.ComponentPlacement;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

//import Client.MainMenu;
import Server.Server.connectionServer;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu extends JFrame {
	//#### edit by szG
	ArrayList<connectionServer> connectionList;
	//#### edit end
	
	public ObjectOutputStream output;
	public ObjectInputStream input;
	public String action="Brush";
	int brushSize=5;
	DrawingBoard board;
	File selectedFile;
	JToolBar toolBar;
	Panel panel_2;
	JMenuItem mntmClose;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainMenu frame = new MainMenu();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public MainMenu(ArrayList<connectionServer> cl) {
		//szG
		connectionList = cl; 

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result=JOptionPane.showConfirmDialog(null, "Do you want to save the changes before exit?","alert",JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					
					try
			        {
						if(selectedFile==null) {
			            JFileChooser chooseDirec = new JFileChooser();
			            chooseDirec.setFileSelectionMode(JFileChooser.FILES_ONLY);
			            chooseDirec.showSaveDialog(getParent());
			            File file = chooseDirec.getSelectedFile();
			            file = new File(file+".spa");
			            
			            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

			            bufferedWriter.close();

			            openFile(file);
			            writeSketchToFile(file);
			            closeFile();
						}
						else {
				            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile));

				            bufferedWriter.close();

				            openFile(selectedFile);
				            writeSketchToFile(selectedFile);
				            closeFile();
				            
							}
						System.exit(0);
						
			        }
			        catch (IOException exception)
			        {
			            System.err.println("Error saving to new file.");
			        }
					
				}
				else if(result==JOptionPane.NO_OPTION) {
				System.exit(0);
				}	
			}
		});
	
		//System.out.print(action);
		
		JFileChooser fileChooser = new JFileChooser();
		 fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        fileChooser.setCurrentDirectory(new File("/Users/Desktop"));
	        fileChooser.setFileFilter(new FileNameExtensionFilter("SPA","spa"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 300, 800, 500);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//board.shapes = new ArrayList();
				//board.shapeFill = new ArrayList();
				//board.shapeStroke = new ArrayList();
				if(!board.shapes.isEmpty() && mntmClose.isEnabled()) {
				int result=JOptionPane.showConfirmDialog(null, "Do you want to save the changes made?","alert",JOptionPane.YES_NO_CANCEL_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					
					try
			        {
						if(selectedFile==null) {
			            JFileChooser chooseDirec = new JFileChooser();
			            chooseDirec.setFileSelectionMode(JFileChooser.FILES_ONLY);
			            chooseDirec.showSaveDialog(mntmNew);
			            File file = chooseDirec.getSelectedFile();
			            file = new File(file+".spa");
			            
			            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

			            bufferedWriter.close();

			            openFile(file);
			            writeSketchToFile(file);
			            closeFile();
			            board.repaint();
						board.shapes = new ArrayList();
						board.shapeFill = new ArrayList();
						board.shapeStroke = new ArrayList();
						}
						else {
				            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile));

				            bufferedWriter.close();

				            openFile(selectedFile);
				            writeSketchToFile(selectedFile);
				            closeFile();
				            
							}
						board.repaint();
						board.shapes = new ArrayList();
						board.shapeFill = new ArrayList();
						board.shapeStroke = new ArrayList();
						//System.exit(0);
						
			        }
			        catch (IOException exception)
			        {
			            System.err.println("Error saving to new file.");
			        }
					
				}
				else if(result==JOptionPane.NO_OPTION) {
					board.repaint();
					board.shapes = new ArrayList();
					board.shapeFill = new ArrayList();
					board.shapeStroke = new ArrayList();
					
				//System.exit(0);
				}
				
				}
				
				
				getContentPane().setVisible(true);
				mntmClose.setEnabled(true);
				
				
				
			}
		});
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mntmNew.setAlignmentX(Component.LEFT_ALIGNMENT);
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(!board.shapes.isEmpty() && mntmClose.isEnabled()) {
					int result=JOptionPane.showConfirmDialog(null, "Do you want to save the changes made?","alert",JOptionPane.YES_NO_CANCEL_OPTION);
					if(result==JOptionPane.YES_OPTION) {
						
						try
				        {
							if(selectedFile==null) {
				            JFileChooser chooseDirec = new JFileChooser();
				            chooseDirec.setFileSelectionMode(JFileChooser.FILES_ONLY);
				            chooseDirec.showSaveDialog(mntmNew);
				            File file = chooseDirec.getSelectedFile();
				            file = new File(file+".spa");
				            
				            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

				            bufferedWriter.close();

				            openFile(file);
				            writeSketchToFile(file);
				            closeFile();
							}
							else {
					            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile));

					            bufferedWriter.close();

					            openFile(selectedFile);
					            writeSketchToFile(selectedFile);
					            closeFile();
					            
								}
							
							//System.exit(0);
							
				        }
				        catch (IOException exception)
				        {
				            System.err.println("Error saving to new file.");
				        }
						
					}
					else if(result==JOptionPane.NO_OPTION) {
						
						
					//System.exit(0);
					}
					
					}
				int returnValue = fileChooser.showOpenDialog(mntmOpen);
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					getContentPane().setVisible(true);
					 loadFile(selectedFile);
					 board.repaint();
					 mntmClose.setEnabled(true);
		             loadElementsFromFile();
		             closeFile();
				  //  //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
				}
				else {
					//System.out.println("No File choosen");
				}
					
			}
		});
		
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try
		        {
					if(selectedFile==null) {
		            JFileChooser chooseDirec = new JFileChooser();
		            chooseDirec.setFileSelectionMode(JFileChooser.FILES_ONLY);
		            chooseDirec.showSaveDialog(mntmSave);
		            File file = chooseDirec.getSelectedFile();
		            file = new File(file+".spa");
		            
		            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

		            bufferedWriter.close();

		            openFile(file);
		            writeSketchToFile(file);
		            closeFile();
					}
					else {
			            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile));

			            bufferedWriter.close();

			            openFile(selectedFile);
			            writeSketchToFile(selectedFile);
			            closeFile();
						}
						
					
		        }
		        catch (IOException exception)
		        {
		            System.err.println("Error saving to new file.");
		        }
			}
		});
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
		        {
		            JFileChooser chooseDirec = new JFileChooser();
		            chooseDirec.setFileSelectionMode(JFileChooser.FILES_ONLY);
		            chooseDirec.showSaveDialog(mntmSave);
		            File file = chooseDirec.getSelectedFile();
		            file = new File(file+".spa");
		            
		            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

		            bufferedWriter.close();

		            openFile(file);
		            writeSketchToFile(file);
		            closeFile();
		        }
		        catch (IOException exception)
		        {
		            System.err.println("Error saving to new file.");
		        }
			}
		});
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result=JOptionPane.showConfirmDialog(null, "Do you want to save the changes before exit?","alert",JOptionPane.YES_NO_CANCEL_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					
					try
			        {
						if(selectedFile==null) {
			            JFileChooser chooseDirec = new JFileChooser();
			            chooseDirec.setFileSelectionMode(JFileChooser.FILES_ONLY);
			            chooseDirec.showSaveDialog(mntmSave);
			            File file = chooseDirec.getSelectedFile();
			            file = new File(file+".spa");
			            
			            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

			            bufferedWriter.close();

			            openFile(file);
			            writeSketchToFile(file);
			            closeFile();
						}
						else {
				            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile));

				            bufferedWriter.close();

				            openFile(selectedFile);
				            writeSketchToFile(selectedFile);
				            closeFile();
				            
							}
						System.exit(0);
						
			        }
			        catch (IOException exception)
			        {
			            System.err.println("Error saving to new file.");
			        }
					
				}
				else if(result==JOptionPane.NO_OPTION) {
				System.exit(0);
				}
			}
		});
		
		 mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result=JOptionPane.showConfirmDialog(null, "Do you want to save the changes before exit?","alert",JOptionPane.YES_NO_CANCEL_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					
					try
			        {
						if(selectedFile==null) {
			            JFileChooser chooseDirec = new JFileChooser();
			            chooseDirec.setFileSelectionMode(JFileChooser.FILES_ONLY);
			            chooseDirec.showSaveDialog(mntmSave);
			            File file = chooseDirec.getSelectedFile();
			            file = new File(file+".spa");
			            
			            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

			            bufferedWriter.close();

			            openFile(file);
			            writeSketchToFile(file);
			            closeFile();
			            getContentPane().hide();
			            mntmClose.setEnabled(false);
						}
						else {
				            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile));

				            bufferedWriter.close();

				            openFile(selectedFile);
				            writeSketchToFile(selectedFile);
				            closeFile();
				            getContentPane().hide();
				            mntmClose.setEnabled(false);
							}
						
						
			        }
			        catch (IOException exception)
			        {
			            System.err.println("Error saving to new file.");
			        }
					
				}
				else if(result==JOptionPane.NO_OPTION) {
					  getContentPane().hide();
					  mntmClose.setEnabled(false);
					  
				}
			}
		});
		mnFile.add(mntmClose);
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		getContentPane().setLayout(new MigLayout("", "[538px]", "[360px][][][]"));
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "flowx,cell 0 0,alignx left,aligny top");
		panel_1.setLayout(new BorderLayout(0, 0));
		
		toolBar = new JToolBar();
		toolBar.setOrientation(SwingConstants.VERTICAL);
		panel_1.add(toolBar);
		
		JButton Brush = new JButton("");
		Brush.setIcon(new ImageIcon(MainMenu.class.getResource("/Images/brushes.jpg")));
		Brush.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.action="Brush";
			}
		});
		Brush.setToolTipText("Brush");
		//Brush.setIcon(new ImageIcon("C:\\Users\\inder\\Desktop\\DS Assignment 2\\brushes.jpg"));
		toolBar.add(Brush);
		
		
		JButton Eraser = new JButton("");
		Eraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.action="Eraser";
			}
		});
		Eraser.setToolTipText("Eraser");
		Eraser.setIcon(new ImageIcon(MainMenu.class.getResource("/Images/Eraser.png")));
		toolBar.add(Eraser);
		
		JButton Line = new JButton("");
		Line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.action="Line";
			}
		});
		Line.setToolTipText("Line");
		Line.setIcon(new ImageIcon(MainMenu.class.getResource("/Images/line.png")));
		toolBar.add(Line);
		
		JButton Circle = new JButton("");
		Circle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.action="Circle";
				
			}
		});
		Circle.setToolTipText("Circle");
		Circle.setIcon(new ImageIcon(MainMenu.class.getResource("/Images/Circle.jpg")));
		toolBar.add(Circle);
		
		JButton Ellipse = new JButton("");
		Ellipse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.action="Ellipse";
			}
		});
		Ellipse.setActionCommand("Ellipse");
		Ellipse.setToolTipText("Ellipse");
		Ellipse.setIcon(new ImageIcon(MainMenu.class.getResource("/Images/Ellipse.png")));
		toolBar.add(Ellipse);
		
		JButton Rectangle = new JButton("");
		Rectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.action="Rectangle";
			}
		});
		Rectangle.setIcon(new ImageIcon(MainMenu.class.getResource("/Images/Rectangle.png")));
		Rectangle.setToolTipText("Rectangle");
		toolBar.add(Rectangle);
		
		JButton Text = new JButton("");
		Text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.action="Text";
			}
		});
		Text.setIcon(new ImageIcon(MainMenu.class.getResource("/Images/text.png")));
		Text.setToolTipText("Text");
		toolBar.add(Text);
		
		//JPanel panel = new JPanel();
		board = new DrawingBoard(connectionList);
		//panel.setBackground(Color.WHITE);
		getContentPane().add(board, "cell 0 0,grow");
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{221, 1, 0};
		gbl_panel.rowHeights = new int[]{1, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		board.setLayout(gbl_panel);

		
		panel_2 = new Panel();
		getContentPane().add(panel_2, "flowx,cell 0 1");
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JLabel lblFillColour = new JLabel("Fill Colour         ");
		lblFillColour.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_2.add(lblFillColour);
		
		Button pink = new Button("    ");
		pink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.fillColor=Color.PINK;
			}
		});
		panel_2.add(pink);
		pink.setBackground(Color.PINK);
		
		Button blue = new Button("    ");
		panel_2.add(blue);
		blue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.fillColor=Color.BLUE;
			}
		});
		blue.setBackground(Color.BLUE);
		
		Button green = new Button("    ");
		green.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.fillColor=Color.GREEN;
			}
		});
		panel_2.add(green);
		green.setBackground(Color.GREEN);
		
		Button lightgray = new Button("    ");
		panel_2.add(lightgray);
		lightgray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.fillColor=Color.LIGHT_GRAY;
			}
		});
		lightgray.setBackground(Color.LIGHT_GRAY);
		
		Button Magenta = new Button("    ");
		Magenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.fillColor=Color.MAGENTA;
			}
		});
		panel_2.add(Magenta);
		Magenta.setBackground(Color.MAGENTA);
		
		Button white = new Button("    ");
		white.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.fillColor=Color.WHITE;
			}
		});
		panel_2.add(white);
		white.setBackground(Color.WHITE);
		
		Button orange = new Button("    ");
		orange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.fillColor=Color.ORANGE;
			}
		});
		panel_2.add(orange);
		orange.setBackground(Color.ORANGE);
		
		Button red = new Button("    ");
		red.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.fillColor=Color.RED;
			}
		});
		panel_2.add(red);
		red.setBackground(Color.RED);
	
		Button gray = new Button("    ");
		gray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.fillColor=Color.GRAY;
			}
		});
		panel_2.add(gray);
		gray.setBackground(Color.GRAY);
		
		Button cyan = new Button("    ");
		cyan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.fillColor=Color.CYAN;
			}
		});
		panel_2.add(cyan);
		cyan.setBackground(Color.CYAN);
		
		Button yellow = new Button("    ");
		yellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.fillColor=Color.YELLOW;
			}
		});
		panel_2.add(yellow);
		yellow.setBackground(Color.YELLOW);
		
		Button darkgray = new Button("    ");
		darkgray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.fillColor=Color.DARK_GRAY;
			}
		});
		panel_2.add(darkgray);
		darkgray.setBackground(Color.DARK_GRAY);
		
		Button black = new Button("    ");
		black.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.fillColor=Color.BLACK;
			}
		});
		panel_2.add(black);
		black.setBackground(Color.BLACK);	
		
		JButton MoreColours;
		MoreColours= setColorButton("More Colours",false);
		getContentPane().add(MoreColours, "cell 0 1");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 2,grow");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblStrokeColour = new JLabel("Stroke Colour  ");
		panel.add(lblStrokeColour);
		
		Button Spink = new Button("  ");
		Spink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.strokeColor=Color.PINK;
			}
		});
		Spink.setActionCommand("");
		Spink.setBackground(Color.PINK);
		panel.add(Spink);
		
		Button Sblue = new Button("  ");
		Sblue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.strokeColor=Color.BLUE;
			}
		});
		Sblue.setBackground(Color.BLUE);
		panel.add(Sblue);
		
		Button Sgreen = new Button("  ");
		Sgreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.strokeColor=Color.GREEN;
			}
		});
		Sgreen.setBackground(Color.GREEN);
		panel.add(Sgreen);
		
		Button Slightgray = new Button("  ");
		Slightgray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.strokeColor=Color.LIGHT_GRAY;
			}
		});
		Slightgray.setBackground(Color.LIGHT_GRAY);
		panel.add(Slightgray);
		
		Button Smagenta = new Button("  ");
		Smagenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.strokeColor=Color.MAGENTA;
			}
		});
		Smagenta.setBackground(Color.MAGENTA);
		panel.add(Smagenta);
		
		Button Swhite = new Button("  ");
		Swhite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.strokeColor=Color.WHITE;
			}
		});
		Swhite.setBackground(Color.WHITE);
		panel.add(Swhite);
		
		Button Sorange = new Button("  ");
		Sorange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.strokeColor=Color.ORANGE;
			}
		});
		Sorange.setBackground(Color.ORANGE);
		panel.add(Sorange);
		
		Button Sred = new Button("  ");
		Sred.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.strokeColor=Color.RED;
			}
		});
		Sred.setBackground(Color.RED);
		panel.add(Sred);
		
		Button Sgray = new Button("  ");
		Sgray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.strokeColor=Color.GRAY;
			}
		});
		Sgray.setBackground(Color.GRAY);
		panel.add(Sgray);
		
		Button Scyan = new Button("  ");
		Scyan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.strokeColor=Color.CYAN;
			}
		});
		Scyan.setBackground(Color.CYAN);
		panel.add(Scyan);
		
		Button Syellow = new Button("  ");
		Syellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.strokeColor=Color.YELLOW;
			}
		});
		Syellow.setBackground(Color.YELLOW);
		panel.add(Syellow);
		
		Button Sdarkgray = new Button("  ");
		Sdarkgray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.strokeColor=Color.DARK_GRAY;
			}
		});
		Sdarkgray.setBackground(Color.DARK_GRAY);
		panel.add(Sdarkgray);
		
		Button Sblack = new Button("  ");
		Sblack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.strokeColor=Color.BLACK;
			}
		});
		Sblack.setBackground(Color.BLACK);
		panel.add(Sblack);
		
		JButton SMoreColours;
		SMoreColours=setColorButton("More Colours",false);
		//SMoreColours.setText("More Colours");
		getContentPane().add(SMoreColours, "cell 0 2");
		
		JLabel Size = new JLabel("Size");
		getContentPane().add(Size, "flowx,cell 0 3");
		
		JSlider slider = new JSlider(5,50,5);
		slider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		board.brushSize=(int) (slider.getValue());
        	}
        });
		getContentPane().add(slider, "cell 0 3");
		//JButton button;
	//	button = setColorButton("More Colours",true);
	//	getContentPane().add(button, "cell 1 1");
	
		
	}
	
	private JButton setColorButton(String buttonText, boolean stroke) {
		JButton MoreColours = new JButton();
		MoreColours.setText(buttonText);
        
		MoreColours.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				if(stroke){
					board.strokeColor = JColorChooser.showDialog(null,  "Pick a Stroke", Color.BLACK);
					//System.out.println(board.strokeColor);
				} else {
					board.fillColor = JColorChooser.showDialog(null,  "Pick a Fill", Color.BLACK);
				}
				
			}
        });
        
        return MoreColours;
	}

	
	    public void writeSketchToFile(File fileName)
	    {
	       try
	        {
	            for (int i=0; i<board.shapes.size(); i++)
	            {
	            //	ArrayList<Color> arrlist =  (ArrayList<Color>) board.shapeStroke.get(i);
	            //    PaintElement elem = (PaintElement) drawPanel.elements.get(i);
	            	output.writeObject(board.shapes.get(i));
	                output.writeObject(board.shapeFill.get(i));
	                output.writeObject(board.shapeStroke.get(i));
	                
	            }
	        }
	        catch ( IOException exception )
	        {
	            System.err.println("Error writing to file.");
	            return;
	        }
	    }
	    public void writeSketchToFile1(File fileName, ObjectOutputStream oos)
	    {
	       try
	        {
	    	   output = oos;
	            for (int i=0; i<board.shapes.size(); i++)
	            {
	            //	ArrayList<Color> arrlist =  (ArrayList<Color>) board.shapeStroke.get(i);
	            //    PaintElement elem = (PaintElement) drawPanel.elements.get(i);
	            	output.writeObject(board.shapes.get(i));
	                output.writeObject(board.shapeFill.get(i));
	                output.writeObject(board.shapeStroke.get(i));
	                
	            }
	        }
	        catch ( IOException exception )
	        {
	            System.err.println("Error writing to file.");
	            return;
	        }
	    }
	    public void loadElementsFromFile()
	    {
	        
	        try
	        {
	            board.shapes.clear();
	            board.shapeFill.clear();
	            board.shapeStroke.clear();
	            while(true)
	            {
	                board.shapes.add((Shape) input.readObject());
	                board.shapeFill.add((Color) input.readObject());
	                board.shapeStroke.add((Color) input.readObject());
	            }
	        }
	        catch (IOException exception)
	        {
	            return;
	        }
	        catch (ClassNotFoundException classNotFoundException)
	        {
	            System.err.println("Unable to create object.");
	        }
	    }

	    public void loadFile(File fileName)
	    {
	        try
	        {
	            input = new ObjectInputStream(new FileInputStream(fileName));
	        }
	        catch(IOException ioException)
	        {
	            System.err.println("Error loading file: "+fileName);
	            return;
	        }
	    }

	    public void openFile(File fileName)
	    {
	        try
	        {
	            output = new ObjectOutputStream(new FileOutputStream(fileName));
	        }
	        catch(IOException ioException)
	        {
	            System.err.println("Error loading file: "+fileName);
	            return;
	        }
	    }

	

	    public void closeFile()
	    {
	        try
	        {
	            if (output != null)
	                output.close();
	        }
	        catch (IOException exception)
	        {
	            System.err.println("Error closing file");
	            System.exit(1);
	        }
	    }
		 
	 		            
}


