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
	 * ����Ц���ͼ�����ʱ�� Panel
	 */
	private static final long serialVersionUID = 0L;

	MainFrame mainFrame;

	private final JLabel labelFace = new JLabel();

	// ���׼���
	private final JLabel labelCountOne = new JLabel();
	private final JLabel labelCountTen = new JLabel();
	private final JLabel labelCountHundred = new JLabel();

	// ʱ�����
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

		// ���õ��׼�����壬���ﳬ��100Ҫע��
		Icon icon0 = new ImageIcon("./image/d"
				+ GameConfig.initBombCount / 100 + ".gif");
		Icon icon1 = new ImageIcon("./image/d"
				+ (GameConfig.initBombCount / 10) % 10 + ".gif");
		Icon icon2 = new ImageIcon("./image/d"
				+ GameConfig.initBombCount % 10 + ".gif");
		labelCountOne.setIcon(icon2);
		labelCountTen.setIcon(icon1);
		labelCountHundred.setIcon(icon0);

		// ����Ц�����
		labelFace.setIcon(StaticIcon.smileFaceIcon);

		// ���ü�ʱ���
		Icon iconTimeInit = new ImageIcon("./image/d0.gif");
		labelTimeTen.setIcon(iconTimeInit);
		labelTimeOne.setIcon(iconTimeInit);
		labelTimeHundred.setIcon(iconTimeInit);

		// �������������ӵ����������
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

		// ���������������ɵ������߿򣬼�����������ڱ߿�
		Border borderLow = BorderFactory.createLoweredBevelBorder();
		Border borderEmpty = BorderFactory.createEmptyBorder(2, 2, 2, 2);
		Border borderCom1 = BorderFactory.createCompoundBorder(borderLow, borderEmpty);
		panel.setBorder(borderCom1);

		panel.setBackground(Color.LIGHT_GRAY);

		// ���ð�ť�������
		labelFace.addMouseListener(new FaceLabelListener());

		// ���ö��������
		this.add(panel);
		this.setBackground(Color.LIGHT_GRAY);

		// ���ö��������߿�
		Border borderEmpty2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		this.setBorder(borderEmpty2);
	}

	public JLabel getLabelFace() {
		return labelFace;
	}

	public void setTime(int count) {
		// ���ö������ʱ��
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
		 * ʵ������������Ц��ͼ��Ķ����͹���
		 * ������mousePressed & mouseReleased
		 * ���ܣ�mouseClicked
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			// ����������ʱ��Ц���Ķ���
			if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
				labelFace.setIcon(StaticIcon.pressedSmileFaceIcon);
				mainFrame.getTimer().stop();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
				// ����Ц����Ϸ�ؿ�
				mainFrame.reStartGame();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// ���������º��ɿ�ʱִ�еĶ���
			if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
				mainFrame.getTimer().start();
				labelFace.setIcon(StaticIcon.smileFaceIcon);
			}
		}
	}

}
