package com.example.seminarhall.booking;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.seminarhall.Booking;
import com.example.seminarhall.Hall;
import com.example.seminarhall.HorizontalAdapter;
import com.example.seminarhall.R;
import com.example.seminarhall.ReservedHall;
import com.example.seminarhall.admin.Admin_Control;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentCalendar extends Fragment implements CalendarPickerView.OnDateSelectedListener,
CalendarPickerView.DateSelectableFilter{

    private static final String TAG = "FragmentCalendar";
    CalendarPickerView calendarPickerView;
    String SelectedDate;
    private OnFragmentInteractionListener mListener;
    List<Date> BookedDates;//dates that are needed to be highlighted, indicating a booked event;

    private void getBookedDates() {
        Hall hall = Reserve.getHall();
        Log.d(TAG, "getBookedDates: "+hall.getKey());
        final List<String> array = new ArrayList<>();

        CollectionReference db = FirebaseFirestore.getInstance().collection("Main/Reservation/Active");
        db.whereEqualTo("hallId", hall.getKey())
//                .whereGreaterThanOrEqualTo("noOfDays",1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    private Map<String, Object> Temp;

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "onSuccess: "+queryDocumentSnapshots.size());
                        for (QueryDocumentSnapshot x : queryDocumentSnapshots) {
                            Temp=x.getData();
                            array.addAll((List<String>) Temp.get("days"));
                        }
                        toDatesArray(array);
                    }
                });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        setViews(view);
        setUpCalendar();

        return view;
    }

    private void setViews(View view) {
        Log.d(TAG, "setViews: ");
        calendarPickerView = view.findViewById(R.id.calendar);
        calendarPickerView.setOnDateSelectedListener(this);
        BookedDates = new ArrayList<>();
        calendarPickerView.setDateSelectableFilter(this);
        getBookedDates();
    }

    private void setUpCalendar() {
        Log.d(TAG, "setUpCalendar: ");
        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.rc_medium);
        calendarPickerView.setTitleTypeface(typeface);
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 5);
        calendarPickerView.init(today, c.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE);
        typeface = ResourcesCompat.getFont(getActivity(), R.font.sf_display_medium);
        calendarPickerView.setDateTypeface(typeface);
        calendarPickerView.highlightDates(BookedDates);
        Log.d(TAG, "setUpCalendar: "+BookedDates.size());

    }

    private void getDates() {
        List<Date> dates = calendarPickerView.getSelectedDates();
        if (dates == null || dates.size() > 10) {
            Toast.makeText(getContext(), "Max Number of days: 10", Toast.LENGTH_SHORT).show();
            setUpCalendar();
            return;
        }
        List<String> selectedDates = new ArrayList<>();
        for (Date x : dates) {
            if (BookedDates.contains(x)) {
                setUpCalendar();
            }
            String temp = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(x);
            selectedDates.add(temp);
        }
        mListener.onFragmentInteraction(selectedDates);
    }


    @Override
    public void onDateSelected(Date date) {
        getDates();

    }

    private void toDatesArray(List<String> dates) {
        Log.d(TAG, "toDatesArray: ");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        for (int i = 0; i < dates.size(); i++) {
            Date t = null;
            try {
                t = sdf.parse(dates.get(i));
                BookedDates.add(t);
                Log.d(TAG, "Converted Date"+t);
            } catch (ParseException e) {
                Log.d(TAG, "Exception In Dates");
                return;
            }
        }
        Log.d(TAG, "toDatesArray: ");
        calendarPickerView.highlightDates(BookedDates);
    }


    @Override
    public void onDateUnselected(Date date) {

    }

    public void sendBack(List<String> sendBackText) {
        if (mListener != null) {
            mListener.onFragmentInteraction(sendBackText);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean isDateSelectable(Date date) {
        if (BookedDates.contains(date)) {
            return false;
        }
        return true;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(List<String> sendBackText);
    }

}
