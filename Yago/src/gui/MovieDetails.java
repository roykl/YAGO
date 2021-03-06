package gui;

//TODO: give constructor the user id and movie id
//TODO: make movie name lable  

import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import runnableLogic.Grade;
import runnableLogic.Search;
import runnableLogic.UserUpdate;
import viewModelLayer.InputVerifier;
import viewModelLayer.MovieInfo;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import db.IdbOparations;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class MovieDetails extends Shell {
	private MovieInfo movie;
	private Text txtMovieName;
	private Text txtDirector;
	private Text txtYear;
	private Text txtLanguage;
	private Text txtPlot;
	private Label lblMovitGrade;
	private Text txtRating;
	private Label lblActors;
	private Button btnRankIt;
	private int idMovie; // TODO
	private Label lblNumberOfRankers;
	private Text txtNumOfRankers;
	private Label lblWiki;
	private Text txtWikiurl;
	private int grade;
	private Text genresTxt;
	private Label lblUsers;
	private Composite composite;
	private Text txtRated;
	private Composite composite_1;
	private Composite composite_2;
	private Composite composite_3;
	private Composite composite_4;
	private Text txtDuration;
	private Label lblDurationmin;
	private Composite composite_6;
	private Button btnTrailer;
	AddRemoveWindow shell;
	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public MovieDetails(final Display display, final IdbOparations operations,
			final int idUser, final MovieInfo movie) {

		super(display, SWT.SHELL_TRIM);

		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		setSize(1042, 769);
		setMinimumSize(new Point(1030, 760));
		setText("MovIt!");
		setImage(SWTResourceManager.getImage(MovieDetails.class, "/movies.png"));
		this.movie = movie;

		try{
			setLayout(new FormLayout());
			composite = new Composite(this, SWT.NONE);
			FormData fd_composite = new FormData();
			fd_composite.bottom = new FormAttachment(100, -10);
			fd_composite.top = new FormAttachment(0);
			fd_composite.left = new FormAttachment(0);
			composite.setLayoutData(fd_composite);
			composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			composite.setLayout(new FormLayout());

			composite_4 = new Composite(composite, SWT.NONE);
			FormData fd_composite_4 = new FormData();
			fd_composite_4.top = new FormAttachment(0, 191);
			fd_composite_4.left = new FormAttachment(0, 10);
			composite_4.setLayoutData(fd_composite_4);

			Browser posterBrowser = new Browser(composite_4, SWT.BORDER);
			posterBrowser.setBounds(0, 0, 343, 474);
			posterBrowser.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));


			composite_1 = new Composite(composite, SWT.BORDER);
			FormData fd_composite_1 = new FormData();
			fd_composite_1.bottom = new FormAttachment(0, 300);
			fd_composite_1.right = new FormAttachment(0, 1016);
			fd_composite_1.top = new FormAttachment(0, 142);
			fd_composite_1.left = new FormAttachment(0, 606);
			composite_1.setLayoutData(fd_composite_1);
			composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			lblUsers = new Label(composite_1, SWT.NONE);
			lblUsers.setBounds(351, 26, 46, 38);
			lblUsers.setText("users");
			lblUsers.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblUsers.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblUsers.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			final Combo userRank = new Combo(composite_1, SWT.READ_ONLY);
			userRank.setBounds(150, 70, 88, 42);
			userRank.setItems(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});

			txtNumOfRankers = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
			txtNumOfRankers.setBounds(202, 23, 132, 26);
			txtNumOfRankers.setText("0");
			txtNumOfRankers.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			lblNumberOfRankers = new Label(composite_1, SWT.NONE);
			lblNumberOfRankers.setBounds(165, 26, 31, 38);
			lblNumberOfRankers.setText("by");
			lblNumberOfRankers.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblNumberOfRankers.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblNumberOfRankers.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			btnRankIt = new Button(composite_1, SWT.NONE);
			btnRankIt.setBounds(105, 118, 187, 28);
			//updating new user rating
			btnRankIt.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if(!userRank.getText().equals("")){
						int grade = Integer.parseInt(userRank.getText());
						updateGrade(display, operations, idUser, grade);
						updateLabel(display, operations);
					}
				}
			});
			btnRankIt.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			btnRankIt.setText("Rate");

			lblMovitGrade = new Label(composite_1, SWT.NONE);
			lblMovitGrade.setBounds(17, 73, 99, 25);
			lblMovitGrade.setText("Your rating");
			lblMovitGrade.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblMovitGrade.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblMovitGrade.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			txtRating = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
			txtRating.setBounds(76, 23, 73, 26);
			txtRating.setText("-");
			txtRating.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			txtRated = new Text(composite_1, SWT.NONE);
			txtRated.setBounds(17, 26, 53, 26);
			txtRated.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			txtRated.setText("Rated");

			composite_2 = new Composite(composite, SWT.BORDER);
			FormData fd_composite_2 = new FormData();
			fd_composite_2.right = new FormAttachment(100, -10);
			fd_composite_2.top = new FormAttachment(composite_1, 6);
			fd_composite_2.bottom = new FormAttachment(0, 495);
			composite_2.setLayoutData(fd_composite_2);
			composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			genresTxt = new Text(composite_2, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
			genresTxt.setBounds(23, 41, 258, 28);

			List actorsList = new List(composite_2, SWT.BORDER | SWT.V_SCROLL);
			actorsList.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			actorsList.setBounds(23, 105, 258, 68);



			Label lblGenre = new Label(composite_2, SWT.NONE);
			lblGenre.setBounds(23, 10, 52, 25);
			lblGenre.setText("Genre");
			lblGenre.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblGenre.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblGenre.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			lblActors = new Label(composite_2, SWT.NONE);
			lblActors.setBounds(23, 75, 60, 28);
			lblActors.setText("Actors");
			lblActors.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblActors.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblActors
			.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			Button btnNewButton = new Button(composite_2, SWT.NONE);
			btnNewButton.setBounds(296, 41, 100, 132);
			btnNewButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// call add remove form
					shell = new AddRemoveWindow(display,operations, movie);
					shell.open();
					shell.layout();
					while (!shell.isDisposed()) {
						if (!display.readAndDispatch()) {
							display.sleep();
						}
					}
				}
			});
			btnNewButton.setText("Add/Remove");

			composite_3 = new Composite(composite, SWT.BORDER);
			fd_composite_2.left = new FormAttachment(composite_3, 8);
			FormData fd_composite_3 = new FormData();
			fd_composite_3.bottom = new FormAttachment(composite_2, 0, SWT.BOTTOM);
			fd_composite_3.top = new FormAttachment(composite_1, 0, SWT.TOP);
			fd_composite_3.left = new FormAttachment(composite_4, 6);
			fd_composite_3.right = new FormAttachment(0, 598);
			composite_3.setLayoutData(fd_composite_3);
			composite_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			Label lblLanguage = new Label(composite_3, SWT.NONE);
			lblLanguage.setBounds(10, 77, 79, 23);
			lblLanguage.setText("Language");
			lblLanguage.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblLanguage.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblLanguage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			txtYear = new Text(composite_3, SWT.BORDER);
			txtYear.setBounds(10, 44, 138, 27);
			txtYear.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			Label lblYear = new Label(composite_3, SWT.NONE);
			lblYear.setBounds(10, 10, 64, 23);
			lblYear.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblYear.setText("Year");
			lblYear.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblYear.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			txtLanguage = new Text(composite_3, SWT.BORDER);
			txtLanguage.setToolTipText("Enter a language. Please use Capital letter at the beginning of the word");
			txtLanguage.setBounds(10, 106, 138, 27);

			txtLanguage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			Label lblNewLabel_2 = new Label(composite_3, SWT.NONE);
			lblNewLabel_2.setBounds(10, 139, 67, 23);
			lblNewLabel_2.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			lblNewLabel_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblNewLabel_2.setText("Director");

			txtDirector = new Text(composite_3, SWT.BORDER);
			txtDirector.setBounds(10, 168, 138, 27);

			txtDirector.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			txtDuration = new Text(composite_3, SWT.BORDER);
			txtDuration.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			txtDuration.setBounds(10, 227, 138, 28);


			lblDurationmin = new Label(composite_3, SWT.NONE);
			lblDurationmin.setText("Duration (min.)");
			lblDurationmin.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblDurationmin.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblDurationmin.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
			lblDurationmin.setBounds(10, 201, 106, 20);

			lblWiki = new Label(composite_3, SWT.NONE);
			lblWiki.setBounds(10, 261, 76, 20);
			lblWiki.setText("Wikipedia");
			lblWiki.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblWiki.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblWiki.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			txtWikiurl = new Text(composite_3, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.SEARCH);
			txtWikiurl.setBounds(10, 292, 215, 30);
			txtWikiurl.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			Button buttonLanguage = new Button(composite_3, SWT.NONE);
			buttonLanguage.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					String lang = movie.language;
					if(lang == null)
						lang ="";
					if(txtLanguage.getText().equals(lang))
						return;
					else if (!InputVerifier.verifyInput(txtLanguage.getText()) || !InputVerifier.verifyInjection(txtLanguage.getText())){
						MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
						messageBox.setText("Illegal Language");
						messageBox.setMessage("Language is Illegal");
						messageBox.open();	
						return;
					}
					else
					{
						update(operations, "Movie", movie.idMovie, movie.idLanguage, "idLanguage", txtLanguage.getText(), null, display);
					}
				}
			});
			buttonLanguage.setText("Update");
			buttonLanguage.setBounds(167, 104, 58, 30);

			Button buttonDirector = new Button(composite_3, SWT.NONE);
			buttonDirector.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					String dire = movie.directorName;
					if(dire == null)
						dire = "";
					if(txtDirector.getText().equals(dire))
						return;
					else if (!InputVerifier.verifyInput(txtDirector.getText()) || !InputVerifier.verifyInjection(txtDirector.getText())){
						MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
						messageBox.setText("Illegal Director");
						messageBox.setMessage("Director name is Illegal");
						messageBox.open();	
						return;
					}
					else
						display.syncExec(new Search(operations, "Distinct idDirector", "Director", "directorName = '" + txtDirector.getText() +"'"){
							@Override
							public void run() {
								super.run();
								ResultSet res = this.getResult();
								try {
									if (res.next()){
										int idDirector = res.getInt(1);

										UserUpdate update = new UserUpdate(operations, "Movie", movie.idMovie, movie.idDirector, "idDirector", idDirector);


										update.run();
										int returnVal;
										returnVal = update.getValue();

										if (returnVal==1){
											MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_INFORMATION);
											messageBox.setText("Your changes have been saved!\nChanges will apply on next search");
											messageBox.setMessage("Successfully Updated!");
											messageBox.open();
										}
										else if(returnVal == 0){
											MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
											messageBox.setText("Error");
											messageBox.setMessage("Value must exist in our  data.");
											messageBox.open();

										}




									}else{
										MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
										messageBox.setText("Error");
										messageBox.setMessage("Value must exist in our  data.");
										messageBox.open();
									}
								} catch (SQLException e) {
									MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
									messageBox.setText("Error");
									messageBox.setMessage("Couldn't update");
									messageBox.open();
								}
							}
						});

				}
			});
			buttonDirector.setText("Update");
			buttonDirector.setBounds(167, 166, 58, 30);

			Label backgroundLabl = new Label(composite, SWT.NONE);
			FormData fd_backgroundLabl = new FormData();
			fd_backgroundLabl.bottom = new FormAttachment(0, 136);
			fd_backgroundLabl.top = new FormAttachment(0);
			fd_backgroundLabl.left = new FormAttachment(0);
			backgroundLabl.setLayoutData(fd_backgroundLabl);
			backgroundLabl.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			backgroundLabl.setForeground(SWTResourceManager.getColor(255, 255, 255));
			backgroundLabl.setImage(SWTResourceManager.getImage(MovieDetails.class, "/movie_clapper.jpg"));
			backgroundLabl.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			composite_6 = new Composite(composite, SWT.BORDER);
			FormData fd_composite_6 = new FormData();
			fd_composite_6.top = new FormAttachment(composite_2, 6);
			fd_composite_6.left = new FormAttachment(composite_4, 9);
			fd_composite_6.right = new FormAttachment(100, -8);
			composite_6.setLayoutData(fd_composite_6);
			composite_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			txtPlot = new Text(composite_6, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.CANCEL);
			txtPlot.setBounds(10, 36, 630, 136);

			txtPlot.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

			Label lblPlot = new Label(composite_6, SWT.SHADOW_IN);
			lblPlot.setBounds(10, 10, 43, 20);
			lblPlot.setText("Plot");
			lblPlot.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			lblPlot.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
			lblPlot.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			txtMovieName = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
			FormData fd_txtMovieName = new FormData();
			fd_txtMovieName.bottom = new FormAttachment(0, 185);
			fd_txtMovieName.right = new FormAttachment(0, 353);
			fd_txtMovieName.top = new FormAttachment(0, 142);
			fd_txtMovieName.left = new FormAttachment(0, 10);
			txtMovieName.setLayoutData(fd_txtMovieName);
			txtMovieName.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
			txtMovieName.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			txtMovieName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));

			btnTrailer = new Button(composite, SWT.NONE);
			fd_composite_6.bottom = new FormAttachment(btnTrailer, 0, SWT.BOTTOM);
			FormData fd_btnTrailer = new FormData();
			fd_btnTrailer.bottom = new FormAttachment(100);
			fd_btnTrailer.top = new FormAttachment(composite_4, 6);
			fd_btnTrailer.right = new FormAttachment(composite_4, 0, SWT.RIGHT);
			fd_btnTrailer.left = new FormAttachment(composite_4, 0, SWT.LEFT);
			btnTrailer.setLayoutData(fd_btnTrailer);
			btnTrailer.setText("View trailer");

			//trying to update all fields
			if(movie.plot != null)
				txtPlot.setText(movie.plot);
			if(movie.duration != null)
				txtDuration.setText(movie.duration);
			if(movie.directorName != null)
				txtDirector.setText(movie.directorName);
			if(movie.language != null)
				txtLanguage.setText(movie.language);
			if(movie.year != null)
				txtYear.setText(movie.year);
			if(movie.posterUrl != null)
				posterBrowser.setUrl(movie.posterUrl);
			if(movie.movieName != null)
				txtMovieName.setText(movie.movieName);
			if(movie.youtubeUrl != null){
				btnTrailer.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						try {
							TrailerWindow shell = new TrailerWindow(display, movie);
							shell.open();
							shell.layout();
							while (!shell.isDisposed()) {
								if (!display.readAndDispatch()) {
									display.sleep();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			if(movie.wikiUrl != null)
				txtWikiurl.setText(movie.wikiUrl);
			if(movie.genresList != null){
				String genresToString = "";
				for (String genre : movie.genresList.values()) {
					genresToString+= genre+"-";
				}
				if(genresToString.length() > 0)
					genresToString = genresToString.substring(0, genresToString.length()-1);
				genresTxt.setText(genresToString);
			}
			if(movie.actorsList != null){
				for (String actor : movie.actorsList.values()) {
					actorsList.add(actor);
				}
			}
			if(movie.numRankers > 0){
				txtRating.setText(""+movie.grade);
				txtNumOfRankers.setText(""+movie.numRankers);
			}

			createContents();
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
	}

	private void update(IdbOparations operations, final String tableName,
			int idMovie, int key2, String column, final String newVal, final Text txtBox,final Display display) {
		display.syncExec(new UserUpdate(operations, tableName, idMovie, key2, column, newVal.hashCode()){
			@Override
			public void run(){	
				super.run();
				int returnVal= this.getValue();
				if (returnVal==OK){
					MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_INFORMATION);
					messageBox.setText("Your changes have been saved!");
					messageBox.setMessage("Successfully Updated!\nChanges will apply on next search");
					messageBox.open();
				}
				else if(returnVal == ERR){
					MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
					messageBox.setText("Error");
					messageBox.setMessage("Value must exist in our " +tableName+ " data.");
					messageBox.open();

				}

			}
		});

	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
	}

	private void updateGrade(Display display, IdbOparations operations, int idUser, int grade) {

		display.syncExec(new Grade(operations, idUser, movie.idMovie, grade) {
			@Override
			public void run() {
				super.run();
			}
		});
	}

	private void updateLabel(Display display, IdbOparations operations) {
		display.syncExec(new Search(operations,
				"grade, numberOfRankers", "MoviesGrades", "idMovie = "
						+ movie.idMovie) {
			@Override
			public void run() {
				System.out.println("IM IN UPDATE LABEL");
				super.run();
				ResultSet rs = null;
				try {
					rs = this.getResult();
					if(rs.next()){
						int numberOfRankers = rs.getInt("numberOfRankers");
						double grade = this.getResult().getDouble("grade");
						txtRating.setText(Double.toString(grade));
						txtNumOfRankers.setText(Integer.toString(numberOfRankers));
					}
					else{					
					}
					// TODO: add number of rankers label
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
