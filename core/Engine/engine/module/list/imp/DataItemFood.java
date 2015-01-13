package engine.module.list.imp;

import engine.module.list.AbstractDataItem;
import utils.listener.OnSelectListener;

public class DataItemFood extends AbstractDataItem {
	public String	title		= "";
	public String	description	= "";

	public DataItemFood(int index, String title, String description,
			float width, float height, final OnSelectListener onSelectListener) {
		super(index, width, height, onSelectListener);
		this.title = title;
		this.description = description;
	}
}