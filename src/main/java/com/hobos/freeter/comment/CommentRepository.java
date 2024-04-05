package com.hobos.freeter.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.SequencedCollection;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPostId(Pageable pageable, Long postId);

    List<CommentEntity> findByParentId(Long id);

    List<CommentEntity> findByPostIdAndParentId(Pageable pageable, Long postId, Long parentId);
}
