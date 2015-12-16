public class CDThread implements Runnable {
	ClientField field;
	public CDThread(ClientField c){
		field = c;
	}
	@Override
	public void run() {
		while(true){
			System.out.print("");
			if (field.myTank.coolDown > 0){
				field.myTank.coolDown--;
				field.cdLabel.setText(field.myTank.coolDown+" second/s");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
			}
		}	
	}
}
