package modelview;

import javax.swing.JPanel;

public class Octave extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private Key[] keys;
	private Key[] sharpKeys;
	private int octave = 2;
	private float coeff = 1;
	
	public Octave(int octave)
	{
		this.octave = octave;
	}
	
	public Octave(int octave, int[] position)
	{
		this.octave = octave;
		setup(position, true);
	}
	
	public Octave(int octave, int[] position, float coeffSize)
	{
		this.octave = octave;
		this.coeff = coeffSize;
		setup(position, true);
	}
	
	public Octave(int octave, int[] position, float coeffSize, boolean mouse)
	{
		this.octave = octave;
		this.coeff = coeffSize;
		setup(position, mouse);
	}
	
	public String toString()
	{
		return ""+octave;
	}
	
	public void setup(int[] position, boolean mouse)
	{
		this.setLayout(null);
		this.setBounds(position[0], position[1], Key.KEY_SIZE[0]*7, Key.KEY_SIZE[1]);
		initKeys(mouse);
		if(coeff != 1.0f)
			refreshOctaveSize(coeff);
	}
	
	public void initKeys(boolean mouse)
	{
		keys = new Key[7];
		sharpKeys = new Key[5];
		char c = 'C';
		for(int i=0;i<keys.length;i++)
		{
			if(i == keys.length-2)
				c = (char)('A'-i);
			keys[i] = new Key(this, ""+(char)(c+i), new int[] {Key.KEY_SIZE[0]*i, 0}, mouse);
		}
		
		int w = Key.KEY_SIZE[0]/2;
		int temp = 0;
		c = 'C';
		for(int i=0;i<2;i++)
		{
			sharpKeys[temp] = new Key(this, (char)(c+i)+"#", new int[] {keys[i+1].getX()-w/2, 0}, mouse);
			temp++;
		}
		
		for(int i=0;i<2;i++)
		{
			sharpKeys[temp] = new Key(this, (char)(c+3+i)+"#", new int[] {keys[4+i].getX()-w/2, 0}, mouse);
			temp++;
		}
		
		sharpKeys[temp] = new Key(this, "A#", new int[] {keys[6].getX()-w/2, 0}, mouse);
		
		for(int i=0;i<sharpKeys.length;i++)
			this.add(sharpKeys[i]);
		
		for(int i=0;i<keys.length;i++)
			this.add(keys[i]);
		
	}
	
	public Key getLeftSharpKey(Key k)
	{
		int index = getIndex(keys, k);
		if(index == -1)
			return null;
		
		if(index > 0 && index <= 2)
			return sharpKeys[index-1];
		else if(index > 3 && index <= 6)
			return sharpKeys[index-2];
		else if(index > 7 && index <= 9)
			return sharpKeys[index-3];
		
	
		
		return null;
	}
	
	public Key getRightSharpKey(Key k)
	{
		int index = getIndex(keys, k);
		if(index == -1)
			return null;
		
		if(index >= 0 && index < 2)
			return sharpKeys[index];
		else if(index >= 3 && index < 6)
			return sharpKeys[index-1];
		else if(index >= 7 && index < 9)
			return sharpKeys[index-2];
		
		return null;
	}
	
	public int getIndex(Key[] keys, Key k)
	{
		for(int i=0;i<keys.length;i++)
		{
			if(keys[i] == k)
				return i;
		}
		return -1;
	}
	
	public void refreshOctaveSize(float coeff)
	{
		this.setSize((int)(getWidth()*coeff), (int)(getHeight()*coeff));
		for(int i=0;i<keys.length;i++)
			keys[i].refreshSize(coeff);
		for(int i=0;i<sharpKeys.length;i++)
			sharpKeys[i].refreshSize(coeff);
	}
	
	public Key getKey(int index)
	{
		if(index < 0 || index >= length())
			return null;
		int[] k = {0, 2, 4, 5, 7, 9, 11};
		int[] s = {1, 3, 6, 8, 10};
		for(int i=0;i<k.length;i++) {
			if(index == k[i])
				return keys[i];
			else if(i < s.length && s[i] == index)
				return sharpKeys[i];
		}
		return null;
	}

	public int length()
	{
		return keys.length + sharpKeys.length;
	}
	
}
