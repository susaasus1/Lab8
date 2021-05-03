package Commands;

import Data.SpaceMarines;
import Data.User;
import Exceptions.NotDatabaseUpdateException;

import java.sql.SQLException;
import java.util.Scanner;

public class RegisterCommand extends Command implements Fillable {
    public RegisterCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, SpaceMarines persons, Object... args) {
        try {
            validate(args);
            User regUser = (User) args[0];
            db.insert(regUser);
            return "Пользователь " + regUser.getLogin() + " успешно зарегистрирован";
        } catch (SQLException | NotDatabaseUpdateException e) {
            return e.getMessage();
        }
    }

    @Override
    public Object[] fill(Scanner scanner) {
        Object[] args = new Object[1];
        args[0] = User.fillUser(scanner);
        return args;
    }
}
