package yatdel;

/**
 *
 * @author Ben
 */
public enum Direction {
    Left, Right, Up, Down;

    static Direction random() {
        int num = (int) (Math.random() * (4 - 1 + 1) + 1);
        switch (num) {
            case 1:
                return Left;
            case 2:
                return Right;
            case 3:
                return Up;
            case 4:
                return Down;
            default:
                return Right;
        }
    }
}
