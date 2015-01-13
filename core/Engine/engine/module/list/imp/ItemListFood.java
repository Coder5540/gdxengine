package engine.module.list.imp;

import utils.factory.Factory;
import utils.factory.FontFactory.fontType;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.coder5560.game.assets.Assets;

import engine.module.list.AbstractItem;

public class ItemListFood extends AbstractItem {
	private Image	bg;
	private LabelStyle	styleTitle, styleDesciption;
	private Label		lbTitle, lbShortDescription;
	private Color		bgColor, wordColor, desColor;
	DataItemFood		dataItemList;

	public ItemListFood(DataItemFood dataItemList) {
		this.dataItemList = dataItemList;
		createColor();
		{
			styleTitle = new LabelStyle();
			styleTitle.font = Assets.instance.fontFactory.getFont(22,
					fontType.Medium);
			styleTitle.fontColor = wordColor;

			styleDesciption = new LabelStyle();
			styleDesciption.font = Assets.instance.fontFactory.getFont(18,
					fontType.Regular);
			styleDesciption.fontColor = desColor;
		}

		{
			lbTitle = new Label("", styleTitle);
			lbTitle.setAlignment(Align.left);
			lbTitle.setTouchable(Touchable.disabled);
		}

		lbShortDescription = new Label(Factory.getSubString("",
				2 * getWidth() / 3 - 10, styleDesciption.font), styleDesciption);
		lbShortDescription.setAlignment(Align.left);
		lbShortDescription.setTouchable(Touchable.disabled);
		lbShortDescription.setWrap(true);

		bg = new Image(Assets.instance.ui.reg_ninepatch);
		bg.setColor(bgColor);

		bg.addListener(new ClickListener() {
			Vector2	touch	= new Vector2();

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				touch.set(x, y);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (!touch.epsilonEquals(x, y, 20))
					return;
				final float px = x, py = y;
				bg.addAction(Actions.sequence(Actions.color(new Color(
						220 / 255f, 220 / 255f, 220 / 255f, 1f), 0.1f), Actions
						.run(new Runnable() {

							@Override
							public void run() {
								if (ItemListFood.this.dataItemList != null
										&& ItemListFood.this.dataItemList.onSelectListener != null) {
									ItemListFood.this.dataItemList.onSelectListener
											.onSelect(
													ItemListFood.this.dataItemList.index,
													"");
								}
								bg.setColor(bgColor);
							}
						})));

			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (!touch.epsilonEquals(x, y, 10))
					touch.set(0, 0);
				super.touchDragged(event, x, y, pointer);
			}
		});
		addActor(bg);
		addActor(lbTitle);
		addActor(lbShortDescription);
		recreateData(this.dataItemList);
	}

	private void createColor() {
		bgColor = new Color(10 / 255f, 10 / 255f, 10 / 255f, 0.95f);
		wordColor = new Color(255 / 255f, 255 / 255f, 255 / 255f, 0.95f);
		desColor = new Color(245 / 255f, 245 / 255f, 245 / 255f, 0.95f);
	}

	public void valid() {
		setSize(dataItemList.width, dataItemList.height);
		float w = this.getWidth();
		float h = this.getHeight();
		bg.setSize(w - 10, h - 2);
		bg.setPosition(5, 1);
		lbShortDescription.setWidth(2 * w / 3);
		lbTitle.setPosition(10,
				bg.getY(Align.top) - styleTitle.font.getCapHeight() - 5);
		lbShortDescription.setPosition(40, lbTitle.getY()
				- styleDesciption.font.getCapHeight() - 10);
	}

	@Override
	public int getIndex() {
		return dataItemList.index;
	}

	public ItemListFood recreateData(Object object) {
		if (object instanceof DataItemFood) {
			DataItemFood data = (DataItemFood) object;
			this.dataItemList = data;
			this.setSize(data.width, data.height);
			lbTitle.setText(data.title);
			lbShortDescription
					.setText(Factory.getSubString(data.description,
							2 * getWidth() / 3 - 10,
							lbShortDescription.getStyle().font));
			valid();
		}

		return this;
	}

	@Override
	public void reset() {

	}

}