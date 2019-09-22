package com.bjmasc.arcore.augmented;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.bjmasc.arcore.BaseActivity;
import com.bjmasc.arcore.R;
import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.util.Collection;

public class AugmentedImagesActivity extends BaseActivity implements Scene.OnUpdateListener {

    private CustomArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmented_images);

        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        if (arFragment != null) {
            arFragment.getArSceneView().getScene().addOnUpdateListener(this);
        }
    }

    public void setupDatabase(Config config, Session session) {
        Bitmap foxBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fox);
        AugmentedImageDatabase aid = new AugmentedImageDatabase(session);
        aid.addImage("fox", foxBitmap);
        config.setAugmentedImageDatabase(aid);
    }

    @Override
    public void onUpdate(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();
        if (frame != null) {
            Collection<AugmentedImage> images = frame.getUpdatedTrackables(AugmentedImage.class);

            for (AugmentedImage image : images) {
                if (image.getTrackingState() == TrackingState.TRACKING) {
                    if (image.getName().equals("fox")) {
                        Anchor anchor = image.createAnchor(image.getCenterPose());

                        createModel(anchor);
                    }
                }
            }
        }
    }

    private void createModel(Anchor anchor) {
        ModelRenderable.builder()
                .setSource(this, Uri.parse("ArcticFox_Posed.sfb"))
                .build()
                .thenAccept(modelRenderable -> addToScene(anchor, modelRenderable))
                .exceptionally(throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage()).show();
                    return null;
                });
    }

    private void addToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);

        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }
}
