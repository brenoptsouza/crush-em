package com.breno.factories;

import java.util.Comparator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.breno.crushem.ArmyType;
import com.breno.crushem.Battlefield;
import com.breno.crushem.BuildingType;
import com.breno.crushem.GameObject;
import com.breno.crushem.Team;
import com.breno.crushem.bean.ArmyBean;

/**
 * Factory basica do Game.
 * Classe que redireciona a criacao dos objetos para as Factories do exercito.
 * 
 * @author Diego
 *
 */
public class GameFactory
{
	
	
	public static ArmyBean createArmy(ArmyType armyType){
		
		switch(armyType) {
		
		case SPARTAN:
			return SpartanArmyFactory.createArmy();
		
		case PIRATE:
			return PirateArmyFactory.createArmy();
			
		case ZOMBIE:
			return ZombieArmyFactory.createArmy();
		
		default:
			throw new IllegalArgumentException("Army type not supported yet..." + armyType);
			
		}
		
	}
	
	protected static GameObject createBasicGameObject(Team team, TextureRegion region, TextureAtlas atlas)	{
		
			final GameObject basicFighter = Pools.get(GameObject.class).obtain();
			basicFighter.reset();
			((TextureRegionDrawable) basicFighter.getDrawable()).setRegion(region);
			basicFighter.setWidth(region.getRegionWidth());
			basicFighter.setHeight(region.getRegionHeight());
			basicFighter.setHpTextures(atlas.findRegion("hp-bg"), atlas.findRegion("hp-fg"));

			return basicFighter;
	}

	
	public static GameObject createBaseWall(Team team, ArmyType armyType, AssetManager assetMgr)
	{
		
		switch(armyType) {
		
		case PIRATE:
			return PirateArmyFactory.createBaseWall(team, assetMgr);
			
		case ZOMBIE:
			return ZombieArmyFactory.createBaseWall(team, assetMgr);
			
		case SPARTAN:
			return SpartanArmyFactory.createBaseWall(team, assetMgr);
		
		}
		
		throw new IllegalArgumentException("This army type is not supported yet: " + armyType);
		
	}


	public static GameObject createBaseWallBg(Team team, ArmyType armyType, AssetManager assetMgr)
	{
		
		switch(armyType) {
		
		case PIRATE:
			return PirateArmyFactory.createBaseWallBg(team, assetMgr);
			
		case ZOMBIE:
			return ZombieArmyFactory.createBaseWallBg(team, assetMgr);
			
		case SPARTAN:
			return SpartanArmyFactory.createBaseWallBg(team, assetMgr);
		
		}
		
		throw new IllegalArgumentException("This army type is not supported yet: " + armyType);
		
	}


	private static void addOnStageOrdered(GameObject gameObject, Stage stage)
	{
		stage.addActor(gameObject);
		final Array<Actor> actors = stage.getActors();
		actors.ordered = true;
		actors.sort(comparator);
	}

	private static Comparator<Actor> comparator = new Comparator<Actor>()
	{

		@Override
		public int compare(Actor o1, Actor o2)
		{
			final GameObject gameObject1 = (GameObject) o1;
			final GameObject gameObject2 = (GameObject) o2;

			if (gameObject1.getLane() > gameObject2.getLane())
				return -1;
			if (gameObject1.getLane() < gameObject2.getLane())
				return 1;
			if (gameObject1.getLane() == gameObject2.getLane())
				return (int) (gameObject2.getY() - gameObject1.getY());
			return 0;
		}
	};

	public static void spawnGameObject(GameObject gameObject, int lane, float x, float y, Stage stage, Battlefield battlefield)
	{
		gameObject.setLane(lane);
		gameObject.setPosition(x, y);
		// Add the Gameobject to the stage and battlefield
		addOnStageOrdered(gameObject, stage);
		battlefield.addFighter(gameObject);

	}

	public static GameObject createFighter(BuildingType type, Team team, int level, AssetManager assetMgr)
	{
		switch (type)
		{
		case SPARTAN_BLUE_GUY_FOR_TEST:
			return SpartanArmyFactory.createBlueFighter(team, level, assetMgr);
			
		case SPARTAN_RED_GUY_FOR_TEST:
			return PirateArmyFactory.createRedFighter(team, level, assetMgr);
			
		case SPARTAN_GREEN_GUY_FOR_TEST:
			return ZombieArmyFactory.createGreenFighter(team, level, assetMgr);

		default:
			throw new IllegalArgumentException("Fighter type not supported yet..." + type);
		}
	}

}
