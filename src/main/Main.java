package main;

public class Main 
{

	public Main()
	{
		new Window(420, 200);
	}

	public static void main(String[] args)
	{
		new Main();
		
		
		//MediaRecorder mR;
		
	    /*private SoundPool soundpool; 
	    private HashMap<Integer, Integer> soundsMap;

	    protected void onCreate(Bundle savedInstanceState) {

		soundpool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		        soundsMap = new HashMap<Integer, Integer>();
		        soundsMap.put(cowbell1, soundpool.load(this, R.raw.cowbell, 1));
		        soundsMap.put(cowbell2, soundpool.load(this, R.raw.cowbell1, 1));
		        soundsMap.put(cowbell3, soundpool.load(this, R.raw.windhh3, 1));
	
		}

	    public void playSound(int sound, float fSpeed) {
	    AudioManager mgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    float volume = streamVolumeCurrent / streamVolumeMax;  
	    soundpool.play(soundsMap.get(sound), volume, volume, 1, 0, fSpeed);
	   }*/

	}
	
}
