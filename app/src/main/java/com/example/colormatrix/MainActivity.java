package com.example.colormatrix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.colormatrix.DBHelpers.CellsDBHelper;
import com.example.colormatrix.DBHelpers.ColorPaletteDBHelper;
import com.example.colormatrix.Fragments.AvailableDevicesFragment;
import com.example.colormatrix.Fragments.DrawingGridFragment;
import com.example.colormatrix.Fragments.YourWorksFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    DrawingGridFragment drawingGridFragment = new DrawingGridFragment();
    YourWorksFragment yourWorksFragment = new YourWorksFragment();
    AvailableDevicesFragment availableDevicesFragment = new AvailableDevicesFragment();

    @NonNull
    @Override
    public FragmentManager getSupportFragmentManager() {
        return super.getSupportFragmentManager();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, drawingGridFragment).commit();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();}
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.drawingGridMenuItem:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, drawingGridFragment).commit();
                        return true;
                    case R.id.availableDevicesMenuItem:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,availableDevicesFragment).commit();
                        return true;
                    case R.id.yourWorksMenuFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, yourWorksFragment).commit();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}