package com.hobos.freeter.common;

public interface LikeService {
    void like(Long targetId, Long memberId);

    void unlike(Long targetId, Long memberId);
}
