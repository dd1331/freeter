package com.hobos.freeter.post;

import com.hobos.freeter.comment.CommentCreateService;
import com.hobos.freeter.comment.CommentDeleteService;
import com.hobos.freeter.comment.CommentEntity;
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
import org.springframework.test.annotation.Rollback;

import java.util.List;

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

    @Autowired
    CommentCreateService commentCreateService;

    @Autowired
    private CommentDeleteService commentDeleteService;

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
        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("1234").name("dd").build();
        Member member = signupService.signup(dto);

        PostCreateRequest request = PostCreateRequest.builder().title("title").content("content").categoryId(1L).build();

        Post post = postCreateService.create(member.getId(), request);

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
    @Transactional()
    @Rollback(false)
    @DisplayName("목록 성공")
    void getAll() {
        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("1234").name("dd").build();
        Member member = signupService.signup(dto);

        Long categoryId = entityManager.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList().getFirst().getId();

        PostCreateRequest request = PostCreateRequest.builder().title("title").content("content").categoryId(categoryId).build();

        postCreateService.create(member.getId(), request);
        postCreateService.create(member.getId(), request);

        commentCreateService.createComment(1L, "comment", member.getId());
        commentCreateService.createComment(1L, "comment2", member.getId());
        commentCreateService.createComment(1L, "comment3", member.getId());
        System.out.println("@@" + entityManager.find(CommentEntity.class, 1L));
        commentDeleteService.delete(1L, 1L);
        String jpql = "SELECT c FROM CommentEntity c WHERE c.deletedAt IS NOT NULL OR c.deletedAt IS NULL";
        List<CommentEntity> comments = entityManager.createQuery(jpql, CommentEntity.class).getResultList();

        System.out.println("@@@" + entityManager.find(CommentEntity.class, 1L));
        System.out.println("@@@" + comments.size());

        Pageable pageable = PageRequest.of(0, 5);
        PostListRequest listDto = PostListRequest.builder().categoryId(categoryId).build();
        PostListDto found = postReadService.getPosts(pageable, listDto);

        PostDto firstPost = found.getPosts().stream().findFirst().get();

        assertNotNull(firstPost.getTitle());
        assertNotNull(firstPost.getCreatedAt());
        assertNotNull(firstPost.getName());
        assertEquals(2, firstPost.getCommentCount());


        assertTrue(found.getPosts().stream().findFirst().get().getCategoryIds().contains(categoryId));

//        assertEquals(2, found.getPosts().size());
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
        PostListDto found = postReadService.getPosts(pageable, listDto);

        assertEquals(0, found.getPosts().size());
    }


    @Test()
    @Transactional
    @DisplayName("카테고리별 목록 성공")
    void getAllByCategory() {
        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("1234").name("dd").build();
        Member member = signupService.signup(dto);
        Long categoryId = entityManager.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList().getFirst().getId();

        PostCreateRequest request = PostCreateRequest.builder().title("title").content("content").categoryId(categoryId).build();

        postCreateService.create(member.getId(), request);
        postCreateService.create(member.getId(), request);

        Pageable pageable = PageRequest.of(0, 5);
        PostListRequest listDto = PostListRequest.builder().categoryId(categoryId).build();
        PostListDto found = postReadService.getPosts(pageable, listDto);

        assertTrue(found.getPosts().stream().findFirst().get().getCategoryIds().contains(categoryId));
        assertEquals(2, found.getPosts().size());
    }

}