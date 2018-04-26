package ru.job4j.parser.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.parser.model.Vacancy;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * The class VacancyDaoImpl is an implementation of the VacancyDao interface.
 */
public class VacancyDaoImpl implements VacancyDao {

    /**
     * The logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(VacancyDaoImpl.class);

    /**
     * The SQL line for adding a vacancy to the db.
     */
    private static final String INSERT = "INSERT INTO Vacancies "
            + "(id, description, date, link) "
            + "VALUES (?, ?, ?, ?) ON CONFLICT (id) DO NOTHING";

    /**
     * The SQL line for removing a vacancy from the db.
     */
    private static final String DELETE = "DELETE FROM Vacancies WHERE Vacancies.id = ?;";

    /**
     * The SQL line for finding an vacancy into the db by the id.
     */
    private static final String FIND_BY_ID = "SELECT "
            + "id, description, date, link FROM Vacancies WHERE id = ?;";

    /**
     * The SQL line for finding all vacancies into the db.
     */
    private static final String SELECT_ALL = "SELECT "
            + "Vacancies.id AS id, Vacancies.description AS description, "
            + "Vacancies.date AS date, Vacancies.link AS link "
            + "FROM Vacancies;";

    /**
     * The SQL line for finding date of the lust update.
     */
    private static final String SELECT_LUST_UPDATE = "SELECT Updates.date "
            + "FROM Updates WHERE Updates.attempt = 1;";

    /**
     * The SQL line for updating date of the lust update.
     */
    private static final String UPDATE_LUST_UPDATE = "UPDATE Updates SET date = ? WHERE attempt = 1;";

    @Override
    public void saveVacancies(Connection connection, BlockingQueue<Vacancy> vacancies) {
        try (PreparedStatement st = connection.prepareStatement(INSERT)) {
            for (Vacancy vacancy : vacancies) {
                st.setInt(1, vacancy.getId());
                st.setString(2, vacancy.getDescription());
                st.setTimestamp(3, vacancy.getDate());
                st.setString(4, vacancy.getLink());
                st.execute();
            }
        } catch (SQLException e) {
            LOG.error(e.getSQLState(), e);
        }
    }

    @Override
    public boolean deleteVacancyById(Connection connection, int id) {
        boolean result = false;
        try (PreparedStatement st = connection.prepareStatement(DELETE)) {
            st.setInt(1, id);
            result = st.execute();
        } catch (SQLException e) {
            LOG.error(e.getSQLState(), e);
        }
        return result;
    }

    @Override
    public Vacancy findItemById(Connection connection, int id) {
        Vacancy vacancy = new Vacancy();
        try (PreparedStatement st = connection.prepareStatement(FIND_BY_ID)) {
            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                vacancy.setId(resultSet.getInt("id"));
                vacancy.setDescription(resultSet.getString("description"));
                vacancy.setDate(resultSet.getTimestamp("date"));
                vacancy.setLink(resultSet.getString("link"));
            }
        } catch (SQLException e) {
            LOG.error(e.getSQLState(), e);
        }
        return vacancy;
    }

    @Override
    public List<Vacancy> showAllVacancy(Connection connection) {
        List<Vacancy> list = new LinkedList<>();
        try (PreparedStatement st = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                Vacancy vacancy = new Vacancy();
                vacancy.setId(resultSet.getInt("id"));
                vacancy.setDescription(resultSet.getString("description"));
                vacancy.setDate(resultSet.getTimestamp("date"));
                vacancy.setLink(resultSet.getString("link"));
                list.add(vacancy);
            }
        } catch (SQLException e) {
            LOG.error(e.getSQLState(), e);
        }
        return list;
    }

    @Override
    public Timestamp getLastUpdate(Connection connection) {
        Timestamp result = null;
        try (PreparedStatement st = connection.prepareStatement(SELECT_LUST_UPDATE)) {
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getTimestamp("date");
            }
        } catch (SQLException e) {
            LOG.error(e.getSQLState(), e);
        }
        return result;
    }

    /**
     * The method updates the row with id = 1, it sets the date of the lust update into it.
     * @param connection to db.
     * @param time of the lust update.
     */
    @Override
    public void updateLustTimeUpdate(Connection connection, Timestamp time) {
        try (PreparedStatement st = connection.prepareStatement(UPDATE_LUST_UPDATE)) {
            st.setTimestamp(1, time);
            st.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getSQLState(), e);
        }
    }

}
