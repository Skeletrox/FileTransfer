package thefirstone;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.*;
import filetransfer.TranClient;
import mains.IntegratedClient;


public class MainGUI {

	JFrame frame1, frame2;
	JLabel percentLabel;
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
				percentLabel = new JLabel("0");
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
				frame2.add(percentLabel);
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
