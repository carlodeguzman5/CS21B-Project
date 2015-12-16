//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;


public class Bullet{
	int xpos,ypos,turDir;
	BufferedImage missileL, missileU, missileR, missileD, explosion;
	private int movement;
	Thread bThread;
	ClientField field;
	boolean explode;
	Timer timer;
	ExTime task;
	
	public Bullet(int x, int y, int dir, ClientField c) throws IOException{
		xpos = x;
		ypos = y;
		turDir = dir;
		field = c;
		task = new ExTime(this);
		timer = new Timer();		
		missileL = ImageIO.read(getClass().getResource("misL.png"));
		missileU = ImageIO.read(getClass().getResource("misU.png"));
		missileR = ImageIO.read(getClass().getResource("misR.png"));
		missileD = ImageIO.read(getClass().getResource("misD.png"));	
		explosion = ImageIO.read(getClass().getResource("bexplosion.png"));
		bThread = new Thread(new BulletMove(this));
		movement = 0;
		explode = false;
	}
	
	public void draw(Graphics g) throws IOException{	
		if (explode){
			missileL = explosion;
			missileU = explosion; 
			missileR = explosion;
			missileD = explosion;
			
			if ( turDir == 1)g.drawImage(missileL, xpos+movement-explosion.getHeight()/2+10, ypos-explosion.getHeight()/2+10, null);
			else if( turDir == 2)g.drawImage(missileU, xpos-explosion.getHeight()/2+10, ypos+movement-explosion.getHeight()/2+10, null);
			else if( turDir == 3)g.drawImage(missileR, xpos+movement-explosion.getHeight()/2+10, ypos-explosion.getHeight()/2+10, null);
			else if( turDir == 4)g.drawImage(missileD, xpos-explosion.getHeight()/2+10, ypos+movement-explosion.getHeight()/2+10, null);
		}
		else{
			if ( turDir == 1)g.drawImage(missileL, xpos+movement, ypos, null);
			else if( turDir == 2)g.drawImage(missileU, xpos, ypos+movement, null);
			else if( turDir == 3)g.drawImage(missileR, xpos+movement, ypos, null);
			else if( turDir == 4)g.drawImage(missileD, xpos, ypos+movement, null);
		}		
	}	
	public boolean isColL(){
		boolean col = false;
		for(int i = 0; i < field.bricks.size(); i++ ){
			if( this.xpos+movement >= field.bricks.get(i).xpos && 
					this.xpos+movement <= field.bricks.get(i).xpos+field.bricks.get(i).getWidth() && 
					this.ypos+20 >= field.bricks.get(i).ypos && 
					this.ypos+20 <= field.bricks.get(i).ypos + field.bricks.get(i).getHeight() ){
				field.myTank.sendRemBrick(i);
				field.bricks.remove(i);	
				col = true;
				explode = true;
				timer.schedule(task,1000, 2000);
				field.fireSound.stop();
				field.exSound.start();
				
				
			}
		}
		for(int i = 0; i < 3; i++){
			if ( this.xpos+movement >= field.oTanks.get(i).xpos1a && 
					this.xpos+movement <= field.oTanks.get(i).xpos1a + field.oTanks.get(i).image1aD.getWidth(null) && 
					this.ypos +20 >= field.oTanks.get(i).ypos1a && 
					this.ypos+20 <= field.oTanks.get(i).ypos1a + field.oTanks.get(i).image1aD.getHeight(null)){
				
				field.oTanks.get(i).isHit();
				col = true;
				explode = true;
				timer.schedule(task,1000, 2000);
				
				field.fireSound.stop();
				field.exSound.start();
			
			}
		}
		return col;
	}
	public boolean isColU(){
		boolean col = false;
		for(int i = 0; i < field.bricks.size(); i++ ){
			if( this.ypos+movement <= field.bricks.get(i).ypos + field.bricks.get(i).getHeight() && 
					this.ypos+movement >= field.bricks.get(i).ypos && 
					this.xpos+20 >= field.bricks.get(i).xpos && 
					this.xpos+15 <= field.bricks.get(i).xpos + field.bricks.get(i).getWidth() ){
				field.myTank.sendRemBrick(i);
				field.bricks.remove(i);
				
				col = true;
				explode = true;
				timer.schedule(task,1000);
				
				field.fireSound.stop();
				field.exSound.setFramePosition(0);
				field.exSound.start();
			
			}
		}
		for(int i = 0; i < 3; i++){
			if ( this.ypos+movement <= field.oTanks.get(i).ypos1a + field.oTanks.get(i).image1aD.getHeight() && 
					this.ypos+movement >= field.oTanks.get(i).ypos1a && 
					this.xpos+20 >= field.oTanks.get(i).xpos1a && 
					this.xpos+15 <= field.oTanks.get(i).xpos1a + field.oTanks.get(i).image1aD.getWidth() ){
				
				field.oTanks.get(i).isHit();
				col = true;
				explode = true;
				timer.schedule(task,1000, 2000);
				
				field.fireSound.stop();
				field.exSound.setFramePosition(0);
				field.exSound.start();
			
			}
		}
		return col;
	}
	public boolean isColR(){
		boolean col = false;
		for(int i = 0; i < field.bricks.size(); i++ ){
			if( this.xpos + movement +this.missileD.getWidth() >= field.bricks.get(i).xpos && 
					this.xpos+movement+this.missileD.getWidth() <= field.bricks.get(i).xpos+field.bricks.get(i).getWidth() && 
					this.ypos+20 >= field.bricks.get(i).ypos && 
					this.ypos+20 <= field.bricks.get(i).ypos + field.bricks.get(i).getHeight() ){
				field.myTank.sendRemBrick(i);
				field.bricks.remove(i);
				col = true;
				explode = true;
				timer.schedule(task,1000, 2000);
				
				field.fireSound.stop();
				field.exSound.setFramePosition(0);
				field.exSound.start();
			}
		}
		for(int i = 0; i < 3; i++){
			if ( this.xpos+movement +this.missileD.getWidth() >= field.oTanks.get(i).xpos1a && 
					this.xpos+movement  + this.missileD.getWidth() <= field.oTanks.get(i).xpos1a + field.oTanks.get(i).image1aD.getWidth() && 
					this.ypos +20 >= field.oTanks.get(i).ypos1a && 
					this.ypos+20 <= field.oTanks.get(i).ypos1a + field.oTanks.get(i).image1aD.getHeight()){
				
				field.oTanks.get(i).isHit();
				col = true;
				explode = true;
				timer.schedule(task,1000, 2000);
				
				field.fireSound.stop();
				field.exSound.setFramePosition(0);
				field.exSound.start();
			}
		}
		return col;
	}
	public boolean isColD(){
		boolean col = false;
		for(int i = 0; i < field.bricks.size(); i++ ){
			if( this.ypos + movement +this.missileD.getHeight() >= field.bricks.get(i).ypos && 
					this.ypos+movement+this.missileD.getHeight() <= field.bricks.get(i).ypos+field.bricks.get(i).getHeight() && 
					this.xpos+20 >= field.bricks.get(i).xpos && 
					this.xpos+20 <= field.bricks.get(i).xpos + field.bricks.get(i).getWidth()  ){
				field.myTank.sendRemBrick(i);
				field.bricks.remove(i);
				col = true;
				explode = true;
				timer.schedule(task,1000, 2000);
				
				field.fireSound.stop();
				field.exSound.setFramePosition(0);
				field.exSound.start();
			}
		}
		for(int i = 0; i < 3; i++){
			if (  this.ypos + movement +this.missileD.getHeight() >= field.oTanks.get(i).ypos1a && 
					this.ypos+movement+this.missileD.getHeight() <= field.oTanks.get(i).ypos1a+field.oTanks.get(i).image1aD.getHeight() && 
					this.xpos+20 >= field.oTanks.get(i).xpos1a && 
					this.xpos+20 <= field.oTanks.get(i).xpos1a + field.oTanks.get(i).image1aD.getWidth()    ){
				field.oTanks.get(i).isHit();
				col = true;
				explode = true;
				timer.schedule(task,1000, 2000);
				
				field.fireSound.stop();
				field.exSound.setFramePosition(0);
				field.exSound.start();
			}
		}
		return col;
	}
	public void explode(){
		
	}
	@SuppressWarnings("deprecation")
	public void shoot (){
		if ( turDir == 1 )
			if(!isColL() )
				movement--;
			else{
				field.myTank.stopBullet();//
				bThread.stop();
				
				
				}			
		if (turDir == 2)
			if (!isColU())
				movement--;
			else {
				field.myTank.stopBullet();
				bThread.stop();
				
			}
		if( turDir == 3)
			if(!isColR() )
				movement++;
			else{
				field.myTank.stopBullet();
				bThread.stop();
				
			}
		if( turDir == 4)
			if(!isColD())
				movement++;
			else{
				field.myTank.stopBullet();
				bThread.stop();
				
				
				
			}
	}
	
}
class ExTime extends TimerTask{
	Bullet bullet;
	public ExTime(Bullet b){
		bullet = b;
	}
	@Override
	public void run() {
		bullet.field.shot = false;
		cancel();
	}
	
}


