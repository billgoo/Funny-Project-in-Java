package listener;

import config.GameConfig;
import dialog.CustomGameJDialog;
import main.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomGameDialogButtonListener implements ActionListener {
	/**
	 * 点击自定义游戏的设置对话框上的按钮动作
	 */
	CustomGameJDialog customGameJDialog;

	MainFrame mainFrame;

	public CustomGameDialogButtonListener(CustomGameJDialog customGameJDialog,
										  MainFrame mainFrame) {
		this.customGameJDialog = customGameJDialog;
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == customGameJDialog.getButtonCancer()) {
			customGameJDialog.dispose();
			mainFrame.reStartGame();
		}
		else if (e.getSource() == customGameJDialog.getButtonOK()) {
			String rowText = customGameJDialog.getJTextFieldHigh().getText();
			int row = 10;
			try {
				row = Integer.parseInt(rowText);
				if (row < 10 || row > 30) {
					customGameJDialog.getJLabelMessage().setText("输入的行数范围应在10-30之间");
					return;
				}
			} catch (Exception e1) {
				customGameJDialog.getJLabelMessage().setText("行数应该为数字且范围应在10-30之间");
				return;
			}


			String colText = customGameJDialog.getJTextFieldWide().getText();
			int col = 10;
			try {
				col = Integer.parseInt(colText);
				if (col < 10 || col > 30) {
					customGameJDialog.getJLabelMessage().setText("输入的列数范围应在10-30之间");
					return;
				}
			} catch (Exception e2) {
				customGameJDialog.getJLabelMessage().setText("列数应该为数字且范围应在10-30之间");
				return;
			}

			String bombText = customGameJDialog.getJTextFieldBomb().getText();
			int bomb = 1;
			try {
				bomb = Integer.parseInt(bombText);
				if (bomb < 1) {
					customGameJDialog.getJLabelMessage().setText("雷数不能少于1个");
					return;
				} else {
					if (bomb > GameConfig.boardRow * GameConfig.boardCol * 4 / 5) {
						customGameJDialog.getJLabelMessage().setText("雷数不能多于格子数量的五分之四");
						return;
					}
				}
			} catch (Exception e3) {
				customGameJDialog.getJLabelMessage().setText("雷数应该为数字");
				return;
			}

			// 重设游戏参数
			GameConfig.boardRow = row;
			GameConfig.boardCol = col;
			GameConfig.initBombCount = bomb;

			customGameJDialog.dispose();
			mainFrame.reStartGame();
		}
	}
}
