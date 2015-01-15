package engine.module.view;

import engine.element.Engine;
import engine.module.screens.AbstractGameScreen;
import engine.module.screens.GameCore;

public interface IController {

	public void update(float delta);

	public GameCore getGameCore();

	public Engine getEngine();

	public AbstractGameScreen getScreen();
	
	public boolean isContainView(ViewName name);

	public IViewElement getView(ViewName name);

	public IController removeView(ViewName name);

	public IController addView(IViewElement view);

	public IViewElement getCurrentView();

	public IController setCurrentView(IViewElement view);

	public IController onBack();

}
