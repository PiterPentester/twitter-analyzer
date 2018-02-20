package io.github.gandrade.analyzer.twitter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.twitter.config.TwitterInboundChannelAdapterParser;
import org.springframework.integration.twitter.inbound.SearchReceivingMessageSource;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
public class IntegrationConfiguration {

    private TwitterTemplate twitterTemplate;

//    @Bean
//    @InboundChannelAdapter(value =  "twitter-followers", poller = @Poller(fixedDelay = "5000"))
//    public SearchReceivingMessageSource twitterFollowers() {
//        return new SearchReceivingMessageSource()
//    }

}
