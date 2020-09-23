import java.util.*; 
import java.io.*; 
import java.text.*; 
import org.jnativehook.GlobalScreen; 
import org.jnativehook.keyboard.NativeKeyEvent; 
import org.jnativehook.keyboard.NativeKeyListener; 
import java.lang.Math; 
public class MM2Lives implements NativeKeyListener 
{ 
   public static void main(String [] args) 
   { 
      //Reads user input (currently set to work when the 1,2,3,0,9 buttons are pressed, you might need help on setting this up if you don't know about classpaths / using downloaded .jar files for imports 
      try 
      { 
         GlobalScreen.registerNativeHook(); 
      } 
      catch(Exception e) { e.printStackTrace(); } 
      GlobalScreen.getInstance().addNativeKeyListener(new MM2Lives()); 
   } 
   static File oldoutput; 
   static File output; 
   static long seconds; 
   static int totalclears = 0;
   static int livesgained = 0;
   static long starttime = System.currentTimeMillis(); 
   static long cooldown = System.currentTimeMillis(); 
   static ArrayList<Integer> times = new ArrayList<Integer>(); 
   static int hourcount = 0; 
   static int tenmincount = 0; 
   public void nativeKeyPressed(NativeKeyEvent e) 
   { 
      long time = (System.currentTimeMillis() - starttime)/1000;
      long secs = time%60; 
      String sds = ""; 
      if(secs < 10) 
      { 
         sds = "0"; 
      } 
      long minutes = (time/60)%60; 
      String sdm = ""; 
      if(minutes < 10) 
      { 
         sdm = "0"; 
      }  
      long hours = time/3600; 
      String kP = NativeKeyEvent.getKeyText(e.getKeyCode());
      if(kP.equals("Back Slash"))
      {
         //System.out.println("!");
         System.exit(1);
      }   
      long cd = System.currentTimeMillis() - cooldown; //Cooldown of 10 seconds prevents accidental secondary presses
      int seclast = ((int) cd ) / 1000; 
      if(cd >= 10000) // <- adjust this number of milliseconds to adjust cooldown timer
      { 
         if(kP.equals("1") || kP.equals("2") || kP.equals("3") || kP.equals("0") || kP.equals("9")) 
         { 
            if(kP.equals("1") || kP.equals("2") || kP.equals("3") || kP.equals("0") || kP.equals("9"))
            {
               cooldown = System.currentTimeMillis(); 
               int sec = (int) (System.currentTimeMillis()/1000); 
               times.add(sec); 
               Integer [] timearray = times.toArray(new Integer[times.size()]); 
               hourcount = 0; 
               tenmincount = 0; 
               for(int i = 0; i < timearray.length; i++) 
               { 
                  if((sec - timearray[i]) <= 3600) 
                  { 
                     hourcount++; 
                     if((sec - timearray[i]) <= 600)
                     { 
                        tenmincount++; 
                     } 
                  } 
                }
             }
             if(kP.equals("1"))
             {
               livesgained++;
             }
             else if(kP.equals("2"))
             {
               livesgained += 2;
             }
             else if(kP.equals("3"))
             {
               livesgained += 3;
             }
             else if(kP.equals("9"))
             {
               livesgained--;
             }
             if(kP.equals("1") || kP.equals("2") || kP.equals("3") || kP.equals("0"))
             {
               totalclears++;
             }
             if(livesgained > 94)
             {
               livesgained = 94;
             }
             long pacetime = 0;
             if(livesgained > 0)
             {
               pacetime = (time*94)/livesgained;
             }
             long pacesecs = pacetime%60; 
             String pacesds = ""; 
             if(pacesecs < 10) 
             { 
                  pacesds = "0"; 
             }
             long paceminutes = (pacetime/60)%60; 
             String pacesdm = "";
             if(paceminutes < 10) 
             { 
                 pacesdm = "0"; 
             }  
             long pacehours = pacetime/3600; 
             double livesperminute = livesgained/(double) time;
             livesperminute *= 60.0;
             double lpm = (double)Math.round(livesperminute * 100d) / 100d;
             String line = "";
             
               //Feel free to comment/uncomment out any lines you don't want to see, you can even replace the run timer with your own stopwatch
                //line += "Recent clear rates: "; line += "\n";
                //line += Integer.toString(hourcount); line += " clears / past hr "; line += "\n"; 
                //line += Integer.toString(tenmincount); line += " clears / past 10 min "; line += "\n"; 
                //line += "Clears since run start: "; line += totalclears; line += "\n";
                //line += "Current Lives: "; line += (livesgained + 5); line += "\n";
                
               
                
                
                line += "Time elapsed: "; line += "  "; line += ":"; line += "  "; line += ":"; line += "  "; line += " (+"; line += Integer.toString(seclast); line += ")"; line += "\n"; 
                line += "Current Pace: "; line += Long.toString(pacehours); line += ":"; line += pacesdm; line += Long.toString(paceminutes); line += ":"; line += pacesds; line += Long.toString(pacesecs); line += "\n";
                line += "Lives per minute: "; line += lpm; line += "\n"; 
                line += "WR: 45:56 | PB: 49:38 "; line += "\n";
                System.out.println(line); 
                
                
             WriteFile(line); 
          } 
       } 
   } 
   public void nativeKeyReleased(NativeKeyEvent e){} 
   public void nativeKeyTyped(NativeKeyEvent e){} 
   public static void WriteFile(String line) 
   { 
      oldoutput = new File("Events.txt"); 
      if(oldoutput.canRead() == true) 
      { 
         oldoutput.delete(); 
      } 
      output = new File("Events.txt"); 
      try 
      { 
         FileWriter fW = new FileWriter(output, false); 
         fW.write(line); 
         fW.close(); 
      } 
      catch (IOException e) { e.printStackTrace(); } 
   } 
}





/*
             //Feel free to comment/uncomment out any lines you don't want to see, you can even replace the run timer with your own stopwatch
                line += "Recent clear rates: "; line += "\n";
                line += Integer.toString(hourcount); line += " clears / past hr "; line += "\n"; 
                line += Integer.toString(tenmincount); line += " clears / past 10 min "; line += "\n"; 
                line += "Time elapsed: "; line += Long.toString(hours);  line += " ";  line += ":"; line += sdm; line += Long.toString(minutes);  line += " ";  line += ":"; line += sds; line += Long.toString(secs); line += " "; line += " (+"; line += Integer.toString(seclast); line += ")"; line += "\n"; 
                line += "Clears since run start: "; line += totalclears; line += "\n";
                line += "Current Lives: "; line += (livesgained + 5); line += "\n";
                line += "Current Pace: "; line += Long.toString(pacehours);line += " "; line += ":"; line += pacesdm; line += Long.toString(paceminutes);line += " "; line += ":"; line += pacesds; line += Long.toString(pacesecs); line += " "; line += "\n";
                line += "Lives per minute: "; line += lpm; line += "\n"; 
                line += "WR: 45:56 | PB: 50:54 "; line += "\n";
                System.out.println(line); 
*/
