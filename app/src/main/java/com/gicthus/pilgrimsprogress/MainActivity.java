package com.gicthus.pilgrimsprogress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ScrollView ;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    Random randomGenerator = new Random();


    String measure = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
    String introA =  null;


    String[] nam = new  String[500] ; //Short name like John
    String[] nameLong = new  String[500] ; // Long name like John the Baptist
    int[] testament = new  int[500] ;
    int[] difficulty = new  int[500] ;
    int[] namIndex = new  int[500] ;
    String[] allNames = new  String[500] ;
    String[] fakeName = new  String[600] ;
    String[] clue = new  String[20] ;  //clues for one person
    String[] ans = new  String[5] ; // The multiple choices

    String scorePrefix = "";

//All the orderings of 4 multiple choices
// Could not get 2D array to work so using 1D array


    int[] ord = {0,1,2,3,4,
            1,2,4,3,
            1,3,2,4,
            1,3,4,2,
            1,4,2,3,
            1,4,3,2,
            2,1,3,4,
            2,1,4,3,
            2,3,1,4,
            2,3,4,1,
            2,4,1,3,
            2,4,3,1,
            3,1,2,4,
            3,1,4,2,
            3,2,1,4,
            3,2,4,1,
            3,4,1,2,
            3,4,2,1,
            4,1,2,3,
            4,1,3,2,
            4,2,1,3,
            4,2,3,1,
            4,3,1,2,
            4,3,2,1} ;

    //Description of each level

    String[] levelDescr = {

            "You are in the City of Destruction.  The city stands as a symbol of the entire world as it is, with all of its sins, corruptions, and sorrows. No one living here can have any hope of salvation.\n",

            "You are wallowing in the Slough of Despond.  It is a swamp, a bog, a quagmire, the first obstacle in your course. Pilgrims are apt to get mired down here by their doubts and fears.\n",

            "You have reached the Village of Morality.  You meet Mr. Worldly Wiseman but your friend Evangelist berates you sharply for having listened to anything Mr. Worldly Wiseman might have to say.\n",

    "You made it to the Wicket Gate which has this sign: 'Knock and it shall be opened unto you.'\n",

    "You have arrived at Interpreter's House.  Interpreter symbolizes the Holy Spirit. Interpreter shows you a number of 'excellent things.' These include a portrait of the ideal pastor with the Bible in his hand and a crown of gold on his head; a dusty parlor which is like the human heart before it is cleansed with the Gospel; a sinner in an iron cage, an apostate doomed to suffer the torments of Hell through all eternity; a wall with a fire burning against it. A figure (the Devil himself) is busily throwing water on the fire to put it out. But he would never succeed, Interpreter explains, because the fire represents the divine spirit in the human heart and a figure on the far side of the wall keeps the fire burning brightly by secretly pouring oil on it — the oil of Christ's Grace.\n",

    "You are at The Cross.  Below it, at the foot of the gentle slope, is an open grave. When you stop by the Cross, the burden of sin on your back suddenly slips from your shoulders, rolls down the slope, and falls into the open grave, to be seen no more. As you stand weeping with joy, three Shining Ones (angels) appear. They tell you all your sins are now forgiven, give you bright new raiment to replace your old ragged clothes, and hand you a parchment, 'a Roll with a seal upon it.' For your edification and instruction, you are to read the Roll as you go along, and when you reach the Pearly Gates, you are to present it as your credentials a sort of passport to Heaven, as it were.\n",

    "You are trudging up Difficulty Hill.  The Holy Way beyond the Cross is fenced in with a high wall on either side. The walls have been erected to force all aspiring Pilgrims to enter the Holy Way in the proper manner, through the Wicket Gate.\n",

    "You are on a narrow path that leads off the Holy Way to the lodge in front of Palace Beautiful. Starting up the path, you see two lions.  They are chained, one on either side of the path, and anyone with faith can pass safely between them if he keeps carefully to the middle of the path.\n",

    "Now you must pass through the Valley of Humiliation.  Here you are attacked and almost overcome by a 'foul fiend' named Apollyon — a hideous monster with scales like a fish, wings like a dragon, mouth like a lion, and feet like a bear; flames and smoke belch out of a hole in his belly. After a painful struggle, you wound the fiend with your sword and you drive him off.\n",

    "You are walking through the Valley of the Shadow of Death.  This is a wilderness, a land of deserts and pits, inhabited only by yowling hobgoblins and other dreadful creatures. The path here is very narrow, edged on one side by a deep, water-filled ditch in which many have drowned; on the other side, by a treacherous bog. Walking carefully, you go on and soon finds yourself close to the open mouth of Hell, the Burning Pit, out of which comes a cloud of noxious fumes, long fingers of fire, showers of sparks, and hideous noises.\n",

    "You have come to Vanity Fair.  Interested only in commerce and money-making, the town holds a year-round fair at which all kinds of things are bought and sold — 'houses, lands, trades, titles, . . . lusts, pleasures, . . . bodies, souls, silver, gold, pearls, precious stones, and what not.' You say that you would buy nothing but the Truth which leads the authorities to jail you for disturbing the peace.\n",

    "You have escaped from jail and come next to Doubting Castle.  You are seized by the prince of By-path Meadow, a giant named Despair. He throws you into a deep dark dungeon, where you lie for days without food or drink. At length, Giant Despair appears, beats you almost senseless, and advises you to take your own life.  You finally remember that you have a key called Promise which will open any lock in Doubting Castle.\n",

    "You have reached the Delectable Mountains where you find gardens, orchards, vineyards, and fountains of water. Four shepherds — Experience, Knowledge, Watchful, and Sincere — come to greet you, telling you that the mountains are the Lord's, as are the flocks of sheep grazing there.\n",

    "You are in the Country of Beulah.  This is a happy land where the sun shines day and night, flowers bloom continuously, and the sweet and pleasant air is filled with bird-song. There is no lack of grain and wine and the Celestial City is now within sight.\n",

    "You have come to the Dark River, a wide, swift-flowing stream. A Shining One appears and tells them that they must make their way across as best they can, that fording the river is a test of faith.\n",

    "On the far side of the river, two Shining Ones are waiting for you and take you by the arm to assist you in climbing the steep slope to the Celestial City, which stands on a 'mighty hill . . . higher than the clouds.' The streets are paved with gold and along them walk many people with crowns on their heads and golden harps in their hands.\n"


};
    String forest = "You are in a beautiful green forest.";

    int[][] maze = new int[16][4];

    //int level = 13 ; // debug 0;
    int level = 0;

    String wayBack = ""; //Opposite of the button that got you into the forest
    boolean goBack = false; //=true if user clicked the opposite of the button that got him into the woods

    // Meaning of 2nd index
    // NEWS
    // 0123
    // 1 - Go to next level & add points
    // 2 - Go to previous level & subtract points
    // 4 - Subtract points
    // 3 - Subtract points
    int choice = -1 ; //integer 1-4 as defined above
    int prevChoice = 1 ; // previous choice.  If it was 3 then 4 is the only valid choice and vice versa
    Boolean inTheWoods = false;

    int score = 30;

    int forward =0; //Index of the direction that goes to the next level (value=1)
    int returnInt =0; //Index of the direction that goes to the previous level (value=1)
    int twoIndex =0; //Index that has the value 2 before switch


    int nc = 0; // # of clues processed
    int nm =0; // Index of the person being guessed
    int nn =0; // No. of Bible names
    int ng =0; // No. of names in set being guessed
    int nf =0; // No. of fake names
    int na =0; // the answer 1-4
    int ic =0; // Index of the chosen clue
    int ntried=0; // No. of questions tried
    int nright=0; //No you got right
    int settingTestament =2 ; // 0=OT, 1=NT, 2=Whole Bible
    int settingHowHard =3 ; // 1-easy, 2=easy+medium, 3=easy, medium, & hard

    boolean answerRight = false;

    File fileEvents = null;
    BufferedReader br = null;

    TextView current = null;


    public final static String EXTRA_MESSAGE = "com.gicthus.pilgrimsprogress.MESSAGE";
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        View view = getWindow().findViewById(Window.ID_ANDROID_CONTENT);

        // Check if user has given permission to record audio
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.KILL_BACKGROUND_PROCESSES);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.KILL_BACKGROUND_PROCESSES}, 1);
            String you = "are here";
        }

        current = findViewById(R.id.current);

        // Hide the direction buttons to begin with
        Button north = findViewById(R.id.north);
        north.setVisibility(View.GONE);
        Button west = findViewById(R.id.west);
        west.setVisibility(View.GONE);
        Button east = findViewById(R.id.east);
        east.setVisibility(View.GONE);
        Button south = findViewById(R.id.south);
        south.setVisibility(View.GONE);
        Button exitApp = findViewById(R.id.exitApp);
        exitApp.setVisibility(View.GONE);

        // Get all bible names to use as wrong answers
        try {
            String tempStr = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("nameOnly.txt")));
            int i = 1;
            tempStr = reader.readLine();
            while (tempStr != null) {
                allNames[i] = tempStr;
                i++;
                tempStr = reader.readLine();
            }
            nn=i-1;
            reader.close();
        } catch (Exception e){
            String you = "are here";
        }

        // Get fake names to use as wrong answers
        try {
            String tempStr = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("fakeNames.txt")));
            int i = 1;
            tempStr = reader.readLine();
            while (tempStr != null) {
                fakeName[i] = tempStr;
                i++;
                tempStr = reader.readLine();
            }
            nf=i-1;
            reader.close();
        } catch (Exception e){
            String you = "are here";
        }


        intro(view);

    }


    public void intro(View view){

        TextView  tv1 = findViewById(R.id.textView1);
        tv1.setText(R.string.intro);
        tv1.setVisibility(View.VISIBLE);

        // Get  names based on testament and difficulty
        try {
            String tempStr = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("namesRanked.txt")));
            int i = 1;
            tempStr = reader.readLine();
            while (tempStr != null) {
                String[] arrSplit = tempStr.split("#");
                int ntest = Integer.valueOf(arrSplit[1]);
                int howHard = Integer.valueOf(arrSplit[2]);
                if ((ntest==settingTestament || settingTestament==2) &&
                        (howHard <= settingHowHard)){
                    namIndex[i] = Integer.valueOf(arrSplit[0]);
                    testament[i] = ntest;
                    difficulty[i] = howHard;
                    nameLong[i] = arrSplit[3]; //short name
                    nam[i] = arrSplit[4]; //short name
                    i++;
                }

                tempStr = reader.readLine();
            }
            ng = i-1;
            reader.close();
        } catch (Exception e){
            String you = "are here";
        }

         TextView tv = new TextView(this);

        //tv.setTextSize();
        tv.setText(introA);
        LinearLayout child = (LinearLayout)findViewById( R.id.child);
        ScrollView  scrollView = findViewById(R.id.sv);
        scrollView.setVisibility(View.VISIBLE);
        Button button = findViewById(R.id.submit);
        button.setVisibility(View.GONE);


        try {
            child.removeAllViews();
            child.addView(tv);
        } catch (Exception ex) {
            String errmsg = ex.getMessage();
        }



        Button btn = findViewById(R.id.exit);
        btn.setVisibility(View.GONE);
        RadioGroup rg = findViewById(R.id.radioAns);
        rg.setVisibility(View.GONE);

        GridLayout yesNoStart = findViewById(R.id.yesNoStart);
        yesNoStart.setVisibility(View.VISIBLE);

    }

    public void goExit (View view){
        System.exit(0);
    }


    public void yesStart(View view){

        // Show the direction buttons
        Button north = findViewById(R.id.north);
        north.setVisibility(View.VISIBLE);
        Button west = findViewById(R.id.west);
        west.setVisibility(View.VISIBLE);
        Button east = findViewById(R.id.east);
        east.setVisibility(View.VISIBLE);
        Button south = findViewById(R.id.south);
        south.setVisibility(View.VISIBLE);
        Button exitApp = findViewById(R.id.exitApp);
        exitApp.setVisibility(View.VISIBLE);

        // Build maze array
        //  1-go to next level
        //  2-go to prev level
        //  3-answer question
        //  4-lose points

        //for debugging make each level of the maze = 1,2,3,4
        /****************************************************
        for (int i=0; i<16; i++) {

            maze[i][0] = 1;
            maze[i][1] = 3;
            maze[i][2] = 4;
            maze[i][3] = 2;

        }
         ***************************************************/
        for (int i=0; i<16; i++){
            int na = randomGenerator.nextInt(23);

            maze[i][0] = ord[4*na+1];
            maze[i][1] = ord[4*na+2];
            maze[i][2] = ord[4*na+3];
            maze[i][3] = ord[4*na+4];
            // Put the value 2 (return) in opposite dir. of 1 on prev. level
            if (i==0) {
                System.out.println("Skipping first level...");
                continue;
            }
            for (int j=0; j<16; j++){
                if(maze[i-1][j]==1){
                    forward=j;
                    break;
                }
            }
            //System.out.println(" forward="+forward);
            // Get the direction opposite to forward
            if(forward==0){
                returnInt = 3;
            }
            if(forward==3){
                returnInt = 0;
            }
            if(forward==1){
                returnInt = 2;
            }
            if(forward==2){
                returnInt = 1;
            }
            //System.out.println("returnInt ="+returnInt);
            //If return value (2) is not in returnInt element then switch
            if(maze[i][returnInt]!=2){
                //System.out.println("maze value at returnInt is not 2");
                //Find where the 2 is
                for (int j=0; j<16; j++){
                    if(maze[i][j]==2){
                        twoIndex=j;
                        break;
                    }
                }
                //System.out.println("twoIndex ="+twoIndex);
                // Make the switch
                maze[i][twoIndex] = maze[i][returnInt];
                maze[i][returnInt] = 2;
                //System.out.println("i="+i+"After switch, ");
                //System.out.println("i="+i+" " +maze[i][0]+" "+maze[i][1]+" "+maze[i][2]+" "+maze[i][3]);

            }
        }
/****************************************************/
        // Hide the Yes & No buttons
        GridLayout yesNoStart = findViewById(R.id.yesNoStart);
        yesNoStart.setVisibility(View.GONE);

        sendMessage(view);
    }

    public void decide2Ask(View view){

        if(goBack){
            askQuestion(view);
        }
        if (choice != 2 ){
            askQuestion(view);
        }
        if (choice == 2 && !inTheWoods){
            prevChoice=2;
            if(level > 0){
                score = score - 30;
                level--;
            }
            adjustScore(view);
        }
        if(score < 0){
            Toast.makeText(this, "Your score is 0 or less.  You lose.",
                    Toast.LENGTH_LONG).show();
            inTheWoods=false;
            goBack=false;
            wayBack="";
            level=0;
            score=30;
            scorePrefix= "Resetting... ";
        }
        if(level < 0) {
            level=0;
        }
        if(level ==15) {

            Toast.makeText(this, "YOU WON !!!",
                    Toast.LENGTH_LONG).show();
            inTheWoods=false;
            goBack=false;
            wayBack="";
            scorePrefix= "You won! ";
        }
        String scoreText = scorePrefix + "Level="+level+" Score="+score;
        current.setText(scoreText);
        scorePrefix="";

    }



    public void adjustScore(View view){


        if (inTheWoods){
            //if ((choice ==3 && prevChoice ==4) || (choice ==4 && prevChoice ==3)){
            if (goBack){
                if (answerRight) {
                    inTheWoods=false;
                    goBack=false;
                    wayBack="";
                    choice=1;
                } else {
                    score = score - 10;
                }

            } else {
                Toast.makeText(this, "You are going deeper into the woods.  Starting over",
                        Toast.LENGTH_LONG).show();
                inTheWoods=false;
                goBack=false;
                wayBack="";
                level=0;
                score=30;
                scorePrefix= "Resetting... ";

            }
        } else { // not in the woods
            if (choice == 1) {
                prevChoice = 1;
                if (answerRight) {
                    score = score + 30;
                    level++;
                } else {
                    score = score - 10;
                }
            }
            if (choice == 3 || choice == 4) {
                if (answerRight) {
                    prevChoice = choice;
                    inTheWoods = true;
                } else {
                    score = score - 10;
                }

            }
        }

        if(score <= 0){
            Toast.makeText(this, "Your score is 0.  You lose.",
                    Toast.LENGTH_LONG).show();
            inTheWoods=false;
            goBack=false;
            wayBack="";
            level=0;
            score=30;
            scorePrefix= "Resetting... ";
       }
        if(level < 0) {
            level=0;
        }
        if(level == 15) {
            // Make fanfare sound
            MediaPlayer mp = MediaPlayer.create(this,R.raw.fanfare);
            mp.start();
            Toast.makeText(this, "YOU WIN !!!",
                    Toast.LENGTH_LONG).show();
            inTheWoods=false;
            goBack=false;
            wayBack="";
            scorePrefix= "You win!! ";
        }

        String scoreText = scorePrefix + "Level="+level+" Score="+score;
        current.setText(scoreText);
        scorePrefix="";

        sendMessage(view);
    }

    public void goNorth(View view){
        choice = maze[level][0];
        if(inTheWoods){
           if (wayBack.equals("N")) {
               goBack=true;
           }
        }
        if((choice==3 || choice ==4) && !inTheWoods) {
            wayBack = "S";
        }
        decide2Ask(view);
    }

    public void goWest(View view){
        choice = maze[level][2];
        if(inTheWoods){
            if (wayBack.equals("W")) {
                goBack=true;
            }
        }
        if((choice==3 || choice ==4) && !inTheWoods) {
            wayBack = "E";
        }        decide2Ask(view);

    }

    public void goEast(View view){
        choice = maze[level][1];
        if(inTheWoods){
            if (wayBack.equals("E")) {
                goBack=true;
            }
        }
        if((choice==3 || choice ==4) && !inTheWoods) {
            wayBack = "W";
        }
        decide2Ask(view);
    }

    public void goSouth(View view){
        choice = maze[level][3];
        if(inTheWoods){
            if (wayBack.equals("S")) {
                goBack=true;
            }
        }
        if((choice==3 || choice ==4) && !inTheWoods) {
            wayBack = "N";
        }
        decide2Ask(view);
    }

    public void sendMessage(View view) {

        //Make sure the multiple choice question is not visible
        RadioGroup radioAns  = (RadioGroup) findViewById(R.id.radioAns);
        radioAns.setVisibility(View.GONE);
        Button submit  = (Button) findViewById(R.id.submit);
        submit.setVisibility(View.GONE);

        TextView tv1 = findViewById(R.id.textView1);
        tv1.setVisibility(View.VISIBLE);

        TextView north = findViewById(R.id.north);
        north.setVisibility(View.VISIBLE);
        TextView east = findViewById(R.id.east);
        east.setVisibility(View.VISIBLE);
        TextView west = findViewById(R.id.west);
        west.setVisibility(View.VISIBLE);
        TextView south = findViewById(R.id.south);
        south.setVisibility(View.VISIBLE);
        Button exitApp = findViewById(R.id.exitApp);
        exitApp.setVisibility(View.VISIBLE);
        //TextView current = findViewById(R.id.current);
        current.setVisibility(View.VISIBLE);


        System.out.println("Here is your maze:");
        String mazeStr = "Here is your maze: ";
        for (int i = 0; i < 16; i++) {
            System.out.println(maze[i][0] + " " + maze[i][1] + " " + maze[i][2] + " " + maze[i][3]);
            mazeStr = mazeStr + "\n" + "i=" + i + " " + maze[i][0] + " " + maze[i][1] + " " + maze[i][2] + " " + maze[i][3];
        }

        // Write level description in a scrollview

        //        tv1.setText(mazeStr);
        tv1.setVisibility(view.GONE);

        TextView tv = new TextView(this);

        if(inTheWoods){
            tv.setText(forest);
        } else {
            tv.setText(levelDescr[level]);
        }
        LinearLayout child = (LinearLayout)findViewById( R.id.child);
        ScrollView  scrollView = findViewById(R.id.sv);
        scrollView.setVisibility(View.VISIBLE);

        try {
            child.removeAllViews();
            child.addView(tv);
        } catch (Exception ex) {
            String errmsg = ex.getMessage();
        }

        return;
    }
        public void askQuestion (View view){

            TextView tv = new TextView(this);
            String verse1=null;

            TextView north = findViewById(R.id.north);
            north.setVisibility(View.GONE);
            TextView east = findViewById(R.id.east);
            east.setVisibility(View.GONE);
            TextView west = findViewById(R.id.west);
            west.setVisibility(View.GONE);
            TextView south = findViewById(R.id.south);
            south.setVisibility(View.GONE);
            Button exitApp = findViewById(R.id.exitApp);
            exitApp.setVisibility(View.GONE);
            TextView tv1 = findViewById(R.id.textView1);
            tv1.setVisibility(View.GONE);
            current.setVisibility(View.GONE);
            nc++;
            // Get random name
            nm = randomGenerator.nextInt(ng) + 1;
            // Confine questions to have answer = Eli for debugging purposes.
            //nm = 95;
            //verse1 = "Random name # "+nv+" is " + nam[nv] +"\n";

            // Get clue for the randomly chosen person

            //Choose one of the 24 possible orderings of the 4 choices
            na = randomGenerator.nextInt(23) + 1;

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("indexed.txt")));
                String tempStr = reader.readLine();
                while (tempStr != null) {
                    if (tempStr.contains(" " + Integer.toString(namIndex[nm]) + "{")) {
                        verse1 = tempStr;
                        break;
                    }
                    tempStr = reader.readLine();
                }
                if (tempStr == null) {
                    verse1 = "Could not find nm=" + nm;
                } else {
                    // Get all clues and choose one at random
                    int i = 1;
                    tempStr = reader.readLine();
                    while (!tempStr.contains("{") && tempStr != null) {

                        clue[i] = tempStr.replaceAll(nam[nm], "_____");
                        i++;
                        tempStr = reader.readLine();

                    }
                    nc = i - 1;
                    //If there is more than one clue, pick one at random
                    ic = randomGenerator.nextInt(nc) + 1;

//                verse1 = "ord id = " + na + "ord1-4=" +  ord[4*na+1] +""+ ord[4*na+2] +""+ ord[4*na+3] +""+ ord[4*na+4]+" ";
//                verse1="nm="+nm+" nam="+nam[nm];
                    verse1 = clue[ic] + "\n";
                }
                reader.close();
            } catch (Exception e) {

            }

            //Get wrong answers


            //Make an array of multiple choices with 1 = right answer, 2 = fake name
            //and 3,4 = wrong bible names

            ans[1] = nam[nm];
            ans[2] = fakeName[randomGenerator.nextInt(nf) + 1];

            //Make sure randomly chosen Bible name is not the right answer
            String tempStr = nam[randomGenerator.nextInt(ng) + 1];
            while (tempStr.equals(nam[nm])) {
                tempStr = nam[randomGenerator.nextInt(ng) + 1];
            }
            ans[3] = tempStr;

            //tempStr = allNames[randomGenerator.nextInt(nn)+1];
            tempStr = nam[randomGenerator.nextInt(ng) + 1];
            while (tempStr.equals(nam[nm]) || tempStr.equals(ans[3])) {
                //tempStr = allNames[randomGenerator.nextInt(nn)+1];
                tempStr = nam[randomGenerator.nextInt(ng) + 1];
            }
            ans[4] = tempStr;

            // Get random ordering of the answers nord = 1-23

            RadioButton b1 = findViewById(R.id.radioAns1);
            try {
                b1.setText(ans[ord[na * 4 + 1]]);
                b1.setChecked(false);
                RadioButton b2 = findViewById(R.id.radioAns2);
                b2.setText(ans[ord[na * 4 + 2]]);
                b2.setChecked(false);
                RadioButton b3 = findViewById(R.id.radioAns3);
                b3.setText(ans[ord[na * 4 + 3]]);
                b3.setChecked(false);
                RadioButton b4 = findViewById(R.id.radioAns4);
                b4.setText(ans[ord[na * 4 + 4]]);
                b4.setChecked(false);
                RadioButton b5 = findViewById(R.id.radioAns5);
                b5.setChecked(true);

            } catch (Exception e) {
                String you = "are here";
            }

            tv.setTextSize(16);
            tv.setText(verse1);
            LinearLayout child = (LinearLayout) findViewById(R.id.child);
            ScrollView scrollView = findViewById(R.id.sv);
            scrollView.setVisibility(View.VISIBLE);
            // Multiple choices
            RadioGroup rg = findViewById(R.id.radioAns);
            rg.setVisibility(View.VISIBLE);

            Button btn = findViewById(R.id.submit);
            //Submit button must be inactive until an answer is chosen.
            btn.setVisibility(View.VISIBLE);

            GridLayout yesNoStart = findViewById(R.id.yesNoStart);
            yesNoStart.setVisibility(View.GONE);

            try {
                child.removeAllViews();
                child.addView(tv);
            } catch (Exception ex) {
                String errmsg = ex.getMessage();
            }
    }

    public void checkAnswer(View view) {
        RadioGroup rg = findViewById(R.id.radioAns);
        int btnId = rg.getCheckedRadioButtonId();
        //Do nothing if no answer was picked
        if(btnId == -1){
            return;
        }
        // find the radiobutton by returned id
        RadioButton selectedButton = findViewById(btnId);
        String selectedText = (String) selectedButton.getText();

        TextView tv = findViewById(R.id.textView2);

        NumberFormat formatter = new DecimalFormat("#0.0");

        if (selectedText.equals(ans[1])) {
            // Make good sound
            MediaPlayer mp = MediaPlayer.create(this,R.raw.got_verse);
            mp.start();
            answerRight=true;

        } else {
            // Make bad sound`
            MediaPlayer mp = MediaPlayer.create(this, R.raw.zero_on_verse);
            mp.start();
            answerRight = false;
        }
        adjustScore(view);
         }

    }
