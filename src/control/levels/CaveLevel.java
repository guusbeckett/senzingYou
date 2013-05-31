package control.levels;

import java.awt.Image;
import java.awt.Toolkit;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.entities.cave.Droplet;
import model.entities.cave.Rock;
import view.ground.CaveGround;
import view.ground.GroundRenderer;
import control.Climate;
import control.Hardware;

public class CaveLevel extends DodgeLevel
{
	private GroundRenderer groundRenderer = new CaveGround();
	
	public CaveLevel(Game game)
	{
		super(game);
		Hardware.getInstance().setClimate(Climate.MOIST);
	}

	public void update(double time)
	{
		super.update(time);
//		if((int)(Math.random() * time) == 1){
//			getGame().getEntities().add(new Droplet());
//		}
//		if((int)(Math.random() * (time * 0.5)) == 1){
//			getGame().getEntities().add(new Rock(getGame().getCamera().getUsers()));
//		}
	}

	@Override
	public Entity getRandomEntity()
	{
		return new Droplet();
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		return new Rock(getGame().getCamera().getUsers());
	}

	@Override
	public int getEntitySpawnRate()
	{
		return 100;
	}

	@Override
	public int getHostileEntitySpawnRate()
	{
		return 300;
	}

	@Override
	public Image getBackground()
	{
		return Toolkit.getDefaultToolkit().getImage("./images/cave/background.png");
	}

	@Override
	public GroundRenderer getGroundRenderer()
	{
		return groundRenderer;
	}
}
