package config;

public class GameConfig {
    // 游戏初始化数据
    public static int boardRow = 10;
    public static int boardCol = 10;
    public static int initBombCount = 10;

    public static int currentBombCount = initBombCount;

    // 游戏模式，是否是作弊模式
    public static boolean isCheatMode = false;

    // 游戏是否开始进行的状态
    public static boolean isStart = false;

    // 计时器
    public static int timerCount = 0;

    public static int getLevel() {
        // 返回关卡难度
        if (boardRow == 10 && boardCol == 10 && initBombCount == 10) {
            return 1;
        } else if (boardRow == 16 && boardCol == 16 && initBombCount == 40) {
            return 2;
        } else if (boardRow == 25 && boardCol == 30 && initBombCount == 100) {
            return 3;
        } else {
            return 0;
        }
    }
}
