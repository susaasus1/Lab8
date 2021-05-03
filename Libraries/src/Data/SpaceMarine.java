package Data;

import Exceptions.FiledIncorrect;
import Exceptions.WrongSpaceMarineException;
import Reader.ConsoleReader;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class SpaceMarine implements Comparable<SpaceMarine>,Serializable {
    private static final long serialVersionUID = -6565259818539791441L;
    private Long id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDate creationDate;
    private Float health;
    private double height;
    private AstartesCategory category;
    private Weapon weaponType;
    private Chapter chapter;
    private User owner;

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public SpaceMarine(Long id, String name, Coordinates coordinates, LocalDate creationDate, Float health, double height, String category, String weaponType, Chapter chapter, User owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.health = health;
        this.height = height;
        this.category = AstartesCategory.valueOf(category);
        this.weaponType = Weapon.valueOf(weaponType);
        this.chapter = chapter;
        this.owner=owner;
    }

    public SpaceMarine() {
    }


    public void validate() throws WrongSpaceMarineException {
        try {
            if (this.id <= 0 ) throw new WrongSpaceMarineException("ID");
            if (this.name == null || this.name.equals("")) throw new WrongSpaceMarineException("Name");
            if (this.coordinates == null) throw new WrongSpaceMarineException("Coordinates");
            if (this.creationDate == null) throw new WrongSpaceMarineException("CreationDate");
            if (this.health <= 0) throw new WrongSpaceMarineException("Health");
            if (this.weaponType == null) throw new WrongSpaceMarineException("WeaponType");
            if (this.chapter.getName() == null || this.chapter.getName().equals(""))
                throw new WrongSpaceMarineException("Chapter (Name)");
            if (this.chapter.getMarinesCount() <= 0 || this.chapter.getMarinesCount() > 1000)
                throw new WrongSpaceMarineException("Chapter (MarinesCount)");
            if (this.chapter.getWorld() == null) throw new WrongSpaceMarineException("Chapter (World)");
        } catch (WrongSpaceMarineException e) {
            e.getMessage();
            System.exit(0);
        }
    }

    public static SpaceMarine fillSpaceMarine(Scanner scanner) {
        SpaceMarine spaceMarine = new SpaceMarine();
        try {
            System.out.println("Ввод объекта SpaceMarine");
            spaceMarine.name = (String) ConsoleReader.conditionalRead(scanner, "Введите имя: ", false, String::toString, Objects::nonNull, (m) -> !m.equals(""));
            spaceMarine.coordinates = Coordinates.fillCoordinates(scanner);
            spaceMarine.creationDate = LocalDate.now();
            spaceMarine.health = (Float) ConsoleReader.conditionalRead(scanner, "Введите здоровье: ", false, Float::parseFloat, (m) -> Float.parseFloat(m) > 0);
            spaceMarine.height = (double) ConsoleReader.conditionalRead(scanner, "Введите рост: ", false, Double::parseDouble);
            spaceMarine.category = AstartesCategory.fillAstartesCategory(scanner);
            spaceMarine.weaponType = Weapon.fillWeapon(scanner);
            spaceMarine.chapter = Chapter.fillChapter(scanner);
        } catch (NoSuchElementException e) {
            System.err.println("Ну почему-ты до конца не ввел Person :(");
        } catch (FiledIncorrect filedIncorrect) {
            filedIncorrect.printStackTrace();
        }
        return spaceMarine;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Float getHealth() {
        return health;
    }

    public double getHeight() {
        return height;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public Chapter getChapter() {
        return chapter;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setHealth(Float health) {
        this.health = health;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setCategory(AstartesCategory category) {
        this.category = category;
    }

    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public int compareTo(SpaceMarine p) {
        return this.getId().compareTo(p.getId());
    }

    @Override
    public String toString() {
        return  "ID:" + id +
                "\nUSER: "+ owner+
                "\n\tName: " + name  +
                "\n\tCoordinates: " + coordinates +
                "\n\tCreationDate(YYYY-MM-DD): " + creationDate +
                "\n\tHealth: " + health +
                "\n\tHeight: " + height +
                "\n\tCategory: " + category +
                "\n\tWeaponType: " + weaponType +
                "\n\tChapter: " + chapter
                ;
    }

    public User getOwner() {
        return owner;
    }
}
