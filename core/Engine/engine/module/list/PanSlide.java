package engine.module.list;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.coder5560.game.enums.Direct;

public class PanSlide extends PanListView {
	public PanSlide(Rectangle bound, PanStopListener onStopListener) {
		super(bound, onStopListener);
	}

	@Override
	public void onUpdate(float delta) {
		onUpdateOther(delta);
	}

	private void udateOther(float delta) {
		next.setPosition(current.getX() + current.getWidth(), current.getY());
		previous.setPosition(current.getX() - previous.getWidth(),
				current.getY());
		next.setTouchable(Touchable.disabled);
		previous.setTouchable(Touchable.disabled);
	}

	private void onUpdateOther(float delta) {
		current.setVisible(true);
		// ======= process when we have next and previous page ===
		if (hasNext() && hasPrevious()) {
			if (current.getX(Align.center) > bound.x + bound.width / 2) {
				next.setPosition(current.getX(Align.right), bound.y);
				next.setVisible(false);
				previous.setVisible(true);
				previous.setPosition(
						current.getX(Align.left) - previous.getWidth(), bound.y);
				previous.setZIndex(current.getZIndex() - 1);
			} else if (current.getX(Align.center) < bound.x + bound.width / 2) {
				previous.setVisible(false);
				previous.setPosition(
						current.getX(Align.left) - previous.getWidth(), bound.y);
				next.setPosition(current.getX(Align.right), bound.y);
				next.setVisible(true);
				next.setZIndex(current.getZIndex() - 1);
			}
			next.setTouchable(Touchable.disabled);
			previous.setTouchable(Touchable.disabled);
		}

		// ==== Process when we only have the previous page ========
		if (!hasNext() && hasPrevious()) {
			previous.setPosition(bound.x, bound.y);
			previous.setZIndex(current.getZIndex() - 1);
			if (current.getX(Align.center) > bound.x + bound.width / 2) {
				previous.setVisible(true);
			} else if (current.getX(Align.center) < bound.x + bound.width / 2) {
				previous.setVisible(false);
			}
			previous.setTouchable(Touchable.disabled);
		}

		// ==== Process when we only have the next page ========
		if (hasNext() && !hasPrevious()) {
			next.setPosition(bound.x, bound.y);
			next.setZIndex(current.getZIndex() - 1);
			if (current.getX(Align.center) > bound.x + bound.width / 2) {
				next.setVisible(false);
			} else if (current.getX(Align.center) < bound.x + bound.width / 2) {
				next.setVisible(true);
			}
			next.setTouchable(Touchable.disabled);
		}
	}

	public boolean hasNext() {
		return next != null;
	}

	public boolean hasPrevious() {
		return previous != null;
	}

	@Override
	public void onPan(float x, float y, float deltaX, float deltaY) {
		if (previous == null) {
			current.setPosition(current.getX() + ((deltaX > 0) ? 0 : deltaX),
					current.getY() + deltaY);
			return;
		}
		if (next == null) {
			current.setPosition(current.getX() + ((deltaX < 0) ? 0 : deltaX),
					current.getY() + deltaY);
			return;
		}
		current.setPosition(current.getX() + deltaX, current.getY() + deltaY);
	}

	@Override
	public void onStop(float x, float y) {
		current.clearActions();
		if (current.getX(Align.center) > bound.x + 2 * bound.width / 3) {
			current.addAction(Actions.sequence(
					Actions.moveTo(bound.x + bound.width, bound.y, .25f),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							onStopListener.onStop(Direct.RIGHT);
						}
					})));
		} else if (current.getX(Align.center) < bound.x + bound.width / 3) {
			current.addAction(Actions.sequence(
					Actions.moveTo(bound.x - bound.width, bound.y, .25f),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							onStopListener.onStop(Direct.LEFT);
						}
					})));
		} else {
			current.addAction(Actions.sequence(Actions.moveTo(bound.x, bound.y,
					.3f)));
		}
	}

	@Override
	public void onFling(float velocityX, float velocityY) {

	}
}