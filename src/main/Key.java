package main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class Key extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public static int[] KEY_SIZE = {40, 100};
	
	private String name;
	private int type = 0;
	private float coeff = 1;
	private boolean isFocused = false, isPressed = false;
	private Octave octave;
	
	public Key(String name, int octave, int[] position)
	{
		super();
		this.octave = new Octave(octave);
		this.name = name;
		setup(position, true);
	}
	
	public Key(String name, int octave, int[] position, float coeffSize)
	{
		super();
		this.octave = new Octave(octave);
		this.name = name;
		this.coeff = coeffSize;
		setup(position, true);
	}
	
	public Key(Octave octave, String name, int[] position)
	{
		super();
		this.octave = octave;
		this.name = name;
		setup(position, true);
	}
	
	public Key(Octave octave, String name, int[] position, boolean mouse)
	{
		super();
		this.octave = octave;
		this.name = name;
		setup(position, mouse);
	}
	
	public Key(Octave octave, String name, int[] position, float coeffSize)
	{
		super();
		this.octave = octave;
		this.name = name;
		this.coeff = coeffSize;
		setup(position, true);
	}

	public void mouseSetup()
	{
		this.addMouseListener(new MouseAdapter() 
		{

			public void mousePressed(MouseEvent e) 
			{
				isPressed = true;
				repaint();
				play();
			}
			
			public void mouseReleased(MouseEvent e)
			{
				isPressed = false;
				repaint();
			}
			
			public void mouseEntered(MouseEvent e) 
			{
				isFocused = true;
				repaint();
			    int buttonsDownMask = MouseEvent.BUTTON1_DOWN_MASK; // On peut en rajouter d'autre avec un '|'
			    if((e.getModifiersEx() & buttonsDownMask) != 0)
			    {
					try {
						Robot robot = new Robot();
						robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
					} catch (AWTException e1) {e1.printStackTrace();}
			    }
			}
			
			public void mouseExited(MouseEvent e)
			{
				isFocused = false;
				isPressed = false;
				repaint();
			}
			
		});
	}
	
	public void setKeyTypeAndPosition(String name, int[] position)
	{
		if(name.charAt(1) == '#' || name.charAt(1) == 'b')
		{
			this.type = 1;
			this.setBounds(position[0], position[1], (int)((KEY_SIZE[0]*coeff)/2), (int)((KEY_SIZE[1]*coeff)/2));
		}
		else
		{
			this.setBounds(position[0], position[1], (int)(KEY_SIZE[0]*coeff), (int)(KEY_SIZE[1]*coeff));
		}
	}
	
	public static boolean checkFormat(String key)
	{
		if(key.length() < 2 || key.length() > 3)
			return false;
		
		if(key.charAt(0) < 'A' || key.charAt(0) > 'G')
			return false;
		
		int index = 1;
		
		if(key.length() == 3)
		{
			if(key.charAt(1) != '#' && key.charAt(1) != 'b')
				return false;
			index = 2;
		}
			
		if(key.charAt(index) < '1' || key.charAt(index) > '3')
			return false;
		
		return true;
	}
	
	public static String toSharpKey(String key)
	{
		if(!checkFormat(key) || key.length() == 2)
			return key;
		
		char let = key.charAt(0), type = key.charAt(1);
		char oct = key.charAt(2);
		
		if(type == 'b')
		{
			if(let == 'A')
			{
				if(oct == '0')
					return key;
				return "G#"+(int)(oct-1);
			}
			else if(let == 'F' || let == 'C')
			{
				return ""+(char)(let-1)+key.charAt(2);
			}
			else
			{
				return ""+(char)(let-1)+"#"+key.charAt(2);
			}
		}
		
		return key;
	}
	
	public void setup(int[] position, boolean mouse)
	{
		try {
			if(name.length() == 1 || (name.length() == 2 && 
			  (name.charAt(1) == '#' || name.charAt(1) == 'b')))
				name += octave;
			if(!checkFormat(name))
				throw new BadKeyNameException(name+" is not a valid key !");

			//sound = new AudioPlayer("sounds/"+getConvertName()+".wav", false);
			setKeyTypeAndPosition(name, position);
			
			//setOpaque(false); /////////////////////////////////////////////////////////////////
			if(mouse)
				mouseSetup();
		}catch(BadKeyNameException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(isPressed)
			g.setColor(new Color(50, 50, 50));
		else if(isFocused)
			g.setColor(new Color(200, 200, 200));
		else
			g.setColor(type == 0 ? Color.WHITE : Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth(), getHeight());
		
		g.drawString(this.getConvertName(), (this.getWidth()/2)-5, this.getHeight()/2);
		
		repaintNearbySharpKeys();
	}
	
	public void repaintNearbySharpKeys()
	{
		if(type == 0)
		{
			Key k1 = octave.getLeftSharpKey(this);
			Key k2 = octave.getRightSharpKey(this);
			if(k1 != null)
				k1.repaint();
			if(k2 != null)
				k2.repaint();
		}
	}
	
	public void refreshSize(float coeff)
	{
		
		this.setBounds((int)((coeff*getX())/this.coeff), (int)((coeff*getY())/this.coeff), 
				       (int)((coeff*getWidth())/this.coeff), (int)((coeff*getHeight())/this.coeff));
		
		this.coeff = coeff;
		repaint();
	}
	
	public static void play(String name)
	{
		//System.out.println(name+" = "+toSharpKey(name));
		(new AudioPlayer("sounds/"+toSharpKey(name)+".wav")).play();
	}
	
	public void play()
	{
		play(getName());
	}
	
	public Octave getOctave()
	{
		return octave;
	}
	
	public int getOctaveNb()
	{
		return Integer.parseInt(octave.toString());
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getConvertName()
	{
		return toSharpKey(this.name);
	}
	
	public String getNameWithoutOctave()
	{
		return name.substring(0, 1);
	}
	
}
