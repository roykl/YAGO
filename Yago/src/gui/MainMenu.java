package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import db.IdbOparations;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.layout.GridData;

import thread_logic.MultiThreadSearch;
import thread_logic.ThreadSearch;
import viewModelLayer.InputVerifier;
import viewModelLayer.MovieInfo;
import viewModelLayer.MoviesResults;
import viewModelLayer.SearchQueries;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;



public class MainMenu extends Shell {
	private Text txtMovieTitle;
	private Text actorTxtBox3;
	private Text actorTxtBox2;
	private Text actorTxtBox1;
	private Table genreTable;
	private Text text;
	private Display display;
	private IdbOparations operations;


	/**
	 * Create the shell.
	 * @param display
	 */ //
	public MainMenu(final Display display, final IdbOparations operations,final boolean isAdmin,final int idUser) {
		super(display, SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE);
		this.display = display;
		this.operations = operations;
		setSize(1168, 671);
		setBackground(SWTResourceManager.getColor(128, 0, 0));
		setImage(SWTResourceManager.getImage(MainMenu.class, "/movies.png"));
		setLayout(new FormLayout());

		final ExpandBar expandBar = new ExpandBar(this, SWT.V_SCROLL);
		expandBar.setSpacing(10);
		expandBar.setBackground(SWTResourceManager.getColor(128, 0, 0));
		FormData fd_expandBar = new FormData();
		fd_expandBar.left = new FormAttachment(0);
		expandBar.setLayoutData(fd_expandBar);

		Composite composite = new Composite(this, SWT.NONE);
		fd_expandBar.right = new FormAttachment(composite, -13);
		composite.setBackground(SWTResourceManager.getColor(0, 0, 0));
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0);
		fd_composite.right = new FormAttachment(100, 5);
		fd_composite.bottom = new FormAttachment(0, 638);
		fd_composite.left = new FormAttachment(0, 292);
		composite.setLayoutData(fd_composite);

		final List searchResultsList = new List(composite, SWT.V_SCROLL);
		searchResultsList.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		searchResultsList.setItems(new String[] {});
		searchResultsList.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		searchResultsList.setBounds(10, 10, 855, 497);

		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(MainMenu.class, "/video screen.jpg"));
		lblNewLabel.setBounds(0, 10, 875, 628);

		txtMovieTitle = new Text(this, SWT.BORDER);
		txtMovieTitle.setToolTipText("Enter a movie title");
		txtMovieTitle.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		FormData fd_txtMovieTitle = new FormData();
		fd_txtMovieTitle.right = new FormAttachment(composite, -24);
		fd_txtMovieTitle.left = new FormAttachment(0, 10);
		txtMovieTitle.setLayoutData(fd_txtMovieTitle);

		Button btnSearch = new Button(this, SWT.NONE);
		fd_expandBar.bottom = new FormAttachment(btnSearch, -6);

		ExpandItem xpndtmNewExpanditem = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem.setText("Genre");

		genreTable = new Table(expandBar, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		genreTable.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		xpndtmNewExpanditem.setControl(genreTable);

		TableItem tableItem = new TableItem(genreTable, SWT.NONE);
		tableItem.setText("Horror");

		TableItem tableItem_1 = new TableItem(genreTable, SWT.NONE);
		tableItem_1.setText("Documentary");

		TableItem tableItem_2 = new TableItem(genreTable, SWT.NONE);
		tableItem_2.setText("Sci-Fi");

		TableItem tableItem_3 = new TableItem(genreTable, SWT.NONE);
		tableItem_3.setText("History");

		TableItem tableItem_4 = new TableItem(genreTable, SWT.NONE);
		tableItem_4.setText("Western");

		TableItem tableItem_5 = new TableItem(genreTable, SWT.NONE);
		tableItem_5.setText("Romance");

		TableItem tableItem_6 = new TableItem(genreTable, SWT.NONE);
		tableItem_6.setText("Musical");

		TableItem tableItem_7 = new TableItem(genreTable, SWT.NONE);
		tableItem_7.setText("Animation");

		TableItem tableItem_8 = new TableItem(genreTable, SWT.NONE);
		tableItem_8.setText("Mystery");

		TableItem tableItem_9 = new TableItem(genreTable, SWT.NONE);
		tableItem_9.setText("War");

		TableItem tableItem_10 = new TableItem(genreTable, SWT.NONE);
		tableItem_10.setText("News");

		TableItem tableItem_11 = new TableItem(genreTable, SWT.NONE);
		tableItem_11.setText("Crime");

		TableItem tableItem_12 = new TableItem(genreTable, SWT.NONE);
		tableItem_12.setText("Drama");

		TableItem tableItem_13 = new TableItem(genreTable, SWT.NONE);
		tableItem_13.setText("Music");

		TableItem tableItem_14 = new TableItem(genreTable, SWT.NONE);
		tableItem_14.setText("Short");

		TableItem tableItem_15 = new TableItem(genreTable, SWT.NONE);
		tableItem_15.setText("Sport");

		TableItem tableItem_16 = new TableItem(genreTable, SWT.NONE);
		tableItem_16.setText("Fantasy");

		TableItem tableItem_17 = new TableItem(genreTable, SWT.NONE);
		tableItem_17.setText("Reality-TV");

		TableItem tableItem_18 = new TableItem(genreTable, SWT.NONE);
		tableItem_18.setText("Film-Noir");

		TableItem tableItem_19 = new TableItem(genreTable, SWT.NONE);
		tableItem_19.setText("Adventure");

		TableItem tableItem_20 = new TableItem(genreTable, SWT.NONE);
		tableItem_20.setText("Thriller");

		TableItem tableItem_21 = new TableItem(genreTable, SWT.NONE);
		tableItem_21.setText("Action");

		TableItem tableItem_22 = new TableItem(genreTable, SWT.NONE);
		tableItem_22.setText("Comedy");

		TableItem tableItem_23 = new TableItem(genreTable, SWT.NONE);
		tableItem_23.setText("Biography");

		TableItem tableItem_24 = new TableItem(genreTable, SWT.NONE);
		tableItem_24.setText("Family");
		xpndtmNewExpanditem.setHeight(120);
		FormData fd_btnSearch = new FormData();
		fd_btnSearch.top = new FormAttachment(0, 473);
		fd_btnSearch.left = new FormAttachment(0, 51);
		fd_btnSearch.right = new FormAttachment(0, 223);
		btnSearch.setLayoutData(fd_btnSearch);
		btnSearch.setText("Search");

		Label lblMovieTitle = new Label(this, SWT.NONE);
		fd_txtMovieTitle.top = new FormAttachment(lblMovieTitle, 12);
		lblMovieTitle.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMovieTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		FormData fd_lblMovieTitle = new FormData();
		fd_lblMovieTitle.bottom = new FormAttachment(100, -591);
		fd_lblMovieTitle.left = new FormAttachment(0, 10);
		lblMovieTitle.setLayoutData(fd_lblMovieTitle);
		lblMovieTitle.setText("Movie title");

		Label lblAdvancedSearch = new Label(this, SWT.NONE);
		fd_txtMovieTitle.bottom = new FormAttachment(lblAdvancedSearch, -30);
		lblAdvancedSearch.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		fd_expandBar.top = new FormAttachment(0, 135);

		ExpandItem xpndtmDirectorName = new ExpandItem(expandBar, 0);
		xpndtmDirectorName.setText("Director");

		Composite directorTxtBox = new Composite(expandBar, SWT.NONE);
		xpndtmDirectorName.setControl(directorTxtBox);
		directorTxtBox.setLayout(new FormLayout());

		text = new Text(directorTxtBox, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(0, 10);
		fd_text.right = new FormAttachment(100, -25);
		fd_text.left = new FormAttachment(0, 32);
		text.setLayoutData(fd_text);
		xpndtmDirectorName.setHeight(50);

		ExpandItem xpndtmActors = new ExpandItem(expandBar, 0);
		xpndtmActors.setText("Actors");

		Composite composite_4 = new Composite(expandBar, SWT.NONE);
		xpndtmActors.setControl(composite_4);
		composite_4.setLayout(new FormLayout());

		actorTxtBox1 = new Text(composite_4, SWT.BORDER);
		FormData fd_actorTxtBox1 = new FormData();
		actorTxtBox1.setLayoutData(fd_actorTxtBox1);

		actorTxtBox2 = new Text(composite_4, SWT.BORDER);
		fd_actorTxtBox1.bottom = new FormAttachment(100, -51);
		FormData fd_actorTxtBox2 = new FormData();
		fd_actorTxtBox2.right = new FormAttachment(actorTxtBox1, 0, SWT.RIGHT);
		fd_actorTxtBox2.left = new FormAttachment(actorTxtBox1, 0, SWT.LEFT);
		fd_actorTxtBox2.top = new FormAttachment(actorTxtBox1, 6);
		actorTxtBox2.setLayoutData(fd_actorTxtBox2);

		actorTxtBox3 = new Text(composite_4, SWT.BORDER);
		fd_actorTxtBox1.right = new FormAttachment(actorTxtBox3, 0, SWT.RIGHT);
		fd_actorTxtBox1.left = new FormAttachment(actorTxtBox3, 0, SWT.LEFT);
		FormData fd_actorTxtBox3 = new FormData();
		fd_actorTxtBox3.bottom = new FormAttachment(100, -83);
		fd_actorTxtBox3.left = new FormAttachment(0, 35);
		fd_actorTxtBox3.right = new FormAttachment(100, -26);
		actorTxtBox3.setLayoutData(fd_actorTxtBox3);
		xpndtmActors.setHeight(118);

		ExpandItem xpndtmYear = new ExpandItem(expandBar, 0);
		xpndtmYear.setText("Years");

		Composite composite_5 = new Composite(expandBar, SWT.NONE);
		xpndtmYear.setControl(composite_5);
		composite_5.setLayout(null);

		Label lblNewLabel_1 = new Label(composite_5, SWT.NONE);
		lblNewLabel_1.setBounds(47, 13, 48, 20);
		lblNewLabel_1.setText("From:");

		final Spinner yearFromSpinner = new Spinner(composite_5, SWT.BORDER);
		yearFromSpinner.setTextLimit(4);
		yearFromSpinner.setMaximum(2013);
		yearFromSpinner.setMinimum(1900);
		yearFromSpinner.setBounds(106, 13, 62, 22);

		Label lblTo = new Label(composite_5, SWT.NONE);
		lblTo.setText("To:");
		lblTo.setBounds(47, 60, 48, 20);

		final Spinner yearToSpinner = new Spinner(composite_5, SWT.BORDER);
		yearToSpinner.setTextLimit(4);
		yearToSpinner.setMaximum(2100);
		yearToSpinner.setMinimum(1900);
		yearToSpinner.setSelection(2103);
		yearToSpinner.setBounds(106, 57, 62, 22);
		xpndtmYear.setHeight(115);

		final ExpandItem xpndtmNewExpanditem_1 = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem_1.setText("Language");

		final ScrolledComposite scrolledComposite_1 = new ScrolledComposite(expandBar, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		xpndtmNewExpanditem_1.setControl(scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);

		final List languageList = new List(scrolledComposite_1, SWT.BORDER | SWT.V_SCROLL);
		languageList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.keyCode == SWT.CR)
					xpndtmNewExpanditem_1.setExpanded(false);
			}
		});
		languageList.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		languageList.setItems(new String[] {"English", "Hindi", "Spanish", "French", "Italian", "German", "Malayalam", "Telugu", "Japanese", "Bengali", "Danish", "Dutch", "Filipino", "Portuguese", "Russian", "Arabic", "Hebrew"});
		scrolledComposite_1.setContent(languageList);
		scrolledComposite_1.setMinSize(languageList.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		xpndtmNewExpanditem_1.setHeight(110);
		lblAdvancedSearch.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		FormData fd_lblAdvancedSearch = new FormData();
		fd_lblAdvancedSearch.bottom = new FormAttachment(expandBar, -1);
		fd_lblAdvancedSearch.left = new FormAttachment(0, 10);
		lblAdvancedSearch.setLayoutData(fd_lblAdvancedSearch);
		lblAdvancedSearch.setText("Advanced search options");

		if(isAdmin){
			Button btnImport = new Button(this, SWT.NONE);
			btnImport.addSelectionListener(new SelectionAdapter() {
				@Override
				//Import button pressed
				public void widgetSelected(SelectionEvent arg0) {
					Import(display,operations);
				}
			});
			btnImport.setText("Import");
			FormData fd_btnImport = new FormData();
			fd_btnImport.left = new FormAttachment(btnSearch, 0, SWT.LEFT);
			fd_btnImport.top = new FormAttachment(btnSearch, 19);
			fd_btnImport.right = new FormAttachment(btnSearch, 0, SWT.RIGHT);
			btnImport.setLayoutData(fd_btnImport);
		}



		//Search button listener - TODO: complete extracting data from search parameters
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			//Search button pressed
			public void widgetSelected(SelectionEvent arg0) {
				boolean enteredMovieName = false;
				boolean performSearch = true;

				SearchQueries sq = new SearchQueries();
				
				//final boolean importRunnig ;
				final AtomicBoolean searchRunning = new AtomicBoolean(true);
				//progress bar thread
				new Thread() {
					@Override
					public void run(){

						JFrame theFrame =  new JFrame();
						showProgressBar(theFrame);

						while(searchRunning.get()){
							try {
								sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						theFrame.dispose();

						return;

					}
				}.start();
				
				
			//disable form
				setEnabledRecursive(display.getActiveShell(), false);
				
				

					
				
				

				//check if user entered movie name
				if (!sq.createWheres(txtMovieTitle.getText())){
					// the user didn't entered movie name so we use the advanced properties
					System.out.println("user didn't enter movie name");

					//check director name is correct
					if(!text.getText().trim().isEmpty() && !InputVerifier.verifyInput(text.getText())){
						// director was entered but it's not correct
						MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
						messageBox.setText("Illegal Director Name");
						messageBox.setMessage("Director Name should not contain ' or " + '"');
						messageBox.open();	
						return;
					}

					//check actor name is correct
					if((!actorTxtBox1.getText().trim().isEmpty() &&  !InputVerifier.verifyInput(actorTxtBox1.getText()))
							|| (!actorTxtBox2.getText().trim().isEmpty() &&  !InputVerifier.verifyInput(actorTxtBox2.getText()))
							|| (!actorTxtBox3.getText().trim().isEmpty() &&  !InputVerifier.verifyInput(actorTxtBox3.getText()))){
						// actor was added but it's not correct

						MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
						messageBox.setText("Illegal Actor Name");
						messageBox.setMessage("Actor Name should not contain ' or " + '"');
						messageBox.open();	
						return;						
					}

					System.out.println(yearFromSpinner.getSelection());

					//check years are correct
					if(yearFromSpinner.getSelection() > yearToSpinner.getSelection()){
						MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
						messageBox.setText("Illegal Year Format");
						messageBox.setMessage("From can't be greater than To");
						messageBox.open();	
						return;
					}

					String language = null;
					if(languageList.getSelectionCount() > 0)
						language = languageList.getSelection()[0];

					sq.createWheresFromProperties(genreTable.getItems(), text.getText(), actorTxtBox3.getText(),
							actorTxtBox2.getText(), actorTxtBox1.getText(), yearFromSpinner.getSelection(),yearToSpinner.getSelection(), language);

					System.out.println("-----------------------------------");
					System.out.println("SELECT - " + sq.selectProp);
					System.out.println("FROM- " + sq.fromProp);
					System.out.println("WHERE- " + sq.whereProp);



					Display.getCurrent().syncExec(new ThreadSearch(operations, sq.selectProp, sq.fromProp, sq.whereProp){
						@Override
						public void run(){
							super.run();
							moviesIds = this.getResult();
							//	sq.createFromMoviesIds(moviesIds);
							return;
						}
					});

					sq.createFromMoviesIds(moviesIds);
					moviesIds= null;
				}
				else {
					// user enter movieName 
					enteredMovieName = true;
					// verify that the input is ok
					if (!InputVerifier.verifyInput(txtMovieTitle.getText())){
						// input not ok
						MessageBox messageBox =  new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
						messageBox.setText("Illegal Movie Title");
						messageBox.setMessage("Movie Title should not contain ' or " + '"');
						messageBox.open();	
						return;
					}


				}
				if (sq.preformSearch){
					System.out.println("**********************************************");
					System.out.println("SELECT - " + SearchQueries.MOVIE_SELECT);
					System.out.println("FROM- " + SearchQueries.MOVIE_FROM);
					System.out.println("WHERE- " + sq.whereMovie);

					display.syncExec(new MultiThreadSearch(operations, SearchQueries.MOVIE_SELECT, SearchQueries.MOVIE_FROM, sq.whereMovie + " order by grade desc, year desc",
							SearchQueries.GENRES_SELECT,  SearchQueries.GENRES_FROM,  sq.whereGenre,
							SearchQueries.ACTORS_SELECT, SearchQueries.ACTORS_FROM, sq.whereActor){
						@Override		
						public void run(){
							super.run();
//							try {
//								this.join();
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
							ResultSet resultMovie = this.getResultMovie();
							ResultSet resultGenre = this.getResultGenre();
							ResultSet resultActor = this.getResultActor();

							moviesRes = new MoviesResults();
							moviesRes.setResultsMoive(resultMovie);
							moviesRes.setResultsGenre(resultGenre);
							moviesRes.setResultsActors(resultActor);
							moviesRes.addYoutubeAndPoster();

							System.out.println(moviesRes.getMoviesResult().toString());
							return;
						}
					});
					displaySearchResults(searchResultsList, moviesRes.getMoviesResult(),idUser);
					moviesRes = null;
				}			
				else{
					// displaySearchResults(searchResultsList, null);
				}
				searchRunning.set(false);
				//enable form
				setEnabledRecursive(display.getActiveShell(), true);
			}
		});
		
		createContents();
	}


	private void massiveImport(Display display1, IdbOparations operations1 ){
		display1.syncExec(new thread_logic.ThreadImport(operations1){
			@Override
			public void run(){
				super.run();

			}
		});	
	}

	ResultSet moviesIds;
	MoviesResults moviesRes;

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MovIt!");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	private void Import(final Display display, IdbOparations operations ){

		MessageBox messageBox = new MessageBox(display.getActiveShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
		messageBox.setText("Import");
		messageBox.setMessage("This action may take a few minutes.\nDo you wish to continue? ");
		int buttonID = messageBox.open();
		switch(buttonID) {
		case SWT.YES: //call import thread

			//final boolean importRunnig ;
			final AtomicBoolean importRunnig = new AtomicBoolean(true);
			//progress bar thread
			new Thread() {
				@Override
				public void run(){

					JFrame theFrame =  new JFrame();
					showProgressBar(theFrame);

					while(importRunnig.get()){
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					theFrame.dispose();

					return;

				}
			}.start();

			//import thread
			display.syncExec(new thread_logic.ThreadImport(operations){


				@Override
				public void run(){
					//disable form
					setEnabledRecursive(display.getActiveShell(), false);
	try {
		sleep(10000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} //super.run();
	//				super.run();
					importRunnig.set(false);
					//enable form
					setEnabledRecursive(display.getActiveShell(), true);
					return;
				}
			});	
			break; 

		case SWT.NO:
			//dispose();
			break;

		}
	}

	private void showProgressBar( JFrame theFrame){
		final JProgressBar aJProgressBar = new JProgressBar(0, 100);
		aJProgressBar.setIndeterminate(true);
		aJProgressBar.setBackground(Color.DARK_GRAY);
		//
		theFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
		JLabel label1 = new JLabel("Processing...",SwingConstants.CENTER );


		Container contentPane = theFrame.getContentPane();
		contentPane.add(aJProgressBar, BorderLayout.SOUTH);
		contentPane.add(label1, BorderLayout.NORTH);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//on top
		theFrame.setResizable(false);
		theFrame.setUndecorated(true);
		theFrame.setSize(500, 40);
		theFrame.setLocation(450,350);
		theFrame.show();

	}


	public static void setEnabledRecursive(final Composite composite, final boolean enabled){
		//Check.notNull(composite, "composite"); //$NON-NLS-1$
		
		Control[] children = composite.getChildren();

		for (int i = 0; i < children.length; i++)
		{
			if (children[i] instanceof Composite)
			{
				setEnabledRecursive((Composite) children[i], enabled);
			}
			else
			{
				children[i].setEnabled(enabled);
				children[i].setVisible(enabled);
			}
		}

		composite.setEnabled(enabled);
		
	}

	/**
	 * 
	 * @param searchResultsList
	 * @param moviesResult
	 */
	@SuppressWarnings("unchecked")
	private void displaySearchResults(final List searchResultsList, final ArrayList<MovieInfo> moviesResult,final int idUser) {
		//clear the list of previous results
		searchResultsList.removeAll();
		//no search results found
		if(moviesResult == null || moviesResult.isEmpty()){
			searchResultsList.add("Oops...Your search did not return any matches");
		}
		else{
			//add search results to the list
			for (MovieInfo movieInfo : moviesResult) {
				searchResultsList.add(movieInfo.movieName);
			}
			//one result opens movie details immediately
			if(moviesResult.size() == 1){
				//TODO - change idUser to meaningful
				MovieDetails detailsShell = new MovieDetails(display, operations, idUser,moviesResult.get(0));
				dispose();
				detailsShell.open();
				detailsShell.layout();
				while (!detailsShell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
			
			searchResultsList.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					//TODO - change idUser to meaningful
					
					int selectionIndex = searchResultsList.getSelectionIndex();
			        if(selectionIndex < 0)
			        	return;
					MovieDetails detailsShell = new MovieDetails(display, operations, idUser, 
							moviesResult.get(searchResultsList.getSelectionIndex()));
					
					System.out.println("************************"+moviesResult.get(searchResultsList.getSelectionIndex()).movieName+"********************************************");
					for (MovieInfo movieInfo : moviesResult) {
						System.out.println(movieInfo.movieName);
					}
					System.out.println("**************************************************************************************************************************");
					dispose();
					detailsShell.open();
					detailsShell.layout();
					while (!detailsShell.isDisposed()) {
						if (!display.readAndDispatch()) {							
							display.sleep();
						}
					}
					
					System.out.println("Disposed");
				}
			});
		}
	}

	 

}
