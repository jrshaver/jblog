package org.launchcode.jblog.service;

import org.launchcode.jblog.dao.TagDao;
import org.launchcode.jblog.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public Tag findById(int id) {
        return tagDao.findOne(id);
    }

    @Override
    public List<Tag> findTop5() {
        //sort by number of posts
        List<Tag> allTags = tagDao.findAll();
        TagComparator tagComparator = new TagComparator();
        allTags.sort(tagComparator);
        List<Tag> topTags = allTags.subList(0,4);
        //return first 5
        return topTags;

    }
}
