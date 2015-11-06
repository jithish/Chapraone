package com.ceino.chaperonandroid;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by jp on 6/10/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;
    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

  /*  public void testAddNote() throws Exception {
        //Unlock the lock screen
        solo.unlockScreen();

        //Assert that NoteEditor activity is opened
        solo.assertCurrentActivity("Expected NoteEditor activity", "MainActivity");
        //In text field 0, enter Note 1
        solo.enterText(0, "Note 1");

        //In text field 0, type Note 2
        solo.typeText(0, "Note 2");
        //Go back to first activity

        //Takes a screenshot and saves it in "/sdcard/Robotium-Screenshots/".
        solo.takeScreenshot();
        boolean notesFound = solo.searchText("Note 1") && solo.searchText("Note 2");
        //Assert that Note 1 & Note 2 are found
        assertTrue("Note 1 and/or Note 2 are not found", notesFound);

    }*/
    public void test_login() {
        solo.unlockScreen();
        solo.waitForFragmentByTag("LoginFragment");
        //solo.scrollToSide(Solo.RIGHT);
      /*  solo.scrollToSide(Solo.RIGHT);
        solo.scrollToSide(Solo.RIGHT);
        solo.scrollToSide(Solo.RIGHT);
        solo.scrollToSide(Solo.RIGHT);
        solo.scrollToSide(Solo.RIGHT);
        solo.scrollToSide(Solo.RIGHT);*/

        solo.clickOnText("DONE");
        solo.assertCurrentActivity("Expected NoteEditor activity", "MainActivity");
        solo.enterText(0, "jjj");
        solo.enterText(1, "kkkkkkk");
        solo.clickOnText(getActivity().getResources().getString(R.string.sign_in));
      //  solo.clickOnButton("Login");
       // assertTrue(solo.searchText("Please wait. Logging in."));
       // solo.waitForActivity("com.pointabout.personal.MainTabActivity", 3000);
        solo.assertCurrentActivity("The activity should be MainActivity ",  MainActivity.class);
     //   solo.sendKey(Solo.MENU);
     //   solo.clickOnText("Logout");
      //  solo.waitForText("Are you sure you want to log out");
      //  solo.clickOnButton("Logout");
      //  solo.waitForText("You have been logged out of Personal.");
    }
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
