package org.launchcode.jblog.dao;

import org.launchcode.jblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostDao extends JpaRepository<Post, Integer> {
}