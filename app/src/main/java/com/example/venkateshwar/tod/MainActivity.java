package com.example.venkateshwar.tod;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> arrayList = new ArrayList<>();

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void updateCloud(DatabaseReference mLearnings, ArrayList<String> arrayList) {
        if (isNetworkAvailable()) {
            mLearnings.setValue(arrayList).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mLearnings = mDatabase.child("Learnings");
        ImageButton gimme = (ImageButton) findViewById(R.id.button);
        ImageButton add = (ImageButton) findViewById(R.id.button2);
        final TextView textView = (TextView) findViewById(R.id.textView);
        final EditText editText = (EditText) findViewById(R.id.editText);
        ImageButton all = (ImageButton) findViewById(R.id.button3);
        mLearnings.keepSynced(true);
        if (textView != null) {
            textView.setMovementMethod(new ScrollingMovementMethod());
            textView.setLongClickable(true);
            textView.setTextIsSelectable(true);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
        }

        mLearnings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> l = (List<String>) dataSnapshot.getValue();
                int i = 0;
                while (i < l.size()) {
                    if (i >= arrayList.size()) {
                        arrayList.add(l.get(i));
                    } else if (!(arrayList.get(i).equals(l.get(i)))) {
                        arrayList.set(i, l.get(i));
                    }
                    i++;
                }
                Toast.makeText(MainActivity.this, "Successfully Synced.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        final ArrayList<String> arrayList =new ArrayList<>(Arrays.asList("people first-", "technical next", "running behind money wont earn us money", "run behind knowledge/learning and it\'ll earn us money", "family first", "keep working and keep learning", "respect be respectful", "get out of that \'middle class\' tag", "don\'t learn half or leave something halfway", "don\'t learn something which is useless", "have specific and unrealistic goals(becoming a better person isn\'t a goal)", "go out of your comfort zone", "hard work will stretch you in a way so you grow", "focus your energy in a more directed path", "people spend money on you because they take value from you", "Current education system sucks", "patch your gaps(both in knowledge and personally)", "develop personality", "manage your sleep", "prioritize things", "learning by self, using google, internet etc", "Everything exists for a reason", "Everything happens for a reason", "Everyone comes into your life with a reason. They either teach you something or make you forget something.", "eft-emotional freedom techniques", "resolve stuff that condition us", "deschooling", "you can\'t complete something just by spending time on it", "only if we work on it can we achieve it", "few things are not meant to be taken to heart. if you dont know those few things, then explore those few things.", "you have talent. don\'t worry too much. you\'ll definitely get a job. might take time. not immediately but definitely.", "there shall be stress", "10-70-20", "whichever company we go, we will find someone who is better than us. if we dont, then we are in a bad company :P ;)", "no panicking if our peers work better or faster than us. we\'re either under stress when we underperform or it is beyond our current potency", "what we can do is, work harder either to get the skillset required.", "if anyone doesn\'t deserve to be in our life, cut them", "keep yourself occupied to relieve from depression", "world would be a much better place if humans are treated as humans", "world would be a better place if everyone did their works sincerely and honestly all their life"));
//        final ArrayList<String> arrayList =new ArrayList<>();
//        final HashSet<String> arrayList=new HashSet<String>();
//        arrayList.addAll(Arrays.asList("People First-", "Technical Next", "Running Behind Money Wont Earn Us Money", "Run Behind Knowledge/Learning And It'Ll Earn Us Money", "Family First", "Keep Working And Keep Learning", "Respect Be Respectful", "Get Out Of That 'Middle Class' Tag", "Don'T Learn Half Or Leave Something Halfway", "Don'T Learn Something Which Is Useless", "Have Specific And Unrealistic Goals(Becoming A Better Person Isn'T A Goal)", "Go Out Of Your Comfort Zone", "Hard Work Will Stretch You In A Way So You Grow", "Focus Your Energy In A More Directed Path", "People Spend Money On You Because They Take Value From You", "Current Education System Sucks", "Patch Your Gaps(Both In Knowledge And Personally)", "Develop Personality", "Manage Your Sleep", "Prioritize Things", "Learning By Self, Using Google, Internet Etc", "Everything Exists For A Reason", "Everything Happens For A Reason", "Everyone Comes Into Your Life With A Reason. They Either Teach You Something Or Make You Forget Something.", "Eft-Emotional Freedom Techniques", "Resolve Stuff That Condition Us", "Deschooling", "You Can'T Complete Something Just By Spending Time On It", "Only If We Work On It Can We Achieve It", "Few Things Are Not Meant To Be Taken To Heart. If You Dont Know Those Few Things, Then Explore Those Few Things.", "You Have Talent. Don'T Worry Too Much. You'Ll Definitely Get A Job. Might Take Time. Not Immediately But Definitely.", "There Shall Be Stress", "10-70-20", "Whichever Company We Go, We Will Find Someone Who Is Better Than Us. If We Dont, Then We Are In A Bad Company :P ;)", "No Panicking If Our Peers Work Better Or Faster Than Us. We'Re Either Under Stress When We Underperform Or It Is Beyond Our Current Potency", "What We Can Do Is, Work Harder Either To Get The Skillset Required.", "If Anyone Doesn'T Deserve To Be In Our Life, Cut Them", "Keep Yourself Occupied To Relieve From Depression", "World Would Be A Much Better Place If Humans Are Treated As Humans", "World Would Be A Better Place If Everyone Did Their Works Sincerely And Honestly All Their Life"));
        if (gimme != null) {
            gimme.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getApplicationContext(), "Gimme one", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
        if (all != null) {
            all.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getApplicationContext(), "Show All", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
        if (add != null) {
            add.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getApplicationContext(), "Add", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
        if (all != null && textView != null) {
            all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringBuffer s1 = new StringBuffer();
                    int i = 0;

                    for (String str : arrayList) {
                        s1.append(i + 1 + ". " + str + "\n");
                        i++;
                    }
                    textView.setText(s1);
                }
            });
        }
        try {
            InputStream inputStream = getApplicationContext().openFileInput("config.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                while ((receiveString = bufferedReader.readLine()) != null) {
                    if (!arrayList.contains(receiveString) && !(receiveString.isEmpty())) {
                        arrayList.add(receiveString);
                    }
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "login activity" + "File not found: " + e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "login activity" + "Can not read file: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        assert gimme != null;
        gimme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = (int) (Math.random() * arrayList.size());
                assert textView != null;
                textView.setText(arrayList.get(x));
            }
        });

        if (add != null) {
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String s = editText.getText().toString();
                        if (arrayList.contains(s)) {
                            Toast.makeText(getApplicationContext(), "Already learnt! Try something new next time.", Toast.LENGTH_LONG).show();
                        } else if (s.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Oy! Write something!", Toast.LENGTH_LONG).show();
                        } else if (!s.isEmpty() && !arrayList.contains(s)) {
                            arrayList.add(s);
                        }
                        OutputStreamWriter outputStream = new OutputStreamWriter(getApplicationContext().openFileOutput("config.txt", Context.MODE_APPEND));

                        outputStream.write(s + "\n");
                        outputStream.close();
                        editText.setText("");
                        updateCloud(mLearnings, arrayList);
                    } catch (IOException e) {
                        Log.e("Exception", "File write failed: " + e.toString());
                    }

                }
            });
        }


    }
}
