package Commands;

import Data.SpaceMarine;
import Data.SpaceMarines;
import Data.User;

import java.util.*;
import java.util.stream.Collectors;

public class FilterContainsNameCommand extends Command{
    public FilterContainsNameCommand(String name, String description, Class<?>[] argsTypes) {
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
                    List<String> list = new ArrayList<>();
                    StringBuilder builder = new StringBuilder();
                    list = spaceMarines.stream().filter(x -> x.getName().equals(args[0])).map(x -> x.toString()).collect(Collectors.toList());
                    if (list.size() == 0) {
                        return "Солдат с таким именем нет в базе данных";
                    } else {
                        for (Object o : list) {
                            builder.append(o).append("\n");
                        }
                        return builder.toString();
                    }
                }
            }
//                LinkedList<SpaceMarine> space=new LinkedList<>();
//                for (SpaceMarine p:spaceMarines){
//                    if (p.getName().equals(args[0])){
//                        space.add(p);
//                    }
//                }
//                Object[] out = space.toArray();
//                StringBuilder builder = new StringBuilder();
//                if (space.size()==0){
//                    return "В коллекции не найдено солдат с таким именем";
//                }else {
//                    for (Object o : out) {
//                        builder.append(o).append("\n");
//                    }
//                    return builder.toString();
//                }
//            }
            return "Вы не вошли в систему";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
}
