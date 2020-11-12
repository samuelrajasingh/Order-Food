package com.urk17cs290.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


public class GetBill extends AppCompatActivity {
	static String bill;
	String toAddr;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_bill);


		SharedPreferences myprefs;
		myprefs = getSharedPreferences("myprefs",MODE_PRIVATE);
		name=myprefs.getString("name","Sam");
		toAddr=myprefs.getString("toAddr", "rajasinghsamuelb@karunya.edu.in");

		Intent g =getIntent();
		bill = g.getStringExtra("bill");
		TextView billView = findViewById(R.id.bill);
		billView.setText(bill);


		Button getBill = findViewById(R.id.buttonGetBill);
		getBill.setOnClickListener(v -> new SendMail().execute());
	}

	private class SendMail extends AsyncTask<String, Integer, Void> {

		private ProgressDialog progressDialog;


		@Override
		protected void onPreExecute() {
			super.onPreExecute();


			progressDialog = ProgressDialog.show(GetBill.this, "Please wait", "Sending Bill", true, false);
			if(toAddr.isEmpty()||toAddr.equals(null))
				toAddr=String.valueOf(R.string.myMail);
			if(name.isEmpty() || name.equals(null))
				name=String.valueOf(R.string.name);

		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			progressDialog.dismiss();
		}

		protected Void doInBackground(String... params) {
			Mail m = new Mail("rajasinghsamuelb@karunya.edu.in", "ctgpbbqrhthgyayg");
			String[] toArr = {toAddr, "rajasinghsamuelb@karunya.edu.in"};

			java.util.Date date=new java.util.Date();
			m.setTo(toArr);
			m.setFrom("rajasinghsamuelb@karunya.edu.in");
			m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device on"+date);
			m.setBody("Hello "+name+"\nYour Bill for Today "+date+"\n"+bill);
			Log.e("TAG", "doInBackground: mail body  =\n  "+m.getBody()+"\ntoAddr : "+toAddr+"\n mail :\n"+m.toString());

			try {
				if(m.send()) {
					runOnUiThread(() -> {
						Toast.makeText(GetBill.this, "Bill was sent successfully.", Toast.LENGTH_LONG).show();
					});

				} else {
					runOnUiThread(() -> {
						Toast.makeText(GetBill.this, "Bill was not sent.", Toast.LENGTH_LONG).show();
					});
				}
			} catch(Exception e) {
				Log.e("TAG", "Could not send email", e);
			}
			return null;
		}
	}
}