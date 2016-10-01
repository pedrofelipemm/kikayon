package pmoreira.kikayon.utils;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

public class DrawableUtils {

    public static Drawable getDrawable(final Context context, final int id) {
        Drawable drawable;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getResources().getDrawable(id, context.getTheme());
        } else {
            drawable = context.getResources().getDrawable(id);
        }

        return drawable;
    }

}
