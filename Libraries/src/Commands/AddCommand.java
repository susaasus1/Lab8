package Commands;


import Data.SpaceMarine;
import Data.SpaceMarines;
import Data.User;
import Exceptions.NotDatabaseUpdateException;
import Exceptions.UserNotFoundException;

import java.sql.SQLException;
import java.util.Scanner;


public class AddCommand extends Command implements Fillable {

    public AddCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase dataBase, SpaceMarines spaceMarines, Object... args) {
        try {
            validate(args);
            if (user != null) {
                int userID = dataBase.selectUserID(user.getLogin(), user.getPassword());
                dataBase.insert(((SpaceMarine) args[0]), userID);
                CommandManager.updateCollection();
                return "\nЭлемент добавлен в коллекцию";
            }
            return "Вы не вошли в систему";
        } catch (IllegalArgumentException | SQLException | UserNotFoundException | NotDatabaseUpdateException e) {
            return e.getMessage();
        }
    }
    @Override
    public Object[] fill(Scanner scanner) {
        Object[] args = new Object[1];
        args[0] = SpaceMarine.fillSpaceMarine(scanner);
        return args;
    }
}