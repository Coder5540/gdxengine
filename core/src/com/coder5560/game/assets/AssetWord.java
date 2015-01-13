package com.coder5560.game.assets;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class AssetWord implements Disposable {

	private TextureAtlas			textureAtlas;
	HashMap<String, TextureRegion>	listText = new HashMap<String, TextureRegion>();

	public AssetWord(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
	}

	public TextureRegion getChar(String character) {
		if (listText.containsKey(character))
			return listText.get(character);
		TextureRegion reg = textureAtlas.findRegion(character);
		listText.put(character, reg);
		return reg;
	}

	@Override
	public void dispose() {
		
	}
	

}
