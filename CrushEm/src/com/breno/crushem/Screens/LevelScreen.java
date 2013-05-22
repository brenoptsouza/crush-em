package com.breno.crushem.Screens;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.breno.CPUPlayer;
import com.breno.crushem.ArmyBase;
import com.breno.crushem.ArmyType;
import com.breno.crushem.Battlefield;
import com.breno.crushem.GameObject;
import com.breno.crushem.MainGame;
import com.breno.crushem.Team;
import com.breno.crushem.bean.ArmyBean;
import com.breno.crushem.hud.BaseManagementPanel;
import com.breno.crushem.hud.FighterProgressButton;
import com.breno.crushem.hud.Minimap;
import com.breno.crushem.hud.TopBar;
import com.breno.crushem.hud.TopBar.TopBarInputListener;
import com.breno.factories.GameFactory;

public class LevelScreen extends AbstractScreen
{

	public static final boolean DEBUG = false;
	public static final int GAME_WIDTH = 1280;
	public static final int GAME_HEIGHT = 720;
	
	
	FPSLogger mFPSLogger;
	Vector3 mCamVelocity;
	Stage mBattlefieldStage;
	Stage mHudStage;
	TopBar mTopBar;
	Minimap mMinimap;
	TiledMap mMap;
	OrthogonalTiledMapRenderer mMapRenderer;
	OrthoCamController mOrthoCamController;
	OrthographicCamera mBgCamera;
	Battlefield mBattlefield;
	Image mLaneIndicator;
	Image mFighterGhost;
	CPUPlayer mCpu;
	Rectangle scissors = new Rectangle();
	Rectangle clipBounds = new Rectangle();
	BaseManagementPanel mManagementPanel;
	
	ArmyBean mHomeArmy;
	ArmyBean mAwayArmy;
	
	public LevelScreen(MainGame game, ArmyBean homeArmy, ArmyBean awayArmy)
	{
		super(game);
		
		mHomeArmy = homeArmy;
		mAwayArmy = awayArmy;
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		mOrthoCamController.update(delta);
		
		mBattlefield.update(delta);
		mBattlefieldStage.act();
		
		final OrthographicCamera cam = (OrthographicCamera) mBattlefieldStage.getCamera();
		cam.update();
		
		clipBounds.x = mBattlefieldStage.getCamera().position.x - GAME_WIDTH/2;
		clipBounds.y = mBattlefieldStage.getCamera().position.y - GAME_HEIGHT/2;
		clipBounds.width = GAME_WIDTH;
		clipBounds.height = GAME_HEIGHT;
		
		ScissorStack.calculateScissors(cam, mMapRenderer.getSpriteBatch().getTransformMatrix(), clipBounds, scissors);
		ScissorStack.pushScissors(scissors);
		
		mMapRenderer.setView(mBgCamera);
		mMapRenderer.render(new int[]{0});
		
		mMapRenderer.setView((OrthographicCamera) cam);
		mMapRenderer.render(new int[]{1});
		mBattlefieldStage.draw();
		
		mHudStage.act();
		mHudStage.draw();
		mCpu.update(delta);
		//mFPSLogger.log();
		ScissorStack.popScissors();
	}

	@Override
	public void resize(int width, int height)
	{
		mBattlefieldStage.setViewport(GAME_WIDTH, GAME_HEIGHT, true);
		mHudStage.setViewport(GAME_WIDTH, GAME_HEIGHT, true);
		
		mBattlefieldStage.getCamera().translate(- mBattlefieldStage.getGutterWidth(), - mBattlefieldStage.getGutterHeight(), 0);
		mHudStage.getCamera().translate(-mHudStage.getGutterWidth(), -mHudStage.getGutterHeight(), 0);
		
		mBattlefieldStage.getCamera().position.set(GAME_WIDTH/2, GAME_HEIGHT/2,0);
		
		mMapRenderer.setView((OrthographicCamera) mBattlefieldStage.getCamera());
		mOrthoCamController = new OrthoCamController((OrthographicCamera) mBattlefieldStage.getCamera());
		mMinimap.setX(0);
		mMinimap.setX(0);
		mMinimap.setWidth(GAME_WIDTH);
		mMinimap.setHeight(36);
		mTopBar.setWidth(GAME_WIDTH);
		mTopBar.setHeight(91);
		mTopBar.invalidate();
		mTopBar.setY(GAME_HEIGHT - mTopBar.getHeight());
		mManagementPanel.setX(GAME_WIDTH/2 - mManagementPanel.getWidth()/2);
		mManagementPanel.setY(GAME_HEIGHT);
		mManagementPanel.setVisible(false);
		
		
		mBgCamera = new OrthographicCamera(GAME_WIDTH, GAME_HEIGHT);
		mBgCamera.position.set(GAME_WIDTH/2, GAME_HEIGHT/2,0);
		mBgCamera.update();
		
		InputMultiplexer plex = new InputMultiplexer(mHudStage, mOrthoCamController);
		Gdx.input.setInputProcessor(plex);
	}
	
	private void initBattlefield()
	{
		
		mBattlefield = new Battlefield(this, new ArmyBase(mHomeArmy), new ArmyBase(mAwayArmy));
		
		final MapLayer objectsLayer = mMap.getLayers().get("lanes");
		final MapObjects mapObjects = objectsLayer.getObjects();
		final Iterator<MapObject> i = mapObjects.iterator();
		while(i.hasNext())
		{
			final RectangleMapObject rectMapObj = (RectangleMapObject) i.next();
			mBattlefield.addLane(Integer.parseInt(rectMapObj.getName()), rectMapObj.getRectangle());
		}
	}
	
	public void declareWinner(ArmyBase mPlayerBase) {

		System.out.println(mPlayerBase.getTeam());
	}


	@Override
	public void show()
	{
		mMap = mGame.assetMgr.get("data/levels/level1.tmx", TiledMap.class);
		initBattlefield();
		int width = (Integer)mMap.getProperties().get("width");
		int tileWidth = (Integer)mMap.getProperties().get("tilewidth");
		final float levelWidth = width * tileWidth;
		mBattlefield.setLevelWidth(levelWidth);
		
		mMapRenderer = new OrthogonalTiledMapRenderer(mMap);
		mBattlefieldStage = new Stage(0,0,true);
		mFighterGhost = new Image();
		final TextureAtlas atlas = mGame.assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		mLaneIndicator = new Image(atlas.findRegion("lane-indicator"));
		mLaneIndicator.setHeight(128);
		mLaneIndicator.setWidth(1258);
		mLaneIndicator.setVisible(false);
		
		mTopBar = new TopBar(mGame.assetMgr, mBattlefield, new TopBarButtonsInputListener());
		mMinimap = new Minimap(mBattlefield, mBattlefieldStage.getCamera(), levelWidth);
		mManagementPanel = new BaseManagementPanel(mBattlefield, mGame.assetMgr);
		
		mHudStage = new Stage(0,0,true);
		mHudStage.addActor(mMinimap);
		mHudStage.addActor(mLaneIndicator);
		mHudStage.addActor(mTopBar);
		mHudStage.addActor(mManagementPanel);
		
		mCamVelocity = new Vector3(0, 0, 0);
		mFPSLogger = new FPSLogger();
		
		addBaseWalls();
		
		mCpu = new CPUPlayer(mBattlefield, mBattlefieldStage, mAwayArmy.getArmyType(), mGame.assetMgr);
	}
	
	private void addBaseWalls()
	{
		final ArmyType homeArmyType = mHomeArmy.getArmyType();
		final ArmyType enemyArmyType = mAwayArmy.getArmyType();
		
		final GameObject playerBaseWall = GameFactory.createBaseWall(Team.HOME, homeArmyType, mGame.assetMgr);
		final GameObject cpuBaseWall = GameFactory.createBaseWall(Team.AWAY, enemyArmyType, mGame.assetMgr);
		
		mBattlefield.setHomeBaseWall(playerBaseWall);
		mBattlefield.setAwayBaseWall(cpuBaseWall);
		cpuBaseWall.setX(mBattlefield.getLevelWidth() - cpuBaseWall.getWidth());
	
		final GameObject playerBaseWallBg = GameFactory.createBaseWallBg(Team.HOME, homeArmyType, mGame.assetMgr);
		final GameObject cpuBaseWallBg = GameFactory.createBaseWallBg(Team.AWAY, enemyArmyType, mGame.assetMgr);
		cpuBaseWallBg.setX(mBattlefield.getLevelWidth() - cpuBaseWall.getWidth());

		mBattlefieldStage.getActors().insert(0, playerBaseWallBg);
		mBattlefieldStage.getActors().insert(0, cpuBaseWallBg);
		mBattlefieldStage.addActor(playerBaseWall);
		mBattlefieldStage.addActor(cpuBaseWall);
		
	}

	@Override
	public void hide()
	{
		
	}

	@Override
	public void pause()
	{
		
	}

	@Override
	public void resume()
	{
		
	}

	@Override
	public void dispose()
	{
		
	}
	
	public class OrthoCamController extends InputAdapter 
	{
		final OrthographicCamera camera;
		final Vector3 curr = new Vector3();
		final Vector3 last = new Vector3(-1, -1, -1);
		final Vector3 delta = new Vector3();
		private static final int SPEED = 30;
		private static final float CAM_SPEED_CONSERVATION_FACTOR = 0.90f;
 
		public OrthoCamController(OrthographicCamera camera) {
			this.camera = camera;
		}
		
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button)
		{
			System.out.println("ORTHO_CAM_CONTROLLER");
			return super.touchDown(screenX, screenY, pointer, button);
		}
 
		@Override
		public boolean touchDragged(int x, int y, int pointer) {
			camera.unproject(curr.set(x, y, 0));
			if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
				camera.unproject(delta.set(last.x, last.y, 0));
				delta.sub(curr);
				camera.position.add(delta.x, 0, 0);
			}
			last.set(x, y, 0);
			keepCamInBounds();
			
			
			return false;
		}
 
		@Override
		public boolean touchUp(int x, int y, int pointer, int button) {
			last.set(-1, -1, -1);
			mCamVelocity.set(delta.x, 0, 0);
			return false;
		}
		
		public void update(float delta)
		{
			camera.position.add(mCamVelocity.cpy().scl(delta*SPEED));
			if(mCamVelocity.len() > 1)
			{
				mCamVelocity.scl(CAM_SPEED_CONSERVATION_FACTOR);
				keepCamInBounds();
			}
			else
				mCamVelocity.scl(0);
		}
		
		private void keepCamInBounds()
		{
			final float camLeft = camera.position.x - camera.viewportWidth/2;
			final float camRight = camera.position.x + camera.viewportWidth/2;
			//Gdx.app.log("MyGame", "x: " + stage.getCamera().position.x + ", left: " + camLeft + ", right: "+ camRight + ") ... STAGE 2 Width: " + stage2.setgetCamera().viewportWidth);
			if(camLeft < 0)
			{
				camera.position.x = camera.viewportWidth/2;
				//camSpeed.set(0, 0, 0);
			}
			else if(camRight > mBattlefield.getLevelWidth())
			{
				camera.position.x = mBattlefield.getLevelWidth() - camera.viewportWidth/2;
				//camSpeed.set(0, 0, 0);
			}
		}
	}
	
	public class TopBarButtonsInputListener implements TopBarInputListener
	{
		Random mRandom = new Random();
		final int MARGIN_BOTTOM = 64;
		final int LANE_HEIGHT = 128;
		int mCurrentLane = -1;
		Rectangle mCurrentRectangle;
		float mCurrentScaleFac;
		private GameObject mFighter;
		final Vector3 battlefieldCoord = new Vector3();
		
		@Override
		public void touchDownFighter(float x, float y, FighterProgressButton button)
		{
			System.out.println("TOP BAR BUTTON");
			mFighter = GameFactory.createFighter(button.mFighterType, Team.HOME, mGame.assetMgr);
			mFighterGhost.setDrawable(mFighter.getDrawable());
			mFighterGhost.setColor(1, 1, 1, 0.6f);
			
			final float width = mFighter.getWidth();
			final float height = mFighter.getHeight();
			mFighterGhost.setWidth(width);
			mFighterGhost.setHeight(height);
			
			mHudStage.addActor(mFighterGhost);
			mFighterGhost.setX(x - width/2);
			mFighterGhost.setY(y);
		}

		@Override
		public void touchDraggedFighter(float x, float y, FighterProgressButton fighterButton)
		{
			final float width = mFighterGhost.getWidth();
			mFighterGhost.setX(x - width/2);
			mFighterGhost.setY(y);
			
			final int numberOfLanes = mBattlefield.getNumberOfLanes();
			mCurrentLane = -1;
			battlefieldCoord.set(x, mBattlefieldStage.getCamera().viewportHeight - y, 0);
			mBattlefieldStage.getCamera().unproject(battlefieldCoord);
			for(int i = 0 ; i < numberOfLanes ; ++i)
			{
				final Rectangle r = mBattlefield.getLaneBounds(i);
				if(r.contains(battlefieldCoord.x, battlefieldCoord.y))
				{
					mCurrentLane = i;
					mCurrentScaleFac = mBattlefield.getLaneScaleFactor(i);
					mCurrentRectangle = r;
					break;
				}
			}
			
			if(mCurrentLane >= 0)
			{
				mLaneIndicator.setVisible(true);
				mLaneIndicator.setScale(mCurrentScaleFac);
				mLaneIndicator.setX(10);
				mLaneIndicator.setY(mCurrentLane * LANE_HEIGHT + MARGIN_BOTTOM);
				mFighterGhost.setScale(mCurrentScaleFac);
			}
			else
				mLaneIndicator.setVisible(false);
		}

		@Override
		public void touchUpFighter(float x, float y, FighterProgressButton fighterButton)
		{
			mLaneIndicator.setVisible(false);
			mFighterGhost.remove();
			if(mCurrentLane >=0 && mCurrentRectangle != null)
			{
				mFighter.setScale(mCurrentScaleFac);
				mFighter.getVelocity().scl(mCurrentScaleFac);
				GameFactory.spawnGameObject(mFighter, mCurrentLane, /*-mFighter.getWidth()*/0, (7 + mCurrentRectangle.y) + mRandom.nextInt((int)mCurrentRectangle.height-29), mBattlefieldStage, mBattlefield);
				fighterButton.popFighter();
			}
		}

		@Override
		public void manageButtonClicked()
		{
			if(!mManagementPanel.isVisible())
				mManagementPanel.animateIn();

		}
		
	}

}
