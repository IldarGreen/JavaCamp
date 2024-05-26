package edu.school21.repositories;

import edu.school21.models.User;


public interface Repository {
    void update(Object entity);
    void save(Object entity);
    public <T> T findById(Long id, Class<T> aClass);

}
