package controllers;

import entitys.Hashtags;
import entitys.Tweets;
import facades.TweetsFacades;
import twitter4j.TwitterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonSerializable;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

 



@CrossOrigin()
@RestController
@RequestMapping("/Tweets")
public class tweetsController {
	@Autowired 
	TweetsFacades tweetsFacades;

	@GetMapping()
	   public String getTweets() {
		String json = "";
		   List<Tweets> tweetsList = tweetsFacades.getTweets();	
		   if(tweetsList.size()<=0) {
			     json = new Gson().toJson("No hay tweets en persistencia" );
		   }else {
			     json = new Gson().toJson(tweetsList);
		   }
	       return json;
	   }
	  
	   
   @RequestMapping("/persistTweets")
   public String consumeAndPersistTweets(@RequestParam(defaultValue = "1500", required=false) String maxFollowers, @RequestParam(defaultValue = "{}" , required=false) String[] languageList) {
	   String[] arrayFollow =  {"es","eng","ita"};
	    System.out.println(languageList);
	    String[] arrayLanguages;
	    if(languageList.length<=0) {
	    	arrayLanguages = arrayFollow;
	    }else {
	    	arrayLanguages = languageList;
	    }
	   
	   boolean isSuccess = tweetsFacades.persistFilteredTweets(Integer.parseInt(maxFollowers), languageList );
	   String json ="";
	   if(isSuccess) {
		   List<Tweets> tweetsList = tweetsFacades.getTweets();	
		   if(tweetsList.size()<=0) {
			     json = new Gson().toJson("No hay tweets en persistencia" );
		   }else {
			     json = new Gson().toJson("Los tweets se han guardado en persistencia correctamente");
		   }
	   } 
	    
	   return json;
   }
   @GetMapping("/validarTweet/{id}")
   public String validateTweet(@PathVariable Long id) {
       return tweetsFacades.setValidTweet(id);
   }
   @GetMapping("/valids")
   public String getValidTweets() {
	   List<Tweets> tweetsList = tweetsFacades.getValidTweets();	
	   
	   String json = "";
	   if(tweetsList.size()<=0) {
		     json = new Gson().toJson("no hay tweets validos en este momento" );
	   }else {
		     json = new Gson().toJson(tweetsList);
	   }
	   
	return json;
   }
   @RequestMapping("/hashtags")
   public String consultHastags(@RequestParam(defaultValue = "10", required=false) String maxHashtags) {

	   List<Hashtags> hashtagsList = tweetsFacades.getOrderedMaxHashtags(Integer.parseInt(maxHashtags));

	   String json = "";
	   if(hashtagsList.size()<=0) {
		     json = new Gson().toJson("no hay hashtags validos en este momento" );
	   }else {
				json = new Gson().toJson(hashtagsList);
			
	   }
	   return json;
	   
   }


}