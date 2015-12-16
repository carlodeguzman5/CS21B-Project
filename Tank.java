import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.MessageFormat.Field;
import java.util.TimerTask;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;


public class Tank{
	int playerNumber, coolDown,weapon,centerX2,centerY2,xpos1a,ypos1a,xpos1b,ypos1b, centerX, centerY, armor;
	private int xoffset, yoffset;
	public BufferedImage tankImage1a,tankImage1b, image1aD, image1bD,image1aU, image1bU,image1aL, image1bL, image1aR, image1bR;
	PrintStream out;
	int turDir;
	private int tankDir;
	public boolean shot;
	public String playerName;
	BufferedImage tankEx;
	boolean isDead;
	public boolean canMove;
	Timer timer;
	TaskBoom task;
	ClientField field;
	boolean onShield;
	
	
	public Tank(int player, PrintStream ps, ClientField cf){
		
		field = cf;
		shot = false;
		isDead = false;
		onShield = false;
		
		weapon = 5;
		armor = 5;
		
		task = new TaskBoom(this);
		timer = new Timer("");
		out = ps;
		playerNumber = player;
		playerName = field.name;
		turDir = 4;
		tankDir = 4;
		canMove = true;
		
		if (player == 1){
			xoffset = 0;
			yoffset = 0;
		}
		else if (player == 2){
			xoffset = 830;
			yoffset = 625;
		}
		else if (player == 3){
			xoffset = 830;
			yoffset = 0;
		}
		else if (player == 4){
			xoffset = 0;
			yoffset = 625;
		}
		
		try{
			image1aR = ImageIO.read(getClass().getResource("tank1aR.png")) ;
			centerX = (image1aR.getWidth(null))/2;
			centerY = (image1aR.getHeight(null))/2;
			image1bR = ImageIO.read(getClass().getResource("tank1bR.png"));
			centerX2 = (image1bR.getWidth(null))/2;
			centerY2 = (image1bR.getHeight(null))/2;
			image1aD = ImageIO.read(getClass().getResource("tank1aD.png"));
			image1bD = ImageIO.read(getClass().getResource("tank1bD.png"));
			image1aL = ImageIO.read(getClass().getResource("tank1aL.png"));
			image1bL = ImageIO.read(getClass().getResource("tank1bL.png"));
			image1aU = ImageIO.read(getClass().getResource("tank1aU.png"));
			image1bU = ImageIO.read(getClass().getResource("tank1bU.png"));
			
			tankEx = ImageIO.read(getClass().getResource("texplosion.png"));
				
		} catch (IOException e){
		}
			
		xpos1a = xpos1a + xoffset;
		ypos1a = ypos1a + yoffset;
		xpos1b = centerX - centerX2 + xoffset;
		ypos1b = centerY - centerY2 + yoffset;
		tankImage1a = image1aD;
		tankImage1b = image1bD;
		}
	
	
	public void draw (Graphics g) throws IOException{
		
		if(!isDead){
			if ( tankDir== 1) tankImage1a = image1aL;
			else if( tankDir == 2)tankImage1a = image1aU;
			else if( tankDir == 3)tankImage1a = image1aR;
			else if( tankDir == 4)tankImage1a = image1aD;
			if ( turDir== 1) tankImage1b = image1bL;
			else if( turDir == 2)tankImage1b = image1bU;
			else if( turDir == 3)tankImage1b = image1bR;
			else if( turDir == 4)tankImage1b = image1bD;
		}
		else{
			tankImage1a = null;
			tankImage1b = tankEx;
		}
		
		g.drawImage(tankImage1a, xpos1a, ypos1a, null);
		g.drawImage(tankImage1b, xpos1b, ypos1b, null);
		
		
	}

	public void moveTurret(int m){
		if (m == 1)tankImage1b = image1bL;
		if (m == 2)tankImage1b = image1bU;
		if (m == 3)tankImage1b = image1bR;
		if (m == 4)tankImage1b = image1bD;
	}

	public int[] getPosition(){
		int [] x = {xpos1a,ypos1a ,xpos1b, ypos1b};
		return x;
	}

	public void setPos(int x[]){
		xpos1a = x[0];
		ypos1a = x[1];
		xpos1b = x[2];
		ypos1b = x[3];
	}
	
	public void changePos(int x[]){
		if(canMove){
			xpos1a += x[0];
			ypos1a += x[1];
			xpos1b += x[2];
			ypos1b += x[3];
		}
	}
	public void setTurDir(int x){
		turDir = x;
	}
	public void setTankDir(int x){
		tankDir = x;
	}
	public void sendUpdate(){	
		String update = playerNumber + " " + xpos1a + " " + ypos1a + " " + turDir + " " + tankDir + " " + field.name ;
		out.println(update);
		out.flush();
	}
	public void stopBullet(){
		String temp = playerNumber + "stop";
		out.println(temp);
		out.flush();
	}
	public void sendConnect(){
		String temp = playerNumber + "con";
		out.println(temp);
		out.flush();
	}
	public int getWidth(){
		return tankImage1a.getWidth(null);
	}
	public int getHeight(){
		return tankImage1a.getHeight(null);
	}
	public int getTurDir(){
		return turDir;
	}
	public int isHit(){
		int temp = 0;
		if (!onShield)
			temp = armor--;
		field.text.append("You have been hit!\n");
		if (armor <= 0){
			isDead = true;
			canMove = false;
			timer.schedule(task, 2000, 4000);
			field.text.append("You have been destroyed!\n");
			out.println(playerNumber+" dead");
			out.flush();
			field.deadCount++;
			System.out.println(field.conCount-1 +" "+ field.deadCount);
			if(field.conCount-1 == field.deadCount){
				field.gameOver();
				System.out.println("gameover");
				out.println("gameover");
				out.flush();
			}
		}
		return temp;
	}
	public void hasHit(int x){
		String temp = "";
		if(x == 1){
			if(field.p1 != null)
				temp = field.p1.playerName;
			else
				temp = field.myTank.playerName;
		}
		else if(x == 2){
			if(field.p2 != null)
				temp = field.p2.playerName;
			else
				temp = field.myTank.playerName;
			
		}
		else if(x == 3){
			if(field.p2 != null)
				temp = field.p3.playerName;
			else
				temp = field.myTank.playerName;
		}
		else if (x == 4){
			if(field.p4 != null)
				temp = field.p4.playerName;
			else
				temp = field.myTank.playerName;
			
		}
		field.text.append("You've hit "+temp+"\n");
		temp = x + "hit";
		out.println(temp);
		out.flush();
	}
	
	public void remPow(int x){
		String temp = x + " removepow";
		out.println(temp);
		out.flush();
	}
	public void sendRemBrick(int x){
		String temp = x + " removeBrick";
		out.println(temp);
		out.flush();
	}
	public void getPow(PowerUp x){
		field.upSound.setFramePosition(0);
		field.upSound.start();
		if (x.name.equals("ammo")) {
			weapon++;		
			field.text.append("You've picked up an ammo-inator powerup!\n");
			String temp = playerNumber+" ammo";
			out.println(temp);
			out.flush();
		}
		else if(x.name.equals("armor")){
			armor++;
			field.text.append("You've picked up an armor-inator powerup!\n");
			String temp = playerNumber+" armor";
			out.println(temp);
			out.flush();
		}
		else if(x.name.equals("boost")){
			field.speed = 20;
			field.text.append("You've picked up a boost-inator powerup!\n");;
			String temp = playerNumber+" boost";
			out.println(temp);
			out.flush();
			Timer timer = new Timer();
			timer.schedule(new TaskSpeed(this), 5000);
			
		}
		else if(x.name.equals("EMP")){
			field.text.append("You've activated an EMP!\n");
			String temp = playerNumber+" EMP";
			out.println(temp);
			out.flush();
		}
		else if (x.name.equals("shield")){
			onShield = true;
			timer.schedule(new TaskShield(this), 10000);
			field.text.append("You've picked up a shield-inator powerup!\n");
			String temp = playerNumber + " shield";
			out.println(temp);
			out.flush();
		}
		else if(x.name.equals("slow")){
			field.speed = 1;
			timer.schedule(new TaskSlow(this), 5000);
			field.text.append("You've picked up a slow-inator powerdown!\n");
			String temp = playerNumber + " slow";
			out.println(temp);
			out.flush();
		}
		else if(x.name.equals("stun")){
			canMove = false;
			coolDown = 5;
			timer.schedule(new TaskStun(this), 5000);
			field.text.append("You've picked up a stun-inator powerdown!\nYou are stunned for 5 seconds!\n");
			String temp = playerNumber + " stun";
			out.println(temp);
			out.flush();
		}
		else if(x.name.equals("cd")){
			coolDown = 10;
			timer.schedule(new TaskStun(this), 5000);
			field.text.append("You've picked up a cooldown extend-inator powerdown!\nTurret is disabled for 10 seconds.\n");
			String temp = playerNumber + " cd";
			out.println(temp);
			out.flush();
		}
		
		else System.out.print("Fail!");
	}
}

class TaskBoom extends TimerTask{
	Tank tank;
	public TaskBoom(Tank t){
		tank = t;
		
	}
	@Override
	public void run() {
		try {
			tank.tankEx = ImageIO.read(getClass().getResource("tomb.png"));
		} catch (IOException e) {}
		cancel();
	}
	
}

class TaskSpeed extends TimerTask{
	Tank tank;
	public TaskSpeed (Tank t){
		tank = t;
	}
	@Override
	public void run() {
		tank.field.speed = 5;
		cancel();
	}
}

class TaskShield extends TimerTask{
	Tank tank;
	public TaskShield (Tank t){
		tank = t;
	}
	@Override
	public void run() {
		tank.onShield = false;
		cancel();
	}
}
class TaskSlow extends TimerTask{
	Tank tank;
	public TaskSlow (Tank t){
		tank = t;
	}
	@Override
	public void run() {
		tank.field.speed = 5;
		cancel();
	}
}
class TaskStun extends TimerTask{
	Tank tank;
	public TaskStun (Tank t){
		tank = t;
	}
	@Override
	public void run() {
		tank.canMove = true;
		cancel();
	}
}




