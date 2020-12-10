package com.drakosha.augmentedr_2;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import com.google.ar.core.Plane;


public class AR_Activity extends AppCompatActivity {


    private ArFragment fragment;
    private Button chair;
    private Button Duck;
    private Button table;
    private Button sofa;
    private Button info;

    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    public boolean Info_mode = false;

    private enum ObjectType {
        CHAIR,
        DUCK,
        TABLE,
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

        table = findViewById(R.id.table);
        table.setOnClickListener(view -> objectType = ObjectType.TABLE);

        sofa = findViewById(R.id.sofa);
        sofa.setOnClickListener(view -> objectType = ObjectType.SOFA);

        Duck = findViewById(R.id.duck);
        Duck.setOnClickListener(view -> objectType = ObjectType.DUCK);

        fragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            if (Info_mode==false){
            switch (objectType) {
                case CHAIR:
                    createChair(hitResult.createAnchor());
                    break;
                case TABLE:
                    createTable(hitResult.createAnchor());
                    break;
                case SOFA:
                    createSofa(hitResult.createAnchor());
                    break;
                case DUCK:
                    createDuck(hitResult.createAnchor());
                default:
                    break;
            }}
        });
    }

    private void createSofa(Anchor anchor) {
        ModelRenderable.builder()
                .setSource(this, Uri.parse("sofa.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable,anchor));

    }

    private void createTable(Anchor anchor) {
        ModelRenderable.builder()
                .setSource(this, Uri.parse("table.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable,anchor));

    }

    private void createChair(Anchor anchor) {
        ModelRenderable.builder()
                .setSource(this,Uri.parse("chair.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable,anchor));

    }

    private void createDuck(Anchor anchor){
        ModelRenderable.builder()
                .setSource(this,Uri.parse("Duck.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable,anchor));
    }


    private void placeModel(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode node = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(fragment.getTransformationSystem());
        transformableNode.setOnTapListener((HitTestResult hitTestResult, MotionEvent Event) ->
        {
            if(Info_mode==true){
                    layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.activity_pop_up_window_1, null);
                    popupWindow = new PopupWindow(container, 800, 800, true);
                    popupWindow.showAtLocation(this.findViewById(R.id.chair), Gravity.CENTER, 0, 0);
                    // dismiss the popup window when touched
                    container.setOnTouchListener(new View.OnTouchListener() {
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