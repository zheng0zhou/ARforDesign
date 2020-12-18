package com.drakosha.augmentedr_2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;


public class AR_Activity extends AppCompatActivity {


    private ArFragment fragment;
    private Button chair;
    private Button Duck;
    private Button lamp;
    private Button sofa;
    private Button info;

    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    public boolean Info_mode = false;

    private enum ObjectType {
        CHAIR,
        DUCK,
        LAMP,
        SOFA,
    }

    ObjectType objectType = ObjectType.CHAIR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);


        fragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        Config.PlaneFindingMode HORIZONTAL_AND_VERTICAL;

        info = findViewById(R.id.info);
        info.setOnClickListener(view -> {
            Info_mode=!Info_mode;
            if (Info_mode==true){
                Toast.makeText(getApplicationContext(), "INFO_MODE : ON", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "INFO_MODE : OFF", Toast.LENGTH_SHORT).show();
            }
        });

        chair = findViewById(R.id.chair);
        chair.setOnClickListener(view -> objectType = ObjectType.CHAIR);

        lamp = findViewById(R.id.lamp);
        lamp.setOnClickListener(view -> objectType = ObjectType.LAMP);

        sofa = findViewById(R.id.sofa);
        sofa.setOnClickListener(view -> objectType = ObjectType.SOFA);

        Duck = findViewById(R.id.duck);
        Duck.setOnClickListener(view -> objectType = ObjectType.DUCK);

        fragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            if (Info_mode==false){
            switch (objectType) {
                case CHAIR:
                    createChair(hitResult.createAnchor(), objectType);
                    break;
                case LAMP:
                    createLamp(hitResult.createAnchor(), objectType);
                    break;
                case SOFA:
                    createSofa(hitResult.createAnchor(), objectType);
                    break;
                case DUCK:
                    createDuck(hitResult.createAnchor(), objectType);
                default:
                    break;
            }}
        });
    }

    private void createSofa(Anchor anchor, ObjectType objectType) {
        ModelRenderable.builder()
                .setSource(this, Uri.parse("sofa.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable,anchor,objectType));

    }

    private void createLamp(Anchor anchor,  ObjectType objectType) {
        ModelRenderable.builder()
                .setSource(this, Uri.parse("lampYZ.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable,anchor, objectType));

    }

    private void createChair(Anchor anchor,  ObjectType objectType) {
        ModelRenderable.builder()
                .setSource(this,Uri.parse("chaiseYZ.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable,anchor, objectType));

    }

    private void createDuck(Anchor anchor,  ObjectType objectType){
        ModelRenderable.builder()
                .setSource(this,Uri.parse("Duck.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable,anchor, objectType));
    }

    public void updateTextView(String toThis, int textview_id, View layout) {
        TextView textView = (TextView) layout.findViewById(textview_id);
        textView.setText(toThis);
    }


    private void placeModel(ModelRenderable modelRenderable, Anchor anchor, ObjectType objectType) {
        AnchorNode node = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(fragment.getTransformationSystem());
        transformableNode.setOnTapListener((HitTestResult hitTestResult, MotionEvent Event) ->
        {
            if(Info_mode==true){

//                    layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
//                    ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.activity_pop_up_window_1, null);
//                    View layout = layoutInflater.inflate(R.layout.activity_pop_up_window_1, container, false);
//                    popupWindow = new PopupWindow(container, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//                    popupWindow.showAtLocation(info, Gravity.CENTER, 0, 0);

                    LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View layout = inflater.inflate(R.layout.activity_pop_up_window_1, (ViewGroup) findViewById(R.id.popup_element), false);

                    PopupWindow popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    switch (objectType){
                        case CHAIR:
                            updateTextView("CHAIR", R.id.textView1,layout);
                            updateTextView("This is a chair!", R.id.textView2,layout);
                            break;
                        case LAMP:
                            updateTextView("LAMP", R.id.textView1,layout);
                            updateTextView("This is a lamp!", R.id.textView2,layout);
                            break;
                        case SOFA:
                            updateTextView("SOFA",R.id.textView1,layout);
                            updateTextView("This is a sofa!", R.id.textView2,layout);
                            break;
                        case DUCK:
                            updateTextView("DUCK",R.id.textView1,layout);
                            updateTextView("This is a duck!", R.id.textView2,layout);
                        default:
                            break;
                    }
                    popupWindow.showAtLocation(info, Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    layout.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });
            }
        });

        transformableNode.setParent(node);
        transformableNode.setRenderable(modelRenderable);
        fragment.getArSceneView().getScene().addChild(node);
        transformableNode.select();
    }


}