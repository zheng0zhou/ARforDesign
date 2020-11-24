package com.drakosha.augmentedr_2;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {

    private ArFragment fragment;
    private Button chair;
    private Button Duck;
    private Button table;
    private Button sofa;

    private enum ObjectType {
        CHAIR,
        DUCK,
        TABLE,
        SOFA
    }

    ObjectType objectType = ObjectType.CHAIR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        chair = findViewById(R.id.chair);
        chair.setOnClickListener(view -> objectType = ObjectType.CHAIR);

        table = findViewById(R.id.table);
        table.setOnClickListener(view -> objectType = ObjectType.TABLE);

        sofa = findViewById(R.id.sofa);
        sofa.setOnClickListener(view -> objectType = ObjectType.SOFA);

        Duck = findViewById(R.id.duck);
        Duck.setOnClickListener(view -> objectType = ObjectType.DUCK);

        fragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

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
            }
        });
    }

    private void createSofa(Anchor anchor) {
        ModelRenderable.builder()
                .setSource(this,Uri.parse("sofa.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable,anchor));

    }

    private void createTable(Anchor anchor) {
        ModelRenderable.builder()
                .setSource(this,Uri.parse("table.sfb"))
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
        transformableNode.setParent(node);
        transformableNode.setRenderable(modelRenderable);
        fragment.getArSceneView().getScene().addChild(node);
        transformableNode.select();
    }
}
