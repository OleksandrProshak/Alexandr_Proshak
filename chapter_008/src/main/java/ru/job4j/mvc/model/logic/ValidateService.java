package ru.job4j.mvc.model.logic;

import ru.job4j.mvc.model.entity.City;
import ru.job4j.mvc.model.entity.Country;
import ru.job4j.mvc.model.entity.User;

import java.util.Collection;

/**
 * The interface ValidateService provides base CRUD operation with validation of correct date.
 */
public interface ValidateService {

    /**
     * Adds user in case of absence it in the storage.
     * @param user to add.
     * @return true in case of success, or false otherwise.
     */
    boolean add(User user);

    /**
     * Updates user in case of present it in the storage.
     * @param user to update.
     * @return true in case of success, or false otherwise.
     */
    boolean update(User user);

    /**
     * Deletes user in case of present it in the storage.
     * @param id of user to remove.
     * @return true in case of success, or false otherwise.
     */
    boolean delete(int id);

    /**
     * Returns all users from a storage as collection of user.
     * @return collection of users.
     */
    Collection<User> findAll();

    /**
     * Returns user in case of present it in the storage.
     * @param id of user to search.
     * @return user or null in case of absent..
     */
    User findById(int id);

    /**
     * The method check user's data.
     * @param login of user.
     * @param password of user.
     * @return user if it exist, or null - otherwise.
     */
    User isCredential(String login, String password);

    /**
     * Finds all countries into a storage.
     * @return a collection with countries.
     */
    Collection<Country> findAllCountries();

    /**
     * Finds all cities into a storage.
     * @param countryName country's name.
     * @return a collection with cities.
     */
    Collection<City> findAllCitiesByCountry(String countryName);
}