package model.entities;

import control.GameController;
import model.Game;


public abstract class MenuEntity extends Entity
{
	
	public MenuEntity()
	{
	}

	public abstract void actionToPerform(Game game);
	public abstract void goToLevel(Game game);
}
