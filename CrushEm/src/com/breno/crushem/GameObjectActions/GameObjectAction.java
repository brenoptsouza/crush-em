package com.breno.crushem.GameObjectActions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.breno.crushem.GameObject;

public abstract class GameObjectAction extends Action
{
	protected GameObject mGameObject;
	
	public GameObjectAction (GameObject gameObject)
	{
		mGameObject = gameObject;
	}
}
