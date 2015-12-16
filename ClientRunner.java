import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
import java.io.IOException;
//import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
//import java.util.Scanner;

public class ClientRunner extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientRunner (int y,  PrintStream ps, InputStreamReader isr, String un ) throws IOException, Exception{ 
		setSize(910,718);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		ClientField f = new ClientField( y, ps, isr , un ); //y is playerNumber, 
		add(f);
		setResizable(false);
	}

}
