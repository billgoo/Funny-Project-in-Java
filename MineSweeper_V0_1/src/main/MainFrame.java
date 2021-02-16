package main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import config.GameConfig;
import panel.BombJMenuBar;
import panel.BombJPanel;
import icon.StaticIcon;
import panel.TopInfoJPanel;
import timer.TimerListener;

public class MainFrame extends JFrame {
	/**
	 * MainFrame
	 */
	private static final long serialVersionUID = 0L;

	private BombJMenuBar menuBar = new BombJMenuBar(this);
	private TopInfoJPanel topInfoJPanel = new TopInfoJPanel(this);
	private BombJPanel bombJPanel = new BombJPanel(this);

	// 计时器每隔1秒进行一次动作
	private final TimerListener timerListener = new TimerListener(this);
	private final Timer timer = new Timer(1000, timerListener);

	public MainFrame() {
		// 依次添加菜单栏，顶端栏和地雷面板
		init();

		this.setIconImage(StaticIcon.mainIcon.getImage());
		this.setTitle("扫雷");
		this.setSize(new Dimension(220, 300));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	private void init() {
		// 依次添加菜单栏，顶端栏和地雷面板
		this.setJMenuBar(menuBar);
		this.add(topInfoJPanel, BorderLayout.NORTH);
		this.add(bombJPanel);
	}

	public void reStartGame() {
		// 重置原有面板
		this.remove(menuBar);
		this.remove(topInfoJPanel);
		this.remove(bombJPanel);

		// 游戏开始状态重置为未初始点击过
		GameConfig.isStart = false;

		// 其他config初始化
		GameConfig.currentBombCount = GameConfig.initBombCount;
		GameConfig.timerCount = 0;

		// 重开则重新初始化整个面板
		menuBar = new BombJMenuBar(this);
		topInfoJPanel = new TopInfoJPanel(this);
		bombJPanel = new BombJPanel(this);
		this.add(topInfoJPanel, BorderLayout.NORTH);
		this.add(bombJPanel);

		// 调整此窗口的大小，以适合其子组件的首选大小和布局
		this.pack();
		// 容器本身和它所包含的组件布局及其大小发生变化，调用该方法使这种变化在界面上被反映出来
		this.validate();

		getTimer().stop();
	}

	public TopInfoJPanel getTopInfoJPanel() {
		return topInfoJPanel;
	}

	public BombJPanel getBombJPanel() {
		return bombJPanel;
	}

	public Timer getTimer() {
		// 封装swing Timer类对象的接口
		return timer;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 设置窗体风格
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		// 初始化窗体
		new MainFrame();
	}

}
