package engine.module.list;

import com.badlogic.gdx.utils.Pool.Poolable;

import engine.element.GroupElement;

public abstract class AbstractItem extends GroupElement implements Poolable {

	public abstract AbstractItem recreateData(Object object);

	public abstract int getIndex();
}
