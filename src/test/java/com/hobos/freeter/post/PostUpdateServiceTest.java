package com.hobos.freeter.post;

import com.hobos.freeter.member.Member;
import com.hobos.freeter.member.SignupDTO;
import com.hobos.freeter.member.SignupService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostUpdateServiceTest {


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostCreateService postCreateService;

    @Autowired
    PostUpdateService postUpdateService;

    @Autowired
    private SignupService signupService;

    @Autowired
    private PostRepository postRepository;


    @BeforeEach
    public void beforeAll() {
        Category categoryA = Category.builder().name("aa").build();
        Category categoryB = Category.builder().name("bb").build();

        entityManager.persist(categoryA);
        entityManager.persist(categoryB);


        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("1234").build();
        Member member = signupService.signup(dto);

        Long categoryId = entityManager.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList().getFirst().getId();

        PostCreateRequest request = PostCreateRequest.builder().title("title").content("content").categoryId(categoryId).build();

        Post post = postCreateService.create(member.getId(), request);

    }

    @Test
    @Transactional
    void update() {
        Post post = postRepository.findAll().getFirst();

        UpdatePostDto updateDto = UpdatePostDto.builder().postId(post.getId()).title("updated title").content("updated content").build();
        Post updated = postUpdateService.update(updateDto);
        assertEquals("updated title", updated.getTitle());
        assertEquals("updated content", updated.getContent());
    }

    @Test
    @Transactional
    @DisplayName("없는포스트")
    void updateNotExist() {
        UpdatePostDto updateDto = UpdatePostDto.builder().postId(1L).title("updated title").content("updated content").build();
        assertThrows(PostNotFoundException.class, () -> postUpdateService.update(updateDto));
    }
}