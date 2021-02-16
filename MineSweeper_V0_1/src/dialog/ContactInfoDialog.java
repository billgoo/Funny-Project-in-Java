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

	// 空格标签
	private Box boxGap;
	private JLabel labelGap;

	// 作者
	private Box boxAuthor;
	private JLabel labelAuthor;

	// 联系方式
	private Box boxContact;
	private JLabel labelContact;
	private Box boxEmail;
	private JLabel labelEmail;

	private JPanel mainPanel;

	private ContactInfoDialog infoDialog;

	public ContactInfoDialog(MainFrame mainFrame) {
		super(mainFrame);

		infoDialog = this;

		this.setTitle("关于扫雷");
		this.add(buildMainPanel());
		this.setSize(new Dimension(300, 200));
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(mainFrame);
		this.setModal(true);
		this.setVisible(true);
	}

	private JPanel buildMainPanel() {
		// 顶端标题
		labelMainIcon = new JLabel(StaticIcon.mainIcon);
		labelMainTitle = new JLabel("扫雷");
		boxTopMain = Box.createHorizontalBox();
		boxTopMain.add(labelMainIcon);
		boxTopMain.add(Box.createHorizontalStrut(20));
		boxTopMain.add(labelMainTitle);

		// 中心文字内容
		labelGap = new JLabel("");
		boxGap = Box.createHorizontalBox();
		boxGap.add(labelGap);

		labelAuthor = new JLabel(" 作 者：   Bill Goo   ");
		boxAuthor = Box.createHorizontalBox();
		boxAuthor.add(labelAuthor);

		labelContact = new JLabel("   有任何问题请联系：   ");
		boxContact = Box.createHorizontalBox();
		boxContact.add(labelContact);

		labelEmail = new JLabel("billgoo0813@gmail.com");
		boxEmail = Box.createHorizontalBox();
		boxEmail.add(labelEmail);

		// 底部按钮
		JButton button = new JButton("确定");
		button.addActionListener(e -> infoDialog.dispose());

		JPanel bottomSubPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomSubPanel.add(button);

		// 主面板内嵌层子面板
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
