package com.hobos.freeter.common;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class Likable {
    private int likeCount;

    // TODO: 동시성??
    public void increaseLikeCount() {
        likeCount += 1;
    }

    public void decreaseLikeCount() {
        System.out.println(likeCount);
        likeCount -= 1;
        System.out.println(likeCount);
    }
}
