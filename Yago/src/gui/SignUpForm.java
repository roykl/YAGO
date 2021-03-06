
// @Override
// public void widgetSelected(SelectionEvent arg0) {
// if(passwordText.getText().equals(repeatPasswordText.getText()))
// display.syncExec(new ThreadAddUser(oparations, userNameText.getText(), passwordText.getText()){
// @Override
// public void run(){
// super.run();
// MessageBox messageBox = new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
// messageBox.setText("Connection Error");
// messageBox.setMessage("Error");
// messageBox.open();
// }
// });
// }
// });



/////////////////////////////////////////////////////////////////////////////////

package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import db.DBOparations;
import db.IdbOparations;

import runnableLogic.AddUser;
import viewModelLayer.InputVerifier;

public class SignUpForm extends Shell {
	private Text userNameText;
	private Text passwordText;
	private Text repeatPasswordText;

	/**
	 * Launch the application.
	 * @param args
	 */
	// public static void main(String args[]) {
	// try {
	// Display display = Display.getDefault();
	// SignUpForm shell = new SignUpForm(display);
	// shell.open();
	// shell.layout();
	// while (!shell.isDisposed()) {
	// if (!display.readAndDispatch()) {
	// display.sleep();
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * Create the shell.
	 * @param display
	 */
	public SignUpForm(final Display display, final IdbOparations oparations) {
		super(display, SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE);

		setImage(SWTResourceManager.getImage(SignUpForm.class, "/movies.png"));
		setLayout(null);

		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(0, 0, 800, 589);

		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				dispose();
				//open login window
				LoginWindow loginShell = new LoginWindow(display,oparations);
				loginShell.open();
				loginShell.layout();
				while (!loginShell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});
		btnNewButton.setBounds(269, 439, 90, 30);
		btnNewButton.setText("Cancel");

		Button btnSign = new Button(composite, SWT.NONE);
		btnSign.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(!InputVerifier.verifyUsername(userNameText.getText())){ // illegal user name
					MessageBox messageBox = new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
					messageBox.setText("Illegal Username");
					messageBox.setMessage("Username must contain 3 - 12 chars\n Only letters or numbers allowed.");
					messageBox.open();
				}else if(!InputVerifier.verifyPass(passwordText.getText())){ // invalid password
					MessageBox messageBox = new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
					messageBox.setText("Illegal Password");
					messageBox.setMessage("Password must contain 3 - 12 chars.");
					messageBox.open();
				}

				else if(passwordText.getText().equals(repeatPasswordText.getText())){
					display.syncExec(new AddUser(oparations, userNameText.getText(), passwordText.getText()){
						@Override
						public void run(){
							super.run();
							int result = this.getValue();
							MessageBox messageBox = new MessageBox(display.getActiveShell(),SWT.ICON_INFORMATION);
							if (result == 0){

								messageBox.setText("Error");
								messageBox.setMessage("Connection Error");
								messageBox.open();

							}else if(result == 1){
								messageBox.setText("Welcome");
								messageBox.setMessage("New user was added: " + userNameText.getText());
								messageBox.open();

								// open main menu
								int idUser = userNameText.getText().hashCode();
								dispose();
								MainMenu MainMenuShell = new MainMenu(display,oparations,false, idUser);
								MainMenuShell.open();
								MainMenuShell.layout();
								while (!MainMenuShell.isDisposed()) {
									if (!display.readAndDispatch()) {
										display.sleep();
									}
								}

							}
							else {
								messageBox.setText("Warning");
								messageBox.setMessage("User already exist : " + userNameText.getText());
								messageBox.open();
							}

						}


					});
				}
				else //passwords not identical
				{
					MessageBox messageBox = new MessageBox(display.getActiveShell(), SWT.ICON_WARNING);
					messageBox.setText("Warning");
					messageBox.setMessage("Passwords are not identical");
					messageBox.open();
				}
			}

		});
		btnSign.setBounds(488, 439, 90, 30);
		btnSign.setText("Submit");

		repeatPasswordText = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		repeatPasswordText.setBounds(269, 345, 309, 26);

		passwordText = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		passwordText.setBounds(269, 294, 309, 26);

		userNameText = new Text(composite, SWT.BORDER);
		userNameText.setBounds(269, 241, 309, 26);

		Label signupBackground = new Label(composite, SWT.NONE);
		signupBackground.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		signupBackground.setImage(SWTResourceManager.getImage(SignUpForm.class, "/red-fabric-background-800x600-captioned.jpg"));
		signupBackground.setBounds(0, 0, 800, 589);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Signup To MovIt!");
		setSize(806, 622);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}


}