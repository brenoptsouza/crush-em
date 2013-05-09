package com.breno.crushem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Pools;
import com.breno.crushem.Screens.LevelScreen;

public class GameObject extends Image
{
	private float BOUNDING_BOX_PADDING;
	private float BOUNDING_BOX_PADDING_2;

	private float mHpBarTopMargin = 0;
	private int mTotalHp;
	private int mHp;
	private Rectangle mBounds;
	private Vector2 mVelocity;
	private Team mTeam;
	private int mLane;
	public float mAnimationAccumulator;
	private Battlefield mBattlefield;
	private TextureRegion mHpBg;
	private TextureRegion mHpFg;
	private boolean mIsStopped = false;
	private ShapeRenderer sr;

	public GameObject(TextureRegion image)
	{
		super(image);
		mBounds = new Rectangle();
		sr = new ShapeRenderer();
	}

	public void setBoundingBoxPadding(float value)
	{
		BOUNDING_BOX_PADDING = value;
		BOUNDING_BOX_PADDING_2 = BOUNDING_BOX_PADDING * 2;
	}

	public boolean isAlive()
	{
		return mHp > 0;
	}

	public void setHpBarTopMargin(float value)
	{
		mHpBarTopMargin = value;
	}

	@Override
	public void act(float delta)
	{
		mAnimationAccumulator += delta;
		super.act(delta);
	}

	public Vector2 getVelocity()
	{
		return mVelocity;
	}

	public void setVelocity(Vector2 velocity)
	{
		this.mVelocity = velocity;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
		if (isAlive())
		{
			batch.draw(mHpBg, getX() + 40, getHeight() + getY() - mHpBarTopMargin, getWidth() - 80, 4);
			batch.draw(mHpFg, getX() + 40, getHeight() + getY() - mHpBarTopMargin, ((getWidth() - 80) * mHp) / mTotalHp, 4);
		}
		if (LevelScreen.DEBUG)
		{
			batch.end();

			sr.setProjectionMatrix(batch.getProjectionMatrix());
			sr.setTransformMatrix(batch.getTransformMatrix());

			sr.begin(ShapeType.Line);
			final Rectangle r = getBounds();
			sr.rect(r.x, r.y, r.width, r.height);
			sr.end();

			batch.begin();
		}
	}

	public void setHp(int hp)
	{
		mTotalHp = hp;
		mHp = hp;
	}

	public void setLane(int lane)
	{
		mLane = lane;
	}

	public void setBattlefield(Battlefield battlefield)
	{
		this.mBattlefield = battlefield;
	}

	public Battlefield getBattlefield()
	{
		return mBattlefield;
	}

	public int getLane()
	{
		return mLane;
	}

	public Rectangle getBounds()
	{
		mBounds.set(getX() + BOUNDING_BOX_PADDING, getY() + BOUNDING_BOX_PADDING, getWidth() - BOUNDING_BOX_PADDING_2, getHeight() - BOUNDING_BOX_PADDING_2);
		return mBounds;
	}

	public boolean isStopped()
	{
		return mIsStopped;
	}

	public void setStopped(boolean stopped)
	{
		mIsStopped = stopped;
	}

	public void takeDamage(float strenght)
	{
		mHp -= strenght;
	}

	public Team getTeam()
	{
		return mTeam;
	}

	public void setTeam(Team team)
	{
		this.mTeam = team;
	}

	public void setHpTextures(AtlasRegion bg, AtlasRegion fg)
	{
		mHpBg = bg;
		mHpFg = fg;
	}

	public void reset()
	{
		mHp = 0;
		mIsStopped = false;
		setColor(1, 1, 1, 1);
		mBattlefield = null;
		mAnimationAccumulator = 0;
		mBounds.set(0, 0, 0, 0);
		mHpBarTopMargin = 0;
		mHpBg = null;
		mHpFg = null;
		mVelocity = null;
		Pools.freeAll(getActions());
		getActions().clear();
	}

}
