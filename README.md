# TwitterFilterredApi
 

Api Rest links:
---------------------------

1)Config results Filtered Tweets:
http://localhost:8080/Tweets/persistTweets?languageList=it,pr,fr&maxFollowers=500

2) Get Filtered Tweets (refresh for see more streams filtered):
http://localhost:8080/Tweets

3) Validate Tweet by id:
http://localhost:8080/Tweets/validarTweet/1 (id tweet in database)

4) Get Valid Tweets: 
http://localhost:8080/Tweets/valids

5) Get hashtags:
http://localhost:8080/Tweets/hashtags?maxHashtags=10

Links for heroku:
---------------------------------
1)Config results Filtered Tweets:
https://twitter-filtered-stream.herokuapp.com/Tweets/persistTweets?languageList=pr,jp&maxFollowers=500

2) Get Filtered Tweets(refresh for see more streams filtered):
https://twitter-filtered-stream.herokuapp.com/Tweets

3) Validate Tweet by id:
https://twitter-filtered-stream.herokuapp.com/Tweets/validarTweet/1 (id tweet in database)

4) Get Valid Tweets: 
https://twitter-filtered-stream.herokuapp.com/Tweets/valids

5) Get hashtags:
https://twitter-filtered-stream.herokuapp.com/Tweets/hashtags?maxHashtags=10
application.properties example,dont uploaded in gitignore
---------------------------------------------------


server.port = 8080

spring.datasource.url=jdbc:h2:mem:testdb&characterEncoding=UTF-8;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

spring.datasource.driverClassName=org.h2.Driver

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

spring.jpa.properties.hibernate.hbm2ddl.charset=UTF-8

spring.jpa.properties.hibernate.hbm2ddl.auto=update

spring.jpa.hibernate.ddl-auto=update



ConsumerKey=

ConsumerSecret=

AccessToken=

AccessTokenSecret=



