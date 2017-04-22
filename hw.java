import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.apache.http.client.fluent.Request;
import java.io.IOException;
import java.util.ArrayList;

// All imports from the solution have been included. You can import more classes, though it is confirmed
// that the assignment can be completed with the above imports only. This includes the bonus question.

// The Song class and this MusicRatings class will both be in the default package during graded.

/**
 * The MusicRating class is a collection of methods used to process data from the music ratings API.
 *
 * API url: http://fury.cse.buffalo.edu/musicRatings
 */
public class MusicRatings {


    /**
     * 5 points
     *
     * Returns the average rating of the given Song object. This method assumes the Song class is
     * in the default package.
     *
     * @param song  A Song object
     * @return the average rating of the song
     */
    public static double averageRating(Song song){
    	double total = 0;
    	for(int ans: song.getRatings()){
    		total += ans;
    	}
    	double answer = total/(song.getRatings().size());
        return answer;
    }



    /**
     * 5 points
     *
     * Converts a JSON Object into a Song object. This method assumes that the input is in the format:
     * {"title":"Lose Yourself", "artist":"Eminem", "ratings":[5,5,4,5], "youtubeID":"xFYQQPAOz7Y"}
     *
     * @param jsonSong  A song in JSON format
     * @return A Song object with the data from the JSON Value
     */
    
    
    // Find a way to get the key value in the JsonObject once you do that you have to assign the key and value in the Song class. 
    // * find a way to acces keys and values?? 
    public static Song jsonToSong(JsonObject jsonSong){
    	ArrayList<Integer> ratings = new ArrayList<Integer>();
    	for (String number: jsonSong.get("ratings").toString().split(",")){
    		if(number.contains("[")){
    			StringBuilder sb = new StringBuilder(number);
    			sb.deleteCharAt(0);
    			number = sb.toString();
    		}if(number.contains("]")){
    			StringBuilder sb = new StringBuilder(number);
    			sb.deleteCharAt(1);
    			number = sb.toString();
    		}
    		ratings.add(Integer.parseInt(number));
    	}
    	Song answer = new Song(jsonSong.get("youtubeID").asString(), jsonSong.get("title").asString(), jsonSong.get("artist").asString(), ratings);
    	
    	return answer;
    }


    /**
     * 10 points
     *
     * Return every song from the music ratings API as an ArrayList of Song objects.
     *
     * A JSON string containing the information for every rated song can be obtained with a GET
     * request to the url: http://fury.cse.buffalo.edu/musicRatings/getAllSongs
     * The format of the returned string is a JSON list of songs represented as JSON objects in the
     * same format as the input of the previous method. To see this format and the data you can paste
     * the url into a web browser.
     *
     * @return An ArrayList containing all the songs from the music ratings API
     */
    public static ArrayList<Song> getAllSongsFromAPI() {
    	String response = "";
        try{
            response = Request.Get("http://fury.cse.buffalo.edu/musicRatings/getAllSongs").execute().returnContent().asString();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        JsonValue jsonValue = Json.parse(response);
        JsonArray jsonArray = jsonValue.asArray();
        ArrayList<Song> answer = new ArrayList<Song>();
        for(JsonValue item: jsonArray){
        	JsonObject newest = item.asObject();
        	answer.add(jsonToSong(newest));
        }
        return answer;
    }


    /**
     * 10 points
     *
     * Returns the song from the music ratings API that has been rated the most number of times as
     * a Song object. The rating of the songs should not be considered in this method, only the number
     * of times they have been rated. Ties can be broken arbitrarily.
     *
     * @return A Song with the most reviews
     */
    public static Song getMostRatedSong(){
    	ArrayList<Song> songList = new ArrayList<Song>();
    	songList = getAllSongsFromAPI();
    	Song answer = null;
    	int number = 0;
    	for(Song ans: songList){
    		if(ans.getRatings().size() >= number){ 
    			number = ans.getRatings().size();
    			answer = ans;
    		}
    	}
    	
        return answer;
    }


    /**
     * 10 points
     *
     * Returns an ArrayList containing all songs from the song ratings API with at least "minRating" average
     * rating and at least "minNumberOfRatings" number of ratings. The songs can be listed in any order.
     *
     * @param minRating  minimum rating of returned songs
     * @param minNumberOfRatings  minimum number of ratings of returned songs
     * @return all songs from the song ratings API with >= minRating and >= minNumberOfRatings
     */
    public static ArrayList<Song> getPlaylist(double minRating, int minNumberOfRatings){
    	ArrayList<Song> songList = new ArrayList<Song>();
    	songList = getAllSongsFromAPI();
    	ArrayList<Song> emptyList = new ArrayList<Song>();
    	
    	for(Song item: songList){
    		if((averageRating(item) >= minRating) && (item.getRatings().size() >= minNumberOfRatings)){
    			emptyList.add(item);
    		}
    	}
        return emptyList;
    }


    /**
     * Challenge Question
     * Bonus: 10 points
     *
     * Returns an ArrayList of the top k songs sorted by the bayesian average of their ratings.
     *
     * references:
     * https://en.wikipedia.org/wiki/Bayesian_average
     * http://fulmicoton.com/posts/bayesian_rating/
     *
     * For m use the average rating cast across all songs. Note that this is not the average of
     * all the average ratings, but the average of all individual ratings. For example if the API
     * contained only 2 songs with ratings [4,4,4] and [5] m would be 4.25.
     *
     * For C use the average number of ratings across all songs. In the example with 2 songs with
     * ratings [4,4,4] and [5] C would be 2. C is not necessarily an integer so if the 2 songs had 
     * ratings [4,4] and [5] C would be 1.5.
     *
     * @return The k best songs based on the bayesian average of their ratings starting with
     *         the best song at index 0
     */
    public static ArrayList<Song> getBestPlaylist(int k){
        // Earning credit for this question will require self-learning. The concepts needed to complete
        // this method have not been covered in this course, or were only covered briefly.

        return null;
    }



    public static void main(String[] args) {
    }

}
