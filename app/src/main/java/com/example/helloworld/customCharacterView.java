package com.example.helloworld;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class customCharacterView extends RelativeLayout {

    //parts of character
    private ImageView body, head, eyes, nose, mouth, hat;

    public customCharacterView(Context context) {
        super(context);
        init(context);
    }

    public customCharacterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public customCharacterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // Initialize character components
        body = new ImageView(context);
        head = new ImageView(context);
        eyes = new ImageView(context);
        nose = new ImageView(context);
        mouth = new ImageView(context);
        hat = new ImageView(context);

        // Set unique IDs for components
        int headId = View.generateViewId();
        head.setId(headId);

        int bodyId = View.generateViewId();
        body.setId(bodyId);

        int eyesId = View.generateViewId();
        eyes.setId(eyesId);

        int noseId = View.generateViewId();
        nose.setId(noseId);

        int mouthId = View.generateViewId();
        mouth.setId(mouthId);

        int hatId = View.generateViewId();
        hat.setId(hatId);

        // Define LayoutParams for head
        RelativeLayout.LayoutParams headParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        headParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        // Define LayoutParams for body
        RelativeLayout.LayoutParams bodyParams = new RelativeLayout.LayoutParams(
                200,
                200
        );
        bodyParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bodyParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        // Define LayoutParams for eyes
        RelativeLayout.LayoutParams eyesParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        eyesParams.addRule(RelativeLayout.ALIGN_TOP, headId);
        eyesParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        // Define LayoutParams for nose
        RelativeLayout.LayoutParams noseParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        noseParams.addRule(RelativeLayout.ALIGN_TOP, headId);
        noseParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        // Define LayoutParams for mouth
        RelativeLayout.LayoutParams mouthParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        mouthParams.addRule(RelativeLayout.ALIGN_TOP, headId);
        mouthParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        // Define LayoutParams for hat
        RelativeLayout.LayoutParams hatParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        hatParams.addRule(RelativeLayout.ALIGN_TOP, headId);
        hatParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        // Apply LayoutParams to ImageView components
        head.setLayoutParams(headParams);
        body.setLayoutParams(bodyParams);
        eyes.setLayoutParams(eyesParams);
        nose.setLayoutParams(noseParams);
        mouth.setLayoutParams(mouthParams);
        hat.setLayoutParams(hatParams);

        // Add character components to the big layout
        addView(body);
        addView(head);
        addView(eyes);
        addView(nose);
        addView(mouth);
        addView(hat);
    }

    //for setting their resources from other classes
    public void setBody(int resId) {
        body.setImageResource(resId);
    }

    public void setHead(int resId) {
        head.setImageResource(resId);
    }

    public void setEyes(int resId) {
        eyes.setImageResource(resId);
    }

    public void setNose(int resId) {
        nose.setImageResource(resId);
    }

    public void setMouth(int resId) {
        mouth.setImageResource(resId);
    }

    public void setHat(int resId) {
        hat.setImageResource(resId);
    }

}