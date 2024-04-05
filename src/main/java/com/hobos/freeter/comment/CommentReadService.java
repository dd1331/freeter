package com.hobos.freeter.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentReadService {
    private final CommentRepository commentRepository;

    public List<CommentResponse> getPostComments(Pageable pageable, CommentListRequest dto, Optional<Long> memberId) {

        List<CommentEntity> comments = commentRepository.findByPostId(pageable, dto.getPostId());
        return comments.stream()
                .map(comment -> CommentResponse.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .name(comment.getCommenter().getName())
                        .profileImg(comment.getCommenter().getProfileImg())
                        .createdAt(comment.getCreatedAt())
                        .updatedAt(comment.getUpdatedAt())
                        .isMine(comment.isMine(memberId))
                        .parentId(Optional.ofNullable(comment.getParent())
                                .map(CommentEntity::getId))
                        .build())
                .collect(Collectors.toList());

    }

    public List<CommentResponse> getChildComments(Pageable pageable, ChildCommentListRequest dto, Optional<Long> memberId) {
        CommentListRequest request = new CommentListRequest(dto.postId);
        return getPostComments(pageable, request, memberId);
    }
}
