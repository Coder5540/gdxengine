package engine.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import engine.module.updatehandler.IUpdateHandler;
import engine.module.updatehandler.UpdateHandlerList;

public class Element extends Actor implements IEntityPosition{

	protected boolean mIgnoreUpdate;
	
	private UpdateHandlerList handlerList = new UpdateHandlerList(4);
	
	public Element() {
		super();
	}
	public void draw(Batch batch, float parentAlpha) {
		if(isVisible()) {
			Color color = getColor();        							
			
	        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
	
	        beginDrawActor(batch);
	        
	        onDraw(batch);	        	        
	        
	        endDrawActor(batch);
		}
	}	
	
	protected void beginDrawActor(Batch batch) {
		
	}
	
	protected void endDrawActor(Batch batch) {
		
	}
	
	public void onDraw(Batch batch) {
		// implemented by subclass
	}
	
	public void registerUpdateHandler(IUpdateHandler handler) {
		handlerList.add(handler);
	}
	
	public boolean unregisterUpdateHandler(IUpdateHandler handler) {		
		return handlerList.removeValue(handler,true);
	}
	
	public void act(float deltatime) {
		if(!mIgnoreUpdate) {
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

	public void clearUpdateHandlers() {
		if(handlerList == null) {
			return;
		}		
		handlerList.clear();
	}

}
