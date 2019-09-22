package com.bjmasc.arcore.runtime;

import android.os.Bundle;
import android.widget.Button;

import com.bjmasc.arcore.BaseActivity;
import com.bjmasc.arcore.R;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;

public class ShapesDuringRuntimeActivity extends BaseActivity {

    private ArFragment arFragment;

    private enum ShapeType {
        CUBE,
        SPHERE,
        CYLINDER
    }

    private ShapeType shapeType = ShapeType.CUBE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes_during_runtime);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        Button cube = findViewById(R.id.cube);
        Button sphere = findViewById(R.id.sphere);
        Button cylinder = findViewById(R.id.cylinder);

        cube.setOnClickListener(view -> shapeType = ShapeType.CUBE);
        sphere.setOnClickListener(view -> shapeType = ShapeType.SPHERE);
        cylinder.setOnClickListener(view -> shapeType = ShapeType.CYLINDER);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            if (shapeType == ShapeType.CUBE) {
                placeCube(hitResult.createAnchor());
            } else if (shapeType == ShapeType.SPHERE) {
                placeSphere(hitResult.createAnchor());
            } else if (shapeType == ShapeType.CYLINDER) {
                placeCylinder(hitResult.createAnchor());
            }
        });
    }

    private void placeCube(Anchor anchor) {
        MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.BLUE))
                .thenAccept(material -> {
                    ModelRenderable modelRenderable = ShapeFactory.makeCube(new Vector3(0.1f, 0.1f, 0.1f),
                            new Vector3(0f, 0.1f, 0f), material);

                    placeModel(anchor, modelRenderable);
                });
    }

    private void placeSphere(Anchor anchor) {
        MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.BLUE))
                .thenAccept(material -> {
                    ModelRenderable modelRenderable = ShapeFactory.makeSphere(0.1f,
                            new Vector3(0f, 0.1f, 0f), material);

                    placeModel(anchor, modelRenderable);
                });
    }

    private void placeCylinder(Anchor anchor) {
        MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.BLUE))
                .thenAccept(material -> {
                    ModelRenderable modelRenderable = ShapeFactory.makeCylinder(0.1f, 0.2f,
                            new Vector3(0f, 0.2f, 0f), material);

                    placeModel(anchor, modelRenderable);
                });
    }

    private void placeModel(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);

        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }
}
