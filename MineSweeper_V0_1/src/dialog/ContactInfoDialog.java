package dialog;

import main.MainFrame;
import icon.StaticIcon;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ContactInfoDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;

	// top title
	private Box boxTopMain;
	private JLabel labelMainIcon;
	private JLabel labelMainTitle;

	// �ո��ǩ
	private Box boxGap;
	private JLabel labelGap;

	// ����
	private Box boxAuthor;
	private JLabel labelAuthor;

	// ��ϵ��ʽ
	private Box boxContact;
	private JLabel labelContact;
	private Box boxEmail;
	private JLabel labelEmail;

	private JPanel mainPanel;

	private ContactInfoDialog infoDialog;

	public ContactInfoDialog(MainFrame mainFrame) {
		super(mainFrame);

		infoDialog = this;

		this.setTitle("����ɨ��");
		this.add(buildMainPanel());
		this.setSize(new Dimension(300, 200));
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(mainFrame);
		this.setModal(true);
		this.setVisible(true);
	}

	private JPanel buildMainPanel() {
		// ���˱���
		labelMainIcon = new JLabel(StaticIcon.mainIcon);
		labelMainTitle = new JLabel("ɨ��");
		boxTopMain = Box.createHorizontalBox();
		boxTopMain.add(labelMainIcon);
		boxTopMain.add(Box.createHorizontalStrut(20));
		boxTopMain.add(labelMainTitle);

		// ������������
		labelGap = new JLabel("");
		boxGap = Box.createHorizontalBox();
		boxGap.add(labelGap);

		labelAuthor = new JLabel(" �� �ߣ�   Bill Goo   ");
		boxAuthor = Box.createHorizontalBox();
		boxAuthor.add(labelAuthor);

		labelContact = new JLabel("   ���κ���������ϵ��   ");
		boxContact = Box.createHorizontalBox();
		boxContact.add(labelContact);

		labelEmail = new JLabel("billgoo0813@gmail.com");
		boxEmail = Box.createHorizontalBox();
		boxEmail.add(labelEmail);

		// �ײ���ť
		JButton button = new JButton("ȷ��");
		button.addActionListener(e -> infoDialog.dispose());

		JPanel bottomSubPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomSubPanel.add(button);

		// �������Ƕ�������
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
		subPanel.add(boxTopMain);
		subPanel.add(boxGap);
		subPanel.add(boxAuthor);
		subPanel.add(boxContact);
		subPanel.add(boxEmail);
		subPanel.add(bottomSubPanel);

		Border innerBorder = BorderFactory.createEtchedBorder();
		subPanel.setBorder(innerBorder);

		Border outBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(subPanel);
		mainPanel.setBorder(outBorder);

		return mainPanel;
	}

}
