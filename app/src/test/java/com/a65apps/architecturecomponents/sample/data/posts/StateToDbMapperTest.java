package com.a65apps.architecturecomponents.sample.data.posts;

import com.a65apps.architecturecomponents.sample.domain.posts.PostState;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StateToDbMapperTest {

    @Test
    public void mapTest() {
        PostState from = PostState.builder()
                .name("name")
                .displayName("displayName")
                .shortDescription("shortDescription")
                .description("description")
                .createdBy("createdBy")
                .score(1.0)
                .featured(true)
                .curated(true)
                .build();
        StateToDbMapper mapper = new StateToDbMapper();

        PostDb to = mapper.map(from);

        assertThat(to, equalTo(new PostDb("name",
                "displayName",
                "shortDescription",
                "description",
                "createdBy",
                1.0,
                true,
                true)));
    }
}
