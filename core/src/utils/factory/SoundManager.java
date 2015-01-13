package utils.factory;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	HashMap<SoundName, Sound>	sounds		= new HashMap<SoundManager.SoundName, Sound>();
	HashMap<MusicName, Music>	musics		= new HashMap<SoundManager.MusicName, Music>();

	public boolean				sound		= true;
	public boolean				music		= true;

	private Music				currentMusic;
	private static SoundManager	instance	= new SoundManager();

	public static enum SoundName {
		WORD_Q("sounds/q.mp3", 1f), WORD_A("sounds/a.mp3", 1f), WORD_Z(
				"sounds/z.mp3", 1f), WORD_W("sounds/w.mp3", 1f), WORD_S(
				"sounds/s.mp3", 1f), WORD_X("sounds/x.mp3", 1f), WORD_E(
				"sounds/e.mp3", 1f), WORD_D("sounds/d.mp3", 1f), WORD_C(
				"sounds/c.mp3", 1f), WORD_R("sounds/r.mp3", 1f), WORD_F(
				"sounds/f.mp3", 1f), WORD_V("sounds/v.mp3", 1f), WORD_T(
				"sounds/t.mp3", 1f), WORD_G("sounds/g.mp3", 1f), WORD_B(
				"sounds/b.mp3", 1f), WORD_Y("sounds/y.mp3", 1f), WORD_H(
				"sounds/h.mp3", 1f), WORD_N("sounds/n.mp3", 1f), WORD_U(
				"sounds/u.mp3", 1f), WORD_J("sounds/j.mp3", 1f), WORD_M(
				"sounds/m.mp3", 1f), WORD_I("sounds/i.mp3", 1f), WORD_K(
				"sounds/k.mp3", 1f), WORD_O("sounds/o.mp3", 1f), WORD_L(
				"sounds/l.mp3", 1f), WORD_P("sounds/p.mp3", 1f);

		String	path;
		float	volumn;

		private SoundName(String path, float vol) {
			this.path = path;
			this.volumn = vol;
		}

		public String getName() {
			int indexStart = path.indexOf("/");
			int indexend = path.indexOf(".");
			return path.substring(indexStart + 1, indexend);
		}
	}

	public static SoundManager getInstance() {
		if (instance == null)
			instance = new SoundManager();
		return instance;
	}

	public static enum MusicName {
		MAGIC_DREAM("sounds/a.mp3", 0.5f);

		String	path;
		float	volumn;

		private MusicName(String path, float vol) {
			this.path = path;
			this.volumn = vol;
		}
	}

	private SoundManager() {
		initComponents();
	}

	private void initComponents() {
		for (SoundName name : SoundName.values()) {
			Sound sound = Gdx.audio.newSound(Gdx.files.internal(name.path));
			sounds.put(name, sound);
		}
		for (MusicName name : MusicName.values()) {
			Music music = Gdx.audio.newMusic(Gdx.files.internal(name.path));
			music.setLooping(true);
			music.setVolume(name.volumn);
			musics.put(name, music);
		}
		sound = GamePreferences.getInstance().isSoundOn();
		music = GamePreferences.getInstance().isMusicOn();
	}

	public void turnSound() {
		sound = !sound;
		GamePreferences.getInstance().setSound(sound, true);
	}

	public void turnMusic() {
		if (music) {
			music = false;
			for (MusicName mn : MusicName.values()) {
				if (musics.get(mn).isPlaying()) {
					musics.get(mn).stop();
					currentMusic = musics.get(mn);
				}
			}
		} else {
			music = true;
			if (currentMusic == null) {
				// playMusic(MusicName.MAGIC_DREAM);
			} else {
				currentMusic.play();
			}
		}
		GamePreferences.getInstance().setMusic(music, true);
	}

	public void playMusic(MusicName name) {
		if (!music)
			return;

		if (musics.get(name).isPlaying())
			return;

		for (MusicName mn : MusicName.values()) {
			if (musics.get(mn).isPlaying())
				musics.get(mn).stop();
		}

		musics.get(name).play();

		currentMusic = musics.get(name);
	}

	public void playSound(SoundName name) {
		try {
			sounds.get(name).play(name.volumn);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void dispose() {
		for (Sound sound : sounds.values()) {
			sound.dispose();
		}
		for (Music music : musics.values()) {
			music.dispose();
		}
		sounds.clear();
		musics.clear();
	}

	public void playSound(String text) {
		try{
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("enviroment/"
				+ text + ".mp3"));
		sound.play(1f);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
