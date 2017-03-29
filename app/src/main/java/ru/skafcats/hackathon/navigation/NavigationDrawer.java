package ru.skafcats.hackathon.navigation;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
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
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import ru.skafcats.hackathon.LoginActivity;
import ru.skafcats.hackathon.MailReportCheckActivity;
import ru.skafcats.hackathon.R;
import ru.skafcats.hackathon.RegistryActivity;
import ru.skafcats.hackathon.helpers.PreferenceHelper;

/**
 * Created by vasidmi on 28/03/2017.
 */

public class NavigationDrawer {


    PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Авторизация");
    PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Регистрация");
    PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(3).withName("Выход");
    PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Отчеты");
    AccountHeader headerResult;
    Drawer mDrawer;

    public NavigationDrawer(final Activity activity, Toolbar toolbar) {

        headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.background_drawer)
                .addProfiles(
                        new ProfileDrawerItem().withName(new PreferenceHelper(activity).getUser().getName() + new PreferenceHelper(activity).getUser().getSurname()).withIcon(R.drawable.default_avatar)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(activity)
                .withToolbar(toolbar)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        FragmentManager mFragmentManager = activity.getFragmentManager();
                        Intent intent;
                        switch ((int) drawerItem.getIdentifier()) {
                            case 1:
                                intent = new Intent(view.getContext(), MailReportCheckActivity.class);
                                view.getContext().startActivity(intent);
                                mDrawer.closeDrawer();
                                break;
                            case 2:
                                intent = new Intent(view.getContext(), LoginActivity.class);
                                view.getContext().startActivity(intent);
                                mDrawer.closeDrawer();
                                break;
                            case 3:
                                intent = new Intent(view.getContext(), RegistryActivity.class);
                                view.getContext().startActivity(intent);
                                mDrawer.closeDrawer();
                                break;
                            case 4:
                                new PreferenceHelper(view.getContext()).setUser(null);
                                Toast.makeText(view.getContext(), "Вы вышли!", Toast.LENGTH_LONG).show();
                                mDrawer.closeDrawer();
                                break;
                        }
                        return true;
                    }
                });
        if (!new PreferenceHelper(activity).isLogin()) {
            drawerBuilder.addDrawerItems(item2, item3);
        } else {
            drawerBuilder.addDrawerItems(item1, new DividerDrawerItem(), item4);
        }
        mDrawer = drawerBuilder.build();
    }

}
