package com.breno.crushem.Screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.primitives.MutableFloat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.breno.crushem.ArmyType;
import com.breno.crushem.MainGame;

public class ArmySelectionScreen extends AbstractScreen
{

	private Image mScreenTitle;
	private Label mArmyDescription;
	private Button mLeftButton;
	private Button mRightButton;
	private PagedScrollPane mScroll;
	private Array<Button> mArmyCells;
	private Array<TextureRegion> mArmyRibbons;
	private Array<String> mArmyDescriptions;
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
		Gdx.gl.glClearColor(226f/255f, 206f/255f, 163f/255f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		mStage.act();
		mStage.draw();
	}

	@Override
	public void resize(int width, int height)
	{
		mStage.setViewport(1280, 720, true);
		mScroll.setY(720 / 2 - mScroll.getHeight() / 2 + 90);

		mLeftButton.setY(720/2 - mLeftButton.getHeight()/2);
		mLeftButton.setX(170);
		
		mRightButton.setY(720/2 - mRightButton.getHeight()/2);
		mRightButton.setX(1280 - mRightButton.getWidth() - 170);
		
		mRibbon.setX(1280/2 - mRibbon.getWidth()/2);
		mRibbon.setY(155);
		mShadow.setX(1280/2 - mShadow.getWidth()/2);
		mShadow.setY(mRibbon.getY() - 50);
		
		mScreenTitle.setX(1280/2 - mScreenTitle.getWidth()/2);
		mScreenTitle.setY(720 - mScreenTitle.getHeight());
		
		mArmyDescription.setWidth(width - 120);
		mArmyDescription.setX(60);
		mArmyDescription.setHeight(170);
		
	}
	
	private void animateRibbonUp()
	{
		final Action action = 
				Actions.sequence(
						Actions.parallel(
							Actions.moveTo(mRibbon.getX(), 180, 0.2f,Interpolation.exp5Out),
							Actions.fadeOut(0.2f,Interpolation.exp5Out)));
		mRibbon.clearActions();
		mRibbon.addAction(action);
		mShadow.clearActions();
		mShadow.addAction(Actions.fadeOut(0.2f, Interpolation.exp5Out));
		mArmyDescription.clearActions();
		mArmyDescription.addAction(Actions.fadeOut(0.2f, Interpolation.exp5Out));
	}
	
	private void animateRibbonDown()
	{
		mArmyDescription.setText(mArmyDescriptions.get(mScroll.mCurrentPage));
		final TextureRegion newRegion = mArmyRibbons.get(mScroll.mCurrentPage);
		((TextureRegionDrawable)mRibbon.getDrawable()).setRegion(newRegion);
		final Action action = 
				Actions.parallel(
						Actions.moveTo(mRibbon.getX(), 155, 0.3f,Interpolation.exp5Out),
						Actions.fadeIn(0.3f, Interpolation.exp5Out));
		mRibbon.clearActions();
		mRibbon.addAction(action);
		mShadow.clearActions();
		mShadow.addAction(Actions.alpha(0.2f, 0.3f, Interpolation.exp5Out));
		mArmyDescription.clearActions();
		mArmyDescription.addAction(Actions.fadeIn(0.3f, Interpolation.exp5Out));
	}
	
	@Override
	public void show()
	{
		mStage = new Stage(0, 0, true);

		final TextureAtlas atlas = mGame.assetMgr.get("data/menu_screen.atlas");

		mWidget = new Group();
		
		mArmyCells = getArmyImages();
		for(int i = 0 ; i < mArmyCells.size ; ++i)
		{
			final Button btn = mArmyCells.get(i);
			mWidget.addActor(btn);
			btn.setX(500 + i*btn.getWidth() + i*500);
		}
		mWidget.setWidth(mArmyCells.peek().getRight() + 500);
		mWidget.setHeight(mArmyCells.get(0).getHeight());
		
		mArmyRibbons = getRibbonTextures();
		mRibbon = new Image(mArmyRibbons.get(0));
		mShadow = new Image(atlas.findRegion("shadow-ribbon"));
		mShadow.setColor(1,1,1,0.2f);
		mScreenTitle = new Image(atlas.findRegion("choose-army-title"));
		
		
		mArmyDescriptions = getArmyDescriptions();
		final LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = mGame.assetMgr.get("data/fonts/charlemagne.fnt", BitmapFont.class);
		mArmyDescription = new Label(mArmyDescriptions.get(0), labelStyle);
		mArmyDescription.setWrap(true);
		
		mScroll = new PagedScrollPane(mWidget);
		mScroll.setHeight(mWidget.getHeight());
		mScroll.setWidth(1280);
		mScroll.addListener(new ActorGestureListener()
		{
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX, float deltaY)
			{
				animateRibbonUp();
				super.pan(event, x, y, deltaX, deltaY);
			}
		});
		
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
			}
		});

		mStage.addActor(mScreenTitle);
		mStage.addActor(mScroll);
		mStage.addActor(mLeftButton);
		mStage.addActor(mRightButton);
		mStage.addActor(mArmyDescription);
		mStage.addActor(mShadow);
		mStage.addActor(mRibbon);

		Gdx.input.setInputProcessor(mStage);
	}

	private Array<String> getArmyDescriptions()
	{
		final Array<String> result = new Array<String>(3);
		result.add("Pirates have medium strengh units that have the abiliy to steal money from the enemy.");
		result.add("Zombies are normaly weak, but can be trained in high numbers. They also have abilities like 'Revive' or 'Infect'.");
		result.add("Spartan buildings are expensive, but the trained units are very strong and can take lots of damage.");
		return result;
	}

	//TODO Get this data from a factory or something like that
	private Array<Button> getArmyImages()
	{
		final TextureAtlas atlas = mGame.assetMgr.get("data/menu_screen.atlas");

		final ButtonStyle style1 = new ButtonStyle();
		style1.up = new TextureRegionDrawable (atlas.findRegion("army-select-pirates"));
		style1.down = new TextureRegionDrawable (atlas.findRegion("army-select-pirates-down"));
		
		final ButtonStyle style2 = new ButtonStyle();
		style2.up = new TextureRegionDrawable (atlas.findRegion("army-select-zombies"));
		style2.down = new TextureRegionDrawable (atlas.findRegion("army-select-zombies-down"));
		
		final ButtonStyle style3 = new ButtonStyle();
		style3.up = new TextureRegionDrawable (atlas.findRegion("army-select-spartans"));
		style3.down = new TextureRegionDrawable (atlas.findRegion("army-select-spartans-down"));
		
		final Array<Button> result = new Array<Button>(3);
		
		final Button b1 = new Button(style1);
		b1.addListener(new ArmyButtonInputListener(ArmyType.PIRATE));
		
		final Button b2 = new Button(style2);
		b2.addListener(new ArmyButtonInputListener(ArmyType.ZOMBIE));
		
		final Button b3 = new Button(style3);
		b3.addListener(new ArmyButtonInputListener(ArmyType.SPARTAN));
		
		result.add(b1);
		result.add(b2);
		result.add(b3);
		
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
	
	private class ArmyButtonInputListener extends ActorGestureListener
	{
		private ArmyType mArmy;
		
		private ArmyButtonInputListener(ArmyType army)
		{
			mArmy = army;
		}
		
		@Override
		public void tap(InputEvent event, float x, float y, int count, int button)
		{
			mGame.setScreen(new LevelLoadingScreen(mGame, mArmy, ArmyType.ZOMBIE));
		}
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
		private float mPreviousScrollX;
		private float mPreviousDelta;
			
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
				Button cell = mArmyCells.get(mCurrentPage + 1);
				float target = cell.getX() + cell.getWidth() / 2f;
				mCurrentPage++;
				final float a = target - getWidth() / 2f;
				
				setTweenValues(a);
				animateRibbonUp();
			}
		}
		
		public void moveLeft()
		{
			if(mCurrentPage > 0)
			{
				Button cell = mArmyCells.get(mCurrentPage - 1);
				float target = cell.getX() + cell.getWidth() / 2f;
				mCurrentPage--;
				final float a = target - getWidth() / 2f;
				
				setTweenValues(a);
				animateRibbonUp();
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
					Button cell = mArmyCells.get(nextPageIndex);
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
				Button cell = mArmyCells.get(i);
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
		
		TweenCallback animFinished = new TweenCallback()
		{
			
			@Override
			public void onEvent(int type, BaseTween<?> source)
			{
				if(type == TweenCallback.COMPLETE)
					animateRibbonDown();
			}
		};
		
		private void setTweenValues(float targetValue)
		{
			mTween.free();
			mFloat.setValue(getScrollX());
			final float previousDist = getScrollX() - mPreviousScrollX;
			final float targetDist = targetValue - getScrollX() ;
			final float speed = previousDist / mPreviousDelta;
			final float time = speed ==  0 || !(speed == speed) ? 0.2f : targetDist / speed;
			mTween = Tween.to(mFloat, 0, Math.abs(time)).target(targetValue).ease(TweenEquations.easeNone).start();
			mTween.setCallback(animFinished);
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
					setTweenValues(nearest());
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
			mPreviousScrollX = getScrollX();
			mPreviousDelta = delta;
		}
		
	}
}
