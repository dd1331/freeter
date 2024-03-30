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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostReadServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostReadService postReadService;

    @Autowired
    private SignupService signupService;

    @Autowired
    private PostCreateService postCreateService;

    @BeforeEach
    public void beforeAll() {
        Category categoryA = Category.builder().name("aa").build();
        Category categoryB = Category.builder().name("bb").build();

        entityManager.persist(categoryA);
        entityManager.persist(categoryB);
    }


    @Test()
    @Transactional
    @DisplayName("성공")
    void getOne() {
        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("1234").build();
        Member member = signupService.signup(dto);

        PostCreateRequest request = PostCreateRequest.builder().title("title").content("content").build();

        Post post = postCreateService.create(member.getMemberId(), request);

        Post found = postReadService.getOne(post.getId());
        assertEquals(post, found);
    }

    @Test()
    @Transactional
    @DisplayName("없는 포스트")
    void notFound() {
        assertThrows(PostNotFoundException.class, () -> postReadService.getOne(1L));
    }

    @Test()
    @Transactional
    @DisplayName("목록 성공")
    void getAll() {
        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("1234").build();
        Member member = signupService.signup(dto);

        Long categoryId = entityManager.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList().getFirst().getId();

        PostCreateRequest request = PostCreateRequest.builder().title("title").content("content").categoryId(categoryId).build();

        postCreateService.create(member.getMemberId(), request);
        postCreateService.create(member.getMemberId(), request);

        Pageable pageable = PageRequest.of(0, 5);
        PostListRequest listDto = PostListRequest.builder().categoryId(categoryId).build();
        Page<Post> found = postReadService.getPosts(pageable, listDto);

        assertEquals(2, found.stream().count());
    }

    @Test()
    @Transactional
    @DisplayName("목록 없으면 빈배열")
    void getAllEmpty() {
        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("1234").build();
        Long categoryId = entityManager.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList().getFirst().getId();

        Pageable pageable = PageRequest.of(0, 5);
        PostListRequest listDto = PostListRequest.builder().categoryId(categoryId).build();
        Page<Post> found = postReadService.getPosts(pageable, listDto);

        assertEquals(0, found.stream().count());
    }


    @Test()
    @Transactional
    @DisplayName("카테고리별 목록 성공")
    void getAllByCategory() {
        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("1234").build();
        Member member = signupService.signup(dto);
        Long categoryId = entityManager.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList().getFirst().getId();

        PostCreateRequest request = PostCreateRequest.builder().title("title").content("content").categoryId(categoryId).build();

        postCreateService.create(member.getMemberId(), request);
        postCreateService.create(member.getMemberId(), request);

        Pageable pageable = PageRequest.of(0, 5);
        PostListRequest listDto = PostListRequest.builder().categoryId(categoryId).build();
        Page<Post> found = postReadService.getPosts(pageable, listDto);

        assertEquals(categoryId, found.getContent().getFirst().getPostCategories().getFirst().getCategory().getId());
        assertEquals(2, found.stream().count());
    }

}