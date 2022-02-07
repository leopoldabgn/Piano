package main;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		this.getContentPane().add(piano);
		this.requestFocus();
		this.setVisible(true);
		keyboardSetup();
	}

	public void keyboardSetup()
	{
		this.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				super.keyPressed(e);
				for(int i=0;i<10;i++)
					if(e.getKeyCode() == KeyEvent.VK_A+i)
						piano.getKey(i).play();
			}
			public void keyReleased(KeyEvent e)
			{
				super.keyReleased(e);

			}
		});
	}

}
