package com.breno;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.breno.crushem.ArmyBase;
import com.breno.crushem.ArmyType;
import com.breno.crushem.Battlefield;
import com.breno.crushem.Building;
import com.breno.crushem.BuildingType;
import com.breno.crushem.GameObject;
import com.breno.crushem.MilitaryBuilding;
import com.breno.crushem.PopulationBuilding;
import com.breno.crushem.Team;
import com.breno.crushem.bean.MilitaryBuildingBean;
import com.breno.crushem.bean.PopulationBuildingBean;
import com.breno.crushem.factories.GameFactory;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

public class CPUPlayer
{
	private Battlefield mBattlefield;
	private Random mRandom;
	private Stage mStage;
	private AssetManager mAssetMgr;

	public CPUPlayer(Battlefield battlefield, Stage stage, ArmyType armyType, AssetManager assetMgr)
	{
		this.mBattlefield = battlefield;
		this.mStage = stage;
		this.mAssetMgr = assetMgr;
		mRandom = new Random();
		
		
		
//				buildMilitaryBuilding(mBattlefield.getCpuBase(), BuildingType.SPARTAN_BLUE_GUY_FOR_TEST, 20, 50, "Building Description");

		// TODO This is just for tests
		for(int i = 0 ; i < battlefield.getCpuBase().getBuildingSupported().length ; ++i)
		{
			if(battlefield.getCpuBase().getBuildingSupported()[i] instanceof MilitaryBuildingBean)
				addMilitaryBuilding(new MilitaryBuilding(((MilitaryBuildingBean)battlefield.getCpuBase().getBuildingSupported()[i]), battlefield.getCpuBase()));
			if(battlefield.getCpuBase().getBuildingSupported()[i] instanceof PopulationBuildingBean)
				addBuilding(new PopulationBuilding(((PopulationBuildingBean)battlefield.getCpuBase().getBuildingSupported()[i]), battlefield.getCpuBase()));
				
		}
//		addMilitaryBuilding(new MilitaryBuilding(bean));

	}

	public void update(float delta)
	{
		final ArmyBase base = mBattlefield.getCpuBase();

		final Array<MilitaryBuilding> buildings = base.getMilitaryBuildings();
		Iterator<MilitaryBuilding> i = buildings.iterator();
		while(i.hasNext())
		{
			final MilitaryBuilding mb = i.next();
			final GameObject fighter = mb.getFighter(mAssetMgr);
			if(fighter != null)
			{
				final int lane = mRandom.nextInt(mBattlefield.getNumberOfLanes());
				final Rectangle laneBounds = mBattlefield.getLaneBounds(lane);
				GameFactory.spawnGameObject(fighter, lane, mBattlefield.getLevelWidth(), (7 + laneBounds.y) + mRandom.nextInt((int) laneBounds.height - 29), mStage, mBattlefield);
				mb.reset();
			}
		}
	}

	private void addMilitaryBuilding(MilitaryBuilding building)
	{
		mBattlefield.getCpuBase().addMilitaryBuilding(building);
	}
	private void addBuilding(Building building)
	{
		mBattlefield.getCpuBase().addBuilding(building);
	}
}
