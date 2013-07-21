package com.breno.crushem.Screens;

import com.badlogic.gdx.Screen;
import com.breno.crushem.MainGame;

public abstract class AbstractScreen implements Screen
{
	public MainGame mGame;
	public static final int GAME_WIDTH = 1280;
	public static final int GAME_HEIGHT = 720;
	
	public AbstractScreen(MainGame game)
	{
		this.mGame = game;
	}
	
}
