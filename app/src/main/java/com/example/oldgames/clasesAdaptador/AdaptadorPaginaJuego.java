package com.example.oldgames.clasesAdaptador;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.oldgames.fragmentsJuego.DatosJuego;
import com.example.oldgames.fragmentsJuego.Foro;
import com.example.oldgames.fragmentsJuego.Personajes;
import com.example.oldgames.fragmentsJuego.Trucos;

public class AdaptadorPaginaJuego extends FragmentStatePagerAdapter {

    public AdaptadorPaginaJuego(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;

        switch (i){
            case 0:
                fragment= new DatosJuego();
                break;
            case 1:
                fragment=new Personajes();
                break;
            case 2:
                fragment= new Trucos();
                break;
            case 3:
                fragment= new Foro();
                break;
            default:
                fragment=null;


        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "";
            case 1:
                return "";
            case 2:
                return "";
            case 3:
                return "";
        }
        return null;
    }
}
