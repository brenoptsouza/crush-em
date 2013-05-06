package com.breno.crushem.hud;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class FighterButtonCell extends Group
{

	private Image mThumb;
	private Image mFrame;
	private Image mProgress;

	private float mProgressScale;

	public FighterButtonCell(TextureRegion thumb, NinePatch frame, TextureRegion progress, float margin)
	{
		mFrame = new Image(frame);
		mThumb = new Image(thumb);
		mProgress = new Image(progress);

		mFrame.setWidth(mThumb.getWidth() + margin*2);
		mFrame.setHeight(mThumb.getWidth() + margin*2);
		
		setWidth(mFrame.getWidth());
		setHeight(mFrame.getHeight());

		mThumb.setX(margin);
		mThumb.setY(margin);

		mProgress.setWidth(mThumb.getWidth());
		mProgress.setHeight(mThumb.getHeight());
		mProgress.setX(mThumb.getX());
		mProgress.setY(mThumb.getY());
		mProgress.setColor(1, 1, 1, 0.6f);
		
		addActor(mFrame);
		addActor(mThumb);
		addActor(mProgress);
	}

	@Override
	public void act(float delta)
	{
		mProgress.setScaleY(mProgressScale);
		super.act(delta);
	}
	
	public void setProgressScale(float scale)
	{
		mProgressScale = scale;
	}
}
