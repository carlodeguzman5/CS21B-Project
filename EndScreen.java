import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class EndScreen extends JFrame {
	private JTextField textField;
	public EndScreen(String x) {
		setBounds(100, 100, 406, 335);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton Quit = new JButton("Quit");
		Quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		Quit.setBounds(158, 213, 89, 23);
		getContentPane().add(Quit);
		
		textField = new JTextField();
		textField.setBounds(107, 256, 215, 29);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.setEditable(false);
		textField.setText(x +" has won the battle!");
		
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(getClass().getResource("menu2.png")));
		label.setBounds(0, -21, 392, 333);
		getContentPane().add(label);

	}
}
