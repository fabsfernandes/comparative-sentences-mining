
package br.com.ufu.lsi.comparative.preprocess;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import br.com.ufu.lsi.comparative.model.Tweet;
import br.com.ufu.lsi.comparative.model.TweetType;
import br.com.ufu.lsi.comparative.util.FileUtil;
import br.com.ufu.lsi.comparative.util.PropertiesUtil;

public class TwitterPreprocess {

    private static final String TWITTER_CONSUMER_KEY = "1EFJ89dlytTcSgPNNh1sbxCDE";

    private static final String TWITTER_SECRET_KEY = "KKAymR8zaPK7nfx3FHwJKDaLsloKMtJRCpWtBdpC9I59Kqqz1L";

    private static final String TWITTER_ACCESS_TOKEN = "991826522-lu0cSH1hjz44lLr4WD81Z1MaVjJP0NurkSKby0iL";

    private static final String TWITTER_ACCESS_TOKEN_SECRET = "AmOGARF53oE5ovS6bWfwiWTwaAjpwY6sT6a9QK2edwjfS";
    
    private static final String TWITTER_FILE = PropertiesUtil.getProperty( "TWITTER_FILE" );
    
    private static final int BUFFER = PropertiesUtil.getIntProperty( "BUFFER" );
    

    public static void collectTweets( String queryString ) throws Exception {


        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled( true ).setOAuthConsumerKey( TWITTER_CONSUMER_KEY ).setOAuthConsumerSecret( TWITTER_SECRET_KEY ).setOAuthAccessToken( TWITTER_ACCESS_TOKEN ).setOAuthAccessTokenSecret( TWITTER_ACCESS_TOKEN_SECRET );
        TwitterFactory tf = new TwitterFactory( cb.build() );
        Twitter twitter = tf.getInstance();

        Query query = new Query( queryString );
        query.setLang( "en" );
        query.setResultType( ResultType.popular );
        query.setSince( "2013-01-01" );
        query.setUntil( "2014-01-01" );
        query.setCount( 5 );
        
        long newId = 0;
        for( int i = 0; i < 2; i++ ) {
            long maxId = queryTweeter( twitter, query, newId );
            newId = maxId;
            System.out.println("###############");
        }
        

    }
    
    public static long queryTweeter( Twitter twitter, Query query, long maxId ) throws Exception {

        long lowestId = 999999999999999999L;
        
        if( maxId != 0 )
            query.setMaxId( maxId );
        
        List< Tweet > tweetsList = new ArrayList< Tweet >();
        
        QueryResult result;
        int i = 0;
        do {
            result = twitter.search( query );
            List< Status > tweets = result.getTweets();
           
            for ( Status tweet : tweets ) {

                Tweet newTweet = new Tweet();

                if ( tweet.isRetweet() ) {

                    newTweet.setUser( tweet.getUser().getScreenName() );
                    newTweet.setType( TweetType.RETWEET );
                    newTweet.setCreatedAt( tweet.getRetweetedStatus().getCreatedAt() );
                    newTweet.setId( tweet.getRetweetedStatus().getId() );
                    String text = tweet.getRetweetedStatus().getText();
                    newTweet.setText( text.replaceAll( "\n", " " ) );
                    
                } else {
                    
                    newTweet.setUser( tweet.getUser().getScreenName() );
                    newTweet.setType( TweetType.TWEET );
                    newTweet.setCreatedAt( tweet.getCreatedAt() );
                    newTweet.setId( tweet.getId() );
                    String text = tweet.getText();
                    newTweet.setText( text.replaceAll( "\n", " " ) );
                }
                System.out.println( (i++) + "\t" + newTweet );
                tweetsList.add( newTweet );
                
                if( tweet.getId() < lowestId )
                    lowestId = tweet.getId();
            }
            
            if( tweetsList.size() > BUFFER ) {
                serializeTweets( tweetsList );
                tweetsList.clear();
            }
            
        } while ( ( query = result.nextQuery() ) != null );
        serializeTweets( tweetsList );
        
        return lowestId;
    }
    
    public static void serializeTweets( List<Tweet> tweets ) throws Exception {
        
        System.out.println( "Serializing...");
        
        BufferedWriter bw = FileUtil.openOutputFile( TWITTER_FILE );
        
        for( Tweet tweet : tweets ) {
            
            bw.write( tweet.toString() );
            bw.newLine();
        }
    }
    

    public static void main( String ... args ) throws Exception {
        collectTweets( "I gave the film rating #IMDB" );
    }

}