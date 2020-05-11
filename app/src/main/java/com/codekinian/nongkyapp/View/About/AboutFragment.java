package com.codekinian.nongkyapp.View.About;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codekinian.nongkyapp.Base.MvpFragment;
import com.codekinian.nongkyapp.R;
import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;

public class AboutFragment extends MvpFragment<AboutPresenter> implements AboutView {

    @Override
    protected AboutPresenter createPresenter() {
        return new AboutPresenter(this);
    }

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        FancyAboutPage fancyAboutPage= view.findViewById(R.id.fancyaboutpage);
        //fancyAboutPage.setCoverTintColor(Color.BLUE);  //Optional
        fancyAboutPage.setCover(R.drawable.pict); //Pass your cover image
        fancyAboutPage.setName("Nongky Team");
        fancyAboutPage.setDescription("Hangout Here, There and Everywhere.");
       /* fancyAboutPage.setAppIcon(R.drawable.brand_logoo);*/ //Pass your app icon image
        fancyAboutPage.setAppName("Nongky App");
        fancyAboutPage.setVersionNameAsAppSubTitle("Alpha");
        fancyAboutPage.setAppDescription("");
        fancyAboutPage.addEmailLink("example@email.com);     //Add your email id
        fancyAboutPage.addFacebookLink("https://www.facebook.com/");  //Add your facebook address url
        fancyAboutPage.addTwitterLink("https://twitter.com/");
        fancyAboutPage.addLinkedinLink("https://www.linkedin.com/in/");
        fancyAboutPage.addGitHubLink("https://github.com/");

        return view;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
