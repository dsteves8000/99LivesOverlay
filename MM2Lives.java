import java.util.*; 
import java.io.*; 
import java.text.*; 
import org.jnativehook.GlobalScreen; 
import org.jnativehook.keyboard.NativeKeyEvent; 
import org.jnativehook.keyboard.NativeKeyListener; 
import java.lang.Math; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.ChartUtils;
public class MM2Lives implements NativeKeyListener 
{ 
   public static void main(String [] args) throws FileNotFoundException, IOException 
   {
	  //Reads the file with the WR and PB
	  handleConfig();
	  handlePBWR();
	  loadConfig();
	  loadPBWR();
      //Reads user input (currently set to work when the 1,2,3,0,9 buttons are pressed, you might need help on setting this up if you don't know about classpaths / using downloaded .jar files for imports 
      try 
      { 
         GlobalScreen.registerNativeHook(); 
      } 
      catch(Exception e) { e.printStackTrace(); } 
      String line = "";
      if(header == true) { line += "Recent clear rates: "; line += "\n"; }
      if(pasthr == true) { line += Integer.toString(hourcount); line += " clears / past hr "; line += "\n"; }
      if(pastten == true) { line += Integer.toString(tenmincount); line += " clears / past 10 min "; line += "\n"; }
      line += "Time elapsed: "; if(timerunstart) {line += "0:00:00";} else { line += "  "; line += ":"; line += "  "; line += ":"; line += "  "; } if(lastinput == true) { line += " (+0)"; } line += "\n"; 
      if(clearsrunstart == true) { line += "Clears since run start: 0"; line += "\n"; }
      if(curpace == true) { line += "Current Pace: "; line += "0:00:00"; line += "\n"; }
      if(curlives == true) {line += "Lives: "; line += (livesgained + startinglives); } if(avglives == true) { line += " (Per min: 0.0)"; } if(curlives == true || avglives == true) { line += "\n"; } 
      if(linewithPBWR == true) { line += thePBWR;  line += "\n" ; }
      System.out.println(line); 
      WriteFile(line); 
      times.add(0);
      paces.add(0);
      lives.add(livesgained + startinglives);
      GlobalScreen.getInstance().addNativeKeyListener(new MM2Lives()); 
   } 
   static File oldoutput, output, PBWR, Config; 
   static boolean header, pasthr, pastten, timerunstart, lastinput, clearsrunstart, curpace, curlives, avglives, linewithPBWR, dispPB, dispWR;
   static long seconds; 
   static int totalclears = 0;
   static int livesgained = 0;
   static long starttime = System.currentTimeMillis(); 
   static long cooldown = System.currentTimeMillis(); 
   static ArrayList<Integer> times = new ArrayList<Integer>();
   static ArrayList<Integer> paces = new ArrayList<Integer>();
   static ArrayList<Integer> lives = new ArrayList<Integer>();
   static int hourcount = 0; 
   static int tenmincount = 0; 
   static String thePBWR = "";
   static long cdval = 10;
   static int startinglives = 5;
   public static boolean handlePBWR() throws IOException
   {
	   PBWR = new File ("PBWR.txt");
	   PBWR.createNewFile();
	   return true;
   }
   public static boolean handleConfig() throws IOException
   {
	   Config = new File ("Config.txt");
	   Config.createNewFile();
	   return true;
   }
   public static void loadPBWR() throws FileNotFoundException
   {
	   Scanner in = new Scanner(PBWR);
      Scanner check = new Scanner(PBWR);
      String [] tPBWR = new String [2];
      tPBWR[0] = "";
      tPBWR[1] = "";
      
	   int c = 0;
      boolean twolinecheck = false;
      if(check.hasNextLine())
      {
        check.nextLine();
        if(check.hasNextLine())
        {
            twolinecheck = true;
        }
      }           
	   while(in.hasNextLine())
	   {
			 tPBWR[c] += in.nextLine();
			 if(c == 0 && twolinecheck == true && (dispWR == true && dispPB == true))
			 {  
				 tPBWR[c] += " | ";
			 }
          c++;
	   }   
      if(dispWR == true && dispPB == true)
      {
         thePBWR += tPBWR[0];
         thePBWR += tPBWR[1];
      }
      if(dispWR == true && dispPB == false)
      {
         thePBWR += tPBWR[0];
      }
      if(dispWR == false && dispPB == true)
      {
         thePBWR += tPBWR[1];
         if(twolinecheck == false)
         {
            thePBWR += tPBWR[0];
         }
      }
   } 
   public static void loadConfig() throws FileNotFoundException
   {
       Scanner in = new Scanner(Config);
       String curLine = null;
       while(in.hasNextLine())
       {
         curLine = in.nextLine();
         if(curLine.contains("Recent clear rates:") && curLine.contains("TRUE")) {header = true;}
         if(curLine.contains("X clears / past hr") && curLine.contains("TRUE")) {pasthr = true;}
         if(curLine.contains("X clears / past 10 min") && curLine.contains("TRUE")) {pastten = true;}
         if(curLine.contains("Time elapsed: X:XX:XX") && curLine.contains("TRUE")) {timerunstart = true;}
         if(curLine.contains("(+TimeSinceLastInputInSeconds)") && curLine.contains("TRUE")) {lastinput = true;}
         if(curLine.contains("Clears since run start: X") && curLine.contains("TRUE")) {clearsrunstart = true;}
         if(curLine.contains("Current Pace: X:XX:XX") && curLine.contains("TRUE")) {curpace = true;}
         if(curLine.contains("Lives: X") && curLine.contains("TRUE")) {curlives = true;}
         if(curLine.contains("(Per min: X)") && curLine.contains("TRUE")) {avglives = true;}
         if(curLine.contains("LineWithPB/WR") && curLine.contains("TRUE")) {linewithPBWR = true;}
         if(curLine.contains("WR: X:XX:XX") && curLine.contains("TRUE")) {dispWR = true;}
         if(curLine.contains("PB: X:XX:XX") && curLine.contains("TRUE")) {dispPB = true;}
         if(curLine.contains("COOLDOWN")) {cdval = Integer.parseInt(curLine.substring(curLine.lastIndexOf("=") + 1));}
         if(curLine.contains("STARTING LIVES")) {startinglives = Integer.parseInt(curLine.substring(curLine.lastIndexOf("=") + 1));}
       }
   } 
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
    	  int sec = (int) (starttime/1000);          
    	  Integer [] timearray = times.toArray(new Integer[times.size()]);           
    	  Integer [] pacearray = paces.toArray(new Integer[times.size()]);
    	  Integer [] lifearray = lives.toArray(new Integer[times.size()]);
    	  double [] timedoubles = new double [timearray.length];          
    	  double [] livesarray = new double [timearray.length];          
    	  double [] pacetimes = new double [pacearray.length];                 
    	  for(int i = 0; i < timearray.length; i++)          
    	  {         
    		  if (i == 0)
    		  {
    			  timedoubles[0] = 0.0;
    		  }
    		  if(i > 0)
    		  {
    			  timedoubles[i] = (double) (timearray[i] - sec);   
    			  timedoubles[i] = timedoubles[i]/60;
    		  }
    		  pacetimes[i] = (double) (pacearray[i]/60);
    		  livesarray[i] = (double) (lifearray[i]); 
    		  System.out.println(timedoubles[i] + " " + livesarray[i] + " " + pacetimes[i]);
    	  }                      
    	  try          
    	  {             
    		  plot(timedoubles, livesarray, pacetimes);          
    	  }          
    	  catch(Exception g) {}            
    	  System.exit(1);
      }   
      long cd = System.currentTimeMillis() - cooldown; //Cooldown of 10 seconds prevents accidental secondary presses
      int seclast = ((int) cd ) / 1000; 
      if(cd >= cdval*1000) // <- adjust this number of milliseconds to adjust cooldown timer
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
             if(livesgained > (99-startinglives))
             {
               livesgained = (99-startinglives);
             }
             long pacetime = 0;
             if(livesgained > 0)
             {
               pacetime = (time*(99-startinglives))/livesgained;
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
                      
                if(header == true) { line += "Recent clear rates: "; line += "\n"; }
                if(pasthr == true) { line += Integer.toString(hourcount); line += " clears / past hr "; line += "\n"; }
                if(pastten == true) { line += Integer.toString(tenmincount); line += " clears / past 10 min "; line += "\n"; }
                line += "Time elapsed: "; if(timerunstart) {line += Long.toString(hours); line += ":"; line += sdm; line += Long.toString(minutes); line += ":"; line += sds; line += Long.toString(secs);} else { line += "  "; line += ":"; line += "  "; line += ":"; line += "  "; } if(lastinput == true) { line += " (+"; line += Integer.toString(seclast); line += ")"; } line += "\n"; 
                if(clearsrunstart == true) { line += "Clears since run start: "; line += totalclears; line += "\n"; }
                if(curpace == true) { line += "Current Pace: "; line += Long.toString(pacehours); line += ":"; line += pacesdm; line += Long.toString(paceminutes); line += ":"; line += pacesds; line += Long.toString(pacesecs); line += "\n"; }
                if(curlives == true) {line += "Lives: "; line += (livesgained + startinglives); } if(avglives == true) { line += " (Per min: "; line += lpm; line += ")"; } if(curlives == true || avglives == true) { line += "\n"; } 
                if(linewithPBWR == true) { line += thePBWR;  line += "\n" ; }
                System.out.println(line); 
                paces.add((int) pacetime);
                lives.add(livesgained + startinglives);
                
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
   public static void plot(double [] x, double [] y, double [] p) throws Exception    
   {       
	   final XYSeries thelives = new XYSeries("Lives");       
	   final XYSeries thepaces = new XYSeries("Pace");       
	   for(int i = 0; i < y.length; i++)       
	   {          
		   thelives.add(x[i], y[i]);          
		   if(i > 0)          
		   {             
			   thepaces.add(x[i], p[i]);          
		   }       
	   }       
	   final XYSeriesCollection dataset = new XYSeriesCollection();       
	   dataset.addSeries(thelives);       
	   dataset.addSeries(thepaces);       
	   JFreeChart xylineChart = ChartFactory.createXYLineChart("Lives, Pace Over Time", "Time (minutes)", "Lives, Pace (minutes)", dataset, PlotOrientation.VERTICAL, true, true, false);      
	   int width = 640;       
	   int height = 480;           
	   int count = 1;       
	   String save = "RunAnalytics.jpeg";       
	   File XYChart = new File(save);       
	   while(XYChart.exists())        
	   {          
		   save = "RunAnalytics" + (count++) +".jpeg";          
		   XYChart = new File(save);        
	   }       
	   ChartUtils.saveChartAsJPEG(XYChart, xylineChart, width, height);    
   }
}
