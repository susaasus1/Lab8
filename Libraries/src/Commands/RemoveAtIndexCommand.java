package Commands;

import Data.SpaceMarine;
import Data.SpaceMarines;
import Data.User;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class RemoveAtIndexCommand extends Command {
    public RemoveAtIndexCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, SpaceMarines spaceMarines, Object... args) {
        try {
            validate(args);
            if (user != null) {
                try {
                    int i = 0;
                    int index = Integer.parseInt((String) args[0]);
                    Long id = 0L;
                    for (SpaceMarine p : spaceMarines) {
                        i++;
                        if (index == i) {
                            id = p.getId();
                        }
                    }
                    SpaceMarine spaceMarine=db.selectSpaceMarine(id);
                    if (user.getLogin().equals(spaceMarine.getOwner().getLogin())) {
                        db.deleteNote(id);
                        CommandManager.updateCollection();
                        return "Объект успешно удален";
                    }
                    return "У вас нет прав на удаление этого объекта";
                } catch (Exception e) {
                    return "Вы ввели данные не верно.";
                }
            }
            return "Вы не вошли в систему";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
}
