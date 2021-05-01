package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entitys.Tweets;
import repository.TweetsRepository;

@Service
public class ServiceTweets {
	@Autowired 
	TweetsRepository tweetsRepository;
	
	public List<Tweets> getTweetsService() {
		
		return tweetsRepository.findAll();
	}
	public List<Tweets> getTweetsValidService() {
		
		return tweetsRepository.findTweetsValid();
	}
	public Optional<Tweets> getTweetById(Long id) {
		return tweetsRepository.getTweetById(id);
	}
	
}
