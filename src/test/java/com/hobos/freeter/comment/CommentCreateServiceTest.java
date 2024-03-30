package com.hobos.freeter.comment;

import com.hobos.freeter.member.Member;
import com.hobos.freeter.member.MemberRepository;
import com.hobos.freeter.member.SignupDTO;
import com.hobos.freeter.member.SignupService;
import com.hobos.freeter.post.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentCreateServiceTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;


    @Autowired
    private SignupService signupService;

    @Autowired
    private PostCreateService postCreateService;

    @Autowired
    private CommentCreateService commentCreateService;

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

        Post post = postCreateService.create(member.getMemberId(), request);
    }


    @Test
    @Transactional
    void createComment() {

        Member member = memberRepository.findAll().getFirst();

        Post post = postRepository.findAll().getFirst();

        CommentEntity comment = commentCreateService.createComment(post.getId(), "content", member.getMemberId());


        System.out.println(comment);

        assertEquals(comment.getContent(), "content");
        assertEquals(comment.getCommenter().getMemberId(), member.getMemberId());
        assertEquals(post.getId(), comment.getPost().getId());

    }
}