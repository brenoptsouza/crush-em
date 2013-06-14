package com.breno.crushem.hud;

import java.util.Iterator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.breno.crushem.BuildingType;
import com.breno.crushem.MilitaryBuilding;
import com.breno.crushem.bean.BuildingBean;
import com.breno.factories.GameFactory;

public class FighterProgressButton extends Group
{
	public static final float SPACE_BETWEEN_CELLS = 15;
	public static final float MARGIN = 6;
	
	private Array<FighterButtonCell> mCells;
	
	//Every building on this array must have the same fighter type
	private Array<MilitaryBuilding> mBuildings;
	private boolean mHasAvailableUnit;
	private TextureAtlas mAtlas;
	
	private int mPivot;
	private String mFighterButtonThumb;
	public BuildingType mFighterType;
	
	public FighterProgressButton(AssetManager assetMgr, String fighterButtonThumb, BuildingType fighterType)
	{
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		mAtlas = atlas;
		mFighterButtonThumb = fighterButtonThumb;
		mBuildings = new Array<MilitaryBuilding>();
		mBuildings.ordered = true;
		mFighterType = fighterType;
		mCells = new Array<FighterButtonCell>();
		
		LabelStyle counterStyle = new LabelStyle();
		counterStyle.font = assetMgr.get("data/fonts/charlemagne.fnt", BitmapFont.class);
		
	}
	
	@Override
	public void act(float delta)
	{
		int availableUnitCount = 0;
		for(int i = 0 ; i < mCells.size ; ++i)
		{
			final MilitaryBuilding mb = mBuildings.get(i);
			if(mb.getProgress() >= mb.getTotal())
				availableUnitCount++;
			final float timeLeft = mb.getTotal() - mb.getProgress();
			mCells.get(i).setProgressScale(timeLeft/mb.getTotal());
		}
		
		if(availableUnitCount == 0)
		{
			mHasAvailableUnit = false;
		}
		else
		{
			mHasAvailableUnit = true;
		}
		super.act(delta);
	}

	public Array<MilitaryBuilding> getBuildings()
	{
		return mBuildings;
	}

	public void addMilitaryBuilding(MilitaryBuilding militaryBuilding)
	{
		final TextureAtlas atlas = mAtlas;
		
		final FighterButtonCell cell = new FighterButtonCell(mAtlas.findRegion(mFighterButtonThumb),
				atlas.createPatch("thumb-portrait"), atlas.findRegion("fighter-progress"), MARGIN);

		mCells.add(cell);
		
		final int insertPoint = insertBuildingOrdered(militaryBuilding);
		
		setWidth(cell.getWidth() + (mBuildings.size - 1) * SPACE_BETWEEN_CELLS);
		setHeight(cell.getHeight());
		
		addActor(cell);
		
		cell.setZIndex(0);
		
		cell.setTouchable(Touchable.disabled);
		
		cell.setX((mBuildings.size - 1) * SPACE_BETWEEN_CELLS);
		
		final FighterButtonCell insertionCell = mCells.get(insertPoint);
		insertionCell.setY(insertionCell.getHeight());
		insertionCell.addAction(Actions.moveBy(0, -insertionCell.getHeight(), 0.2f));
	}

	private int insertBuildingOrdered(MilitaryBuilding militaryBuilding)
	{
		final Iterator<MilitaryBuilding> i = mBuildings.iterator();
		int index = 0;
		while(i.hasNext())
		{
			final MilitaryBuilding mb = i.next();
			final float timeLeft = mb.getTotal() - mb.getProgress();
			if(militaryBuilding.getTotal() - militaryBuilding.getProgress() < timeLeft)
				break;
			index++;
		}
		mBuildings.insert(index, militaryBuilding);
		return index;
	}
	
	public boolean hasAvailableUnit()
	{
		return mHasAvailableUnit;
	}
	
	public MilitaryBuilding getFirstAvailableBuilding()
	{
		if(mHasAvailableUnit)
			return mBuildings.first();
		else 
			return null;
	}

	public void popFighter()
	{
		final MilitaryBuilding mb = mBuildings.first();
		mBuildings.removeIndex(0);
		mb.setProgress(0);
		mPivot = insertBuildingOrdered(mb);
		
		final FighterButtonCell first = mCells.first();
		first.setY(0);
		first.setColor(1,1,1,1);
		
		for(int i = 0 ; i < mPivot ; ++i)
		{
			final FighterButtonCell c = mCells.get(i);
			c.translate(SPACE_BETWEEN_CELLS, 0);
			final MoveByAction left = Actions.moveBy(-SPACE_BETWEEN_CELLS, 0, 0.2f);
			
			c.addAction(left);
		}
		
		final FighterButtonCell pivot = mCells.get(mPivot);
		pivot.setY(pivot.getHeight());
		
		final MoveByAction down = Actions.moveBy(0, -pivot.getHeight(), 0.3f);
		down.setInterpolation(Interpolation.linear);
		
		pivot.addAction(down); 
	}
	
}
