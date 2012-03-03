package sjsu.cs286.proj2;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class List_fc extends ListActivity
{	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setListAdapter(new ArrayAdapter<String>(List_fc.this,android.R.layout.simple_list_item_1, items));
		
		selection = (TextView) findViewById(R.id.selection);
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		//Class myClass;
		
			Intent myIntent = new Intent();
		    startActivity(myIntent);	    
	}
	
	public void update()
	{
		for (int i = 0; i < list.size(); i++)
		{
			items[i] = list.get(i).getWord();
		}
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, items));
	}
	
	public void addFlashCard(FlashCards fc)
	{
		list.add(fc);
		//update();
	}
	
	public ArrayList<FlashCards> getList()
	{
		return list;
	}
	
	public void deleteAll()
	{
		// delete list in db
		// delete arraylist list
	}
	
	TextView selection;
	ArrayList<FlashCards> list = new ArrayList<FlashCards>();
	private String[] items = {"Hi", "Bye", "Okay"};
}
