/*
    My Books - A database App for Android
    Created by: Rodrigo Escobar
    as a MIDTERM program for: Mobile Computing - CSIS-4020 course
    at Nova South Eastern University
    on 13 March 2016
*/
package com.rodrigoescobar.mybooks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Program Name : My Books
 * Created by Rodrigo Escobar on 3/6/2016 @ 07:31 pm EST.
 * Assignment # : MidTerm
 *
 * Updated on 03/28/2016 @ 09:45 pm EST.
 *
 * This class will handle the splash display time
 * it also start the splash animation to rotate the logo
 */

// Class created to handle the splash screen
public class Splash  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Set content view for the splash class

        final ImageView iv = (ImageView) findViewById(R.id.splashImageView); // Creates new image view for final animation
        final Animation an = (AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate)); // Creates new animation
        final Animation an2 = (AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out)); // Fade out animation

        /*
         * Start the animation and handles behavior on start, stop and pause using
         * the animation listener.
         */
        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(an2); // Start fading the animation
                finish(); // Finish the Splash activity
                Intent i = new Intent(getBaseContext(),Home.class); // Starts the Home (main) activity
                startActivity(i); // Start the Home activity (Main Menu)
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
