package Commands;

import Data.SpaceMarines;
import Data.User;
import Exceptions.UserNotFoundException;

import java.sql.SQLException;

public class ClearCommand extends Command {
    public ClearCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user,DataBase db,SpaceMarines spaceMarines, Object... args) {
        try {
            validate(args);
            if (user!= null){
                int userID = db.selectUserID(user.getLogin(), user.getPassword());
                db.deleteUserNotes(userID);
                CommandManager.updateCollection();
                return "Коллекция очищена";
            }
            return "Вы не вошли в систему";
        } catch (IllegalArgumentException | SQLException | UserNotFoundException e) {
            return e.getMessage();
        }

    }
}
