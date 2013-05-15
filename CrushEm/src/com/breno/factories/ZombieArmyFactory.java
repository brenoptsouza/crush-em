package com.breno.factories;

import static com.breno.crushem.BuildingType.SPARTAN_ECONOMY;
import static com.breno.crushem.BuildingType.SPARTAN_GREEN_GUY_FOR_TEST;
import static com.breno.crushem.BuildingType.SPARTAN_POPULATION;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.breno.crushem.ArmyType;
import com.breno.crushem.BuildingType;
import com.breno.crushem.GameObject;
import com.breno.crushem.Team;
import com.breno.crushem.GameObjectActions.Die;
import com.breno.crushem.GameObjectActions.MeleeAttack;
import com.breno.crushem.GameObjectActions.Run;
import com.breno.crushem.bean.Army;

/**
 * Classe factory para o exercito de Zumbis
 * 
 * @author Diego
 *
 */
public class ZombieArmyFactory {
	
	
	public static Army createArmy() {
		
		Army zumbiArmy = new Army(ArmyType.ZOMBIE);
		
		zumbiArmy.setInitialCash(300);
		zumbiArmy.setInitialPopulation(6);
		
		zumbiArmy.setSupportedBuildings(new BuildingType[] {
				SPARTAN_GREEN_GUY_FOR_TEST, 
				SPARTAN_ECONOMY, 
				SPARTAN_POPULATION});
		
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
	
	
	public static GameObject createGreenFighter(Team team, AssetManager assetMgr)
	{
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		// Getting the run animation frames
		final Array<AtlasRegion> walkFrames = atlas.findRegions("green-fighter-run");
		final Array<AtlasRegion> atkFrames = atlas.findRegions("green-fighter-attack");
		final Array<AtlasRegion> dieFrames = atlas.findRegions("green-fighter-die");

		// Creating the gameobject
		final GameObject greenFighter = GameFactory.createBasicGameObject(team, walkFrames.get(0), atlas);

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

}
