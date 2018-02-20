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
public class TwitterReader<T> implements ItemReader<T> {

    private CursoredList<T> iterator;
    private int currentPosition = 0;

    @Autowired
    private TwitterTemplate twitterTemplate;


    public TwitterReader(CursoredList<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public T read() throws UnexpectedInputException, ParseException, NonTransientResourceException, InterruptedException {
        if (iterator.isEmpty()) {
            log.info("Forced throttling... (10s)");
            Thread.sleep(10000);
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
