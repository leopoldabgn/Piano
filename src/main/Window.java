package main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame
{
	private static final long serialVersionUID = 1L;

	private Piano piano = new Piano(3);
	
	public Window(int w, int h)
	{
		super();
		this.setTitle("Piano");
		this.setMinimumSize(new Dimension(w, h));
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setDefaultLookAndFeelDecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		this.getContentPane().add(piano);
		
		this.setVisible(true);
	}

}
