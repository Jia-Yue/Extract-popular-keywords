import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class keywordcounter
{ 
	//C:\Users\anagh\workspace\ADS\src\input
       //C:\Users\anagh\Downloads\ADSTest.txt

    public static void main(String[] args) throws IOException  
    { 
    	//taking input file from argument
    	File inFile = null;
    	if (0 < args.length) {
    	   inFile = new File(args[0]);
    	} else {
    	   System.err.println("Invalid arguments count:" + args.length);
    	}
    	
       
        //specifying output file to output.txt
        PrintWriter writer=new PrintWriter("output_file.txt");
      
        
        BufferedReader br = new BufferedReader(new FileReader(inFile)); 
        HashMap<String,node> keyval;
	keyval=new HashMap<String,node>();
       
        heapops ops=new heapops();
        String st; 
        while ((st = br.readLine()) != null){ 
       
        Pattern pt = Pattern.compile("([$])(.*\\w+.*)(\\s+)(\\d+)");             
        Pattern p1 = Pattern.compile("(\\d+)");
          
        Matcher match= pt.matcher(st);
        Matcher match1=p1.matcher(st);
        
          
        if(match.find())
        {
          //check if the key is contained in hashmap otherwise insert
          if(keyval.containsKey(match.group(2))){
        	  //increase key since the key is already present
        	  node val=keyval.get(match.group(2));
        	  int old=val.value;
        	  ops.increasekey(val,old+Integer.parseInt(match.group(4)));
        		  
        		  
        	  }
        	  else{
        		  //insert in hashmap and then in fibonacci heap
        		  node insert=new node(Integer.parseInt(match.group(4)),match.group(2));
        		  ops.insert(insert);
        		  keyval.put(match.group(2),insert);
        		          		  
        	  }
              
          }
          else if(match1.find()){
        	  
        	  //remove max operation since only a number entry was found
        	  
        	  ArrayList<String> output = new ArrayList<String>();
              ArrayList<Integer> outputvals=new ArrayList<Integer>();
        	  
        	  for(int i=0;i<Integer.parseInt(match1.group(1));i++){
        		  node q=ops.removemax();
        		  output.add(q.key);
        		  outputvals.add(q.value);
        		  
        		  if(i==Integer.parseInt(match1.group(1))-1){
        			  writer.write(output.get(i));
        		  }
        		  else{
        			  writer.write(output.get(i)+",");
        		  }
        		  
        		  keyval.remove(q.key);
        	  } 
        	  writer.println("");
        	  
        	  //re inserting the removed key value pairs into fibonacci heap
        	  for(int i=0;i<Integer.parseInt(match1.group(1));i++){
        		  node insert=new node(outputvals.get(i),output.get(i));
        		  ops.insert(insert);
        		  keyval.put(output.get(i), insert);
        	  }
        	  
        	  
        	  
          }          
          else {
        	  //if the input file reads stop then stop executing the code
        	  writer.flush();
        	  writer.close();
        	 
        	  System.exit(1);
        	  
          }
        }    
    } 
} 
