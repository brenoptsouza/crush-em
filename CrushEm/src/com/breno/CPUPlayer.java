package com.breno;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.breno.crushem.ArmyBase;
import com.breno.crushem.ArmyType;
import com.breno.crushem.Battlefield;
import com.breno.crushem.BuildingType;
import com.breno.crushem.GameObject;
import com.breno.crushem.MilitaryBuilding;
import com.breno.crushem.Team;
import com.breno.factories.GameFactory;

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
//		addMilitaryBuilding(new MilitaryBuilding(bean));
//		addMilitaryBuilding(new MilitaryBuilding(bean));
//		addMilitaryBuilding(new MilitaryBuilding(bean));

	}

	public void update(float delta)
	{
		final ArmyBase base = mBattlefield.getCpuBase();

		if (base.consumeAvailableFighter(BuildingType.SPARTAN_BLUE_GUY_FOR_TEST))
		{
			final int lane = mRandom.nextInt(mBattlefield.getNumberOfLanes());
			final Rectangle laneBounds = mBattlefield.getLaneBounds(lane);
			final GameObject fighter = GameFactory.createFighter(BuildingType.SPARTAN_BLUE_GUY_FOR_TEST, 
					Team.AWAY, mAssetMgr);
			GameFactory.spawnGameObject(fighter, lane, mBattlefield.getLevelWidth(), (7 + laneBounds.y) + mRandom.nextInt((int) laneBounds.height - 29), mStage, mBattlefield);
		}
	}

	private void addMilitaryBuilding(MilitaryBuilding building)
	{
		mBattlefield.getCpuBase().addMilitaryBuilding(building);
	}
}
