package com.a65apps.architecturecomponents.sample.data.posts;

import com.a65apps.architecturecomponents.sample.domain.posts.PostState;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class NetworkToStateMapperTest {

    @Test
    public void mapTest() {
        NetworkToStateMapper mapper = new NetworkToStateMapper();
        PostsJson from = PostsJson.builder()
                .incompleteResults(false)
                .totalCount(1)
                .items(new ArrayList<PostJson>() {{
                    add(PostJson.builder()
                            .name("name")
                            .displayName("displayName")
                            .shortDescription("shortDescription")
                            .description("description")
                            .createdBy("createdBy")
                            .score(1.0)
                            .featured(true)
                            .curated(true)
                            .build());
                }})
                .build();

        List<PostState> to = mapper.map(from);
        assertThat(to.size(), equalTo(1));
        assertThat(to.get(0), equalTo(PostState.builder()
                .name("name")
                .displayName("displayName")
                .shortDescription("shortDescription")
                .description("description")
                .createdBy("createdBy")
                .score(1.0)
                .featured(true)
                .curated(true)
                .build()));
    }
}
