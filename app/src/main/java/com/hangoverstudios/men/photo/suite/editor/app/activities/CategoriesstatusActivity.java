package com.hangoverstudios.men.photo.suite.editor.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.fragments.AccessoriesFragment;
import com.hangoverstudios.men.photo.suite.editor.app.fragments.BackgroundFragment;
import com.hangoverstudios.men.photo.suite.editor.app.fragments.BgeraserFragment;
import com.hangoverstudios.men.photo.suite.editor.app.fragments.DressesFragment;
import com.hangoverstudios.men.photo.suite.editor.app.fragments.EffectsStatusFragment;
import com.hangoverstudios.men.photo.suite.editor.app.fragments.FramesFragment;

import com.hangoverstudios.men.photo.suite.editor.app.fragments.ManStylesFragment;

import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;

import java.util.ArrayList;
import java.util.List;

public class CategoriesstatusActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener  {
    ViewPager viewPager;
    int position;

    ViewPagerAdapter adapter;
    int nextpostion;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoriesstatus);
        if (getIntent().hasExtra("position")){
            position=getIntent().getIntExtra("position",0);

        }
        Log.d("positon", String.valueOf(position));
        viewPager=findViewById(R.id.vieapagerstatus);


        addTabs(viewPager);
        viewPager.setCurrentItem(position);
       // viewPager.setOffscreenPageLimit(1);
        viewPager.setOnPageChangeListener(this);



    }
    private void addTabs(ViewPager viewPager) {
        adapter= new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ManStylesFragment());
        adapter.addFrag(new DressesFragment());
       // adapter.addFrag(new UniformFragment());
        adapter.addFrag(new EffectsStatusFragment());
        adapter.addFrag(new BackgroundFragment());
        adapter.addFrag(new AccessoriesFragment());
        adapter.addFrag(new BgeraserFragment());
      //  adapter.addFrag(new MakeoverFragment());
        adapter.addFrag(new FramesFragment());

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        nextpostion=position;

    }
    @Override
    public void onPageSelected(int position) {
        Log.d("selectedvalusfrom", String.valueOf(nextpostion));
        if (nextpostion!=-1){
            Fragment frag = adapter.getFragment(nextpostion);
            if (frag != null && frag instanceof ManStylesFragment) {



                ((ManStylesFragment) frag).restatStory();

            }
            else if (frag != null && frag instanceof DressesFragment){

                ((DressesFragment) frag).restatStory();

            }
           /* else if (frag != null && frag instanceof UniformFragment){


                ((UniformFragment) frag).restatStory();

            }*/
            else if (frag != null && frag instanceof EffectsStatusFragment){


                ((EffectsStatusFragment) frag).restatStory();

            }
            else if (frag != null && frag instanceof BackgroundFragment){
                ((BackgroundFragment) frag).restatStory();

            }
            else if (frag != null && frag instanceof AccessoriesFragment){
                ((AccessoriesFragment) frag).restatStory();

            }
            else if (frag != null && frag instanceof BgeraserFragment){
                ((BgeraserFragment) frag).restatStory();

            }

            else if (frag != null && frag instanceof FramesFragment){
                ((FramesFragment) frag).restatStory();

            }

        }
        nextpostion=position;
      //  adapter.notifyDataSetChanged();
        Fragment frag = adapter.getItem(position);


        if (frag != null && frag instanceof ManStylesFragment) {



            ((ManStylesFragment) frag).startStory();


        }
        else if (frag != null && frag instanceof DressesFragment){

            ((DressesFragment) frag).startStory();

        }

        else if (frag != null && frag instanceof EffectsStatusFragment){


            ((EffectsStatusFragment) frag).startStory();

        }
        else if (frag != null && frag instanceof BackgroundFragment){
            ((BackgroundFragment) frag).startStory();

        }
        else if (frag != null && frag instanceof AccessoriesFragment){
            ((AccessoriesFragment) frag).startStory();

        }
        else if (frag != null && frag instanceof BgeraserFragment){
            ((BgeraserFragment) frag).startStory();

        }

        else if (frag != null && frag instanceof FramesFragment){
            ((FramesFragment) frag).startStory();

        }
          /* FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.instanceFragment,new EffectsStatusFragment());
            transaction.commit();*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public void changeState() {
        Log.d("currentposition",String.valueOf(getItem(+1)));

        nextpostion=getItem(0);



        viewPager.setCurrentItem(getItem(+1), true);
       // adapter.notifyDataSetChanged();
       /* Fragment frag = adapter.getItem(getItem(-1));
        if (frag != null && frag instanceof ManStylesFragment) {


            ((ManStylesFragment) frag).onDestroy();

        }
        else if (frag != null && frag instanceof DressesFragment){

            ((DressesFragment) frag).onDestroy();

        }
        else if (frag != null && frag instanceof UniformFragment){


            ((UniformFragment) frag).onDestroy();

        }
        else if (frag != null && frag instanceof EffectsStatusFragment){


            ((EffectsStatusFragment) frag).onDestroy();

        }*/





    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);

        }
        public Fragment getFragment(int position){
            return mFragmentList.get(position);

        }
    }

}