package com.urk17cs290.orderfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

	static String OTP;
	static int otp;
	Random r = new Random();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button regButton = findViewById(R.id.reg);
		regButton.setOnClickListener(view -> new SendMail().execute(""));
	}

	public void onClick(View view) {
		TextInputLayout password =findViewById(R.id.password);
		int  pass= Integer.parseInt(password.getEditText().getText().toString().trim());
		Log.e("TAG", "pass: "+pass );
		if (OTP.equals(Integer.toString(pass))) {
			Intent order = new Intent(MainActivity.this,OrderFood.class);
			startActivity(order);
		}
		else
			Toast.makeText(MainActivity.this,"Invalid Password",Toast.LENGTH_LONG).show();
	}

	private class SendMail extends AsyncTask<String, Integer, Void> {

		private ProgressDialog progressDialog;

		TextInputLayout toAddrInputLayout = findViewById(R.id.email);
		String toAddr=toAddrInputLayout.getEditText().getText().toString();
		TextInputLayout nameEd=findViewById(R.id.name);
		String name=nameEd.getEditText().getText().toString();



		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Sending mail", true, false);
			if(toAddr.isEmpty()||toAddr.equals(null))
				toAddr=toAddrInputLayout.getPlaceholderText().toString().trim();
			if(name.isEmpty() || name.equals(null))
				name=nameEd.getPlaceholderText().toString().trim();
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			progressDialog.dismiss();
		}

		protected Void doInBackground(String... params) {
			Mail m = new Mail(getString(R.string.myMail), getString(R.string.mailPassword));

			String[] toArr = {toAddr, "rajasinghsamuelb@karunya.edu.in"};

			//OTP
			int number = r.nextInt(9999);
			OTP = String.format("%04d",number);
			int otp = number;

			Log.e("TAG", "otp: "+otp );

			m.setTo(toArr);
			m.setFrom("rajasinghsamuelb@karunya.edu.in");
			m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
			m.setBody("Hello "+name+",\nPassword for login in Order Food App -> "+otp);

			try {
				if(m.send()) {
					runOnUiThread(() -> {
						Toast.makeText(MainActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
					});
				} else {
					runOnUiThread(() -> {
						Toast.makeText(MainActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
					});
				}
			} catch(Exception e) {
				Log.e("MailApp", "Could not send email", e);
			}
			SharedPreferences myprefs;
			myprefs = getSharedPreferences("myprefs",MODE_PRIVATE);
			myprefs.edit().putString("name",name).apply();
			myprefs.edit().putString("toAddr",toAddr).apply();
			return null;

		}
	}


}