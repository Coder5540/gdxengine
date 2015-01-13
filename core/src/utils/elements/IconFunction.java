package utils.elements;

import utils.factory.FontFactory.fontType;
import utils.factory.StringSystem;
import utils.listener.OnClickListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.views.IViewController;

public class IconFunction extends Group {
	Img		bg, icon, bgTitle;
	Label	lbTitle;

	public IconFunction(final IViewController viewController, float width,
			float height, Texture iconTexture, final String title) {
		setSize(width, height);
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = Assets.instance.fontFactory
				.getFont(14, fontType.Bold);
		lbTitle = new Label(title, labelStyle);
		labelStyle.fontColor = Color.BLACK;

		bg = new Img(new NinePatch(Assets.instance.ui.reg_ninepatch4,
				Color.GRAY));
		bg.setSize(width, height);
		icon = new Img(iconTexture);
		icon.setSize(3 * width / 4, 3 * height / 4);
		bgTitle = new Img(Assets.instance.ui.reg_ninepatch);
		bgTitle.setTouchable(Touchable.disabled);
		bgTitle.setColor(240 / 255f, 240 / 255f, 240 / 255f, 0.75f);
		bgTitle.setSize(width - 12, 20 + lbTitle.getHeight());
		this.addActor(bg);
		this.addActor(icon);
		this.addActor(bgTitle);
		this.addActor(lbTitle);
		icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(float x, float y) {
			}
		});
		valid();
	}

	public void valid() {
		icon.setPosition(bg.getWidth() / 2 - icon.getWidth() / 2,
				bg.getHeight() / 2 - icon.getHeight() / 2);
		bgTitle.setPosition(bg.getX() + 6, bg.getY() + 10);
		lbTitle.setPosition(bgTitle.getX() + 5,
				bgTitle.getX() + bgTitle.getHeight() / 2 - lbTitle.getHeight()
						/ 2);
	}
}