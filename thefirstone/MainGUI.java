package thefirstone;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.IOException;

import filetransfer.*;


public class MainGUI {
	JFrame frame1;
	JButton select;
	JTextField p,opInput;
	public void letsRoll() {
		frame1 = new JFrame("VASScrypt");
		JButton buttonS = new JButton("Send");		//buttonS - Send button in frame1
		buttonS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame2 = new JFrame("Send");			//Sender frame. Because, dialog box doesn't provide much facility
				FlowLayout fLayout = new FlowLayout(FlowLayout.LEFT, 5, 30);
				frame2.setLayout(fLayout);
				JLabel fileExtLabel = new JLabel("Select a File");
				JButton button1 = new JButton("Open");		
				JTextField fileExt = new JTextField(20);
				JLabel passwLabel = new JLabel("Enter the Password");
				JTextField passwText = new JTextField(10);
				JLabel passlLabel = new JLabel("Set Password Length");
				JTextField passlText = new JTextField(5);
				JLabel destLabel = new JLabel("Enter the Destination address");
				JTextField destText = new JTextField(10);
				JLabel portLabel = new JLabel("Enter the Port");
				JTextField portText = new JTextField(5);
				JButton button2 = new JButton("Send");
				/* The details of the objects used
				 * frame 2 - It is the object for 2nd frame which starts upon clicking the "Send" button in frame1
				 * fileExtLabel - For writing the Label field "Select a File"
				 * button1 - For Open button
				 * fileExt - Text field for receiving the path of the file to be sent
				 * passwLabel - For writing the label field "Enter the Password"
				 * passwText - Text field for receiving the Password
				 * passlLabel - For writing the label field "Set Password Length"
				 * passlText - Text field for receiving the password length
				 * destLabel - For writing the label field "Enter the destination address"
				 * destText - Text field for receiving the Destination address
				 * portLabel - For writing the label field "Enter the port"
				 * portText - Text field for receiving the port number
				 */
				button1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser filePath = new JFileChooser();
						filePath.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						int returnVal = filePath.showOpenDialog(button1);
						if(e.getSource() == button1) {
							if (returnVal == JFileChooser.APPROVE_OPTION) {
						        String filePathString = filePath.getSelectedFile().getAbsolutePath();
						        fileExt.setText(filePathString); // Writes the file path into the fileExt Text field
							}
						}
					}
				});
				frame2.add(fileExtLabel);
				frame2.add(button1);
				frame2.add(fileExt);
				frame2.add(passwLabel);
				frame2.add(passwText);
				frame2.add(passlLabel);
				frame2.add(passlText);
				frame2.add(destLabel);
				frame2.add(destText);
				frame2.add(portLabel);
				frame2.add(portText);
				frame2.add(button2);
				
				frame2.setSize(500, 280);
				frame2.setVisible(true);
			}
		});
		JButton buttonR = new JButton("Receive");	//buttonR - Receive button in frame1
		buttonR.addActionListener( new ReceiveListener());
		
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FlowLayout fLayout = new FlowLayout(FlowLayout.CENTER, 0, 200);
	    frame1.setLayout(fLayout);
		frame1.add(buttonS);
		frame1.add(buttonR);
		frame1.setSize(500, 500);
		frame1.setVisible(true);
	}
	
	class ReceiveListener implements ActionListener
	{
		public void actionPerformed ( ActionEvent e)
		{
			JFrame frame3 = new JFrame ("Receive");
			//FlowLayout fLayout = new FlowLayout(FlowLayout.LEFT, 5, 30);
			BorderLayout fLayout = new BorderLayout(5,30);
			frame3.setLayout(fLayout);
			
			//manufacturing Labels for prompting input of data
			JLabel  portPrompt = new JLabel ("Enter port number to listen on:");
			JLabel  filePrompt = new JLabel ("File destination:");
			
			//manufacturing textfields and buttons
			p = new JTextField (5);
			opInput = new JTextField(20);
			select = new JButton ("Select");
			select.addActionListener(new selectListener());
			JButton listen = new JButton ("Listen for any incoming files!");
			listen.addActionListener(new listenListener());
			//Manufacturing panels
			JPanel p1 = new JPanel();
			JPanel p2 = new JPanel();
			
			p1.add(portPrompt);
			p1.add(p);
			p2.add(filePrompt);
			p2.add(select);
			p2.add(opInput);
			frame3.add(p1,BorderLayout.NORTH);
			frame3.add(p2,BorderLayout.CENTER);
			frame3.add(listen,BorderLayout.SOUTH);
			frame3.setSize(500, 280);
			frame3.setVisible(true);
		}
		
		class selectListener implements ActionListener
		{
			public void actionPerformed (ActionEvent e)
			{
				JFileChooser filePath = new JFileChooser();
				filePath.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = filePath.showSaveDialog(select);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
			        String filePathString = filePath.getSelectedFile().getAbsolutePath();
			        opInput.setText(filePathString); // Writes the file path into the fileExt Text field
				}
			}
		}
		
		class listenListener implements ActionListener
		{
			public void actionPerformed (ActionEvent e)
			{
				TranServer ts = new TranServer();
				ts.setPort(Integer.parseInt(p.getText()));
				try
				{
					ts.setOutFile(opInput.getText());
				}
				catch (Exception ae)
				{
					String s = ae.getMessage();
					JOptionPane.showMessageDialog(null,s,"Exception",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				
				try
				{
					ts.makeConnection();
				}
				catch (Exception ae)
				{
					String s = ae.getMessage();
					JOptionPane.showMessageDialog(null,s,"Exception!",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				JOptionPane.showMessageDialog(null,"Listening... PLease click OK.");
				
				try
				{
					JOptionPane.showMessageDialog(null,"Accepted connection. Now starting reception of data. Click OK and wait.");
					ts.getData();
				}
				
				catch (Exception x)
				{
					String s = x.getMessage();
					JOptionPane.showMessageDialog(null,s,"Exception!",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				JOptionPane.showMessageDialog(null, "Data reception complete. Starting extraction. Click OK and wait.");
				String fileName = "INFILE";
				Decompressor dc = new Decompressor();
				try {
		            dc.decompress(new File(fileName));
		        }
		        catch (IOException io) {
		        	JOptionPane.showMessageDialog(null,"Insufficient permissions.","Exception!",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
		        }
				
				JOptionPane.showMessageDialog(null,"Decompression over.");
				String[] pass = pwInput();
				Encoder enc = new Encoder(pass[0],Integer.parseInt(pass[1]));
				try
				{
				enc.setInFileName("OUTPUT");
				enc.setOutFileName(opInput.getText());
				}
				catch (Exception fe)
				{
					String s = fe.getMessage();
					JOptionPane.showMessageDialog(null,s,"Exception!",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				new File(fileName).delete();
				new File("OUTPUT").delete();
				try
				{
					enc.encode();				
				}
				catch (Exception lastOnePhew)
				{
					String s = lastOnePhew.getMessage();
					JOptionPane.showMessageDialog(null,s,"Exception!",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				JOptionPane.showMessageDialog(null, "Decoding is done. Thanks for using VASSCrypt.");
			}
		}
	}
	
	public String[] pwInput()
	{
		String[] pw = new String[2];
		 JLabel labelPassword = new JLabel("Enter password to decode:");
		 JLabel labelConfirmPassword = new JLabel("Enter length of password key:");
		
		 JPasswordField passwordField1 = new JPasswordField(20);
		 JPasswordField passwordField2 = new JPasswordField(20);
		
		 JButton buttonOK = new JButton("OK");
		JFrame passFrame = new JFrame ("Decrypt file");
		passFrame.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
			
		constraints.gridx = 0;
		constraints.gridy = 0;
		passFrame.add(labelPassword, constraints);
			
		constraints.gridx = 1;
		passFrame.add(passwordField1, constraints);
			
		constraints.gridx = 0;
		constraints.gridy = 1;
		passFrame.add(labelConfirmPassword, constraints);
			
		constraints.gridx = 1;
		passFrame.add(passwordField2, constraints);
			
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		passFrame.add(buttonOK, constraints);

			buttonOK.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					char[] password1 = passwordField1.getPassword();
					char[] password2 = passwordField2.getPassword();
					pw[0] = new String(password1);
					pw[1] = new String(password2);
				}
			});
			
			passFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			passFrame.pack();
			passFrame.setLocationRelativeTo(null);
			passFrame.setVisible(true);
			return pw;
		}
	
		public static void main(String[] args) {
		MainGUI gui = new MainGUI();
		gui.letsRoll();
	}
}
