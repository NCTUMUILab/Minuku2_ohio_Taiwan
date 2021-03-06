package edu.ohio.minuku_2.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.ohio.minuku.Utilities.ScheduleAndSampleManager;
import edu.ohio.minuku.config.Constants;
import edu.ohio.minuku.manager.SessionManager;
import edu.ohio.minuku.model.Annotation;
import edu.ohio.minuku.model.Session;
import edu.ohio.minuku_2.R;

/**
 * Created by Lawrence on 2017/8/29.
 */

public class OhioListAdapter extends ArrayAdapter<Session> {

    private static final String TAG = "OhioListAdapter";
    private Context mContext;
    private ArrayList<Session> data;

    public static ArrayList<Integer> dataPos;

    private long startTime = -9999;
    private long endTime = -9999;

    private SharedPreferences sharedPrefs;

    public OhioListAdapter(Context context, int resource, ArrayList<Session> sessions) {

        super(context, resource, sessions);
        this.mContext = context;
        this.data = sessions;
        dataPos = new ArrayList<Integer>();
    }

    @Override
    public View getView(int sessionPos, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recordinglistview_ohio, parent, false);

        TextView textView = (TextView) view.findViewById(R.id.ListText);

        Session session = getItem(sessionPos);

        /**
         * setting the style of the trip title
         */
        //if there's not annotation, put the text in red
        if (session!=null){

            String sessionTitle = null;
            String timeLabel=null;
            String labelStr = SessionManager.SESSION_DISPLAY_NO_ANNOTATION;
            String noteText = "No Content Yet";

            //show the text of the session with annotation

            startTime = session.getStartTime();
            endTime = session.getEndTime();

            boolean isSessionOngoing = SessionManager.isSessionOngoing(session.getId());
            //Log.d(TAG, "[test show trip] session ongoing flag:  " + isSessionOngoing );

            if (isSessionOngoing)
                labelStr = SessionManager.SESSION_DISPLAY_ONGOING;

            //format the time string
            SimpleDateFormat sdf_minute = new SimpleDateFormat(Constants.DATE_FORMAT_HOUR_MIN_AMPM);
            timeLabel = ScheduleAndSampleManager.getTimeString(startTime,sdf_minute);

            //try to the find the transportation mode provided by the user. It is in the annotaiton with ESM tag
            ArrayList<Annotation> annotations = session.getAnnotationsSet().getAnnotationByTag("ESM");

            //{"Entire_session":true,"Tag":["ESM"],"Content":"{\"ans1\":\"Walking outdoors.\",\"ans2\":\"Food\",\"ans3\":\"No\",\"ans4\":\"Right before\"}"}
            if (annotations.size()>0){
                try {

                    String content = annotations.get(0).getContent();
                    JSONObject contentJSON = new JSONObject(content);
                    //Log.d(TAG, "[test show trip] the contentofJSON ESMJSONObject  is " + contentJSON );
                    labelStr = contentJSON.getString("ans1");

                    labelStr = getLabelFromAns1(labelStr);

                } catch (JSONException e) {
                    //e.printStackTrace();
                }
            }

            //checking the date of the startTime is Today or yesterday.
            SimpleDateFormat sdf_date = new SimpleDateFormat(Constants.DATE_FORMAT_NOW_DAY);
            String StartTimeDate = ScheduleAndSampleManager.getTimeString(startTime,sdf_date);
            String CurrentTimeDate = ScheduleAndSampleManager.getTimeString(ScheduleAndSampleManager.getCurrentTimeInMillis(), sdf_date);

            String today_yesterday;
            if(StartTimeDate.equals(CurrentTimeDate)){
                today_yesterday = "今天";
            }else{
                today_yesterday = "昨天";
            }

            sessionTitle = today_yesterday + " " + timeLabel + "    " + labelStr;


            //if there's annotation in the session and the session is not ongoing
            if (!labelStr.equals(SessionManager.SESSION_DISPLAY_ONGOING) && !labelStr.equals(SessionManager.SESSION_DISPLAY_NO_ANNOTATION)) {
                //if they've edited, put the text in green
                textView.setTextColor(mContext.getResources().getColor(R.color.text_default));

                if(labelStr.equals("Combine")||labelStr.equals("Delete")){

                    textView.setVisibility(View.GONE);
                }
            }
            //if there's no annotation or the session is ongoing
            else {
                textView.setTextColor(mContext.getResources().getColor(R.color.listed_trip_not_ongoing_text_color));

                if (labelStr.equals(SessionManager.SESSION_DISPLAY_ONGOING)) {

                    textView.setTextColor(mContext.getResources().getColor(R.color.listed_trip_ongoing_text_color));
                    sessionTitle = labelStr;
                }
            }

            //set the title of the view
            textView.setText(sessionTitle);

            //Log.d(TAG, "[test show trip] the session id is " + session.getId() + " start Time is " + session.getStartTime() + " session title " + textView.getText().toString() );
            // My layout has only one TextView


        }

        return view;
    }

    private String getLabelFromAns1(String labelStr){

        switch (labelStr){
            case "Walking outdoors": //ans1
                return "走路";
            case "Walking indoors": //ans1
                return "走路";
            case "Riding a bicycle":
                return "自行車";
            case "Riding a scooter":
                return "摩托車";
            case "Driving (I'm the driver)":
                return "汽車";
            case "Driving (I'm the passenger)":
                return "汽車";
            case "Trip is part of previous trip (COMBINE)":
                return "合併";
            case "Taking a bus":
                return "巴士";
            case "Trip is incorrect (DELETE)":
                return "刪除";
            case "Other transportation":
                return "其他";
            default:
                return "";
        }
    }

}
