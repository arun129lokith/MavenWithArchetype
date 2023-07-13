package com.instagram.view;

import com.instagram.controller.AuthenticationController;
import com.instagram.controller.PostController;
import com.instagram.controller.UserController;
import com.instagram.database.DataBaseConnectionPool;
import com.instagram.view.validation.CommonValidation;

import java.util.Scanner;

/**
 * <p>
 * Represents the commonly used field and methods in the application
 * </p>
 *
 * @author Arun
 * @version 1.0
 */
public class CommonView {

    protected final Scanner scanner = ScannerInstance.getInstance();
    protected final CommonValidation validation = CommonValidation.getInstance();
    protected final AuthenticationController authenticationController = AuthenticationController.getInstance();
    protected final PostController postController = PostController.getInstance();
    protected final UserController userController = UserController.getInstance();
    protected final DataBaseConnectionPool connection = DataBaseConnectionPool.getInstance();

    /**
     * <p>
     * Gets the valid choice from the user.
     * </p>
     *
     * @return The choice of the user.
     */
    protected int getChoice() {
        System.out.println("Enter Your Choice:");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (final NumberFormatException message) {
            System.out.println("Invalid Choice. Please Enter An Integer");
        }

        return getChoice();
    }

    /**
     * <p>
     * Checks the process to continue or exit.
     * </p>
     *
     * @return True if exit the process, false otherwise.
     */
    protected boolean exitAccess() {
        System.out.println(String.join(" ","Do You Want To Continue The Process Press 'Any Key Or",
                "Word' Else Press 'N Key Or No Word' For Exit The Process\nEnter Your Message For Continue Or Exit:"));

        return validation.continueOrExit(scanner.nextLine());
    }

    /**
     * <p>
     * Prints the information to the user
     * </p>
     *
     * @param message Represents information of the process
     */
    protected void printMessage(final String message) {
        System.out.println(message);
    }
}
