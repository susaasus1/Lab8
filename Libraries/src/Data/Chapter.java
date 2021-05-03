package Data;

import Exceptions.FiledIncorrect;
import Reader.ConsoleReader;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

/**
 * Часть солдата
 */
public class Chapter implements Serializable {
    private static final long serialVersionUID = -599087130950208425L;
    private String name;
    private String parentLegion;
    private Integer marinesCount;
    private String world;

    public Chapter(String name, String parentLegion, Integer marinesCount, String world) throws FiledIncorrect {
        if ((name == null) || (name == "")) {
            throw new NullPointerException("Параметр 'name' не может быть равен null");
        }
        if (marinesCount == null) {
            throw new NullPointerException("Параметр 'marinesCount' не может быть равен null");
        }
        if ((marinesCount <= 0) || (marinesCount > 1000)) {
            throw new FiledIncorrect("Параметр 'marinesCount' должен быть больше 0 и меньше либо равен 1000");
        }
        if (world == null) {
            throw new NullPointerException("Параметр 'world' не может быть равен null");
        }
        this.name = name;
        this.parentLegion = parentLegion;
        this.marinesCount = marinesCount;
        this.world = world;
    }

    public static Chapter fillChapter(Scanner scanner) throws FiledIncorrect {
        System.out.println("Ввод объекта Chapter");
        String name = (String) ConsoleReader.conditionalRead(scanner, "Введите name: ", false, String::toString, m -> !m.equals(""));
        String parentLegion = (String) ConsoleReader.conditionalRead(scanner, "Введите parentLegion: ", true, String::toString);
        Integer marinesCount = (Integer) ConsoleReader.conditionalRead(scanner, "Введите marinesCount: ", false, Integer::parseInt, (m) -> Integer.parseInt(m) > 0, m -> Integer.parseInt(m) <= 1000);
        String world = (String) ConsoleReader.conditionalRead(scanner, "Введите world: ", false, String::toString, Objects::nonNull);
        return new Chapter(name,parentLegion, marinesCount, world);
    }

    public static Chapter fillChapterFromFile(Scanner scanner) throws FiledIncorrect, IOException {
        System.out.println("Ввод объекта Chapter");
        String name = (String) ConsoleReader.conditionalRead(scanner, "", false, String::toString, m -> !m.equals(""));
        String parentLegion = (String) ConsoleReader.conditionalRead(scanner, "", true, String::toString);
        Integer marinesCount = (Integer) ConsoleReader.conditionalRead(scanner, "", false, Integer::parseInt, (m) -> Integer.parseInt(m) > 0, m -> Integer.parseInt(m) <= 1000);
        String world = (String) ConsoleReader.conditionalRead(scanner, "", false, String::toString, Objects::nonNull);
        return new Chapter(name,parentLegion, marinesCount, world);
    }
    /**
     * @return Имя служебной части
     */
    public String getName() {
        return name;
    }

    /**
     * @return Родительскую служебную часть
     */
    public String getParentLegion() {
        return parentLegion;
    }

    /**
     * @return Количество солдат в части
     */
    public Integer getMarinesCount() {
        return marinesCount;
    }

    /**
     * @return Название мира служебной части
     */
    public String getWorld() {
        return world;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chapter chapter = (Chapter) o;
        return Objects.equals(name, chapter.name) && Objects.equals(parentLegion, chapter.parentLegion) && Objects.equals(marinesCount, chapter.marinesCount) && Objects.equals(world, chapter.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parentLegion, marinesCount, world);
    }

    @Override
    public String toString() {
        return
                "\n\t\tname: " + name +
                        "\n\t\tparentLegion: " + parentLegion +
                        "\n\t\tmarinesCount: " + marinesCount +
                        "\n\t\tworld: " + world
                ;
    }
}