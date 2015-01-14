package engine.module.view;

import utils.listener.OnCompleteListener;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.coder5560.game.enums.GameEvent;
import com.coder5560.game.enums.ViewState;

import engine.module.updatehandler.IUpdateHandler;

public interface IViewElement {

	public IViewElement buildComponent();

	public IViewElement show(Stage stage, float duration,
			final OnCompleteListener listener);

	public IViewElement hide(float duration, final OnCompleteListener listener);

	public void act(float deltatime);

	public void onBeginDraw(Batch batch);

	public void onEndDraw(Batch batch);

	public void registerUpdateHandler(IUpdateHandler handler);

	public boolean unregisterUpdateHandler(IUpdateHandler handler);

	public boolean clearUpdatehandler(IUpdateHandler handler);

	public void clearUpdateHandlers();

	public IViewElement setViewState(ViewState state);

	public ViewState getViewState();

	public boolean isIgnoreUpdate();

	public void setIgnoreUpdate(boolean pIgnoreUpdate);

	public IViewElement setViewName(ViewName viewName);

	public ViewName getParentViewName();

	public ViewName getViewName();

	public IViewElement setAutoRemoveWhenSwitchView(boolean autoRemove);

	public boolean isAutoRemoveWhenSwitchView();

	public IController getController();

	public void setPosition(float x, float y);

	public Rectangle getBound();

	public IViewElement onGameEvent(GameEvent gameEvent);

	public IViewElement destroyElement();

	public void back();
}
