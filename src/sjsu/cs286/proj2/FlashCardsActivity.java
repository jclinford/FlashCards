package sjsu.cs286.proj2;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class FlashCardsActivity extends ListActivity 
{

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		index = 0;
		fcList = new ArrayList<FlashCards>();
		words = new ArrayList<String>();
		
		fcList.add(new FlashCards("testface", "testsssbackside"));
		fcList.add(new FlashCards("test2face", "testsss2backside!!!!"));

		super.onCreate(savedInstanceState);

		if (getScreenOrientation() == Configuration.ORIENTATION_PORTRAIT)
		{
			setContentView(R.layout.main);

			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);
			setListAdapter(adapter);
		}

		if (getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE)
		{
			setContentView(R.layout.main_landscape);


		}
	}

	//	// Detect orientation changes, and set the appropriate view
	//	@Override
	//	public void onConfigurationChanged(Configuration newConfig)
	//	{
	//		super.onConfigurationChanged(newConfig);
	//		
	//		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
	//			setContentView(R.layout.main_landscape);
	//		else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
	//			setContentView(R.layout.main);
	//	}


	// get the screen orientation
	public int getScreenOrientation()
	{
		Display getOrient = getWindowManager().getDefaultDisplay();
		int orientation = Configuration.ORIENTATION_UNDEFINED;

		if(getOrient.getWidth()==getOrient.getHeight())
		{
			orientation = Configuration.ORIENTATION_SQUARE;
		} else
		{ 
			if(getOrient.getWidth() < getOrient.getHeight())
			{
				orientation = Configuration.ORIENTATION_PORTRAIT;
			}else
			{ 
				orientation = Configuration.ORIENTATION_LANDSCAPE;
			}
		}
		return orientation;
	}

	// Called when OK button in add_word is clicked
	// Just adds the word to the lists, makes a flashCard out of it
	// and redisplays the list to update the adapter
	public void okButtonClicked(View v)
	{
		FlashCards tmpFlashCard;
		String tmpName;
		String tmpDescp;
		String tmpFull;
		EditText nameTextBox;
		EditText descpTextBox;

		// Name and Description textBoxes from the add item view
		nameTextBox = (EditText) findViewById(R.id.nameText);
		descpTextBox = (EditText) findViewById(R.id.definitionText);

		// Convert inputs to strings and store
		tmpName = nameTextBox.getText().toString();
		tmpDescp = descpTextBox.getText().toString();

		// Make a new flash card and store in fcList
		tmpFlashCard = new FlashCards(tmpName, tmpDescp);
		fcList.add(tmpFlashCard);

		// if the description is long, shorten it for display
		if (tmpDescp.length() >= 20)
			tmpDescp = tmpDescp.substring(0, 20);

		// Full display name
		tmpFull = tmpName + ":  " + tmpDescp;

		// add it to list of display words
		words.add(tmpFull);

		// change back to main view
		setContentView(R.layout.main);

		// update the adapter with new words added
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);
		setListAdapter(adapter);

		Log.i("FlashCards", "Added a flash card to list");
	}

	// If the add button is clicked, switch to the add_word view
	public void addButtonClicked(View v)
	{
		setContentView(R.layout.add_word);
		Log.i("FlashCards", "Add button clicked");
	}

	// If the cancel button is clicked, do nothing 
	// and switch back to main view
	public void cancelButtonClicked(View v)
	{
		setContentView(R.layout.main);
		Log.i("FlashCards", "Cancel button clicked");
	}

	// If the next button (in landscape mode) is clicked
	// we increment the index if there are more words
	public void nextButtonClicked(View v)
	{
		if (fcList.size() - 1 != index)
		{
			index++;
			
			// always show face on next click
			showFace = true;
			
			// reset textviews
			displayLandscape();
		}

		Log.i("FlashCards", "Next button clicked");
	}

	public void changeFace(View v)
	{
		showFace = !showFace;
		displayLandscape();
		
		Log.i("FlashCards", "Changing face");
	}
	
	public void displayLandscape()
	{
		TextView textView = (TextView) findViewById(R.id.noteCardText);
		
		// if we are showing face show the word
		if (showFace)
			textView.setText(fcList.get(index).getWord());
		else
			textView.setText(fcList.get(index).getDescp());
	}

	private boolean showFace;
	private int index;
	private ArrayList<String> words;
	private ArrayAdapter<String> adapter;
	private ArrayList<FlashCards> fcList;
}
