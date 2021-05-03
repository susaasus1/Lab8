package Commands;

import Data.SpaceMarine;
import Data.SpaceMarines;
import Data.User;
import org.omg.CosNaming.NamingContextPackage.NotFound;


public class RemoveByIdCommand extends Command {
    public RemoveByIdCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, SpaceMarines spaceMarines, Object... args) {
        try {
            validate(args);
            if (user != null) {
                Long id = Long.parseLong((String) args[0]);
                try {
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