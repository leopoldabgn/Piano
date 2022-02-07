package main;

import java.awt.Dimension;

import javax.swing.JPanel;

public class Piano extends JPanel
{
	private static final long serialVersionUID = 1L;

	Octave[] octaves = new Octave[3];
	private float coeff = 1;
	private ZoomBar zB;
	
	public Piano()
	{
		super();
		setup(true, true);
	}
	
	public Piano(float coeffSize)
	{
		super();
		this.coeff = coeffSize;
		setup(true, true);
	}
	
	public Piano(float coeffSize, boolean bar, boolean mouse)
	{
		super();
		this.coeff = coeffSize;
		setup(bar, mouse);
	}

	public void setup(boolean bar, boolean mouse)
	{
		this.setLayout(null);
		this.setPreferredSize(new Dimension(Key.KEY_SIZE[0]*21, Key.KEY_SIZE[1]));
		int y = 0;
		if(bar)
		{
			zB = new ZoomBar(this);	
			
			this.add(zB);
	
			y = zB.getHeight();
		}

		octaves[0] = new Octave(1, new int[] {0, y}, coeff, mouse);
		octaves[1] = new Octave(2, new int[] {octaves[0].getWidth(), y}, coeff, mouse);
		octaves[2] = new Octave(3, new int[] {octaves[0].getWidth()*2, y}, coeff, mouse);
		
		for(int i=0;i<3;i++)
			this.add(octaves[i]);
		this.requestFocus();
	}

	public void octavesShift(ZoomBar zB)
	{
		int w = octaves[0].getWidth()*3;
		double coeff = (double)(zB.getRectX())/zB.getWidth();
		
		for(int i=0;i<octaves.length;i++)
			octaves[i].setLocation((int)(-(w*coeff)+i*octaves[i].getWidth()+zB.getX()), octaves[i].getY());
		
		repaint();
	}
	
	public Octave getOctave(int nb)
	{
		if(nb >= 1 && nb <= 3)
			return octaves[nb-1];
		else
			return null;
	}
	
	public Key getKey(int index)
	{
		int octLength = octaves[0].length();
		int octave = index / octLength;
		int key = index % octLength;
		
		if(octave >= 3)
			return null;
		
		return octaves[octave].getKey(key);
	}

}
