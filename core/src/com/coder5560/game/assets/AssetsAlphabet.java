package com.coder5560.game.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class AssetsAlphabet implements Disposable{
	private static AssetsAlphabet	instance	= null;
	public AssetWord				assetWord;

	private AssetsAlphabet() {
		super();
	}

	public static AssetsAlphabet getInstance() {
		if (instance == null)
			instance = new AssetsAlphabet();
		return instance;
	}

	public void loadResource(TextureAtlas textureAtlas) {
		assetWord = new AssetWord(textureAtlas);
	}
	
	public void dispose(){
		assetWord.dispose();
		assetWord = null;
	}
}
