package player;

public interface Actions {

    boolean isFree(int x, int y);

    void shoot();

    void pick(int x, int y, boolean pos1, boolean pos2);

    void retake(int x, int y, int ballField);

    void moveDirection(int x, int y);

    void move();

    void specialSkill();
}