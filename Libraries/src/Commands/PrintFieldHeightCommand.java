package Commands;

import Data.SpaceMarine;
import Data.SpaceMarines;
import Data.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintFieldHeightCommand extends Command{
    public PrintFieldHeightCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }
    @Override
    public String execute(User user,DataBase db,SpaceMarines spaceMarines, Object... args) {
        try {
            validate(args);
            if(user!=null) {
            if (spaceMarines.isEmpty()) {
                return "Коллекция пустая. Данные внутри файла отсутствуют!";
            } else {
                List<Double> list = new ArrayList<>();
                StringBuilder builder = new StringBuilder();
                list = spaceMarines.stream().map(x -> x.getHeight()).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                for (Object o : list) {
                    builder.append(o).append("\n");
                }
                return builder.toString();
//                LinkedList<Double> height=new LinkedList<>();
//                for (SpaceMarine p:spaceMarines){
//                    height.add(p.getHeight());
//                }
//                Object[] out = height.toArray();
//                Arrays.sort(out, Collections.reverseOrder());
//                StringBuilder builder = new StringBuilder();
//                for (Object o : out) {
//                    builder.append(o).append("\n");
//                }
//                return builder.toString();
            }
            }
            return "Вы не вошли в систему";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
}
