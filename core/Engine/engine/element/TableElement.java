package engine.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import engine.module.updatehandler.IUpdateHandler;
import engine.module.updatehandler.UpdateHandlerList;

public abstract class TableElement extends Table implements IEntityPosition {

	protected boolean			mIgnoreUpdate;

	private boolean				drawChildren	= true;
	
	private UpdateHandlerList	handlerList		= new UpdateHandlerList(4);

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isVisible()) {
			beginDrawActor(batch);

			Color color = getColor();

			batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

			onDraw(batch);

			beginDrawChildren(batch);

			if (drawChildren)
				super.draw(batch, parentAlpha);

			endDrawActor(batch);
		}
	}

	
	protected void beginDrawActor(Batch batch) {

	}
	public void onDraw(Batch batch) {
	}
	
	protected void beginDrawChildren(Batch batch) {

	}

	protected void endDrawActor(Batch batch) {

	}

	public abstract int getId();
	
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
		if (handlerList == null)
			return;
		handlerList.clear();
	}

}
