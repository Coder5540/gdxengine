package com.coder5560.game.views;

import imp.view.HomeView;
import imp.view.HomeViewBackUp;
import imp.view.MainMenu;
import imp.view.TopBarView;
import utils.factory.PlatformResolver;
import utils.factory.StringSystem;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.coder5560.game.enums.Constants;
import com.coder5560.game.enums.ViewState;

import engine.module.networks.FacebookConnector;
import engine.module.screens.AbstractGameScreen;
import engine.module.screens.GameCore;

public class ViewController implements IViewController {
	public Stage				stage;
	public Array<IViews>		views;

	public IViews				currentView;
	public FacebookConnector	facebookConnector;
	private GameCore			_gameParent;
	private AbstractGameScreen			_gameScreen;
	public PlatformResolver		platformResolver;

	public ViewController(GameCore _gameParent, AbstractGameScreen gameScreen) {
		super();
		this._gameParent = _gameParent;
		this._gameScreen = gameScreen;
	}

	public void build(Stage stage) {
		this.stage = stage;
		views = new Array<IViews>();
		HomeView homeView = new HomeView();
		homeView.build(getStage(), this, StringSystem.VIEW_HOME, new Rectangle(
				0, 0, Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN
						- Constants.HEIGHT_ACTIONBAR));
		homeView.buildComponent();

		TopBarView topBarView = new TopBarView();
		topBarView.build(getStage(), this, StringSystem.VIEW_ACTION_BAR,
				new Rectangle(0, Constants.HEIGHT_SCREEN
						- Constants.HEIGHT_ACTIONBAR, Constants.WIDTH_SCREEN,
						Constants.HEIGHT_ACTIONBAR));
		topBarView.buildComponent();

		MainMenu mainMenu = new MainMenu();
		mainMenu.build(getStage(), this, StringSystem.VIEW_MAIN_MENU,
				new Rectangle(0, 0, Constants.WIDTH_SCREEN,
						Constants.HEIGHT_SCREEN));
		mainMenu.buildComponent();
		mainMenu.show(null);
		toFront(StringSystem.VIEW_HOME);
	}

	@Override
	public void update(float delta) {
		for (int i = 0; i < views.size; i++) {
			views.get(i).update(delta);
			if (views.get(i).getViewState() == ViewState.DISPOSE) {
				removeView(views.get(i).getName());
			}
		}
	}

	@Override
	public boolean isContainView(String name) {
		if (avaiable()) {
			for (int i = 0; i < views.size; i++) {
				if (views.get(i).getName().equalsIgnoreCase(name))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean addView(IViews view) {
		if (!avaiable())
			return false;
		if (isContainView(view.getName()))
			return false;
		views.add(view);
		return true;
	}

	@Override
	public void removeView(String name) {
		if (!avaiable())
			return;
		IViews view = getView(name);
		if (view == null)
			return;
		view.destroyComponent();
		views.removeValue(view, false);
		stage.getActors().removeValue((Actor) view, true);
	}

	@Override
	public void toFront(String name) {
		if (isContainView(name)) {
			((Actor) getView(name)).toFront();
			if (isContainView(StringSystem.VIEW_MAIN_MENU))
				((Actor) getView(StringSystem.VIEW_MAIN_MENU)).toFront();
			if (isContainView(StringSystem.VIEW_ACTION_BAR))
				((Actor) getView(StringSystem.VIEW_ACTION_BAR)).toFront();
		}
	}

	@Override
	public IViews getView(String name) {
		for (int i = 0; i < views.size; i++) {
			if (views.get(i).getName().equalsIgnoreCase(name)) {
				return views.get(i);
			}
		}
		return null;
	}

	@Override
	public Array<IViews> getViews() {
		if (avaiable())
			return views;
		return null;
	}

	public boolean avaiable() {
		return views != null && stage != null;
	}

	@Override
	public Stage getStage() {
		return stage;
	}

	public void setFacebookConnector(FacebookConnector facebookConnector) {
		this.facebookConnector = facebookConnector;
	}

	public FacebookConnector getFacebookConnector() {
		return facebookConnector;
	}

	@Override
	public void setGameParent(GameCore gameParent) {
		this._gameParent = gameParent;
	}

	@Override
	public GameCore getGameParent() {
		return _gameParent;
	}

	// this method will sort that all of our view from a container of view.
	@Override
	public void sortView() {
	}

	@Override
	public IViews getCurrentView() {
		return currentView;
	}

	@Override
	public void setCurrentView(IViews view) {
		this.currentView = view;
		TraceView.instance.addViewToTrace(view.getName());
	}

	@Override
	public AbstractGameScreen getScreen() {
		return _gameScreen;
	}

	public PlatformResolver getPlatformResolver() {
		return platformResolver;
	}

	public void setPlatformResolver(PlatformResolver platformResolver) {
		this.platformResolver = platformResolver;
	}

}
