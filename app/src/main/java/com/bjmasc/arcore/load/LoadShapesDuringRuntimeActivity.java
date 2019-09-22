package com.bjmasc.arcore.load;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.bjmasc.arcore.BaseActivity;
import com.bjmasc.arcore.R;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class LoadShapesDuringRuntimeActivity extends BaseActivity {

    private ArFragment arFragment;
    private String ASSETS_3D_URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_shapes_during_runtime);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        if (arFragment != null) {
            arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                placeModel(hitResult.createAnchor());
            });
        }
    }

    private void placeModel(Anchor anchor) {
        ModelRenderable.builder()
                .setSource(this, RenderableSource.builder()
                        .setSource(this, Uri.parse(ASSETS_3D_URL), RenderableSource.SourceType.GLTF2)
                        .setScale(0.75f)
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build())
                .setRegistryId(ASSETS_3D_URL)
                .build()
                .thenAccept(modelRenderable -> addNodeToScene(anchor, modelRenderable))
                .exceptionally(throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage()).show();
                    return null;
                });
    }

    private void addNodeToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);

        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }
}
