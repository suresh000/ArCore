package com.bjmasc.arcore.hello;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.bjmasc.arcore.BaseActivity;
import com.bjmasc.arcore.R;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class HelloArActivity extends BaseActivity {

    private ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_ar);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        if (arFragment != null) {
            arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                Anchor anchor = hitResult.createAnchor(); // position

                ModelRenderable.builder()
                        .setSource(this, Uri.parse("ArcticFox_Posed.sfb"))
                        .build()
                        .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable))
                        .exceptionally(throwable -> {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage(throwable.getMessage()).show();
                            return null;
                        });
            });
        }
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);

        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);

        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }
}
