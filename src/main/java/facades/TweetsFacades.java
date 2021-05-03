package facades;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
 
import entitys.Hashtags;
import entitys.Tweets;
import repository.HashtagsRepository;
import repository.TweetsRepository;
import services.HashtagsService;
import services.ServiceTweets;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.ResponseList;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.StreamListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
 
@Service
public class TweetsFacades {


	@Autowired 
	Environment env;
	
	@Autowired 
	TweetsRepository tweetsRepository;
	
	@Autowired 
	HashtagsRepository hashtagsRepository;
	
	@Autowired 
	HashtagsService hashtagsService;
	
	@Autowired
	ServiceTweets serviceTweets;
	
 
	
	public List<Tweets> getTweets() {
		return serviceTweets.getTweetsService();
	}
	
	public List<Tweets> getValidTweets() {
		return serviceTweets.getTweetsValidService();
	}
	
	public String setValidTweet(Long id) {
		String response = "error contacte con el administrador";
		Optional<Tweets> isTweetForValidFound = serviceTweets.getTweetById(id);
		if(isTweetForValidFound.isPresent()) {
			isTweetForValidFound.get().setValid(true);
			tweetsRepository.save(isTweetForValidFound.get());
			response = "El tweet se ha validado correctamente";
		}else {
			response = "No se encuentra ningun Tweet por ese id" + id;
		}
		return response;
	}
	public List<Hashtags> getOrderedMaxHashtags(int maxResults){
		
		List<Hashtags> hashtagsList = hashtagsService.getOrderByCountHashtags(maxResults);
		 
		return hashtagsList;
	}
	public List<Hashtags> getHashtags(){
		return hashtagsRepository.findAll();
	}

	public void listener(int maxFollowers , String[] languages ){
		tweetsRepository.deleteAll();
		hashtagsRepository.deleteAll();
		  StatusListener listener = new StatusListener(){
		        public void onStatus(Status status) {		        	
		        	int friendFollowerCount = status.getUser().getFollowersCount();
        			String friendLanguage = status.getLang();
        			
        			if(friendFollowerCount >= maxFollowers) {
        				
	        			if(friendLanguage!=null) {
	        				for (String language : languages) {
	        					if(friendLanguage.equals(language)) {
	        						HashtagEntity[] entities = status.getHashtagEntities();
	       	        			 for (HashtagEntity entityArray: entities) {
	       	        				 Hashtags hashtag = new Hashtags();
	       	        				 if(entityArray.getText()!=null) {
	       	        					 hashtag.setName(entityArray.getText());
	       	        				 }
	       	        				 hashtagsRepository.save(hashtag);
	       	        			 }
	       	        			 
	        						Tweets tweet = new Tweets();
	        						if(status.getUser()!=null) {
	        						tweet.setUser(status.getUser().getId());
	        						}
	        						if(status.getGeoLocation()!=null) {
	        						tweet.setLocation(status.getGeoLocation());
	        						}
	        						if(status.getText()!=null) {
	        						tweet.setText(status.getText());
	        						}
	        						tweet.setValid(false);
	        						tweetsRepository.save(tweet);
	        					}
	        				}
	        			}
        			}
            
		        }
		        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
		        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
		        public void onException(Exception ex) {
		            ex.printStackTrace();
		        }
				@Override
				public void onScrubGeo(long userId, long upToStatusId) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void onStallWarning(StallWarning warning) {
					// TODO Auto-generated method stub
					
				}
		    };
	  
	    TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
	    twitterStream.addListener((StreamListener) listener);
	    // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
	     
	    twitterStream.setOAuthConsumer(env.getProperty("ConsumerKey"),env.getProperty("ConsumerSecret"));
	    
	    AccessToken accessToken = new AccessToken(env.getProperty("AccessToken") ,env.getProperty("AccessTokenSecret"));
	    twitterStream.setOAuthAccessToken(accessToken);
	    
	
	    twitterStream.sample();
	
	}
		
}


