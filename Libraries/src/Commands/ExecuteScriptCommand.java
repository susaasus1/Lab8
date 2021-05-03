package Commands;

import Data.SpaceMarines;
import Data.User;
import Exceptions.NotFoundCommandException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    public String execute(User user, DataBase db, SpaceMarines spaceMarines, Object... args) {
        try {
            validate(args);

            if (user != null) {
                // TODO: Это тоже не до конца доделано
                File file = new File((String) args[0]);
                if (!file.exists()) return "Скрипта не существует";
                else if (file.exists() && !file.canRead()) return "Скрипт невозможно прочитать, проверьте права файла(права на чтение)";
                else if (file.exists() && !file.canExecute()) return "Скрипт невозможно выполнить, проверьте права файла (права на выполнение)";
                else {
                    try {
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine().trim();
                            List<String> collection = Arrays.asList(line.split(" "));
                            if (collection.get(0).equals("execute_script")) {
                                if (!spaceMarines.isEmpty()) {
//							Command cmd = Command.getCommand(Command.parseName(collection.get(0)));
//							cmd.execute(collection.get(1));
                                } else {
                                    return "Коллекция пустая, рекурсия прервалась";
                                }
                            } else {
//						Command cmd = Command.getCommand(Command.parseName(line));
//						String[] arg = Command.parseArgs(line);
//						cmd.execute(arg);
                            }
                        }
                        scanner.close();
                        return "Скрипт был выполнен";
                    } catch (FileNotFoundException e) {
                        return "Скрипта не существует";
                    }
                }
            }
            return "Вы не вошли в систему";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

//		if (args.length != 1) {
//			System.err.println("В команде " + getName() + " должен быть 1 параметр");
//
//		} else {
//				File file = new File(args[0]);
//				if (!file.exists()) System.err.println("Скрипта не существует");
//				else if (file.exists() && !file.canRead()) System.err.println("Скрипт невозможно прочитать, проверьте права файла(права на чтение)");
//				else if (file.exists() && !file.canExecute()) System.err.println("Скрипт невозможно выполнить, проверьте права файла (права на выполнение)");
//				else {
//				Scanner scanner = new Scanner(file);
//				while (scanner.hasNextLine()) {
//					String line = scanner.nextLine().trim();
//					List<String> collection = Arrays.asList(line.split(" "));
//					if (collection.get(0).equals("execute_script")) {
//						if (!persons.isEmpty()) {
//							Command cmd = Command.getCommand(Command.parseName(collection.get(0)));
//							cmd.execute(collection.get(1));
//						} else System.err.println("Коллекция пустая, рекурсия прервалась");
//					} else {
//						Command cmd = Command.getCommand(Command.parseName(line));
//						String[] arg = Command.parseArgs(line);
//						cmd.execute(arg);
//					}
//				}
//				scanner.close();
//				}

    }

    @Override
    public void validate(Object... args) throws IllegalArgumentException {
        super.validate(args);
    }
}