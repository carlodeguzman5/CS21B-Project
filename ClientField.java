import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ClientField extends Canvas implements KeyListener{
	
	public ArrayList<Brick> bricks;
	public static TankOther p1,p2,p3,p4;
	public static ArrayList<PowerUp> powerAl;
	public ArrayList<TankOther> oTanks;
	Tank myTank;
	public int playerNumber;
	private InputStreamReader in;
	public PrintStream out;
	int speed;
	private int brickH;
	private int brickW;
	int conCount, deadCount;
	public boolean shot,shot1,shot2,shot3,shot4,isCon1, isCon2, isCon3,isCon4 ;
	public static String name;
	JTextField cdLabel, armLabel, ammoLabel;
	Bullet b;
	Thread coolThread;
	JFrame win;
	Bullet myBullet;
	BulletOther  bullet1, bullet2, bullet3, bullet4;
	Scanner input;
	Timer timer;
	AmmoUp power;
	JTextArea text;
	JScrollPane pane;
	Clip clip, fireSound, exSound, upSound;
	boolean dead1,dead2,dead3,dead4;

	boolean showPowerUp;

	public ClientField(int y, PrintStream ps, InputStreamReader isr, String un) throws Exception{ // players, playerNumber, team
		
		JFrame console = new JFrame();
		console.setSize(350,400);
		console.setLocation(1000, 100);
		console.setDefaultCloseOperation(2);
		console.setVisible(true);
		console.setLayout(new FlowLayout());
		console.setAlwaysOnTop(true);
		console.setResizable(false);
		console.setTitle("What iz happening?");
		console.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		text = new JTextArea (22,28);
		text.setEditable(false);
		pane = new JScrollPane(text);
		console.add(pane, "Center");
		
		playerNumber = y;
		out = ps;
		in = isr;
		name = un;
		input = new Scanner(in);
		timer = new Timer();
		deadCount = 0;
		conCount = 1;
		
		isCon1 = false;
		isCon2 = false;
		isCon3 = false;
		isCon4 = false;
	
		if (y==2){
			isCon1=true;
			conCount=2;
		}
		if (y==3){
			isCon1=true;
			isCon2=true;
			conCount=3;

		}
		if(y==4){
			conCount=4;
			isCon1=true;
			isCon2=true;
			isCon3=true;
		}
		oTanks = new ArrayList<TankOther>();

		setBackground(Color.BLACK);
		if (y == 1){
			 
			myTank = new Tank(1, ps, this);
			p2 = new TankOther(2, isr, this);
			p3 = new TankOther(3, isr, this);
			p4 = new TankOther(4, isr, this);
			
			oTanks.add(p2);
			oTanks.add(p3);
			oTanks.add(p4);
			
		}
		else if (y == 2){
			p1 = new TankOther(1, isr, this);
			myTank = new Tank(2, ps, this);
			p3 = new TankOther(3, isr, this);
			p4 = new TankOther(4, isr, this);
			
			oTanks.add(p1);
			oTanks.add(p3);
			oTanks.add(p4);
		}
		else if (y == 3){
			p1 = new TankOther(1, isr, this);
			p2 = new TankOther(2, isr, this);
			myTank = new Tank(3, ps, this);
			p4 = new TankOther(4, isr, this);
			
			oTanks.add(p1);
			oTanks.add(p2);
			oTanks.add(p4);
		}
		else if (y == 4){
			p1 = new TankOther(1, isr, this);
			p2 = new TankOther(2, isr, this);
			p3 = new TankOther(3, isr, this);
			myTank = new Tank(4, ps, this);
			
			oTanks.add(p1);
			oTanks.add(p2);
			oTanks.add(p3);
		}
		
		myTank.sendConnect();

		addKeyListener(this);
		
		speed = 5;
		
		Thread move = new Thread(new MoveThread(isr, playerNumber, this));
		move.start();

		bricks = new ArrayList<Brick>();
		bricks.add(new Brick(-90,0));
		brickH = bricks.get(0).getHeight();
		brickW = bricks.get(0).getWidth();
		
		//base 1
		bricks.add(new Brick(brickW*4, 0));
		bricks.add(new Brick(brickW*4,brickH));
		bricks.add(new Brick(brickW*4,brickH*2));
		bricks.add(new Brick(brickW*4,brickH*3));		
		bricks.add(new Brick(brickW,brickH*3));
		bricks.add(new Brick(0,brickH*3));
		//base 3
		bricks.add(new Brick(brickW*13,brickH*0));
		bricks.add(new Brick(brickW*13,brickH*1));
		bricks.add(new Brick(brickW*13,brickH*4));
		bricks.add(new Brick(brickW*13,brickH*4));
		bricks.add(new Brick(brickW*14,brickH*4));
		bricks.add(new Brick(brickW*15,brickH*4));
		bricks.add(new Brick(brickW*16,brickH*4));
		bricks.add(new Brick(brickW*17,brickH*4));
		//base 2
		bricks.add(new Brick(brickW*12,brickH*10));
		bricks.add(new Brick(brickW*12,brickH*11));
		bricks.add(new Brick(brickW*12,brickH*12));
		bricks.add(new Brick(brickW*12,brickH*9));
		bricks.add(new Brick(brickW*15,brickH*9));
		bricks.add(new Brick(brickW*16,brickH*9));
		//base 4
		bricks.add(new Brick(brickW*3,brickH*8));
		bricks.add(new Brick(brickW*3,brickH*11));
		bricks.add(new Brick(brickW*3,brickH*12));
		bricks.add(new Brick(brickW*2,brickH*8));
		bricks.add(new Brick(brickW*0,brickH*8));
		bricks.add(new Brick(brickW*1,brickH*8));
		
		bricks.add(new Brick(brickW*8,brickH*2));
		

		bricks.add(new Brick(brickW*9,brickH*3));
		bricks.add(new Brick(brickW*8,brickH*4));
		bricks.add(new Brick(brickW*9,brickH*5));

		bricks.add(new Brick(brickW*8,brickH*6));
		bricks.add(new Brick(brickW*9,brickH*7));
		bricks.add(new Brick(brickW*8,brickH*8));
		
		bricks.add(new Brick(brickW*9,brickH*9));
		bricks.add(new Brick(brickW*8,brickH*10));
		
		cdLabel = new JTextField(myTank.coolDown+" seconds");
		cdLabel.setSize(10, 4);
		cdLabel.setEditable(false);
		
		armLabel = new JTextField(myTank.armor+"");
		armLabel.setSize(10,4);
		armLabel.setEditable(false);
		
		ammoLabel = new JTextField(myTank.weapon+"");
		ammoLabel.setSize(10,4);
		ammoLabel.setEditable(false);
		
		
		win = new JFrame();
		win.setTitle(un+"'s Statistics");
		win.setLayout(new GridLayout(5,2));
		win.add(new JLabel("Name:"));
		win.add(new JLabel(un));
		win.add(new JLabel("Ammunition:"));
		win.add(ammoLabel);
		win.add(new JLabel("Armor:"));
		win.add(armLabel);
		win.add(new JLabel("Turret is ready in"));
		win.add(cdLabel);
		win.setLocation(1000, 550);
		win.setVisible(true);
		win.setSize(400,200);
		win.setResizable(false);
		win.setAlwaysOnTop(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Thread rThread = new Thread(new Repainter(this));
		rThread.start();	
		coolThread = new Thread(new CDThread(this));
		coolThread.start();	
		powerAl = new ArrayList<PowerUp>();
		
		//Music
		try {
		     URL url = this.getClass().getClassLoader().getResource("bgm.wav");
		     AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
		     clip = AudioSystem.getClip();
		     clip.open(audioIn);
		     clip.start();
		     clip.loop(clip.LOOP_CONTINUOUSLY);
		     
		     url = this.getClass().getClassLoader().getResource("fire.wav");
		     audioIn = AudioSystem.getAudioInputStream(url);
		     fireSound = AudioSystem.getClip();
		     fireSound.open(audioIn);
		     fireSound.setMicrosecondPosition(0);
		     fireSound.setFramePosition(0);
		     
		     url = this.getClass().getClassLoader().getResource("explode.wav");
		     audioIn = AudioSystem.getAudioInputStream(url);
		     exSound = AudioSystem.getClip();
		     exSound.open(audioIn);
		     exSound.setFramePosition(0);
		     
		     url = this.getClass().getClassLoader().getResource("power.wav");
		     audioIn = AudioSystem.getAudioInputStream(url);
		     upSound = AudioSystem.getClip();
		     upSound.open(audioIn);
		     upSound.setFramePosition(0);
		     
		     
		  } catch (UnsupportedAudioFileException e) {
		     e.printStackTrace();
		  } catch (IOException e) {
		     e.printStackTrace();
		  } catch (LineUnavailableException e) {
		     e.printStackTrace();
		  }
		
		
	}
	
	public void paint (Graphics g){ 
		Graphics gra;
		BufferedImage offscreen = null;
		Dimension d = new Dimension(900,700);
		offscreen = (BufferedImage) createImage(d.width, d.height);
		gra = offscreen.getGraphics();
		gra.setColor(getBackground());
		gra.fillRect(0, 0, d.width, d.height);
		gra.setColor(getForeground());
		
		Image bg = null;
		try {
			bg = ImageIO.read(getClass().getResource("bg.png"));
		} catch (IOException e1) {}
		gra.drawImage(bg,0,0,null);
		
		try{
			
			if (playerNumber == 1){
				myTank.draw(gra);
				if (isCon2)
				p2.draw(gra);
				if(isCon3)
				p3.draw(gra);
				if(isCon4)
				p4.draw(gra);
			}
			else if (playerNumber == 2){
				if(isCon1)
				p1.draw(gra);
				myTank.draw(gra);
				if(isCon3)
				p3.draw(gra);
				if(isCon4)
				p4.draw(gra);
			}
			else if (playerNumber == 3){
				if(isCon1)
				p1.draw(gra);
				if(isCon2)
				p2.draw(gra);
				myTank.draw(gra);
				if(isCon4)
				p4.draw(gra);
			}
			else if (playerNumber == 4){
				if(isCon1)
				p1.draw(gra);
				if(isCon2)
				p2.draw(gra);
				if(isCon3)
				p3.draw(gra);
				myTank.draw(gra);
			}
			
			for(int i = 0; i < bricks.size(); i++)
				bricks.get(i).draw(gra);
			if (shot)myBullet.draw(gra);
			if (shot1)bullet1.draw(gra);
			if (shot2)bullet2.draw(gra);
			if (shot3)bullet3.draw(gra);
			if (shot4)bullet4.draw(gra);
			
			for (int i = 0; i< powerAl.size(); i++)
			powerAl.get(i).draw(gra);
	
			
			g.drawImage(offscreen, 0, 0, this);
		}catch(IOException e){}
	}
	
	@Override
	public void update(Graphics g){
		paint(g);
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		
		int c = key.getKeyCode();
		if (c == KeyEvent.VK_A ){
			if(!isColL()){
				myTank.changePos(new int [] {-speed,0,-speed,0});
				myTank.setTankDir(1);
				for (int i = 0; i<powerAl.size(); i++){
					if (myTank.xpos1a - speed <= powerAl.get(i).xpos + powerAl.get(i).getWidth() && 
						myTank.xpos1a >= powerAl.get(i).xpos && 
						myTank.ypos1a <= powerAl.get(i).ypos + powerAl.get(i).getHeight() && 
						myTank.ypos1a >= powerAl.get(i).ypos-powerAl.get(i).getHeight()){
						myTank.getPow(powerAl.remove(i));
						myTank.remPow(i);
						ammoLabel.setText(myTank.weapon+"");
						armLabel.setText(myTank.armor+"");
	
					}
				}
			}
		}
		else if (c == KeyEvent.VK_W){
			if(!isColU()){
				myTank.changePos(new int [] {0,-speed,0,-speed});
				myTank.setTankDir(2);
				for (int i = 0; i<powerAl.size(); i++){
					if(myTank.ypos1a - speed <= powerAl.get(i).ypos+powerAl.get(i).getHeight() && 
						myTank.ypos1a >= powerAl.get(i).ypos && 
						myTank.xpos1a <= powerAl.get(i).xpos + powerAl.get(i).getWidth() && 
						myTank.xpos1a >= powerAl.get(i).xpos-powerAl.get(i).getWidth()){
						myTank.getPow(powerAl.remove(i));
						myTank.remPow(i);
						ammoLabel.setText(myTank.weapon+"");
						armLabel.setText(myTank.armor+"");
						
					}
				}
			}
		}
		else if (c == KeyEvent.VK_D){
			if(!isColR()){
				myTank.changePos(new int [] {speed,0,speed,0});
				myTank.setTankDir(3);
				for (int i = 0; i<powerAl.size(); i++){
					if(myTank.xpos1a + myTank.getWidth() + speed >= powerAl.get(i).xpos && 
						myTank.xpos1a <= powerAl.get(i).xpos+powerAl.get(i).getWidth() && 
						myTank.ypos1a <= powerAl.get(i).ypos + powerAl.get(i).getHeight() && 
						myTank.ypos1a >= powerAl.get(i).ypos-powerAl.get(i).getHeight()){
						myTank.getPow(powerAl.remove(i));
						myTank.remPow(i);
						ammoLabel.setText(myTank.weapon+"");
						armLabel.setText(myTank.armor+"");
						
					}
				}
				
			}
		}
		else if (c == KeyEvent.VK_S){
			if(!isColD()){
				myTank.changePos(new int [] {0,speed,0,speed});
				myTank.setTankDir(4);
				for (int i = 0; i<powerAl.size(); i++){
					if(myTank.ypos1a + myTank.getHeight() + speed >= powerAl.get(i).ypos && 
						myTank.ypos1a <= powerAl.get(i).ypos+powerAl.get(i).getHeight() && 
						myTank.xpos1a <= powerAl.get(i).xpos + powerAl.get(i).getWidth() && 
						myTank.xpos1a >= powerAl.get(i).xpos-powerAl.get(i).getWidth()){
						myTank.getPow(powerAl.remove(i));
						myTank.remPow(i);
						ammoLabel.setText(myTank.weapon+"");
						armLabel.setText(myTank.armor+"");
						
					}
				}
			}
		}
		if (c == KeyEvent.VK_LEFT){
			myTank.setTurDir(1);
		}
		else if (c == KeyEvent.VK_UP){
			myTank.setTurDir(2);
		}
		else if (c == KeyEvent.VK_RIGHT){
			myTank.setTurDir(3);
		}
		else if (c == KeyEvent.VK_DOWN){
			myTank.setTurDir(4);
		}
		
        if (c == KeyEvent.VK_SPACE){
	        if(myTank.weapon > 0){	
		        if(myTank.coolDown == 0){	
		        	myTank.coolDown = 3;
		        	try {
		        		myBullet = new Bullet(myTank.xpos1a, myTank.ypos1a, myTank.turDir, this);
		        		myBullet.bThread.start();
					} catch (IOException e) {}
					shot = true;
					myTank.weapon--;
					fireSound.setFramePosition(0);
					fireSound.start();
					
					ammoLabel.setText(myTank.weapon+"");			
					out.println(playerNumber + "fire");
					out.flush();
		        }
	        }
        }
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	public boolean isColL(){
		boolean col = false;
		if (myTank.xpos1a <= 0 ){
			col = true;
		}
		for(int i = 0 ; i < bricks.size(); i++){
			if (myTank.xpos1a - speed <= bricks.get(i).xpos+bricks.get(i).getWidth() && 
					myTank.xpos1a >= bricks.get(i).xpos && 
					myTank.ypos1a <= bricks.get(i).ypos + bricks.get(i).getHeight() && 
					myTank.ypos1a >= bricks.get(i).ypos-bricks.get(i).getHeight()){
				col = true;
			}
		}
		for(int i = 0 ; i < oTanks.size(); i++){
			if (myTank.xpos1a - speed <= oTanks.get(i).xpos1a+oTanks.get(i).getWidth() && 
					myTank.xpos1a >= oTanks.get(i).xpos1a &&
					myTank.ypos1a <= oTanks.get(i).ypos1a + oTanks.get(i).getHeight() && 
					myTank.ypos1a >= oTanks.get(i).ypos1a-oTanks.get(i).getHeight()){
				col = true;
			}
		}
		return col;
	}
	public boolean isColU(){
		boolean col = false;
		if (myTank.ypos1a <= 0){
			col = true;
		}
		for (int i = 0; i < bricks.size(); i++){
			if(myTank.ypos1a - speed <= bricks.get(i).ypos+bricks.get(i).getHeight() && 
					myTank.ypos1a >= bricks.get(i).ypos && 
					myTank.xpos1a <= bricks.get(i).xpos + bricks.get(i).getWidth() && 
					myTank.xpos1a >= bricks.get(i).xpos-bricks.get(i).getWidth()){
				col = true;
				
			}	
		}
		for (int i = 0; i < oTanks.size(); i++){
			if(myTank.ypos1a - speed <= oTanks.get(i).ypos1a+oTanks.get(i).getHeight() && 
					myTank.ypos1a >= oTanks.get(i).ypos1a && 
					myTank.xpos1a <= oTanks.get(i).xpos1a + oTanks.get(i).getWidth() && 
					myTank.xpos1a >= oTanks.get(i).xpos1a-oTanks.get(i).getWidth()){
				col = true;
				
			}	
		}
		return col;
	}	
	public boolean isColR(){
		boolean col = false;
		if (myTank.xpos1a + myTank.getWidth() >= 900){
			col = true;
		}
		for (int i = 0; i < bricks.size(); i++){
			if (myTank.xpos1a + myTank.getWidth() + speed >= bricks.get(i).xpos && 
					myTank.xpos1a <= bricks.get(i).xpos+bricks.get(i).getWidth() && 
					myTank.ypos1a <= bricks.get(i).ypos + bricks.get(i).getHeight() && 
					myTank.ypos1a >= bricks.get(i).ypos-bricks.get(i).getHeight()){
				col = true;
				
			}
		}
		for (int i = 0; i < oTanks.size(); i++){
			if (myTank.xpos1a + myTank.getWidth() + speed >= oTanks.get(i).xpos1a && 
					myTank.xpos1a <= oTanks.get(i).xpos1a+bricks.get(i).getWidth() && 
					myTank.ypos1a <= oTanks.get(i).ypos1a + oTanks.get(i).getHeight() && 
					myTank.ypos1a >= oTanks.get(i).ypos1a-oTanks.get(i).getHeight()){
				col = true;
				
			}
		}
		return col;
	}
	public boolean isColD(){
		boolean col = false;
		if (myTank.ypos1a + myTank.getHeight() >= 680){
			col = true;
		}
		for (int i = 0; i < bricks.size(); i++){
			if (myTank.ypos1a + myTank.getHeight() + speed >= bricks.get(i).ypos && 
					myTank.ypos1a <= bricks.get(i).ypos+bricks.get(i).getHeight() && 
					myTank.xpos1a <= bricks.get(i).xpos + bricks.get(i).getWidth() && 
					myTank.xpos1a >= bricks.get(i).xpos-bricks.get(i).getWidth()){
				col = true;
				
			}
		}
		for (int i = 0; i < oTanks.size(); i++){
			if (myTank.ypos1a + myTank.getHeight() + speed >= oTanks.get(i).ypos1a && 
					myTank.ypos1a <= oTanks.get(i).ypos1a+oTanks.get(i).getHeight() && 
					myTank.xpos1a <= oTanks.get(i).xpos1a + oTanks.get(i).getWidth() && 
					myTank.xpos1a >= oTanks.get(i).xpos1a-oTanks.get(i).getWidth()){
				col = true;
				
			}
		}
		return col;
	}
	
	public boolean hasPow(int x, int y){
		boolean pow = false;
		for (int i = 0;  i<powerAl.size(); i++){
			if(powerAl.get(i).xpos == x && powerAl.get(i).ypos == y){
				pow = true;
			}
		}
		return pow;
		
	}
	public void appender(String x){
		text.append(x);
	}
	
	
	public void makePowerUp(String x, double n) throws IOException{
		double space = n;//Math.random()*8;
		int xpos = 0;
		int ypos = 0;
		if ( space <= 1 ){
			xpos = brickW*3;
			ypos = brickH*0;
		}
		else if( space <= 2){
			xpos = brickW*13;
			ypos = brickH*13;
		}
		else if( space <= 3){
			xpos = brickW*16;
			ypos = brickH*3;
		}
		else if( space <= 4){
			xpos = brickW*0;
			ypos = brickH*9;
		}
		else if( space <= 5){
			xpos = brickW*5;
			ypos = brickH*6;
		}
		else if( space <= 6){
			xpos = brickW*11;
			ypos = brickH*6;
		}
		else if( space <= 7){
			xpos = brickW*9;
			ypos = brickH*0;
		}
		else if( space <= 8){
			xpos = brickW*8;
			ypos = brickH*13;
		}
			
		
		if (x.equals("ammo")){
			if (!hasPow(xpos,ypos)){
				powerAl.add(new AmmoUp(xpos,ypos));
			}
		}
		else if (x.equals("armor")){
			if (!hasPow(xpos,ypos)){
				powerAl.add(new ArmorUp(xpos,ypos));
			}
		}
		else if(x.equals("boost")){
			if (!hasPow(xpos,ypos)){
				powerAl.add(new BoostUp(xpos,ypos));
			}
		}
		else if(x.equals("EMP")){
			if(!hasPow(xpos,ypos)){
				powerAl.add(new EmpUp(xpos,ypos));
			}
		}
		else if(x.equals("shield")){
			if(!hasPow(xpos,ypos)){
				powerAl.add(new ShieldUp(xpos,ypos));
			}
		}
		else if(x.equals("slow")){
			if(!hasPow(xpos,ypos)){
				powerAl.add(new SlowDown(xpos,ypos));
			}
		}
		else if(x.equals("stun")){
			if(!hasPow(xpos,ypos)){
				powerAl.add(new StunDown(xpos,ypos));
			}
		}
		else if(x.equals("cd")){
			if(!hasPow(xpos,ypos)){
				powerAl.add(new CdDown(xpos,ypos));
			}
		}
		
	}
	
	public void gameOver(){
		String temp = "";
		if (!myTank.isDead){
			temp = myTank.playerName;
		}
		else if(!p1.isDead && p1.playerName !=null){
			temp = p1.playerName;
		}
		else if(!p2.isDead && p2.playerName !=null){
			temp = p2.playerName;
		}
		else if(!p3.isDead && p3.playerName !=null){
			temp = p3.playerName;
		}
		else if(!p4.isDead && p4.playerName !=null){
			temp = p4.playerName;
		}
		EndScreen ex = new EndScreen(temp);
		ex.setVisible(true);
	}
	
}
	
	