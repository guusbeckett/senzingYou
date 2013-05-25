package control;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.OutputStream;

public class Hardware
{
	private static final String PORT = "COM3";
	
	private static Hardware hardware = null;
	private Scent scent;
	private Temperature temperature;
	
	private SerialPort serialPort;
	private OutputStream output;
	
	public static Hardware getInstance()
	{
		if (hardware == null)
			hardware = new Hardware();

		return hardware;
	}

	public Hardware()
	{
		try
		{
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(PORT);
			serialPort = (SerialPort) portId.open(this.getClass().getName(), 2000);
			serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			output = serialPort.getOutputStream();
		} catch (NoSuchPortException npE)
		{
			System.err.println(npE.toString());
		} catch (Exception e)
		{
			System.err.println(e.toString());
		}
	}

	public Scent getScent()
	{
		return scent;
	}

	public void setScent(Scent scent)
	{
		writeToArduino(0x10 | scent.ordinal());
		this.scent = scent;
	}

	public Temperature getTemperature()
	{
		return temperature;
	}

	public void setTemperature(Temperature temperature)
	{
		writeToArduino(0x10 | temperature.ordinal());
		this.temperature = temperature;
	}

	public void writeToArduino(int command)
	{
		if (output != null)
		{
			try
			{
				output.write(command);
				output.flush();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public synchronized void close()
	{
		if (serialPort != null)
		{
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
