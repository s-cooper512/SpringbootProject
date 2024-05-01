package org.example.socialmediathing.test;

import org.example.socialmediathing.model.Tag;
import org.example.socialmediathing.repository.ITagRepository;
import org.example.socialmediathing.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @MockBean
    private ITagRepository tagRepository;
    final Tag TEST_TAG = new Tag(1L, "Name", "Description");

    @Test
    public void testGetAllTags_returnsAllTags() {
        // Mock data
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "Name 1", "Description 1"));
        tags.add(new Tag(2L, "Name 2", "Description 2"));

        // Stub the repository method to return the mock data
        when(tagRepository.findAll()).thenReturn(tags);

        // Call the service method
        List<Tag> result = tagService.getAllTags();

        // Verify the result
        assertEquals(tags.size(), result.size());
        assertEquals(tags.get(0).getName(), result.get(0).getName());
        assertEquals(tags.get(1).getName(), result.get(1).getName());
    }

    @Test
    public void testGetAllTags_fails() {
        // Stub the repository method to throw an exception when called
        given(tagRepository.findAll()).willThrow(new RuntimeException("Failed to retrieve tags"));

        // Verify that calling getAllTags method throws an exception
        assertThrows(RuntimeException.class, () -> tagService.getAllTags());
    }


    @Test
    public void testGetTagByID_validID_returnsTag() throws Exception {
        given(tagRepository.findById(TEST_TAG.getId())).willReturn(Optional.of(TEST_TAG));

        Tag example = tagService.getTagById(TEST_TAG.getId());
        assertNotNull(example);
        assertEquals(TEST_TAG.getId(), example.getId());
    }

    @Test
    public void testGetTagByID_invalidID_fails() {
        given(tagRepository.findById(TEST_TAG.getId())).willReturn(Optional.empty());

        assertThrows(Exception.class, () -> tagService.getTagById(TEST_TAG.getId()));
    }

    @Test
    public void testAddTag_Successful() {
        // Stub the repository method to return the saved tag
        given(tagRepository.save(TEST_TAG)).willReturn(TEST_TAG);

        // Call the service method
        Tag example = tagService.createTag(TEST_TAG);

        // Verify that the tag was saved successfully
        assertNotNull(example);
        assertEquals(TEST_TAG.getId(), example.getId());
        assertEquals(TEST_TAG.getName(), example.getName());
        assertEquals(TEST_TAG.getDescription(), example.getDescription());
    }

    @Test
    public void testAddTag_Unsuccessful() {
        // Stub the repository method to throw an exception when called
        given(tagRepository.save(TEST_TAG)).willThrow(new RuntimeException("Failed to add tag"));

        // Call the service method and verify that it throws an exception
        assertThrows(RuntimeException.class, () -> tagService.createTag(TEST_TAG));
    }

    @Test
    public void testDeleteTag_Successful() {
        // Call the service method
        assertDoesNotThrow(() -> tagService.deleteTag(TEST_TAG.getId()));

        // Verify that the delete method of the repository was called with the correct tagId
        verify(tagRepository, times(1)).deleteById(TEST_TAG.getId());
    }

    @Test
    public void testDeleteTag_Unsuccessful() {
        // Stub the repository method to throw an exception when called
        doThrow(new RuntimeException("Failed to delete tag")).when(tagRepository).deleteById(TEST_TAG.getId());

        // Call the service method and verify that it throws an exception
        assertThrows(RuntimeException.class, () -> tagService.deleteTag(TEST_TAG.getId()));
    }

    @Test
    public void testUpdateTag_Successful() throws Exception {
        // Mock data
        Tag updatedTag = new Tag(TEST_TAG.getId(), "Updated Name", "Updated Description");

        // Stub the repository method to return the existing tag
        when(tagRepository.findById(TEST_TAG.getId())).thenReturn(Optional.of(TEST_TAG));

        // Stub the repository method to return the updated tag
        when(tagRepository.save(any(Tag.class))).thenReturn(updatedTag);

        // Call the service method
        Tag result = tagService.updateTag(TEST_TAG.getId(), updatedTag);

        // Verify the result
        assertNotNull(result);
        assertEquals(updatedTag.getId(), result.getId());
        assertEquals(updatedTag.getName(), result.getName());
        assertEquals(updatedTag.getDescription(), result.getDescription());
    }

    @Test
    public void testUpdateTag_Unsuccessful_TagNotFound() {
        // Mock data
        Tag updatedTag = new Tag(TEST_TAG.getId(), "Updated Name", "Updated Description");

        // Stub the repository method to return an empty optional
        when(tagRepository.findById(TEST_TAG.getId())).thenReturn(Optional.empty());

        // Call the service method and verify that it throws an exception
        assertThrows(Exception.class, () -> tagService.updateTag(TEST_TAG.getId(), updatedTag));

        // Verify that the repository method save was not called
        verify(tagRepository, never()).save(any(Tag.class));
    }

}

