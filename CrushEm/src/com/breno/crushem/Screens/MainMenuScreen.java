package com.breno.crushem.Screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.equations.Elastic;
import aurelienribon.tweenengine.primitives.MutableFloat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.breno.crushem.MainGame;

public class MainMenuScreen extends AbstractScreen
{

	FPSLogger mFPSLogger;
	Label mLabel;
	Button mStartButton;
	Button mSoundButton;
	Button mMusicButton;
	Stage mStage;
	
	Tween titleTween;
	MutableFloat titleFloat;
	Tween startTween;
	MutableFloat startFloat;
	Tween soundTween;
	MutableFloat soundFloat;
	Tween musicTween;
	MutableFloat musicFloat;
	
	
	public MainMenuScreen(MainGame game)
	{
		super(game);
	}

	@Override
	public void render(float delta)
	{
		//Clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        titleTween.update(delta);
        startTween.update(delta);
        soundTween.update(delta);
        musicTween.update(delta);
        mLabel.setY(titleFloat.floatValue());
        mStartButton.setX(startFloat.floatValue());
        mMusicButton.setY(musicFloat.floatValue());
        mSoundButton.setY(soundFloat.floatValue());
        
        mStage.act();
        mStage.draw();
        mFPSLogger.log();
	}

	@Override
	public void resize(int width, int height)
	{
		mStage.setViewport(width, height, true);
		mLabel.setX(width/2 - mLabel.getWidth()/2);
		mLabel.setY(height/2 + 200);
		
		mStartButton.setX(width);
		mStartButton.setY(height/2);
		
		mSoundButton.setX(width - mSoundButton.getWidth() - 25);
		mSoundButton.setY(25);
		
		mMusicButton.setX(mSoundButton.getX() - mSoundButton.getWidth() - 25);
		mMusicButton.setY(25);
		
		titleFloat = new MutableFloat(mLabel.getY());
		titleTween = Tween.from(titleFloat, 0, 1.5f).target(height).ease(Elastic.OUT.p(0.4f)).start();
		startFloat = new MutableFloat(mStartButton.getX());
		startTween = Tween.to(startFloat, 0, 0.9f).target(width/2 - mStartButton.getWidth()/2).delay(0.2f).ease(Back.OUT).start();
		soundFloat = new MutableFloat(-mSoundButton.getHeight());
		soundTween = Tween.to(soundFloat, 0, 0.5f).target(25).ease(Back.OUT).start();
		musicFloat = new MutableFloat(-mMusicButton.getHeight());
		musicTween = Tween.to(musicFloat, 0, 0.5f).target(25).delay(0.1f).ease(Back.OUT).start();
	}

	@Override
	public void show()
	{
		mStage = new Stage(0,0,true);
		Gdx.input.setInputProcessor(mStage);

		//final Texture mainMenuTexture = mGame.assetMgr.get("data/main_menu_gui.png", Texture.class);
		final TextureAtlas atlas = mGame.assetMgr.get("data/menu_screen.atlas", TextureAtlas.class);
		
		final LabelStyle style = new LabelStyle();
		style.font = mGame.assetMgr.get("data/fonts/charlemagne.fnt", BitmapFont.class);
		
		final ButtonStyle startButtonStyle = new ButtonStyle();
		startButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("start-button-up"));
		startButtonStyle.down = new TextureRegionDrawable(atlas.findRegion("start-button-down"));
		
		final ButtonStyle soundButtonStyle = new ButtonStyle();
		soundButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("sound-button-up"));
		soundButtonStyle.checked = new TextureRegionDrawable(atlas.findRegion("sound-button-checked"));
		
		final ButtonStyle musicButtonStyle = new ButtonStyle();
		musicButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("music-button-up"));
		musicButtonStyle.checked = new TextureRegionDrawable(atlas.findRegion("music-button-checked"));
		
		mLabel = new Label("CHRUSH'EM", style);
		mStartButton = new Button(startButtonStyle);
		mStartButton.addListener(new InputListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
			{
				return true;
			};
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) 
			{
				//mGame.setScreen(new LevelLoadingScreen(mGame));
				mGame.setScreen(new ArmySelectionScreen(mGame));
			};
		}
		);
		mSoundButton = new Button(soundButtonStyle);
		mMusicButton = new Button(musicButtonStyle);
		
		mStage.addActor(mLabel);
		mStage.addActor(mStartButton);
		mStage.addActor(mSoundButton);
		mStage.addActor(mMusicButton);
		mFPSLogger = new FPSLogger();
	}

	@Override
	public void hide()
	{
		
	}

	@Override
	public void pause()
	{
		
	}

	@Override
	public void resume()
	{
		
	}

	@Override
	public void dispose()
	{
		mGame.assetMgr.unload("data/main_menu.atlas");
	}

}
