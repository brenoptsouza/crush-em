package com.breno.crushem.Screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.breno.crushem.ArmyType;
import com.breno.crushem.GameObject;
import com.breno.crushem.MainGame;
import com.breno.crushem.GameObjectActions.Die;
import com.breno.crushem.GameObjectActions.MeleeAttack;
import com.breno.crushem.GameObjectActions.Run;

public class LevelLoadingScreen extends AbstractLoadingScreen
{

	public LevelLoadingScreen(MainGame game)
	{
		super(game);
	}

	@Override
	protected void loadData(AssetManager assetManager)
	{
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load("data/levels/level1.tmx", TiledMap.class);
		assetManager.load("data/game_screen.atlas", TextureAtlas.class);
		
		//init pools
		final Pool<MoveByAction> movebypool = Pools.get(MoveByAction.class);
		for(int i = 0 ; i < 30 ; ++i)
		{
			MoveByAction m = new MoveByAction();
			movebypool.free(m);
		}
		
		final Pool<SequenceAction> sequencepool = Pools.get(SequenceAction.class);
		for(int i = 0 ; i < 32 ; ++i)
		{
			SequenceAction m = new SequenceAction();
			sequencepool.free(m);
		}
		final Pool<RunnableAction> runpool = Pools.get(RunnableAction.class);
		for(int i = 0 ; i < 32 ; ++i)
		{
			RunnableAction m = new RunnableAction();
			runpool.free(m);
		}
		final Pool<AlphaAction> alphapool = Pools.get(AlphaAction.class);
		for(int i = 0 ; i < 32 ; ++i)
		{
			AlphaAction m = new AlphaAction();
			alphapool.free(m);
		}
		final Pool<DelayAction> delaypool = Pools.get(DelayAction.class);
		for(int i = 0 ; i < 16 ; ++i)
		{
			DelayAction m = new DelayAction();
			delaypool.free(m);
		}
		
		final Pool<GameObject> gameObjectPool= Pools.get(GameObject.class);
		for(int i = 0 ; i < 128 ; ++i)
		{
			GameObject m = new GameObject(new TextureRegion());
			gameObjectPool.free(m);
		}
		
		final Pool<Run> runPool= Pools.get(Run.class);
		for(int i = 0 ; i < 128 ; ++i)
		{
			Run m = new Run(null, null);
			runPool.free(m);
		}
		
		final Pool<MeleeAttack> meleeattackPool = Pools.get(MeleeAttack.class);
		for(int i = 0 ; i < 128 ; ++i)
		{
			MeleeAttack m = new MeleeAttack(null, null, 0, 0);
			meleeattackPool.free(m);
		}
		
		final Pool<Die> diePool= Pools.get(Die.class);
		for(int i = 0 ; i < 128 ; ++i)
		{
			Die m = new Die(null, null);
			diePool.free(m);
		}
	}

	@Override
	protected void onFinishLoading()
	{
		// TODO: [BRENO] Aqui será o ponto de partida para passar a escolha do exercito
		mGame.setScreen(new LevelScreen(mGame, ArmyType.SPARTAN, ArmyType.ZOMBIE));
	}

}
