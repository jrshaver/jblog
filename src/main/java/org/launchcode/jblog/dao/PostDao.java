package org.launchcode.jblog.dao;

import org.launchcode.jblog.models.Post;
import org.launchcode.jblog.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface PostDao extends PagingAndSortingRepository<Post, Integer>, JpaRepository<Post, Integer> {
    List<Post> findByTitleContaining(String title);
    List<Post> findByBodyContaining(String title);
}