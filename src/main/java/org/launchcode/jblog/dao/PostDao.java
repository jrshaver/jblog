package org.launchcode.jblog.dao;

import org.launchcode.jblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PostDao extends JpaRepository<Post, Integer> {
}