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
	 * ����Զ�����Ϸ�����öԻ���
	 */
	private static final long serialVersionUID = 0L;

	private final JLabel jLabelHigh = new JLabel("�߶ȣ� ");
	private final JLabel jLabelWide = new JLabel("��ȣ� ");
	private final JLabel jLabelBomb = new JLabel("������ ");
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

		// ���������
		this.add(getMainPanel());

		// ��ʾ��Ϣ��
		jLabelMessage.setFont(new Font("����", Font.PLAIN, 12));
		jLabelMessage.setForeground(Color.red);
		this.add(jLabelMessage, BorderLayout.NORTH);

		this.setTitle("�Զ�������");
		this.setSize(new Dimension(200, 150));
		this.setResizable(false);
		this.setLocationRelativeTo(mainFrame);
		// �������������Ҫ��˳��
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setModal(true);	// ��ֹ���������Ӵ���֮�����
		this.setVisible(true);

		// �رմ��ڵ�ʱ��������Ϸ
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				mainFrame.reStartGame();
			}
		});
	}

	public JPanel getMainPanel() {
		// �����ȫ�����
		JPanel jPanel = new JPanel();

		// �����panel���������panel
		JPanel subJPanel = new JPanel();
		subJPanel.setLayout(new GridLayout(1, 2));

		// ���߿�
		Border border = BorderFactory.createEmptyBorder(3, 15, 5, 15);
		// �ڲ�߿�
		Border innerBorder = BorderFactory.createEmptyBorder(5, 20, 5, 5);

		// �����������box layout����ʼ�������
		jTextFieldHigh = new JTextField(GameConfig.boardRow + "");
		jTextFieldWide = new JTextField(GameConfig.boardCol + "");
		jTextFieldBomb = new JTextField(GameConfig.currentBombCount + "");
		Box boxHigh = buildInputBox(jTextFieldHigh, jLabelHigh);
		Box boxWide = buildInputBox(jTextFieldWide, jLabelWide);
		Box boxBomb = buildInputBox(jTextFieldBomb, jLabelBomb);
		// ��������
		Box boxInput = new Box(BoxLayout.Y_AXIS);
		boxInput.add(boxHigh);
		boxInput.add(Box.createVerticalStrut(8));
		boxInput.add(boxWide);
		boxInput.add(Box.createVerticalStrut(8));
		boxInput.add(boxBomb);
		boxInput.add(Box.createVerticalStrut(8));
		boxInput.setBorder(innerBorder);

		// ��ť��box layout
		Box boxButton = new Box(BoxLayout.Y_AXIS);
		buttonOK = new JButton("ȷ��");
		buttonCancer = new JButton("ȡ��");
		// ���ð�ť���
		buttonOK.setPreferredSize(new Dimension(70, 30));
		buttonOK.setMargin(new Insets(0, 2, 0, 2));
		buttonCancer.setSize(new Dimension(70, 30));
		buttonCancer.setMargin(new Insets(0, 2, 0, 2));
		// ���ð�ť������
		CustomGameDialogButtonListener buttonListener = new CustomGameDialogButtonListener(
				this, mainFrame);
		buttonOK.addActionListener(buttonListener);
		buttonCancer.addActionListener(buttonListener);
		// ��Ӱ�ť
		boxButton.add(buttonOK);
		boxButton.add(Box.createVerticalStrut(25));
		boxButton.add(buttonCancer);
		boxButton.setBorder(innerBorder);

		// �ڲ��������������Ͱ�ť��
		subJPanel.add(boxInput);
		subJPanel.add(boxButton);

		// �ڲ����ŵ���������
		jPanel.setBorder(border);
		jPanel.add(subJPanel);

		return jPanel;
	}

	private Box buildInputBox(JTextField inputJTextField, JLabel jLabel) {
		Box inputBox = Box.createHorizontalBox();

		// �����������������
		inputJTextField.setPreferredSize(new Dimension(30, 20));
		inputJTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = inputJTextField.getText();

				Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
				Matcher matcher = pattern.matcher(text);
				if (!matcher.matches()) {
					jLabelMessage.setText("���������֣����ܳ�����λ");
					if (text.length() > 3) {
						jTextFieldHigh.setText(text.substring(0, 3));
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if ((ch < '0') || (ch > '9')) {
					jLabelMessage.setText("���������֣����ܳ�����λ");
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
