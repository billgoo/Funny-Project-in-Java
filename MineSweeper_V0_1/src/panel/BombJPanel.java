package panel;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import config.GameConfig;
import icon.StaticIcon;
import listener.MyMouseListener;
import main.MainFrame;
import utils.LayRandomMines;

public class BombJPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;

	MineSquareLabel[][] labels = new MineSquareLabel[GameConfig.boardRow][GameConfig.boardCol];
	private MyMouseListener listener;
	private final MainFrame mainFrame;

	public BombJPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.setLayout(new GridLayout(GameConfig.boardRow, GameConfig.boardCol));

		init();
	}

	private void init() {
		// ��ʼ��������
		listener = new MyMouseListener(labels, mainFrame);

		// ��ʼ��UI
		// ��JPanel�����JLabel
		for (int i = 0; i < labels.length; i++) {
			for (int j = 0; j < labels[i].length; j++) {
				labels[i][j] = new MineSquareLabel(i, j);
				labels[i][j].setIcon(StaticIcon.initBlankBombIcon);
				labels[i][j].addMouseListener(listener);
				this.add(labels[i][j]);
			}
		}

		Border borderLow = BorderFactory.createLoweredBevelBorder();
		Border borderEmpty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border borderCompound1 = BorderFactory.createCompoundBorder(borderEmpty,
				borderLow);
		this.setBorder(borderCompound1);

		this.setBackground(Color.LIGHT_GRAY);

		// Ԥ�ȳ�ʼ������
		LayRandomMines.lay(labels);
	}

	public void changeCheatBombIcon(boolean isCheatMode) {
		if (isCheatMode) {
			// ����ģʽ�ǵ��׵ĸ���δ������ģ�����Ϊ���ͼ����
			for (MineSquareLabel[] labelRow : labels) {
				for (MineSquareLabel label : labelRow) {
					if (label.isMineTag() && !label.isSweptTag() && !label.isFlagTag()) {
						// δ����ע�ĵ��׸���
						label.setIcon(StaticIcon.holeIcon);
					} else if (!label.isMineTag() && label.isFlagTag()) {
						// �����Ϊ���ĵķǵ��׸��ӣ����Ϊ���ͼ��
						label.setIcon(StaticIcon.errorFlagBombIcon);
						// ����Ϊ�ո���
						label.setRightClickCount(0);
						label.setFlagTag(false);
					}
					// inner if-else
				}
			}
			// for end
		}
		else {
			// ����ģʽ�л�Ϊ������ģʽ
			for (MineSquareLabel[] labelRow : labels) {
				for (MineSquareLabel label : labelRow) {
					if (label.getIcon() == StaticIcon.holeIcon) {
						label.setIcon(StaticIcon.initBlankBombIcon);
					} else if (label.getIcon() == StaticIcon.errorFlagBombIcon) {
						// �����Ϊ���ĵķǵ��׸��ӣ����Ϊ���ͼ�������Ϊ������ģʽ�µ��ʺ�
						label.setIcon(StaticIcon.askBombIcon);
						label.setRightClickCount(2);
						label.setFlagTag(false);
					}
					// inner if-else
				}
			}
			// for end
		}
	}

}
