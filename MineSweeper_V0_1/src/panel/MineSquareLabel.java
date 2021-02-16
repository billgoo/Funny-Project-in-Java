package panel;

import javax.swing.JLabel;

public class MineSquareLabel extends JLabel {
	/**
	 * JLabel实现面板上每一个地雷小方块
	 */
	private static final long serialVersionUID = 0L;

	private int rowX;
	private int colY;

	private boolean mineTag;
	private boolean flagTag;
	private boolean sweptTag;	// 被扫描过的
	private int rightClickCount;	// 0=空，1=旗，2=问号

	private int numMinesAround;

	public MineSquareLabel(int x, int y) {
		// 坐标
		this.rowX = x;
		this.colY = y;
	}

	public boolean isMineTag() {
		return mineTag;
	}

	public void setMineTag(boolean mineTag) {
		this.mineTag = mineTag;
	}

	public boolean isFlagTag() {
		return flagTag;
	}

	public void setFlagTag(boolean flagTag) {
		this.flagTag = flagTag;
	}

	public boolean isSweptTag() {
		return sweptTag;
	}

	public void setSweptTag(boolean sweptTag) {
		this.sweptTag = sweptTag;
	}

	public int getRightClickCount() {
		return rightClickCount;
	}

	public void setRightClickCount(int rightClickCount) {
		this.rightClickCount = rightClickCount;
	}

	public int getNumMinesAround() {
		return numMinesAround;
	}

	public void setNumMinesAround(int numMinesAround) {
		this.numMinesAround = numMinesAround;
	}

	public int getRowX() {
		return rowX;
	}

	public void setRowX(int rowX) {
		this.rowX = rowX;
	}

	public int getColY() {
		return colY;
	}

	public void setColY(int colY) {
		this.colY = colY;
	}
}
