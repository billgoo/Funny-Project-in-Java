package timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import config.GameConfig;

import main.MainFrame;

public class TimerListener implements ActionListener {
	MainFrame mainFrame;

	public TimerListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GameConfig.timerCount++;
		if (GameConfig.timerCount > 999) {
			GameConfig.timerCount = 999;
		}
		mainFrame.getTopInfoJPanel().setTime(GameConfig.timerCount);
	}

}
