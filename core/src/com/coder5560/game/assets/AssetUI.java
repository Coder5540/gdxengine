package com.coder5560.game.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetUI {

	public TextureRegion	reg_submenu;
	public TextureRegion	reg_user;
	public TextureRegion	reg_ninepatch;
	public TextureRegion	reg_ninepatch1;
	public TextureRegion	reg_ninepatch2;
	public TextureRegion	reg_ninepatch3;
	public TextureRegion	reg_ninepatch4;
	public TextureRegion	reg_down;
	public TextureRegion	reg_avatar;
	public TextureRegion	reg_userManagements;
	private TextureRegion	reg_mail;
	private TextureRegion	reg_circle;
	private TextureRegion reg_ninepathKnob;
	
	private TextureAtlas	textureAtlas;

	public AssetUI(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
		reg_down = textureAtlas.findRegion("down");
		reg_submenu = textureAtlas.findRegion("icon_submenu");
		reg_user = textureAtlas.findRegion("icon_user");
		reg_ninepatch = textureAtlas.findRegion("ninepatch_none");
		reg_ninepatch1 = textureAtlas.findRegion("ninepatch_rounded");
		reg_ninepatch2 = textureAtlas.findRegion("ninepatch_outline");
		reg_ninepatch3 = textureAtlas.findRegion("ninepatch_stock");
		reg_ninepatch4 = textureAtlas.findRegion("ninepatch_shadow_bottom");
	}

	public TextureRegion getAvatar() {
		if (reg_avatar == null)
			reg_avatar = textureAtlas.findRegion("avatar");
		return reg_avatar;
	}

	public TextureRegion getRegUsermanagement() {
		if (reg_userManagements == null)
			reg_userManagements = textureAtlas.findRegion("user_manager");
		return reg_userManagements;
	}

	public TextureRegion getRegionMail() {
		if (reg_mail == null)
			reg_mail = textureAtlas.findRegion("email");
		return reg_mail;
	}

	public TextureRegion getCircle() {
		if (reg_circle == null)
			reg_circle = textureAtlas.findRegion("circle");
		return reg_circle;
	}

	public TextureRegion getNinePathKnob() {
		if(reg_ninepathKnob == null) reg_ninepathKnob = textureAtlas.findRegion("bg_white");
		return reg_ninepathKnob;
	}

}