package com.coder5560.game.enums;

import com.badlogic.gdx.math.Rectangle;

public class Constants {

	public static final int			HEIGHT_SCREEN			= 480;
	public static final int			WIDTH_SCREEN			= 800;
	public static final int			HEIGHT_ACTIONBAR		= 60;
	public static final int			WIDTH_MAINMENU			= 300;
	public static final String		APP_NAME				= "IEnglish";
	public static String			DEVICE_ID				= "id";
	public static String			DEVICE_NAME				= "name";

	public static final String		TEXTURE_ATLAS_UI		= "packs/ui.pack";
	public static final String		TEXTURE_ATLAS_DRAWABLE	= "packs/drawable.pack";
	public static final String		DEFAULT_SKIN			= "skins/uiskin.json";
	public static final String		PACK_ALPHABET			= "packs/words.pack";
	public static Density			density					= Density.mdpi;

	public static final Rectangle	GAME_BOUND				= new Rectangle(
																	0,
																	0,
																	WIDTH_SCREEN,
																	HEIGHT_SCREEN);
}
