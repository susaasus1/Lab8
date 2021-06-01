package Commands;

import Data.Chapter;
import Data.Coordinates;
import Data.SpaceMarine;
import Data.User;
import Exceptions.*;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class DataBase {
    private Connection connection;
    private Statement statement;

    public DataBase(String login, String password) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/Laba", login, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int insert(User user) throws SQLException, NotDatabaseUpdateException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into users (login, password) values (?, ?) returning id");
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());

        try {
            if (preparedStatement.execute()) {
                ResultSet rs = preparedStatement.getResultSet();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            throw new NotDatabaseUpdateException("Объект user не был добавлен");
        } catch (PSQLException e) {
            System.out.println(e.getMessage());
            throw new NotDatabaseUpdateException("Объект user не был добавлен");
        }
    }

    private int insert(Coordinates coords) throws SQLException, NotDatabaseUpdateException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into coordinates (x, y) values (?, ?) returning id");
        preparedStatement.setDouble(1, coords.getX());
        preparedStatement.setFloat(2, coords.getY());

        try {
            if (preparedStatement.execute()) {
                ResultSet rs = preparedStatement.getResultSet();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            throw new NotDatabaseUpdateException("Объект coordinates не был добавлен");
        } catch (PSQLException e) {
            System.out.println(e.getMessage());
            throw new NotDatabaseUpdateException("Объект coordinates не был добавлен");
        }
    }

    private int insert(Chapter chapter) throws SQLException, NotDatabaseUpdateException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into chapters (name, parentlegion, marinescount, world) values (?, ?, ?, ?) returning id");
        preparedStatement.setString(1, chapter.getName());
        preparedStatement.setString(2, chapter.getParentLegion());
        preparedStatement.setInt(3, chapter.getMarinesCount());
        preparedStatement.setString(4, chapter.getWorld());

        try {
            if (preparedStatement.execute()) {
                ResultSet rs = preparedStatement.getResultSet();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            throw new NotDatabaseUpdateException("Объект chapter не был добавлен");
        } catch (PSQLException e) {
            System.out.println(e.getMessage());
            throw new NotDatabaseUpdateException("Объект chapter не был добавлен");
        }
    }
    public int isertName(String name,int userId)throws SQLException, NotDatabaseUpdateException{
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into spacemarines (name) values (?) returning id");
        preparedStatement.setString(1, name);
        try {
            if (preparedStatement.execute()) {
                ResultSet rs = preparedStatement.getResultSet();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            throw new NotDatabaseUpdateException("Объект Name не был добавлен");
        } catch (PSQLException e) {
            System.out.println(e.getMessage());
            throw new NotDatabaseUpdateException("Объект Name не был добавлен");
        }
    }
    public int insert(SpaceMarine spaceMarine, int userID) throws SQLException, NotDatabaseUpdateException {
        int coordsID = insert(spaceMarine.getCoordinates());
        int chapterID = insert(spaceMarine.getChapter());
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into spacemarines (name, coordinates_id, creationdate,health, height, category, weapontype, chapter_id, user_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?) returning id");
        preparedStatement.setString(1, spaceMarine.getName());
        preparedStatement.setInt(2, coordsID);
        preparedStatement.setDate(3, Date.valueOf(spaceMarine.getCreationDate()));
        preparedStatement.setFloat(4, spaceMarine.getHealth());
        preparedStatement.setDouble(5, spaceMarine.getHeight());
        preparedStatement.setString(6, spaceMarine.getCategory().toString());
        preparedStatement.setString(7, spaceMarine.getWeaponType().toString());
        preparedStatement.setInt(8, chapterID);
        preparedStatement.setInt(9, userID);

        try {
            if (preparedStatement.execute()) {
                ResultSet rs = preparedStatement.getResultSet();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            throw new NotDatabaseUpdateException("Объект spaceMarine не был добавлен");
        } catch (PSQLException e) {
            System.err.println(e.getMessage());
            throw new NotDatabaseUpdateException("Объект spaceMarine не был добавлен");
        }

    }

    public int selectUserID(String login, String password) throws SQLException, UserNotFoundException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from users where login=? and password=?");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        if (preparedStatement.execute()) {
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }

        throw new UserNotFoundException("Пользователь с таким логином или паролем не найден");
    }

    public Chapter selectChapter(int id) throws SQLException, ChapterNotFoundException, FiledIncorrect {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from chapters where id=?");
        preparedStatement.setInt(1, id);
        if (preparedStatement.execute()) {
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                String name = rs.getString("name");
                String parentlegion = rs.getString("parentLegion");
                Integer marinesCount = rs.getInt("marinescount");
                String world = rs.getString("world");
                return new Chapter(name, parentlegion, marinesCount, world);
            }
        }

        throw new ChapterNotFoundException("Нет chapter с id = " + id);
    }

    public User selectUser(int id) throws SQLException, UserNotFoundException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id=?");
        preparedStatement.setInt(1, id);
        if (preparedStatement.execute()) {
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                String login = rs.getString("login");
                String password = rs.getString("password");
                return new User(login, password);
            }
        }

        throw new UserNotFoundException("Нет пользователя с id = " + id);
    }

    public Coordinates selectCoordinates(int id) throws SQLException, CoordinatesNotFound {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from coordinates where id=?");
        preparedStatement.setInt(1, id);
        if (preparedStatement.execute()) {
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                float x = rs.getFloat("x");
                int y = rs.getInt("y");
                return new Coordinates(x, y);
            }
        }

        throw new CoordinatesNotFound("Нет пользователя с id = " + id);
    }

    public SpaceMarine selectSpaceMarine(Long id) throws SQLException, SpaceMarineNotFound {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from spacemarines where id=?");
        preparedStatement.setLong(1, id);
        if (preparedStatement.execute()) {
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                String name = rs.getString("name");
                int coords_id = rs.getInt("coordinates_id");
                LocalDate creationDate = rs.getDate("creationdate").toLocalDate();
                Float health = rs.getFloat("health");
                double height = rs.getDouble("height");
                String category = rs.getString("category");
                String weaponType = rs.getString("weaponType");
                int chapter_id = rs.getInt("chapter_id");
                int user_id = rs.getInt("user_id");

                try {
                    Coordinates coordinates = selectCoordinates(coords_id);
                    Chapter chapter = selectChapter(chapter_id);
                    User user = selectUser(user_id);
                    return new SpaceMarine(id, name, coordinates, creationDate, health, height, category, weaponType, chapter, user);
                } catch (ChapterNotFoundException | CoordinatesNotFound | UserNotFoundException | FiledIncorrect e) {
                    throw new SpaceMarineNotFound("Ошибка считывания подполей класса person");
                }
            }
        }

        throw new SpaceMarineNotFound("Нет person с id = " + id);
    }

    public List<SpaceMarine> selectAllNotes() throws SQLException, NotDatabaseUpdateException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from spacemarines ");

        if (preparedStatement.execute()) {

            ResultSet rs = preparedStatement.getResultSet();
            List<SpaceMarine> spaceMarines = new LinkedList<>();
            while (rs.next()) {

                Long id = rs.getLong("id");
                String name = rs.getString("name");
                int coords_id = rs.getInt("coordinates_id");
                LocalDate creationDate = rs.getDate("creationdate").toLocalDate();
                Float health = rs.getFloat("health");
                double height = rs.getDouble("height");
                String category = rs.getString("category");
                String weaponType = rs.getString("weaponType");
                int chapter_id = rs.getInt("chapter_id");
                int user_id = rs.getInt("user_id");

                try {

                    Coordinates coordinates = selectCoordinates(coords_id);

                    Chapter chapter = selectChapter(chapter_id);
                    User user = selectUser(user_id);

                    SpaceMarine spaceMarine = new SpaceMarine(id, name, coordinates, creationDate, health, height, category, weaponType, chapter, user);
                    spaceMarines.add(spaceMarine);

                } catch (ChapterNotFoundException | CoordinatesNotFound | UserNotFoundException e) {
                    throw new NotDatabaseUpdateException("Ошибка в обновлнии коллекции");
                } catch (FiledIncorrect filedIncorrect) {
                    filedIncorrect.printStackTrace();
                }
            }
            return spaceMarines;
        }
        throw new NotDatabaseUpdateException("Ошибка в обновлнии коллекции");
    }

    public void update(Long id, SpaceMarine spaceMarine) throws SQLException, NotDatabaseUpdateException {
        PreparedStatement preparedStatement = connection.prepareStatement("update spacemarines set " +
                "(name, coordinates_id, creationdate, health, height, category, weapontype, chapter_id) " +
                "= (?, ?, ?, ?, ?, ?, ?, ?) where id=?");
        preparedStatement.setString(1, spaceMarine.getName());
        preparedStatement.setInt(2, insert(spaceMarine.getCoordinates()));
        preparedStatement.setDate(3, Date.valueOf(spaceMarine.getCreationDate()));
        preparedStatement.setFloat(4, spaceMarine.getHealth());
        preparedStatement.setDouble(5, spaceMarine.getHeight());
        preparedStatement.setString(6, spaceMarine.getCategory().toString());
        preparedStatement.setString(7, spaceMarine.getWeaponType().toString());
        preparedStatement.setInt(8, insert(spaceMarine.getChapter()));
        preparedStatement.setLong(9, id);
        preparedStatement.execute();
    }
    public void updateName(Long id,String name) throws SQLException,NotDatabaseUpdateException{
        PreparedStatement preparedStatement = connection.prepareStatement("update spacemarines set " +
                "(name) " +
                "= (?) where id=?");
        preparedStatement.setString(1,name);
        preparedStatement.setLong(2, id);
        preparedStatement.execute();
    }
    public void deleteUserNotes(int userID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from spacemarines where user_id=?");
        preparedStatement.setInt(1, userID);
        preparedStatement.execute();
    }

    public void deleteNote(Long ID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from spacemarines where id=?");
        preparedStatement.setLong(1, ID);
        preparedStatement.execute();
    }

}
