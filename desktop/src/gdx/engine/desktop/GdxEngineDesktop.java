package gdx.engine.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.coder5560.game.enums.Constants;
import com.coder5560.game.screens.FlashScreen;

import engine.module.screens.GameCore;

public class GdxEngineDesktop {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.WIDTH_SCREEN;
		config.height = Constants.HEIGHT_SCREEN;

		GameCore game = new GameCore() {
			@Override
			public void create() {
				super.create();
				setScreen(new FlashScreen(this));
			}
		};

		new LwjglApplication(game, config);
	}
}
