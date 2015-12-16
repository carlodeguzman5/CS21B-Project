import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
//import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TimerTask;
import java.util.Timer;
//import java.io.PrintStream;
//import java.net.Socket;
//import java.util.Scanner;
import javax.imageio.ImageIO;


public class TankOther{
	public int direction, armor, damage, playerNumber, coolDown, weapon, centerX, centerY , centerX2, centerY2 ,xpos1a, ypos1a, xpos1b, ypos1b, xoffset, yoffset;
	//private String ipAdd; 
	//private BufferedImage tank;
	public BufferedImage tankImage1a,tankImage1b, image1aD, image1bD,image1aU, image1bU,image1aL, image1bL, image1aR, image1bR;
	int turDir, thingy;
	private int tankDir;
	ClientField field;
	BufferedImage tankEx;
	public boolean isDead, onShield;
	private Timer timer;
	private ExTankTime task;
	public String playerName;
	public TankOther(int player, InputStreamReader isr, ClientField cf) throws InterruptedException{
		
		field = cf;
		playerNumber = player;
		turDir = 4;
		tankDir = 4;
		isDead = false;
		onShield = false;
		
		task = new ExTankTime(this);
		timer = new Timer ();
		
		armor = 5;
		
		playerNumber = player;
		if (player == 1){
			xoffset = 0;
			yoffset = 0;
		}
		else if (player == 2){
			xoffset = 2000;//out of canvas
			yoffset = 2000;//out of canvas
		}
		else if (player == 3){
			xoffset = 2000;//out of canvas
			yoffset = 2500;//out of canvas
		}
		else if (player == 4){
			xoffset = 2000;//out of canvas
			yoffset = 3000;//out of canvas
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
		if(armor <= 0){
			tankImage1b = tankEx;
		}
		else{
			tankImage1a = image1aD;
			tankImage1b = image1bD;
			}
		}
	public void draw (Graphics g){
		if(!isDead){
			if ( tankDir== 1) tankImage1a = image1aL;
			else if( tankDir == 2)tankImage1a = image1aU;
			else if( tankDir == 3)tankImage1a = image1aR;
			else if( tankDir == 4)tankImage1a = image1aD;
			if ( turDir== 1) tankImage1b = image1bL;
			else if( turDir == 2)tankImage1b = image1bU;
			else if( turDir == 3)tankImage1b = image1bR;
			else if( turDir == 4)tankImage1b = image1bD;
			
			g.drawImage(tankImage1a, xpos1a, ypos1a, null);
			g.drawImage(tankImage1b, xpos1b, ypos1b, null);
		}
		else{
			tankImage1a = tankEx;
			tankImage1b = null;
			
			g.drawImage(tankImage1a, xpos1a-tankEx.getWidth()/2, ypos1a-tankEx.getHeight()/2, null);
		}
		
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
		xpos1a += x[0];
		ypos1a += x[1];
		xpos1b += x[2];
		ypos1b += x[3];
	}
	public void setTurDir(int x){
		turDir = x;
	}
	public void setTankDir(int x){
		tankDir = x;
	}	
	public void isHit(){
		if(!onShield){
			armor--;
			field.myTank.hasHit(playerNumber);
			if (armor <= 0){
				isDead = true;
				timer.schedule(task, 2000, 4000);
				String temp;
				
				field.text.append(playerName+ " has been destroyed!\n");
			}
		}
	}
	
	public int getHeight(){
		return tankImage1a.getHeight();
	}
	public int getWidth(){
		return tankImage1a.getWidth();
	}
	public void setShield(){
		onShield = true;
		Timer timer = new Timer();
		timer.schedule(new TaskShield2(this), 10000);
	}
	
}

class ExTankTime extends TimerTask{
	TankOther tank;
	public ExTankTime(TankOther to){
		tank = to;
	}
	@Override
	public void run() {
		try {
			tank.tankEx = ImageIO.read(getClass().getResource("tomb.png"));
		} catch (IOException e) {}
		cancel();
	}
	
}

class TaskShield2 extends TimerTask{
	TankOther tank;
	public TaskShield2 (TankOther t){
		tank = t;
	}
	@Override
	public void run() {
		tank.onShield = false;
		cancel();
	}
}


