package repository; 

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
 
import entitys.Tweets;
@Repository
public interface TweetsRepository extends JpaRepository<Tweets, Long> {
	
	List<Tweets> findAll();
	
	@Query("select t from Tweets t where t.valid = TRUE")
	 List<Tweets> findTweetsValid();
	
	@Query("select t from Tweets t where t.id = :userId")
	 Optional<Tweets> getTweetById(@Param("userId") Long idUsuario);
	
}



