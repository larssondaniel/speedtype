package your.HelloAndroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelloAndroidActivity extends Activity {

	TextView text;
	Button button;
	int i = 2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		button = (Button) findViewById(R.id.ok);
		text = (TextView) findViewById(R.id.textView1);
		button.setOnClickListener(clickListener);
	}

	// Listener object to handle the click events
	View.OnClickListener clickListener = new View.OnClickListener() {
		public void onClick(View v) {
			text.setText("Ord " + i);
			i++;
		}
	};
}