package com.hobos.freeter.post;

import com.hobos.freeter.member.Member;
import com.hobos.freeter.member.SignupDTO;
import com.hobos.freeter.member.SignupService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostDeleteServiceTest {

    @Autowired
    private PostCreateService postCreateService;

    @Autowired
    PostUpdateService postUpdateService;

    @Autowired
    PostDeleteService postDeleteService;

    @Autowired
    private SignupService signupService;

    @Autowired
    private PostReadService postReadService;

    @Test
    void delete() {
        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("1234").build();
        Member member = signupService.signup(dto);

        PostCreateRequest request = PostCreateRequest.builder().title("title").content("content").build();

        Post post = postCreateService.create(member.getUserId(), request);


        Post found = postReadService.getOne(post.getId());

        assertEquals(post, found);

        Post deleted = postDeleteService.delete(post.getId());

        assertNotNull(deleted.getDeletedAt());

    }

    @Test()
    @DisplayName("없는 포스트")
    void notFound() {
        assertThrows(PostNotFoundException.class, () -> postDeleteService.delete(1L));
    }
}