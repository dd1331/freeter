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
class CommentDeleteServiceTest {

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

    @Autowired
    private CommentDeleteService commentDeleteService;

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
    void delete() {
        Member member = memberRepository.findAll().getFirst();

        Post post = postRepository.findAll().getFirst();

        CommentEntity comment = commentCreateService.createComment(post.getId(), "content", member.getId());

        commentDeleteService.delete(comment.getId(), member.getId());

        assertNull(commentRepository.findById(comment.getId()).orElse(null));

    }

    @Test
    @Transactional
    void notFound() {
        Member member = memberRepository.findAll().getFirst();
        assertThrows(NoSuchElementException.class, () -> commentDeleteService.delete(1L, member.getId()));
    }

    @Test
    @Transactional
    void notMine() {
        Member member = memberRepository.findAll().getFirst();

        Post post = postRepository.findAll().getFirst();

        CommentEntity comment = commentCreateService.createComment(post.getId(), "content", member.getId());

        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("12345").build();
        Member anotherMember = signupService.signup(dto);

        assertThrows(UnauthorizedAccessException.class, () -> commentDeleteService.delete(comment.getId(), anotherMember.getId()));
    }
}