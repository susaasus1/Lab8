package Commands;

import Data.SpaceMarines;
import Data.User;
import Data.Weapon;

import java.util.stream.Collectors;

public class FilterLessThanWeaponTypeCommand extends Command {
    public FilterLessThanWeaponTypeCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user, DataBase db, SpaceMarines spaceMarines, Object... args) {
        try {
            validate(args);
            if (user != null) {
                if (spaceMarines.isEmpty()) {
                    return "Коллекция пустая. Данные внутри файла отсутствуют!";
                } else {
                    Weapon weapon = Weapon.valueOf(args[0].toString());
                    return spaceMarines.stream().filter(x -> x.getWeaponType().getInd() < weapon.getInd()).map(x -> x.toString()).collect(Collectors.joining("\n"));

                }
            }
            return "Вы не вошли в систему";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
}
