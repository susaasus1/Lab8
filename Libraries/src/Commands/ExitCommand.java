package Commands;

import Data.SpaceMarines;
import Data.User;

public class ExitCommand extends Command {
    public ExitCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, SpaceMarines spaceMarines, Object... args) {
        try {
            validate(args);
            if (user != null) {
                System.exit(0);
                return "Программа успешно завершена!";
            }
            return "Вы не вошли в систему";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
}
