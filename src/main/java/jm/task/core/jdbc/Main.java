package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl dao = new UserDaoJDBCImpl();

//      Создание таблицы User(ов)
        dao.createUsersTable();

//        Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
        dao.saveUser("Tom", "Tomson", (byte) 20);
        System.out.printf("User с именем – %s добавлен в базу данных\n", dao.getAllUsers().get(0).getName());
        dao.saveUser("Sam", "Samson", (byte) 30);
        System.out.printf("User с именем – %s добавлен в базу данных\n", dao.getAllUsers().get(1).getName());
        dao.saveUser("Jack", "Jackson", (byte) 40);
        System.out.printf("User с именем – %s добавлен в базу данных\n", dao.getAllUsers().get(2).getName());
        dao.saveUser("John", "Johnson", (byte) 50);
        System.out.printf("User с именем – %s добавлен в базу данных\n", dao.getAllUsers().get(3).getName());

//        Получение всех User из базы и вывод в консоль (должен быть переопределен toString в классе User)
        dao.getAllUsers().forEach(System.out::println);

//        Очистка таблицы User(ов)
        dao.cleanUsersTable();

//        Удаление таблицы
        dao.dropUsersTable();
    }
}
