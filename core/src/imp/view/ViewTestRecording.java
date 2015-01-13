package imp.view;

import utils.listener.OnClickListener;
import utils.listener.OnCompleteListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.views.IViewController;
import com.coder5560.game.views.View;

public class ViewTestRecording extends View {

	boolean			isRecording	= false;
	boolean			isPlaying	= false;
	final int		samples		= 22050;
	boolean			isMono		= false;
	final short[]	data		= new short[samples * 6];
	Image			btnPlay;
	Image			btnRecord;

	public ViewTestRecording() {
		super();
	}

	@Override
	public void build(Stage stage, IViewController viewController,
			String viewName, Rectangle bound) {
		super.build(stage, viewController, viewName, bound);
		btnPlay = new Image(new NinePatch(Assets.instance.ui.reg_ninepatch4));
		btnPlay.setSize(80, 80);
		btnPlay.setPosition(400, 200);
		btnPlay.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("Click Play");
				play();
			}

		});

		btnRecord = new Image(new NinePatch(Assets.instance.ui.reg_ninepatch4));
		btnRecord.setSize(80, 80);
		btnRecord.setPosition(200, 200);
		btnRecord.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("Click record");
				record();
			}

		});
		addActor(btnPlay);
		addActor(btnRecord);
	}

	@Override
	public void show(OnCompleteListener listener) {
		super.show(listener);
	}

	@Override
	public void hide(OnCompleteListener listener) {
		super.hide(listener);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}

	@Override
	public void back() {
		super.back();
	}

	public void play() {
		final AudioDevice player = Gdx.audio.newAudioDevice(samples, isMono);
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Play : Start");
				player.writeSamples(data, 0, data.length);
				System.out.println("Play : End");
				player.dispose();
			}
		}).start();
	}

	public void record() {
		final AudioRecorder recorder = Gdx.audio.newAudioRecorder(samples,
				isMono);
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Record: Start");
				recorder.read(data, 0, data.length);
				recorder.dispose();
				System.out.println("Record: End");
			}
		}).start();
	}

}
