import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Canvas;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;


public class Menu extends JFrame {
	Clip clip;
	private JPanel contentPane;
	private static Menu frame;
	
	public Menu() throws IOException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 417, 288);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Canvas canvas = new Canvas();
		canvas.setBounds(5, 5, 0, 251);
		contentPane.add(canvas);
		
		Image image = ImageIO.read(getClass().getResource("bg.png"));
		JPanel panel = new JPanel();
		JLabel label = new JLabel(new ImageIcon(image));
		panel.add(label);
		panel.setBounds(0, 0, 445, 260);
		contentPane.add(panel);
		panel.setLayout(null);
		
		final JButton btnHost = new JButton("");
		btnHost.setIcon(new ImageIcon(getClass().getResource("host1.png")));
		btnHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
			}
		});
		btnHost.setBounds(10, 11, 148, 97);
		panel.add(btnHost);
		
		final JButton btnClient = new JButton("");
		btnClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent m) {
				btnClient.setIcon(new ImageIcon(getClass().getResource("client2.png")));
			}
			@Override
			public void mouseExited(MouseEvent m) {
				btnClient.setIcon(new ImageIcon(getClass().getResource("client1.png")));
			}
			@Override
			public void mouseClicked(MouseEvent m) {
				ClientScreen cs;
				try {
					cs = new ClientScreen();
					cs.setLocation( 500, 300);
					clip.stop();
					frame.dispose();
					
				} catch (IOException e) {}
				catch(NullPointerException e1){}	
			}
		});
		btnClient.setIcon(new ImageIcon(getClass().getResource("client1.png")));
		btnClient.setBounds(249, 11, 148, 97);
		panel.add(btnClient);
		
		final JLabel bgLabel = new JLabel("");
		bgLabel.setBounds(0, -12, 462, 321);
		panel.add(bgLabel);
		bgLabel.setIcon(new ImageIcon(getClass().getResource("menu.png")));
		btnHost.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent m) {
				btnHost.setIcon(new ImageIcon(getClass().getResource("host2.png")));
			}
			@Override
			public void mouseExited(MouseEvent m) {
				btnHost.setIcon(new ImageIcon(getClass().getResource("host1.png")));
			}
			@Override
			public void mouseClicked(MouseEvent m) {
				try {
					
					ServerRunner sr = new ServerRunner();
					clip.stop();
					frame.dispose();
					
				} catch (NumberFormatException e) {
				} catch (Exception e) {}
			}
			
		});
		
		//Music
		
		
		 try {
		     URL url = this.getClass().getClassLoader().getResource("bgmMenu.wav");
		     AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
		     clip = AudioSystem.getClip();
		     clip.open(audioIn);
		     clip.start();
		     clip.loop(clip.LOOP_CONTINUOUSLY);
		  } catch (UnsupportedAudioFileException e) {
		     e.printStackTrace();
		  } catch (IOException e) {
		     e.printStackTrace();
		  } catch (LineUnavailableException e) {
		     e.printStackTrace();
		  }
	}
}

