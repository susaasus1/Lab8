package client;

import Answers.Request;
import Commands.*;
import Data.SpaceMarine;
import Data.User;
import Exceptions.CommandAlreadyExistsException;
import Exceptions.NotFoundCommandException;
import aplicattion.MainApp;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;


public class CommandReader {
    private final Sender sender;
    private User user;
    private File tmp = new File("tmp");
    private FileWriter tmpWriter = new FileWriter(tmp, false);

    public CommandReader(Sender sender) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, CommandAlreadyExistsException, IOException {
        this.sender = sender;

        CommandManager manager = CommandManager.getInstance();
        manager.initCommand(ExitCommand.class, "exit", "Выход, без сохранения");
        manager.initCommand(InfoCommand.class, "info", "Выводит информацию о коллекции");
        manager.initCommand(HelpCommand.class, "help", "Выводит справку по коммандам");
        manager.initCommand(ShowCommand.class, "show", "Выводит все элементы коллекции");
        manager.initCommand(AddCommand.class, "add", "Добавляет элемент в коллекцию", SpaceMarine.class);
        manager.initCommand(ClearCommand.class, "clear", "Очищает коллекцию");
        manager.initCommand(UpdateCommand.class, "update", "Обновляет значение элемента по id", String.class, SpaceMarine.class);
        manager.initCommand(InsertAtIndexCommand.class, "insert_at_index", "Обновляет значение элемента по индексу", String.class, SpaceMarine.class);
        manager.initCommand(RemoveByIdCommand.class, "remove_by_id", "Удаляет элемент с заданным id", String.class);
        manager.initCommand(RemoveAtIndexCommand.class, "remove_at_index", "Удаляет элемент с заданным id", String.class);
        manager.initCommand(RemoveGreaterCommand.class, "remove_greater", "Удаляет из коллекции все элементы, превыщающий заданный", SpaceMarine.class);
        manager.initCommand(ExecuteScriptCommand.class, "execute_script", "Считывает и испольняет скрипт из файла", String.class);
        manager.initCommand(PrintFieldHeightCommand.class, "print_field_descending_height", "Выводит все значения поля height всех элементов в порядке убывания");
        manager.initCommand(FilterContainsNameCommand.class, "filter_contains_name", "Выводит элементы, значение поля name которых содержит заданную подстроку", String.class);
        manager.initCommand(FilterLessThanWeaponTypeCommand.class, "filter_less_than_weapon_type", "Выводит элементы, значение поля weaponType которых меньше заданного", String.class);
        manager.initCommand(AuthCommand.class, "auth", "Авторизует пользователя", User.class);
        manager.initCommand(RegisterCommand.class, "register", "Региструет пользователя", User.class);
        manager.initCommand(UpdateData.class,"upda","");
    }

    public void read() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String line = scanner.nextLine().trim();
                readCommand(line, scanner);
            } catch (NotFoundCommandException | IllegalArgumentException e) {
                PrintConsole.printerror("Такой команды не существует");
            } catch (ArrayIndexOutOfBoundsException e) {
                PrintConsole.printerror("Вы не указали аргумент");
            }
        }
    }

    public void readFXMLCommand(String arg) {
        try {
            Scanner fxmlScanner = new Scanner(arg);
            while (fxmlScanner.hasNextLine()) {
                String line = fxmlScanner.nextLine().trim();
                readCommand(line, fxmlScanner);
            }
            fxmlScanner.close();
        } catch (NotFoundCommandException e) {
            e.printStackTrace();
        }
    }

    private void readCommand(String line, Scanner scanner) throws NotFoundCommandException {
        String name = CommandManager.parseName(line);

        if (name.equals("exit")) {
            System.exit(0);
        } else if (name.equals("execute_script")) {
            Object[] args = CommandManager.parseArgs(line);
            File file = new File((String) args[0]);
            if (!file.exists())
                MainApp.answerLine = "Скрипта не существует";
            else if (file.exists() && !file.canRead())
                MainApp.answerLine = ("Скрипт невозможно прочитать, проверьте права файла(права на чтение)");
            else if (file.exists() && !file.canExecute())
                MainApp.answerLine = ("Скрипт невозможно выполнить, проверьте права файла (права на выполнение)");
            else {
                try {
                    Scanner fileScanner = new Scanner(file);
                    while (fileScanner.hasNextLine()) {
                        String fileLine = fileScanner.nextLine().trim();

                        if (fileLine.equals(name + ' ' + args[0])) {
                            PrintConsole.printerror("Обнаружена рекурссия");
                        } else {
                            readCommand(fileLine, fileScanner);
                        }
                    }
                    fileScanner.close();
                } catch (FileNotFoundException e) {
                    MainApp.answerLine = ("Скрипта не существует");
                }
            }
        }
        else if (name.equals("auth")) {
            Object[] args = CommandManager.parseArgs(line);
            Command command = CommandManager.getCommand(name);
            Object[] fillableArg = CommandManager.getFillableArgs(command, scanner);
            args = CommandManager.concatArgs(args, fillableArg);
            CommandManager.validate(command, args);
            user = (User) args[0];
            sender.send(new Request(user, command, args));
        } else {
            Object[] args = CommandManager.parseArgs(line);
            Command command = CommandManager.getCommand(name);
            Object[] fillableArg = CommandManager.getFillableArgs(command, scanner);
            args = CommandManager.concatArgs(args, fillableArg);
            CommandManager.validate(command, args);
            sender.send(new Request(user, command, args));
        }
    }
}
