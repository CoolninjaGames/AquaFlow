package yatdel;

import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public class Pointer extends Thread {

    public int x, y;
    public int prevX, prevY;
    public Direction dir;
    public Abstract currentAbstracts;
    public int abstractsIndex;

    //debugging purposes
    public boolean printPos = false;

    public Stack<Integer> stack;

    public Pointer(int x, int y, int abstractIndex) {
        this.x = x;
        this.y = y;
        this.abstractsIndex = abstractIndex;
        this.currentAbstracts = Yatdel.getAbstract(abstractIndex);
        dir = Direction.Right;
        stack = new Stack<>();
    }

    @Override
    public void run() {
        int a = 0;
        int b = 0;
        char c = ' ';
        String s = "";
        int i = 0;
        Pointer p;
        Scanner scan = new Scanner(System.in);
        while (!this.isInterrupted()) {
            if (printPos == true) {
                Console.printPos(this.getName(), currentAbstracts, stack, x, y);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Pointer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            char code = currentAbstracts.grid[y][x];
            switch (code) {
                case 'S':
                    moveFoward();
                    break;
                case 'E':
                    this.interrupt();
                    break;
                case 'Q':
                    System.exit(0);
                    break;
                case 'T':
                    p = new Pointer(this.x, this.y, this.abstractsIndex);
                    p.dir = dirRule(this.dir, false);
                    p.printPos = this.printPos;
                    p.moveFoward();
                    p.start();
                    moveFoward();
                    break;
                case 't':
                    p = new Pointer(this.x, this.y, this.abstractsIndex);
                    p.dir = dirRule(this.dir, true);
                    p.printPos = this.printPos;
                    p.moveFoward();
                    p.start();
                    moveFoward();
                    break;
                case '=':
                    moveFoward();
                    break;
                case '|':
                    moveFoward();
                    break;
                case '>':
                    this.dir = Direction.Right;
                    moveFoward();
                    break;
                case '<':
                    this.dir = Direction.Left;
                    moveFoward();
                    break;
                case 'v':
                    this.dir = Direction.Down;
                    moveFoward();
                    break;
                case '^':
                    this.dir = Direction.Up;
                    moveFoward();
                    break;
                case '(':
                    moveFoward();
                    s = "";
                    try {
                        while (currentAbstracts.grid[y][x] != ')') {
                            s += currentAbstracts.grid[y][x];
                            moveFoward();
                        }
                    } catch (Exception e) {
                        moveFoward();
                        break;
                    }
                    for (i = 1; i < s.length(); i++) {
                        stack.push((int) s.charAt(s.length() - i));
                    }
                    stack.push((int) s.charAt(0));
                    moveFoward();
                    break;
                case '#':
                    s = "";
                    moveFoward();
                    try {
                        while (currentAbstracts.grid[y][x] != '#') {
                            s = s + currentAbstracts.grid[y][x];
                            moveFoward();
                        }
                    } catch (Exception e) {
                        break;
                    }
                    a = Integer.parseInt(s);
                    stack.push(a);
                    moveFoward();
                    break;
                case 'p':
                    stack.pop();
                    moveFoward();
                    break;
                case '+':
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a + b);
                    moveFoward();
                    break;
                case '-':
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a - b);
                    moveFoward();
                    break;
                case '*':
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a * b);
                    moveFoward();
                    break;
                case 'd':
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a / b);
                    moveFoward();
                    break;
                case '%':
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a % b);
                    moveFoward();
                    break;
                case 'I':
                    System.out.print("?: ");
                    s = scan.nextLine();
                    try {
                        stack.push(Integer.parseInt(s));
                    } catch (NumberFormatException e) {
                        if (s.length() == 1) {
                            stack.push((int) s.charAt(0));
                        } else {
                            for (i = 1; i < s.length(); i++) {
                                stack.push((int) s.charAt(s.length() - i));
                            }
                            stack.push((int) s.charAt(0));
                        }
                    }
                    moveFoward();
                    break;
                case 'O':
                    a = stack.pop();
                    c = (char) (int) a;
                    System.out.print(c);
                    moveFoward();
                    break;
                case 'o':
                    a = stack.pop();
                    System.out.print(a);
                    moveFoward();
                    break;
                case '\'':
                    Console.doNothing(); //this exists purely so my IDE will format this try-catch correctly.
                    try {
                        currentAbstracts = Yatdel.getAbstract(++abstractsIndex);
                        dir = Direction.Right;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        try {
                            throw new InvalidAbstractIndex();
                        } catch (InvalidAbstractIndex ex) {
                            Logger.getLogger(Pointer.class.getName()).log(Level.SEVERE, null, ex);
                            this.interrupt();
                        }
                    }
                    break;
                case '\"':
                    int moveCount = stack.pop();
                    try {
                        abstractsIndex += moveCount;
                        currentAbstracts = Yatdel.getAbstract(abstractsIndex);
                        dir = Direction.Right;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        try {
                            throw new InvalidAbstractIndex();
                        } catch (InvalidAbstractIndex ex) {
                            Logger.getLogger(Pointer.class.getName()).log(Level.SEVERE, null, ex);
                            this.interrupt();
                        }
                    }
                    break;
                case '~':
                    int negative = stack.pop();
                    stack.push(-negative);
                    moveFoward();
                    break;
                case 'C':
                    Console.doNothing();
                    try {
                        a = stack.peek();
                    } catch (EmptyStackException e) {
                        moveFoward();
                        break;
                    }
                    if (a > 0) {
                        this.dir = dirRule(this.dir, false);
                    } else {
                        this.dir = dirRule(this.dir, true);
                    }
                    moveFoward();
                    break;
                case 'c':
                    Console.doNothing();
                    try {
                        a = stack.peek();
                    } catch (EmptyStackException e) {
                        moveFoward();
                        break;
                    }
                    if (a > 0) {
                        this.dir = dirRule(this.dir, true);
                    } else {
                        this.dir = dirRule(this.dir, false);
                    }
                    moveFoward();
                    break;
                case '/':
                    if (this.dir == Direction.Right) {
                        this.dir = Direction.Up;
                    } else if (this.dir == Direction.Left) {
                        this.dir = Direction.Left;
                    } else if (this.dir == Direction.Down) {
                        this.dir = Direction.Left;
                    } else if (this.dir == Direction.Up) {
                        this.dir = Direction.Right;
                    }
                    moveFoward();
                    break;
                case '\\':
                    if (this.dir == Direction.Left) {
                        this.dir = Direction.Up;
                    } else if (this.dir == Direction.Right) {
                        this.dir = Direction.Down;
                    } else if (this.dir == Direction.Down) {
                        this.dir = Direction.Right;
                    } else if (this.dir == Direction.Up) {
                        this.dir = Direction.Left;
                    }
                    moveFoward();
                    break;
                case '@':
                    a = stack.pop();
                    b = stack.pop();
                    c = (char) (int) stack.pop();
                    Yatdel.setGrid(Yatdel.getAbstract(abstractsIndex), a, b, c);
                    moveFoward();
                    break;
                case '&':
                    stack.push((int) (Math.random() * (100 - 1 + 1) + 1));
                    moveFoward();
                    break;
                case '?':
                    this.dir = Direction.random();
                    moveFoward();
                    break;
                case '$':
                    Console.doNothing();
                    try {
                        Thread.sleep(stack.pop());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Pointer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    moveFoward();
                    break;
                case '!':
                    a = stack.peek();
                    stack.push(a);
                    moveFoward();
                    break;
                default: {
                    try {
                        throw new InvalidPointerSpace();
                    } catch (InvalidPointerSpace ex) {
                        Logger.getLogger(Pointer.class.getName()).log(Level.SEVERE, null, ex);
                        this.interrupt();
                    }
                }
            }

        }
        scan.close();
    }

    public static synchronized Direction dirRule(Direction dir, boolean isRule2) {
        if (!isRule2) {
            switch (dir) {
                case Left:
                    return Direction.Down;
                case Right:
                    return Direction.Down;
                case Up:
                    return Direction.Left;
                case Down:
                    return Direction.Left;
            }
        } else {
            switch (dir) {
                case Left:
                    return Direction.Up;
                case Right:
                    return Direction.Up;
                case Up:
                    return Direction.Right;
                case Down:
                    return Direction.Right;
            }
        }
        return dir;
    }

    public void moveFoward() {
        switch (dir) {
            case Left:
                x--;
                break;
            case Right:
                x++;
                break;
            case Up:
                y--;
                break;
            case Down:
                y++;
                break;
        }
    }

}
