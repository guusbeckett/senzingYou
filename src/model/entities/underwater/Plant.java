package model.entities.underwater;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import model.CameraData;
import model.entities.Entity;

public class Plant extends Entity
{
	private int counter;
	private double waveSpeed;
	
	public Plant()
	{
		super();
		
		position.setLocation(Math.random() * (CameraData.VIEW_WIDTH - 20) + 1, CameraData.VIEW_HEIGHT);
		waveSpeed = (Math.random() * (1.1 - 1) + 1);
	}
	
	public void update(double time)
	{
		super.update(time);
		rotation = Math.sin(Math.toRadians((counter++)*waveSpeed)) / 2 + 3;
	}

	@Override
	public Point2D getRotationPoint()
	{
		return new Point2D.Double(getDimensions().getWidth()/2,0);
	}

	@Override
	public Dimension2D getDimensions()
	{
		return new Dimension(30, 150);
	}

	@Override
	public List<Image> getImages()
	{
		ArrayList<Image> images = new ArrayList<Image>();
		
		images.add(Toolkit.getDefaultToolkit().getImage("./images/underwater/seaweed.png"));
		
		return images;
	}

}