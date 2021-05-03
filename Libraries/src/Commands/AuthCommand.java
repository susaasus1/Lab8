package Commands;

import Data.SpaceMarines;
import Data.User;
import Exceptions.NotFoundCommandException;
import Exceptions.UserNotFoundException;

import java.sql.SQLException;
import java.util.Scanner;

public class AuthCommand extends Command implements Fillable {
    public AuthCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, SpaceMarines spaceMarines, Object... args) {
        try {
            validate(args);
            User authUser = (User)args[0];
            db.selectUserID(authUser.getLogin(), authUser.getPassword());
            CommandManager.auth(authUser);
            return "Вы успешно вошли в систему как " + authUser.getLogin();
        } catch (SQLException | UserNotFoundException | NotFoundCommandException
                e) {
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