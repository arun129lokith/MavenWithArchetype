package com.instagram.service.impl2;

import com.instagram.dao.PostDao;
import com.instagram.dao.impl.PostDaoImpl;
import com.instagram.model.Post;
import com.instagram.model.User;
import com.instagram.service.PostService;
import com.instagram.view.UserView;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Implements the service of the post related operation
 * </p>
 *
 * @author Arun
 * @version 1.1
 */
public class PostServiceImpl implements PostService {

    private static PostServiceImpl postServiceImpl = null;
    private  final PostDao postDao;

    private PostServiceImpl() {
        postDao = PostDaoImpl.getInstance();
    }

    /**
     * <p>
     * Gets the object of the class
     * </p>
     *
     * @return The post service implementation object
     */
    public static PostServiceImpl getInstance() {
        return null == postServiceImpl ? postServiceImpl = new PostServiceImpl() : postServiceImpl;
    }

    /**
     * {@inheritDoc}
     *
     * @param post Represents {@link Post} details of the user
     * @return True if post is created, false otherwise
     */
    @Override
    public boolean create(final Post post) {
        final UserView userView = UserView.getInstance();
        final User user = userView.getUserById(post.getUserId());
        final List<Post> posts = user.getPosts();

        posts.add(post);
        user.setPosts(posts);

        return postDao.create(post);
    }

    /**
     * {@inheritDoc}
     *
     * @return The collection of post
     */
    @Override
    public Collection<Post> getAllPost() {
        return postDao.getAllPost();
    }

    /**
     * {@inheritDoc}
     *
     * @param id Represents post id
     * @return Represents {@link Post} details
     */
    @Override
    public Post getPost(final Long id) {
        return postDao.getPost(id);
    }

    /**
     * {@inheritDoc}
     *
     * @param id Represents post id
     * @return True if post is removed, false otherwise
     */
    @Override
    public boolean delete(final Long id) {
        return postDao.delete(id);
    }

    /**
     * {@inheritDoc}
     *
     * @param updatedPost Represents {@link Post} update details
     * @return True if post is updated, false otherwise
     */
    @Override
    public boolean update(final Post updatedPost) {
        return postDao.update(updatedPost);
    }

    /**
     * {@inheritDoc}
     *
     * @param userId Represents id of the user
     * @param id Represents post id
     * @return The post details of the user
     */
    @Override
    public Post getPost(final Long id, final Long userId) {
        return postDao.getPost(id, userId);
    }
}
