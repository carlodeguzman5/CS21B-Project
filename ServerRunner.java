import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerRunner extends JFrame{


private JLabel ipLabel;
static JButton go;
private JButton quit;
private JTextField ip;
static JTextArea connects;
private int numPlay;
private JScrollPane pane;
private static ArrayList <PrintStream> outGoing;
private static ArrayList <InputStreamReader> recieving;
public static boolean con1, con2, con3, con4, start, connected;
private Thread se1, se2, se3, se4;
static boolean startThread;
PrintStream out;
Socket s;
Thread aThread;


static int input;
private ServerSocket ss;
public ServerRunner() throws NumberFormatException, Exception{	

			setTitle("Tank Destroyers (Host)");
			setLayout(new FlowLayout());
			setVisible(true);
			setResizable(false);
			setSize(300,400);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLocation( 500, 200);
			
			go = new JButton("    Start Game    ");
			quit = new JButton("      Stop Server      ");
			
			ip = new JTextField(InetAddress.getLocalHost().toString());
			ip.setEditable(false);
			ipLabel = new JLabel("Your IP Address");
			connects = new JTextArea(18,25);
			connects.setEditable(false);
			pane = new JScrollPane(connects);
			
			
			add (ipLabel);
			add(ip);
			add(go);
			add(quit);
			add(pane);
			
			go.addActionListener(new GoListener(this));
			quit.addActionListener(new ExitListener());
			
			aThread = new Thread(new AcceptThread(this));
			aThread.start();
			connects.append("Server has started...\n");
	}

class GoListener implements ActionListener{
	ServerRunner sr;
	public GoListener (ServerRunner s) throws IOException{
		sr = s;
	}
	public void actionPerformed(ActionEvent ae){
		sr.go.setEnabled(false);
		try {
			ClientScreen cs = new ClientScreen(InetAddress.getLocalHost().toString());
			
			
		} catch (Exception e) {}
	}
}	
	class ExitListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			System.exit(0);
		}
	}
}
