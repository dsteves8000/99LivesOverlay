# 99LivesOverlay

Make sure you have Java and the Java Runtime Environment installed and current, it is confirmed to work with Java 11 at least.

This is used as a customizable overlay to display current pace for obtaining lives. To compile and run this, make sure JNativeHook1.1 is attached to the classpath. In the terminal to compile this (you only need to do this part once) you just need to go to the directory that you downloaded the program and type:

javac -cp JNativeHook1.1.jar MM2Lives.java

and everytime you want to start/restart a run for the program it is:

java -cp .:JNativeHook1.1.jar MM2Lives

this is necessary to allow background inputs like "1" "2" "3" "0" "9" to be pressed while running your broadcasting software in the foreground. When running the program, the text gets set to a file named Events.txt, which can be used as a text source in OBS (or whatever broadcasting software you use) and will update in real time. You can then map 1, 2, 3, 0, 9, \ to buttons on your stream deck. (1,2,3,0 represent lives gained, press them anytime you hit a flagpole, 9 represents lives lost, press it anytime you die, there is a cooldown of 10 seconds per button press to prevent accidental secondary presses including at the very start of the run). I put it at 10 seconds because it is physically impossible to clear 2 levels in less than 20 seconds and furthermore impossible to lose 2 lives in less than 10 seconds. "\" now closes the program so that you can end the run and reopen the program to restart it.
