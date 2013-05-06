package com.breno.crushem.GameObjectActions;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.breno.crushem.Battlefield;
import com.breno.crushem.GameObject;
import com.breno.crushem.Team;

public class MeleeAttack extends GameObjectAction
{
	
	private Animation mAtkAnimation;
	private GameObject mCurrentTarget;
	private float mStrenght;
	private float mAtkSpeed;
	private float mAtkTimeAcc;
	private Random mRandom;
	
	public MeleeAttack(GameObject gameObject, Animation runAnimation, float strenght, float attackSpd)
	{
		super(gameObject);
		mAtkAnimation = runAnimation;
		mStrenght = strenght;
		mRandom = new Random();
		mAtkSpeed = attackSpd;
	}

	private void attackCurrentTarget(float deltaTime) 
	{
		if(mCurrentTarget.isAlive() && mAtkTimeAcc >= mAtkSpeed)
		{
			mCurrentTarget.takeDamage(mStrenght - mRandom.nextInt((int)mStrenght));
			mAtkTimeAcc = 0;
		}
		mAtkTimeAcc += deltaTime;
	}
	
	@Override
	public boolean act(float delta)
	{
		if(mGameObject.isAlive())
		{
			//first we check if he is already attacking something
			if(mCurrentTarget == null)
			{
				final Battlefield battlefield = mGameObject.getBattlefield();
				//now see he can attack another player
				mCurrentTarget = mGameObject.getBattlefield().getAvailableTarget(mGameObject);
				if(mCurrentTarget != null)
					mGameObject.setStopped(true);
				else
				{
					//in the last case, see if the castle is in range
					final Rectangle laneBounds = battlefield.getLaneBounds(mGameObject.getLane());
					final Rectangle fighterBounds = mGameObject.getBounds();
					final boolean canAttackCastle = mGameObject.getTeam() == Team.HOME ? fighterBounds.x + fighterBounds.width/2 >= laneBounds.x + laneBounds.width && battlefield.getAwayBaseWall().isAlive()
							: fighterBounds.x + fighterBounds.width/2 <= laneBounds.x && battlefield.getHomeBaseWall().isAlive();
					if(canAttackCastle)
					{
						mCurrentTarget =  mGameObject.getTeam() == Team.HOME ? battlefield.getAwayBaseWall() : battlefield.getHomeBaseWall();
						mGameObject.setStopped(true);
					}
				}
			}
			if(mCurrentTarget != null)
			{
				final float animationAcc = mGameObject.mAnimationAccumulator;
				final TextureRegion frame = mAtkAnimation.getKeyFrame(animationAcc, true);
				mGameObject.setDrawable(new TextureRegionDrawable(frame));
				
				attackCurrentTarget(delta);
				if(!mCurrentTarget.isAlive())
				{
					mCurrentTarget = null;
					mGameObject.setStopped(false);
				}
			}
		}
		return false;
	}

}
