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
		 * ��д��갴�¶���Ч��������Ҽ����Ч��
		 * ��������ʱ��Ц��״̬�ı�Ч��
		 */
		MineSquareLabel mineSquareLabel = (MineSquareLabel) e.getSource();

		int row = mineSquareLabel.getRowX();
		int col = mineSquareLabel.getColY();

		if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK
				+ InputEvent.BUTTON3_DOWN_MASK) {
			// ������Ҽ�ͬʱ����
			isDoubleButtonPress = true;
			doublePress(row, col);
		}
		else if (e.getModifiers() == InputEvent.BUTTON1_MASK
				&& !mineSquareLabel.isFlagTag()) {
			// �����������Ҹĸ���û�б����Ϊ��������
			if (!mineSquareLabel.isSweptTag()) {
				// δɨ���ĸ���������Ϊ����Ч����ͼ��
				mineSquareLabel.setIcon(StaticIcon.plainPressedBombIcon);
			}
			// �������ʱЦ��ͼ��ı�Ч��
			mainFrame.getTopInfoJPanel().getLabelFace().setIcon(StaticIcon.clickBombPanelFaceIcon);
		}
		else if (e.getModifiers() == InputEvent.BUTTON3_MASK
				&& !mineSquareLabel.isSweptTag()) {
			// �������Ҽ�����ʾ����Ч�������¸���״̬Ϊ���ʺ�/����
			if (mineSquareLabel.getRightClickCount() == 0) {
				// Ϊ�ո��� -> ����
				// ���µ��׸���
				mineSquareLabel.setIcon(StaticIcon.flagBombIcon);
				mineSquareLabel.setRightClickCount(1);
				mineSquareLabel.setFlagTag(true);
				// ���¶����������
				GameConfig.currentBombCount--;
				mainFrame.getTopInfoJPanel().setNumber(GameConfig.currentBombCount);
			}
			else if (mineSquareLabel.getRightClickCount() == 1) {
				// ���ĸ��� -> �ʺ�
				mineSquareLabel.setIcon(StaticIcon.askBombIcon);
				mineSquareLabel.setRightClickCount(2);
				mineSquareLabel.setFlagTag(false);

				GameConfig.currentBombCount++;
				mainFrame.getTopInfoJPanel().setNumber(GameConfig.currentBombCount);
			}
			else {
				// �ʺŸ��� -> �ո���
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
		 * ��д��갴�º��ɿ�ʱ�Ķ���Ч��
		 * ��������ʱ��Ц��״̬�ı�Ч��
		 */
		MineSquareLabel mineSquareLabel = (MineSquareLabel) e.getSource();
		int row = mineSquareLabel.getRowX();
		int col = mineSquareLabel.getColY();

		if (isDoubleButtonPress) {
			// ������Ҽ�ͬʱ���£�һ����ɨ��Χ9������
			isDoubleButtonPress = false;
			if (!mineSquareLabel.isSweptTag() && !mineSquareLabel.isFlagTag()) {
				resumeIcon(row, col);
			}
			else {
				// ���Ҽ�ͬʱ���¿հ׸���ʱ�������Χ�����������ڵ����������Զ�������չɨ��
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
			// �������������Ҹø��Ӳ�������
			if (!GameConfig.isStart) {
				GameConfig.isStart = true;
				mainFrame.getTimer().start();
			}

			if (mineSquareLabel.isMineTag()) {
				// �㵽���ף���Ϸ����
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
		 * ������Ҽ�ͬʱ���£���ʾ�������İ�ť����Χ8����ť�����¶���
		 * �����Χ��ť�Ѿ���ɨ�����Ǹ���ť��ʹ�ö���Ч��
		 * �����Χ��ť���������Ǹ���ť��ʹ�ö���Ч��
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
		 * ��Ϸ���������µ��������Ϣ
		 */
		for (MineSquareLabel[] mineSquareRows : mineSquareLabels) {
			for (MineSquareLabel mineSquare : mineSquareRows) {
				if (mineSquare.isMineTag()) {
					if (!mineSquare.isFlagTag()) {
						mineSquare.setIcon(StaticIcon.blackBombIcon);
					}
				} else {
					// ���ǵ���ȴ����עΪ����
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

			// �ݹ� DFS
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
		// ���Ҽ�ͬʱ���µ�ɨ��������չ
		for (int x = Math.max(0, i - 1);
			 x <= Math.min(GameConfig.boardRow - 1, i + 1); x++) {
			for (int y = Math.max(0, j - 1);
				 y <= Math.min(GameConfig.boardCol - 1, j + 1); y++) {
				if (!mineSquareLabels[x][y].isFlagTag()) {
					if (mineSquareLabels[x][y].isMineTag()) {
						// ����е���û�б����ı������Ϸ����
						reviewEndBombPanel(mineSquareLabels[x][y]);
					} else {
						// �������ɨ��
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
		 * �ж���Ϸ�Ƿ����
		 */
		// ͳ�Ƴ������⣬��ɨ���������
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

		// ȫ���ǵ��׸��Ӷ���ɨ�裬��Ϸʤ����
		// �������һ��
		boolean gameIsWin = spareSquareCount == safeSquareCount;
		if (gameIsWin) {
			for (MineSquareLabel[] mineSquareLabel : mineSquareLabels) {
				for (MineSquareLabel mineSquare : mineSquareLabel) {
					// ɨ���δ��עΪ���ĵĵ��׸���ȫ�����Ϊ����
					if (mineSquare.isMineTag() && !mineSquare.isFlagTag()) {
						mineSquare.setIcon(StaticIcon.flagBombIcon);
						mineSquare.setFlagTag(true);
					}

					// �Ƴ���ť����
					mineSquare.removeMouseListener(this);
				}
			}

			// ���üƷְ�
			mainFrame.getTopInfoJPanel().setNumber(0);
			mainFrame.getTimer().stop();
			mainFrame.getTopInfoJPanel().getLabelFace().setIcon(StaticIcon.winFaceIcon);

			// ���ݵȼ����õ���
			int level = GameConfig.getLevel();
			if (level != 0) {
				if (level == 1) {
					JOptionPane.showMessageDialog(mainFrame, "ɨ�׳ɹ�����ϲ��ɳ���ɨ�ף�");
				} else if (level == 2) {
					JOptionPane.showMessageDialog(mainFrame, "ɨ�׳ɹ�����ϲ����м�ɨ�ף�");
				} else if (level == 3) {
					JOptionPane.showMessageDialog(mainFrame, "ɨ�׳ɹ�����ϲ��ɸ߼�ɨ�ף�");
				}
			}
		}
	}

}
