import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PowerUp {
	public String name;
	public int xpos;
	public int ypos;
	BufferedImage pu;
	public PowerUp() throws IOException{
		pu = ImageIO.read(getClass().getResource("star.png"));
	}
	
	public void draw(Graphics g){
		g.drawImage(pu, xpos , ypos, null);
	}
	public int getHeight() {
		return pu.getHeight();
	}

	public int getWidth() {
		return pu.getWidth();
	}
}
