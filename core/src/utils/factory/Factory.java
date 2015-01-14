package utils.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import utils.factory.SoundManager.SoundName;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.enums.Constants;
import com.coder5560.game.enums.Direct;

public class Factory {
	public static Vector2 getVisualPoint(Vector2 point, float scale) {
		float minX = Constants.WIDTH_SCREEN * scale / 2;
		float maxX = Constants.WIDTH_SCREEN * (1 - scale / 2);
		float minY = Constants.HEIGHT_SCREEN * scale / 2;
		float maxY = Constants.HEIGHT_SCREEN * (1 - scale / 2);

		float x = (point.x < minX) ? minX : ((point.x > maxX) ? maxX : point.x);
		float y = (point.y < minY) ? minY : ((point.y > maxY) ? maxY : point.y);

		return new Vector2(x, y);
	}

	public static Vector2 getVisualPoint(Vector2 point, float scale,
			Rectangle bound) {
		float minX = bound.x + bound.width * scale / 2;
		float maxX = bound.x + bound.width * (1 - scale / 2);
		float minY = bound.y + bound.height * scale / 2;
		float maxY = bound.y + bound.height * (1 - scale / 2);
		float x = (point.x < minX) ? minX : ((point.x > maxX) ? maxX : point.x);
		float y = (point.y < minY) ? minY : ((point.y > maxY) ? maxY : point.y);
		return new Vector2(x, y);
	}

	public static int getNext(int curentValue, int min, int max) {
		curentValue = clamp(curentValue, min, max);
		if (curentValue == max)
			return min;

		return (curentValue + 1);
	}

	public static int getNext(int curentValue, int min, int max, boolean loop) {
		curentValue = clamp(curentValue, min, max);
		if (curentValue == max) {
			if (loop)
				return min;
			else
				return max;
		}
		return (curentValue + 1);
	}

	public static int getPrevious(int curentValue, int min, int max) {
		curentValue = clamp(curentValue, min, max);
		if (curentValue == min)
			return max;
		return (curentValue - 1);
	}

	public static int getPrevious(int curentValue, int min, int max,
			boolean loop) {
		curentValue = clamp(curentValue, min, max);
		if (curentValue == min) {
			if (loop)
				return max;
			else
				return min;
		}
		return (curentValue - 1);
	}

	public static int clamp(int curentValue, int min, int max) {
		if (curentValue <= min)
			curentValue = min;
		if (curentValue >= max)
			curentValue = max;
		return curentValue;
	}

	public static Vector2 getPosition(Rectangle bounds, Direct direct) {
		switch (direct) {
		case TOP_LEFT:
			return new Vector2(bounds.x, bounds.y + bounds.height);
		case TOP_RIGHT:
			return new Vector2(bounds.x + bounds.width, bounds.y
					+ bounds.height);
		case TOP:
			return new Vector2(bounds.x + bounds.width / 2, bounds.y
					+ bounds.height);
		case BOTTOM:
			return new Vector2(bounds.x + bounds.width / 2, bounds.y);
		case BOTTOM_LEFT:
			return new Vector2(bounds.x, bounds.y);
		case BOTTOM_RIGHT:
			return new Vector2(bounds.x + bounds.width, bounds.y);
		case MIDDLE:
			return new Vector2(bounds.x + bounds.width / 2, bounds.y
					+ bounds.height / 2);
		case MIDDLE_LEFT:
			return new Vector2(bounds.x, bounds.y + bounds.height / 2);
		case MIDDLE_RIGHT:
			return new Vector2(bounds.x + bounds.width, bounds.y
					+ bounds.height / 2);
		default:
			return new Vector2();
		}
	}

	public static TextureRegion[] getArrayTextureRegion(
			TextureRegion textureRegion, int FRAME_COLS, int FRAME_ROWS) {
		float width = textureRegion.getRegionWidth() / FRAME_COLS;
		float height = textureRegion.getRegionHeight() / FRAME_ROWS;

		TextureRegion[] textureRegions = new TextureRegion[FRAME_COLS
				* FRAME_ROWS];
		TextureRegion[][] temp = textureRegion.split((int) width, (int) height);
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				textureRegions[index++] = temp[i][j];
			}
		}
		return textureRegions;
	}

	// ====================Common Checker=====================
	public static boolean validEmail(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean validDate(String dateToValidate, String dateFromat) {
		if (dateToValidate == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		try {
			// if not valid, it will throw ParseException
			@SuppressWarnings("unused")
			Date date = sdf.parse(dateToValidate);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean isNumeric(String str) {
		return str.matches("\\d+");
	}

	public static boolean validPhone(String phoneNumber) {
		if (!(phoneNumber.startsWith("0") || phoneNumber.startsWith("84") || phoneNumber
				.startsWith("+84")))
			return false;
		String phone = "";
		if (phoneNumber.startsWith("+84"))
			phone = phoneNumber.substring(3);
		if (phoneNumber.startsWith("0"))
			phone = phoneNumber.substring(1);
		if (phoneNumber.startsWith("84"))
			phone = phoneNumber.substring(2);

		System.out.println("Phone : " + phone);
		if (phone.length() == 9 || phone.length() == 10) {
			return isNumeric(phone);
		}
		return false;
	}

	public static String getStrMoney(int money) {
		String str = money + "";
		if (money < 10000) {
			str = money + "";
		} else if (money < 1000000) {
			int firstNumber = money / 1000;
			int secondNumber = (money - firstNumber * 1000) / 100;
			str = firstNumber + "." + secondNumber + "K";
		} else if (money < 1000000000) {
			int firstNumber = money / 1000000;
			int secondNumber = (money - firstNumber * 1000000) / 100000;
			str = firstNumber + "." + secondNumber + "M";
		} else {
			int firstNumber = money / 1000000000;
			int secondNumber = (money - firstNumber * 1000000000) / 100000000;
			str = firstNumber + "." + secondNumber + "B";
		}
		return str;
	}

	public static String getDotMoney(int money) {
		String str = "";
		while (money > 1000) {
			int temp = money % 1000;
			if (temp < 10)
				str = ".00" + money % 1000 + str;

			else if (temp < 100)
				str = ".0" + money % 1000 + str;

			else
				str = "." + money % 1000 + str;

			money = money / 1000;
		}
		return money + str;
	}

	public static int round(float a) {
		int result = Math.round(a);
		if (result < a) {
			result++;
		}
		return result;
	}

	public static int countChar(String content, String contentCount) {
		int number = content.split(contentCount, -1).length - 1;
		return number;
	}

	public static String getSubString(String str, float width, BitmapFont font) {
		String substr = str;
		for (int i = 0; i < str.length(); i++) {
			if (font.getBounds(str.substring(0, i)).width > width
					- font.getBounds("...").width) {
				substr = str.substring(0, i) + "...";
				break;
			}
		}
		return substr;
	}

	public static void addLine(Table table, float height, float padLeft,
			float padTop, float padRight, float padBottom) {
		Image line = new Image(Assets.instance.ui.reg_ninepatch);
		line.setColor(new Color(100 / 255f, 100 / 255f, 100 / 255f, 0.7f));
		line.setHeight(height);
		line.setWidth(table.getWidth());
		table.add(line).expandX().fillX().height(height).padTop(padTop)
				.padLeft(padLeft).padBottom(padBottom).padRight(padRight)
				.colspan(4);
		table.row();
	}

	public static void save(String word) throws MalformedURLException,
			IOException {
		Log.d("Saving File : " + word + ".mp3");
		String directory = "/home/dinhanh/Study/English Dictionary/English Sound/";
		URLConnection conn = new URL(getUrl(word, 1)).openConnection();
		InputStream is = conn.getInputStream();

		OutputStream outstream = new FileOutputStream(new File(directory
				+ word.toLowerCase() + ".mp3"));
		byte[] buffer = new byte[4096];
		int len;
		while ((len = is.read(buffer)) > 0) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
	}

	public static String getUrl(String word, int serverType) {
		String url = "";
		if (serverType == 1) {
			url = "https://ssl.gstatic.com/dictionary/static/sounds/de/0/"
					+ word.toLowerCase() + ".mp3";
		}
		if (serverType == 2) {
			String data = word.toLowerCase();
			String first = String.valueOf(data.charAt(0)) + "/";
			String sercond = data.substring(0, data.length() - 1) + "_/";
			String third = data + "__us_1";
			url = "﻿www.oxforddictionaries.com/media/american_english/us_pron/"
					+ first + sercond + third + ".mp3";
		}
		return url;
	}

	public static void save(String strDirectory, String word)
			throws MalformedURLException, IOException {
		Log.d("Saving File : " + word + ".mp3");
		URLConnection conn = new URL(
				"https://ssl.gstatic.com/dictionary/static/sounds/de/0/" + word
						+ ".mp3").openConnection();
		InputStream is = conn.getInputStream();

		OutputStream outstream = new FileOutputStream(new File(strDirectory
				+ word + ".mp3"));
		byte[] buffer = new byte[4096];
		int len;
		while ((len = is.read(buffer)) > 0) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
	}

	public static void playSound(String character) {
		if (character == null || character.length() != 1
				|| character.equalsIgnoreCase("")) {
			return;
		}

		if (character.equalsIgnoreCase(SoundName.WORD_A.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_A);
			return;
		}

		if (character.equalsIgnoreCase(SoundName.WORD_B.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_B);
			return;
		}

		if (character.equalsIgnoreCase(SoundName.WORD_Q.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_Q);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_Z.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_Z);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_W.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_W);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_S.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_S);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_X.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_X);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_E.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_E);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_F.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_F);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_V.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_V);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_D.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_D);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_C.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_C);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_R.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_R);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_T.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_T);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_G.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_G);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_Y.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_Y);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_H.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_H);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_N.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_N);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_U.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_U);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_J.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_J);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_M.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_M);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_I.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_I);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_K.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_K);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_O.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_O);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_L.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_L);
			return;
		}
		if (character.equalsIgnoreCase(SoundName.WORD_P.getName())) {
			SoundManager.getInstance().playSound(SoundName.WORD_P);
			return;
		}
	}

}
