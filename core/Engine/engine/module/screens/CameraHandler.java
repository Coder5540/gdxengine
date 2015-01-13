package engine.module.screens;

import utils.listener.OnCompleteListener;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.coder5560.game.enums.Constants;

public class CameraHandler {

	private OrthographicCamera	camera;
	private Actor				actorVariable;
	private Actor				actorTime;
	private boolean				isDoneAction	= false;
	private boolean				waitTouch		= false;
	private boolean				block			= false;

	private float				flingTime		= 0.1f;
	private float				flingTimer		= 0;
	private float				amountX			= 0;
	private float				amountY			= 0;
	private float				velocityX		= 0;
	private float				velocityY		= 0;
	
	OnCompleteListener			onCompleteIntroducePlayerListener;
	public OnCompleteListener	waitTouchListener;

	public enum CameraState {
		NORMAL, ZOOM_OUT, ZOOM_IN, RESET

	}

	private CameraState	camState	= CameraState.NORMAL;

	public CameraHandler(OrthographicCamera camera) {
		super();
		this.camera = camera;
		actorVariable = new Actor();
		actorTime = new Actor();
	}

	public void update(float delta) {
		actorVariable.act(delta);
		actorTime.act(delta);

		if (getCamState() == CameraState.RESET) {
			if (!isDoneAction) {
				camera.position.set(actorVariable.getX(), actorVariable.getY(),
						0);
				camera.zoom = actorVariable.getRotation();
			}
		}
		if (getCamState() == CameraState.ZOOM_OUT) {
			if (!isDoneAction) {
				camera.position.set(actorVariable.getX(), actorVariable.getY(),
						0);
				camera.zoom = actorVariable.getRotation();
			} else {
				float alpha = flingTimer / flingTime;
				amountX = velocityX * alpha * delta;
				amountY = velocityY * alpha * delta;
				flingTimer -= delta * 0.09f;
				if (flingTimer <= 0) {
					velocityX = 0;
					velocityY = 0;
				} else {
					camera.position.set(
							MathUtils.clamp(camera.position.x - amountX
									* camera.zoom, Constants.WIDTH_SCREEN
									* camera.zoom / 2, Constants.WIDTH_SCREEN
									* (1 - camera.zoom / 2)),
							camera.position.y = MathUtils.clamp(
									camera.position.y + amountY * camera.zoom,
									Constants.HEIGHT_SCREEN * camera.zoom / 2,
									Constants.HEIGHT_SCREEN
											* (1 - camera.zoom / 2)), 0);
				}
			}
		}
	}

	public void reset(final OnCompleteListener onResetListener) {
		setCamState(CameraState.RESET);
		actorVariable.clearActions();
		actorVariable.setPosition(camera.position.x, camera.position.y);
		actorVariable.setRotation(camera.zoom);
		Action actionMoveTo = Actions.moveTo(Constants.WIDTH_SCREEN / 2,
				Constants.HEIGHT_SCREEN / 2, .5f, Interpolation.exp5Out);
		Action zoomin = Actions.rotateTo(1f, 0.5f);
		setDoneAction(false);
		actorVariable.addAction(Actions.sequence(
				Actions.parallel(actionMoveTo, zoomin),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						setCamState(CameraState.NORMAL);
						setDoneAction(true);
						if (onResetListener != null)
							onResetListener.onComplete(null);
					}
				})));
	}

	public void zoomOut(Vector2 position, float zoom,
			final OnCompleteListener oncompleteListener) {
		setCamState(CameraState.ZOOM_OUT);
		actorVariable.clearActions();
		actorVariable.setPosition(camera.position.x, camera.position.y);
		actorVariable.setRotation(camera.zoom);
		Action actionMoveTo = Actions.moveTo(position.x, position.y, .5f,
				Interpolation.exp5In);
		Action zoomin = Actions.rotateTo(zoom, 0.5f);
		setDoneAction(false);
		actorVariable.addAction(Actions.sequence(
				Actions.parallel(actionMoveTo, zoomin),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						setDoneAction(true);
						if (oncompleteListener != null)
							oncompleteListener.onComplete(null);
					}
				})));

	}

	public CameraState getCamState() {
		return camState;
	}

	public void setCamState(CameraState camState) {
		this.camState = camState;
	}

	public boolean tap(float x, float y, int count) {
		if (waitTouchListener != null) {
			if (waitTouch) {
				waitTouchListener.onComplete(null);
				waitTouchListener = null;
				waitTouch = false;
			}
			return true;
		}

		// if (count >= 2 && !isBlock()) {
		// if (getCamState() == CameraState.NORMAL) {
		// float scale = 0.6f;
		// zoomOut(Factory.getVisualPoint(new Vector2(x, y), scale),
		// scale, new OnCompleteListener() {
		//
		// @Override
		// public void onComplete() {
		//
		// }
		// });
		// } else if (getCamState() == CameraState.ZOOM_OUT) {
		// reset(new OnCompleteListener() {
		// @Override
		// public void onComplete() {
		// }
		// });
		// }
		// }

		return false;
	}

	public void pan(float x, float y, float deltaX, float deltaY) {
		if (waitTouchListener != null)
			return;

		if (isDoneAction && !block)
			camera.position.set(
					MathUtils.clamp(camera.position.x - deltaX * camera.zoom,
							Constants.WIDTH_SCREEN * camera.zoom / 2,
							Constants.WIDTH_SCREEN * (1 - camera.zoom / 2)),
					camera.position.y = MathUtils.clamp(camera.position.y
							+ deltaY * camera.zoom, Constants.HEIGHT_SCREEN
							* camera.zoom / 2, Constants.HEIGHT_SCREEN
							* (1 - camera.zoom / 2)), 0);
	}

	public void fling(float velocityX, float velocityY) {
		if (waitTouchListener != null)
			return;
		if (isDoneAction && !block) {
			flingTimer = flingTime;
			this.velocityX = velocityX;
			this.velocityY = velocityY;
		}
	}

	public void waitTouch(float f, final OnCompleteListener onWaitDone) {
		this.waitTouchListener = onWaitDone;
		actorTime.clearActions();
		actorTime.addAction(Actions.sequence(Actions.delay(f),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						waitTouch = true;
					}
				})));
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public boolean isDoneAction() {
		return isDoneAction;
	}

	public void setDoneAction(boolean isDoneAction) {
		this.isDoneAction = isDoneAction;
	}
}
