package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;

public class Theater {

    LocalDateProvider provider;
    private List<Showing> schedule;
    //DateTimeFormatter formatter;
    
    public Theater(LocalDateProvider provider) {
        this.provider = provider;
    }
    
    public void loadSchedule(List<Showing> showings) {    	
    	this.schedule = new ArrayList<Showing>(showings);
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }
        return new Reservation(customer, showing, howManyTickets);
    }

    public void printSchedule() {    
        System.out.println(provider.currentDate());
        System.out.println("===================================================");
       
        schedule.forEach(s ->
                System.out.println(s.getSequenceOfTheDay() + ": " + 
                                   s.getStartTime().format(provider.getFormatter()) + "\t" + 
                		           s.getMovie().getTitle() + "\t" + 
                                   humanReadableFormat(s.getMovie().getRunningTime()) + "\t" + 
                                   String.format("$%.2f", s.getFee()))
        );
        System.out.println("===================================================");
    }
    
    @SuppressWarnings("unchecked")
	public void printScheduleJson() {
        JSONObject todayObj = new JSONObject();
        todayObj.put("date", provider.currentDate());
                
        JSONArray arr = new JSONArray();
        
        for(Showing s : schedule) {
        	JSONObject obj = new JSONObject();
            obj.put("sequence_of_the_day", s.getSequenceOfTheDay());
            obj.put("start_time", s.getStartTime().format(provider.getFormatter())); 
            obj.put("title", s.getMovie().getTitle());
            obj.put("running_time", humanReadableFormat(s.getMovie().getRunningTime()));
            obj.put("price", String.format("$%.2f",s.getMovie().getTicketPrice()));
            arr.add(obj);
        }
        todayObj.put("showing" , arr);
       
        System.out.println(todayObj.toString());
    }

    public String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }
 
    
    public List<Showing> getTodayShows(String inputJsonStr) throws ParseException {
        List<Showing> showingList = new ArrayList<Showing>();
        JSONParser parser = new JSONParser();
        try {        	
        	Object obj = parser.parse(inputJsonStr);
            JSONObject jsonObject = (JSONObject)obj;
            JSONArray movies = (JSONArray)jsonObject.get("movies");
            HashMap<String, Movie> moviesByID = new HashMap<String, Movie>();
            for(Object mObj : movies) {
            	JSONObject movie = (JSONObject)mObj;
            	String id = (String)movie.get("id");
            	String title = (String)movie.get("title");
            	long duration = (long)movie.get("duration");
            	String ticketPriceStr = (String)movie.get("ticket_price");
            	Double ticketPrice = Double.parseDouble(ticketPriceStr);
            	long specialCode = (long)movie.get("special_code");
            	Movie aMovie = new Movie(title, Duration.ofMinutes(duration), ticketPrice, (int)specialCode);
            	moviesByID.put(id, aMovie);
            }

            JSONArray showings = (JSONArray)jsonObject.get("showings");
           
            for(Object sObj : showings) {
            	JSONObject showing = (JSONObject)sObj;
            	long seq = (long)showing.get("sequence_of_the_day");
            	String movieId = (String)showing.get("movie_id");
            
            	String startTimeStr = (String)showing.get("start_time");
            	String[] timeStrArr = startTimeStr.split(":");
            	Integer hour = Integer.parseInt(timeStrArr[0]);
            	Integer min = Integer.parseInt(timeStrArr[1]);
            	
            	Showing sh = new Showing(moviesByID.get(movieId), (int)seq, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(hour, min)));
            	showingList.add(sh);
            }           
            
        }
        catch(ParseException pe) {
        	System.out.println("Input JSON string is not formatted correctly.");
        	throw pe;
        }
        catch(Exception e) {
        	e.printStackTrace();
        	throw e;
        }
   
        return showingList;
    }
    public static void main(String[] args) {
    	String inputJsonString = "{\"movies\" : [" +
    			  "{ \"id\": \"spiderMan\", \"title\": \"Spider-Man: No Way Home\", \"duration\": 90, \"ticket_price\": \"12.5\", \"special_code\": 1 },"+
    	          "{ \"id\": \"turningRed\", \"title\": \"Turning Red\", \"duration\": 85, \"ticket_price\": \"11\", \"special_code\": 0 }," +
    	          "{ \"id\": \"theBatMan\", \"title\": \"The Batman\", \"duration\": 95, \"ticket_price\": \"9\", \"special_code\": 0 } ],"   +
    	         " \"showings\": [" +
    	         "{  \"sequence_of_the_day\": 1,     \"movie_id\": \"turningRed\",     \"start_time\": \"09:00\" }," +
    	         "{  \"sequence_of_the_day\": 2,     \"movie_id\": \"spiderMan\",     \"start_time\": \"11:00\" }," +
    	         "{  \"sequence_of_the_day\": 3,     \"movie_id\": \"theBatMan\",     \"start_time\": \"12:50\" }," +
    	         "{  \"sequence_of_the_day\": 4,     \"movie_id\": \"turningRed\",     \"start_time\": \"15:30\" }," +
    	         "{  \"sequence_of_the_day\": 5,     \"movie_id\": \"spiderMan\",     \"start_time\": \"16:10\" }," +
    	         "{  \"sequence_of_the_day\": 6,     \"movie_id\": \"theBatMan\",     \"start_time\": \"17:50\" }," +
    	         "{  \"sequence_of_the_day\": 7,     \"movie_id\": \"turningRed\",     \"start_time\": \"19:30\" }," +
    	         "{  \"sequence_of_the_day\": 8,     \"movie_id\": \"spiderMan\",     \"start_time\": \"22:10\" }," +
    	         "{  \"sequence_of_the_day\": 9,     \"movie_id\": \"theBatMan\",     \"start_time\": \"23:00\" } ]}"; 
    	 
        Theater theater = new Theater(LocalDateProvider.singleton());
        List<Showing> showingList = null;
        try {
        	showingList = theater.getTodayShows(inputJsonString);        	
        }
        catch(ParseException pe) {
        	pe.printStackTrace();
        	return;
        }
        theater.loadSchedule(showingList);
        theater.printSchedule();
        theater.printScheduleJson();
    }
    
    
}
