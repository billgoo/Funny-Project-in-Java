package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import config.GameConfig;

import listener.CustomGameDialogButtonListener;
import main.MainFrame;

public class CustomGameJDialog extends JDialog {
	/**
	 * 点击自定义游戏的设置对话框
	 */
	private static final long serialVersionUID = 0L;

	private final JLabel jLabelHigh = new JLabel("高度： ");
	private final JLabel jLabelWide = new JLabel("宽度： ");
	private final JLabel jLabelBomb = new JLabel("雷数： ");
	private final JLabel jLabelMessage = new JLabel("    ");

	private JTextField jTextFieldHigh;
	private JTextField jTextFieldWide;
	private JTextField jTextFieldBomb;

	private JButton buttonOK;
	private JButton buttonCancer;

	MainFrame mainFrame;

	public CustomGameJDialog(final MainFrame mainFrame) {
		super(mainFrame);
		this.mainFrame = mainFrame;

		// 主输入面板
		this.add(getMainPanel());

		// 提示信息栏
		jLabelMessage.setFont(new Font("楷书", Font.PLAIN, 12));
		jLabelMessage.setForeground(Color.red);
		this.add(jLabelMessage, BorderLayout.NORTH);

		this.setTitle("自定义雷区");
		this.setSize(new Dimension(200, 150));
		this.setResizable(false);
		this.setLocationRelativeTo(mainFrame);
		// 以下三个语句需要按顺序
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setModal(true);	// 禁止父窗体与子窗体之间操作
		this.setVisible(true);

		// 关闭窗口的时候重启游戏
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				mainFrame.reStartGame();
			}
		});
	}

	public JPanel getMainPanel() {
		// 最外层全局面板
		JPanel jPanel = new JPanel();

		// 最外层panel里面的容器panel
		JPanel subJPanel = new JPanel();
		subJPanel.setLayout(new GridLayout(1, 2));

		// 外层边框
		Border border = BorderFactory.createEmptyBorder(3, 15, 5, 15);
		// 内层边框
		Border innerBorder = BorderFactory.createEmptyBorder(5, 20, 5, 5);

		// 长宽地雷数的box layout，初始化输入框
		jTextFieldHigh = new JTextField(GameConfig.boardRow + "");
		jTextFieldWide = new JTextField(GameConfig.boardCol + "");
		jTextFieldBomb = new JTextField(GameConfig.currentBombCount + "");
		Box boxHigh = buildInputBox(jTextFieldHigh, jLabelHigh);
		Box boxWide = buildInputBox(jTextFieldWide, jLabelWide);
		Box boxBomb = buildInputBox(jTextFieldBomb, jLabelBomb);
		// 添加输入框
		Box boxInput = new Box(BoxLayout.Y_AXIS);
		boxInput.add(boxHigh);
		boxInput.add(Box.createVerticalStrut(8));
		boxInput.add(boxWide);
		boxInput.add(Box.createVerticalStrut(8));
		boxInput.add(boxBomb);
		boxInput.add(Box.createVerticalStrut(8));
		boxInput.setBorder(innerBorder);

		// 按钮的box layout
		Box boxButton = new Box(BoxLayout.Y_AXIS);
		buttonOK = new JButton("确定");
		buttonCancer = new JButton("取消");
		// 设置按钮外观
		buttonOK.setPreferredSize(new Dimension(70, 30));
		buttonOK.setMargin(new Insets(0, 2, 0, 2));
		buttonCancer.setSize(new Dimension(70, 30));
		buttonCancer.setMargin(new Insets(0, 2, 0, 2));
		// 设置按钮监听器
		CustomGameDialogButtonListener buttonListener = new CustomGameDialogButtonListener(
				this, mainFrame);
		buttonOK.addActionListener(buttonListener);
		buttonCancer.addActionListener(buttonListener);
		// 添加按钮
		boxButton.add(buttonOK);
		boxButton.add(Box.createVerticalStrut(25));
		boxButton.add(buttonCancer);
		boxButton.setBorder(innerBorder);

		// 内部面板添加输入区和按钮区
		subJPanel.add(boxInput);
		subJPanel.add(boxButton);

		// 内层面板放到外层面板上
		jPanel.setBorder(border);
		jPanel.add(subJPanel);

		return jPanel;
	}

	private Box buildInputBox(JTextField inputJTextField, JLabel jLabel) {
		Box inputBox = Box.createHorizontalBox();

		// 设置输入框及输入属性
		inputJTextField.setPreferredSize(new Dimension(30, 20));
		inputJTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = inputJTextField.getText();

				Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
				Matcher matcher = pattern.matcher(text);
				if (!matcher.matches()) {
					jLabelMessage.setText("请输入数字，不能超过三位");
					if (text.length() > 3) {
						jTextFieldHigh.setText(text.substring(0, 3));
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if ((ch < '0') || (ch > '9')) {
					jLabelMessage.setText("请输入数字，不能超过三位");
					e.setKeyChar((char) 9);
				} else {
					jLabelMessage.setText("    ");
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		inputBox.add(jLabel);
		inputBox.add(inputJTextField);

		return inputBox;
	}

	public JLabel getJLabelMessage() {
		return jLabelMessage;
	}

	public JTextField getJTextFieldHigh() {
		return jTextFieldHigh;
	}

	public JTextField getJTextFieldWide() {
		return jTextFieldWide;
	}

	public JTextField getJTextFieldBomb() {
		return jTextFieldBomb;
	}

	public JButton getButtonOK() {
		return buttonOK;
	}

	public JButton getButtonCancer() {
		return buttonCancer;
	}

}
