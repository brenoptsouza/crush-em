package com.breno.crushem.hud;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.breno.crushem.Battlefield;
import com.breno.crushem.GameObject;
import com.breno.crushem.Team;

public class Minimap extends Actor
{
	private Battlefield mBattleField;
	private float mMapWidth;
	private Camera mCamera;
	private ShapeRenderer mRenderer;
	
	public Minimap(Battlefield battlefield, Camera camera, float mapWidth)
	{
		mBattleField = battlefield;
		mCamera = camera;
		mRenderer = new ShapeRenderer();
		mMapWidth = mapWidth;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		final float factor = mMapWidth / this.getWidth();
		batch.end();
		
		mRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        mRenderer.setTransformMatrix(batch.getTransformMatrix());
        mRenderer.translate(getX(), getY(), 0);
        
        //Render the background
        mRenderer.begin(ShapeType.Filled);
        mRenderer.setColor(0.25f, 0.25f, 0.25f, 1);
        mRenderer.rect(0, 0, getWidth(), getHeight()/3);
        mRenderer.setColor(0.125f, 0.125f, 0.125f, 1);
        mRenderer.rect(0, getHeight()/3, getWidth(), getHeight()/3);
        mRenderer.setColor(0, 0, 0, 1);
        mRenderer.rect(0, (getHeight()/3)*2, getWidth(), getHeight()/3);
        
        Array<GameObject> allFighters = mBattleField.getAllFighters(null);
        Iterator<GameObject> i = allFighters.iterator();
        while(i.hasNext())
        {
        	GameObject f = (GameObject) i.next();
        	float x;
        	float width;
        	if(f.getTeam() == Team.HOME)
        	{
        		mRenderer.setColor(0, 0.5f, 1, 1f);
        		x = (f.getBounds().x + f.getBounds().width) / factor;
        		width = -3;
        		x--;
        	}
        	else
        	{
        		mRenderer.setColor(1, 0, 0, 1f);
        		x = (f.getBounds().x) / factor;
        		width = 3;
        		x++;
        	}
        	
        	if(x > 0 && x < getWidth())
        		mRenderer.rect(x, f.getLane() * getHeight()/3, width, getHeight()/3);
        }
        
        mRenderer.end();
        
        //Render the camera marker
        mRenderer.begin(ShapeType.Line);
        mRenderer.setColor(0.75f, 0.75f, 0, 1);
        final float camWidth = mCamera.viewportWidth;
        final float camX = mCamera.position.x - camWidth/2;
        //System.out.println("left: " + (camX/factor) + ", right: " + (camWidth/factor) + ", camWidth: " + camWidth + ", factor: " + factor);
        mRenderer.rect(camX/factor, 1, camWidth/factor, getHeight()-1);
        mRenderer.end();
        
        batch.begin();
        super.draw(batch, parentAlpha);
	}
}
