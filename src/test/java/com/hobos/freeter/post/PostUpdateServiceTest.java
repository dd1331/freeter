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
class PostUpdateServiceTest {

    @Autowired
    private PostCreateService postCreateService;

    @Autowired
    PostUpdateService postUpdateService;

    @Autowired
    private SignupService signupService;

    @Test
    void update() {
        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("1234").build();
        Member member = signupService.signup(dto);

        PostCreateRequest request = PostCreateRequest.builder().title("title").content("content").build();

        Post post = postCreateService.create(member.getUserId(), request);

        UpdatePostDto updateDto = UpdatePostDto.builder().postId(post.getId()).title("updated title").content("updated content").build();
        Post updated = postUpdateService.update(updateDto);
        assertEquals("updated title", updated.getTitle());
        assertEquals("updated content", updated.getContent());
    }

    @Test
    @DisplayName("없는포스트")
    void updateNotExist() {
        UpdatePostDto updateDto = UpdatePostDto.builder().postId(1L).title("updated title").content("updated content").build();
        assertThrows(PostNotFoundException.class, () -> postUpdateService.update(updateDto));
    }
}