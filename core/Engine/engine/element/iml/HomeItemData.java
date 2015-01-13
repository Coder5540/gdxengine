package engine.element.iml;

import com.badlogic.gdx.graphics.Texture;

import engine.element.iml.HomeItem.HomeItemName;

public class HomeItemData {
	public HomeItemName itemName = HomeItemName.ITEM_DEFAULT;
	public float width;
	public float height;
	public Texture iconTexture = null;
	public String title = "";
	public ClickItemEvent homeItemClickEvent;

	public HomeItemData(HomeItemName itemName, float width, float height,
			Texture iconTexture, String title, ClickItemEvent homeItemClickEvent) {
		super();
		this.itemName = itemName;
		this.width = width;
		this.height = height;
		this.iconTexture = iconTexture;
		this.title = title;
		this.homeItemClickEvent = homeItemClickEvent;
	}
}