package com.urk17cs290.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class OrderFood extends AppCompatActivity {

	CheckBox ChickenBriyani;
	CheckBox VegBriyani;
	CheckBox EggNoodles;
	CheckBox VegNoodles;
	CheckBox FriedRice;
	CheckBox Burger;
	Button buttonPlaceOrder;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_food);
		addListenerOnButtonClick();
	}
	public void addListenerOnButtonClick(){
		//Getting instance of CheckBoxes and Button from the activty_main.xml file
		ChickenBriyani= findViewById(R.id.chickenBriyani);
		VegBriyani= findViewById(R.id.vegBriyani);
		VegNoodles= findViewById(R.id.vegNoodles);
		FriedRice = findViewById(R.id.friedRice);
		EggNoodles= findViewById(R.id.eggNoodles);
		Burger = findViewById(R.id.burger);

		buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);

		//Applying the Listener on the Button click
		buttonPlaceOrder.setOnClickListener((View.OnClickListener) view -> {
			int totalamount=0;
			StringBuilder result=new StringBuilder();
			result.append("Selected Items:");
			if(ChickenBriyani.isChecked()){
				result.append("\nChickenBriyani - 100");
				totalamount+=100;
			}
			if(VegBriyani.isChecked()){
				result.append("\nVeg Briyani - 80");
				totalamount+=80;
			}
			if(EggNoodles.isChecked()){
				result.append("\nEggNoodles - 60");
				totalamount+=60;
			}

			if(VegNoodles.isChecked()){
				result.append("\nVegNoodles - 50");
				totalamount+=50;
			}

			if(FriedRice.isChecked()){
				result.append("\nFriedRice - 110");
				totalamount+=110;
			}

			if(Burger.isChecked()){
				result.append("\nBurger - 100");
				totalamount+=100;
			}

			result.append("\nTotal: " + "Rs ").append(totalamount);
			//Displaying the message on the toast
			Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
			Log.e("TAG", "bill: \n "+result );
			Intent i =new Intent(this,GetBill.class);
			i.putExtra("bill",result.toString());
			startActivity(i);
		});
	}
}