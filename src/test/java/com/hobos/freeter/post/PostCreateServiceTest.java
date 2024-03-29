package com.hobos.freeter.post;

import com.hobos.freeter.member.Member;
import com.hobos.freeter.member.SignupDTO;
import com.hobos.freeter.member.SignupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
class PostCreateServiceTest {

    @Autowired
    private PostCreateService postCreateService;

    @Autowired
    private SignupService signupService;

    @Test
    void create() {

        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("1234").build();
        Member member = signupService.signup(dto);

        PostCreateRequest request = PostCreateRequest.builder().title("title").content("content").build();

        Post post = postCreateService.create(member.getUserId(), request);

        assertEquals(post.getPosterId(), member.getUserId());
    }
}




