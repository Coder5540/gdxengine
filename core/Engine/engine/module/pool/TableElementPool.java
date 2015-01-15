package engine.module.pool;

import com.badlogic.gdx.utils.Pool;

import engine.element.TableElement;

public class TableElementPool extends Pool<TableElement> {

	
	public TableElementPool() {
		super();
	
	}

	public TableElementPool(int initialCapacity, int max) {
		super(initialCapacity, max);
	
	}

	public TableElementPool(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	protected TableElement newObject() {
		return new TableElement();
	}

}
