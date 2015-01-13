package com.coder5560.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetFactory {
	private static final AssetFactory instance = new AssetFactory();
	
	public static final AssetFactory getInstance() {
		return instance;
	}
	private AssetManager manager;
	
	private AssetFactory() {
		manager = new AssetManager();
		Texture.setAssetManager(manager);
	}

	public AssetManager getManager() {
		return manager;
	}
}
