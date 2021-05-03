package Commands;

import Data.SpaceMarine;
import Data.SpaceMarines;
import Data.User;
import Exceptions.NotDatabaseUpdateException;
import Exceptions.SpaceMarineNotFound;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.sql.SQLException;
import java.util.Scanner;

public class UpdateCommand extends Command implements Fillable {

    public UpdateCommand(String name, String description, Class<?>[] argsTypes) {
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
                    Long id = Long.parseLong((String) args[0]);
                    SpaceMarine spaceMarine = (SpaceMarine) args[1];
                    try {
                        SpaceMarine dbSpaceMarine = db.selectSpaceMarine(id);
                        if (user.getLogin().equals(dbSpaceMarine.getOwner().getLogin())) {
                            db.update(id, spaceMarine);
                            return "Объект успешно обновлен";
                        }
                        return "У вас нет прав на измение этого объекта";
                    } catch (SpaceMarineNotFound spaceMarineNotFound) {
                        spaceMarineNotFound.printStackTrace();
                    } catch (NotDatabaseUpdateException e) {
                        e.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
            return "Вы не вошли в систему";
        } catch (IllegalArgumentException e) {
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