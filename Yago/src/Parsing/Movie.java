package Parsing;

import java.util.ArrayList;
import java.util.List;

public class Movie{

	private String name;
	private String duration;
	private String dateCreated;
	private String genre; // need to do
	private String language; // need to do
	private Person director;
	private String preferredMean; // cancelled
	private List<Person> actorsLst = new ArrayList<Person>();
	private String wikiURL;
	private String youTubeURL; // need to do


	/* constructor */	

	public Movie(String name){
		this.name = name;
	}


	/**adding an actor to the actors List*/
	public void addActor(Person actor) {
		if(actor != null)
			this.actorsLst.add(actor);
	}

	/* getters & setters */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Person getDirector() {
		return director;
	}

	public void setDirector(Person director) {
		this.director = director;
	}

	public List<Person> getActorsLst() {
		return actorsLst;
	}

	public String getWikiURL() {
		return wikiURL;
	}

	public void setWikiURL(String wikiURL) {
		this.wikiURL = wikiURL;
	}

	public String getYouTubeURL() {
		return youTubeURL;
	}

	public void setYouTubeURL(String youuTubeURL) {
		this.youTubeURL = youuTubeURL;
	}


	public String getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}


	public String getPreferredMean() {
		return preferredMean;
	}


	public void setPreferredMean(String preferredMean) {
		this.preferredMean = preferredMean;
	}

}
