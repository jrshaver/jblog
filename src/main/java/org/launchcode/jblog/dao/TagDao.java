package org.launchcode.jblog.dao;

import org.launchcode.jblog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TagDao extends JpaRepository<Tag, Integer> {
    Tag findByName(String name);
    List<Tag> findByNameContaining(String name);
}