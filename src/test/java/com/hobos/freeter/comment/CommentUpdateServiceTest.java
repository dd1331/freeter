package com.hobos.freeter.comment;

import com.hobos.freeter.member.*;
import com.hobos.freeter.post.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentUpdateServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;


    @Autowired
    private SignupService signupService;

    @Autowired
    private PostCreateService postCreateService;

    @Autowired
    private CommentCreateService commentCreateService;

    @Autowired
    private CommentUpdateService commentUpdateService;

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

        postCreateService.create(member.getId(), request);
    }

    @Test
    @Transactional
    void update() {
        Member member = memberRepository.findAll().getFirst();

        Post post = postRepository.findAll().getFirst();

        CommentEntity comment = commentCreateService.createComment(post.getId(), "content", member.getId());

        CommentUpdateRequest request = CommentUpdateRequest.builder().id(comment.getId()).content("update").build();

        commentUpdateService.update(request, member.getId());

        CommentEntity updated = commentRepository.findById(comment.getId()).orElseThrow();


        assertEquals("update", updated.getContent());
        assertEquals(comment.getId(), updated.getId());

    }

    @Test
    @Transactional
    void notFound() {

        CommentUpdateRequest request = CommentUpdateRequest.builder().id(1L).content("update").build();

        assertThrows(NoSuchElementException.class, () -> commentUpdateService.update(request, 2L));
    }

    @Test
    @Transactional
    void notMine() {
        Member member = memberRepository.findAll().getFirst();

        Post post = postRepository.findAll().getFirst();

        CommentEntity comment = commentCreateService.createComment(post.getId(), "content", member.getId());

        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("12345").build();
        Member anotherMember = signupService.signup(dto);
        CommentUpdateRequest request = CommentUpdateRequest.builder().id(comment.getId()).content("update").build();
        assertThrows(UnauthorizedAccessException.class, () -> commentUpdateService.update(request, anotherMember.getId()));


    }
}