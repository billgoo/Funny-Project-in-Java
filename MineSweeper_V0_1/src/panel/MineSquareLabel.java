package panel;

import javax.swing.JLabel;

public class MineSquareLabel extends JLabel {
	/**
	 * JLabelʵ�������ÿһ������С����
	 */
	private static final long serialVersionUID = 0L;

	private int rowX;
	private int colY;

	private boolean mineTag;
	private boolean flagTag;
	private boolean sweptTag;	// ��ɨ�����
	private int rightClickCount;	// 0=�գ�1=�죬2=�ʺ�

	private int numMinesAround;

	public MineSquareLabel(int x, int y) {
		// ����
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
