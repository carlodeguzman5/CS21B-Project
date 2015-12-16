//import java.io.IOException;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
import java.util.Scanner;

import javax.swing.JTextField;


public class MoveThread implements Runnable{
Scanner in;
InputStreamReader reader;
ClientField field;
int [] pos;
int num;
int playNum, xpos, ypos, turDir, tankDir;
String name1,name2,name3,name4;
	public MoveThread(InputStreamReader is, int x, ClientField cf){
		reader = is;
		field = cf;
		num = x;
	}
	@Override
	public void run() throws NumberFormatException{
		in = new Scanner(reader);
		while(in.hasNextLine()){
			String temp = in.nextLine();
			String[] a = temp.split(" ");
			if (a.length == 6){
				int x [] = {Integer.parseInt(a[1]), Integer.parseInt(a[2]), Integer.parseInt(a[1]), Integer.parseInt(a[2])};	
				int newTurDir = Integer.parseInt(a[3]);
				int newTankDir = Integer.parseInt(a[4]);
				
				if(Integer.parseInt(a[0]) == 1){
					field.p1.setPos(x);
					field.p1.setTurDir(newTurDir);
					field.p1.setTankDir(newTankDir);
					field.p1.playerName = a[5];
					name1 = a[5];
				}
				if(Integer.parseInt(a[0]) == 2){
					field.p2.setPos(x);
					field.p2.setTurDir(newTurDir);
					field.p2.setTankDir(newTankDir);
					field.p2.playerName = a[5];
					name2 = a[5];
				}
				if(Integer.parseInt(a[0]) == 3){
					field.p3.setPos(x);
					field.p3.setTurDir(newTurDir);
					field.p3.setTankDir(newTankDir);
					field.p3.playerName = a[5];
					name3 = a[5];
				}
				if(Integer.parseInt(a[0]) == 4){
					field.p4.setPos(x);
					field.p4.setTurDir(newTurDir);
					field.p4.setTankDir(newTankDir);
					field.p4.playerName = a[5];
					name4 = a[5];
				}
			}
			else if (a.length == 1){
				//try{
					if (a[0].equals("1fire")){
						try {
							field.bullet1 = new BulletOther(field.p1.xpos1a, field.p1.ypos1a, field.p1.turDir, field);
						} catch (IOException e) {}
						field.shot1 = true;
						field.bullet1.bThread.start();
						field.fireSound.setFramePosition(0);
						field.fireSound.start();
					
					}
					else if (a[0].equals("2fire")){
						try {
							field.bullet2 = new BulletOther(field.p2.xpos1a, field.p2.ypos1a, field.p2.turDir, field);
						} catch (IOException e) {}
						field.shot2 = true;
						field.bullet2.bThread.start();
						field.fireSound.setFramePosition(0);
						field.fireSound.start();
						
					}
					else if (a[0].equals("3fire")){
						field.shot3 = true;
						try {
							field.bullet3 = new BulletOther(field.p3.xpos1a, field.p3.ypos1a, field.p3.turDir, field);
						} catch (IOException e) {}
						field.bullet3.bThread.start();
						field.fireSound.setFramePosition(0);
						field.fireSound.start();
					
					}
					else if (a[0].equals("4fire")){
						field.shot4 = true;
						try {
							field.bullet4 = new BulletOther(field.p4.xpos1a, field.p4.ypos1a, field.p4.turDir, field);
						} catch (IOException e) {}
						field.bullet4.bThread.start();	
						field.fireSound.setFramePosition(0);
						field.fireSound.start();
					}
				//}catch(IOException e){}
					else if (a[0].equals("1stop")){
						field.bullet1.bThread.stop();
						field.bullet1.explode = true;
						
						field.exSound.setFramePosition(0);
						field.exSound.start();
						
						field.bullet1.timer.schedule(field.bullet1.ex, 1000, 2000);
					}
					else if (a[0].equals("2stop")){
						field.bullet2.bThread.stop();
						field.bullet2.explode = true;
						
						field.exSound.setFramePosition(0);
						field.exSound.start();
						
						field.bullet2.timer.schedule(field.bullet2.ex, 1000, 2000);
					}
					else if (a[0].equals("3stop")){
						field.bullet3.bThread.stop();
						field.bullet3.explode = true;
						
						field.exSound.setFramePosition(0);
						field.exSound.start();
						
						field.bullet3.timer.schedule(field.bullet3.ex, 1000, 2000);
					}
					else if (a[0].equals("4stop")){
						field.bullet4.bThread.stop();
						field.bullet4.explode = true;
						
						field.exSound.setFramePosition(0);
						field.exSound.start();
						
						field.bullet4.timer.schedule(field.bullet4.ex, 1000, 2000);
					}
					else if (a[0].equals("1con")){
						field.isCon2=true;
						
					}
					else if (a[0].equals("2con")){
						field.isCon2=true;
						field.conCount=2;//field.p2.isDead = false;
					}
					else if (a[0].equals("3con")){
						field.isCon3=true;
						field.conCount=3;//field.p3.isDead = false;
					}
					else if (a[0].equals("4con")){
						field.isCon4=true;
						field.conCount=4;//field.p4.isDead = false;
					}
					else if (a[0].equals("1hit")){
						if (field.p1 == null){
							field.armLabel.setText(field.myTank.isHit()-1+"");
						}
						else
							field.p1.armor--;
					}
					else if (a[0].equals("2hit")){
						if (field.p2 == null){
							field.armLabel.setText(field.myTank.isHit()-1+"");
						}
						else
							field.p2.armor--;
					}
					else if (a[0].equals("3hit")){
						if(field.p3 == null){
							field.armLabel.setText(field.myTank.isHit()-1+"");	
						}
						else
							field.p3.armor--;		
					}
					else if (a[0].equals("4hit")){
						if(field.p4 == null){
							field.armLabel.setText(field.myTank.isHit()-1+"");
						}
						else
							field.p4.armor--;
					}
					
					else if(a[0].equals("gameover")){
						field.gameOver();
					}
			}
					
					
			else if (a.length == 2){
						if(a[1].equals("ammo")){
							if (a[0].equals("1"))
								field.appender(name1+" has picked up an ammo-inator powerup!\n");
							else if (a[0].equals("2"))
								field.appender(name2+" has picked up an ammo-inator powerup!\n");		
							else if (a[0].equals("3"))
								field.appender(name3+" has picked up an ammo-inator powerup!\n");
							else if (a[0].equals("4"))
								field.appender(name4+" has picked up an ammo-inator powerup!\n");
						}											
						else if(a[1].equals("armor")){
							if (a[0].equals("1")){
								field.appender(name1+" has picked up an armor-inator powerup!\n");
								field.p1.armor++;
							}
							else if (a[0].equals("2")){
								field.appender(name2+" has picked up an armor-inator powerup!\n");
								field.p2.armor++;
							}
							else if (a[0].equals("3")){
								field.appender(name3+" has picked up an armor-inator powerup!\n");
								field.p3.armor++;
							}
							else if (a[0].equals("4")){
								field.appender(name4+" has picked up an armor-inator powerup!\n");
								field.p4.armor++;
							}
						}				
						else if(a[1].equals("boost")){
							if (a[0].equals("1"))
								field.appender(name1+" has picked up an boost-inator powerup!\n");
							else if (a[0].equals("2"))
								field.appender(name2+" has picked up an boost-inator powerup!\n");		
							else if (a[0].equals("3"))
								field.appender(name3+" has picked up an boost-inator powerup!\n");
							else if (a[0].equals("4"))
								field.appender(name4+" has picked up an boost-inator powerup!\n");
						}
						else if(a[1].equals("EMP")){
							field.myTank.coolDown = 10;
							if (a[0].equals("1")){
								field.appender(name1+" has activated an EMP!\nTurrets are disabled for 10 seconds!\n");
							}
							else if (a[0].equals("2")){
								field.appender(name2+" has activated an EMP!!\nTurrets are disabled for 10 seconds!\n");		
							}
							else if (a[0].equals("3")){
								field.appender(name3+" has activated an EMP!!\nTurrets are disabled for 10 seconds!\n");
							}
							else if (a[0].equals("4")){
								field.appender(name4+" has activated an EMP!!\nTurrets are disabled for 10 seconds!\n");
							}
						}
						else if(a[1].equals("shield")){
							if (a[0].equals("1")){
								field.appender(name1+" has picked up a shield-inator powerup!\n");
								field.p1.setShield();
							}
							else if (a[0].equals("2")){
								field.appender(name2+" has picked up a shield-inator powerup!\n");	
								field.p2.setShield();
							}
							else if (a[0].equals("3")){
								field.appender(name3+" has picked up a shield-inator powerup!\n");
								field.p3.setShield();
							}
							else if (a[0].equals("4")){
								field.appender(name4+" has picked up a shield-inator powerup!\n");
								field.p4.setShield();
							}
						}
						else if(a[1].equals("slow")){
							if (a[0].equals("1")){
								field.appender(name1+" has picked up a slow-inator powerdown!\n");
							}
							else if (a[0].equals("2")){
								field.appender(name2+" has picked up a slow-inator powerdown!\n");		
							}
							else if (a[0].equals("3")){
								field.appender(name3+" has picked up a slow-inator powerdown!\n");
							}
							else if (a[0].equals("4")){
								field.appender(name4+" has picked up a slow-inator powerdown!\n");
							}
						}
						else if(a[1].equals("stun")){
							if (a[0].equals("1")){
								field.appender(name1+" has picked up a stun-inator powerdown!\n");
							}
							else if (a[0].equals("2")){
								field.appender(name2+" has picked up a stun-inator powerdown!\n");		
							}
							else if (a[0].equals("3")){
								field.appender(name3+" has picked up a stun-inator powerdown!\n");
							}
							else if (a[0].equals("4")){
								field.appender(name4+" has picked up a stun-inator powerdown!\n");
							}
						}
						else if(a[1].equals("cd")){
							if (a[0].equals("1")){
								field.appender(name1+" has picked up a cooldown extend-inator powerdown!\n");
							}
							else if (a[0].equals("2")){
								field.appender(name2+" has picked up a cooldown extend-inator powerdown!\n");		
							}
							else if (a[0].equals("3")){
								field.appender(name3+" has picked up a cooldown extend-inator powerdown!\n");
							}
							else if (a[0].equals("4")){
								field.appender(name4+" has picked up a cooldown extend-inator powerdown!\n");
							}
						}
						else if (a[1].equals("removepow")){
							field.powerAl.remove(Integer.parseInt(a[0]));
						}
						else if (a[1].equals("removeBrick")){
							field.bricks.remove(Integer.parseInt(a[0]));
							/*int num = Integer.parseInt(a[0]);
							field.bricks.remove(num);*/
						}	
						else if(a[1].equalsIgnoreCase("dead")){
							if (a[0].equals("1")){
								field.dead1 =true;
								field.deadCount++;
							}
							else if (a[0].equals("2")){
								field.dead2 =true;	
								field.deadCount++;
							}
							else if (a[0].equals("3")){
								field.dead3 =true;
								field.deadCount++;
							}
							else if (a[0].equals("4")){
								field.dead4 =true;
								field.deadCount++;
							}		
						}
					}
			else if(a.length == 3){
						if (a[2].equals("makepower")){
								double num = Double.parseDouble(a[0]);
								double num2 = Double.parseDouble(a[1]);
								
								if(num < 5){
									try {
										field.makePowerUp("EMP",num2);
									} catch (IOException e) {}
								}
								else if(num < 15){
									try {
										field.makePowerUp("boost",num2);
									} catch (IOException e) {}
								}
								else if(num < 20){
									try {
										field.makePowerUp("armor",num2);
									} catch (IOException e) {}
								}
								else if( num < 30){
									try {
										field.makePowerUp("ammo",num2);
									} catch (IOException e) {}
								}
								else if( num < 40){
									try {
										field.makePowerUp("shield",num2);
									} catch (IOException e) {}
								}
								else if (num <50){
									try {
										field.makePowerUp("slow",num2);
									} catch (IOException e) {}
								}
								else if (num < 60){
									try {
										field.makePowerUp("stun",num2);
									} catch (IOException e) {}
								}
								else if (num < 80){
									try {
										field.makePowerUp("cd",num2);
									} catch (IOException e) {}
								}
						}		
					}
		}
	}
}
