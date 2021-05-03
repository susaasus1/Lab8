package Commands;

import Data.SpaceMarines;
import Data.User;

public class InfoCommand extends Command {
    public InfoCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, SpaceMarines spaceMarines, Object... args) {
        try {

            validate(args);
            if (user != null) {
                return spaceMarines.toString();
            }
            return "Вы не вошли в систему";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
}
