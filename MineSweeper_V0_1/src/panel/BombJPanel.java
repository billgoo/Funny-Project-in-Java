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
		// 初始化监听器
		listener = new MyMouseListener(labels, mainFrame);

		// 初始化UI
		// 在JPanel上添加JLabel
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

		// 预先初始化地雷
		LayRandomMines.lay(labels);
	}

	public void changeCheatBombIcon(boolean isCheatMode) {
		if (isCheatMode) {
			// 作弊模式是地雷的格子未被标出的，设置为点的图标标记
			for (MineSquareLabel[] labelRow : labels) {
				for (MineSquareLabel label : labelRow) {
					if (label.isMineTag() && !label.isSweptTag() && !label.isFlagTag()) {
						// 未被标注的地雷格子
						label.setIcon(StaticIcon.holeIcon);
					} else if (!label.isMineTag() && label.isFlagTag()) {
						// 被误标为旗帜的非地雷格子，标记为错标图标
						label.setIcon(StaticIcon.errorFlagBombIcon);
						// 重置为空格子
						label.setRightClickCount(0);
						label.setFlagTag(false);
					}
					// inner if-else
				}
			}
			// for end
		}
		else {
			// 作弊模式切换为非作弊模式
			for (MineSquareLabel[] labelRow : labels) {
				for (MineSquareLabel label : labelRow) {
					if (label.getIcon() == StaticIcon.holeIcon) {
						label.setIcon(StaticIcon.initBlankBombIcon);
					} else if (label.getIcon() == StaticIcon.errorFlagBombIcon) {
						// 被误标为旗帜的非地雷格子，标记为错标图标后，重置为非作弊模式下的问号
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
