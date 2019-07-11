<div align="center">
  <h1>ALARM GENERATOR</h1>
</div>
How to use it:<br>
1)Import the library:<br>

  ```java
    import it.unibo.arces.wot.sepa.apps.alarmgenerator.control;
    import it.unibo.arces.wot.sepa.apps.alarmgenerator.model.Email;
  ```
2)Create an Email object and setting the right configuration of your gmail account:

  ```java
    Email myEmail = new Email("adressee@gmail.com","name of the sensor");
    myEmail.setSender("my gmail account");
    myEmail.setPassword("my gmail account password");
  ```
  Remember to allow to the less secure apps to enter inside your gmail account.(gmail guide: https://support.google.com/accounts/answer/6010255?hl=it)
 
3)Create an Alarm object:

```java
  int threshold = 27;//the threshold value which won't never be exceeded
  int lowest_limit = 0;//where to start looking the values, is always zero(except that in some case)
  /*this is an important value, it tells to the alarm system how many measures have to pass before the prevision
  and the control of the values*/
  int greatest_limit = 40; 
  //if you want the verification when the value is higher than threshold else the set up false
  boolean up = true;
  Alarm myAlarm = new Alarm(threshold, lowest_limit, greatest_limit, email, up);
```
 Pay attention to the greatest_limit variable, you need to find the most reliable value for your sensor. Higher is the value 
 better is the prediction but worse is the periodical checking on the value, lower is the value worse is the prediction (more 
 fake alarms) and better is the periodical checking. So choose what's the best for your system and for your needs.
 It's possible disable the prediction inside the alarm:
 ```java
 myAlarm.disablePrediction();
 ```
 Now the system controls the value of every measure in the same moment that it arrives.
 
 4)Create a SubscriptionAlarmGenerator object and run inside:
 ```java
 String topic = "the topic of your sensor"
 SubscriptionAlarmGenerator sag = new SubscriptionAlarmGenerator(topic, myAlarm);
 Thread alarmGenerator = new Thread(sag);
 alarmGenerator.start();//start the loop listener on the topic of your sensor
 ```
 It's also possible create several alarms generator, on the same machine listening on different sensor, at the same time!!
 
 <br><br><br>
 <div align="right">
  developed by Edoardo Carrà in collaboration with [**ARCES**](http://www.arces.unibo.it), the *Advanced Research Center on Electronic Systems "Ercole De Castro"* of the [**University of Bologna**](http://www.unibo.it).
 </div>
