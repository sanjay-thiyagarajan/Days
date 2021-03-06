package project.days.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import project.days.R;

public class MainActivity extends AppCompatActivity {

    private CardView personalCard,groupCard;
    private ImageView settingsIcon;
    private FirebaseAuth mAuth;
    String nickname,currentUserID;
    private DatabaseReference usersRef;
    private TextView greetText;
    private Button FriendsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greetText = findViewById(R.id.main_greet_1);
        mAuth = FirebaseAuth.getInstance();
        FriendsButton = (Button) findViewById(R.id.friends_button);
        currentUserID = mAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Nickname"))
                {
                    nickname = snapshot.child("Nickname").getValue().toString();
                    greetText.setText("Hello " + nickname);
                }
                else
                {
                    nickname = snapshot.child("Name").getValue().toString();
                    greetText.setText("Hello " + nickname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        personalCard = findViewById(R.id.inner_card_1);
        groupCard = findViewById(R.id.inner_card_2);
        settingsIcon = findViewById(R.id.settings_icon_main);

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });
        personalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent diaryIntent = new Intent(MainActivity.this, DiaryViewActivity.class);
                diaryIntent.putExtra("type","personal");
                startActivity(diaryIntent);
            }
        });

        groupCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent diaryIntent = new Intent(MainActivity.this, DiaryViewActivity.class);
                diaryIntent.putExtra("type","group");
                startActivity(diaryIntent);
            }
        });
        FriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendsIntent = new Intent(MainActivity.this, FriendsActivity.class);
                startActivity(friendsIntent);
            }
        });
    }
}