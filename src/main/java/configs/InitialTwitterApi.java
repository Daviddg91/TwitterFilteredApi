package configs;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
@Service
public class InitialTwitterApi {
 
	@Autowired
	private Environment env;
	 
	public Twitter getTwitter() {
		  ConfigurationBuilder cb = new ConfigurationBuilder();
		  cb.setDebugEnabled(true)
		    .setOAuthConsumerKey(env.getProperty("ConsumerKey"))
		    .setOAuthConsumerSecret(env.getProperty("ConsumerSecret"))
		    .setOAuthAccessToken(env.getProperty("AccessToken"))
		    .setOAuthAccessTokenSecret(env.getProperty("AccessTokenSecret"));
		  TwitterFactory tf = new TwitterFactory(cb.build());
		  Twitter twitter = tf.getInstance();
		  
		return twitter;
	}
	 
}