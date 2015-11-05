package ch.ccapps.android.zeneggen.model;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import ch.ccapps.android.zeneggen.MainActivity;
import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.tourismus.ActivitiesActivity;
import ch.ccapps.android.zeneggen.activity.tourismus.DriveToActivity;
import ch.ccapps.android.zeneggen.activity.tourismus.EventsActivity;
import ch.ccapps.android.zeneggen.activity.tourismus.HotelRestaurantActivity;
import ch.ccapps.android.zeneggen.activity.tourismus.ViewPointActivity;

/**
 * Created by celineheldner on 04.08.15.
 */
public class Menu {
    private String title;
    private int iconResource;
    private String iconText;
    private Intent intent;
    private boolean isSubMenu = false;

    public Menu(String title, String iconTxt, Intent i, boolean isSubmenu){
        this.title = title;
        this.iconText = iconTxt;
        this.intent = i;
        this.isSubMenu = isSubmenu;
    }

    public Menu(String title, int res, Intent i, boolean isSubmenu){
        this.title = title;
        this.iconResource = res;
        this.intent = i;
        this.isSubMenu = isSubmenu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSubMenu() {
        return isSubMenu;
    }

    public void setIsSubMenu(boolean isSubMenu) {
        this.isSubMenu = isSubMenu;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getIconText() {
        return iconText;
    }

    public void setIconText(String iconText) {
        this.iconText = iconText;
    }

    public static List<Menu> tourismusMenu(Context context, boolean isSubmenu){
        List<Menu> menuItems = new ArrayList<Menu>();
        Menu m1 = new Menu("Anfahrt", "{fa-road}", new Intent(context, DriveToActivity.class),isSubmenu);
        menuItems.add(m1);
        Menu m2 = new Menu("Events", "{fa-calendar}", new Intent(context, EventsActivity.class),isSubmenu);
        menuItems.add(m2);
        Menu m3 = new Menu("Hotel & Restaurant", "{fa-bed}", new Intent(context, HotelRestaurantActivity.class),isSubmenu);
        menuItems.add(m3);
        Menu m4 = new Menu("Aktivitäten", "{fa-child}", new Intent(context, ActivitiesActivity.class),isSubmenu);
        menuItems.add(m4);
        Menu m5 = new Menu("Sehenswürdigkeiten", "{fa-university}", new Intent(context, ViewPointActivity.class),isSubmenu);
        menuItems.add(m5);
        return menuItems;
    }

    public static List<Menu> gemeindeMenu(Context context, boolean isSubmenu){
        List<Menu> menuItems = new ArrayList<Menu>();
        Menu m1 = new Menu("Lage", "{fa-map-marker}", new Intent(context, HotelRestaurantActivity.class),isSubmenu);
        menuItems.add(m1);
        Menu m2 = new Menu("Zahlen", "{fa-lightbulb-o}", new Intent(context, HotelRestaurantActivity.class),isSubmenu);
        menuItems.add(m2);
        Menu m3 = new Menu("Info", "{fa-info}", new Intent(context, HotelRestaurantActivity.class),isSubmenu);
        menuItems.add(m3);
        Menu m4 = new Menu("Geschichte", "{fa-book}", new Intent(context, HotelRestaurantActivity.class),isSubmenu);
        menuItems.add(m4);
        return menuItems;
    }

    public static List<Menu> sideMenu(Context context){
        List<Menu> menuItems = new ArrayList<Menu>();
        Menu m1 = new Menu("Tourismums", null, new Intent(context, HotelRestaurantActivity.class),false);
        menuItems.add(m1);

        menuItems.addAll(tourismusMenu(context,true));

        Menu m2 = new Menu("Gemeinde", null, new Intent(context, HotelRestaurantActivity.class),false);
        menuItems.add(m2);

        menuItems.addAll(gemeindeMenu(context,true));

        return menuItems;
    }
}
