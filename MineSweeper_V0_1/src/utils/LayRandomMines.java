package utils;

import java.util.Random;

import config.GameConfig;
import icon.StaticIcon;
import panel.MineSquareLabel;

public class LayRandomMines {

	public static void lay(MineSquareLabel[][] mineSquareLabels) {
		int count = 0;
		Random random = new Random();

		while (count < GameConfig.initBombCount) {
			int x = random.nextInt(GameConfig.boardRow);
			int y = random.nextInt(GameConfig.boardCol);

			if (!mineSquareLabels[x][y].isMineTag()) {
				mineSquareLabels[x][y].setMineTag(true);
				mineSquareLabels[x][y].setNumMinesAround(-1);	// 地雷设置周围雷数为-1

				if (GameConfig.isCheatMode) {
					mineSquareLabels[x][y].setIcon(StaticIcon.holeIcon);
				}

				count++;
			}
		}

		initComputeMinesAround(mineSquareLabels);
	}

	private static void initComputeMinesAround(MineSquareLabel[][] mineSquareLabels) {

		for (int i = 0; i < mineSquareLabels.length; i++) {
			for (int j = 0; j < mineSquareLabels[i].length; j++) {
				if (!mineSquareLabels[i][j].isMineTag()) {
					int count = 0;

					for (int x = Math.max(0, i - 1);
						 x <= Math.min(GameConfig.boardRow - 1, i + 1); x++) {
						for (int y = Math.max(0, j - 1);
							 y <= Math.min(GameConfig.boardCol - 1, j + 1); y++) {
							if (mineSquareLabels[x][y].isMineTag()) {
								count++;
							}
						}
					}

					mineSquareLabels[i][j].setNumMinesAround(count);
				}
			}
		}
	}

}
