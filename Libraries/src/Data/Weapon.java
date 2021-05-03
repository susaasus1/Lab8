package Data;


import java.io.IOException;
import java.util.Scanner;

/**
 * Перечесление оружия солдата
 */
public enum Weapon {
    COMBI_FLAMER(1),
    GRENADE_LAUNCHER(2),
    HEAVY_FLAMER(3),
    MULTI_MELTA(4);

    private int ind;

    Weapon(int ind) {
        this.ind=ind;
    }


    static Weapon fillWeapon(Scanner scanner){
        while(true){
            System.out.println("Введите одно из значений: COMBI_FLAMER,GRENADE_LAUNCHER,HEAVY_FLAMER,MULTI_MELTA");
            String weapon = scanner.nextLine().trim();
            if (weapon.equalsIgnoreCase("COMBI_FLAMER")){
                return COMBI_FLAMER;
            } else if (weapon.equalsIgnoreCase("GRENADE_LAUNCHER")){
                return GRENADE_LAUNCHER;
            }else if (weapon.equalsIgnoreCase("HEAVY_FLAMER")){
                return HEAVY_FLAMER;
            }else if (weapon.equalsIgnoreCase("MULTI_MELTA")){
                return MULTI_MELTA;
            }else{
                System.err.println("Вы ввели несуществующее значение.");
            }

        }

    }
    static Weapon fillWeaponFromFile(Scanner scanner) throws IOException {
        while(true){
            String weapon = scanner.nextLine().trim();
            if (weapon.equalsIgnoreCase("COMBI_FLAMER")){
                return COMBI_FLAMER;
            } else if (weapon.equalsIgnoreCase("GRENADE_LAUNCHER")){
                return GRENADE_LAUNCHER;
            }else if (weapon.equalsIgnoreCase("HEAVY_FLAMER")){
                return HEAVY_FLAMER;
            }else if (weapon.equalsIgnoreCase("MULTI_MELTA")){
                return MULTI_MELTA;
            }else{
                throw new IOException("Вы ввели неверные данные.");
            }

        }

    }
    /**
     * Генерирует крассивый вывод enum
     * @return Возвращает все элементы enum через запятую
     */
    public static String nameList() {
        String nameList = "";
        for (Weapon weaponType : values()) {
            nameList += weaponType.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }

    /**
     *
     * @return индекс оружия солдата
     */
    public int getInd() {
        return ind;
    }
}
