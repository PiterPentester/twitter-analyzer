package io.github.gandrade.analyzer.twitter.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Slf4j
public class TwitterFollowersReader<T> implements ItemReader<T> {

    private CursoredList<T> iterator;
    private long sleep;

    @Autowired
    private TwitterTemplate twitterTemplate;

    public TwitterFollowersReader(CursoredList<T> iterator) {
        this(iterator, 10000);
    }

    public TwitterFollowersReader(CursoredList iterator, long sleep){
        this.iterator = iterator;
        this.sleep = sleep;
    }

    @Override
    public T read() throws UnexpectedInputException, ParseException, NonTransientResourceException, InterruptedException {
        if (iterator.isEmpty()) {
            log.info("Forced throttling... (10s)");
            Thread.sleep(sleep);
            long nextCursor = iterator.getNextCursor();
            log.info("Fetching more data, cursor ID {}:", nextCursor);
            if (nextCursor == 0){
                return null;
            }
            iterator = (CursoredList<T>) twitterTemplate.friendOperations().getFollowersInCursor(nextCursor);
        }
        return iterator.remove(0);
    }
}
