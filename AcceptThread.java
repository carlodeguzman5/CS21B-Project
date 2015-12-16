import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class AcceptThread implements Runnable {
	static JButton go;
	static JTextArea connects;
	private int numPlay;
	private static ArrayList <PrintStream> outGoing;
	private static ArrayList <InputStreamReader> recieving;
	public static boolean con1, con2, con3, con4, start, connected;
	private Thread se1, se2, se3, se4;
	static boolean startThread;
	PrintStream out;
	Socket s;
	static int input;
	private ServerSocket ss;
	ServerRunner sr;
	
	public AcceptThread (ServerRunner r) throws IOException{
		sr = r;
		outGoing = new ArrayList<PrintStream>();
		recieving = new ArrayList<InputStreamReader>();
		numPlay = 0;	
		ss = new ServerSocket(8000);
	}
	
	@Override
	public void run() {
		
		while(true){		
			 try{	
			 
			 s = ss.accept();
			 
			 sr.connects.append(s.getInetAddress().getHostAddress()+ " -  has connected." + "\n");
			 
			 out = new PrintStream(s.getOutputStream());
			 outGoing.add(out);
			 InputStreamReader in = new InputStreamReader(s.getInputStream());
			 recieving.add(in);
			 
			 numPlay++;
			 out.println(numPlay+"");
			 out.flush();	 
			 
			 
			 for(int i = 0; i<outGoing.size(); i++){
			 outGoing.get(i).println(numPlay+"con");
			 outGoing.get(i).flush();
			 }
			 if (numPlay == 1){
				 se1 = new Thread(new Send(outGoing,recieving.get(0), 1));
				 se1.start();
			 }
			 if (numPlay == 2){
				 se1.stop();
				 se1 = new Thread(new Send(outGoing,recieving.get(0), 1));
				 se1.start();
				 se2 = new Thread(new Send(outGoing,recieving.get(1), 2));
				 se2.start();
			 }
			 if (numPlay == 3){
				 se1.stop();
				 se2.stop();
				 se1 = new Thread(new Send(outGoing,recieving.get(0), 1));
				 se1.start();
				 se2 = new Thread(new Send(outGoing,recieving.get(1), 2));
				 se2.start();
				 se3 = new Thread(new Send(outGoing,recieving.get(2), 3));
				 se3.start();
			 }
			 if (numPlay == 4){
				 se1.stop();
				 se2.stop();
				 se3.stop();
				 se1 = new Thread(new Send(outGoing,recieving.get(0), 1));
				 se1.start();
				 se2 = new Thread(new Send(outGoing,recieving.get(1), 2));
				 se2.start();
				 se3 = new Thread(new Send(outGoing,recieving.get(2), 3));
				 se3.start();
				 se4 = new Thread(new Send(outGoing,recieving.get(3), 4));
				 se4.start();
			 }	 
			 }catch(Exception e){}
		}		
		
	}
}
