package listener;

import config.GameConfig;
import dialog.CustomGameJDialog;
import main.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomGameDialogButtonListener implements ActionListener {
	/**
	 * ����Զ�����Ϸ�����öԻ����ϵİ�ť����
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
					customGameJDialog.getJLabelMessage().setText("�����������ΧӦ��10-30֮��");
					return;
				}
			} catch (Exception e1) {
				customGameJDialog.getJLabelMessage().setText("����Ӧ��Ϊ�����ҷ�ΧӦ��10-30֮��");
				return;
			}


			String colText = customGameJDialog.getJTextFieldWide().getText();
			int col = 10;
			try {
				col = Integer.parseInt(colText);
				if (col < 10 || col > 30) {
					customGameJDialog.getJLabelMessage().setText("�����������ΧӦ��10-30֮��");
					return;
				}
			} catch (Exception e2) {
				customGameJDialog.getJLabelMessage().setText("����Ӧ��Ϊ�����ҷ�ΧӦ��10-30֮��");
				return;
			}

			String bombText = customGameJDialog.getJTextFieldBomb().getText();
			int bomb = 1;
			try {
				bomb = Integer.parseInt(bombText);
				if (bomb < 1) {
					customGameJDialog.getJLabelMessage().setText("������������1��");
					return;
				} else {
					if (bomb > GameConfig.boardRow * GameConfig.boardCol * 4 / 5) {
						customGameJDialog.getJLabelMessage().setText("�������ܶ��ڸ������������֮��");
						return;
					}
				}
			} catch (Exception e3) {
				customGameJDialog.getJLabelMessage().setText("����Ӧ��Ϊ����");
				return;
			}

			// ������Ϸ����
			GameConfig.boardRow = row;
			GameConfig.boardCol = col;
			GameConfig.initBombCount = bomb;

			customGameJDialog.dispose();
			mainFrame.reStartGame();
		}
	}
}
