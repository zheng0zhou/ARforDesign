package com.drakosha.augmentedr_2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class AR_Activity<line> extends AppCompatActivity {


    private ArFragment fragment;
    private Button chair;
    private Button table;
    private Button lamp;
    private Button sofa;
    private Button info;

    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    public boolean Info_mode = false;

    public AR_Activity() throws FileNotFoundException {
    }

    private enum ObjectType {
        CHAIR,
        TABLE,
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

        table = findViewById(R.id.table);
        table.setOnClickListener(view -> objectType = ObjectType.TABLE);

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
                case TABLE:
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
                .setSource(this,Uri.parse("table.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable,anchor, objectType));
    }

    public void updateTextView(String toThis, int textview_id, View layout) {
        TextView textView = (TextView) layout.findViewById(textview_id);
        textView.setText(toThis);
    }
    public void updateImageView(int Imageview_id, View layout,int srcImage) {
        ImageView myImgView = (ImageView) layout.findViewById(R.id.imageView);
        //myImgView.setImageResource(R.drawable.chair);
        myImgView.setImageResource(srcImage);
    }
    public String readTxt(ObjectType  objectType){
        String txt = "";
        InputStream input=getResources().openRawResource(R.raw.init);
        switch (objectType){
            case CHAIR:
                input=getResources().openRawResource(R.raw.chair);
                break;
            case LAMP:
                input=getResources().openRawResource(R.raw.lamp);
                break;
            case SOFA:
                input=getResources().openRawResource(R.raw.sofa);
                break;
            case TABLE:
                input=getResources().openRawResource(R.raw.table);
            default:
                break;
        }
        Reader reader=new InputStreamReader(input);
        StringBuffer stringBuffer=new StringBuffer();
        char b[]=new char[1024];
        int len=-1;
        try {
            while ((len = reader.read(b))!= -1){
                stringBuffer.append(b);
            }
        }catch (IOException e){
            Log.e("ReadingFile","IOException");
        }
        String string=stringBuffer.toString();

        return string;
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
                    String txt="1111";
                    switch (objectType){
                        case CHAIR:
                            updateTextView("CHAIR", R.id.textView1,layout);
                            updateTextView(readTxt(objectType), R.id.textView2,layout);
                            updateImageView(R.id.imageView,layout,R.drawable.chair);
                            break;
                        case LAMP:
                            updateTextView("LAMP", R.id.textView1,layout);
                            updateTextView(readTxt(objectType), R.id.textView2,layout);
                            updateImageView(R.id.imageView,layout,R.drawable.lamp);
                            break;
                        case SOFA:
                            updateTextView("SOFA",R.id.textView1,layout);
                            updateTextView(readTxt(objectType), R.id.textView2,layout);
                            updateImageView(R.id.imageView,layout,R.drawable.sofa);
                            break;
                        case TABLE:
                            updateTextView("Table",R.id.textView1,layout);
                            updateTextView(readTxt(objectType), R.id.textView2,layout);
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