package io.github.gandrade.analyzer.twitter.exception;

import org.springframework.social.RateLimitExceededException;

public class TwitterRateLimiteExceedException extends Throwable {

    public TwitterRateLimiteExceedException(RateLimitExceededException e) {
        super(e);
    }
}
