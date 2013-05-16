package control;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import model.CameraData;
import model.Game;

import org.OpenNI.Context;
import org.OpenNI.OutArg;
import org.OpenNI.ScriptNode;

public class CameraController
{
	private Game game;
	
	public CameraController(Game game)
	{
		
		this.game = game;
		try
		{
			OutArg<ScriptNode> scriptNode = new OutArg<ScriptNode>();
			game.setCameraData(new CameraData(Context.createFromXmlFile("./OpenNIConfig.xml", scriptNode)));
			
			//RGB image		
			game.getCameraData().getContext().startGeneratingAll();
		}
		catch(Exception e)
		{
			
		}
	}
	
}
