package Parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;



public class YagoParser implements Iparser{

	final static String YAGO_TYPES_FILE = "yagoSimpleTypes.ttl";
	final static String YAGO_FACTS_FILE = "yagoFacts.ttl";
	final static String YAGO_LITERAL_FACTS_FILE = "yagoLiteralFacts.ttl";
	final static String YAGO_LABELS_FILE = "yagoLabels.ttl";
	final static String YAGO_WIKIPEDIA_FILE = "yagoWikipediaInfo.ttl";
	final static String ACTED_IN = "actedIn";
	final static String DIRECTED = "directed";
	final static String CREATED_ON = "wasCreatedOnDate";
	final static String DURATION = "hasDuration";
	final static String LABEL = "rdfs:label";
	final static String FAMILY_NAME = "hasFamilyName";
	final static String FIRST_NAME = "hasGivenName";
	final static String WIKI = "hasWikipediaUrl";
	final static String PREFERRED_MEAN = "skos:prefLabel";
	final static String MOVIE_ID = "106613686";
	final static String ACTOR_ID = "109765278";
	final static String DIRECTOR_ID = "110088200";

	private HashMap<String, Movie> moviesTable; // key= movie name, value = Movie object
	private HashMap<String,Person> actorsTable; // key= actor name, value = Person object
	private HashMap<String,Person> directorsTable; // key = director name, value = Person object


	/* constructor */
	public YagoParser(){
		this.moviesTable = new HashMap<String,Movie>();
		this.actorsTable = new HashMap<String,Person>();
		this.directorsTable = new HashMap<String,Person>();
	}


	/* public function */

	/** expect to get the file yagoSimpleTpyes.ttl and populate movieTable, actorTable, directorTable */
	public void parseYagoTypes(String path){
		//check that the path is correct (the right yago file)
		if (isFileCorrect(path, YAGO_TYPES_FILE)){			
			try{
				// create a buffered reader for the current file
				BufferedReader br = new BufferedReader(new FileReader(path));					
				String[] strArr;
				//parse the next line by tabs to String[3]
				while ((strArr = parseLine2Array(br)) != null){ 					
					//in case of Movie Entity
					if(strArr.length >= 3 && strArr[2].contains(MOVIE_ID)){
						moviesTable.put(strArr[0],new Movie(strArr[0])); 
						continue;
					}
					//in case of Actor Entity	
					else if(strArr.length >= 3 && strArr[2].contains(ACTOR_ID)){
						actorsTable.put(strArr[0], new Person(strArr[0]));
						continue;
					}
					//in case of Director Entity	
					else if(strArr.length >= 3 && strArr[2].contains(DIRECTOR_ID)){
						directorsTable.put(strArr[0], new Person(strArr[0]));
						continue;
					}					
				}
				br.close();
				return;
			} 
			catch(Exception ex){
				//to-do
			}			
		}
		else
			System.out.println("Wrong Path. Please Provide a correct path");
	}

	/** expect to get the file yagoFacts.ttl and update the fats: "actedIn" and "directed" */
	public void parseYagoFacts(String path){
		//make sure the path is not null or empty and that it's the correct file
		if (isFileCorrect(path,YAGO_FACTS_FILE)){			
			try{
				// create a buffered reader for the current file
				BufferedReader br = new BufferedReader(new FileReader(path));
				String[] strArr;	
				//read line and parse it by tabs	
				while((strArr = parseLine2Array(br)) != null){ 			
					//if it contains "actedIn" or "directed" add that fact
					if(strArr.length >= 3 && (strArr[1].contains(ACTED_IN) || strArr[1].contains(DIRECTED))){
						addFact(strArr);
					}
				}
				br.close();
				return;
			} 
			catch(Exception ex){
				System.out.println(ex.toString());
			}	
		}
	}

	/** expect the filename yagoLiteralFacts and it updates: "createdOnDate" and "duration" */
	public void parseYagoLiteralFacts(String path){
		if (isFileCorrect(path,YAGO_LITERAL_FACTS_FILE)){			
			try{
				// create a buffered reader for the current file
				BufferedReader br = new BufferedReader(new FileReader(path));
				String[] strArr;
				while((strArr = parseLine2Array(br)) != null){
					//add to the movie the literal if it is createdOnDate or Duration
					if(strArr.length >= 3 && (strArr[1].contains(CREATED_ON) || strArr[1].contains(DURATION))){
						addLiteral(strArr);
					}
				}
				br.close();
				return;
			}
			catch(Exception ex){
				System.out.println(ex.toString());
			}
		}
	}

	@Deprecated
	/** expect the filename yagoLabels.ttl and update the correct labels */
	public void parseYagoLabels(String path){
		if(isFileCorrect(path, YAGO_LABELS_FILE)){
			try{
				// create a buffered reader for the current file
				BufferedReader br = new BufferedReader(new FileReader(path));
				String[] strArr;
				while((strArr = parseLine2Array(br)) != null){
					//add to the movie the literal if it is createdOnDate or Duration
					if(strArr.length >= 3 && strArr[1].contains(PREFERRED_MEAN)){
						addLabelPrefMean(strArr);
					}
					else if (strArr.length >= 3 &&( strArr[1].contains(FIRST_NAME) || strArr[1].contains(FAMILY_NAME))){
						addLabelNames(strArr);
					}
				}
				br.close();
				return;
			}
			catch(Exception ex){
				System.out.println(ex.toString());
			}	
		}		
	}

	public void parseYagoWikiInfo(String path){
		if(isFileCorrect(path, YAGO_WIKIPEDIA_FILE)){
			try{
				// create a buffered reader for the current file
				BufferedReader br = new BufferedReader(new FileReader(path));
				String[] strArr;
				while((strArr = parseLine2Array(br)) != null){
					//add to the movie the literal if it is createdOnDate or Duration
					if(strArr.length >= 3 && strArr[1].contains(WIKI)){
						addWikiInfo(strArr);
					}
				}
				br.close();
				return;
			}
			catch(Exception ex){
				System.out.println(ex.toString());
			}	
		}	
	}
	
	
	/* helper functions */

	/** check that the filePath is correct and it's the right fileName from yago*/
	private boolean isFileCorrect(String path, String fileName2compare){
		//make sure the path is not null or empty and that it's the correct file
		if (path != null && !path.isEmpty() && new File(path).getName().compareTo(fileName2compare)== 0)
			return true;
		else{
			System.out.println("Wrong path! you gave path = " +path + ". we expected = " + fileName2compare);
			return false;
		}
	}

	/**get a BufferedReader, read the next line and split line by tabs*/
	private String[] parseLine2Array(BufferedReader br){		
		String line;
		String[] strArr = new String[3]; //array with 3 cells to parse each line
		try{
			if((line = br.readLine()) != null){ //read line	
				strArr = line.split("\\t"); //split the line by tabs - we know there are 2 tabs in each line				
				return strArr;
			}
			else
				return null;
		}
		catch (Exception ex){
			System.out.println("Could not read line");
		}
		return null;
	}

	/** add the fact: <actor> <actedIn> <movie> OR <director> <directed> <movie>*/
	private void addFact(String[] strArr){		
		//get the movie name from strArr[2]
		String currentMovie = strArr[2].substring(0, strArr[2].length()-2);
		//get the person name from strArr[0]
		String personName = strArr[0];
		//get the movie object and check it's not null
		Movie movie = getMoviesTable().get(currentMovie);
		if(movie == null){
			System.out.println("No such movie object with the name: " + currentMovie);
			return;
		}
		//in case of an actor
		if (strArr[1].contains(ACTED_IN)){
			Person actor = getActorsTable().get(personName);
			//add the Actor to the Movie actorsList
			movie.addActor(actor);	
		}
		//in case of a director
		else{
			Person director = getDirectorsTable().get(personName);
			movie.setDirector(director);	
		}
	}

	/** add the literal: <movie> <wasCreatedOnDate> <date> OR <movie> <hasDuration> <duration> */
	private void addLiteral(String[] strArr) {
		//get the movie name from strArr[0]
		String currentMovie = strArr[0];
		//get the literal from strArr[2]
		String literal = strArr[2].substring(0, strArr[2].length()-2);
		//get the movie object and check it's not null
		Movie movie = getMoviesTable().get(currentMovie);
		if(movie == null){
			System.out.println("No such movie object with the name: " + currentMovie);
			return;
		}
		//in case of an createdOnDate
		if (strArr[1].contains(CREATED_ON)){
			//add the creation date of the Movie 
			movie.setDateCreated(literal);	
		}
		//in case of a Duration
		else{			
			movie.setDuration(literal);	
		}		
	}

	@Deprecated
	/** add the correct preferred meaning as appear in yago */
	private void addLabelPrefMean(String[] strArr) {
		// get the movie/actor/director
		String entity = strArr[0];
		// get the label from strArr[2]
		String label = strArr[2].substring(0, strArr[2].length()-2);
		// parse the label- cut what comes after '@' including it 
		label = label.split("@")[0];
		if(getMoviesTable().containsKey(entity)){
			Movie movie = getMoviesTable().get(entity);
			movie.setPreferredMean(label);
		}
		else if(getActorsTable().containsKey(entity)){
			Person p = getActorsTable().get(entity);
			p.setPreferredMean(label);
		}
		else if(getDirectorsTable().containsKey(entity)){
			Person p = getDirectorsTable().get(entity);
			p.setPreferredMean(label);
		}
	}

	@Deprecated
	private void addLabelNames(String[] strArr){
		//get the person
		String personName = strArr[0];
		//get the label from strArr[2]
		String label = strArr[2].substring(0, strArr[2].length()-2);

		if(strArr[1].contains(FIRST_NAME)){
			if(getActorsTable().containsKey(personName)){
				Person p = getActorsTable().get(personName);
				p.setFirstName(label);
			}
			else if(getDirectorsTable().containsKey(personName)){
				Person p = getDirectorsTable().get(personName);
				p.setFirstName(label);
			}
		}
		else  if(strArr[1].contains(FAMILY_NAME)){
			if(getActorsTable().containsKey(personName)){
				Person p = getActorsTable().get(personName);
				p.setLastName(label);
			}
			else if(getDirectorsTable().containsKey(personName)){
				Person p = getDirectorsTable().get(personName);
				p.setLastName(label);
			}
		}
	}

	/** add the wikiURL to the movie */
	private void addWikiInfo(String[] strArr) {
		//get the movie name from strArr[0]
		String currentMovie = strArr[0];
		//get the literal from strArr[2]
		String wikiURL = strArr[2].substring(0, strArr[2].length()-2);
		if(getMoviesTable().containsKey(currentMovie)){
			Movie movie = getMoviesTable().get(currentMovie);
			movie.setWikiURL(wikiURL);
		}		
	}

	/* getters */

	public HashMap<String,Movie> getMoviesTable() {
		return moviesTable;
	}

	public HashMap<String,Person> getActorsTable() {
		return actorsTable;
	}

	public HashMap<String,Person> getDirectorsTable() {
		return directorsTable;
	}


	/* interface function implementation */

	@Override
	public HashMap<String, Movie> getMovie(String fileName) {
		return getMoviesTable();
	}

	@Override
	public HashMap<String, Person> getActor(String fileName) {
		return getActorsTable();
	}

	@Override
	public HashMap<String, Person> getDirector(String fileName) {
		return getDirectorsTable();
	}
}