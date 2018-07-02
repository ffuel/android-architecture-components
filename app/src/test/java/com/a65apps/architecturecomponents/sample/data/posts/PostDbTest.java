package com.a65apps.architecturecomponents.sample.data.posts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class PostDbTest {

    @Test
    public void equalsTest() {
        PostDb x = new PostDb("name",
                "displayName",
                "shortDescription",
                "description",
                "createdBy",
                1.0,
                true,
                true);
        PostDb y = new PostDb("name",
                "displayName",
                "shortDescription",
                "description",
                "createdBy",
                1.0,
                true,
                true);
        PostDb z = new PostDb("name",
                "displayName",
                "shortDescription",
                "description",
                "createdBy",
                1.0,
                true,
                true);

        assertTrue(x.equals(y));
        assertTrue(y.equals(x));
        assertTrue(y.equals(z));
        assertTrue(x.equals(z));
        assertTrue(x.equals(y));
    }

    @Test
    public void hashCodeTest() {
        PostDb x = new PostDb("name",
                "displayName",
                "shortDescription",
                "description",
                "createdBy",
                1.0,
                true,
                true);
        PostDb y = new PostDb("name",
                "displayName",
                "shortDescription",
                "description",
                "createdBy",
                1.0,
                true,
                true);
        assertTrue(x.hashCode() == y.hashCode());
        assertTrue(y.hashCode() == x.hashCode());
        assertTrue(x.equals(y));
        assertTrue(y.equals(x));
    }
}
