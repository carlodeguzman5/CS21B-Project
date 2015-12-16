import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Timer;


import javax.imageio.ImageIO;

public class BulletOther {
	int xpos,ypos,turDir;
	BufferedImage missileL, missileU, missileR, missileD, explosion;
	private int movement;
	Thread bThread;
	ClientField field;
	boolean explode, disappear;
	Explode ex;
	Timer timer;
	
	public BulletOther(int x, int y, int dir, ClientField c) throws IOException{
		xpos = x;
		ypos = y;
		turDir = dir;
		field = c;
		explode = false;
		ex = new Explode(this);
		timer = new Timer ("");
		disappear = false;
		
		missileL = ImageIO.read(field.getClass().getResource("misL.png"));
		missileU = ImageIO.read(field.getClass().getResource("misU.png"));
		missileR = ImageIO.read(field.getClass().getResource("misR.png"));
		explosion = ImageIO.read(getClass().getResource("bexplosion.png"));
		missileD = ImageIO.read(field.getClass().getResource("misD.png"));
		
		
		bThread = new Thread(new BulletMove(this));
		
		movement = 0;	
	}
	public void draw(Graphics g){
		if(!explode){
			if ( turDir == 1)g.drawImage(missileL, xpos+movement, ypos+5, null);
			else if( turDir == 2)g.drawImage(missileU, xpos, ypos+movement, null);
			else if( turDir == 3)g.drawImage(missileR, xpos+movement, ypos, null);
			else if( turDir == 4)g.drawImage(missileD, xpos, ypos+movement, null);
		}
		if (disappear){
			
		}
		else if (explode){
			if ( turDir == 1)g.drawImage(explosion, xpos+movement-explosion.getWidth()/2, ypos-explosion.getHeight()/2, null);
			else if( turDir == 2)g.drawImage(explosion, xpos-explosion.getWidth()/2, ypos+movement-explosion.getHeight()/2, null);
			else if( turDir == 3)g.drawImage(explosion, xpos+movement-explosion.getWidth()/2, ypos-explosion.getHeight()/2, null);
			else if( turDir == 4)g.drawImage(explosion, xpos-explosion.getWidth()/2, ypos+movement-explosion.getHeight()/2, null);
		}
		
	}
	public void shoot (){
		if ( turDir == 1 )
				movement--;		
		if (turDir == 2)
				movement--;
		if( turDir == 3)
				movement++;
		if( turDir == 4)
				movement++;
		}
}


class Explode extends TimerTask{
	BulletOther bullet;
	public Explode(BulletOther b){
		bullet = b;
	}
	@Override
	public void run() {
		bullet.explode = false;
		bullet.missileL = null;
		bullet.missileU = null;
		bullet.missileR = null;
		bullet.missileD = null;
		cancel();
	}
	
}

	
	
	
	
