package tcss450.uw.edu.team15project450.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This model class will be used to create a Tour object. Audio and image not
 * yet implemented.
 *
 * Created by Gabe on 4/28/2016.
 */
public class Tour implements Serializable {
    private String mTitle;
    private String mDescription;
    private String mAudioFilePath;
    //private Image mTourImage;
    private ArrayList<Place> mPlaceList;
    private boolean mIsPublic;
    private boolean mIsPublished;
    private String mCreatedBy;
    private String mDateCreated;
    private String mDateModified;

    public static final String TITLE = "title",
            DESC = "description", AUDIO = "audio", IS_PUBLIC = "bPublic",
            IS_PUBLISH = "bPublished", CREATED_BY = "createdBy",
            DATE_CREATED = "dateCreated", DATE_MOD = "dateModified";

    public Tour(String title, String description, String audioFilePath
            , ArrayList<Place> placeList, boolean isPublic
            , boolean isPublished, String createdBy
            , String dateCreated, String dateModified) {
        setTitle(title);
        setDescription(description);
        mAudioFilePath = audioFilePath;
        mPlaceList = placeList;
        mIsPublic = isPublic;
        mIsPublished = isPublished;
        setCreatedBy(createdBy);
        setDateCreated(dateCreated);
        setDateModified(dateModified);
    }

    public Tour(String title, String description, String audioFilePath, boolean isPublic,
            boolean isPublished, String createdBy,String dateCreated, String dateModified) {
        setTitle(title);
        setDescription(description);
        mAudioFilePath = audioFilePath;
        mIsPublic = isPublic;
        mIsPublished = isPublished;
        setCreatedBy(createdBy);
        setDateCreated(dateCreated);
        setDateModified(dateModified);
    }

    public String getTitle() { return mTitle; }

    public void setTitle(String title) {
        if (title == null)
            throw new IllegalArgumentException("Title must be supplied");
        mTitle = title;
    }

    public String getDescription() { return mDescription; }

    public void setDescription(String description) {
        if (description == null)
            throw new IllegalArgumentException("Tour description must be supplied.");
        if (description.length() < 25 )
            throw new IllegalArgumentException("Tour description must be at least 25 characters long.");
        mDescription = description;
    }

    public String getAudioFilePath() { return mAudioFilePath; }

    public void setAudioFilePath(String audioFilePath) {
        mAudioFilePath = audioFilePath;
    }

    public ArrayList<Place> getPlaceList() { return mPlaceList; }

    public void setPlaceList(ArrayList<Place> tourPlaceList) {
        mPlaceList = tourPlaceList;
    }

    public void addPlace(Place place) {
        mPlaceList.add(place);
    }

    public boolean getIsPublic() { return mIsPublic; }

    public void setIsPublic(boolean isPublic) { mIsPublic = isPublic; }

    public boolean getIsPublished() { return mIsPublished; }

    public void setIsPublished(boolean isPublished) { mIsPublished = isPublished; }

    public String getCreatedBy() { return mCreatedBy; }

    public void setCreatedBy(String createdBy) {
        if (createdBy == null)
            throw new IllegalArgumentException("Creator must be supplied");
        mCreatedBy = createdBy;
    }

    public String getDateCreated() { return mDateCreated; }

    public void setDateCreated(String dateCreated) {
        if (dateCreated == null)
            throw new IllegalArgumentException("Date created must be supplied");
        mDateCreated = dateCreated;
    }

    public String getDateModified() { return mDateModified; }

    public void setDateModified(String dateModified) {
        if (dateModified == null)
            throw new IllegalArgumentException("Date modified must be supplied");
        mDateModified = dateModified;
    }

    @Override
    public String toString() {
        String isPublic;
        String isPublished;
        if(mIsPublic == true) {
            isPublic = "Yes";
        } else {
            isPublic = "No";
        }
        if(mIsPublished == true) {
            isPublished = "Yes";
        } else {
            isPublished = "No";
        }
        return "Title: " + mTitle + " Description: " + mDescription
                + "Audio file path: " + mAudioFilePath + " Public: " + isPublic
                + " Published: " + isPublished + " Created By: " + mCreatedBy
                + " Date Created: " + mDateCreated + " Date Modified: " + mDateModified;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns tour list if success.
     * @param tourJSON
     * @param tourList
     * @return reason or null if successful.
     */
    public static String parseTourJSON(String tourJSON, List<Tour> tourList) {
        String reason = null;
        if (tourJSON != null) {
            try {
                JSONArray arr = new JSONArray(tourJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    boolean isPublic = (1 == obj.getInt(IS_PUBLIC));
                    boolean isPublish = (1 == obj.getInt(IS_PUBLISH));
                    Tour tour = new Tour(obj.getString(Tour.TITLE), obj.getString(Tour.DESC),
                            obj.getString(Tour.AUDIO), new ArrayList<Place>(), isPublic, isPublish,
                            obj.getString(Tour.CREATED_BY), obj.getString(Tour.DATE_CREATED),
                            obj.getString(Tour.DATE_MOD));
                    tourList.add(tour);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }
        }
        return reason;
    }

}