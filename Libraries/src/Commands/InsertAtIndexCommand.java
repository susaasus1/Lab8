package Commands;

import Data.SpaceMarine;
import Data.SpaceMarines;
import Data.User;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class InsertAtIndexCommand extends Command {
    public InsertAtIndexCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, SpaceMarines spaceMarines, Object... args) {
        try {
            validate(args);

            if (spaceMarines.isEmpty()) {
                return "Команда не может быть выполнена, т.к. коллекция пуста. " +
                        "Добавьте элементы в коллекцию с помощью команды add";
            } else {
                try {
                    int index = Integer.parseInt((String) args[0]);
                    SpaceMarine spaceMarine = (SpaceMarine) args[1];
                    int i = 0;
                    Long id = 0L;
                    for (SpaceMarine p : spaceMarines) {
                        i += 1;
                        if (index == i) {
                            id = p.getId();
                        }
                    }
                    if ((index > i) || (index == 0)) return "Не существует элемента с таким индексом";
                    else {
                        SpaceMarine dbSpaceMarine = db.selectSpaceMarine(id);
                        if (user.getLogin().equals(dbSpaceMarine.getOwner().getLogin())) {
                            db.update(id, spaceMarine);
                            return "Объект успешно обновлен";
                        }
                        return "У вас нет прав на измение этого объекта";
                    }

                } catch (Exception e) {
                    return "Вы ввели данные не верно.";
                }
            }

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

}