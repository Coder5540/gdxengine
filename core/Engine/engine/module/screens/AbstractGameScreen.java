package engine.module.screens;

import utils.factory.Factory;
import utils.listener.IInputListener;
import utils.listener.OnComplete;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.coder5560.game.enums.Constants;

import engine.element.Engine;
import engine.module.keyboard.VirtualKeyboard;
import engine.module.screens.CameraHandler.CameraState;

public abstract class AbstractGameScreen implements Screen, InputProcessor,
		GestureListener {
	public GameCore					parent;
	public Viewport					viewport;
	public OrthographicCamera		camera;
	public SpriteBatch				batch;
	public Engine					_Engine;

	public CameraHandler			cameraHandler;
	private GameState				gameState;
	public static VirtualKeyboard	keyboard;
	public IInputListener			iInput;

	public enum GameState {
		INITIAL, ANIMATING, RUNING, END, NEXT, WORD_COMPLETE;
	}

	public AbstractGameScreen(GameCore game) {
		this.parent = game;
	}

	public abstract void resize(int width, int height);

	public void show() {
		camera = new OrthographicCamera(Constants.WIDTH_SCREEN,
				Constants.HEIGHT_SCREEN);
		viewport = new StretchViewport(Constants.WIDTH_SCREEN,
				Constants.HEIGHT_SCREEN, camera);
		_Engine = new Engine(viewport);
		batch = new SpriteBatch();
		keyboard = new VirtualKeyboard(batch);

		cameraHandler = new CameraHandler(camera);
		{
			parent.inputMultiplexer = new InputMultiplexer(keyboard,
					new GestureDetector(this), this, _Engine);
			Gdx.input.setInputProcessor(getInputProcessor());
		}
		gameState = GameState.INITIAL;
		parent.setShowFps(true);
	}

	public abstract void update(float delta);

	BitmapFont	font	= new BitmapFont();

	public void render(float delta) {
		{
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		}

		cameraHandler.update(delta);
		if (cameraHandler.getCamState() == CameraState.NORMAL) {
			update(delta);
		}

		{
			_Engine.act(delta);
			_Engine.draw();
		}

		if (parent.isShowFps()) {
			_Engine.getBatch().begin();
			font.draw(_Engine.getBatch(),
					"FPS : " + Gdx.graphics.getFramesPerSecond(),
					Constants.WIDTH_SCREEN - 60, Constants.HEIGHT_SCREEN - 60);
			_Engine.getBatch().end();

		}

		{
			keyboard.update(delta);
			keyboard.draw();
		}
		if (Toast.instance != null)
			Toast.instance.render(delta);
	}

	public void pause() {
	}

	public void hide() {
	}

	public void resume() {
	}

	public InputProcessor getInputProcessor() {
		return parent.inputMultiplexer;
	}

	public void dispose() {
		keyboard.dispose();
		_Engine.dispose();
		batch.dispose();
	}

	public void switchScreen(AbstractGameScreen screen,
			ScreenTransition transition) {
		if (transition == null)
			parent.setScreen(screen);
		else
			parent.setScreen(screen, transition);
	}

	// ===================== input method =======================
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (iInput != null)
			return iInput.touchDown(x, y, pointer, button);
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		if (cameraHandler != null && cameraHandler.tap(x, y, count)
				&& cameraHandler.getCamState() == CameraState.ZOOM_OUT) {
			return true;
		}
		if (count >= 2 && cameraHandler != null && !cameraHandler.isBlock()) {
			final Vector2 point = new Vector2();
			viewport.unproject(point.set(x, y));
			if (cameraHandler.getCamState() == CameraState.NORMAL) {
				float scale = 0.6f;
				cameraHandler.zoomOut(Factory.getVisualPoint(point, scale),
						scale, new OnComplete() {
							@Override
							public void onComplete(Object object) {

							}
						});
			} else if (cameraHandler.getCamState() == CameraState.ZOOM_OUT) {
				cameraHandler.reset(new OnComplete() {
					@Override
					public void onComplete(Object object) {
					}
				});
			}
		}

		if (iInput != null)
			return iInput.tap(x, y, count, button);
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		if (iInput != null)
			return iInput.longPress(x, y);
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if (cameraHandler != null
				&& cameraHandler.getCamState() == CameraState.ZOOM_OUT) {
			cameraHandler.fling(velocityX, velocityY);
			return true;
		}

		if (iInput != null)
			return iInput.fling(velocityX, velocityY, button);
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if (cameraHandler != null
				&& cameraHandler.getCamState() == CameraState.ZOOM_OUT) {
			cameraHandler.pan(x, y, deltaX, deltaY);
			return true;
		}
		if (iInput != null)
			return iInput.pan(x, y, deltaX, deltaY);
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		if (iInput != null)
			return iInput.panStop(x, y, pointer, button);
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		if (iInput != null)
			return iInput.pinch(initialPointer1, initialPointer2, pointer1,
					pointer2);
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (iInput != null) {
			return iInput.touchDown(screenX, screenY, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (iInput != null)
			return iInput.touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (iInput != null)
			return iInput.touchDragged(screenX, screenY, pointer);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if (iInput != null)
			return iInput.mouseMoved(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public void setGestureDetector(IInputListener detector) {
		this.iInput = detector;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Engine getEngine() {
		return _Engine;
	}

	public void setEngine(Engine engine) {
		this._Engine = engine;
	}

}
