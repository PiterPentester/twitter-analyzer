package io.github.gandrade.analyzer.twitter.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Slf4j
public class TwitterFollowersReader<T> implements ItemReader<T> {

    private CursoredList<T> iterator;
    private long sleep;

    @Autowired
    private TwitterTemplate twitterTemplate;

    public TwitterFollowersReader(CursoredList<T> iterator) {
        this(iterator, 500); //FIXME It must be removed when backoff policies being implemented.
    }

    //FIXME It must be removed when backoff policies being implemented.
    public TwitterFollowersReader(CursoredList<T> iterator, long sleep) {
        this.iterator = iterator;
        this.sleep = sleep;
    }

    @Override
    @Retryable(
            value = RateLimitExceededException.class,
            maxAttempts = 10,
            //stateful = true,
            backoff = @Backoff(
                    value = 2000,
                    multiplier = 2,
                    maxDelay = 900000))
    public T read() throws UnexpectedInputException, ParseException, NonTransientResourceException, InterruptedException {
        if (iterator.isEmpty()) {
            Thread.sleep(sleep); //FIXME It must be removed when backoff policies being implemented.
            long nextCursor = iterator.getNextCursor();
            log.info("Fetching more Twitter data, cursor ID '{}'", nextCursor);
            if (nextCursor == 0) {
                return null;
            }
            iterator = (CursoredList<T>) twitterTemplate.friendOperations().getFollowersInCursor(nextCursor);
        }
        return iterator.remove(0);
    }
}
