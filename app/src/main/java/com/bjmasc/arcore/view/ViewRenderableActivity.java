package com.bjmasc.arcore.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bjmasc.arcore.BaseActivity;
import com.bjmasc.arcore.R;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewRenderableActivity extends BaseActivity {

    private ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_renderable);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        if (arFragment != null) {
            arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                createViewRenderable(hitResult.createAnchor());
            });
        }
    }

    private void createViewRenderable(Anchor anchor) {
        ViewRenderable.builder()
                .setView(this, R.layout.view_renderable_item)
                .build()
                .thenAccept(viewRenderable -> {
                    addToScene(viewRenderable, anchor);
                });
    }

    private void addToScene(ViewRenderable viewRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(viewRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);

        View view = viewRenderable.getView();

        ViewPager viewPager = view.findViewById(R.id.viewPager);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.image_one);
        images.add(R.drawable.image_two);
        images.add(R.drawable.image_three);
        images.add(R.drawable.image_four);
        images.add(R.drawable.image_five);
        images.add(R.drawable.image_six);

        ViewPagerAdapter adapter = new ViewPagerAdapter(images);

        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends PagerAdapter {

        private List<Integer> images;

        ViewPagerAdapter(List<Integer> images) {
            this.images = images;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.view_pager_item, container, false);

            ImageView imageView = view.findViewById(R.id.imageView);
            imageView.setImageResource(images.get(position));

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
