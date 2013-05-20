package com.breno.crushem.hud;

import java.util.Iterator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.breno.crushem.Battlefield;
import com.breno.crushem.EconomyBuilding;
import com.breno.crushem.MilitaryBuilding;
import com.breno.crushem.Screens.LevelScreen;
import com.breno.crushem.bean.BuildingBean;
import com.breno.crushem.bean.EconomyBuildingBean;
import com.breno.crushem.bean.MilitaryBuildingBean;
import com.breno.crushem.bean.PopulationBuildingBean;

public class BaseManagementPanel extends Group
{
	public static final float WIDTH = 1220f;
	public static final float HEIGHT = 605f;
	
	private Battlefield mBattlefield;
	private AssetManager mAssetManager;
	private DescriptionPanel mDescPanel;
	private BuildPanel mBuildPanel;
	private UpgradePanel mUpgradePanel;
	private Interpolation mSwingOutInterpolation = new Interpolation.SwingOut(1.0f);
	private Interpolation mSwingInInterpolation = new Interpolation.SwingIn(1.0f);
	
	public BaseManagementPanel(Battlefield battlefield, AssetManager assetMgr)
	{
		super();
		mBattlefield = battlefield;
		mAssetManager = assetMgr;
		
		setWidth(WIDTH);
		setHeight(HEIGHT);
		
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		
		final Image bg = new Image(atlas.createPatch("thumb-portrait"));
		bg.setWidth(WIDTH);
		bg.setHeight(HEIGHT);
		addActor(bg);
		
		mDescPanel = new DescriptionPanel();
		mDescPanel.setX(15);
		mDescPanel.setY(15);
		addActor(mDescPanel);
		
		mUpgradePanel = new UpgradePanel();
		mUpgradePanel.setX(15 + DescriptionPanel.WIDTH + mDescPanel.getX());
		mUpgradePanel.setY(15);
		addActor(mUpgradePanel);
		
		mBuildPanel = new BuildPanel();
		mBuildPanel.setX(15);
		mBuildPanel.setY(15 + DescriptionPanel.HEIGHT + mDescPanel.getY());
		addActor(mBuildPanel);
		
		final ButtonStyle closeStyle = new ButtonStyle();
		closeStyle.up = new TextureRegionDrawable(atlas.findRegion("close-button-up"));
		closeStyle.down = new TextureRegionDrawable(atlas.findRegion("close-button-down"));
		final Button closeBtn = new Button(closeStyle);
		closeBtn.setX(getWidth() - closeBtn.getWidth() + 15);
		closeBtn.setY(getHeight() - closeBtn.getHeight() + 15);
		closeBtn.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				animateOut();
			}
		});
		addActor(closeBtn);
		
	}
	
	private void onBuildingSelected(BuildingCell cell)
	{
		mDescPanel.setContent(cell.getThumb(), cell.getBuildingBean());
	}
	
	public void animateOut()
	{
		final MoveByAction moveUp = Actions.moveBy(0,getY() + getHeight(),0.5f);
		moveUp.setInterpolation(mSwingInInterpolation);
		addAction(Actions.sequence(moveUp, Actions.run(hide)));
	}
	
	final Runnable hide = new Runnable()
	{
		
		@Override
		public void run()
		{
			setVisible(false);
		}
	};
	
	public void animateIn()
	{
		setVisible(true);
		final MoveByAction animateIn = Actions.moveBy(0, -(LevelScreen.GAME_HEIGHT - (LevelScreen.GAME_HEIGHT/2 - getHeight()/2)));
		animateIn.setDuration(0.5f);
		animateIn.setInterpolation(mSwingOutInterpolation);
		addAction(animateIn);
	}
	
	private class DescriptionPanel extends Group
	{
		public static final float WIDTH = 628f;
		public static final float HEIGHT = 385f;
		
		private Label mDescription;
		private Image mThumb;
		private Button mBuildButton;
		private BuildingBean mBuildingBean;
		
		public DescriptionPanel()
		{
			super();
			
			setWidth(WIDTH);
			setHeight(HEIGHT);
			
			final TextureAtlas atlas = mAssetManager.get("data/game_screen.atlas", TextureAtlas.class);
			
			final Image bg = new Image(atlas.createPatch("thumb-portrait"));
			bg.setWidth(WIDTH);
			bg.setHeight(HEIGHT);
			addActor(bg);
			
			mThumb = new Image();
			addActor(mThumb);
			
			final LabelStyle style = new LabelStyle();
			style.font = mAssetManager.get("data/fonts/charlemagne.fnt");
			mDescription = new Label("", style);
			mDescription.setWidth(WIDTH - 30);
			mDescription.setHeight(150);
			mDescription.setX(15);
			mDescription.setY(15);
			addActor(mDescription);
			
			final ButtonStyle buildBtnStyle = new ButtonStyle();
			buildBtnStyle.up = new TextureRegionDrawable(atlas.findRegion("build-button-up"));
			buildBtnStyle.down = new TextureRegionDrawable(atlas.findRegion("build-button-down"));
			buildBtnStyle.disabled =  new TextureRegionDrawable(atlas.findRegion("build-button-disabled"));
			final Button buildButton = new Button(buildBtnStyle);
			addActor(buildButton);
			
			buildButton.setX(getWidth()/2 - buildButton.getWidth()/2);
			buildButton.setY(30);
			buildButton.setVisible(false);
			mBuildButton = buildButton;
			
			mBuildButton.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					return true;
				}
				
				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					final int cost = mBuildingBean.getCostForBuilding();
					if(mBattlefield.getPlayerBase().getCash() >= cost)
					{
						mBattlefield.getPlayerBase().spendCash(cost);
						
						switch(mBuildingBean.getSuperType()){
						
						case ECONOMIY:
							EconomyBuilding economyBuilding = new EconomyBuilding((EconomyBuildingBean) mBuildingBean);
							mBattlefield.getPlayerBase().addBuilding(economyBuilding);
							break;
						
						case MILITARY:
							MilitaryBuilding militaryBuilding = new MilitaryBuilding((MilitaryBuildingBean) mBuildingBean);
							mBattlefield.getPlayerBase().addMilitaryBuilding(militaryBuilding);
							break;
						
						case POPULATION:
							mBattlefield.getPlayerBase().increasePopulationBy(((PopulationBuildingBean) mBuildingBean).getPopulationIncrement());
							break;
						
						}
					}
				}
			});
		}
		
		public void setContent(TextureRegionDrawable thumb, BuildingBean buildingBean)
		{
			mBuildingBean = buildingBean;
			final int cost = buildingBean.getCostForBuilding();
			mThumb.setDrawable(thumb);
			mThumb.setWidth(thumb.getRegion().getRegionWidth());
			mThumb.setHeight(thumb.getRegion().getRegionHeight());
			mThumb.setX(15);
			mThumb.setY(getHeight() - mThumb.getHeight() - 15);
			mDescription.setWrap(true);
			mDescription.setText(buildingBean.getDescription());
			mDescription.setX(15);
			mDescription.setY(mThumb.getY() - mDescription.getHeight());
			mDescription.setAlignment(Align.left | Align.top);
			
			mBuildButton.setVisible(true);
			mBuildButton.setDisabled(mBattlefield.getPlayerBase().getCash() < cost);
		}
		
		@Override
		public void act(float delta)
		{
//			mBuildButton.setDisabled(mBattlefield.getPlayerBase().getCash() < mBuildingBean.getCostForBuilding());
			super.act(delta);
		}
	}
	
	private class BuildPanel extends Group
	{
		public static final float WIDTH = 628f;
		public static final float HEIGHT = 175f;
		
		private Array<BuildingCell> mCells;
		
		public BuildPanel()
		{
			super();
			
			setWidth(WIDTH);
			setHeight(HEIGHT);
			
			final TextureAtlas atlas = mAssetManager.get("data/game_screen.atlas", TextureAtlas.class);
			
			final Image bg = new Image(atlas.createPatch("thumb-portrait"));
			bg.setWidth(WIDTH);
			bg.setHeight(HEIGHT);
			addActor(bg);
			
			mCells = new Array<BuildingCell>(4);
			final BuildingBean[] buildings = mBattlefield.getPlayerBase().getBuildingSupported();
			for(int i = 0 ; i < buildings.length ; ++i)
			{
				final BuildingCell cell = new BuildingCell(buildings[i]);
				cell.setX((i+1)*15 + i * cell.getWidth());
				cell.setY(20);
				addActor(cell);
				mCells.add(cell);
			}
			
			addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					System.out.println("CELL");
					return true;
				}
				
				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					final Actor clickedActor = BuildPanel.this.hit(x, y, true);
					if(clickedActor != null && clickedActor instanceof BuildingCell)
					{
						final Iterator<BuildingCell> i = mCells.iterator();
						while(i.hasNext())
						{
							final BuildingCell bc = i.next();
							if(bc.equals(clickedActor))
							{
								bc.setSelected(true);
								onBuildingSelected(bc);
							}
							else
								bc.setSelected(false);
						}
					}
				}
			});
		}
		
		@Override
		public void act(float delta)
		{
			super.act(delta);
			final Iterator<BuildingCell> i = mCells.iterator();
			final int currentCash = mBattlefield.getPlayerBase().getCash();
			while(i.hasNext())
			{
				final BuildingCell bc = i.next();
				bc.setColor(1, 1, 1, currentCash >= bc.getCost() ? 1 : 0.5f);
			}
		}
	}
	
	private class UpgradePanel extends Group
	{
		public static final float WIDTH = 550f;
		public static final float HEIGHT = 575f;
		
		public UpgradePanel()
		{
			super();
			
			setWidth(WIDTH);
			setHeight(HEIGHT);
			
			final TextureAtlas atlas = mAssetManager.get("data/game_screen.atlas", TextureAtlas.class);
			
			final Image bg = new Image(atlas.createPatch("thumb-portrait"));
			bg.setWidth(WIDTH);
			bg.setHeight(HEIGHT);
			addActor(bg);
			
		}
	}
	
	private class BuildingCell extends Group
	{
		private Image mBg;
		private int mCost;
		private Image mThumb;
		private BuildingBean mBuilding;
		
		public BuildingCell(BuildingBean buildingBean)
		{
			super();
			final TextureAtlas atlas = mAssetManager.get("data/game_screen.atlas", TextureAtlas.class);
			
			mBuilding = buildingBean;
			mCost = buildingBean.getCostForBuilding();
			mBg = new Image(atlas.createPatch("thumb-portrait"));
			final Image thumb = new Image(atlas.findRegion(mBuilding.getThumb()));
			mThumb = thumb;
			mBuilding = buildingBean;
			final Image coins = new Image(atlas.findRegion("coins"));
			final LabelStyle labelStyle = new LabelStyle();
			labelStyle.font = mAssetManager.get("data/fonts/charlemagne.fnt", BitmapFont.class);
			final Label costLabel = new Label(String.valueOf(mCost), labelStyle);
			
			setWidth(thumb.getWidth() + 10);
			setHeight(thumb.getHeight() + 35);

			mBg.setWidth(getWidth());
			mBg.setHeight(getHeight());
			mBg.setTouchable(Touchable.disabled);
			mBg.setVisible(false);
			addActor(mBg);
			
			thumb.setX(5);
			thumb.setY(30);
			thumb.setTouchable(Touchable.disabled);
			addActor(thumb);
			
			coins.setX(5);
			coins.setY(5);
			coins.setTouchable(Touchable.disabled);
			addActor(coins);
			
			costLabel.setX(coins.getX() + coins.getWidth() + 5);
			costLabel.setTouchable(Touchable.disabled);
			addActor(costLabel);
			
			setColor(1, 0, 0, 0.5f);
		}
		
		public BuildingBean getBuildingBean() {
			return mBuilding;
		}

		public TextureRegionDrawable getThumb()
		{
			return ((TextureRegionDrawable)mThumb.getDrawable());
		}

		public void setSelected(boolean selected)
		{
			mBg.setVisible(selected);
		}

		public int getCost()
		{
			return mCost;
		}
	}
	
}
