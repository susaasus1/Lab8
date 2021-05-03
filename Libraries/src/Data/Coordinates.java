package Data;

import Reader.ConsoleReader;

import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

/**
 * X,Y координаты
 */
public class Coordinates implements Serializable {
    private static final long serialVersionUID = -592087130950208425L;
    private float x;
    private int y;

    public Coordinates(float x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinates fillCoordinates(Scanner scanner) throws NullPointerException {
        System.out.println("Ввод объекта Coordinates:");
        float x = (float) ConsoleReader.conditionalRead(scanner, "Введите x: ", false, Float::parseFloat,Objects::nonNull);
        int y = (int) ConsoleReader.conditionalRead(scanner, "Введите y: ", false, Integer::parseInt);
        return new Coordinates(x, y);
    }

    public static Coordinates fillCoordinatesFromFile(Scanner scanner) throws NullPointerException {
        System.out.println("Ввод объекта Coordinates:");
        float x = (float) ConsoleReader.conditionalRead(scanner, "", false, Float::parseFloat);
        int y = (int) ConsoleReader.conditionalRead(scanner, "", false, Integer::parseInt);
        return new Coordinates(x, y);
    }

    /**
     * @return Координату X
     */
    public float getX() {
        return x;
    }

    /**
     * @return Координату Y
     */
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "X=" + x + "," + "Y=" + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(that.x, x) == 0 && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
