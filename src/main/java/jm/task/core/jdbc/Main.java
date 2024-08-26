package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
//        Создание таблицы User(ов)
        userService.createUsersTable();
//        Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль (User с именем — name добавлен в базу данных)
        userService.saveUser("Petr", "Ivanov", (byte) 24);
        userService.saveUser("Andrey", "Belov>", (byte) 24);
        userService.saveUser("Oleg", "Markov", (byte) 24);
        userService.saveUser("Ignat", "Sidorov", (byte) 24);
//        Получение всех User из базы и вывод в консоль (должен быть переопределен toString в классе User)
        List<User> listUsers = userService.getAllUsers();
        listUsers.forEach(v -> System.out.println(v.toString()));
//        Очистка таблицы User(ов)
        userService.cleanUsersTable();
//        Удаление таблицы
        userService.dropUsersTable();
    }
}