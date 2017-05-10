package thefirstone;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainGUI {
	JFrame frame1;
	public void letsRoll() {
		frame1 = new JFrame("VASScrypt");
		JButton buttonS = new JButton("Send");
		buttonS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame2 = new JFrame("Send");			//Sender frame. Because, dialog box doesn't provide much facility
				FlowLayout fLayout = new FlowLayout(FlowLayout.LEFT, 5, 30);
				frame2.setLayout(fLayout);
				JLabel fileExtLabel = new JLabel("Select a File");
				JButton button1 = new JButton("Open");
				button1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser filePath = new JFileChooser();
						int returnVal = filePath.showOpenDialog(button1);
						if(e.getSource() == button1) {
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								File file = filePath.getSelectedFile();
							}
						}
					}
				});
				JTextField fileExt = new JTextField(20);
				JLabel passwLabel = new JLabel("Enter the Password");
				JTextField passwText = new JTextField(10);
				JLabel passlLabel = new JLabel("Set Password Length");
				JTextField passlText = new JTextField(5);
				JLabel destLabel = new JLabel("Enter the Destination address");
				JTextField destText = new JTextField(10);
				JLabel portLabel = new JLabel("Enter the Port");
				JTextField portText = new JTextField(5);
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
				
				JButton button2 = new JButton("Send");
				frame2.add(button2);
				
				frame2.setSize(500, 280);
				frame2.setVisible(true);
			}
		});
		JButton buttonR = new JButton("Receive");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FlowLayout fLayout = new FlowLayout(FlowLayout.CENTER, 0, 200);
	    frame1.setLayout(fLayout);
		frame1.add(buttonS);
		frame1.add(buttonR);
		frame1.setSize(500, 500);
		frame1.setVisible(true);
	}
	public static void main(String[] args) {
		MainGUI gui = new MainGUI();
		gui.letsRoll();
	}
}