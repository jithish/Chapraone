package utils;

import android.content.Context;
import android.util.TypedValue;

import java.net.URI;
import java.util.Random;

public class Utilities {
	public static int generateRandomNumber()
	{
		int min = 20;
		int max = 99999;

		Random r = new Random();
		int i1 = r.nextInt(max - min + 1) + min;
		return i1 ;
	}
	
	
	public static class Constants{
		
		
		
	
		public static float ITEM_WIDTH=300;
		
		public static class LogConstants
		{
			public static final String APP_FLOW="App_Flow";
			public static final String API = "API";
			
		}
		
		
		
		public  static class PreferenceKeys
		{
			public static final String IS_ON_BOARD_DONE="isonboarddone";
			public static final String CHAPERON_ACCESS_USERID="ChaperOnAccessUserId";
			public static final String CHAPERON_ACCESS_EMAIL="ChaperOnAccessEmail";
			public static final String CHAPERON_ACCESS_IMAGE="ChaperOnAccessimage";
			public static final String CHAPERON_ACCESS_FNAME="ChaperOnAccessfname";
			public static final String CHAPERON_ACCESS_LNAME="ChaperOnAccesslname";
			public static final String CHAPERON_ACCESS_STREET="ChaperOnAccessstreet";
			public static final String CHAPERON_ACCESS_DESC="ChaperOnAccessdesc";
			public static final String CHAPERON_ACCESS_PHONE="ChaperOnAccessphone";

			public static final String DATE_PAGING_ANCHOR_2="datePagingAnchor2";
			public static final String REQUEST_ID_="REQUEST_ID_";
			public static final String INBOX_REQUEST_ID_="REQUEST_ID_"+"INBOX";
			public static final String OUTBOX_REQUEST_ID_="REQUEST_ID_"+"OUTBOX";
			public static final String EXPLORE_REQUEST_ID_="REQUEST_ID_"+"EXPLORE";
			
			public static final String PARSE_EMAIL_SET="parse_email_set";
            public static final String PARSE_FID_SET="PARSE_FID_SET";
			public static final String PARSE_EMAIL="parse_email";
            public static final String PARSE_FID="PARSE_FID";
		}
		
		public  static class FragmentArguements
		{
			public  static class ErrorFragment
			{
				
				public static final String ERROR_STRING="errorString";
				
			}
			
			
			public  static class WebViewFragment
			{
				
				public static final String URL="URL";
				
			}
			public  static class LoginFragment
			{
				
				public static final String IS_FROM_EXTERNAL="IS_FROM_EXTERNAL";
				public static final String IS_FROM_EXTERNAL_Yes="IS_FROM_EXTERNAL_Yes";
				
			}
			public  static class MainActivity
			{
				
				public static final String CURRENT_POS="CURRENT_POS";
				
			}
			public  static class InboxFragment
			{
				public static final String CATOGORY="CATOGORY";
				public static final String INBOX="INBOX";
				public static final String OUTBOX="OUTBOX";
				public static final String EXPLORE="EXPLORE";
				
			}
			
			public  static class ShareUrlFragment
			{
				
				public static final String URL_TO_SHARE="urlToShare";
				public static final String PARCELABLE_HANDPICKED_NEWS="parcelableHandpickedNews";
				public static final String MODE="MODE";
				public static final String PUBLIC="PUBLIC";
				public static final String PRIVATE="PRIVATE";
				
			}
			public  static class PrivateShareFragment
			{
				
				
				public static final String PARCELABLE_HANDPICKED_NEWS="parcelableHandpickedNews";
				
			}
			
			public  static class PickTextFragment
			{
				
				public static final String TEXT1="text1";
				public static final String TEXT2="text2";
				
			}
			
			public  static class PickTextExternalFragment
			{
				
				public static final String URL_TO_SHARE="urlToShare";
				
				
			}
			
		}
		
		
		public  static class TestFlightCheckPoints
		{
			public static final String MAIN_ACTIVITY_ONCREATE="Oncreate MainActivity";
			public static final String INBOX_POST = "INBOX_POST_EXECUTE";
			public static final String OUTBOX_POST = "OUTBOX_POST_EXECUTE";
			public static final String EXPLORE_POST = "EXPLORE_POST_EXECUTE";
			public static final String SELECTED_FROM_CLIPBOARD = "SELECTED_FROM_CLIPBOARD";
			public static final String SELECTED_FROM_TYPING = "SELECTED_FROM_TYPING";
			public static final String SELECTED_FROM_EXTERNAL = "SELECTED_FROM_EXTERNAL";
			
			public static final String PICK_ACTIVITY_ONCREATE = "PICK_ACTIVITY_ONCREATE";
			public static final String LOGIN_FIRST = "LOGIN_FIRST";
			public static final String GET_ACCESSTOKEN_AGAIN = "GET_ACCESSTOKEN_AGAIN";
			public static final String INTERNET_NOT_CONNECTED = "INTERNET_NOT_CONNECTED";
//			public static final String CAME_TO_SHARE_SCREEN = "CAME_TO_SHARE_SCREEN";
			public static final String LOADED_FRIENDS_LIST_NOT_ZERO = "LOADED_FRIENDS_LIST_NOT_ZERO";
			public static final String LOADED_FRIENDS_LIST_IS_ZERO = "LOADED_FRIENDS_LIST_IS_ZERO";
			public static final String SHARED_FROM_EXTERNAL = "SHARED_FROM_EXTERNAL";
			public static final String SHARED_SOMETHING = "SHARED_SOMETHING";
			public static final String SHARED_ERROR_ID_ = "SHARED_ERROR_ID_";
		}
		
		public  static class URLChecker
		{
			public static boolean isURI( String str ) {

			    if ( str.indexOf( ':' ) == -1 )       return false;
			    str = str.toLowerCase().trim();

			    if ( !str.startsWith( "http://" )
			      && !str.startsWith( "https://" )
			      && !str.startsWith( "file://" )
			      && !str.startsWith( "ftp://" )
			      && !str.startsWith( "mailto:" )
			      && !str.startsWith( "news:" )
			      && !str.startsWith( "urn:" ) )      return false;

			    try {

			        URI uri = new URI( str );
			        String proto = uri.getScheme();

			        if ( proto == null )              return false;

			        if ( proto.equals( "http" ) || proto.equals( "https" ) || proto.equals( "file" ) || proto.equals( "ftp" ) ) {

			            if ( uri.getHost() == null )  return false;

			            String path = uri.getPath();
			            if ( path != null ) {

			                int len = path.length();
			                for ( int i=0; i<len; i++ ) {

			                    if ( "?<>:*|\"".indexOf( path.charAt( i ) ) > -1 )
			                        return false;
			                }
			            }
			        }

			        return true;
			    } catch ( Exception ex ) {

			        return false;
			    }
			}
			
		}
	}


	public static int convertToDp(int i,Context context) {
		
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, context.getResources().getDisplayMetrics());
	}

}
