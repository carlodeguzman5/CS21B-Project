
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Send implements Runnable{

	ArrayList <PrintStream> ps;
	InputStreamReader isr; 
	int playNums;
	private Scanner in;
	public boolean sendPow;
	Timer timer;
	MyTask task;
	
	public Send(ArrayList <PrintStream> x, InputStreamReader y, int z){
	ps = x;
	isr = y;
	playNums = z;
	
	task = new MyTask(this);
	timer = new Timer();
	timer.schedule(task, 15000, 5000 );
	}
	
	@Override
	public void run() {
		in = new Scanner(isr);		
		while(in.hasNextLine()){
			if (sendPow){
				sendPow = false;
				double num = Math.random()*100;
				double num2 = Math.random()*8;
				for(int i = 0; i < ps.size(); i++){
					ps.get(i).println(num + " " + num2 + " makepower");
					ps.get(i).flush();
				}
			}
			for(int i = 0; i < ps.size(); i++){
				if(i != playNums-1){
				ps.get(i).println(in.nextLine());
				ps.get(i).flush();
				}
			}					
		}	
	}
}

class MyTask extends TimerTask{
	Send sender;
	MyTask(Send s){
		sender = s;
	}

	@Override
	public void run() {
		
		sender.sendPow = true;
		
	}
	
}
