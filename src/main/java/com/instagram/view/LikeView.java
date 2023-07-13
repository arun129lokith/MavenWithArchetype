package com.instagram.view;

import com.instagram.controller.LikeController;
import com.instagram.model.Like;
import com.instagram.model.User;

import java.util.Collection;

/**
 * <p>
 * Provides user reaction for the post of the application
 * </p>
 *
 * @author Arun
 * @version 1.0
 */
public class LikeView extends CommonView {

    private final PostView postView;
    private final UserView userView;
    private static LikeView likeView = null;
    private final LikeController LIKE_CONTROLLER = LikeController.getInstance();
    private static Long id = 1L;

    private LikeView() {
        postView = PostView.getInstance();
        userView = UserView.getInstance();
    }

    /**
     * <p>
     * Gets the object of the like view class
     * </p>
     *
     * @return The like view object
     */
    public static LikeView getInstance() {
        return null == likeView ? likeView = new LikeView() : likeView;
    }

    /**
     * <p>
     * Displays the user reaction for the post of the application
     * </p>
     *
     * @param userId Represents id of the user
     */
    public void menu(final Long userId) {
        printMessage(String.join("","Click 1 To Like Post\nClick 2 To Unlike Post\nClick 3 To",
                "Get All User Liked The Post\nClick 4 To Get Like Count Of The Post\nClick 5 To Post Menu\nClick 6 TO",
                "User Menu"));

        if (exitAccess()) {
            postView.menu(userId);
        }

        switch (getChoice()) {
            case 1:
                likePost(userId);
                break;
            case 2:
                unlikePost();
                break;
            case 3:
                getLikedUser();
                break;
            case 4:
                getLikeCount();
                break;
            case 5:
                postView.menu(userId);
                break;
            case 6:
                userView.userScreen(userId);
                break;
            default:
                printMessage("Invalid Choice. Please Enter A Choice In Range[1-5]");
                break;
        }
        menu(userId);
    }

    /**
     * <p>
     * Creates the like for the post
     * </p>
     *
     * @param userId Represents id of the user
     */
    private void likePost(final Long userId) {
        final Like like = new Like();

        like.setId(idGenerator());
        like.setUserId(userId);
        like.setPostId(getPostId());

        LIKE_CONTROLLER.likePost(like);

        printMessage("Post Liked Successfully");
    }

    /**
     * <p>
     * Removes the like for the post provided by the user
     * </p>
     */
    private void unlikePost() {
        final Long id = getLikeId();

        printMessage(LIKE_CONTROLLER.unlikePost(id) ? "Like Removed Successfully" : "Like Not Found");
    }

    /**
     * <p>
     * Gets the collection of user who react for the post
     * </p>
     */
    private void getLikedUser() {
        final Collection<User> users = LIKE_CONTROLLER.getLikeUser(getPostId());

        if (null != users && !users.isEmpty()) {

            for (final User user : users) {
                printMessage(user.getName());
            }
        } else {
            printMessage("Post Not Liked By Any User");
        }
    }

    /**
     * <p>
     * Gets the count of the like for the post
     * </p>
     */
    private void getLikeCount() {
        final Long getCount = LIKE_CONTROLLER.getLikeCount(getPostId());

        System.out.println(null != getCount ? getCount : "Post Not Liked By Any User");
    }

    /**
     * <p>
     * Gets the like id for the post
     * </p>
     *
     * @return The id of the like
     */
    private Long getLikeId() {
        printMessage("Enter Your Like Id:");

        try {
            return Long.valueOf(scanner.nextLine());
        } catch (final NumberFormatException message) {
            printMessage("Invalid Like Id Format. Please Enter A Number");
        }

        return getLikeId();
    }

    /**
     * <p>
     * Gets the id of the post
     * </p>
     *
     * @return The post id
     */
    private Long getPostId() {
        final Long id = postView.getPostId();

        if (null == postController.getPost(id)) {
            printMessage("Post Not Found By The Id. Please Try Again");

            return getPostId();
        }

        return id;
    }

    /**
     * <p>
     * Generates id for the new like
     * </p>
     *
     * @return The like id
     */
    private Long idGenerator() {
        return id++;
    }
}
