package ru.job4j.map;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * UserMap class.
 */
public class UserMap {

    /**
     * User class.
     */
    private static class User {

        /**
         * Name of user.
         */
        private String name;

        /**
         * Amount of children.
         */
        private int children;

        /**
         * Date of born.
         */
        private Calendar birthday;

        /**
         * Constructor.
         *
         * @param name     name of user.
         * @param children amount of children.
         * @param birthday date of born.
         */
        User(String name, int children, Calendar birthday) {
            this.name = name;
            this.children = children;
            this.birthday = birthday;
        }

        /**
         * Overloaded method hashCode.
         * @return hashCode related from field of class user.
         */
        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + children;
            result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
            return result;
        }
    }

    /**
     * Main method.
     * @param args arguments.
     */
    public static void main(String[] args) {
        User firstUser = new User(
                "John", 2,  new GregorianCalendar(1896, Calendar.JULY, 14));
        User secondUser = new User(
                "John", 2,  new GregorianCalendar(1896, Calendar.JULY, 14));

        Map<User, Object> map = new HashMap<>();

        map.put(firstUser, "first user value");
        map.put(secondUser, "second user value");

        System.out.println(map);
    }
}
