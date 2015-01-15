package engine.module.pool;

import com.badlogic.gdx.utils.Pool;

import engine.element.Element;

public class ElementPool extends Pool<Element> {

	public ElementPool() {
		super();
	}

	public ElementPool(int initialCapacity, int max) {
		super(initialCapacity, max);
	}

	public ElementPool(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	protected Element newObject() {
		return new Element();
	}

	
	
}
