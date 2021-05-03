package Commands;

import Data.SpaceMarines;
import Data.User;

import java.util.Arrays;


public class ShowCommand extends Command {
    public ShowCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user,DataBase db,SpaceMarines spaceMarines, Object... args) {
        try {
            validate(args);
            if (user != null) {
                if (spaceMarines.isEmpty()) {
                    return "Коллекция пустая. Данные внутри файла отсутствуют!";
                } else {
                    Object[] out = spaceMarines.toArray();
                    Arrays.sort(out);
                    StringBuilder builder = new StringBuilder();
                    for (Object o : out) {
                        builder.append(o).append("\n");
                    }
                    return builder.toString();

                }
            }
            return "Вы не вошли в систему";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
}