package engine.element;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import engine.module.updatehandler.IUpdateHandler;
import engine.module.updatehandler.UpdateHandlerList;

public class GroupElement extends Group implements IEntityPosition {

	protected boolean mIgnoreUpdate;

	private UpdateHandlerList handlerList = new UpdateHandlerList(4);

	private boolean drawChildren = true;

	public GroupElement() {
		super();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isVisible()) {
			beginDraw(batch);

			if (drawChildren)
				super.draw(batch, parentAlpha);

			endDraw(batch);
		}
	}

	public void beginDraw(Batch batch) {
	}

	public void endDraw(Batch batch) {
	}

	public boolean isDrawChildren() {
		return drawChildren;
	}

	public void setDrawChildren(boolean drawChildren) {
		this.drawChildren = drawChildren;
	}

	public void registerUpdateHandler(IUpdateHandler handler) {
		handlerList.add(handler);
	}

	public boolean unregisterUpdateHandler(IUpdateHandler handler) {
		return handlerList.removeValue(handler, true);
	}

	public void act(float deltatime) {
		if (!mIgnoreUpdate) {
			handlerList.onUpdate(deltatime);
			super.act(deltatime);
		}
	}

	public boolean isIgnoreUpdate() {
		return mIgnoreUpdate;
	}

	public void setIgnoreUpdate(boolean pIgnoreUpdate) {
		mIgnoreUpdate = pIgnoreUpdate;
	}

	public boolean clearUpdatehandler(IUpdateHandler handler) {
		if (handlerList == null)
			return false;

		return handlerList.removeValue(handler, true);
	}

	public void clearUpdateHandlers() {
		if (handlerList == null) {
			return;
		}
		handlerList.clear();
	}

}
