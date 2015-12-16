public class Repainter implements Runnable {

	ClientField canvas;
	public Repainter(ClientField c){
		canvas = c;
	}
	@Override
	public void run() {
		while(true){
			canvas.repaint();
			canvas.myTank.sendUpdate();
			try {
				
				Thread.sleep(33);
			} catch (InterruptedException e) {}
			
			
		}
	}
}
