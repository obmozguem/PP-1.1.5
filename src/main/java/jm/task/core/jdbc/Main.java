package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            UserServiceImpl userServiceImpl = new UserServiceImpl();
            userServiceImpl.createUsersTable();
            userServiceImpl.saveUser("Olga", "Alikuleva", (byte) 36);
            userServiceImpl.saveUser("Lyudmila", "Askerova", (byte) 66);
            userServiceImpl.saveUser("Ahad", "Askerov", (byte) 69);
            userServiceImpl.saveUser("Elmir", "Askerov", (byte) 5);
            System.out.println(userServiceImpl.getAllUsers());
            userServiceImpl.cleanUsersTable();
            userServiceImpl.dropUsersTable();
        } finally {
            Util.closeFactory();
        }
    }
}
