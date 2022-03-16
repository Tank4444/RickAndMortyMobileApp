package ru.itcube.kalc.util;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

public class CharacterSizerTransformation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        return null;
    }

    @Override
    public String key() {
        return "fill()";
    }
}
