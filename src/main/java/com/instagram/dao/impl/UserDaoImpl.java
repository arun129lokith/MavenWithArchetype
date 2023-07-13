package com.instagram.dao.impl;

import com.instagram.dao.UserDao;
import com.instagram.database.DataBaseConnectionPool;
import com.instagram.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Collection;
import java.util.LinkedList;

/**
 * <p>
 * Implements the data base service of the user related operation
 * </p>
 *
 * @author Arun
 * @version 1.1
 */
public class UserDaoImpl implements UserDao {

    private static UserDaoImpl userDaoImpl = null;
    private final DataBaseConnectionPool connectionPool;

    private UserDaoImpl() {
        connectionPool = DataBaseConnectionPool.getInstance();
    }

    /**
     * <p>
     * Gets the object of the class
     * </p>
     *
     * @return The user database service implementation object
     */
    public static UserDaoImpl getInstance() {
        return null == userDaoImpl ? userDaoImpl = new UserDaoImpl() : userDaoImpl;
    }

    /**
     * {@inheritDoc}
     *
     * @param id Represents user id
     * @return The user details
     */
    @Override
    public User getUser(final Long id) {
        final String query = "SELECT * FROM USERS WHERE ID = ?";

        try (final Connection connection = connectionPool.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                final User user = new User();

                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setEmail(resultSet.getString("EMAIL"));
                user.setMobileNumber(resultSet.getString("MOBILE_NUMBER"));
                user.setPassword(resultSet.getString("PASSWORD"));

                connectionPool.releaseConnection(connection);

                return user;
            }
        } catch (final SQLException | InterruptedException message) {
            System.out.println(message.getMessage());
        }

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @return The collection of user details
     */
    @Override
    public Collection<User> getAllUsers() {
        final Collection<User> users = new LinkedList<>();
        final String query = "SELECT * FROM USERS";

        try (final Connection connection = connectionPool.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final User user = new User();

                user.setPassword(resultSet.getString("PASSWORD"));
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setMobileNumber(resultSet.getString("MOBILE_NUMBER"));
                user.setEmail(resultSet.getString("EMAIL"));
                users.add(user);
            }
            connectionPool.releaseConnection(connection);
        } catch (final SQLException | InterruptedException message) {
            System.out.println(message.getMessage());
        }

        return users;
    }

    /**
     * {@inheritDoc}
     *
     * @param user Represents {@link User} details
     * @return True if update is successful, false otherwise
     */
    @Override
    public boolean update(final User user) {
        final String query = "UPDATE USERS SET NAME = ?, MOBILE_NUMBER = ?, EMAIL = ?, PASSWORD = ? WHERE ID = ?";

        try (final Connection connection = connectionPool.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getMobileNumber());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setLong(5, user.getId());
            int rowUpdated = preparedStatement.executeUpdate();

            connectionPool.releaseConnection(connection);

            if (0 < rowUpdated) {
                return true;
            }
        } catch (final SQLException | InterruptedException message) {
            System.out.println(message.getMessage());
        }

        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param id Represents user id
     * @return True if account is deleted, false otherwise
     */
    @Override
    public boolean delete(final Long id) {
        final String query = "DELETE FROM USERS WHERE ID = ?";

        try (final Connection connection = connectionPool.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            int rowDeleted = preparedStatement.executeUpdate();

            connectionPool.releaseConnection(connection);

            if (0 < rowDeleted) {
                return true;
            }
        } catch (final SQLException | InterruptedException message) {
            System.out.println(message.getMessage());
        }

        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param user Represents {@link User} detail
     * @return Users id
     */
    @Override
    public Long getId(final User user) {
        final String query = "SELECT ID FROM USERS WHERE EMAIL = ? OR MOBILE_NUMBER = ?";

        try (final Connection connection = connectionPool.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getMobileNumber());
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                connectionPool.releaseConnection(connection);

                return resultSet.getLong("ID");
            }
        } catch (final SQLException | InterruptedException message) {
            System.out.println(message.getMessage());
        }

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param name Represents user name
     * @return True if name is exists, false otherwise
     */
    @Override
    public boolean isNameExist(final String name) {
        final String query = "SELECT * FROM USERS WHERE NAME = ?";

        try (final Connection connection = connectionPool.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            final ResultSet resultSet = preparedStatement.executeQuery();

            connectionPool.releaseConnection(connection);

            return resultSet.next();
        } catch (final SQLException | InterruptedException message) {
            System.out.println(message.getMessage());
        }

        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param email Represents user email
     * @return True if email is exists, false otherwise
     */
    @Override
    public boolean isEmailExist(final String email) {
        final String query = "SELECT * FROM USERS WHERE EMAIL = ?";

        try (final Connection connection = connectionPool.get();
             final PreparedStatement checkStatement = connection.prepareStatement(query)) {

            checkStatement.setString(1, email);
            final ResultSet resultSet = checkStatement.executeQuery();

            connectionPool.releaseConnection(connection);

            return resultSet.next();
        } catch (final SQLException | InterruptedException message) {
            System.out.println(message.getMessage());
        }

        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param mobileNumber Represents user mobile number
     * @return True if mobile number is exists, false otherwise
     */
    @Override
    public boolean isMobileNumberExist(final String mobileNumber) {
        final String query = "SELECT * FROM USERS WHERE MOBILE_NUMBER = ?";

        try (final Connection connection = connectionPool.get();
             final PreparedStatement checkStatement = connection.prepareStatement(query)) {

            checkStatement.setString(1, mobileNumber);
            final ResultSet resultSet = checkStatement.executeQuery();

            connectionPool.releaseConnection(connection);

            return resultSet.next();
        } catch (final SQLException | InterruptedException message) {
            System.out.println(message.getMessage());
        }

        return true;
    }
}
