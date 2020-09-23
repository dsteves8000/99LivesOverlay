# 99LivesOverlay
This is used as a customizable overlay to display current pace for obtaining lives. To compile and run this, make sure JNativeHook1.1 is attached to the classpath, this is necessary to allow background inputs like "1" "2" "3" "0" "9" to be pressed while running your broadcasting software in the foreground. When running the program, the text gets set to a file named Events.txt, which can be used as a text source in OBS (or whatever broadcasting software you use) and will update in real time. You can then map 1, 2, 3, 0, 9 to buttons on your stream deck. (1,2,3,0 represent lives gained, press them anytime you hit a flagpole, 9 represents lives lost, press it anytime you die, cooldown of 20 seconds per button press to prevent accidental secondary presses)
