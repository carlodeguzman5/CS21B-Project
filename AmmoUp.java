import java.io.IOException;


public class AmmoUp extends PowerUp {
	public String name;
	public AmmoUp(int x, int y) throws IOException{
		
		super.name= "ammo";	
		super.xpos =x;
		super.ypos =y;
	}
}
