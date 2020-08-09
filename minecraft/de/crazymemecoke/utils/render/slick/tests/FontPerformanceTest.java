package de.crazymemecoke.utils.render.slick.tests;
	
import java.util.ArrayList;

import de.crazymemecoke.utils.render.slick.AngelCodeFont;
import de.crazymemecoke.utils.render.slick.AppGameContainer;
import de.crazymemecoke.utils.render.slick.BasicGame;
import de.crazymemecoke.utils.render.slick.Color;
import de.crazymemecoke.utils.render.slick.GameContainer;
import de.crazymemecoke.utils.render.slick.Graphics;
import de.crazymemecoke.utils.render.slick.Input;
import de.crazymemecoke.utils.render.slick.SlickException;

/**
 * A test of the font rendering capabilities
 *
 * @author kevin
 */
public class FontPerformanceTest extends BasicGame {
	/** The font we're going to use to render */
	private AngelCodeFont font;
	
	/** The test text */
	private String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin bibendum. Aliquam ac sapien a elit congue iaculis. Quisque et justo quis mi mattis euismod. Donec elementum, mi quis aliquet varius, nisi leo volutpat magna, quis ultricies eros augue at risus. Integer non magna at lorem sodales molestie. Integer diam nulla, ornare sit amet, mattis quis, euismod et, mauris. Proin eget tellus non nisl mattis laoreet. Nunc at nunc id elit pretium tempor. Duis vulputate, nibh eget rhoncus eleifend, tellus lectus sollicitudin mi, rhoncus tincidunt nisi massa vitae ipsum. Praesent tellus diam, luctus ut, eleifend nec, auctor et, orci. Praesent eu elit. Pellentesque ante orci, volutpat placerat, ornare eget, cursus sit amet, eros. Duis pede sapien, euismod a, volutpat pellentesque, convallis eu, mauris. Nunc eros. Ut eu risus et felis laoreet viverra. Curabitur a metus.";
	/** The text broken into lines */
	private ArrayList lines = new ArrayList();
	/** True if the text is visible */
	private boolean visible = true;
	
	/**
	 * Create a new test for font rendering
	 */
	public FontPerformanceTest() {
		super("Font Performance Test");
	}
	
	/**
	 * @see de.crazymemecoke.utils.render.slick.Game#init(de.crazymemecoke.utils.render.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		font = new AngelCodeFont("testdata/perffont.fnt","testdata/perffont.png");
		
		for (int j=0;j<2;j++) {
			int lineLen = 90;
			for (int i=0;i<text.length();i+=lineLen) {
				if (i+lineLen > text.length()) {
					lineLen = text.length() - i;
				}
				
				lines.add(text.substring(i, i+lineLen));	
			}
			lines.add("");
		}
	}

	/**
	 * @see de.crazymemecoke.utils.render.slick.BasicGame#render(de.crazymemecoke.utils.render.slick.GameContainer, de.crazymemecoke.utils.render.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		g.setFont(font);
		
		if (visible) {
			for (int i=0;i<lines.size();i++) {
				font.drawString(10, 50+(i*20),(String) lines.get(i),i > 10 ? Color.red : Color.green);
			}
		}
	}

	/**
	 * @see de.crazymemecoke.utils.render.slick.BasicGame#update(de.crazymemecoke.utils.render.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) throws SlickException {
	}
	
	/**
	 * @see de.crazymemecoke.utils.render.slick.BasicGame#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE) {
			System.exit(0);
		}
		if (key == Input.KEY_SPACE) {
			visible = !visible;
		}
	}
	
	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments passed in the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new FontPerformanceTest());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}