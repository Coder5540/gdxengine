package engine.module.list;

import utils.listener.OnSelectListener;

public abstract class AbstractDataItem {
	public int				index				= -1;
	public float			width;
	public float			height;
	public OnSelectListener	onSelectListener	= null;

	public AbstractDataItem(int index, float width, float height,
			OnSelectListener onSelectListener) {
		super();
		this.index = index;
		this.width = width;
		this.height = height;
		this.onSelectListener = onSelectListener;
	}
}