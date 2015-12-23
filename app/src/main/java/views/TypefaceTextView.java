package views;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


import com.example.spinno.taxiappdriver.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Subclass of {@link TextView} that supports the <code>customTypeface</code> attribute from XML.
 *
 * @author Ragunath Jawahar <rj@mobsandgeeks.com>
 */
public class TypefaceTextView extends TextView {

    /*
     * Caches typefaces based on their file path and name, so that they don't have to be created
     * every time when they are referenced.
     */
    private static Map<String, Typeface> mTypefaces;

    public TypefaceTextView(final Context context) {
        this(context, null);
    }

    public TypefaceTextView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypefaceTextView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        if (mTypefaces == null) {
            mTypefaces = new HashMap<String, Typeface>();
        }

        // prevent exception in Android Studio / ADT interface builder
        if (this.isInEditMode()) {
            return;
        }

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TypefaceTextView);
        if (array != null) {
            final String typefaceAssetPath = array.getString(
                    R.styleable.TypefaceTextView_customTypeface);

            if (typefaceAssetPath != null) {
                Typeface typeface = null;

                if (mTypefaces.containsKey(typefaceAssetPath)) {
                    typeface = mTypefaces.get(typefaceAssetPath);
                } else {
                    AssetManager assets = context.getAssets();
                    typeface = Typeface.createFromAsset(assets, typefaceAssetPath);
                    mTypefaces.put(typefaceAssetPath, typeface);
                }

                setTypeface(typeface);
            }
            array.recycle();
        }
    }

}
