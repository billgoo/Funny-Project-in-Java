package listener;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import config.GameConfig;
import main.MainFrame;

import icon.StaticIcon;

import panel.MineSquareLabel;

public class MyMouseListener implements MouseListener {
	/**
	 * My implementation of MouseListener interface for mine square board
	 */
	MineSquareLabel[][] mineSquareLabels;
	MainFrame mainFrame;
	private boolean isDoubleButtonPress = false;

	public MyMouseListener(MineSquareLabel[][] mineSquareLabel, MainFrame mainFrame) {
		this.mineSquareLabels = mineSquareLabel;
		this.mainFrame = mainFrame;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		/**
		 * 重写鼠标按下动画效果和鼠标右键点击效果
		 * 附带按下时，笑脸状态改变效果
		 */
		MineSquareLabel mineSquareLabel = (MineSquareLabel) e.getSource();

		int row = mineSquareLabel.getRowX();
		int col = mineSquareLabel.getColY();

		if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK
				+ InputEvent.BUTTON3_DOWN_MASK) {
			// 鼠标左右键同时按下
			isDoubleButtonPress = true;
			doublePress(row, col);
		}
		else if (e.getModifiers() == InputEvent.BUTTON1_MASK
				&& !mineSquareLabel.isFlagTag()) {
			// 按动鼠标左键且改格子没有被标记为地雷旗帜
			if (!mineSquareLabel.isSweptTag()) {
				// 未扫过的格子则设置为按下效果的图标
				mineSquareLabel.setIcon(StaticIcon.plainPressedBombIcon);
			}
			// 点击雷区时笑脸图标改变效果
			mainFrame.getTopInfoJPanel().getLabelFace().setIcon(StaticIcon.clickBombPanelFaceIcon);
		}
		else if (e.getModifiers() == InputEvent.BUTTON3_MASK
				&& !mineSquareLabel.isSweptTag()) {
			// 点击鼠标右键，显示动画效果，更新格子状态为：问号/旗帜
			if (mineSquareLabel.getRightClickCount() == 0) {
				// 为空格子 -> 旗帜
				// 更新地雷格子
				mineSquareLabel.setIcon(StaticIcon.flagBombIcon);
				mineSquareLabel.setRightClickCount(1);
				mineSquareLabel.setFlagTag(true);
				// 更新顶端数据面板
				GameConfig.currentBombCount--;
				mainFrame.getTopInfoJPanel().setNumber(GameConfig.currentBombCount);
			}
			else if (mineSquareLabel.getRightClickCount() == 1) {
				// 旗帜格子 -> 问号
				mineSquareLabel.setIcon(StaticIcon.askBombIcon);
				mineSquareLabel.setRightClickCount(2);
				mineSquareLabel.setFlagTag(false);

				GameConfig.currentBombCount++;
				mainFrame.getTopInfoJPanel().setNumber(GameConfig.currentBombCount);
			}
			else {
				// 问号格子 -> 空格子
				if (GameConfig.isCheatMode && mineSquareLabel.isMineTag()) {
					mineSquareLabel.setIcon(StaticIcon.holeIcon);
				} else {
					mineSquareLabel.setIcon(StaticIcon.initBlankBombIcon);
				}
				mineSquareLabel.setRightClickCount(0);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		/**
		 * 重写鼠标按下后松开时的动画效果
		 * 附带按下时，笑脸状态改变效果
		 */
		MineSquareLabel mineSquareLabel = (MineSquareLabel) e.getSource();
		int row = mineSquareLabel.getRowX();
		int col = mineSquareLabel.getColY();

		if (isDoubleButtonPress) {
			// 鼠标左右键同时按下，一次性扫周围9个格子
			isDoubleButtonPress = false;
			if (!mineSquareLabel.isSweptTag() && !mineSquareLabel.isFlagTag()) {
				resumeIcon(row, col);
			}
			else {
				// 左右键同时按下空白格子时，如果周围旗帜数量等于地雷数量，自动向外扩展扫雷
				boolean isFlagEqualsMine = isFlagEqualsMine(row, col);
				if (isFlagEqualsMine) {
					doublePressExpand(row, col);
				} else {
					resumeIcon(row, col);
				}
			}
			mainFrame.getTopInfoJPanel().getLabelFace().setIcon(StaticIcon.smileFaceIcon);
		} else if (e.getModifiers() == InputEvent.BUTTON1_MASK
				&& !mineSquareLabel.isFlagTag()) {
			// 按动鼠标左键，且该格子不是旗帜
			if (!GameConfig.isStart) {
				GameConfig.isStart = true;
				mainFrame.getTimer().start();
			}

			if (mineSquareLabel.isMineTag()) {
				// 点到地雷，游戏结束
				reviewEndBombPanel(mineSquareLabel);
				mainFrame.getTopInfoJPanel().getLabelFace().setIcon(StaticIcon.failFaceIcon);
			} else {
				mainFrame.getTopInfoJPanel().getLabelFace().setIcon(StaticIcon.smileFaceIcon);
				dfsExpand(row, col);
			}
		}

		isWin();
	}

	private void doublePress(int i, int j) {
		/**
		 * 鼠标左右键同时按下，显示被按动的按钮的周围8个按钮被按下动画
		 * 如果周围按钮已经被扫过则那个按钮不使用动画效果
		 * 如果周围按钮有旗帜则那个按钮不使用动画效果
		 */
		for (int x = Math.max(0, i - 1); x <= Math.min(GameConfig.boardRow - 1,
				i + 1); x++) {
			for (int y = Math.max(0, j - 1); y <= Math.min(GameConfig.boardCol - 1,
					j + 1); y++) {
				if (!mineSquareLabels[x][y].isSweptTag() && !mineSquareLabels[x][y].isFlagTag()) {
					mineSquareLabels[x][y].setIcon(StaticIcon.plainPressedBombIcon);
				}
			}
		}
	}

	private void reviewEndBombPanel(MineSquareLabel mineSquareLabel) {
		/**
		 * 游戏结束，更新地雷面板信息
		 */
		for (MineSquareLabel[] mineSquareRows : mineSquareLabels) {
			for (MineSquareLabel mineSquare : mineSquareRows) {
				if (mineSquare.isMineTag()) {
					if (!mineSquare.isFlagTag()) {
						mineSquare.setIcon(StaticIcon.blackBombIcon);
					}
				} else {
					// 不是地雷却被标注为旗帜
					if (mineSquare.isFlagTag()) {
						mineSquare.setIcon(StaticIcon.errorFlagBombIcon);
					}
				}

				mineSquare.removeMouseListener(this);
			}
		}

		mineSquareLabel.setIcon(StaticIcon.errorClickBombIcon);
		mainFrame.getTimer().stop();
	}

	private void dfsExpand(int x, int y) {
		int count = mineSquareLabels[x][y].getNumMinesAround();

		if (!mineSquareLabels[x][y].isSweptTag() && !mineSquareLabels[x][y].isFlagTag()) {
			mineSquareLabels[x][y].setIcon(StaticIcon.num[count]);
			mineSquareLabels[x][y].setSweptTag(true);

			// 递归 DFS
			if (count == 0) {
				for (int i = Math.max(0, x - 1);
					 i <= Math.min(mineSquareLabels.length - 1, x + 1); i++) {
					for (int j = Math.max(0, y - 1);
						 j <= Math.min(mineSquareLabels[x].length - 1, y + 1); j++) {
						dfsExpand(i, j);
					}
				}
			}
		}
	}

	private void resumeIcon(int i, int j) {
		for (int x = Math.max(0, i - 1);
			 x <= Math.min(GameConfig.boardRow - 1, i + 1); x++) {
			for (int y = Math.max(0, j - 1);
				 y <= Math.min(GameConfig.boardCol - 1, j + 1); y++) {
				if (!mineSquareLabels[x][y].isFlagTag()
						&& !mineSquareLabels[x][y].isSweptTag()) {
					int rightClickCount = mineSquareLabels[x][y].getRightClickCount();
					if (rightClickCount == 2) {
						mineSquareLabels[x][y].setIcon(StaticIcon.askBombIcon);
					} else if (rightClickCount == 0) {
						if (GameConfig.isCheatMode && mineSquareLabels[x][y].isMineTag()) {
							mineSquareLabels[x][y].setIcon(StaticIcon.holeIcon);
						} else {
							mineSquareLabels[x][y].setIcon(StaticIcon.initBlankBombIcon);
						}
					}
				}
			}
		}

	}

	private void doublePressExpand(int i, int j) {
		// 左右键同时按下的扫雷区域扩展
		for (int x = Math.max(0, i - 1);
			 x <= Math.min(GameConfig.boardRow - 1, i + 1); x++) {
			for (int y = Math.max(0, j - 1);
				 y <= Math.min(GameConfig.boardCol - 1, j + 1); y++) {
				if (!mineSquareLabels[x][y].isFlagTag()) {
					if (mineSquareLabels[x][y].isMineTag()) {
						// 如果有地雷没有被旗帜标出，游戏结束
						reviewEndBombPanel(mineSquareLabels[x][y]);
					} else {
						// 否则继续扫雷
						dfsExpand(x, y);
					}
				}
			}
		}
	}

	private boolean isFlagEqualsMine(int i, int j) {
		int count = mineSquareLabels[i][j].getNumMinesAround();
		int numFlags = 0;

		for (int x = Math.max(0, i - 1);
			 x <= Math.min(GameConfig.boardRow - 1, i + 1); x++) {
			for (int y = Math.max(0, j - 1);
				 y <= Math.min(GameConfig.boardCol - 1, j + 1); y++) {
				if (mineSquareLabels[x][y].isFlagTag()) {
					numFlags++;
				}
			}
		}

		return count == numFlags;
	}

	private void isWin() {
		/**
		 * 判断游戏是否结束
		 */
		// 统计除旗帜外，已扫描格子数量
		int spareSquareCount = GameConfig.boardRow * GameConfig.boardCol
				- GameConfig.initBombCount;
		int safeSquareCount = 0;
		for (MineSquareLabel[] mineSquareLabel : mineSquareLabels) {
			for (MineSquareLabel mineSquare : mineSquareLabel) {
				if (mineSquare.isSweptTag()) {
					safeSquareCount++;
				}
			}
		}

		// 全部非地雷格子都被扫描，游戏胜利！
		// 则进行下一步
		boolean gameIsWin = spareSquareCount == safeSquareCount;
		if (gameIsWin) {
			for (MineSquareLabel[] mineSquareLabel : mineSquareLabels) {
				for (MineSquareLabel mineSquare : mineSquareLabel) {
					// 扫描后，未标注为旗帜的地雷格子全部标记为旗帜
					if (mineSquare.isMineTag() && !mineSquare.isFlagTag()) {
						mineSquare.setIcon(StaticIcon.flagBombIcon);
						mineSquare.setFlagTag(true);
					}

					// 移除按钮动作
					mineSquare.removeMouseListener(this);
				}
			}

			// 重置计分板
			mainFrame.getTopInfoJPanel().setNumber(0);
			mainFrame.getTimer().stop();
			mainFrame.getTopInfoJPanel().getLabelFace().setIcon(StaticIcon.winFaceIcon);

			// 根据等级设置弹窗
			int level = GameConfig.getLevel();
			if (level != 0) {
				if (level == 1) {
					JOptionPane.showMessageDialog(mainFrame, "扫雷成功！恭喜完成初级扫雷！");
				} else if (level == 2) {
					JOptionPane.showMessageDialog(mainFrame, "扫雷成功！恭喜完成中级扫雷！");
				} else if (level == 3) {
					JOptionPane.showMessageDialog(mainFrame, "扫雷成功！恭喜完成高级扫雷！");
				}
			}
		}
	}

}
