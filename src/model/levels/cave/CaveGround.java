package model.levels.cave;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import model.GroundRenderer;
import model.MediaProvider;

public class CaveGround implements GroundRenderer
{
	@Override
	public void draw(Graphics2D g2)
	{
		AffineTransform tr = new AffineTransform();
		tr.translate(-400, 0);
		g2.drawImage(MediaProvider.getInstance().getImage("cave/ground.jpg"), tr, null);
	}

	@Override
	public void update(double time)
	{
		// TODO Auto-generated method stub

	}
}
