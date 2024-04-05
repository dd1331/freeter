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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostLikeServiceTest {

    @Autowired
    private PostCreateService postCreateService;

    @Autowired
    PostUpdateService postUpdateService;

    @Autowired
    private SignupService signupService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostLikeService postLikeService;

    @PersistenceContext
    private EntityManager entityManager;

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

        com.hobos.freeter.post.Post post = postCreateService.create(member.getId(), request);

    }

    @Test
    @Transactional
    void like() {
        Post post = postRepository.findAll().getFirst();
        Long memberId = postRepository.findAll().getFirst().getPosterId();

        postLikeService.like(post.getId(), memberId);

        PostLike like = postLikeRepository.findById(post.getId()).orElseThrow();
        Post likedPost = postRepository.findAll().getFirst();
        assertEquals(1, likedPost.getLikeCount());
        assertEquals(like.getPost().getPosterId(), post.getId());
    }

    @Test
    @Transactional
    void unlike() {

        Post post = postRepository.findAll().getFirst();
        Long memberId = postRepository.findAll().getFirst().getPosterId();
        postLikeService.like(post.getId(), memberId);
        boolean liked = !postLikeRepository.findAll().isEmpty();
        postLikeService.unlike(post.getId(), memberId);

        Post unLikedComment = postRepository.findAll().getFirst();
        boolean unLiked = postLikeRepository.findAll().isEmpty();

        assertEquals(0, unLikedComment.getLikeCount());
        assertTrue(liked);
        assertTrue(unLiked);
    }

    @Test
    @Transactional
    void unlikeByLiked() {

        Post post = postRepository.findAll().getFirst();
        Long memberId = postRepository.findAll().getFirst().getPosterId();
        postLikeService.like(post.getId(), memberId);
        postLikeService.like(post.getId(), memberId);

        Post unLikedPost = postRepository.findAll().getFirst();

        assertEquals(0, unLikedPost.getLikeCount());
    }
}