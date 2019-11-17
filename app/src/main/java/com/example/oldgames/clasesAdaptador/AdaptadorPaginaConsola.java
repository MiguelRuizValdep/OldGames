package com.example.oldgames.clasesAdaptador;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.oldgames.fragmentsConsola.DatosConsola;
import com.example.oldgames.fragmentsConsola.ListaJuegos;
import com.example.oldgames.fragmentsConsola.ListaTiendas;

public class AdaptadorPaginaConsola extends FragmentStatePagerAdapter {

    public AdaptadorPaginaConsola(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;

        switch (i){
            case 0:
                fragment= new DatosConsola();
                break;

            case 1:
                fragment=new ListaJuegos();
                break;

            case 2:
                fragment= new ListaTiendas();
                break;

            default:
                fragment=null;


        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
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
        }
        return null;
    }
}
