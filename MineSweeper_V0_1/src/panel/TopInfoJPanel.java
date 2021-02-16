package panel;

import config.GameConfig;
import icon.StaticIcon;
import main.MainFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TopInfoJPanel extends JPanel {
	/**
	 * 顶端笑脸和计数计时的 Panel
	 */
	private static final long serialVersionUID = 0L;

	MainFrame mainFrame;

	private final JLabel labelFace = new JLabel();

	// 地雷计数
	private final JLabel labelCountOne = new JLabel();
	private final JLabel labelCountTen = new JLabel();
	private final JLabel labelCountHundred = new JLabel();

	// 时间计数
	private final JLabel labelTimeOne = new JLabel();
	private final JLabel labelTimeTen = new JLabel();
	private final JLabel labelTimeHundred = new JLabel();

	public TopInfoJPanel(MainFrame frame) {
		this.mainFrame = frame;
		this.setLayout(new BorderLayout());

		init();
	}

	private void init() {
		JPanel panel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.LINE_AXIS);
		panel.setLayout(boxLayout);

		// 设置地雷计数面板，这里超过100要注意
		Icon icon0 = new ImageIcon("./image/d"
				+ GameConfig.initBombCount / 100 + ".gif");
		Icon icon1 = new ImageIcon("./image/d"
				+ (GameConfig.initBombCount / 10) % 10 + ".gif");
		Icon icon2 = new ImageIcon("./image/d"
				+ GameConfig.initBombCount % 10 + ".gif");
		labelCountOne.setIcon(icon2);
		labelCountTen.setIcon(icon1);
		labelCountHundred.setIcon(icon0);

		// 设置笑脸面板
		labelFace.setIcon(StaticIcon.smileFaceIcon);

		// 设置计时面板
		Icon iconTimeInit = new ImageIcon("./image/d0.gif");
		labelTimeTen.setIcon(iconTimeInit);
		labelTimeOne.setIcon(iconTimeInit);
		labelTimeHundred.setIcon(iconTimeInit);

		// 将三个分面板添加到顶端面板上
		panel.add(Box.createHorizontalStrut(2));
		panel.add(labelCountHundred);
		panel.add(labelCountTen);
		panel.add(labelCountOne);
		panel.add(Box.createHorizontalGlue());
		panel.add(labelFace);
		panel.add(Box.createHorizontalGlue());
		panel.add(labelTimeHundred);
		panel.add(labelTimeTen);
		panel.add(labelTimeOne);
		panel.add(Box.createHorizontalStrut(2));

		// 创建三个分面板组成的面板外边框，即：顶端面板内边框
		Border borderLow = BorderFactory.createLoweredBevelBorder();
		Border borderEmpty = BorderFactory.createEmptyBorder(2, 2, 2, 2);
		Border borderCom1 = BorderFactory.createCompoundBorder(borderLow, borderEmpty);
		panel.setBorder(borderCom1);

		panel.setBackground(Color.LIGHT_GRAY);

		// 设置按钮点击动作
		labelFace.addMouseListener(new FaceLabelListener());

		// 设置顶端总面板
		this.add(panel);
		this.setBackground(Color.LIGHT_GRAY);

		// 设置顶端面板外边框
		Border borderEmpty2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		this.setBorder(borderEmpty2);
	}

	public JLabel getLabelFace() {
		return labelFace;
	}

	public void setTime(int count) {
		// 设置顶端面板时间
		int one = count % 10;
		int ten = count / 10 % 10;
		int hundred = count / 100;

		labelTimeOne.setIcon(StaticIcon.time[one]);
		labelTimeTen.setIcon(StaticIcon.time[ten]);
		labelTimeHundred.setIcon(StaticIcon.time[hundred]);
	}

	public void setNumber(int count) {
		int one, ten, hundred;
		if (count < 0) {
			one = 0;
			ten = 1;
			hundred = 0;
		} else {
			one = Math.abs(count) % 10;
			ten = Math.abs(count) / 10 % 10;
			hundred = Math.abs(count) / 100;
		}

		labelCountOne.setIcon(StaticIcon.time[one]);
		labelCountTen.setIcon(StaticIcon.time[ten]);
		labelCountHundred.setIcon(StaticIcon.time[hundred]);
	}

	// implement mouse action
	public class FaceLabelListener extends MouseAdapter {
		/**
		 * 实现鼠标左键单击笑脸图标的动画和功能
		 * 动画：mousePressed & mouseReleased
		 * 功能：mouseClicked
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			// 鼠标左键按下时，笑脸的动画
			if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
				labelFace.setIcon(StaticIcon.pressedSmileFaceIcon);
				mainFrame.getTimer().stop();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
				// 单击笑脸游戏重开
				mainFrame.reStartGame();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// 鼠标左键按下后松开时执行的动作
			if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
				mainFrame.getTimer().start();
				labelFace.setIcon(StaticIcon.smileFaceIcon);
			}
		}
	}

}
