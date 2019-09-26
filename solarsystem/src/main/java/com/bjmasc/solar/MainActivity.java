package com.bjmasc.solar;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;

public class MainActivity extends AppCompatActivity {

    private CustomArFragment arFragment;

    private ModelRenderable earthModelRenderable;
    private ModelRenderable jupiterModelRenderable;
    private ModelRenderable lunaModelRenderable;
    private ModelRenderable marsModelRenderable;
    private ModelRenderable mercuryModelRenderable;
    private ModelRenderable neptuneModelRenderable;
    private ModelRenderable saturnModelRenderable;
    private ModelRenderable sunModelRenderable;
    private ModelRenderable uranusModelRenderable;
    private ModelRenderable venusModelRenderable;

    // Astronomical units to meters ratio. Used for positioning the planets of the solar system.
    private static final float AU_TO_METERS = 0.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("Earth.sfb")).build()
                .thenAccept(modelRenderable -> earthModelRenderable = modelRenderable);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("Jupiter.sfb")).build()
                .thenAccept(modelRenderable -> jupiterModelRenderable = modelRenderable);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("Luna.sfb")).build()
                .thenAccept(modelRenderable -> lunaModelRenderable = modelRenderable);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("Mars.sfb")).build()
                .thenAccept(modelRenderable -> marsModelRenderable = modelRenderable);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("Mercury.sfb")).build()
                .thenAccept(modelRenderable -> mercuryModelRenderable = modelRenderable);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("Neptune.sfb")).build()
                .thenAccept(modelRenderable -> neptuneModelRenderable = modelRenderable);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("Saturn.sfb")).build()
                .thenAccept(modelRenderable -> saturnModelRenderable = modelRenderable);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("Sol.sfb")).build()
                .thenAccept(modelRenderable -> sunModelRenderable = modelRenderable);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("Uranus.sfb")).build()
                .thenAccept(modelRenderable -> uranusModelRenderable = modelRenderable);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("Venus.sfb")).build()
                .thenAccept(modelRenderable -> venusModelRenderable = modelRenderable);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> placeSolarSystem(hitResult.createAnchor()));
    }

    private void placeSolarSystem(Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        Node solarSystem = createSolarSystem();
        anchorNode.addChild(solarSystem);

        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }

    private Node createSolarSystem() {
        Node base = new Node();

        Node sun = new Node();
        sun.setParent(base);
        sun.setLocalPosition(new Vector3(0.0f, 0.5f, 0.0f));

        Node sunVisual = new Node();
        sunVisual.setParent(sun);
        sunVisual.setRenderable(sunModelRenderable);
        sunVisual.setLocalScale(new Vector3(0.5f, 0.5f, 0.5f));

        createPlanet(sun, mercuryModelRenderable, 0.4f);

        createPlanet(sun, venusModelRenderable, 0.7f);

        Node earth = createPlanet(sun, earthModelRenderable, 1.0f);

        createPlanet(earth, lunaModelRenderable, 0.15f);

        createPlanet(sun, marsModelRenderable, 1.5f);

        createPlanet(sun, jupiterModelRenderable, 2.2f);

        createPlanet(sun, saturnModelRenderable, 3.5f);

        createPlanet(sun, uranusModelRenderable, 5.2f);

        createPlanet(sun, neptuneModelRenderable, 6.1f);

        return base;
    }

    private Node createPlanet(Node parent, ModelRenderable modelRenderable, float auFromParent) {
        Node node = new Node();
        node.setParent(parent);
        node.setRenderable(modelRenderable);
        node.setLocalPosition(new Vector3(auFromParent * AU_TO_METERS, 0.0f, 0.0f));

        return node;
    }
}
