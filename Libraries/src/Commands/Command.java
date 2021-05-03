package Commands;

import Data.SpaceMarines;
import Data.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public abstract class Command implements Serializable {
    private static final long serialVersionUID = -6185479884133132648L;
    protected static ArrayList<Command> commands = new ArrayList<>();
    protected Class<?>[] argsTypes;
    protected SpaceMarines spaceMarines;
    private String name;
    private String description;


    public Command(String name, String description, Class<?>[] argsTypes) {
        this.name = name;
        this.description = description;
        this.argsTypes = argsTypes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSignature() {
        return name + " " + Arrays.toString(Arrays.stream(argsTypes)
                .map((arg) -> arg.getName().split("\\."))
                .map((arg) -> arg[arg.length - 1]).toArray())
                .replace('[', '{')
                .replace(']', '}');
    }

    public void validate(Object... args) throws IllegalArgumentException {
       try{ if (args.length == argsTypes.length) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].getClass() != argsTypes[i]) {
                    throw new IllegalArgumentException("Указан неверный тип параметра - " + getSignature());
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
       } catch (IllegalArgumentException e) {
           System.out.println("Количество параметров не соответсвуют количество параметров команде - " + getSignature());
       }

    }

    @Override
    public String toString() {
        return getSignature() + " - " + description;
    }

    public abstract String execute(User user, DataBase dataBase, SpaceMarines spaceMarines, Object... args);
}
