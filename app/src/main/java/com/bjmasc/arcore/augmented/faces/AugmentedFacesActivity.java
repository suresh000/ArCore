package com.bjmasc.arcore.augmented.faces;

import android.os.Bundle;

import com.bjmasc.arcore.BaseActivity;
import com.bjmasc.arcore.R;
import com.google.ar.core.AugmentedFace;
import com.google.ar.core.Frame;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.AugmentedFaceNode;

import java.util.Collection;

public class AugmentedFacesActivity extends BaseActivity {

    private ModelRenderable modelRenderable;
    private Texture texture;
    private boolean isAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmented_faces);

        AugmentedFacesArFragment arFragment = (AugmentedFacesArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);


        ModelRenderable.builder()
                .setSource(this, R.raw.fox_face)
                .build()
                .thenAccept(renderable -> {
                    modelRenderable = renderable;
                    modelRenderable.setShadowCaster(false);
                    modelRenderable.setShadowReceiver(false);
                });

        Texture.builder()
                .setSource(this, R.drawable.fox_face_mesh_texture)
                .build()
                .thenAccept(texture -> this.texture = texture);

        if (arFragment != null) {

            arFragment.getArSceneView().setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);

            arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {

                if (modelRenderable == null || texture == null)
                    return;

                Frame frame = arFragment.getArSceneView().getArFrame();

                if (frame != null) {
                    Collection<AugmentedFace> augmentedFaces = frame.getUpdatedTrackables(AugmentedFace.class);

                    for (AugmentedFace augmentedFace : augmentedFaces) {

                        if (isAdded)
                            return;

                        AugmentedFaceNode augmentedFaceNode = new AugmentedFaceNode(augmentedFace);
                        augmentedFaceNode.setParent(arFragment.getArSceneView().getScene());
                        augmentedFaceNode.setFaceRegionsRenderable(modelRenderable);
                        augmentedFaceNode.setFaceMeshTexture(texture);

                        isAdded = true;
                    }
                }
            });
        }

    }
}
