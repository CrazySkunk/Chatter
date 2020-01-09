package ke.ac.akademiks;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

class ConstraintLayout extends View {
    public ConstraintLayout(Context context) {
        this(context, null);
    }

    public ConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
