package engine.element;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Pool.Poolable;

import engine.module.updatehandler.IUpdateHandler;
import engine.module.updatehandler.UpdateHandlerList;

public class TableElement extends Table implements IEntityPosition,
		Poolable {
	public int id = -1;

	public boolean mIgnoreUpdate;

	public boolean drawChildren = true;

	public TableElement() {
		super();
		setUp();
	}

	public TableElement(Skin skin) {
		super(skin);
		setUp();
	}

	private void setUp() {
		setClip(true);
		setTransform(true);
		setOrigin(Align.center);
	}

	private UpdateHandlerList handlerList = new UpdateHandlerList(4);

	public void act(float deltatime) {
		if (!mIgnoreUpdate) {
			handlerList.onUpdate(deltatime);
			super.act(deltatime);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isVisible()) {
			drawBehind(batch);
			if (drawChildren)
				super.draw(batch, parentAlpha);
			drawInfront(batch);
		}
	}

	public void drawBehind(Batch batch) {
	}

	public void drawInfront(Batch batch) {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public void reset() {
		setTouchable(Touchable.disabled);
		setIgnoreUpdate(false);
		setId(-1);
		clearUpdateHandlers();
		clearChildren();
		clearActions();
		clearListeners();
		drawChildren = true;
		setUp();
	}
}
