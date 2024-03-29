package com.example.seminarhall;

import com.google.firebase.firestore.Exclude;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ReservedHall {
    private String hallId,reservationId, startTime,EndTime,userId,Purpose;
    private Date startDate, endDate;
    private String textStartDate,textEndDate;

    public String getTextStartDate() {
        return textStartDate;
    }

    public void setTextStartDate(String textStartDate) {
        this.textStartDate = textStartDate;
    }

    public String getTextEndDate() {
        return textEndDate;
    }

    public void setTextEndDate(String textEndDate) {
        this.textEndDate = textEndDate;
    }

    private Date BookingDate;
    int NoOfDays;
    List<String> days;
    double startHour,endHour;



    public double getStartHour() {
        return startHour;
    }

    public void setStartHour(double startHour) {
        this.startHour = startHour;
    }

    public double getEndHour() {
        return endHour;
    }

    public void setEndHour(double endHour) {
        this.endHour = endHour;
    }



    public int getNoOfDays() {
        return NoOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        NoOfDays = noOfDays;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    ReservedHall()
    {
        //no args constructor
    }

    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    @Exclude
    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

//    @Exclude
    public Date getBookingDate() {
        return BookingDate;
    }

//    @Exclude
    public void setBookingDate(Date bookingDate) {
        BookingDate = bookingDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    private void setHour()
    {
    }

    public ReservedHall(String hallId, List<String> Dates
            , String startTime, String endTime, String userId, String purpose) {
        this.hallId = hallId;
        this.startDate = toDate(Dates.get(0));
        this.endDate =toDate(Dates.get(Dates.size()-1));
        this.EndTime = endTime;
        this.startTime = startTime;
        this.userId = userId;
        this.Purpose = purpose;
        this.BookingDate= Calendar.getInstance().getTime();
        this.days=Dates;
        this.NoOfDays=Dates.size();
        this.textStartDate = Dates.get(0);
        this.textEndDate = Dates.get(Dates.size() - 1);
        setHour();

    }
    @Exclude
    private Date toDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        try {
            Date t=sdf.parse(date);
            return t;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Exclude
    public String getStartDateText()
    {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY).format(startDate);
    }
    @Exclude
    public String getEndDateText()
    {
        return DateFormat.getDateInstance(DateFormat.SHORT,Locale.ITALY).format(endDate);
    }


}
