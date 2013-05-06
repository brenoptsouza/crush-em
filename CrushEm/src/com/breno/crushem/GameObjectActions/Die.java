package com.breno.crushem.GameObjectActions;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pools;
import com.breno.crushem.GameObject;

public class Die extends GameObjectAction
{
	private Animation mAnimation;
	private boolean mJustDied = true;
	private float mAnimAcc = 0;
	private TextureRegionDrawable mTexRegionDrawable = new TextureRegionDrawable();
	private SequenceAction mFadeOutAfterDeath;
	
	public Die(GameObject gameObject, Animation dieAnimation)
	{
		super(gameObject);
		mAnimation = dieAnimation;
	}

	@Override
	public boolean act(float delta) 
	{
		if(!mGameObject.isAlive())
		{
			if(mJustDied)
			{
				mJustDied = false;
				mGameObject.setStopped(true);
				mGameObject.getBattlefield().removeFighter(mGameObject);
				final Action fadeOutAfterDeath = Actions.sequence(Actions.delay(0.8f), Actions.fadeOut(0.8f), Actions.run(new Runnable()
				{
					@Override
					public void run()
					{
						Die.this.mGameObject.remove();
						Pools.get(GameObject.class).free(Die.this.mGameObject);
					}
				}));
				mGameObject.addAction(fadeOutAfterDeath);
			}
			final TextureRegion frame = mAnimation.getKeyFrame(mAnimAcc, false);
			mTexRegionDrawable.setRegion(frame);
			mGameObject.setDrawable(mTexRegionDrawable);
			final boolean hasFinished = mAnimation.isAnimationFinished(mAnimAcc);
			mAnimAcc += delta;
			return hasFinished;
		}
		return false;
	}
}
