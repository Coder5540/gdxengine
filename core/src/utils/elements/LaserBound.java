package utils.elements;

import utils.listener.OnComplete;
import utils.listener.OnCompleteListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class LaserBound {
	public Array<Vector2>	vertices;
	public Array<LaserLine>	lasers;
	Color					color;
	private boolean			isDoneAction	= true;

	public LaserBound(Array<Vector2> vertices) {
		super();
		lasers = new Array<LaserLine>();
		this.vertices = vertices;
		createLaser(vertices);
	}

	public LaserBound(Array<Vector2> vertices, Color color) {
		super();
		lasers = new Array<LaserLine>();
		this.color = color;
		this.vertices = vertices;
		createLaser(vertices);
	}

	void createLaser(Array<Vector2> vertice) {
		if (vertice.size < 2)
			return;
		for (int i = 0; i < vertice.size - 1; i++) {
			LaserLine line = new LaserLine(vertice.get(i), vertice.get(i + 1));
			if (color != null) {
				line.setColor(color, line.rayColor);
			}
			lasers.add(line);
		}
	}

	public void render(SpriteBatch batch, float delta) {
		for (LaserLine laser : lasers) {
			laser.render(batch, delta);
		}
	}

	public boolean intersect(Rectangle bound) {
		for (LaserLine laser : lasers) {
			if (laser.intersect(bound))
				return true;
		}
		return false;
	}

	public void hide(float duration, final OnCompleteListener onCompleteListener) {
		if (lasers.size == 0) {
			setDoneAction(false);
			if (onCompleteListener != null)
				onCompleteListener.onComplete(null);
			return;
		}
		setDoneAction(false);
		for (int i = 0; i < lasers.size; i++) {
			lasers.get(i).hide(duration,
					(i != lasers.size - 1) ? null : new OnComplete() {

						@Override
						public void onComplete(Object object) {
							setDoneAction(true);
							if (onCompleteListener != null)
								onCompleteListener.onComplete(null);
						}
					});
		}

	}

	public void show(float duration, final OnCompleteListener onCompleteListener) {
		if (lasers.size == 0) {
			setDoneAction(true);
			if (onCompleteListener != null)
				onCompleteListener.onComplete(null);
			return;
		}

		setDoneAction(false);
		for (int i = 0; i < lasers.size; i++) {
			lasers.get(i).show(duration,
					(i != lasers.size - 1) ? null : new OnComplete() {
						@Override
						public void onComplete(Object object) {
							setDoneAction(true);
							if (onCompleteListener != null)
								onCompleteListener.onComplete(null);
						}
					});
		}
	}

	public void hide() {
		setDoneAction(false);
		for (int i = 0; i < lasers.size; i++) {
			lasers.get(i).hide();
		}
	}

	public boolean isDoneAction() {
		return isDoneAction;
	}

	public void setDoneAction(boolean isDoneAction) {
		this.isDoneAction = isDoneAction;
	}

}
