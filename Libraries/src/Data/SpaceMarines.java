package Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class SpaceMarines extends LinkedList<SpaceMarine> implements Serializable {
    private LocalDate initDate;
    private static final long serialVersionUID = -6565259818539791449L;
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
