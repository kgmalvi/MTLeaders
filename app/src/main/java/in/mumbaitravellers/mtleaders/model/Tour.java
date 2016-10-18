package in.mumbaitravellers.mtleaders.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 06-10-2016.
 */
public class Tour extends RealmObject {

    @PrimaryKey
    private int id;
    private String eventName;
    private String division;
    private String divisionHead;
    private String eventStartDate;
    private String eventEndDate;
    private String leaders;
    private String cashCarried;
    private String tourCost;
    private String onTourCollection;
    private String preRegistered;
    private String onSpotRegister;
    private String cancel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDivisionHead() {
        return divisionHead;
    }

    public void setDivisionHead(String divisionHead) {
        this.divisionHead = divisionHead;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getLeaders() {
        return leaders;
    }

    public void setLeaders(String leaders) {
        this.leaders = leaders;
    }

    public String getCashCarried() {
        return cashCarried;
    }

    public void setCashCarried(String cashCarried) {
        this.cashCarried = cashCarried;
    }

    public String getTourCost() {
        return tourCost;
    }

    public void setTourCost(String tourCost) {
        this.tourCost = tourCost;
    }

    public String getOnTourCollection() {
        return onTourCollection;
    }

    public void setOnTourCollection(String onTourCollection) {
        this.onTourCollection = onTourCollection;
    }

    public String getPreRegistered() {
        return preRegistered;
    }

    public void setPreRegistered(String preRegistered) {
        this.preRegistered = preRegistered;
    }

    public String getOnSpotRegister() {
        return onSpotRegister;
    }

    public void setOnSpotRegister(String onSpotRegister) {
        this.onSpotRegister = onSpotRegister;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }
}
