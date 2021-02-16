package icon;

import javax.swing.*;

public class StaticIcon {
    /** Ц����ť **/
    public static ImageIcon mainIcon = new ImageIcon("./image/icon.gif");

    /** Ц����ť **/
    // ��ֹ״̬
    public static Icon smileFaceIcon = new ImageIcon("./image/faceSmile.gif");
    // Ц��������״̬
    public static Icon pressedSmileFaceIcon = new ImageIcon("./image/faceSmilePressed.gif");
    // ������������ʱЦ���Ķ���Ч��
    public static Icon clickBombPanelFaceIcon = new ImageIcon("./image/faceClickPanel.gif");
    // ��Ϸʧ��
    public static Icon failFaceIcon = new ImageIcon("./image/faceFail.gif");
    // ��Ϸʤ��
    public static Icon winFaceIcon = new ImageIcon("./image/faceWin.gif");

    /** ��������״̬ **/
    // ��ʼ/�հ׸���״̬
    public static Icon initBlankBombIcon = new ImageIcon("./image/blank.gif");
    public static Icon plainPressedBombIcon = new ImageIcon("./image/0.gif");
    // ����Ҽ����
    public static Icon flagBombIcon = new ImageIcon("./image/flag.gif");
    public static Icon askBombIcon = new ImageIcon("./image/ask.gif");
    // ���׸���
    public static Icon blackBombIcon = new ImageIcon("./image/mine.gif");
    public static Icon errorFlagBombIcon = new ImageIcon("./image/mineErrorFlag.gif");
    public static Icon errorClickBombIcon = new ImageIcon("./image/mineError.gif");
    // ����ģʽ���׸���״̬
    public static Icon holeIcon = new ImageIcon("./image/mineHintHole.gif");
    // ɨ�����ָ���
    public static Icon[] num = new Icon[9];

    static {
        for (int i = 0; i < num.length; i++) {
            num[i] = new ImageIcon("./image/" + i + ".gif");
        }
    }

    /** ���� **/
    // ʱ��������
    public static Icon[] time = new Icon[10];
    static {
        for (int j = 0; j <= num.length; j++) {
            time[j] = new ImageIcon("./image/d" + j + ".gif");
        }
    }
}
