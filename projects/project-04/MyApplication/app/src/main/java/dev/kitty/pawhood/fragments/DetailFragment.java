package dev.kitty.pawhood.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import dev.kitty.pawhood.helpers.YelpHelper;
import dev.kitty.pawhood.R;
import com.squareup.picasso.Picasso;
import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;

/**
 * Created by kitty on 8/23/16.
 */
public class DetailFragment extends Fragment implements YelpHelper.OnResponseFinished {

    private ImageView parkImage;
    private TextView parkName, address, city, zipcode;
    private RatingBar ratingBar;

    private Business currentPark;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        setViews(v);
        populateViews();
        return v;
    }

    @Override
    public void onBusinessReceived(ArrayList<Business> businesses) {

    }

    public void setCurrentPark (Business currentBusiness) {
        currentPark = currentBusiness;
    }

    public void populateViews() {

        double ratingDouble = currentPark.rating();
        float ratingFloat = (float) ratingDouble;
        Drawable progress = ratingBar.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.BLUE);
        ratingBar.setRating(ratingFloat);

        parkName.setText(currentPark.name());

        String loc = currentPark.location().address().toString();
        address.setText(loc.substring(1, loc.length() - 1));
        city.setText(currentPark.location().city().toString() + ", " + currentPark.location().stateCode().toString());
        zipcode.setText(currentPark.location().postalCode().toString());

        // uses picasso for image when there's internet
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Picasso.with(getContext())
                    .load(currentPark.imageUrl().toString())
                    .into(parkImage);
        } else {}
    }

    public void setViews(View v) {
        //finding views from xml
        parkImage = (ImageView) v.findViewById(R.id.fragment_details_image);
        parkName = (TextView) v.findViewById(R.id.fragment_details_name);
        address = (TextView) v.findViewById(R.id.fragment_details_address_tv);
        city = (TextView) v.findViewById(R.id.fragment_details_city_tv);
        zipcode = (TextView) v.findViewById(R.id.fragment_details_zipcode_tv);
        ratingBar = (RatingBar) v.findViewById(R.id.fragment_details_yelp_review_rating_bar);

    }

}