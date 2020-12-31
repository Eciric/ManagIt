package res.managit.Settings;

public class Settings {
    private static int actualSelectedDataBase;

    public static int getActualSelectedDataBase() {
        return actualSelectedDataBase;
    }

    public static void setActualSelectedDataBase(int actualSelectedDataBase) {
        Settings.actualSelectedDataBase = actualSelectedDataBase;
    }
}
