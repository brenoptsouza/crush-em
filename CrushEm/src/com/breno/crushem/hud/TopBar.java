package com.breno.crushem.hud;

import java.util.Iterator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.breno.crushem.ArmyBase;
import com.breno.crushem.Battlefield;
import com.breno.crushem.MilitaryBuilding;
import com.breno.crushem.Team;

public class TopBar extends Group
{
	
	private float SPACE_BETWEEN_BTNS = 20;
	private float BTN_MARGIN_BOTTOM = 0;
	private Label mCashLabel;
	private Image mCashIcon;
	private Label mPopulationLabel;
	private Image mPopulationIcon;
	private TopBarInputListener mFighterButtonInputListener;
	
	
	private Array<FighterProgressButton> mFighterButtons;
	private Battlefield mBattlefield;
	private AssetManager mAssetManager;
	private Image mBg;
	private Button mManageButton;
	private StringBuffer mStringBuffer = new StringBuffer();
	
	public TopBar(AssetManager assetMgr, Battlefield battlefield, TopBarInputListener inputListener)
	{
		mAssetManager = assetMgr;
		mBattlefield = battlefield;
		mFighterButtonInputListener = inputListener;
		
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		mBg = new Image(atlas.findRegion("top-bar-bg"));
		
		final LabelStyle style = new LabelStyle();
		style.font = mAssetManager.get("data/fonts/charlemagne.fnt", BitmapFont.class);
		mCashIcon= new Image(atlas.findRegion("coins"));
		mCashLabel = new Label("--", style);
		mCashLabel.setWidth(60);
		mCashLabel.setAlignment(Align.center, Align.center);
		
		mPopulationIcon= new Image(atlas.findRegion("population"));
		mPopulationLabel = new Label("--", style);
		mPopulationLabel.setWidth(70);
		mPopulationLabel.setAlignment(Align.center, Align.center);
		
		mFighterButtons = new Array<FighterProgressButton>(4);
		
		addActor(mBg);
		addActor(mCashLabel);
		addActor(mCashIcon);
		addActor(mPopulationLabel);
		addActor(mPopulationIcon);
		mBg.setX(0);
		mBg.setWidth(getWidth());
		
		final ButtonStyle btnStyle  = new ButtonStyle();
		btnStyle.up = new TextureRegionDrawable(atlas.findRegion("manage-button-up"));
		btnStyle.down = new TextureRegionDrawable(atlas.findRegion("manage-button-down"));
		mManageButton = new Button(btnStyle);
		addActor(mManageButton);
		
		mManageButton.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				if(mFighterButtonInputListener != null)
					mFighterButtonInputListener.manageButtonClicked();
			}
		});
	}
	
	
	@Override
	public void act(float delta)
	{
		final ArmyBase playerBase = mBattlefield.getPlayerBase();
		mCashLabel.setText(String.valueOf(playerBase.getCash()));
		mStringBuffer.delete(0, mStringBuffer.length());
		
		mStringBuffer.append(mBattlefield.getAllFighters(Team.HOME).size)
		.append("/")
		.append(mBattlefield.getPlayerBase().getPopulationLimit());
		
		mPopulationLabel.setText(mStringBuffer.toString());
		final Array<MilitaryBuilding> militaryBuildings = playerBase.getMilitaryBuildings();
		final Iterator<MilitaryBuilding> i = militaryBuildings.iterator();
		//TODO implement the removed from ArmyBase case
		while(i.hasNext())
			tryToAdd(i.next());
		super.act(delta);
	}
	
	private void addInputBtnListener(FighterProgressButton fpb)
	{
		fpb.addListener(mInputListener);
	}
	
	private InputListener mInputListener = new InputListener()
	{
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
		{
			System.out.println("TOP BAR");
			final FighterProgressButton fighterButton = (FighterProgressButton) event.getTarget();
			if(!fighterButton.hasAvailableUnit())
				return false;
			if(mFighterButtonInputListener != null)
				mFighterButtonInputListener.touchDownFighter(x + fighterButton.getX() + getX(), y + fighterButton.getY() + getY(), fighterButton);
			return true;
		};
		
		public void touchDragged(InputEvent event, float x, float y, int pointer) 
		{
			final FighterProgressButton fighterButton = (FighterProgressButton) event.getTarget();
			if(mFighterButtonInputListener != null && fighterButton.hasAvailableUnit())
				mFighterButtonInputListener.touchDraggedFighter(x + fighterButton.getX() + getX(), y + fighterButton.getY() + getY(), fighterButton);
		};
		
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) 
		{
			final FighterProgressButton fighterButton = (FighterProgressButton) event.getTarget();
			if(mFighterButtonInputListener != null && fighterButton.hasAvailableUnit())
				mFighterButtonInputListener.touchUpFighter(x + fighterButton.getX() + getX(), y + fighterButton.getY() + getY(), fighterButton);
		};
	};
	
	private boolean tryToAdd(MilitaryBuilding militaryBuilding)
	{
		final Iterator<FighterProgressButton> i = mFighterButtons.iterator();
		boolean addedInExistentButton = false;
		while(i.hasNext())
		{
			final FighterProgressButton fgp = i.next();
			if(!addedInExistentButton)
			{
				if(fgp.fighterType == militaryBuilding.getFighterType())
				{
					if(fgp.getBuildings().contains(militaryBuilding, false))
					{
						addedInExistentButton = false;
						return false;
					}
					else
					{
						fgp.addMilitaryBuilding(militaryBuilding);
						addedInExistentButton = true;
					}
						
				}
			}
			else
				fgp.addAction(Actions.moveBy(FighterProgressButton.SPACE_BETWEEN_CELLS, 0, 0.2f));
		}
		if(!addedInExistentButton)
		{
			final FighterProgressButton fgp = new FighterProgressButton(mAssetManager, militaryBuilding.getFighterType());
			fgp.addMilitaryBuilding(militaryBuilding);
			addActor(fgp);
			final float rightMargin = mFighterButtons.size == 0 ? 0 : mFighterButtons.peek().getRight(); 
			fgp.setX(rightMargin + SPACE_BETWEEN_BTNS);
			fgp.setY(BTN_MARGIN_BOTTOM);
			mFighterButtons.add(fgp);
			addInputBtnListener(fgp);
		}
		
		return true;
	}
	
	public interface TopBarInputListener
	{
		public void touchDownFighter(float x, float y, FighterProgressButton button);
		public void touchDraggedFighter(float x, float y, FighterProgressButton fighterButton);
		public void touchUpFighter(float x, float y, FighterProgressButton fighterButton);
		public void manageButtonClicked();
	}

	public void invalidate()
	{
		mCashLabel.setX(getWidth() - 80);
		mCashLabel.setY(getHeight() - mCashLabel.getHeight() + 3);
		mCashIcon.setX(mCashLabel.getX() - mCashIcon.getWidth() - 5);
		mCashIcon.setY(mCashLabel.getY()+3);
		
		mPopulationLabel.setX(mCashIcon.getX() - mPopulationLabel.getWidth());
		mPopulationLabel.setY(getHeight() - mPopulationLabel.getHeight() + 3);
		mPopulationIcon.setX(mPopulationLabel.getX() - mPopulationIcon.getWidth() - 5);
		mPopulationIcon.setY(mPopulationLabel.getY()+3);
		
		mBg.setWidth(getWidth());
		mBg.setY(getHeight() - mBg.getHeight());
		mBg.invalidate();
		mManageButton.setX(mCashLabel.getX() - 280);
		mManageButton.setY(getHeight() - mManageButton.getHeight());
	}
}
