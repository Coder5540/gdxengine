package utils.elements;

import utils.factory.FontFactory.fontType;
import utils.listener.OnCompleteListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.coder5560.game.assets.Assets;

public class Button extends Group {
	Label	lbText;
	Img		bg;

	public Button(float width, float height) {
		super();
		setSize(width, height);
		setOrigin(Align.center);
	}

	public Button buildText(String text) {
		if (lbText == null) {
			LabelStyle style = new LabelStyle(
					Assets.instance.fontFactory.getFont(20, fontType.Regular),
					Color.BLUE);
			lbText = new Label(text, style);
			lbText.setOrigin(Align.center);
			lbText.setWrap(true);
			lbText.setWidth(getWidth() - 10);
		} else {
			lbText.setText(text);
		}
		lbText.setTouchable(Touchable.disabled);
		lbText.setAlignment(Align.center);
		return this;
	}

	public Button buildBackground(TextureRegion region) {
		bg = new Img(region);
		return this;
	}

	public Button buildColor(Color color) {
		bg.setColor(color);
		return this;
	}

	public Button buidPosition(float x, float y, int align) {
		this.setPosition(x, y, align);
		bg.setSize(getWidth(), getHeight());
		lbText.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
		return this;
	}

	public Button buidPosition() {
		bg.setSize(getWidth(), getHeight());
		lbText.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
		return this;
	}

	public Button buidToContainer() {
		addActor(bg);
		addActor(lbText);
		return this;
	}

	public Button buildOnClick(final OnCompleteListener onCompleteListener) {
		bg.clearListeners();
		bg.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				addAction(Actions.sequence(Actions.scaleTo(1.2f, 1.2f, .05f),
						Actions.scaleTo(1f, 1f, .1f, Interpolation.swingOut),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								if (onCompleteListener != null)
									onCompleteListener.onComplete("");
							}
						})));
			}
		});
		return this;
	}

}