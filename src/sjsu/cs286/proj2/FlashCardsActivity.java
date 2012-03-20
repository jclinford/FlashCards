package sjsu.cs286.proj2;

import java.util.ArrayList;
import java.util.Random;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * 
 * @author John Linford, Shailesh Benake
 * 
 *         A FlashCard reader/organizer for Android.
 * 
 *         Allows creating, deleting and saving flashCards for viewing/studying
 */

public class FlashCardsActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		fcList = new ArrayList<FlashCards>();
		dbStart = new DatabaseHelp(this);
		index = 0;

		// open the database
		dbStart.open();
		fcList = dbStart.lreadDb();

		// If we are in portrait mode, use the list adapters and set content
		// view appropriately
		if (getScreenOrientation() == Configuration.ORIENTATION_PORTRAIT) {
			setContentView(R.layout.main);
			words = dbStart.readDb();
			Log.i("", "before String call");
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, words);
			Log.i("", "Before setting adapter");
			setListAdapter(adapter);

		}

		// else we are in landscape mode, setup flipper for animation and set
		// content view
		if (getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE && fcList != null) {
			setContentView(R.layout.main_landscape);
			flipper = (ViewFlipper) findViewById(R.id.viewFlipper1);

			fcList = dbStart.lreadDb();

			// Start index at a random position
			if (fcList != null){
				Random r = new Random();
				Double rand = r.nextDouble();
				index = dbStart.readPersistence();
				//index = (int) (rand * (fcList.size()));
			}
			else 
				setContentView(R.layout.main_landscape);

			// update landscape view with new index
			displayLandscape();
		}



		// Close DB
		dbStart.close();
	}
	
	public void onPause(){
		dbStart.updateDb(index);
	}

	// get the screen orientation
	public int getScreenOrientation() {
		Display getOrient = getWindowManager().getDefaultDisplay();
		int orientation = Configuration.ORIENTATION_UNDEFINED;

		if (getOrient.getWidth() == getOrient.getHeight()) {
			orientation = Configuration.ORIENTATION_SQUARE;
		} else {
			if (getOrient.getWidth() < getOrient.getHeight()) {
				orientation = Configuration.ORIENTATION_PORTRAIT;
			} else {
				orientation = Configuration.ORIENTATION_LANDSCAPE;
			}
		}
		return orientation;
	}

	public void onName(View v) {
		showKeyboard(v);
	}

	// Called when OK button in add_word is clicked
	// Just adds the word to the lists, makes a flashCard out of it
	// and redisplays the list to update the adapter
	public void okButtonClicked(View v) {

		String tmpName;
		String tmpDescp;
		EditText nameTextBox;
		EditText descpTextBox;

		// Name and Description textBoxes from the add item view
		nameTextBox = (EditText) findViewById(R.id.nameText);
		descpTextBox = (EditText) findViewById(R.id.definitionText);

		// Convert inputs to strings and store
		tmpName = nameTextBox.getText().toString();
		tmpDescp = descpTextBox.getText().toString();

		// Make a new flash card and store it to the DB
		dbStart.writeDb(tmpName, tmpDescp);
		// re-read from DB so they are alphabetical
		words = dbStart.readDb();
		hideKeyboard(v);
		// change back to main view
		setContentView(R.layout.main);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, words);
		setListAdapter(adapter);

		dbStart.close();

		Log.i("FlashCards", "Added a flash card to list");

	}

	// If the add button is clicked, switch to the add_word view
	public void addButtonClicked(View v) {

		setContentView(R.layout.add_word);

		Log.i("FlashCards", "Add button clicked");
	}

	// If the cancel button is clicked, do nothing
	// and switch back to main view
	public void cancelButtonClicked(View v) {

		hideKeyboard(v);
		setContentView(R.layout.main);
		Log.i("FlashCards", "Cancel button clicked");
	}

	// When delete button is clicked
	// First call a dialog to check if the user really wants to delete
	public void deleteAllClicked(View v) {
		// Dialog builder
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to delete List?")
		.setCancelable(false)
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				FlashCardsActivity.this.deleteAll();
				adapter.clear();
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

		AlertDialog confirmation = builder.create();
		confirmation.show();
	}

	// If we confirm we go here to delete everything
	public void deleteAll() {
		Log.i("", "delete button clicked");
		dbStart.deleteFlash();
		adapter.clear();
		words = dbStart.readDb();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, words);
		setListAdapter(adapter);

		dbStart.close();
		setContentView(R.layout.main);
	}

	public DatabaseHelp getDb() {
		return dbStart;
	}

	// If the next button (in landscape mode) is clicked
	// we increment the index if there are more words
	public void nextButtonClicked(View v) {
		Random r = new Random();
		Double rand = r.nextDouble();

		// if we are looking at the back change to front face
		if (!showFace)
			changeFace(v);

		// test to see if we got the same random index
		int tmpIndex = index;
		while (index == tmpIndex) {
			rand = r.nextDouble();
			index = (int) (rand * (fcList.size()));
		}
		dbStart.updateDb(index);
		// reset textviews
		displayLandscape();

		Log.i("FlashCards", "Next button clicked");
	}

	// Called when the user taps on the landscape view
	// flips the card from back to front, or front to back
	public void changeFace(View v) {
		// switch the face
		showFace = !showFace;

		// animate the change
		if (showFace) {
			flipper.setInAnimation(flipToFront());
			flipper.setOutAnimation(flipToBack());
			flipper.showNext();
		} else {
			flipper.setInAnimation(flipToFront());
			flipper.setOutAnimation(flipToBack());
			flipper.showPrevious();
		}

		// redraw the textview
		displayLandscape();

		Log.i("FlashCards", "Changing face");
	}

	// updates the textView's contents
	public void displayLandscape() {
		// if we are showing face show the word
		if (showFace) {
			TextView textView = (TextView) findViewById(R.id.noteCardText);
			textView.setText(fcList.get(index).getWord());

		} else {
			TextView textView = (TextView) findViewById(R.id.noteCardText_back);
			textView.setText(fcList.get(index).getDescp());
		}
	}

	// called when your in the addWord view and click outside the textFields
	public void hideKeyboard(View v) {
		// hide the keyboard
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	}

	public void showKeyboard(View v) {
		// show the keyboard
		InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// only will trigger it if no physical keyboard is open
		mgr.showSoftInput(getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
	}

	// Animation to flip to the front face
	private Animation flipToFront() {
		Animation flip = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
		flip.setDuration(1000);
		flip.setInterpolator(new AccelerateInterpolator());

		return flip;
	}

	// Animation to flip to the back face
	private Animation flipToBack() {
		Animation flip = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f);
		flip.setDuration(500);
		flip.setInterpolator(new AccelerateInterpolator());
		return flip;
	}

	DatabaseHelp dbStart;
	private boolean showFace = true;
	private int index;
	static ArrayList<String> words = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	ArrayList<FlashCards> fcList;
	ViewFlipper flipper;

}
