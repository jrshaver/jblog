package org.launchcode.jblog.dao;

import org.launchcode.jblog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TagDao extends JpaRepository<Tag, Integer> {
}