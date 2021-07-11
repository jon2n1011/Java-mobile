package com.example.mp3jon.AdaptadoresTab;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorMp3Tab extends FragmentPagerAdapter {

   private List<Fragment> fragments=new ArrayList<>();
   private List<String> titulosfragmnetos=new ArrayList<>();


    public AdaptadorMp3Tab(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position){

       return titulosfragmnetos.get(position);
    }


    public void addFragment(Fragment fragment,String titulo){
       fragments.add(fragment);
       titulosfragmnetos.add(titulo);

    }
}
