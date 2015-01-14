package engine.module.list;

import com.badlogic.gdx.math.Rectangle;

import engine.element.TableElement;

public abstract class PanListView {
	public TableElement current, next, previous;
	public Rectangle bound;
	public PanStopListener onStopListener;

	public PanListView(Rectangle bound, PanStopListener onStopListener) {
		super();
		this.bound = bound;
		this.onStopListener = onStopListener;
	}

	public void setData(TableElement current, TableElement next, TableElement previous) {
		this.current = current;
		this.next = next;
		this.previous = previous;
	}

	public abstract void onUpdate(float deltaTime);

	public abstract void onPan(float x, float y, float deltaX, float deltaY);

	public abstract void onStop(float x, float y);

	public abstract void onFling(float velocityX, float velocityY);

}