package facades;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import configs.InitialTwitterApi;
import entitys.Hashtags;
import entitys.Tweets;
import repository.HashtagsRepository;
import repository.TweetsRepository;
import services.HashtagsService;
import services.ServiceTweets;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
 
@Service
public class TweetsFacades {
	@Autowired 
	InitialTwitterApi initialTwitterApi;
	
	@Autowired 
	TweetsRepository tweetsRepository;
	
	@Autowired 
	HashtagsRepository hashtagsRepository;
	
	@Autowired 
	HashtagsService hashtagsService;
	
	@Autowired
	ServiceTweets serviceTweets;
	
	public boolean persistFilteredTweets(int maxFollowers , String[] languages ) {
		tweetsRepository.deleteAll();
		hashtagsRepository.deleteAll();
		System.out.println("comienza la regeneracion de los tweets...");
		try {
		Twitter twitter= initialTwitterApi.getTwitter();
		ResponseList<Status> tweetsDashboard = twitter.getHomeTimeline();
		
		int counter = 0;
		 do {
	            for (Status tweetDashboard : tweetsDashboard) {                  
					counter++;
	        			int friendFollowerCount = tweetDashboard.getUser().getFollowersCount();
	        			String friendLanguage = tweetDashboard.getLang();
	        			
	        			if(friendFollowerCount >= maxFollowers) {
	        				
	        				HashtagEntity[] entities = tweetDashboard.getHashtagEntities();
		        			 for (HashtagEntity entityArray: entities) {
		        				 Hashtags hashtag = new Hashtags();
		        				 hashtag.setName(entityArray.getText());
		        				 hashtagsRepository.save(hashtag);
		        			 }
		        			 
		        			if(friendLanguage!=null) {
		        				for (String language : languages) {
		        					if(friendLanguage.equals(language)) {
		        						 
		        						Tweets tweet = new Tweets();
		        						if(tweetDashboard.getUser()!=null) {
		        						tweet.setUser(tweetDashboard.getUser().getId());
		        						}
		        						if(tweetDashboard.getGeoLocation()!=null) {
		        						tweet.setLocation(tweetDashboard.getGeoLocation());
		        						}
		        						if(tweetDashboard.getText()!=null) {
		        						tweet.setText(tweetDashboard.getText());
		        						}
		        						tweet.setValid(false);
		        						tweetsRepository.save(tweet);
		        					}
		        				}
		        			}
	        			}
	            }
	        } while (tweetsDashboard.size()>=counter);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   System.out.println("terminó la generacion de los tweets");
	return true;
	}
	
	
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
		hashtagsRepository.deleteAll();
		List<Hashtags> hashtagsList = hashtagsService.getOrderByCountHashtags(maxResults);
		hashtagsRepository.save(hashtagsList);
		return hashtagsRepository.findAll();
	}
	public List<Hashtags> getHashtags(){
		return hashtagsRepository.findAll();
	}
}

