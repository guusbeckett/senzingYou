package control;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

public class Hardware
{
	private static Hardware hardware = null;
	private Scent scent;
	private Temperature temperature;
	private Bubbles bubbles;
	SerialPort serialPort;
	private static final String PORT = "COM3";
	private InputStream input;
	private OutputStream output;
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 9600;

	public static Hardware getInstance()
	{
		if (hardware == null)
			hardware = new Hardware();

		return hardware;
	}

	public Hardware()
	{
		initialize();
	}

	public Bubbles getBubbles()
	{
		return bubbles;
	}

	public void setBubbles(Bubbles bubbles)
	{
		this.bubbles = bubbles;
	}

	public Scent getScent()
	{
		return scent;
	}

	public void setScent(Scent scent)
	{
		this.scent = scent;
	}

	public Temperature getTemperature()
	{
		return temperature;
	}

	public void setTemperature(Temperature temperature)
	{
		this.temperature = temperature;
	}

	public void initialize()
	{
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		while (portEnum.hasMoreElements())
		{
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
			if (currPortId.getName().equals(PORT))
			{
				portId = currPortId;
				break;
			}
		}
		if (portId == null)
		{
			System.out.println("Could not find " + PORT + " port.");
			return;
		}
		try
		{
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();
		} catch (Exception e)
		{
			System.err.println(e.toString());
		}
	}

	public void writeToArduino(int command)
	{
		try
		{
			output.write(command);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void close()
	{
		if (serialPort != null)
		{
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	@Override
	public void finalize() throws Throwable
	{
		close();
		super.finalize();
	}
}
