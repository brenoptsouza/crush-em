package com.breno.crushem.factories;

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
 * Classe factory para o exercito Espartano
 * 
 * @author Diego
 * 
 */
public class SpartanArmyFactory
{

	public static ArmyBean createArmy()
	{

		ArmyBean spartanArmy = new ArmyBean(ArmyType.SPARTAN);

		spartanArmy.setInitialCash(1200);
		spartanArmy.setInitialPopulation(3);

		spartanArmy.setSupportedBuildings(new BuildingBean[] { 
				createSpartanSoldierBuilding(),
				createSpartanDefensorBuilding(),
				createSpartanChampionBuilding(),
				createSpartanEconomyBuilding(), 
				createSpartanPopulationBuilding() });

		return spartanArmy;

	}

	private static BuildingBean createSpartanSoldierBuilding()
	{

		MilitaryBuildingBean bean = new MilitaryBuildingBean();

		bean.setCostForBuilding(new int[]{ 200, 200, 200 });
		bean.setThumbs(new String[] { "thumb-spartan-blue-fighter", "thumb-spartan-blue-fighter", "thumb-spartan-blue-fighter" });
		bean.setDescription("The spartan soldiers are brave fighter with balanced strenght and life.");
		bean.setName("Spartan Soldier Academy");
		bean.setTotalProgresses(new float[] { 3, 3, 3 });
		bean.setType(BuildingType.SPARTAN_SOLDIER);

		return bean;
	}
	
	private static BuildingBean createSpartanDefensorBuilding()
	{

		MilitaryBuildingBean bean = new MilitaryBuildingBean();

		bean.setCostForBuilding(new int[]{ 200, 200, 200 });
		bean.setThumbs(new String[] { "thumb-spartan-blue-fighter", "thumb-spartan-blue-fighter", "thumb-spartan-blue-fighter" });
		bean.setDescription("The spartan defensor are resistent fighters but can inflict only small amount of damage.");
		bean.setName("Spartan Defensor Academy");
		bean.setTotalProgresses(new float[] { 6, 6, 6 });
		bean.setType(BuildingType.SPARTAN_DEFENSOR);

		return bean;
	}
	
	private static BuildingBean createSpartanChampionBuilding()
	{

		MilitaryBuildingBean bean = new MilitaryBuildingBean();

		bean.setCostForBuilding(new int[]{ 300, 300, 300 });
		bean.setThumbs(new String[] { "thumb-spartan-blue-fighter", "thumb-spartan-blue-fighter", "thumb-spartan-blue-fighter" });
		bean.setDescription("The spartan champions are the greatest fighters from the spartan army.");
		bean.setName("Spartan Champion Academy");
		bean.setTotalProgresses(new float[] { 15, 15, 15 });
		bean.setType(BuildingType.SPARTAN_CHAMPION);

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

	public static GameObject createSpartanSoldier(Team team, int level, AssetManager assetMgr)
	{
		// final Texture blueFighterTexture =
		// assetMgr.get("data/fighters_blue.png", Texture.class);
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		// Getting the run animation frames
		final Array<AtlasRegion> walkFrames = atlas.findRegions("blue-fighter-run");
		final Array<AtlasRegion> atkFrames = atlas.findRegions("blue-fighter-attack");
		final Array<AtlasRegion> dieFrames = atlas.findRegions("blue-fighter-die");

		// Creating the gameobject
		final GameObject blueFighter = GameFactory.createBasicGameObject(team, walkFrames.get(0), atlas);

		blueFighter.setHp(50 + (10 * level));
		blueFighter.setVelocity(new Vector2(team == Team.HOME ? 120 : -120, 0));
		blueFighter.setBoundingBoxPadding(30);
		blueFighter.setOriginX(blueFighter.getWidth() / 2);
		blueFighter.setOriginY(blueFighter.getHeight() / 2);
		blueFighter.setTeam(team);
		if (team == Team.AWAY)
			blueFighter.setScaleX(-1);

		// adding the actions
		Run runAction = new Run(blueFighter, new Animation(0.09f, walkFrames));
		MeleeAttack meleeAction = new MeleeAttack(blueFighter, new Animation(0.09f, atkFrames), 10 + (5 * level), 0.7f);
		blueFighter.addAction(runAction);
		blueFighter.addAction(meleeAction);
		blueFighter.addAction(new Die(blueFighter, new Animation(0.09f, dieFrames)));

		return blueFighter;
	}
	
	public static GameObject createSpartanDefensor(Team team, int level, AssetManager assetMgr)
	{
		// final Texture blueFighterTexture =
		// assetMgr.get("data/fighters_blue.png", Texture.class);
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		// Getting the run animation frames
		final Array<AtlasRegion> walkFrames = atlas.findRegions("blue-fighter-run");
		final Array<AtlasRegion> atkFrames = atlas.findRegions("blue-fighter-attack");
		final Array<AtlasRegion> dieFrames = atlas.findRegions("blue-fighter-die");

		// Creating the gameobject
		final GameObject blueFighter = GameFactory.createBasicGameObject(team, walkFrames.get(0), atlas);

		blueFighter.setHp(120 + (30 * level));
		blueFighter.setVelocity(new Vector2(team == Team.HOME ? 40 : -40, 0));
		blueFighter.setBoundingBoxPadding(30);
		blueFighter.setOriginX(blueFighter.getWidth() / 2);
		blueFighter.setOriginY(blueFighter.getHeight() / 2);
		blueFighter.setTeam(team);
		if (team == Team.AWAY)
			blueFighter.setScaleX(-1);

		// adding the actions
		Run runAction = new Run(blueFighter, new Animation(0.09f, walkFrames));
		MeleeAttack meleeAction = new MeleeAttack(blueFighter, new Animation(0.09f, atkFrames), 5 + (2 * level), 0.6f);
		blueFighter.addAction(runAction);
		blueFighter.addAction(meleeAction);
		blueFighter.addAction(new Die(blueFighter, new Animation(0.09f, dieFrames)));

		return blueFighter;
	}
	
	public static GameObject createSpartanChampion(Team team, int level, AssetManager assetMgr)
	{
		// final Texture blueFighterTexture =
		// assetMgr.get("data/fighters_blue.png", Texture.class);
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		// Getting the run animation frames
		final Array<AtlasRegion> walkFrames = atlas.findRegions("blue-fighter-run");
		final Array<AtlasRegion> atkFrames = atlas.findRegions("blue-fighter-attack");
		final Array<AtlasRegion> dieFrames = atlas.findRegions("blue-fighter-die");

		// Creating the gameobject
		final GameObject blueFighter = GameFactory.createBasicGameObject(team, walkFrames.get(0), atlas);

		blueFighter.setHp(100 + (30 * level));
		blueFighter.setVelocity(new Vector2(team == Team.HOME ? 130 : -130, 0));
		blueFighter.setBoundingBoxPadding(30);
		blueFighter.setOriginX(blueFighter.getWidth() / 2);
		blueFighter.setOriginY(blueFighter.getHeight() / 2);
		blueFighter.setTeam(team);
		if (team == Team.AWAY)
			blueFighter.setScaleX(-1);

		// adding the actions
		Run runAction = new Run(blueFighter, new Animation(0.09f, walkFrames));
		MeleeAttack meleeAction = new MeleeAttack(blueFighter, new Animation(0.09f, atkFrames), 15 + (10 * level), 0.8f);
		blueFighter.addAction(runAction);
		blueFighter.addAction(meleeAction);
		blueFighter.addAction(new Die(blueFighter, new Animation(0.09f, dieFrames)));

		return blueFighter;
	}

	private static EconomyBuildingBean createSpartanEconomyBuilding()
	{

		EconomyBuildingBean bean = new EconomyBuildingBean();

		bean.setCostForBuilding(new int[]{450, 120, 300});
		bean.setThumbs(new String[] { "thumb-spartan-economy" });
		bean.setDescription("The Market increases the cash income for your base. Adds +5 gold to your funds per second");
		bean.setName("Market.");
		bean.setTotalProgresses(new float[] { 1, 1, 1 });
		bean.setCashIncrements(new int[] { 5, 8, 15 });

		return bean;

	}

	private static PopulationBuildingBean createSpartanPopulationBuilding()
	{

		PopulationBuildingBean bean = new PopulationBuildingBean();

		bean.setCostForBuilding(new int[]{230, 120, 300});
		bean.setThumbs(new String[] { "thumb-spartan-population", "thumb-spartan-population", "thumb-spartan-population" });
		bean.setDescription("A mannor that increases your army's max size by 5");
		bean.setName("Mannor.");
		bean.setPopulationIncrements(new int[]{5, 8, 13});

		return bean;

	}

}
