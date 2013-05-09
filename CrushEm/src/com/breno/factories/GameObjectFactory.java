package com.breno.factories;

import java.util.Comparator;
import java.util.Iterator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
import com.breno.crushem.GameObjectActions.Die;
import com.breno.crushem.GameObjectActions.MeleeAttack;
import com.breno.crushem.GameObjectActions.Run;

public class GameObjectFactory
{

	public static GameObject createBaseWall(Team team, ArmyType armyType, AssetManager assetMgr)
	{
		
		switch(armyType) {
		case PIRATE:
		case ZOMBIE:
		case SPARTAN:
			// TODO: Diferenciar as Walls de acordo com o exercito
			return createSpartanBaseWall(team, assetMgr);
		
		}
		
		throw new IllegalArgumentException("This army type is not supported yet: " + armyType);
		
	}

	private static GameObject createSpartanBaseWall(Team team, AssetManager assetMgr) {
		
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);

		final TextureRegion region = atlas.findRegion("spartan-base-fg");
		// Creating the gameobject
		final GameObject spartanBase = createBasicFighter(team, region, atlas);
		if (team == Team.AWAY)
		{
			spartanBase.setOriginX(spartanBase.getWidth() / 2);
			spartanBase.setScaleX(-1);
		}
		spartanBase.setHpBarTopMargin(200);
		spartanBase.setHp(500);
		spartanBase.setBoundingBoxPadding(20);
		spartanBase.setLane(-1);

		return spartanBase;
		
	}

	public static GameObject createSpartanBaseWallBg(Team team, AssetManager assetMgr)
	{
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);

		final TextureRegion region = atlas.findRegion("spartan-base-bg");
		// Creating the gameobject
		final GameObject spartanBaseBg = new GameObject(region);
		if (team == Team.AWAY)
		{
			spartanBaseBg.setOriginX(spartanBaseBg.getWidth() / 2);
			spartanBaseBg.setScaleX(-1);
		}
		spartanBaseBg.setLane(4);

		return spartanBaseBg;
	}

	private static GameObject createBasicFighter(Team team, TextureRegion region, TextureAtlas atlas)
	{
		final GameObject basicFighter = Pools.get(GameObject.class).obtain();
		basicFighter.reset();
		((TextureRegionDrawable) basicFighter.getDrawable()).setRegion(region);
		basicFighter.setWidth(region.getRegionWidth());
		basicFighter.setHeight(region.getRegionHeight());
		basicFighter.setHpTextures(atlas.findRegion("hp-bg"), atlas.findRegion("hp-fg"));

		return basicFighter;
	}

	private static GameObject createBlueFighter(Team team, AssetManager assetMgr)
	{
		// final Texture blueFighterTexture =
		// assetMgr.get("data/fighters_blue.png", Texture.class);
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		// Getting the run animation frames
		final Array<AtlasRegion> walkFrames = atlas.findRegions("blue-fighter-run");
		final Array<AtlasRegion> atkFrames = atlas.findRegions("blue-fighter-attack");
		final Array<AtlasRegion> dieFrames = atlas.findRegions("blue-fighter-die");

		// Creating the gameobject
		final GameObject blueFighter = createBasicFighter(team, walkFrames.get(0), atlas);

		blueFighter.setHp(50);
		blueFighter.setVelocity(new Vector2(team == Team.HOME ? 120 : -120, 0));
		blueFighter.setBoundingBoxPadding(40);
		blueFighter.setOriginX(blueFighter.getWidth() / 2);
		blueFighter.setOriginY(blueFighter.getHeight() / 2);
		blueFighter.setTeam(team);
		if (team == Team.AWAY)
			blueFighter.setScaleX(-1);

		// adding the actions
		Run runAction = new Run(blueFighter, new Animation(0.09f, walkFrames));
		MeleeAttack meleeAction = new MeleeAttack(blueFighter, new Animation(0.09f, atkFrames), 10, 0.7f);
		blueFighter.addAction(runAction);
		blueFighter.addAction(meleeAction);
		blueFighter.addAction(new Die(blueFighter, new Animation(0.09f, dieFrames)));

		return blueFighter;
	}

	private static GameObject createRedFighter(Team team, AssetManager assetMgr)
	{
		// final Texture blueFighterTexture =
		// assetMgr.get("data/fighters_blue.png", Texture.class);
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		// Getting the run animation frames
		final Array<AtlasRegion> walkFrames = atlas.findRegions("red-fighter-run");
		final Array<AtlasRegion> atkFrames = atlas.findRegions("red-fighter-attack");
		final Array<AtlasRegion> dieFrames = atlas.findRegions("red-fighter-die");

		// Creating the gameobject
		final GameObject redFighter = createBasicFighter(team, walkFrames.get(0), atlas);

		redFighter.setHp(120);
		redFighter.setVelocity(new Vector2(team == Team.HOME ? 110 : -110, 0));
		redFighter.setBoundingBoxPadding(40);
		redFighter.setOriginX(redFighter.getWidth() / 2);
		redFighter.setOriginY(redFighter.getHeight() / 2);
		redFighter.setTeam(team);
		if (team == Team.AWAY)
			redFighter.setScaleX(-1);

		// adding the actions
		Run runAction = new Run(redFighter, new Animation(0.09f, walkFrames));
		MeleeAttack meleeAction = new MeleeAttack(redFighter, new Animation(0.09f, atkFrames), 17, 0.7f);
		redFighter.addAction(runAction);
		redFighter.addAction(meleeAction);
		redFighter.addAction(new Die(redFighter, new Animation(0.09f, dieFrames)));

		return redFighter;
	}

	private static GameObject createGreenFighter(Team team, AssetManager assetMgr)
	{
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		// Getting the run animation frames
		final Array<AtlasRegion> walkFrames = atlas.findRegions("green-fighter-run");
		final Array<AtlasRegion> atkFrames = atlas.findRegions("green-fighter-attack");
		final Array<AtlasRegion> dieFrames = atlas.findRegions("green-fighter-die");

		// Creating the gameobject
		final GameObject greenFighter = createBasicFighter(team, walkFrames.get(0), atlas);

		greenFighter.setHp(320);
		greenFighter.setVelocity(new Vector2(team == Team.HOME ? 90 : -90, 0));
		greenFighter.setBoundingBoxPadding(40);
		greenFighter.setOriginX(greenFighter.getWidth() / 2);
		greenFighter.setOriginY(greenFighter.getHeight() / 2);
		greenFighter.setTeam(team);
		if (team == Team.AWAY)
			greenFighter.setScaleX(-1);

		// adding the actions
		Run runAction = new Run(greenFighter, new Animation(0.09f, walkFrames));
		MeleeAttack meleeAction = new MeleeAttack(greenFighter, new Animation(0.09f, atkFrames), 3, 0.7f);
		greenFighter.addAction(runAction);
		greenFighter.addAction(meleeAction);
		greenFighter.addAction(new Die(greenFighter, new Animation(0.09f, dieFrames)));

		return greenFighter;
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

	public static GameObject createFighter(BuildingType type, Team team, AssetManager assetMgr)
	{
		switch (type)
		{
		case SPARTAN_BLUE_GUY_FOR_TEST:
			return createBlueFighter(team, assetMgr);
		case SPARTAN_RED_GUY_FOR_TEST:
			return createRedFighter(team, assetMgr);
		case SPARTAN_GREEN_GUY_FOR_TEST:
			return createGreenFighter(team, assetMgr);

		default:
			return null;
		}
	}

	public static BuildingType[] getFighterTypes(ArmyType army)
	{
		switch (army)
		{
		case SPARTAN:
			return new BuildingType[] { BuildingType.SPARTAN_BLUE_GUY_FOR_TEST, 
					BuildingType.SPARTAN_RED_GUY_FOR_TEST, 
					BuildingType.SPARTAN_GREEN_GUY_FOR_TEST,
					BuildingType.SPARTAN_ECONOMY,
					BuildingType.SPARTAN_POPULATION};
		default:
			return null;
		}
	}

	public static TextureRegion getThumbForBuilding(BuildingType buildingType, TextureAtlas atlas)
	{
		switch (buildingType)
		{
		case SPARTAN_BLUE_GUY_FOR_TEST:
			return atlas.findRegion("thumb-spartan-blue-fighter");
		case SPARTAN_RED_GUY_FOR_TEST:
			return atlas.findRegion("thumb-spartan-red-fighter");
		case SPARTAN_GREEN_GUY_FOR_TEST:
			return atlas.findRegion("thumb-spartan-green-fighter");
		case SPARTAN_ECONOMY:
			return atlas.findRegion("thumb-spartan-economy");
		case SPARTAN_POPULATION:
			return atlas.findRegion("thumb-spartan-population");

		default:
			return null;
		}
	}

	public static int getCostForBuilding(BuildingType buildingType)
	{
		switch (buildingType)
		{
		case SPARTAN_BLUE_GUY_FOR_TEST:
			return 150;
		case SPARTAN_RED_GUY_FOR_TEST:
			return 230;
		case SPARTAN_GREEN_GUY_FOR_TEST:
			return 575;
		case SPARTAN_ECONOMY:
			return 505;
		case SPARTAN_POPULATION:
			return 200;

		default:
			return 0;
		}
	}

	public static String getDescriptionForBuilding(BuildingType buildingType)
	{
		switch (buildingType)
		{
		case SPARTAN_BLUE_GUY_FOR_TEST:
			return "The training of the blue-furious is cheap and fast, but the units are weak and do not support much damage.";
		case SPARTAN_RED_GUY_FOR_TEST:
			return "Trains the red-blazes, it takes some time but they are strong and well armored.";
		case SPARTAN_GREEN_GUY_FOR_TEST:
			return "The gummy fighters are weak but can take lots of damage.";
		case SPARTAN_ECONOMY:
			return "The Market increases the cash income for your base. Adds +5 gold to your funds per second";
		case SPARTAN_POPULATION:
			return "A mannor that increases your army's max size by 5";

		default:
			return "";
		}
	}

	public static String getBuildingName(BuildingType buildingType)
	{
		switch (buildingType)
		{
		case SPARTAN_BLUE_GUY_FOR_TEST:
			return "Blue-Furious academy";
		case SPARTAN_RED_GUY_FOR_TEST:
			return "Red-blazes barracks.";
		case SPARTAN_GREEN_GUY_FOR_TEST:
			return "Sewers";
		case SPARTAN_ECONOMY:
			return "Market";
		case SPARTAN_POPULATION:
			return "Mannor";

		default:
			return "";
		}
	}

	public static float getTrainingTimeForBuilding(BuildingType buildingType)
	{
		switch (buildingType)
		{
		case SPARTAN_BLUE_GUY_FOR_TEST:
			return 3;
		case SPARTAN_RED_GUY_FOR_TEST:
			return 9;
		case SPARTAN_GREEN_GUY_FOR_TEST:
			return 5;
		case SPARTAN_ECONOMY:
			return 1;
		default:
			return 0;
		}
	}
	
	public static int getCashIncrease(BuildingType buildingType)
	{
		switch (buildingType)
		{
		case SPARTAN_ECONOMY:
			return 5;
		default:
			return 0;
		}
	}
	
	public static int getPopulationIncrease(BuildingType buildingType)
	{
		switch (buildingType)
		{
		case SPARTAN_POPULATION:
			return 5;
		default:
			return 0;
		}
	}

}
