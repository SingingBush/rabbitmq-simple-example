Found this example here:

http://neopatel.blogspot.com/2009/08/rabbitmq-java-clients-for-beginners.html

Steps to get setup.

* Created a pom and put the sample code in a location that maven expected.
* Downloaded the rabbitmq java client.  Compiled it, but found it had problems.  I basically had to adjust the ant builds so that they would generate java classes.  Then I generated a maven consumable bundle.  Then I ran install:install-file to make sure that the JAR/Sources etc. were in the local maven repo.  Then I went back to the sample project and ran mvn clean package to see if it all worked. 
* Generated an IntelliJ project and compiled successfully.
* Ran the consumer
* Ran the producer

I already had RabbitMQ Installed and started so it all worked just fine.

Also, the article I reviewed listed this article:

  http://blogs.digitar.com/jjww/2009/01/rabbits-and-warrens/

I found this article very helpful in understanding the model of AMQP.
