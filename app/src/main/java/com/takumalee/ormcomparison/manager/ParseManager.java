package com.takumalee.ormcomparison.manager;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.takumalee.ormcomparison.entity.MyAccount;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by TakumaLee on 15/1/14.
 */
public class ParseManager {
    private static final String TAG = ParseManager.class.getSimpleName();
    private static String sUserId;
    private static int channelCounts = 0;

    public static void subscribeSingleChannel(String channel) {
        ParsePush.subscribeInBackground(channel);
    }

    public static List<String> fetchMyChannels() {
        return ParseInstallation.getCurrentInstallation().getList("channels");
    }
    public static void unScribeAllChannel(){
        for(int i =0;i< getChannelCounts() ;i++) {
            ParsePush.unsubscribeInBackground(fetchMyChannels().get(i));
            Log.d("debug", "unSubscribe channel :" + fetchMyChannels().get(i));
        }
    }
    public static void sendChannel(Object channel, String message, boolean isAll) {
        ParsePush push = new ParsePush();
        if (isAll)
            push.setChannels((LinkedList<String>)channel);
        else
            push.setChannel((String)channel);//
        push.setMessage(message);
        push.sendInBackground();
    }

    public static void sendAllChannels(String message) {
        LinkedList<String> channels = new LinkedList<String>();
//        for (String channel: fetchMyChannels()) {
        channels.addAll(fetchMyChannels());
//        }
        sendChannel(channels, message, true);
    }

    // Get the userId from the cached currentUser object
    public static void startWithCurrentUser(MyAccount myAccount) {
        sUserId = ParseUser.getCurrentUser().getObjectId();
        if (null != myAccount.getDeviceToken()) {
            ParseUser.getCurrentUser().put("deviceToken", myAccount.getDeviceToken());
            ParseUser.getCurrentUser().saveInBackground();
        }
    }
    public static String getsUserId(){
        return sUserId;
    }

    // Create an anonymous user using ParseAnonymousUtils and set sUserId
    public static void login(final MyAccount myAccount) {
//        ParseUser.getCurrentUser().set
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Anonymous login failed.");
                } else {
                    startWithCurrentUser(myAccount);
                }
            }
        });
    }

    public static void addSubscribedForum(final String forumId) {

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("SubscribeForum");

        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.whereEqualTo("forum", forumId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (parseObjects.size() == 0 ) {
                    ParseObject subscribeForum = new ParseObject("SubscribeForum");
                    subscribeForum.put("user", ParseUser.getCurrentUser());
                    subscribeForum.put("forum", forumId);
                    subscribeForum.saveInBackground();
                }
            }
        });

    }

    public static void subscribeForum(String forum) {
        String channel = "channel_" + forum;
        ParsePush.subscribeInBackground(channel);
    }

    public static void subscribeForum() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("SubscribeForum");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                for(ParseObject object : parseObjects) {
                    String forum = object.getString("forum");
                    subscribeForum(forum);
                }
            }
        });
    }

    private static int getChannelCounts(){
        setChannelCounts();
        return channelCounts;
    }
    private static void setChannelCounts(){
        List<String> my_channel = fetchMyChannels();
        for(int i =0;i< my_channel.size();i++) {
            channelCounts += 1;
        }
    }
}
