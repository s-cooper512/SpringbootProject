package org.example.socialmediathing.test;

import org.example.socialmediathing.controller.TagController;
import org.example.socialmediathing.model.Tag;
import org.example.socialmediathing.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagControllerTest {
    @Autowired
    TagController tagController;

    @MockBean
    TagService tagService;

    final Tag TEST_TAG = new Tag(1L, "Name", "Description");

    @Test
    public void testGetAllTags_Successful () {
        // Mock data
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "Name 1", "Description 1"));
        tags.add(new Tag(2L, "Name 2", "Description 2"));

        when(tagService.getAllTags()).thenReturn(tags);

        ResponseEntity<List<Tag>> result = tagController.getAllTags();

        assertNotNull(result);
        assertEquals(tags.size(), Objects.requireNonNull(result.getBody()).size());
        assertEquals(tags.get(0).getName(), result.getBody().get(0).getName());
        assertEquals(tags.get(1).getName(), result.getBody().get(1).getName());
    }

    @Test
    public void testGetAllTags_Failure () {
        ResponseEntity<List<Tag>> responseEntity = tagController.getAllTags();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetTagByID_Successful () throws Exception {
        given(tagService.getTagById(TEST_TAG.getId())).willReturn(TEST_TAG);

        ResponseEntity<Tag> example = tagController.getTagById(TEST_TAG.getId());
        assertNotNull(example.getBody());
        assertEquals(TEST_TAG.getId(), example.getBody().getId());
        assertEquals(HttpStatus.OK, example.getStatusCode());
    }

    @Test
    public void testGetTagByID_throwsException () throws Exception {
        ResponseEntity<Tag> example = tagController.getTagById(TEST_TAG.getId());
        assertNull(example.getBody());
        assertEquals(HttpStatus.OK, example.getStatusCode());
    }

    @Test
    public void testAddTag_Successful () {
        when(tagService.createTag(TEST_TAG)).thenReturn(TEST_TAG);

        // Call the controller method
        ResponseEntity<Tag> responseEntity = tagController.createTag(TEST_TAG);

        // Verify the response status code
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verify the returned tag
        assertEquals(TEST_TAG, responseEntity.getBody());
    }

    @Test
    public void testAddTag_Unsuccessful() {
        ResponseEntity<Tag> responseEntity = tagController.createTag(TEST_TAG);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateTag_Successful() throws Exception {
        when(tagService.updateTag(TEST_TAG.getId(), TEST_TAG)).thenReturn(TEST_TAG);
        ResponseEntity<Tag> responseEntity = tagController.updateTag(TEST_TAG.getId(), TEST_TAG);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(TEST_TAG, responseEntity.getBody());
    }

    @Test
    public void testUpdateTag_Unsuccessful() throws Exception {
        ResponseEntity<Tag> responseEntity = tagController.updateTag(TEST_TAG.getId(), TEST_TAG);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteTag_Successful() throws Exception {
        ResponseEntity<?> responseEntity = tagController.deleteTag(TEST_TAG.getId());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteTag_Unsuccessful() throws Exception {
        ResponseEntity<?> responseEntity = tagController.deleteTag(TEST_TAG.getId());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
