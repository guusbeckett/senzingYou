package control;

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
	private int lastCall = 0;
	private int position = 0;
	private MP3Decoder decoder;
	private List<List<Float>> thresholds;
	private static final int HOP_SIZE = 512;
	public static final float multiplier = 2f;
	private static final int HISTORY_SIZE = 50;
	private static final float[] bands = { 80, 4000, 4000, 10000, 10000, 16000 };

	public Song(final String track) throws FileNotFoundException, Exception
	{
		decoder = new MP3Decoder(new FileInputStream(track));
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
	}

	public void play() throws InterruptedException, Exception
	{
		Thread th = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					AudioDevice device = new AudioDevice();
					float[] samples = new float[1024];
					long startTime = 0;

					while (decoder.readSamples(samples) > 0)
					{
						device.writeSamples(samples); // plays the music

						if (startTime == 0)
							startTime = System.nanoTime();
						float elapsedTime = (System.nanoTime() - startTime) / 1000000000.0f;
						position = (int) (elapsedTime * (44100 / HOP_SIZE));
						Thread.sleep(20);
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		th.start();
	}

	public float getThreshold()
	{
		int old = lastCall;
		lastCall = position;
		float total = 0;
		for (int i = old; i <= position; i++)
			total += thresholds.get(0).get((i * 44100) / 512);
		return total / (position - old);
	}

	public float getThreshold(int time)
	{
		return thresholds.get(0).get((time * 44100) / 512);
	}

	public int getTime()
	{
		return position;
	}
}
