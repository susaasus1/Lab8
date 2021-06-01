package Commands;


import Data.SpaceMarine;
import Data.SpaceMarines;
import Data.User;
import Exceptions.CommandAlreadyExistsException;
import Exceptions.NotDatabaseUpdateException;
import Exceptions.NotFoundCommandException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public final class CommandManager {
    private static CommandManager instance;
    public static SpaceMarines spaceMarines;
    private static ArrayList<Command> commands;
    private static User user;
    private static DataBase database;

    public CommandManager() {
    }

    private CommandManager(String login, String password) throws ClassNotFoundException {
        database = new DataBase(login, password);
        spaceMarines=new SpaceMarines();
    }

    public static CommandManager getInstance(String login, String password) throws ClassNotFoundException {
        if (instance == null) {
            commands = new ArrayList<>();
            instance = new CommandManager(login,password);
        }

        return instance;
    }
    public static CommandManager getInstance() {
        if (instance == null) {
            commands = new ArrayList<>();
            instance = new CommandManager();
        }

        return instance;
    }
    /**
     * TODO: complete javadoc
     *
     * @param clazz
     * @param name
     * @param description
     * @throws CommandAlreadyExistsException В случае если команда с таким именем уже существует
     * @throws NoSuchMethodException В случае если класс команды будет не найден
     * @throws IllegalAccessException В случае если класс не доступен
     * @throws InvocationTargetException В случае если базовый конструкор выдает исключение
     * @throws InstantiationException В случае если указываемый класс абстрактный
     */
    public void initCommand(Class<? extends Command> clazz, String name, String description, Class<?>... argsTypes) throws CommandAlreadyExistsException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Command command : commands) {
            if (command.getName().equals(name)) {
                throw new CommandAlreadyExistsException("Команда с именем " + name + " уже существует");
            }
        }

        Command command = clazz.getConstructor(String.class, String.class, Class[].class)
                .newInstance(name, description, argsTypes);
        commands.add(command);
    }
    /**
     * Считывает параметры команды из строки
     *
     * @param args строка, которая содержит параметры команды
     * @return параметры команды
     */
    public static String[] parseArgs(String args) {
        String[] splitted = args.split(" ", 2);
        return splitted.length > 1 ? splitted[1].split(" ") : new String[]{};
    }

    /**
     * Считвыает имя команды из строки
     *
     * @param command строка, которая содержит имя команды
     * @return имя команды
     */
    public static String parseName(String command) {
        return command.split(" ")[0];
    }

    public static Command getCommand(String name) throws NotFoundCommandException {
        for (Command cmd : commands) {
            if (cmd.getName().equals(name)) {
                return cmd;
            }
        }
        throw new NotFoundCommandException("Команда '" + name + "' не была инициализирована.");
    }
    public static void updateCollection() {
        try {
            List<SpaceMarine> updated = database.selectAllNotes();
            spaceMarines.update(updated);
        } catch (SQLException  | NotDatabaseUpdateException e) {
            e.printStackTrace();
        }
    }

    public static String getCommandsInfo() {
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<commands.size()-1;i++) {
            builder.append(commands.get(i).toString()).append("\n");
        }

        return builder.toString().trim();
    }

    public static User getUser() {
        return user;
    }

    public static Object execute(User user, Command command, Object[] args) {
        return command.execute(user,database,spaceMarines, args);

    }

    public static Object execute(User user,String commandName, Object[] args) throws NotFoundCommandException {
        Command command = getCommand(commandName);
        return command.execute(user,database,spaceMarines, args);
    }

    public static Object[] getFillableArgs(Command command, Scanner scanner) {
        if (command instanceof Fillable) {
            return ((Fillable) command).fill(scanner);
        }
        return new Object[]{};
    }

    public static Object[] concatArgs(Object[] args, Object[] fillableArgs) {
        return Stream.concat(Arrays.stream(args), Arrays.stream(fillableArgs)).toArray(Object[]::new);
    }

    public static void validate(Command command, Object[] args) {
        command.validate(args);
    }

    public static void validate(String commandName, Object[] args) throws NotFoundCommandException {
        Command command = getCommand(commandName);
        command.validate(args);
    }
    public static void auth(User user) throws NotFoundCommandException {
        CommandManager.user = user;
    }
}
