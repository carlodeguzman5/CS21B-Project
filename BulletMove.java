
public class BulletMove implements Runnable {
	Bullet bullet;
	BulletOther bulletOther;
	public BulletMove(Bullet b){
		bullet = b;
	}
	public BulletMove(BulletOther b){
		bulletOther = b;
	}
	@Override
	public void run() {
		while(true){
			try {
				if(bullet != null)
				bullet.shoot();
				if(bulletOther != null)
				bulletOther.shoot();
				Thread.sleep(1);
			} catch (InterruptedException e) {}	
		}
	}
}
