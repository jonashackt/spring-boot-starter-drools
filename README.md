# spring-boot-starter-drools
Spring Boot Starter for booting fast with the JBoss Business Rules Engine Drools Expert and KIE Components

### Howto
See the Testclass for how to use the drools-api (it's basicly the KieSession you need to interact with)

Just import this spring-boot-starter as dependency in your pom-File and things should work.

###Improve
* Errorhandling of Rules-Editing is bad (getting Cast-Exceptions, when Rules aren´t correctly defined - what happens 1000times while developing...)