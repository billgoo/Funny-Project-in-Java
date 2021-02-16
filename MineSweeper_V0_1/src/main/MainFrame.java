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

	// ��ʱ��ÿ��1�����һ�ζ���
	private final TimerListener timerListener = new TimerListener(this);
	private final Timer timer = new Timer(1000, timerListener);

	public MainFrame() {
		// ������Ӳ˵������������͵������
		init();

		this.setIconImage(StaticIcon.mainIcon.getImage());
		this.setTitle("ɨ��");
		this.setSize(new Dimension(220, 300));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	private void init() {
		// ������Ӳ˵������������͵������
		this.setJMenuBar(menuBar);
		this.add(topInfoJPanel, BorderLayout.NORTH);
		this.add(bombJPanel);
	}

	public void reStartGame() {
		// ����ԭ�����
		this.remove(menuBar);
		this.remove(topInfoJPanel);
		this.remove(bombJPanel);

		// ��Ϸ��ʼ״̬����Ϊδ��ʼ�����
		GameConfig.isStart = false;

		// ����config��ʼ��
		GameConfig.currentBombCount = GameConfig.initBombCount;
		GameConfig.timerCount = 0;

		// �ؿ������³�ʼ���������
		menuBar = new BombJMenuBar(this);
		topInfoJPanel = new TopInfoJPanel(this);
		bombJPanel = new BombJPanel(this);
		this.add(topInfoJPanel, BorderLayout.NORTH);
		this.add(bombJPanel);

		// �����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		this.pack();
		// �������������������������ּ����С�����仯�����ø÷���ʹ���ֱ仯�ڽ����ϱ���ӳ����
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
		// ��װswing Timer�����Ľӿ�
		return timer;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ���ô�����
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

		// ��ʼ������
		new MainFrame();
	}

}
