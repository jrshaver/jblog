package org.launchcode.jblog.service;

import org.launchcode.jblog.models.Tag;

import java.util.List;

public interface TagService {

    List<Tag> findAll();
    Tag findById(int id);
    List<Tag> findTop5();
}
