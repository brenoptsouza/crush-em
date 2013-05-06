package com.breno.crushem.Screens;

import com.badlogic.gdx.Screen;
import com.breno.crushem.MainGame;

public abstract class AbstractScreen implements Screen
{
	public MainGame mGame;
	public AbstractScreen(MainGame game)
	{
		this.mGame = game;
	}
	
}
