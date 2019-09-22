package com.bjmasc.arcore.cloud;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bjmasc.arcore.BaseActivity;
import com.bjmasc.arcore.R;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;

public class CloudAnchorsActivity extends BaseActivity {

    private CustomArFragment arFragment;

    private enum AppAnchorState {
        NONE,
        HOSTING,
        HOSTED
    }

    private Anchor anchor;
    private AppAnchorState appAnchorState = AppAnchorState.NONE;
    private boolean isPlaced = false;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_anchors);

        preferences = getSharedPreferences("Anchor_id", MODE_PRIVATE);
        editor = preferences.edit();

        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        if (arFragment != null) {
            arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                if (arFragment.getArSceneView().getSession() != null) {
                    if (!isPlaced) {
                        anchor = arFragment.getArSceneView().getSession().hostCloudAnchor(hitResult.createAnchor());
                        appAnchorState = AppAnchorState.HOSTING;
                        showToast("Hosting...");

                        createModel(anchor);

                        isPlaced = true;
                    }
                }
            });

            arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
                if (appAnchorState != AppAnchorState.HOSTING)
                    return;

                Anchor.CloudAnchorState cloudAnchorState = anchor.getCloudAnchorState();

                if (cloudAnchorState.isError()) {
                    showToast(cloudAnchorState.toString());
                } else if (cloudAnchorState == Anchor.CloudAnchorState.SUCCESS) {
                    appAnchorState = AppAnchorState.HOSTED;

                    String anchorId = anchor.getCloudAnchorId();
                    editor.putString("anchorId", anchorId);
                    editor.apply();

                    showToast("Anchor Hosted successfully. Anchor ID:" + anchorId);
                }
            });
        }

        Button resolve = findViewById(R.id.resolve);
        resolve.setOnClickListener(view -> {
            String anchorId = preferences.getString("anchorId", "null");
            if (anchorId.equals("null")) {
                showToast("No anchorId found");
                return;
            }

            Anchor resolveAnchor = arFragment.getArSceneView().getSession().resolveCloudAnchor(anchorId);
            createModel(resolveAnchor);
        });
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
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
