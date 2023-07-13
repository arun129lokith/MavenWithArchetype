package com.instagram.service;

import com.instagram.model.Post;

import java.util.Collection;

/**
 * <p>
 * Provides post service for the user
 * </p>
 *
 * @author Arun
 * @version 1.0
 */
public interface PostService {

    /**
     * <p>
     * Creates the user post
     * </p>
     *
     * @param post Represents {@link Post} details of the user
     */
    boolean create(final Post post);

    /**
     * <p>
     * Gets the all post of the user
     * </p>
     *
     * @return The collection of post
     */
    Collection<Post> getAllPost();

    /**
     * <p>
     * Gets the post detail of the user
     * </p>
     *
     * @param id Represents post id
     * @return The post details of the user
     */
    Post getPost(final Long id);

    /**
     * <p>
     * Deletes the user post
     * </p>
     *
     * @param id Represents post id
     * @return True if post is deleted, false otherwise
     */
    boolean delete(final Long id);

    /**
     * <p>
     * Updates the user post details
     * </p>
     *
     * @param updatedPost Represents {@link Post} update details
     * @return True if post is updated, false otherwise
     */
    boolean update(final Post updatedPost);

    /**
     * <p>
     * Gets the post of the user, if the post is posted by the user
     * </p>
     *
     * @param userId Represents id of the user
     * @param id Represents post id
     * @return The post details of the user
     */
    Post getPost(final Long id, final Long userId);
}
