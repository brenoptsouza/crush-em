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
 * Classe factory para o exercito de Zumbis
 * 
 * @author Diego
 *
 */
public class ZombieArmyFactory {
	
	
	public static ArmyBean createArmy() {
		
		ArmyBean zumbiArmy = new ArmyBean(ArmyType.ZOMBIE);
		
		zumbiArmy.setInitialCash(3000);
		zumbiArmy.setInitialPopulation(6);
		
		zumbiArmy.setSupportedBuildings(new BuildingBean[] {
				createZombieGreenFighterBuilding(), 
				createZombieEconomyBuilding(), 
				createZombiePopulationBuilding()});
		
		return zumbiArmy;
		
	}
	
	
	public static GameObject createBaseWall(Team team, AssetManager assetMgr) {
		
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
	
	public static GameObject createBaseWallBg(Team team, AssetManager assetMgr) {
		
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
	
	
	public static GameObject createGreenFighter(Team team, int level, AssetManager assetMgr)
	{
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		// Getting the run animation frames
		final Array<AtlasRegion> walkFrames = atlas.findRegions("green-fighter-run");
		final Array<AtlasRegion> atkFrames = atlas.findRegions("green-fighter-attack");
		final Array<AtlasRegion> dieFrames = atlas.findRegions("green-fighter-die");

		// Creating the gameobject
		final GameObject greenFighter = GameFactory.createBasicGameObject(team, walkFrames.get(0), atlas);

		greenFighter.setHp(320 + level * 5);
		greenFighter.setVelocity(new Vector2(team == Team.HOME ? 90 : -90, 0));
		greenFighter.setBoundingBoxPadding(40);
		greenFighter.setOriginX(greenFighter.getWidth() / 2);
		greenFighter.setOriginY(greenFighter.getHeight() / 2);
		greenFighter.setTeam(team);
		if (team == Team.AWAY)
			greenFighter.setScaleX(-1);

		// adding the actions
		Run runAction = new Run(greenFighter, new Animation(0.09f, walkFrames));
		MeleeAttack meleeAction = new MeleeAttack(greenFighter, new Animation(0.09f, atkFrames), 3 * level, 0.7f);
		greenFighter.addAction(runAction);
		greenFighter.addAction(meleeAction);
		greenFighter.addAction(new Die(greenFighter, new Animation(0.09f, dieFrames)));

		return greenFighter;
	}
	
	private static EconomyBuildingBean createZombieEconomyBuilding() {
		
		EconomyBuildingBean bean = new EconomyBuildingBean();
		
		bean.setCostForBuilding(new int[]{230, 120, 300});
		bean.setThumbs(new String[]{"thumb-spartan-economy", "thumb-spartan-economy", "thumb-spartan-economy"});
		bean.setDescription("The Market increases the cash income for your base. Adds +5 gold to your funds per second");
		bean.setName("Market.");
		bean.setTotalProgresses(new float[]{1, 0.9f, 0.5f});
		bean.setCashIncrements(new int[]{5, 10, 15});
		
		return bean;
		
	}
	
	private static PopulationBuildingBean createZombiePopulationBuilding() {
		
		PopulationBuildingBean bean = new PopulationBuildingBean();
		
		bean.setCostForBuilding(new int[]{230, 120, 300});
		bean.setThumbs(new String[]{"thumb-spartan-population", "thumb-spartan-population", "thumb-spartan-population" });
		bean.setDescription("A mannor that increases your army's max size by 5");
		bean.setName("Mannor.");
		bean.setPopulationIncrements(new int[]{5, 6, 7});
		
		return bean;
		
	}
	
	private static BuildingBean createZombieGreenFighterBuilding() {
		
		MilitaryBuildingBean bean = new MilitaryBuildingBean();
		
		bean.setCostForBuilding(new int[]{230, 120, 300});
		bean.setThumbs(new String[]{"thumb-spartan-green-fighter","thumb-spartan-green-fighter","thumb-spartan-green-fighter"});
		bean.setDescription("The gummy fighters are weak but can take lots of damage.");
		bean.setName("Sewers");
		bean.setTotalProgresses(new float[]{5, 5, 4});
		bean.setType(BuildingType.SPARTAN_GREEN_GUY_FOR_TEST);
		
		return bean;
	}

}
