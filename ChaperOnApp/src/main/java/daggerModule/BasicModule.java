package daggerModule;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

import com.ceino.chaperonandroid.MainActivity;
import com.ceino.chaperonandroid.activities.ForgotpwdActivity;
import com.ceino.chaperonandroid.activities.LicenseActivity;
import com.ceino.chaperonandroid.activities.PhoneActivity;
import com.ceino.chaperonandroid.activities.RegisterActivity;
import com.ceino.chaperonandroid.activities.SettingsActivity;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import adapters.GridBaseAdapter;
import application.ChaperOnApplication;
import connection.ChaperOnConnection;
import dagger.Module;
import dagger.Provides;
import fragments.AddChildFragment;
import fragments.ChangePwdFragment;
import fragments.CreditCardFragment;
import fragments.EditProfileFragment;
import fragments.HomeGridFrament;
import fragments.InitialAddChildFragment;
import fragments.LoginFragment;
import fragments.MainFragment;
import fragments.OnBoardFragment;
import fragments.OthersProfileFragment;
import fragments.ProfileFragment;
import fragments.SettingsFragment;


@Module(injects = { ChaperOnApplication.class, ChaperOnConnection.class, MainActivity.class, LoginFragment.class, MainFragment.class,
		ForgotpwdActivity.class, RegisterActivity.class, PhoneActivity.class, OnBoardFragment.class, GridBaseAdapter.class,
		SettingsActivity.class, HomeGridFrament.class, SettingsFragment.class, ChangePwdFragment.class,
		CreditCardFragment.class, ProfileFragment.class, AddChildFragment.class, LicenseActivity.class, InitialAddChildFragment.class, EditProfileFragment.class,
		OthersProfileFragment.class}, library = true)
public class BasicModule {

	private final ChaperOnApplication app;

	public BasicModule(ChaperOnApplication app) {
		this.app = app;
	}

	@Provides
	@Singleton
	ChaperOnApplication provideApplication() {
		return app;
	}
	


	@Provides
	@Singleton
	Bus provideBus() {
		return new Bus();
	}

	@Provides
	@Singleton
	SharedPreferences provideSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(app
				.getApplicationContext());

	}

	@Provides
	@Singleton
	ConnectivityManager provideConnectivityManager() {
		return (ConnectivityManager) app
				.getSystemService(Context.CONNECTIVITY_SERVICE);

	}

	@Provides
	@Singleton
	Editor provideEditor() {
		return PreferenceManager.getDefaultSharedPreferences(
				app.getApplicationContext()).edit();

	}
	@Provides
	boolean providesIsTesting()
	{
		return false;
	}

}
