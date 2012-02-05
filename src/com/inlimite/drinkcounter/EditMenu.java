package com.inlimite.drinkcounter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditMenu extends Activity
{
	protected String[] drinks;
	protected DatabaseHelper database;
	protected ListView deleteList;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editdrinks);
        
        LinearLayout focus = (LinearLayout) findViewById(R.id.Focus);
        focus.requestFocus();
        
        //connect to the database
        database = new DatabaseHelper(this);
        
        //get list of drinks currently in db
        drinks = database.getAllDrinks();
                
        //create the list of drinks to be deleted.
        createDrinkList();
    }
    
    @Override
    public void onPause()
    {
    	super.onPause();
    	
    	database.close();
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	//update the list of drinks
    }
    
    public void createDrinkList()
    {
    	deleteList = (ListView) findViewById(R.id.ListViewDrinks);
    	
    	//Create array of string to ad into the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.menulist, R.id.list, drinks); 
        //add array to spinner
        deleteList.setAdapter(adapter);    	
    }
    
    public void deleteItem(View view)
    {
    	LinearLayout row = (LinearLayout) view.getParent();
    	TextView drink = (TextView) row.getChildAt(0);
    	
    	database.deleteDrink((String) drink.getText());
    	
    	updateScreen();
    }
    
    private void updateScreen()
    {
    	finish();
    	startActivity(getIntent());
    }

    //FIX!!!!!!!!!!!!!
    public void addNewDrink(View view)
    {    	
    	EditText text = (EditText) findViewById(R.id.FieldNewDrink);
    	
    	database.addDrink(text.getText().toString());
    	
    	//notify the user of action
	    Toast.makeText(this, text.getText().toString() + " has been added to the database.", Toast.LENGTH_SHORT).show();
	    
	    updateScreen();
    }

    public void resetAll(View view)
	{		
		database.resetAllCounts();
		
		//notify the user of action
	    Toast.makeText(this, "All counts cleared.", Toast.LENGTH_SHORT).show();
	    
	    updateScreen();
	}
	
	public void clearAll(View view)
	{
		database.deleteAllDrinks();
		
		//notify the user of action
	    Toast.makeText(this, "All data has been removed.", Toast.LENGTH_SHORT).show();
	    
	    updateScreen();
	}
	
	public void restoreDefault(View view)
	{
		database.restoreDefaultDrinks();
		
		//notify the user of action
	    Toast.makeText(this, "Drinks have been restored to default.", Toast.LENGTH_SHORT).show();
	    
	    updateScreen();
	}
}
