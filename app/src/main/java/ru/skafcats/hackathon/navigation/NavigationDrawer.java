package ru.skafcats.hackathon.navigation;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import ru.skafcats.hackathon.R;

/**
 * Created by vasidmi on 28/03/2017.
 */

public class NavigationDrawer {


    PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Новости");
    SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Тест");
    AccountHeader headerResult;
    Drawer mDrawer;

    public NavigationDrawer(final Activity activity, Toolbar toolbar) {

        headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.background_drawer)
                .addProfiles(
                        new ProfileDrawerItem().withName("Никита Куликов").withEmail("test@gmail.com").withIcon(activity.getResources().getResourceName(R.drawable.huawei_logo))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        mDrawer = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(activity)
                .withToolbar(toolbar)
                .addDrawerItems(item1, new DividerDrawerItem(), item2)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        FragmentManager mFragmentManager = activity.getFragmentManager();

                        switch ((int) drawerItem.getIdentifier()) {
                            case 1:
                                Toast.makeText(activity.getApplicationContext(), "Works!", Toast.LENGTH_SHORT).show();
                                mDrawer.closeDrawer();
                                break;
                        }
                        return true;
                    }
                }).build();
    }

}
