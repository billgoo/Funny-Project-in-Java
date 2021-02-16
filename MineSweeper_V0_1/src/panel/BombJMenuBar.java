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
	 * �˵���
	 */
	private static final long serialVersionUID = 0L;

	MainFrame mainFrame;

	// define Menu
	JMenu menuGame = new JMenu("��Ϸ(G)");
	JMenu menuHelp = new JMenu("����(H)");

	// define MenuItem
	// item for Game
	JMenuItem menuItemStart = new JMenuItem("����(N)", KeyEvent.VK_N);

	JMenuItem menuItemBeginner = new JMenuItem("����(B)", KeyEvent.VK_B);
	JMenuItem menuItemIntermediate = new JMenuItem("�м�(I)", KeyEvent.VK_I);
	JMenuItem menuItemAdvance = new JMenuItem("�߼�(A)", KeyEvent.VK_A);

	JMenuItem menuItemCustom = new JMenuItem("�Զ���(C)", KeyEvent.VK_C);
	JMenuItem menuItemExit = new JMenuItem("�˳�(E)", KeyEvent.VK_E);

	// item for Help
	JMenuItem menuItemCheat = new JMenuItem("������Ϸ��ʾ");
	JMenuItem menuItemAbout = new JMenuItem("����ɨ��");

	public BombJMenuBar(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		// ����ȫ�����ø��²˵�������
		if (GameConfig.isCheatMode) {
			// �Ѿ����ڿ�������ģʽ��������Ϊѡ���Ƿ�ر�����ģʽ
			menuItemCheat = new JMenuItem("�ر���Ϸ��ʾ");
		} else {
			menuItemCheat = new JMenuItem("������Ϸ��ʾ");
		}

		init();
	}

	private void init() {
		// ���ÿ�ݼ� alt + *
		menuGame.setMnemonic(KeyEvent.VK_G);
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuItemStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK));
		menuItemCustom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK));

		// �˵�����뵽�˵�
		// Game
		menuGame.add(menuItemStart);
		// �˵�����ָ���
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

		// ��Ӳ˵�����
		// Game
		// ���¿���
		menuItemStart.addActionListener(e -> mainFrame.reStartGame());
		// �ؿ����и߼��Զ�����
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
		// �Զ��弶���ؿ�����������config����
		menuItemCustom.addActionListener(e -> new CustomGameJDialog(mainFrame));
		// �˳�
		menuItemExit.addActionListener(e -> System.exit(JFrame.EXIT_ON_CLOSE));

		// Help
		menuItemCheat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!GameConfig.isCheatMode) {
					// ��ǰ������ģʽ�������������ģʽ
					GameConfig.isCheatMode = true;
					menuItemCheat.setText("�ر���Ϸ��ʾ");
				} else {
					// ��ǰ����ģʽ������ر�����ģʽ
					GameConfig.isCheatMode = false;
					menuItemCheat.setText("������Ϸ��ʾ");
				}
				mainFrame.getBombJPanel().changeCheatBombIcon(GameConfig.isCheatMode);
			}
		});
		menuItemAbout.addActionListener(e -> new ContactInfoDialog(mainFrame));

		// �˵����뵽�˵���
		this.add(menuGame);
		this.add(menuHelp);
	}
}
