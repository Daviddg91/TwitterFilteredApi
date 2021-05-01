package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entitys.Hashtags;
import repository.HashtagsRepository;

@Service
public class HashtagsService {

	@Autowired
	HashtagsRepository hashTagsRepository;
	
	public List<Hashtags> getOrderByCountHashtags(int maxResults) {
		
		return hashTagsRepository.findAllOrderByCounter().subList(0, maxResults);
		
		
		
	}
}
