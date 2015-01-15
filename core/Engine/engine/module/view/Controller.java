package engine.module.view;

import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.Actor;

import utils.factory.Log;
import engine.element.Engine;
import engine.module.screens.AbstractGameScreen;
import engine.module.screens.GameCore;

public abstract class Controller implements IController {

	public GameCore							_GameCore;
	public Engine							_Engine;
	public AbstractGameScreen				_Screen;
	public IViewElement						_CurrentView;
	public HashMap<ViewName, IViewElement>	map;
	public Controller(GameCore _GameCore, Engine _Engine,
			AbstractGameScreen _Screen) {
		super();
		this._GameCore = _GameCore;
		this._Engine = _Engine;
		this._Screen = _Screen;
		map = new HashMap<ViewName, IViewElement>();

	}

	public abstract IController buildController();

	@Override
	public void update(float delta) {

	}

	@Override
	public GameCore getGameCore() {
		return _GameCore;
	}

	@Override
	public Engine getEngine() {
		return _Engine;
	}

	@Override
	public AbstractGameScreen getScreen() {
		return _Screen;
	}

	@Override
	public boolean isContainView(ViewName name) {
		return map.containsKey(name);
	}

	@Override
	public IViewElement getView(ViewName name) {
		if (!isContainView(name)) {
			return null;
		}
		return map.get(name);
	}

	@Override
	public IController removeView(ViewName name) {
		if (isContainView(name)) {
			IViewElement view = map.get(name);
			view.destroyElement();
			map.remove(name);
		}
		return this;
	}

	@Override
	public IController addView(IViewElement view) {
		if (isContainView(view.getViewName())) {
			Log.d("View " + view.getViewName() + " has been added in this map!");
			return this;
		}
		map.put(view.getViewName(), view);
		return this;
	}

	@Override
	public IViewElement getCurrentView() {
		return _CurrentView;
	}

	@Override
	public IController setCurrentView(IViewElement view) {
		if (_CurrentView != null && _CurrentView.isAutoRemoveWhenSwitchView()) {
			((Actor) _CurrentView).remove();
		}
		this._CurrentView = view;
		return this;
	}

	public IController onBack() {
		if (_CurrentView != null) {
			if (_CurrentView.getParentViewName() == ViewName.DEFAULT) {
				// exit app
				Log.d("On Back in default value");
			} else {
				_CurrentView.back();
			}

		}
		return this;
	}

}
