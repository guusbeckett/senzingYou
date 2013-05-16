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
	private CameraData cameraData;
	
	public CameraController(Game game)
	{
		
		this.game = game;
		try
		{
			OutArg<ScriptNode> scriptNode = new OutArg<ScriptNode>();
			cameraData = new CameraData(Context.createFromXmlFile("./OpenNIConfig.xml", scriptNode));
			
			//RGB image
			cameraData.setImage(new BufferedImage(cameraData.getImageGenerator().getMetaData().getFullXRes(), cameraData.getImageGenerator().getMetaData().getFullYRes(), BufferedImage.TYPE_INT_RGB));
			
			cameraData.getContext().startGeneratingAll();
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void update(){
		updateRGBCamera();
		
	}

	private void updateRGBCamera(){
		int[] imageRGBArray = new int[cameraData.getImageGenerator().getMetaData().getFullXRes() * cameraData.getImageGenerator().getMetaData().getFullYRes()];
		
		int i = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		ByteBuffer rgbBuffer = cameraData.getImageGenerator().getMetaData().getData().createByteBuffer();
		for (int x = 0; x < cameraData.getImageGenerator().getMetaData().getFullXRes(); x++) {
			for (int y = 0; y < cameraData.getImageGenerator().getMetaData().getFullYRes(); y++) {
				i = y * cameraData.getImageGenerator().getMetaData().getFullXRes() + x;
				r = rgbBuffer.get(i * 3) & 0xff;
				g = rgbBuffer.get(i * 3 + 1) & 0xff;
				b = rgbBuffer.get(i * 3 + 2) & 0xff;
				imageRGBArray[i] = (r << 16) | (g << 8) | b;
			}
		}
		cameraData.getImage().setRGB(0, 0, cameraData.getImageGenerator().getMetaData().getFullXRes(), cameraData.getImageGenerator().getMetaData().getFullYRes(), imageRGBArray, 0, cameraData.getImageGenerator().getMetaData().getFullXRes());
	}
	
}
