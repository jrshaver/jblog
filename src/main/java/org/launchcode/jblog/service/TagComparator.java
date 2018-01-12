package org.launchcode.jblog.service;

import org.launchcode.jblog.models.Tag;

import java.util.Comparator;

public class TagComparator implements Comparator<Tag> {

    @Override
    public int compare(Tag o1, Tag o2) {
        return Integer.compare(o2.getPosts().size(), o1.getPosts().size());
    }
}
