package sjsu.cs286.proj2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FlashCardsActivity extends Activity 
{
	/** Called when the activity is first created. */
	Context context;
	List_fc list;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = getContentView(R.layout.main);
        
        list = new List_fc();
    }
    
	private Context getContentView(int main) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public void okButtonClicked(View v)
	{
		FlashCards tmpFlashCard;
		String tmpName;
		String tmpDescp;
		EditText nameTextBox;
		EditText descpTextBox;
		
		nameTextBox = (EditText) findViewById(R.id.nameText);
		descpTextBox = (EditText) findViewById(R.id.definitionText);
		
		tmpName = nameTextBox.getText().toString();
		tmpDescp = descpTextBox.getText().toString();
		
		tmpFlashCard = new FlashCards(tmpName, tmpDescp);
		list.addFlashCard(tmpFlashCard);
		
		Log.i("FlashCards", "Added a flash card to list");
		
		setContentView(R.layout.main);
	}
	
	public void addButtonClicked(View v)
	{
		setContentView(R.layout.add_word);
	}
	
	public void cancelButtonClicked(View v)
	{
		setContentView(R.layout.main);
	}
}
