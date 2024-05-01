package org.example.socialmediathing.service;

import org.example.socialmediathing.repository.ITagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.socialmediathing.model.Tag;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private ITagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTagById(Long tagId) throws Exception {
        return tagRepository.findById(tagId)
                .orElseThrow(Exception::new);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }

    public Tag updateTag(Long tagId, Tag tagDetails) throws Exception {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(Exception::new);

        tag.setName(tagDetails.getName());
        tag.setDescription(tag.getDescription());
        tag.setPosts(tag.getPosts());

        return tagRepository.save(tag);
    }
}

