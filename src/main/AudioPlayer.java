package main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer 
{ 
	///////////// INTERESSANT /////////////////////////////////
	// write the audio file in targeted pitch file
	// AudioSystem.write(in2, AudioFileFormat.Type.WAVE, file2);
	///////////////////////////////////////////////////////////
	
	// to store current position 
	private Long currentFrame; 
	private Clip clip; 
	
	// current status of clip 
	private String status; 
	
	private AudioInputStream audioInputStream; 
	private String filePath; 
	private boolean loop;
	
	// constructor to initialize streams and clip 
	public AudioPlayer(String filePath)
	{ 
		this.filePath = filePath;
		this.loop = false;
		
		try 
		{
			//AudioInputStream temp = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
			
			//AudioFormat format = getOutFormat(temp.getFormat());
			
			//System.out.println(format.getChannels());
			
			//audioInputStream = AudioSystem.getAudioInputStream(format, temp);
			// getClass().getClassLoader().getResourceAsStream(filePath) --> Ne fonctionne pas...
			audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
			//AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File("temp.wav"));
			clip = AudioSystem.getClip(); 
			clip.open(audioInputStream);
		} 
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
		{
			e.printStackTrace();
		} 
	} 
	
	public AudioPlayer(String filePath, boolean loop)
	{ 
		this.filePath = filePath;
		this.loop = loop;
		// create AudioInputStream object 
		try 
		{
			audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
			
			// create clip reference 
			clip = AudioSystem.getClip(); 
			
			// open audioInputStream to the clip 
			clip.open(audioInputStream); 
			
			
			
			if(loop)
				clip.loop(Clip.LOOP_CONTINUOUSLY); 
		
		} 
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
		{
			e.printStackTrace();
		} 
	} 
	
	public static AudioFormat getOutFormat(AudioFormat inFormat) 
	{
		int ch = inFormat.getChannels();
		float rate = inFormat.getSampleRate();
		
		return new AudioFormat(Encoding.PCM_SIGNED, 72000, 16, ch, ch * 2, rate,
		inFormat.isBigEndian());
	}
	
	// Method to play the audio 
	public void play() 
	{ 
		//start the clip 
		clip.start(); 
		
		status = "play"; 
	} 
	
	// Method to pause the audio 
	public void pause() 
	{ 
		if (status.equals("paused")) 
		{ 
			System.out.println("audio is already paused"); 
			return; 
		} 
		this.currentFrame = 
		this.clip.getMicrosecondPosition(); 
		
		clip.stop(); 
		status = "paused"; 
	} 
	
	// Method to resume the audio 
	public void resumeAudio() throws UnsupportedAudioFileException, 
								IOException, LineUnavailableException 
	{ 
		if (status.equals("play")) 
		{ 
			System.out.println("Audio is already "+ 
			"being played"); 
			return; 
		} 
		clip.close(); 
		resetAudioStream(); 
		clip.setMicrosecondPosition(currentFrame); 
		this.play(); 
	} 
	
	// Method to restart the audio 
	public void restart()
	{ 
		try {
			stop();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		currentFrame = 0L; 
		try {
			resetAudioStream();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		} 
		
		//clip.setMicrosecondPosition(0); 
		//this.play(); 
	} 
	
	public void resetClip() throws LineUnavailableException, IOException
	{
		clip.close();
		clip = AudioSystem.getClip(); 
		clip.open(audioInputStream);
		if(loop)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	// Method to stop the audio 
	public void stop() throws UnsupportedAudioFileException, 
	IOException, LineUnavailableException 
	{ 
		currentFrame = 0L; 
		clip.stop(); 
		clip.close(); 
	} 
	
	// Method to jump over a specific part 
	public void jump(long c) throws UnsupportedAudioFileException, IOException, 
														LineUnavailableException 
	{ 
		if (c > 0 && c < clip.getMicrosecondLength()) 
		{ 
			clip.stop(); 
			clip.close(); 
			resetAudioStream(); 
			currentFrame = c; 
			clip.setMicrosecondPosition(c); 
			this.play(); 
		} 
	} 
	
	// Method to reset audio stream 
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, 
											LineUnavailableException 
	{ 
		audioInputStream = AudioSystem.getAudioInputStream( 
		new File(filePath).getAbsoluteFile()); 
		clip.open(audioInputStream); 
		if(loop)
			clip.loop(Clip.LOOP_CONTINUOUSLY); 
	} 
	
	public void finalize()
	{
		System.out.println("L'objet "+this.getClass().getName()+" a ete supprimé !");
	}
	
}