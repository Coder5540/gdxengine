package engine.module.list;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.coder5560.game.enums.Direct;

public class PanZoomOut extends PanListView {
	float maxScale = 1.2f;

	public PanZoomOut(Rectangle bound, PanStopListener onStopListener) {
		super(bound, onStopListener);
	}

	@Override
	public void onUpdate(float delta) {
		onUpdateOther(delta);
	}

	private void onUpdateOther(float delta) {
		next.setPosition(current.getX() + current.getWidth(), current.getY());
		previous.setPosition(current.getX() - previous.getWidth(),
				current.getY());

		float percent = Math.abs(current.getX(Align.center)
				- (bound.x + bound.width / 2))
				/ (bound.width / 2);
		percent = MathUtils.clamp(percent, 0, 1f);

		next.setScale(1 - (1 - percent) * (maxScale - 1));
		previous.setScale(1 - (1 - percent) * (maxScale - 1));
		current.setScale(1 - percent * (maxScale - 1));

		next.setTouchable(Touchable.disabled);
		previous.setTouchable(Touchable.disabled);
	}

	@Override
	public void onPan(float x, float y, float deltaX, float deltaY) {
		current.setPosition(current.getX() + deltaX, current.getY() + deltaY);
	}

	@Override
	public void onStop(float x, float y) {
		current.clearActions();
		if (current.getX(Align.center) > bound.x + 2 * bound.width / 3) {
			current.addAction(Actions.sequence(Actions.moveTo(bound.x
					+ bound.width, bound.y, .25f, Interpolation.linear),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							onStopListener.onStop(Direct.RIGHT);
						}
					})));
		} else if (current.getX(Align.center) < bound.x + bound.width / 3) {
			current.addAction(Actions.sequence(Actions.moveTo(bound.x
					- bound.width, bound.y, .25f, Interpolation.linear),
					Actions.run(new Runnable() {	
						@Override
						public void run() {
							onStopListener.onStop(Direct.LEFT);
						}
					})));
		} else {
			current.addAction(Actions.sequence(Actions.moveTo(bound.x, bound.y,
					.3f, Interpolation.linear)));
		}
	}

	@Override
	public void onFling(float velocityX, float velocityY) {

	}
}