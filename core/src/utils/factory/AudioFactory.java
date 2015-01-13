package utils.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import utils.factory.SoundManager.SoundName;
import utils.listener.INetworkManager;
import utils.listener.OnCompleteListener;

public class AudioFactory {
	private static INetworkManager		_NetworkManager;
	private static IAudioStreamPlayer	_AudioStreamPlayer;
	private static final String			onlineUrl	= "https://ssl.gstatic.com/dictionary/static/sounds/de/0/";

	public static void save(String strDirectory, String word)
			throws MalformedURLException, IOException {
		Log.d("Saving File : " + word + ".mp3");
		URLConnection conn = new URL(onlineUrl + word + ".mp3")
				.openConnection();
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

	public static void playSound(String word, boolean isSaveAudio,
			final OnCompleteListener onCompleteListener) {
		// 1. if network isn't available, notify that function is complete !
		if (_NetworkManager == null) {
			onCompleteListener.onComplete("Canot find network manager");
			return;
		}
		if (!_NetworkManager.isNetworkEnable()) {
			onCompleteListener.onComplete("network isn't available");
			return;
		}

		// 2. if network available, just call audio stream player
		_AudioStreamPlayer.play(true, onlineUrl + word + ".mp3",
				onCompleteListener);

		/*
		 * 3. if save this audio, then put it into offline queue to download
		 * later
		 */
		
		if(isSaveAudio) {
			// open database session
			
			// save to database
			
		}
	}

	public static void setNetworkManage(INetworkManager networkManager) {
		AudioFactory._NetworkManager = networkManager;
	}

	public static void setAudioStreamPlayer(IAudioStreamPlayer audioStreamPlayer) {
		AudioFactory._AudioStreamPlayer = audioStreamPlayer;
	}
}
