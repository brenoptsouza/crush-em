package com.breno.crushem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.breno.crushem.Screens.InitialLoadingScreen;

public class MainGame extends Game 
{
	public AssetManager assetMgr;
	
	@Override
	public void create() 
	{
		assetMgr = new AssetManager();
		setScreen(new InitialLoadingScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
