package com.breno.crushem.hud;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.breno.crushem.Team;
import com.breno.crushem.Screens.LevelScreen;

/**
 * Panel usado para mostrar o HUD do Victory/Defeated apos o final da fase.
 * 
 * @author Diego
 *
 */
public class VictoryDefeatPanel extends Group {
	
	private TextButton mBackButton;
	private TextButton mReplayButton;
	private Image mVictoryImage;
	private Image mDefeatedImage;
	
	private AssetManager mAssetManager;
	private LevelScreen mLevelScreen;
	
	public static final float WIDTH = 1220f;
	public static final float HEIGHT = 605f;
	
	public VictoryDefeatPanel(AssetManager assetMgr, LevelScreen levelScreen) {
		super();
		
		mAssetManager = assetMgr;
		mLevelScreen = levelScreen;
		
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		
		setWidth(WIDTH);
		setHeight(HEIGHT);
		
		mVictoryImage = new Image(atlas.findRegion("end-game-win"));
		mVictoryImage.setX(350);
		mVictoryImage.setY(300);
		
		mDefeatedImage = new Image(atlas.findRegion("end-game-lose"));
		mDefeatedImage.setX(350);
		mDefeatedImage.setY(300);
		
		TextButtonStyle btnStyle = new TextButtonStyle();
		btnStyle.font = mAssetManager.get("data/fonts/charlemagne.fnt");
		
		mBackButton = new TextButton("Back", btnStyle);
		mBackButton.setX(1050);
		mBackButton.setY(100);
		mBackButton.addListener(new BackButtonListener());
		
		mReplayButton = new TextButton("Replay", btnStyle); 
		mReplayButton.setX(150);
		mReplayButton.setY(100);
		mReplayButton.addListener(new RestartButtonListener());
		
		
	}
	
	public void showWinner(Team winner) {
		
		switch(winner) {
		
		case HOME:
			addActor(mVictoryImage);
			break;
		
		case AWAY:
			addActor(mDefeatedImage);
			break;
		
		}

		addActor(mBackButton);
		addActor(mReplayButton);
		
	}
	
	class BackButtonListener extends InputListener {
		
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			return true;
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			mLevelScreen.backLevelScreen();
			
		}
	}
	
	class RestartButtonListener extends InputListener {
		
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			return true;
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			mLevelScreen.restartLevelScreen();
		}
		
	}

	
}
