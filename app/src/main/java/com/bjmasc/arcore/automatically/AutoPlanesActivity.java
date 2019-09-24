package com.bjmasc.arcore.automatically;

import android.os.Bundle;

import com.bjmasc.arcore.BaseActivity;
import com.bjmasc.arcore.R;
import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.Plane;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.Collection;

public class AutoPlanesActivity extends BaseActivity implements Scene.OnUpdateListener {

    private ArFragment arFragment;
    private boolean isModelPlaced = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_planes);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        if (arFragment != null) {
            arFragment.getArSceneView().getScene().addOnUpdateListener(this);
        }
    }

    @Override
    public void onUpdate(FrameTime frameTime) {

        if (isModelPlaced)
            return;

        Frame frame = arFragment.getArSceneView().getArFrame();

        if (frame != null) {
            Collection<Plane> planes = frame.getUpdatedTrackables(Plane.class);
            for (Plane plane : planes) {
                if (plane.getTrackingState() == TrackingState.TRACKING) {
                    Anchor anchor = plane.createAnchor(plane.getCenterPose());

                    placeCube(anchor);

                    break;
                }
            }
        }
    }

    private void placeCube(Anchor anchor) {
        MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.BLUE))
                .thenAccept(material -> {
                    ModelRenderable modelRenderable = ShapeFactory.makeCube(new Vector3(0.3f, 0.3f, 0.3f),
                            new Vector3(0f, 0.1f, 0f), material);

                    placeModel(anchor, modelRenderable);
                });
    }

    private void placeModel(Anchor anchor, ModelRenderable modelRenderable) {

        isModelPlaced = true;

        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);

        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }
}
