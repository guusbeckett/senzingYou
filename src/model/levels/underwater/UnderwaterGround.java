package model.levels.underwater;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import model.GroundRenderer;
import model.MediaProvider;

public class UnderwaterGround implements GroundRenderer
{
	private double time;

	@Override
	public void draw(Graphics2D g2)
	{
		AffineTransform tr = new AffineTransform();
		tr.translate(-100, -100);
		tr.scale(3, 3);
		tr.shear(Math.sin(time / 900) / 10, Math.cos(time / 900) / 10);
		g2.drawImage(MediaProvider.getInstance().getImage("underwater/ground.jpg"), tr, null);
	}

	@Override
	public void update(double time)
	{
		this.time = (this.time + time) % 500;
	}
}
