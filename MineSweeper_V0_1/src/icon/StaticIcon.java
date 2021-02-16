package icon;

import javax.swing.*;

public class StaticIcon {
    /** 笑脸按钮 **/
    public static ImageIcon mainIcon = new ImageIcon("./image/icon.gif");

    /** 笑脸按钮 **/
    // 静止状态
    public static Icon smileFaceIcon = new ImageIcon("./image/faceSmile.gif");
    // 笑脸被按下状态
    public static Icon pressedSmileFaceIcon = new ImageIcon("./image/faceSmilePressed.gif");
    // 按下雷区方格时笑脸的动画效果
    public static Icon clickBombPanelFaceIcon = new ImageIcon("./image/faceClickPanel.gif");
    // 游戏失败
    public static Icon failFaceIcon = new ImageIcon("./image/faceFail.gif");
    // 游戏胜利
    public static Icon winFaceIcon = new ImageIcon("./image/faceWin.gif");

    /** 雷区方块状态 **/
    // 初始/空白格子状态
    public static Icon initBlankBombIcon = new ImageIcon("./image/blank.gif");
    public static Icon plainPressedBombIcon = new ImageIcon("./image/0.gif");
    // 鼠标右键标记
    public static Icon flagBombIcon = new ImageIcon("./image/flag.gif");
    public static Icon askBombIcon = new ImageIcon("./image/ask.gif");
    // 地雷格子
    public static Icon blackBombIcon = new ImageIcon("./image/mine.gif");
    public static Icon errorFlagBombIcon = new ImageIcon("./image/mineErrorFlag.gif");
    public static Icon errorClickBombIcon = new ImageIcon("./image/mineError.gif");
    // 作弊模式地雷格子状态
    public static Icon holeIcon = new ImageIcon("./image/mineHintHole.gif");
    // 扫雷数字格子
    public static Icon[] num = new Icon[9];

    static {
        for (int i = 0; i < num.length; i++) {
            num[i] = new ImageIcon("./image/" + i + ".gif");
        }
    }

    /** 顶栏 **/
    // 时间栏数字
    public static Icon[] time = new Icon[10];
    static {
        for (int j = 0; j <= num.length; j++) {
            time[j] = new ImageIcon("./image/d" + j + ".gif");
        }
    }
}
