package repository;

import java.util.Collections;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
 import org.springframework.stereotype.Repository;

import entitys.Hashtags;
 


@Repository
public interface HashtagsRepository extends JpaRepository<Hashtags, Long> {
	
	
	
	@Query("select name,count(name) as repeats from Hashtags GROUP BY name ORDER BY repeats DESC ")
	List<Hashtags> findAllOrderByCounter();
	
	
	/*
	@Query(nativeQuery = true, value = "select name,count(name) as repeats from Hashtags GROUP BY name ORDER BY repeats DESC LIMIT :maxResults ")
	 List<Hashtags> findAllOrderByCounter(@Param("maxResults") int maxResults);
	*/
	
}