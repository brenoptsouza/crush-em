package com.breno.crushem.Screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.primitives.MutableFloat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.breno.crushem.MainGame;

public class ArmySelectionScreen extends AbstractScreen
{

	private Image mScreenTitle;
	private Label mArmyDescription;
	private Button mLeftButton;
	private Button mRightButton;
	private PagedScrollPane mScroll;
	private Array<Image> mArmyCells;
	private Array<TextureRegion> mArmyRibbons;
	private Image mRibbon;
	private Image mShadow;
	private Group mWidget;
	private Stage mStage;

	public ArmySelectionScreen(MainGame game)
	{
		super(game);
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(218f/255f, 184f/255f, 131f/255f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		mStage.act();
		mStage.draw();
	}

	@Override
	public void resize(int width, int height)
	{
		mStage.setViewport(1280, 720, true);
		mScroll.setY(720 / 2 - mScroll.getHeight() / 2);

		mLeftButton.setY(720/2 - mLeftButton.getHeight()/2);
		mLeftButton.setX(170);
		
		mRightButton.setY(720/2 - mRightButton.getHeight()/2);
		mRightButton.setX(1280 - mRightButton.getWidth() - 170);
		
		mRibbon.setX(1280/2 - mRibbon.getWidth()/2);
		mRibbon.setY(80);
		mShadow.setX(1280/2 - mShadow.getWidth()/2);
		mShadow.setY(mRibbon.getY() - 50);
		
		mScreenTitle.setX(1280/2 - mScreenTitle.getWidth()/2);
		mScreenTitle.setY(720 - mScreenTitle.getHeight());
	}

	private void updateRibbon()
	{
		final Action action = 
				Actions.sequence(
						Actions.parallel(
							Actions.moveBy(0, 25, 0.3f,Interpolation.exp5Out),
							Actions.fadeOut(0.3f,Interpolation.exp5Out)),
						Actions.run(updateRibbonTexture),
						Actions.delay(0.7f),
						Actions.parallel(
								Actions.moveBy(0, -25, 0.2f,Interpolation.exp5Out),
								Actions.fadeIn(0.2f, Interpolation.exp5Out)));
		mRibbon.addAction(action);
	}
	
	Runnable updateRibbonTexture = new Runnable()
	{
		@Override
		public void run()
		{
			final TextureRegion newRegion = mArmyRibbons.get(mScroll.mCurrentPage);
			((TextureRegionDrawable)mRibbon.getDrawable()).setRegion(newRegion);
		}
	};
	
	@Override
	public void show()
	{
		mStage = new Stage(0, 0, true);

		final TextureAtlas atlas = mGame.assetMgr.get("data/menu_screen.atlas");

		mWidget = new Group();
		
		mArmyCells = getArmyImages();
		for(int i = 0 ; i < mArmyCells.size ; ++i)
		{
			final Image img = mArmyCells.get(i);
			mWidget.addActor(img);
			img.setX(500 + i*img.getWidth() + i*500);
		}
		mWidget.setWidth(mArmyCells.peek().getRight() + 500);
		mWidget.setHeight(mArmyCells.get(0).getHeight());
		
		mArmyRibbons = getRibbonTextures();
		mRibbon = new Image(mArmyRibbons.get(0));
		mShadow = new Image(atlas.findRegion("shadow-ribbon"));
		mShadow.setColor(1,1,1,0.2f);
		mScreenTitle = new Image(atlas.findRegion("choose-army-title"));
		
		mScroll = new PagedScrollPane(mWidget);
		mScroll.setHeight(mWidget.getHeight());
		mScroll.setWidth(1280);
		
		final ButtonStyle styleLeft = new ButtonStyle();
		styleLeft.up = new TextureRegionDrawable(atlas.findRegion("double-arrows-left"));
		styleLeft.down = new TextureRegionDrawable(atlas.findRegion("double-arrows-left-down"));
		styleLeft.pressedOffsetX = -10;
		styleLeft.unpressedOffsetX = 10;
		mLeftButton = new Button(styleLeft);
		mLeftButton.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				mScroll.moveLeft();
				updateRibbon();
			}
		});
		
		final ButtonStyle styleRight = new ButtonStyle();
		styleRight.up = new TextureRegionDrawable(atlas.findRegion("double-arrows-right"));
		styleRight.down = new TextureRegionDrawable(atlas.findRegion("double-arrows-right-down"));
		styleRight.pressedOffsetX = 10;
		styleRight.unpressedOffsetX = -10;
		mRightButton = new Button(styleRight);
		mRightButton.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				mScroll.moveRight();
				updateRibbon();
			}
		});

		mStage.addActor(mScreenTitle);
		mStage.addActor(mScroll);
		mStage.addActor(mLeftButton);
		mStage.addActor(mRightButton);
		mStage.addActor(mShadow);
		mStage.addActor(mRibbon);

		Gdx.input.setInputProcessor(mStage);
	}

	//TODO Get this data from a factory or something like that
	private Array<Image> getArmyImages()
	{
		final TextureAtlas atlas = mGame.assetMgr.get("data/menu_screen.atlas");

		Image i1 = new Image(atlas.findRegion("army-select-pirates"));
		Image i2 = new Image(atlas.findRegion("army-select-zombies"));
		Image i3 = new Image(atlas.findRegion("army-select-spartans"));

		final Array<Image> result = new Array<Image>(3);
		result.add(i1);
		result.add(i2);
		result.add(i3);
		
		return result;
	}
	
	//TODO Get this data from a factory or something like that
	private Array<TextureRegion> getRibbonTextures()
	{
		final TextureAtlas atlas = mGame.assetMgr.get("data/menu_screen.atlas");
		final Array<TextureRegion> result = new Array<TextureRegion>(3);
		
		result.add(atlas.findRegion("ribbon-pirates"));
		result.add(atlas.findRegion("ribbon-zombies"));
		result.add(atlas.findRegion("ribbon-spartans"));
		
		return result;
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

	}

	private class PagedScrollPane extends ScrollPane
	{
		
		Tween mTween;
		MutableFloat mFloat;
		int mCurrentPage;
		boolean mThresholdCrossed;
		private float mTarget;
			
		private final static float MAX_VELOCITY = 1200;
		private final static float PAGING_THRESHOLD = 250;
		
		public PagedScrollPane(Actor widget)
		{
			super(widget);
			setOverscroll(false, false);
			mFloat = new MutableFloat(1000);
			mTween = Tween.to(mFloat, 0, 0.2f).ease(TweenEquations.easeNone);
			setForceOverscroll(false, false);
			mTween.pause();
		}
		
		
		public void moveRight()
		{
			if(mCurrentPage < mArmyCells.size - 1)
			{
				Image cell = mArmyCells.get(mCurrentPage + 1);
				float target = cell.getX() + cell.getWidth() / 2f;
				mCurrentPage++;
				final float a = target - getWidth() / 2f;
				
				mTween.free();
				mFloat.setValue(getScrollX());
				mTween = Tween.to(mFloat, 0, 0.8f).target(a).ease(TweenEquations.easeOutBack).start();
			}
		}
		
		public void moveLeft()
		{
			if(mCurrentPage > 0)
			{
				Image cell = mArmyCells.get(mCurrentPage - 1);
				float target = cell.getX() + cell.getWidth() / 2f;
				mCurrentPage--;
				final float a = target - getWidth() / 2f;
				
				mTween.free();
				mFloat.setValue(getScrollX());
				mTween = Tween.to(mFloat, 0, 0.8f).target(a).ease(TweenEquations.easeOutBack).start();
			}
		}


		private float nearest()
		{
			float width = getWidth();

			if(mThresholdCrossed)
			{
				mThresholdCrossed = false;
				int nextPageIndex = getVelocityX() > 0 ? mCurrentPage - 1 : mCurrentPage + 1;
				if(nextPageIndex >= 0 && nextPageIndex < mArmyCells.size)
				{
					Image cell = mArmyCells.get(nextPageIndex);
					float target = cell.getX() + cell.getWidth() / 2f;
					mCurrentPage = nextPageIndex;
					return target - width / 2f;
				}
			}
			
			float current = getScrollX() + width / 2f;
			float bestTarget = 0;
			float bestDistance = Float.MAX_VALUE;
			int newPage = Integer.MAX_VALUE;
			for (int i = 0, n = mArmyCells.size; i < n; ++i)
			{
				Image cell = mArmyCells.get(i);
				float target = cell.getX() + cell.getWidth() / 2f;
				float distance = Math.abs(target - current);
				if (distance >= bestDistance)
					break;
				bestDistance = distance;
				bestTarget = target;
				newPage = i;
			}
			mCurrentPage = newPage;
			return bestTarget - width / 2f;
		}
		
		@Override
		public void act(float delta)
		{
			if(getVelocityX() > MAX_VELOCITY)
				setVelocityX(MAX_VELOCITY);
			if(getVelocityX() < -MAX_VELOCITY)
				setVelocityX(-MAX_VELOCITY);
			super.act(delta);
			if(!isPanning() && Math.abs(getVelocityX()) < PAGING_THRESHOLD)
			{
				if(mTween.isPaused())
				{
					mTween.free();
					mFloat.setValue(getScrollX());
					mTween = Tween.to(mFloat, 0, 0.8f).target(nearest()).ease(TweenEquations.easeOutBack).start();
					setVelocityX(0);
				}

				
				mTween.update(delta);
				setScrollX(mFloat.floatValue());
			}
			else
			{
				mThresholdCrossed = Math.abs(getVelocityX()) >= PAGING_THRESHOLD;
				mTween.pause();
			}
			
		}
		
	}
}
