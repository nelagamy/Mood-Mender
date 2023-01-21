package edu.aucegypt.project_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class Emotional_Support_Activity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotional_support);

        tabLayout = findViewById(R.id.tabLayout2);
        viewPager = findViewById(R.id.viewPager2);


        tabLayout.addTab(tabLayout.newTab().setText("Experience"));
        tabLayout.addTab(tabLayout.newTab().setText("Talking Buddy"));


        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position)
                {
                    case 0:
                        Experience_Fragment experience = new Experience_Fragment();
                        return experience;
                    case 1:
                        Talking_Buddy_Fragment buddy = new Talking_Buddy_Fragment();
                        return buddy;
                    default:
                        return null;
                }
            }

            @Override
            public int getCount()
            {
                return tabLayout.getTabCount();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {

                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}