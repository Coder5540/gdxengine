package engine.module.pool;

import utils.listener.OnCompleteListener;
import engine.module.updatehandler.UpdateHandlerList;

public class PoolManager {
	public UpdateHandlerList listHandlers;

	public GroupElementPool groupElementPool;

	public TableElementPool tableElementPool;

	public ElementPool elementPool;

	public void onCreate(OnCompleteListener onComplete) {
		// ===========Create pool and then call the event done========

		listHandlers = new UpdateHandlerList(10);

		groupElementPool = new GroupElementPool(200);

		tableElementPool = new TableElementPool(200);

		elementPool = new ElementPool(100);

		if (onComplete != null)
			onComplete.onComplete("");

	}

	public void onUpdate(float delta) {

	}

	public void onPause() {
		
	}

	public void onDispose() {
	}

}
