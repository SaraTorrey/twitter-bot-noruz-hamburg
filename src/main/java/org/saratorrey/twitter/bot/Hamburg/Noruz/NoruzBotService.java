package org.saratorrey.twitter.bot.Hamburg.Noruz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.annotation.PostConstruct;

@Service
public class NoruzBotService {

    private static final Logger LOG = LoggerFactory.getLogger( NoruzBotService.class );

    // Don't ever check your credentials into GitHub!
    private static final String TWITTER_CONSUMER_KEY = System.getenv( "TWITTER_CONSUMER_KEY" );
    private static final String TWITTER_CONSUMER_SECRET = System.getenv( "TWITTER_CONSUMER_SECRET" );
    private static final String TWITTER_ACCESS_TOKEN = System.getenv( "TWITTER_ACCESS_TOKEN" );
    private static final String TWITTER_ACCESS_SECRET = System.getenv( "TWITTER_ACCESS_SECRET" );

    @PostConstruct
    public void startBot() throws TwitterException, InterruptedException {

        LOG.info( "Twitter Bot is starting now." );

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled( true )
                .setOAuthConsumerKey( TWITTER_CONSUMER_KEY )
                .setOAuthConsumerSecret( TWITTER_CONSUMER_SECRET )
                .setOAuthAccessToken( TWITTER_ACCESS_TOKEN )
                .setOAuthAccessTokenSecret( TWITTER_ACCESS_SECRET );

        TwitterFactory twitterFactory = new TwitterFactory( configurationBuilder.build() );
        Twitter twitter = twitterFactory.getInstance();

        updateStatus( twitter, "Tada!" );
        updateStatus( twitter, "Goodbye!" );

        LOG.info( "Twitter Bot is done now." );
    }

    private void updateStatus( Twitter twitter, String tweetText ) throws TwitterException {

        try {
            Thread.sleep( 2000 );
        }
        catch ( InterruptedException e ) {
            e.printStackTrace();
        }
        Status status = twitter.updateStatus( tweetText );

        LOG.info( "Successfully updated the status to [{}].", status.getText() );
    }
}