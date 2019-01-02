package com.prototype48.michael.famous_movies.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.prototype48.michael.famous_movies.fragment.MovieDetailsMainFragment;
import com.prototype48.michael.famous_movies.fragment.MovieDetailsReviewsFragment;
import com.prototype48.michael.famous_movies.fragment.MovieDetailsTrailerFragment;
import com.prototype48.michael.famous_movies.interfaces.MovieFragmentInterface;
import com.prototype48.michael.famous_movies.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsPageAdapter extends FragmentPagerAdapter {

    private List<Fragment>    fragments = new ArrayList<>();
 //   private ArrayList<String> pageTitles = new ArrayList<>();

    public MovieDetailsPageAdapter(FragmentManager fm,Movie movie) {
        super(fm);
        loadFragments(movie);
    }

    public void loadFragments(Movie movie){
        MovieDetailsMainFragment    movieDetailsMainFragment    = new MovieDetailsMainFragment();
        MovieDetailsReviewsFragment movieDetailsReviewsFragment = new MovieDetailsReviewsFragment();
        MovieDetailsTrailerFragment movieDetailsTrailerFragment = new MovieDetailsTrailerFragment();

        // details
        fragments.add(movieDetailsMainFragment.create(movie));
        //reviews
        fragments.add(movieDetailsReviewsFragment.create(movie));
        // trailers
        fragments.add(movieDetailsTrailerFragment.create(movie));

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        Fragment selectedFragment = fragments.get(position);

        if (selectedFragment instanceof MovieFragmentInterface) {
            return ((MovieFragmentInterface) selectedFragment).getFragmentTitle();
        } else {
            return "";
        }
    }
}
