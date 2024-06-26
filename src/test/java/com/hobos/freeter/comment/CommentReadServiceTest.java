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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class CommentReadServiceTest {
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

    @Autowired
    CommentReadService commentReadService;

    @BeforeEach
    public void beforeAll() {
        Category categoryA = Category.builder().name("aa").build();
        Category categoryB = Category.builder().name("bb").build();

        entityManager.persist(categoryA);
        entityManager.persist(categoryB);


        SignupDTO dto = SignupDTO.builder().provider(Member.Provider.KAKAO).providerId("1234").name("name").profileImg("test").build();
        Member member = signupService.signup(dto);

        Long categoryId = entityManager.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList().getFirst().getId();

        PostCreateRequest request = PostCreateRequest.builder().title("title").content("content").categoryId(categoryId).build();

        Post post = postCreateService.create(member.getId(), request);


        commentCreateService.createComment(post.getId(), "content", member.getId());
        commentCreateService.createComment(post.getId(), "content2", member.getId());
    }

    @Transactional
    @Test
    void getComments() {
        Post post = entityManager.find(Post.class, 1L);
        Pageable pageable = PageRequest.of(0, 5);
        CommentListRequest listDto = CommentListRequest.builder().postId(post.getId()).build();
        List<CommentResponse> comments = commentReadService.getPostComments(pageable, listDto, Optional.empty());
        System.out.println(comments.getFirst());
        assertEquals(2, comments.size());
        assertEquals("name", comments.getFirst().getName());
        assertEquals("test", comments.getFirst().profileImg);
        assertNotNull(comments.getFirst().getCreatedAt());
        assertNotNull(comments.getFirst().getUpdatedAt());
        assertNotNull(comments.getFirst().getId());
        assertNotNull(comments.getFirst().getContent());
        assertNotNull(comments.getFirst().getIsMine());
    }

    @Transactional
    @Test
    void getChildComments() {
        Post post = entityManager.find(Post.class, 1L);
        Pageable pageable = PageRequest.of(0, 5);
        ChildCommentListRequest listDto = ChildCommentListRequest.builder().postId(post.getId()).parentId(1L).build();
        List<CommentResponse> comments = commentReadService.getChildComments(pageable, listDto, Optional.empty());
        assertTrue(comments.stream().map(
                        r -> {
                            System.out.println(r.getParentId());

                            return r.getParentId();
                        }
//                CommentResponse::getParentId
                )
                .anyMatch(s -> s.equals(1L)));


    }
}