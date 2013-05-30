package control.levels;

import java.awt.Toolkit;

import control.Climate;
import control.Hardware;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.entities.rainforest.Banana;
import model.entities.rainforest.Bird;
import model.entities.rainforest.Snake;
import model.entities.rainforest.Tree;

public class RainforestLevel extends DodgeLevel
{

	public RainforestLevel(Game game)
	{
		super(game);
		Hardware.getInstance().setClimate(Climate.MOIST);
		game.setBackground(Toolkit.getDefaultToolkit().getImage(
				"./images/rainforest/background.png"));
		game.getEntities().add(new Tree());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Entity getRandomEntity()
	{
		// TODO Auto-generated method stub
		if(Math.random() > 0.5){
			return new Bird();
		}
		else{
			return new Snake();
		}
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		// TODO Auto-generated method stub
		return new Banana(getGame().getCamera().getUsers());
	}
}

