package engine.element.iml;

import engine.element.iml.HomeItem.HomeItemName;

public interface ClickItemEvent {
	public void broadcastEvent(HomeItemName itemName);
}