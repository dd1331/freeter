package com.hobos.freeter.common;

import lombok.Getter;

@Getter
public abstract class Likable {
    private int likeCount;

    // TODO: 동시성??
    public void increaseLikeCount() {
        likeCount += 1;
    }

    public void decreaseLikeCount() {

        likeCount -= 1;
    }
}
