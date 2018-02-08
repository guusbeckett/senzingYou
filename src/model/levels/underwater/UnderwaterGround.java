package model.levels.underwater;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import model.GroundRenderer;
import model.MediaProvider;

public class UnderwaterGround implements GroundRenderer
{
	private boolean goingBack = false;
	private double time;

	@Override
	public void draw(Graphics2D g2)
	{
		AffineTransform tr = new AffineTransform();
		 tr.translate(-400, -400);
		tr.shear(Math.sin(time / 900) / 6, Math.cos(time / 900) / 6);
		g2.drawImage(MediaProvider.getInstance().getImage("underwater/ground.jpg"), tr, null);
	}

	@Override
	public void update(double time)
	{
		if (goingBack)
		{
			this.time -= time;
		} else
			this.time += time;

		if (this.time > 160 * Math.PI)
			goingBack = true;
		if (this.time < 0)
			goingBack = false;
	}
}
