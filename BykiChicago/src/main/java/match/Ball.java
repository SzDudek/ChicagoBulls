package match;

public class Ball {
    private static int x;
    private static int y;

    public static int getX() {
        return x;
    }

    public static void setX(int xnew) {
        x = xnew;
    }

    public static int getY() {
        return y;
    }

    public static void setY(int ynew) {
        y = ynew;
    }

    public Ball(){
        x=5;
        y=25;
        Court.courtState[5][25]=3;
    }
}
