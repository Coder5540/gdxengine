package utils.factory;

import utils.listener.OnCompleteListener;

public interface IAudioStreamPlayer {

	public void play();

	public void update(float delta);

	public void play(boolean reset, String url,
			OnCompleteListener onCompleteListener);

	public void pause();

	public void dispose();

	public boolean isPlaying();

	public boolean isLoaded();

	public String getCurrentStream();

}
