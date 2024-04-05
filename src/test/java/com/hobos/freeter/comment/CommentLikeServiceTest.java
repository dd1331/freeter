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
class CommentLikeServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;


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

    @Autowired
    private CommentLikeService commentLikeService;

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

        commentCreateService.createComment(post.getId(), "comment", member.getId());
    }

    @Test
    @Transactional
    void likeComment() {
        CommentEntity comment = commentRepository.findAll().getFirst();
        Long memberId = commentRepository.findAll().getFirst().getCommenter().getId();
        System.out.println(comment.getId());

        commentLikeService.like(comment.getId(), memberId);

        CommentLike like = commentLikeRepository.findById(comment.getId()).orElseThrow();
        CommentEntity likedComment = commentRepository.findAll().getFirst();
        assertEquals(1, likedComment.getLikeCount());
        assertEquals(like.getComment().getId(), comment.getId());
    }

    @Test
    @Transactional
    void unlikeComment() {


        CommentEntity comment = commentRepository.findAll().getFirst();
        Long memberId = commentRepository.findAll().getFirst().getCommenter().getId();
        commentLikeService.like(comment.getId(), memberId);
        boolean liked = !commentLikeRepository.findAll().isEmpty();
        commentLikeService.unlike(comment.getId(), memberId);

        CommentEntity unLikedComment = commentRepository.findAll().getFirst();
        boolean unLiked = commentLikeRepository.findAll().isEmpty();

        assertEquals(0, unLikedComment.getLikeCount());
        assertTrue(liked);
        assertTrue(unLiked);
    }


    @Test
    @Transactional
    void unlikeByLiked() {


        CommentEntity comment = commentRepository.findAll().getFirst();
        Long memberId = commentRepository.findAll().getFirst().getCommenter().getId();
        commentLikeService.like(comment.getId(), memberId);
        commentLikeService.like(comment.getId(), memberId);

        CommentEntity unLikedComment = commentRepository.findAll().getFirst();

        assertEquals(0, unLikedComment.getLikeCount());
    }
}