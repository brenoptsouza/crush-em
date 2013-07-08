package com.breno.factories;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.breno.crushem.ArmyType;
import com.breno.crushem.BuildingType;
import com.breno.crushem.GameObject;
import com.breno.crushem.Team;
import com.breno.crushem.GameObjectActions.Die;
import com.breno.crushem.GameObjectActions.MeleeAttack;
import com.breno.crushem.GameObjectActions.Run;
import com.breno.crushem.bean.ArmyBean;
import com.breno.crushem.bean.BuildingBean;
import com.breno.crushem.bean.EconomyBuildingBean;
import com.breno.crushem.bean.MilitaryBuildingBean;
import com.breno.crushem.bean.PopulationBuildingBean;

/**
 * Classe factory para o exercito de Piratas
 * 
 * @author Diego
 * 
 */
public class PirateArmyFactory
{

	public static ArmyBean createArmy()
	{
		ArmyBean pirateArmy = new ArmyBean(ArmyType.PIRATE);

		pirateArmy.setInitialCash(5000);
		pirateArmy.setInitialPopulation(2);

		pirateArmy.setSupportedBuildings(new BuildingBean[] { createRedFighterBuilding(), createPirateEconomyBuilding(), createPiratePopulationBuilding() });

		return pirateArmy;

	}

	private static MilitaryBuildingBean createRedFighterBuilding()
	{

		MilitaryBuildingBean bean = new MilitaryBuildingBean();

		bean.setCostForBuilding(new int[]{230, 120, 300});
		bean.setThumbs(new String[] { "thumb-spartan-red-fighter", "thumb-spartan-red-fighter", "thumb-spartan-red-fighter" });
		bean.setDescription("Trains the red-blazes, it takes some time but they are strong and well armored.");
		bean.setName("Red-blazes barracks.");
		bean.setTotalProgresses(new float[] { 500, 600, 800 });
		bean.setType(BuildingType.SPARTAN_RED_GUY_FOR_TEST);

		return bean;

	}

	private static EconomyBuildingBean createPirateEconomyBuilding()
	{

		EconomyBuildingBean bean = new EconomyBuildingBean();

		bean.setCostForBuilding(new int[]{500, 400, 450});
		bean.setThumbs(new String[]{"thumb-spartan-economy", "thumb-spartan-economy", "thumb-spartan-economy"});
		bean.setDescription("The Market increases the cash income for your base. Adds +5 gold to your funds per second");
		bean.setName("Market.");
		bean.setTotalProgresses(new float[]{1, 1, 0.9f});
		bean.setCashIncrements(new int[]{5, 7, 8});

		return bean;

	}

	private static PopulationBuildingBean createPiratePopulationBuilding()
	{

		PopulationBuildingBean bean = new PopulationBuildingBean();

		bean.setCostForBuilding(new int[]{220, 75, 100});
		bean.setThumbs(new String[]{"thumb-spartan-population", "thumb-spartan-population", "thumb-spartan-population"});
		bean.setDescription("A mannor that increases your army's max size by 5");
		bean.setName("Mannor.");
		bean.setPopulationIncrements(new int[]{5, 6, 7});

		return bean;
	}

	public static GameObject createBaseWall(Team team, AssetManager assetMgr)
	{

		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);

		final TextureRegion region = atlas.findRegion("spartan-base-fg");
		// Creating the gameobject
		final GameObject spartanBase = GameFactory.createBasicGameObject(team, region, atlas);
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

	public static GameObject createBaseWallBg(Team team, AssetManager assetMgr)
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

	public static GameObject createRedFighter(Team team, int level, AssetManager assetMgr)
	{
		// final Texture blueFighterTexture =
		// assetMgr.get("data/fighters_blue.png", Texture.class);
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		// Getting the run animation frames
		final Array<AtlasRegion> walkFrames = atlas.findRegions("red-fighter-run");
		final Array<AtlasRegion> atkFrames = atlas.findRegions("red-fighter-attack");
		final Array<AtlasRegion> dieFrames = atlas.findRegions("red-fighter-die");

		// Creating the gameobject
		final GameObject redFighter = GameFactory.createBasicGameObject(team, walkFrames.get(0), atlas);

		redFighter.setHp(120 * level);
		redFighter.setVelocity(new Vector2(team == Team.HOME ? 110 : -110, 0));
		redFighter.setBoundingBoxPadding(40);
		redFighter.setOriginX(redFighter.getWidth() / 2);
		redFighter.setOriginY(redFighter.getHeight() / 2);
		redFighter.setTeam(team);
		if (team == Team.AWAY)
			redFighter.setScaleX(-1);

		// adding the actions
		Run runAction = new Run(redFighter, new Animation(0.09f, walkFrames));
		MeleeAttack meleeAction = new MeleeAttack(redFighter, new Animation(0.09f, atkFrames), 17 + 5 * level, 0.7f);
		redFighter.addAction(runAction);
		redFighter.addAction(meleeAction);
		redFighter.addAction(new Die(redFighter, new Animation(0.09f, dieFrames)));

		return redFighter;
	}

}
