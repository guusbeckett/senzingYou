package control;

import org.OpenNI.Context;
import org.OpenNI.OutArg;
import org.OpenNI.ScriptNode;

public class CameraController
{
	protected Context context;
	
	public CameraController()
	{
		try
		{
			OutArg<ScriptNode> scriptNode = new OutArg<ScriptNode>();
            context = Context.createFromXmlFile("./OpenNIConfig.xml", scriptNode);
            context.startGeneratingAll();
		}
		catch(Exception e)
		{
			
		}
	}
}
