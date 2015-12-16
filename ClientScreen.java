import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.net.*;
//import javax.swing.*;
//import java.util.Timer;
import java.util.Scanner;



public class ClientScreen extends JFrame{
	
	private JLabel ipLabel;
	private JButton go, quit;
	private JTextField ip;
	String userName;
	ClientRunner r;
	String hostIP;
	Scanner input;


	public ClientScreen(String ip) throws NumberFormatException, Exception{
		
		while(true){
			userName = JOptionPane.showInputDialog("Input Name");
			String a[] = userName.split(" ");
			if(userName.equals("")){
				userName = "WeirdoWithoutName";
				break;
			}
			else if(userName.contains("Abby")){
				userName = "AbbyTheCreator";
				break;
			}
			else if(userName.contains("Carlo")){
				userName = "CarloTheCreator";
				break;
			}
			else if (a.length > 1){}
			else{
				break;
			}
		}
		
		Socket s = new Socket(InetAddress.getLocalHost(), 8000);
		PrintStream out = new PrintStream(s.getOutputStream());
		InputStreamReader in = new InputStreamReader(s.getInputStream());
		input = new Scanner(in);
		String num = input.nextLine();
		r = new ClientRunner(Integer.parseInt(num), out, in, userName);
		dispose();
	}

	public ClientScreen() throws IOException{
		
		while(true){
			userName = JOptionPane.showInputDialog("Input Name");
			String a[] = userName.split(" ");
			if(userName.equals("")){
				userName = "WeirdoWithoutName";
				break;
			}
			else if(userName.contains("Abby")){
				userName = "AbbyTheCreator";
				break;
			}
			else if(userName.contains("Carlo")){
				userName = "CarloTheCreator";
				break;
			}
			else if (a.length > 1){}
			else{
				break;
			}
		}
		
		
		setTitle("Tank Destroyers");
		setLayout(new FlowLayout());
		go = new JButton("Let's Go!");
		quit = new JButton("Surrender");
		ip = new JTextField(10);
		ip.setSize(5, 5);
		ipLabel = new JLabel("Enter Host's IP Address");
		add (ipLabel);
		add (ip);
		add(go);
		add(quit);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		quit.addActionListener(new ExitListener());
		go.addActionListener(new GoListener(this));
		
		setSize(300,120);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}
	class GoListener implements ActionListener{
		JFrame frame;
		private Scanner input;
		private Socket s;
		public GoListener(JFrame f){
			frame = f;
		}
		
	public void actionPerformed(ActionEvent ae) {
		hostIP = ip.getText();
		try{
			s = new Socket(hostIP,8000);
			go.setEnabled(false);
			
			PrintStream out = new PrintStream(s.getOutputStream());
			InputStreamReader in = new InputStreamReader(s.getInputStream());
			input = new Scanner(in);
			String num = input.nextLine();
			r = new ClientRunner(Integer.parseInt(num), out, in, userName);
			frame.dispose();
	
			}catch(Exception e){}
		}
	}
	class ExitListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			System.exit(0);
		}
	}

}
