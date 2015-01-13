package engine.element.iml;

import utils.elements.Img;
import utils.factory.FontFactory.fontType;
import utils.listener.OnClickListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.coder5560.game.assets.Assets;

import engine.element.GroupElement;

public class HomeItem extends GroupElement {
	Img bg, icon;
	Label lbTitle;
	LabelStyle labelStyle;

	public enum HomeItemName {
		ITEM_DEFAULT(-1),

		;
		int value;

		private HomeItemName(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	public HomeItem(final HomeItemData itemData) {
		setSize(itemData.width, itemData.height);
		this.setOrigin(Align.center);

		labelStyle = new LabelStyle();
		labelStyle.font = Assets.instance.fontFactory
				.getFont(14, fontType.Bold);
		lbTitle = new Label(itemData.title, labelStyle);
		labelStyle.fontColor = Color.BLACK;

		NinePatch ninePatch = new NinePatch(Assets.instance.ui.reg_ninepatch1,
				6, 6, 6, 6);
		ninePatch.setColor(Color.WHITE);
		bg = new Img(ninePatch);
		icon = new Img(itemData.iconTexture);

		{
			this.addActor(bg);
			this.addActor(icon);
			this.addActor(lbTitle);
		}

		icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(float x, float y) {
				if (itemData.homeItemClickEvent != null) {
					itemData.homeItemClickEvent
							.broadcastEvent(itemData.itemName);
				}
			}
		});
		valid();
	}

	public void valid() {
		bg.setSize(getWidth(), getHeight());
		icon.setSize(3 * getWidth() / 4, 3 * getHeight() / 4);
		bg.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
		icon.setPosition(bg.getWidth() / 2, bg.getHeight() / 2, Align.center);
		lbTitle.setPosition(bg.getX(Align.bottom), bg.getY(Align.bottom)
				- lbTitle.getHeight(), Align.center);
	}
}
