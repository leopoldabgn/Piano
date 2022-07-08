package modelview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class ZoomBar extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private Piano piano;
	private Piano drawingPiano = new Piano(0.75f, false, false);
	private RectPan rect;
	private int initialMouseX = 0, initialX = 0;
	
	public ZoomBar(Piano p)
	{
		super();
		this.piano = p;
		this.setBackground(Color.RED);
		setup();
	}
	
	public void setup()
	{
		ZoomBar self = this;
		this.setLayout(null);
		
		rect = new RectPan(0, 0, 100, drawingPiano.getOctave(1).getHeight());
		
		this.add(rect);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setSize(drawingPiano.getOctave(1).getWidth()*3, drawingPiano.getOctave(1).getHeight());
		this.setLocation((int)((screen.getWidth()/2)-this.getWidth()/2), 0);

		drawingPiano.setBounds(0, 0, drawingPiano.getOctave(1).getWidth()*3, drawingPiano.getOctave(1).getHeight());
		//drawingPiano.setOpaque(false);
		
		rect.setLocation(drawingPiano.getWidth()/2 - rect.getWidth()/2, 0);
		
		this.add(drawingPiano);
		
		this.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				initialX = rect.getX();
				initialMouseX = e.getX();
			}
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
		});
		
		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
					if(rect.checkCoord(e.getX(), 0) && isInBorder(initialX+(e.getX()-initialMouseX), 0, rect.getWidth(), rect.getHeight()))
					{
						rect.setLocation(initialX+(e.getX()-initialMouseX), 0);
						piano.octavesShift(self);
					}
			}
		});
		
	}
	
	private class RectPan extends JPanel
	{
		private static final long serialVersionUID = 1L;

		public RectPan(int x, int y, int w, int h)
		{
			this.setOpaque(false);
			this.setBounds(x, y, w, h);
		}
		
		public void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			
			g.setColor(new Color(29, 166, 245, 100));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
		public boolean checkCoord(int tx, int ty)
		{
			return (tx >= getX() && tx <= getX()+getWidth()) && (ty >= getY() && ty <= getY()+getHeight());
		}
				
	}
	
	public int getRectX()
	{
		return rect.getX();
	}
	
	public boolean isInBorder(int tx, int ty, int w, int h)
	{
		return (tx >= 0 && tx+w <= getWidth()) && (ty >= 0 && ty+h <= getHeight());
	}
	
}
