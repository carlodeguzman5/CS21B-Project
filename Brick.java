import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Brick {
	ClientField c;
	BufferedImage brick;
	int xpos, ypos;
	
	public Brick(int x, int y) throws IOException{
		xpos = x;
		ypos = y;
		brick = ImageIO.read(getClass().getResource("brick.png"));//brickImg);
	}
	
	public void draw(Graphics g){
		g.drawImage(brick, xpos, ypos, null, null);
	}
	public int getHeight(){
		return brick.getHeight(null);
	}
	public int getWidth(){
		return brick.getWidth(null);
	}
}
