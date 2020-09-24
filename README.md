# 99LivesOverlay

This is used as a customizable overlay to display current pace for obtaining lives.

Windows:
Simply run the MM2Lives.cmd file and the program should run.

Linux:
Locate the directory where you saved the files, and type java -jar MM2Lives.jar whenever you wish to run the program.


Type " \ " to close the program when you want to end or restart a run (or I guess you could just manually close the program as well like any other, but I added the extra hotkey anyway for convenience). You can even map MM2Lives.cmd (on Windows) to a button on a stream deck to quickly open the file (and map " \ " on a different button to close it). This is necessary to allow background inputs like "1" , "2" , "3" , "0" , "9" , " \ " to be pressed while running your broadcasting software in the foreground. When running the program, the text gets set to a file named Events.txt, which can be used as a text source in OBS (or whatever broadcasting software you use) and will update in real time. You can then map 1, 2, 3, 0, 9, \ to buttons on your stream deck. (1,2,3,0 represent lives gained, press them anytime you hit a flagpole, 9 represents lives lost, press it anytime you die, there is a cooldown of 10 seconds per button press to prevent accidental secondary presses including at the very start of the run). I put it at 10 seconds because it is physically impossible to clear 2 levels in less than 20 seconds and furthermore impossible to lose 2 lives in less than 10 seconds. " \ " now closes the program so that you can end the run and reopen the program to restart it.

If you wish to modify what the text overlay says, I have provided the rest of the supporting files, just make sure you add the JNativeHook1.1 to your classpath when you compile and run the MM2Lives.java file. Then you'll just have to remake the .jar file afterwards after you made your changes. If you want me to just to this instead I'd be more than happy to help, just reach out to me.
