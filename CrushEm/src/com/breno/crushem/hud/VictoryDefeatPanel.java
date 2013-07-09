package com.breno.crushem.hud;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.breno.crushem.Team;

/**
 * Panel usado para mostrar o HUD do Victory/Defeated apos o final da fase.
 * 
 * @author Diego
 *
 */
public class VictoryDefeatPanel extends Group {
	
	private Button mBackButton;
	private Button mReplayButton;
	private Image mVictoryImage;
	private Image mDefeatedImage;
	private ShapeRenderer mBackgroundDim;
	
	public static final float WIDTH = 1220f;
	public static final float HEIGHT = 605f;
	
	public VictoryDefeatPanel(AssetManager assetMgr) {
		super();
		
		final TextureAtlas atlas = assetMgr.get("data/game_screen.atlas", TextureAtlas.class);
		
		setWidth(WIDTH);
		setHeight(HEIGHT);
		
		mVictoryImage = new Image(atlas.findRegion("end-game-win"));
		mVictoryImage.setX(350);
		mVictoryImage.setY(300);
		
		mDefeatedImage = new Image(atlas.findRegion("end-game-lose"));
		mDefeatedImage.setX(350);
		mDefeatedImage.setY(300);
		
		mBackgroundDim = new ShapeRenderer(4);
		mBackgroundDim.setColor(0, 0, 0, 0.5f);
		mBackgroundDim.rect(0, 0, WIDTH, HEIGHT);
		
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

	}

	
}
