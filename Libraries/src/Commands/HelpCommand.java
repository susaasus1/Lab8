package Commands;


import Data.SpaceMarines;
import Data.User;

public class HelpCommand extends Command {
    public HelpCommand(String name, String description, Class<?>[] argsTypes) {
        super(name, description, argsTypes);
    }

    @Override
    public String execute(User user,DataBase db,SpaceMarines spaceMarines, Object... args) {
        try {
            validate(args);
            return CommandManager.getCommandsInfo();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}