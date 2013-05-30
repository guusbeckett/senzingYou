package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import audio.AudioDevice;
import audio.MP3Decoder;
import audio.SpectrumProvider;
import audio.ThresholdFunction;

public class Song
{
	private Thread th;
	
	private File track;
	private float lastCall = 0;
	private int position = 0;
	private float elapsedTime;
	private float maxThreshold;
	private float minThreshold;
	private List<List<Float>> thresholds;
	private static final int HOP_SIZE = 512;
	public static final float multiplier = 2f;
	private static final int HISTORY_SIZE = 50;
	private static final float[] bands = { 80, 4000, 4000, 10000, 10000, 16000 };

	public Song(final File track) throws FileNotFoundException, Exception
	{
		this.track = track;
		MP3Decoder decoder = new MP3Decoder(new FileInputStream(track));
		SpectrumProvider spectrumProvider = new SpectrumProvider(decoder, 1024,
				HOP_SIZE, true);
		float[] spectrum = spectrumProvider.nextSpectrum();
		float[] lastSpectrum = new float[spectrum.length];
		List<List<Float>> spectralFlux = new ArrayList<List<Float>>();
		for (int i = 0; i < bands.length / 2; i++)
			spectralFlux.add(new ArrayList<Float>());

		do
		{
			for (int i = 0; i < bands.length; i += 2)
			{
				int startFreq = spectrumProvider.getFFT().freqToIndex(bands[i]);
				int endFreq = spectrumProvider.getFFT().freqToIndex(
						bands[i + 1]);
				float flux = 0;
				for (int j = startFreq; j <= endFreq; j++)
				{
					float value = (spectrum[j] - lastSpectrum[j]);
					value = (value + Math.abs(value)) / 2;
					flux += value;
				}
				spectralFlux.get(i / 2).add(flux);
			}

			System.arraycopy(spectrum, 0, lastSpectrum, 0, spectrum.length);
		} while ((spectrum = spectrumProvider.nextSpectrum()) != null);

		thresholds = new ArrayList<List<Float>>();
		for (int i = 0; i < bands.length / 2; i++)
		{
			List<Float> threshold = new ThresholdFunction(HISTORY_SIZE,
					multiplier).calculate(spectralFlux.get(i));
			thresholds.add(threshold);
		}
		
		float min = 0;
		float max = 0;
		for (int i = 0; i < thresholds.get(0).size(); i++)
		{
			min = Math.min(thresholds.get(0).get(i), min);
			max = Math.max(thresholds.get(0).get(i), max);
		}
		minThreshold = min;
		maxThreshold = max;
	}

	public void play() throws InterruptedException, Exception
	{
		th = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					float[] samples = new float[1024];
					MP3Decoder reader = new MP3Decoder( new FileInputStream( track ) );
					AudioDevice device = new AudioDevice( );
					long startTime = 0;
					
					while( reader.readSamples( samples ) > 0 )
					{
						device.writeSamples( samples );
						if (startTime == 0)
							startTime = System.nanoTime();
						elapsedTime = (System.nanoTime() - startTime) / 1000000000.0f;
						position = (int) (elapsedTime * (44100 / HOP_SIZE));
						Thread.sleep(20);
					}
					
//					Thread.sleep( 10000 );
//					AudioDevice device = new AudioDevice();
//					float[] samples = new float[1024];
//					long startTime = 0;
//
//					System.out.print(samples);
//					while (decoder.readSamples(samples) > 0)
//					{
//						device.writeSamples(samples); // plays the music
//
//						if (startTime == 0)
//							startTime = System.nanoTime();
//						float elapsedTime = (System.nanoTime() - startTime) / 1000000000.0f;
//						position = (int) (elapsedTime * (44100 / HOP_SIZE));
//						Thread.sleep(20);
//					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		th.start();
	}
	
	public void stop(){
		th.interrupt();	//Have to fix this small issue because I get a interupt error on Thread.sleep
	}

	public float getThreshold()
	{	
		float total = 0;
		int first = (int)(lastCall * 44100 / 512) - 1;
		if (first < 0)
			first = 0;
		int last = (int)(elapsedTime * 44100 / 512);
		for (int i = first; i < last; i++)
			total += getThreshold(i);
		lastCall = elapsedTime;
		return total / (last - first);
	}

	public float getThreshold(int index)
	{
		//JW Fix otherwise big indexout of bounce exception
		if(thresholds.get(0).size() >= index){
			return thresholds.get(0).get(index);
		}
		return 0;
	}

	public int getTime()
	{
		return position;
	}

	public float getMaxThreshold()
	{
		return maxThreshold;
	}

	public float getMinThreshold()
	{
		return minThreshold;
	}
}
