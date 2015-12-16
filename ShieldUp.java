import java.io.IOException;

public class ShieldUp extends PowerUp {
	public ShieldUp(int x, int y) throws IOException{
		super.xpos = x;
		super.ypos = y;
		super.name = "shield";
	}
}
