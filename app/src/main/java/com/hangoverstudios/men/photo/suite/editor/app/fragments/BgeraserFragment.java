package com.hangoverstudios.men.photo.suite.editor.app.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.activities.CategoriesstatusActivity;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class BgeraserFragment extends Fragment implements StoriesProgressView.StoriesListener {

    private final int[] resources = new int[]{
            R.drawable.sbgremove_1,
            R.drawable.sbgremove_2,
            R.drawable.sbgremove_3,

    };
    long pressTime = 0L;
    long limit = 500L;

    // on below line we are creating variables for
    // our progress bar view and image view .
    private StoriesProgressView storiesProgressView;
    private ImageView image;

    // on below line we are creating a counter
    // for keeping count of our stories.
    private int counter = 0;
    boolean isVisible=false;
    public BgeraserFragment() {
        // Required empty public constructor
    }
    LinearLayout Trynow;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bgeraser, container, false);
        Trynow =view.findViewById(R.id.trynowbutton);
        Trynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
                if (CommonMethods.getInstance().getCallimageSelection()!=null){
                    CommonMethods.getInstance().getCallimageSelection().callSelectphoto();
                    CommonMethods.getInstance().setFromDemo("fromEraser");
                }
            }
        });


        // Inflate the layout for this fragment
        storiesProgressView = (StoriesProgressView) view.findViewById(R.id.stories);

        // on below line we are setting the total count for our stories.
        storiesProgressView.setStoriesCount(resources.length);

        // on below line we are setting story duration for each story.
        storiesProgressView.setStoryDuration(3000L);

        // on below line we are calling a method for set
        // on story listener and passing context to it.
        storiesProgressView.setStoriesListener(this);

        // below line is use to start stories progress bar.
        if (isVisible){
            storiesProgressView.startStories(counter);
        }

        // initializing our image view.
        image = (ImageView) view.findViewById(R.id.image);

        // on below line we are setting image to our image view.
        image.setImageResource(resources[counter]);

        // below is the view for going to the previous story.
        // initializing our previous view.
        View reverse = view.findViewById(R.id.reverse);

        // adding on click listener for our reverse view.
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inside on click we are
                // reversing our progress view.
                storiesProgressView.reverse();
            }
        });

        // on below line we are calling a set on touch
        // listener method to move towards previous image.
        reverse.setOnTouchListener(onTouchListener);

        // on below line we are initializing
        // view to skip a specific story.
        View skip = view.findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inside on click we are
                // skipping the story progress view.
                storiesProgressView.skip();
            }
        });
        // on below line we are calling a set on touch
        // listener method to move to next story.
        skip.setOnTouchListener(onTouchListener);
        return view;
    }

    @Override
    public void onNext() {
        int count= ++counter;
        if (count<resources.length){
            image.setImageResource(resources[count]);
        }
        else {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onPrev() {

        if ((counter - 1) < 0) return;

        // on below line we are setting image to image view
        image.setImageResource(resources[--counter]);

    }

    @Override
    public void onComplete() {


        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ((CategoriesstatusActivity)getActivity()).changeState();
            }
        }, 100);
    }
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // inside on touch method we are
            // getting action on below line.
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    // on action down when we press our screen
                    // the story will pause for specific time.
                    pressTime = System.currentTimeMillis();

                    // on below line we are pausing our indicator.
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:

                    // in action up case when user do not touches
                    // screen this method will skip to next image.
                    long now = System.currentTimeMillis();

                    // on below line we are resuming our progress bar for status.
                    storiesProgressView.resume();

                    // on below line we are returning if the limit < now - presstime
                    return limit < now - pressTime;
            }
            return false;
        }
    };
    @Override
    public void onDestroy() {
        // in on destroy method we are destroying
        // our stories progress view.
        storiesProgressView.destroy();
        super.onDestroy();
    }
    public void startStory(){
        counter = 0;
        // storiesProgressView.destroy();

        storiesProgressView.setStoriesCount(resources.length);
        storiesProgressView.setStoryDuration(3000L);
        storiesProgressView.startStories(counter);
        image.setImageResource(resources[counter]);

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            this.isVisible=isVisibleToUser;

        }
    }
    public void restatStory(){



        // storiesProgressView.reverse();
        // storiesProgressView.pause();
        if (storiesProgressView!=null){
         //   storiesProgressView.pause();
         //   storiesProgressView.reverse();
            storiesProgressView.destroy();
        }

    }
}
