/*
 * Copyright © 2013 – 2016 Ricki Hirner (bitfire web engineering).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 */

package com.etesync.syncadapter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.etesync.syncadapter.Constants;
import com.etesync.syncadapter.R;
import com.etesync.syncadapter.ui.setup.LoginActivity;

import static com.etesync.syncadapter.BuildConfig.DEBUG;
import static com.etesync.syncadapter.Constants.serviceUrl;

public class AccountsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountsActivity.this, LoginActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        if (savedInstanceState == null && !getPackageName().equals(getCallingPackage())) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            for (StartupDialogFragment fragment : StartupDialogFragment.getStartupDialogs(this))
                ft.add(fragment, null);
            ft.commit();

            if (DEBUG) {
                Toast.makeText(this, "Server: " + serviceUrl.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        PermissionsActivity.requestAllPermissions(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.nav_app_settings:
                startActivity(new Intent(this, AppSettingsActivity.class));
                break;
            case R.id.nav_website:
                startActivity(new Intent(Intent.ACTION_VIEW, Constants.webUri));
                break;
            case R.id.nav_guide:
                startActivity(new Intent(Intent.ACTION_VIEW, Constants.helpUri));
                break;
            case R.id.nav_faq:
                startActivity(new Intent(Intent.ACTION_VIEW, Constants.faqUri));
                break;
            case R.id.nav_report_issue:
                startActivity(new Intent(Intent.ACTION_VIEW, Constants.reportIssueUri));
                break;
            case R.id.nav_contact:
                startActivity(new Intent(Intent.ACTION_VIEW, Constants.contactUri));
                break;
        }

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
