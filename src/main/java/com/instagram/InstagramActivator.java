package com.instagram;

import com.instagram.customexception.FileAccessException;
import com.instagram.view.AuthenticationView;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

/**
 * <p>
 * Activates the features of the instagram application
 * </p>
 *
 * @author Arun
 * @version 1.0
 */
public class InstagramActivator {

    protected static final Properties PROPERTIES = new Properties();
    protected static FileInputStream file;

    static {
        try {
            file = new FileInputStream("C:\\Users\\arunl\\InstagramMaven\\DataBaseResource\\db.properties");

            PROPERTIES.load(file);
        } catch (IOException message) {
            throw new FileAccessException(message.getMessage());
        }
    }

    /**
     * <p>
     * Starts the execution of the application
     * </p>
     *
     * @param args Represents command line arguments
     */
    public static void main(String[] args) {
        AuthenticationView.getInstance().menu();
    }
}