package com.coder5560.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.coder5560.game.assets.AssetFactory;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.enums.Constants;
import com.coder5560.game.enums.NetworkState;

import engine.module.networks.Request;
import engine.module.screens.AbstractGameScreen;
import engine.module.screens.GameCore;
import engine.module.screens.Toast;

public class FlashScreen extends AbstractGameScreen {
	Image			imgFlash;

	boolean		loaded			= false;
	boolean		checkedNetwork	= false;
	GameScreen	gameScreen;
	public FlashScreen(GameCore game) {
		super(game);
		imgFlash = new Image(new Texture(Gdx.files.internal("Img/splash.png")));
		imgFlash.setOrigin(Align.center);
		imgFlash.setPosition(Constants.WIDTH_SCREEN / 2,
				Constants.HEIGHT_SCREEN / 2, Align.center);
	}

	@Override
	public void show() {
		super.show();
		gameScreen = new GameScreen(parent);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	float	time	= 0;

	@Override
	public void update(float delta) {
		if (time <= 0.1f)
			time += delta;
		if (!loaded && time > 0.1f) {
			if (AssetFactory.getInstance().getManager().update()) {
				Assets.instance.init();
//				if (parent.getPlatformResolver().getPlatform() == PlatformType.ANDROID)
//					checkNetworkAndInitialConfig();
//				else {
//					switchScreen();
//				}
				switchScreen();
				checkedNetwork = false;
				loaded = true;
			}
		}
	}

	boolean	switchScreen	= false;
	void switchScreen() {
		if (!switchScreen) {
//			parent.setScreen(gameScreen);
//			parent.setScreen(dictionaryScreen);
//			parent.setScreen(new MusicPlayerScreen(parent));
			parent.setScreen(new TestScreen(parent));
			switchScreen = true;
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		imgFlash.act(delta);
		imgFlash.draw(batch, 1f);
		batch.end();
	}

	void checkNetworkAndInitialConfig() {
		if (parent.getNetworkManager().isNetworkEnable()) {
			if (!checkedNetwork) {
				Request.getInstance().loadConfig();
				imgFlash.addAction(Actions.sequence(Actions.run(new Runnable() {

					@Override
					public void run() {
						switchScreen();
						checkedNetwork = true;
					}
				})));
			}
		} else {
			parent.getNetworkManager().setNetworkState(NetworkState.DISABLE);
			imgFlash.addAction(Actions.forever(Actions.sequence(
					Actions.run(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getEngine(), "No network avaiable !",
									Toast.LENGTH_SHORT);
						}
					}), Actions.delay(3.5f), Actions.run(new Runnable() {

						@Override
						public void run() {
							if (parent.getNetworkManager().isNetworkEnable()) {
								parent.getNetworkManager().setNetworkState(
										NetworkState.ENABLE);
								if (!checkedNetwork) {
									Request.getInstance().loadConfig();
									imgFlash.addAction(Actions.sequence(
											Actions.alpha(0f, .2f),
											Actions.run(new Runnable() {
												@Override
												public void run() {
													switchScreen();
													checkedNetwork = true;
												}
											})));
								}
							}
						}
					}))));
		}
	}
}
