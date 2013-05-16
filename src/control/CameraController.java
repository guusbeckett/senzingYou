package control;

import model.CameraData;
import model.Game;

import org.OpenNI.Context;
import org.OpenNI.OutArg;
import org.OpenNI.ScriptNode;

public class CameraController
{
	private Game game;
	private CameraData cameraData;
	
	public CameraController(Game game)
	{
		
		this.game = game;
		try
		{
			OutArg<ScriptNode> scriptNode = new OutArg<ScriptNode>();
			cameraData = new CameraData(Context.createFromXmlFile("./OpenNIConfig.xml", scriptNode));
			cameraData.getContext().startGeneratingAll();
		}
		catch(Exception e)
		{
			
		}
	}
}
