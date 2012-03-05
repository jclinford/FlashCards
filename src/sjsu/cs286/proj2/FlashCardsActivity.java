package sjsu.cs286.proj2;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class FlashCardsActivity extends ListActivity 
{

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		fcList = new ArrayList<FlashCards>();
		words = new ArrayList<String>();

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);
		setListAdapter(adapter);
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

	private ArrayList<String> words;
	private ArrayAdapter<String> adapter;
	private ArrayList<FlashCards> fcList;
}
