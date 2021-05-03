package Data;

import java.time.LocalDate;
import java.util.*;

public class SpaceMarines extends LinkedList<SpaceMarine> {
    private LocalDate initDate;

    public SpaceMarines() {
        initDate = LocalDate.now();
    }

    public void update(List<SpaceMarine> list){
        List<SpaceMarine> space=Collections.synchronizedList(list);
        synchronized (space){
            this.clear();
            this.addAll(space);
        }
    }

    @Override
    public String toString() {
        return  "Тип коллекции: LinkedList\n" +
                "Дата инициализации: " + initDate.toString() + "\n" +
                "Количество элементов: " + this.size();
    }

}
