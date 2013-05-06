package com.breno.crushem.GameObjectActions;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.breno.crushem.GameObject;

public class Run extends GameObjectAction
{
	private Animation mRunAnimation;
	
	public Run(GameObject gameObject, Animation runAnimation)
	{
		super(gameObject);
		mRunAnimation = runAnimation;
	}

	@Override
	public boolean act(float delta)
	{
		if(mGameObject.isAlive() && !mGameObject.isStopped())
		{
			final float animationAcc = mGameObject.mAnimationAccumulator;
			final TextureRegion frame = mRunAnimation.getKeyFrame(animationAcc, true);
			mGameObject.setDrawable(new TextureRegionDrawable(frame));
			final float newX = mGameObject.getX() + (mGameObject.getVelocity().x * delta);
			final float newY = mGameObject.getY() + (mGameObject.getVelocity().y * delta);
			mGameObject.setPosition(newX, newY);
		}
		return false;
	}

}
