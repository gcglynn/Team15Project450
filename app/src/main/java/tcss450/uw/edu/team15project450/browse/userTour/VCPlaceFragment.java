package tcss450.uw.edu.team15project450.browse.userTour;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import tcss450.uw.edu.team15project450.R;
import tcss450.uw.edu.team15project450.browse.userTour.MyVCPlaceRecyclerViewAdapter;
import tcss450.uw.edu.team15project450.creation.AddPlaceActivity;
import tcss450.uw.edu.team15project450.model.Place;
import tcss450.uw.edu.team15project450.model.Tour;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Places of a Tour.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnPlaceListFragmentInteractionListener}
 * interface.
 */
public class VCPlaceFragment extends Fragment {

    private static final String TAG = "VCPlaceFragment";
    private int mColumnCount = 1;
    private RecyclerView mRecyclerView;
    private OnPlaceListFragmentInteractionListener mListener;

    public static String TOUR_ITEM_SELECTED = "TourItemSelected";
    ArrayList<Place> placeList = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VCPlaceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_list_vc, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }

        final Bundle bundle = getArguments();

        String mTour = getTourTitle ((Tour) bundle.getSerializable(TOUR_ITEM_SELECTED));
        String mCreatedBy = getCreatedBy ((Tour) bundle.getSerializable(TOUR_ITEM_SELECTED));
        bundle.putString("tour", mTour);
        bundle.putString("userid", mCreatedBy);

        // to hide the floating action button
        FloatingActionButton mapFloatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fabMap);
        mapFloatingActionButton.hide();

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.show();

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), AddPlaceActivity.class);
                i.putExtras(bundle);
                startActivity(i);
                getActivity().finish();
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            getPlaceList((Tour) args.getSerializable(TOUR_ITEM_SELECTED));
            mRecyclerView.setAdapter(new MyVCPlaceRecyclerViewAdapter(placeList, mListener));
        }
        return view;
    }

    public String getTourTitle(Tour tour) {
        return tour.getTitle();
    }

    public String getCreatedBy(Tour tour) {
        return tour.getCreatedBy();
    }

    public ArrayList<Place> getPlaceList(Tour tour) {
        //get place
        if (tour != null && !tour.getPlaceList().isEmpty()) {
            placeList = tour.getPlaceList();
        }
        return placeList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPlaceListFragmentInteractionListener) {
            mListener = (OnPlaceListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPlaceListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPlaceListFragmentInteractionListener {
        void onPlaceListFragmentInteraction(Place item);
    }
}
