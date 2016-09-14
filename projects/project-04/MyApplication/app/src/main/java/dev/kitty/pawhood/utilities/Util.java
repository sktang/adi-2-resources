package dev.kitty.pawhood.utilities;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import dev.kitty.pawhood.R;

/**
 * Created by kitty on 8/26/16.
 */
public class Util {

    private Util(){
        // private constructor so no one creates object of this class. All methods are static.
    }

    /**
     * Creates notification using the title, content and icon.
     *
     * Needs context and notification id.
     *
     * @param title
     * @param content
     * @param icon
     * @param notifId
     * @param context
     */
    public static void builNotification(String title, String content, int icon, int notifId, Context context){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notifId, builder.build());
    }

}
