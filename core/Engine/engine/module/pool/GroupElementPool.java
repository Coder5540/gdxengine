package engine.module.pool;

import com.badlogic.gdx.utils.Pool;

import engine.element.GroupElement;

public class GroupElementPool extends Pool<GroupElement> {
	
	
	public GroupElementPool() {
		super();
	}

	public GroupElementPool(int initialCapacity, int max) {
		super(initialCapacity, max);
	}

	public GroupElementPool(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	protected GroupElement newObject() {
		return new GroupElement();
	}
}
