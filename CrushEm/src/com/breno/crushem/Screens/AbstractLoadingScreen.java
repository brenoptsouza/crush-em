package com.breno.crushem.Screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.primitives.MutableFloat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.breno.crushem.MainGame;

public abstract class AbstractLoadingScreen extends AbstractScreen
{

	private Image mLoading;
	private Image mProgress;
	private Image mPoint;
	private Stage mStage;
	private TweenManager mTweenMgr;
	private MutableFloat mFloat1;
	
	public AbstractLoadingScreen(MainGame game) 
	{
		super(game);
	}

	@Override
	public void render(float delta) 
	{
		//Clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        mStage.act();
        mStage.draw();
        
        final AssetManager assetMgr = mGame.assetMgr;
        
        if(assetMgr.update())
        {
        	mTweenMgr.update(delta);
        	mLoading.setY(mFloat1.floatValue());
        	
        	mProgress.setX(mLoading.getX() + 7);
    		mProgress.setY(mLoading.getY() + 4);
    		
    		mPoint.setX(mProgress.getX()+7);
    		mPoint.setY(mProgress.getY()-4);
        }
        mProgress.setScaleX(assetMgr.getProgress());
        mPoint.setX((mProgress.getX() + (mProgress.getWidth() * mProgress.getScaleX())) - mPoint.getWidth()/2);
	}

	@Override
	public void resize(int width, int height) 
	{
		mStage.setViewport(width, height, true);
		mStage.getCamera().translate(-mStage.getGutterWidth(), mStage.getGutterHeight(), 0);
		
		mLoading.setX(width/2 - mLoading.getWidth()/2);
		mLoading.setY(height/2);
		
		mProgress.setX(mLoading.getX() + 7);
		mProgress.setY(mLoading.getY() + 4);
		
		mPoint.setX(mProgress.getX()+7);
		mPoint.setY(mProgress.getY()-4);
		
		mFloat1 = new MutableFloat(mLoading.getY());
		Tween.to(mFloat1, 0, 1.0f).target(-mLoading.getHeight()).delay(0.2f).ease(Back.INOUT).setCallback(mAnimationFinished).start(mTweenMgr);
		
	}

	private TweenCallback mAnimationFinished = new TweenCallback()
	{
		boolean executed = false;
		
		@Override
		public void onEvent(int type, BaseTween<?> source)
		{
			if(!executed && type == TweenCallback.COMPLETE)
			{
				executed = true;
				onFinishLoading();
			}
		}
	};
	
	@Override
	public void show() 
	{
		mStage =  new Stage(0, 0, true);
		final AssetManager assetMgr = mGame.assetMgr;
		//Load the assest needed for olny this screen
		assetMgr.load("data/loading_screen.atlas", TextureAtlas.class);
		assetMgr.finishLoading();
		
		final TextureAtlas atlas = assetMgr.get("data/loading_screen.atlas", TextureAtlas.class);
		final TextureRegion loadingRegion = atlas.findRegion("loading");
		final TextureRegion progressRegion = atlas.findRegion("progress");
		final TextureRegion pointRegion = atlas.findRegion("progress-tip");
		
		mLoading = new Image(loadingRegion);
		mProgress = new Image(progressRegion);
		mPoint = new Image(pointRegion);
		
		mStage.addActor(mLoading);
		mStage.addActor(mProgress);
		mStage.addActor(mPoint);
		
		mTweenMgr = new TweenManager();
		//Load the assets that will be used in the game
		loadData(assetMgr);
	}

	protected abstract void loadData(AssetManager assetManager);
	
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
		mTweenMgr.killAll();
		mGame.assetMgr.unload("data/loading-screen.atlas");
	}
	
	protected abstract void onFinishLoading();

}
