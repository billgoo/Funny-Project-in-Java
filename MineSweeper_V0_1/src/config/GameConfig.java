package config;

public class GameConfig {
    // ��Ϸ��ʼ������
    public static int boardRow = 10;
    public static int boardCol = 10;
    public static int initBombCount = 10;

    public static int currentBombCount = initBombCount;

    // ��Ϸģʽ���Ƿ�������ģʽ
    public static boolean isCheatMode = false;

    // ��Ϸ�Ƿ�ʼ���е�״̬
    public static boolean isStart = false;

    // ��ʱ��
    public static int timerCount = 0;

    public static int getLevel() {
        // ���عؿ��Ѷ�
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
