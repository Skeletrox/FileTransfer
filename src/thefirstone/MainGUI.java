package thefirstone;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.*;

import filetransfer.Decompressor;
import filetransfer.Encoder;
import filetransfer.TranClient;
import filetransfer.TranServer;
import mains.IntegratedClient;


public class MainGUI {

	JFrame frame1, frame2;
    JButton select;
    JTextField p,opInput;
    String finalName;
    boolean canRun = false;
	public void letsRoll() {
		frame1 = new JFrame("VASSCrypt");
		JButton buttonS = new JButton("Send");		//buttonS - Send button in frame1
		buttonS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame2 = new JFrame("Send");			//Sender frame. Because, dialog box doesn't provide much facility
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
				button2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent sendAction) {
					    String[] data = {fileExt.getText(), passwText.getText(), passlText.getText(), destText.getText(), portText.getText()};
                        try
                        {
                            sendFileThroughClient(data);
                            return;
                        }
                        catch (IOException i)
                        {
                            i.printStackTrace();
                        }
						/*
						Thread t = new Thread(new SendRunner(tC));
						Thread t2 = new Thread(new PercentRunner(tC));
						t.start();
						t2.start();
						*/



					}
				});
				
			}
		});
		JButton buttonR = new JButton("Receive");	//buttonR - Receive button in frame1
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FlowLayout fLayout = new FlowLayout(FlowLayout.CENTER, 0, 200);
	    frame1.setLayout(fLayout);
		frame1.add(buttonS);
		frame1.add(buttonR);
		frame1.setSize(500, 500);
		frame1.setVisible(true);
        buttonR.addActionListener( new ReceiveListener());
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
            finalName = opInput.getText();
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
                    String filePathString = filePath.getSelectedFile().getPath();
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
                String fileName = "INFILE";
                try
                {
                    ts.setOutFile(fileName);
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
                Decompressor dc = new Decompressor();
                try {
                    dc.decompress(new File(fileName));
                }
                catch (IOException io) {
                    JOptionPane.showMessageDialog(null,"Insufficient permissions.","Exception!",JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }

                JOptionPane.showMessageDialog(null,"Decompression over.");

                try
                {

                    //pwInput();
                    String[] pass = new String[2];
                    pass[0] = JOptionPane.showInputDialog("Enter password: ");
                    pass[1] = JOptionPane.showInputDialog("Enter length: ");
                    enc = new Encoder(pass[0],Integer.parseInt(pass[1]));

                        enc.setInFileName("OUTPUT");
                        enc.setOutFileName(opInput.getText());

                }
                catch (Exception fe)
                {
                    String s = fe.getMessage();
                    fe.printStackTrace();
                    JOptionPane.showMessageDialog(null,s,"Exception! XYZ",JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(null,s,"Exception! QRS",JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                JOptionPane.showMessageDialog(null, "Decoding is done. Thanks for using VASSCrypt.");
            }
        }
    }

	String[] pw;
	Encoder enc;

    public String[] pwInput()
    {
        pw = new String[2];
        JLabel labelPassword = new JLabel("Enter password to decode:");
        JLabel labelConfirmPassword = new JLabel("Enter length of password key:");
        JPasswordField passwordField1, passwordField2;
        passwordField1 = new JPasswordField(20);
        passwordField2 = new JPasswordField(20);

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
                getPasswords(passwordField1.getPassword(), passwordField2.getPassword());
                canRun = true;
            }
        });

        passFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        passFrame.pack();
        passFrame.setLocationRelativeTo(null);
        passFrame.setVisible(true);
        return pw;
    }

    void getPasswords(char[] a, char[] b)
    {
        char[] password1 = a;
        char[] password2 = b;
        StringBuilder sb1 = new StringBuilder(), sb2 = new StringBuilder();
        for (char c: password1)
        {
            sb1.append(c);
        }
        for (char c: password2)
        {
            sb2.append(c);
        }
        System.out.println(sb1.toString());
        System.out.println(sb2.toString());
        pw[0] = new String(sb1.toString());
        pw[1] = new String(sb2.toString());
        enc = new Encoder(pw[0],Integer.parseInt(pw[1]));
        for (String s : pw)
        {
            System.out.print(s);
        }
    }
	void sendFileThroughClient(String[] s) throws IOException
    {
       IntegratedClient.main(s);
        JOptionPane.showMessageDialog(frame2,
                "File sent successfully!");
    }
	public static void main(String[] args) {
		MainGUI gui = new MainGUI();
		gui.letsRoll();
	}
}
