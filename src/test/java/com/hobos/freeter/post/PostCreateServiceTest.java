package com.hobos.freeter.post;

import com.hobos.freeter.member.Member;
import com.hobos.freeter.member.SignupDTO;
import com.hobos.freeter.member.SignupService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
class PostCreateServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostCreateService postCreateService;

    @Autowired
    private SignupService signupService;

    @BeforeEach
    public void beforeAll() {
        Category categoryA = Category.builder().name("aa").build();
        Category categoryB = Category.builder().name("bb").build();

        entityManager.persist(categoryA);
        entityManager.persist(categoryB);
    }


    @Test
    @Transactional
    void create() {
        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("1234").build();
        Member member = signupService.signup(dto);


        List<Category> categories = entityManager.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList();

        PostCreateRequest request = PostCreateRequest.builder().title("title").content("content").categoryId(categories.getFirst().getId()).build();

        Post post = postCreateService.create(member.getMemberId(), request);

        assertEquals(categories.getFirst().getId(), post.getPostCategories().getFirst().getCategory().getId());
        assertEquals(post.getPosterId(), member.getMemberId());
    }
}




