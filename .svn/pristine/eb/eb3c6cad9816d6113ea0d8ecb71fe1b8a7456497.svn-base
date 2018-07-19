package com.quayintech.tasklib.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

public class TypefaceManager {
    private static SparseArray<Typeface> sTypefaces = new SparseArray(Roboto.values().length);

    public enum Roboto {
        Bold("fonts/Roboto-Bold.ttf"),
        Thin("fonts/Roboto-Thin.ttf"),
        Light("sans-serif-light"),
        Regular("sans-serif"),
        Medium("fonts/Roboto-Medium.ttf"),
        Condensed("sans-serif-condensed");
        
        String mName;

        private Roboto(String name) {
            this.mName = name;
        }

        public String getResourceName() {
            return this.mName;
        }
    }

    public static Typeface getTypeface(Context context, Roboto name) {
        Typeface typeface = (Typeface) sTypefaces.get(name.ordinal(), null);
        if (typeface == null) {
            if (name.mName.startsWith("fonts/")) {
                typeface = Typeface.createFromAsset(context.getAssets(), name.mName);
            } else {
                typeface = Typeface.create(name.mName, Typeface.NORMAL);
            }
            if (typeface != null) {
                sTypefaces.put(name.ordinal(), typeface);
            }
        }
        return typeface;
    }

    public static Roboto getTypefaceFromFontFamily(String fontFamily) {
        for (Roboto typeface : Roboto.values()) {
            if (typeface.mName.equals(fontFamily)) {
                return typeface;
            }
        }
        return null;
    }
}
