package Data;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public enum AstartesCategory implements Serializable {
    DREADNOUGHT,
    INCEPTOR,
    LIBRARIAN,
    CHAPLAIN,
    HELIX;

    static AstartesCategory fillAstartesCategory(Scanner scanner) {
        while (true) {
            System.out.println("Введите одно из значений: DREADNOUGHT, INCEPTOR, LIBRARIAN, CHAPLAIN, HELIX ");
            String astartesCategory = scanner.nextLine().trim();
            if (astartesCategory.equalsIgnoreCase("DREADNOUGHT")) {
                return DREADNOUGHT;
            } else if (astartesCategory.equalsIgnoreCase("INCEPTOR")) {
                return INCEPTOR;
            } else if (astartesCategory.equalsIgnoreCase("LIBRARIAN")) {
                return LIBRARIAN;
            } else if (astartesCategory.equalsIgnoreCase("CHAPLAIN")) {
                return CHAPLAIN;
            } else if (astartesCategory.equalsIgnoreCase("HELIX")) {
                return HELIX;
            } else {
                System.err.println("Вы ввели несуществующее значение.");
            }

        }
    }
    static AstartesCategory fillAstartesCategoryFromFile(Scanner scanner) throws IOException {
        String astartesCategory = scanner.nextLine().trim();
        if (astartesCategory.equalsIgnoreCase("DREADNOUGHT")) {
            return DREADNOUGHT;
        } else if (astartesCategory.equalsIgnoreCase("INCEPTOR")) {
            return INCEPTOR;
        } else if (astartesCategory.equalsIgnoreCase("LIBRARIAN")) {
            return LIBRARIAN;
        } else if (astartesCategory.equalsIgnoreCase("CHAPLAIN")) {
            return CHAPLAIN;
        } else if (astartesCategory.equalsIgnoreCase("HELIX")) {
            return HELIX;
        } else {
            throw new IOException("Вы ввели неверные данные.");
        }
    }


}
