package Commands;

import Data.SpaceMarine;
import Data.SpaceMarines;
import Data.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class RemoveGreaterCommand extends Command implements Fillable {
    public RemoveGreaterCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, SpaceMarines spaceMarines, Object... args) {
        try {
            validate(args);
            if (user != null) {
                if (spaceMarines.isEmpty()) {
                    return "Команда не может быть выполнена, т.к. коллекция пуста. " +
                            "Добавьте элементы в коллекцию с помощью команды add";
                } else {
                    SpaceMarine person = (SpaceMarine) args[0];
                    Iterator<SpaceMarine> iter = spaceMarines.iterator();
                    while (iter.hasNext()) {
                        SpaceMarine space = iter.next();
                        if (space.hashCode() > person.hashCode()) {
                            if (user.getLogin().equals(space.getOwner().getLogin())) {
                                db.deleteNote(space.getId());
                            }
                        }
                    }
                    CommandManager.updateCollection();
                    return "Были удалены все spaceMarine больше чем " + person.hashCode();
                }

            }
            return "Вы не вошли в систему";
        } catch (IllegalArgumentException | SQLException illegalArgumentException) {
            return illegalArgumentException.getMessage();
        }
    }


    @Override
    public Object[] fill(Scanner scanner) {
        Object[] args = new Object[1];
        args[0] = SpaceMarine.fillSpaceMarine(scanner);
        return args;
    }
}