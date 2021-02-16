package panel;

import config.GameConfig;
import dialog.ContactInfoDialog;
import dialog.CustomGameJDialog;
import main.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class BombJMenuBar extends JMenuBar {
	/**
	 * 菜单栏
	 */
	private static final long serialVersionUID = 0L;

	MainFrame mainFrame;

	// define Menu
	JMenu menuGame = new JMenu("游戏(G)");
	JMenu menuHelp = new JMenu("帮助(H)");

	// define MenuItem
	// item for Game
	JMenuItem menuItemStart = new JMenuItem("开局(N)", KeyEvent.VK_N);

	JMenuItem menuItemBeginner = new JMenuItem("初级(B)", KeyEvent.VK_B);
	JMenuItem menuItemIntermediate = new JMenuItem("中级(I)", KeyEvent.VK_I);
	JMenuItem menuItemAdvance = new JMenuItem("高级(A)", KeyEvent.VK_A);

	JMenuItem menuItemCustom = new JMenuItem("自定义(C)", KeyEvent.VK_C);
	JMenuItem menuItemExit = new JMenuItem("退出(E)", KeyEvent.VK_E);

	// item for Help
	JMenuItem menuItemCheat = new JMenuItem("开启游戏提示");
	JMenuItem menuItemAbout = new JMenuItem("关于扫雷");

	public BombJMenuBar(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		// 根据全局配置更新菜单栏文字
		if (GameConfig.isCheatMode) {
			// 已经处于开启作弊模式，则文字为选择是否关闭作弊模式
			menuItemCheat = new JMenuItem("关闭游戏提示");
		} else {
			menuItemCheat = new JMenuItem("开启游戏提示");
		}

		init();
	}

	private void init() {
		// 设置快捷键 alt + *
		menuGame.setMnemonic(KeyEvent.VK_G);
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuItemStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK));
		menuItemCustom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK));

		// 菜单项加入到菜单
		// Game
		menuGame.add(menuItemStart);
		// 菜单加入分割线
		menuGame.addSeparator();
		menuGame.add(menuItemBeginner);
		menuGame.add(menuItemIntermediate);
		menuGame.add(menuItemAdvance);
		menuGame.addSeparator();
		menuGame.add(menuItemCustom);
		menuGame.addSeparator();
		menuGame.add(menuItemExit);

		// Help
		menuHelp.add(menuItemCheat);
		menuHelp.addSeparator();
		menuHelp.add(menuItemAbout);

		// 添加菜单命令
		// Game
		// 重新开局
		menuItemStart.addActionListener(e -> mainFrame.reStartGame());
		// 重开初中高级自动设置
		menuItemBeginner.addActionListener(e -> {
			GameConfig.boardRow = 10;
			GameConfig.boardCol = 10;
			GameConfig.initBombCount = 10;
			mainFrame.reStartGame();
		});
		menuItemIntermediate.addActionListener(e -> {
			GameConfig.boardRow = 16;
			GameConfig.boardCol = 16;
			GameConfig.initBombCount = 40;
			mainFrame.reStartGame();
		});
		menuItemAdvance.addActionListener(e -> {
			GameConfig.boardRow = 25;
			GameConfig.boardCol = 30;
			GameConfig.initBombCount = 100;
			mainFrame.reStartGame();
		});
		// 自定义级别重开，弹窗设置config参数
		menuItemCustom.addActionListener(e -> new CustomGameJDialog(mainFrame));
		// 退出
		menuItemExit.addActionListener(e -> System.exit(JFrame.EXIT_ON_CLOSE));

		// Help
		menuItemCheat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!GameConfig.isCheatMode) {
					// 当前非作弊模式，点击开启作弊模式
					GameConfig.isCheatMode = true;
					menuItemCheat.setText("关闭游戏提示");
				} else {
					// 当前作弊模式，点击关闭作弊模式
					GameConfig.isCheatMode = false;
					menuItemCheat.setText("开启游戏提示");
				}
				mainFrame.getBombJPanel().changeCheatBombIcon(GameConfig.isCheatMode);
			}
		});
		menuItemAbout.addActionListener(e -> new ContactInfoDialog(mainFrame));

		// 菜单加入到菜单条
		this.add(menuGame);
		this.add(menuHelp);
	}
}
