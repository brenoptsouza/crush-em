package com.breno.factories;

import static com.breno.crushem.BuildingType.SPARTAN_ECONOMY;
import static com.breno.crushem.BuildingType.SPARTAN_POPULATION;
import static com.breno.crushem.BuildingType.SPARTAN_RED_GUY_FOR_TEST;

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
 * Classe factory para o exercito de Piratas
 * 
 * @author Diego
 *
 */
public class PirateArmyFactory {
	
	
	
	public static Army createArmy() {
		
		Army pirateArmy = new Army(ArmyType.PIRATE);
		
		pirateArmy.setInitialCash(500);
		pirateArmy.setInitialPopulation(2);
		
		pirateArmy.setSupportedBuildings(new BuildingType[] {
				SPARTAN_RED_GUY_FOR_TEST, 
				SPARTAN_ECONOMY, 
				SPARTAN_POPULATION});
		
		return pirateArmy;
		
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

	
	public static GameObject createRedFighter(Team team, AssetManager assetMgr)
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

}
