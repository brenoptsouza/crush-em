package com.breno.crushem.Screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.breno.crushem.MainGame;

public class InitialLoadingScreen extends AbstractLoadingScreen
{

	public InitialLoadingScreen(MainGame game)
	{
		super(game);
	}

	@Override
	protected void loadData(AssetManager assetManager)
	{
		assetManager.load("data/fonts/charlemagne.fnt", BitmapFont.class);
		assetManager.load("data/menu_screen.atlas", TextureAtlas.class);
	}

	@Override
	protected void onFinishLoading()
	{
		mGame.setScreen(new MainMenuScreen(mGame));
	}

}
