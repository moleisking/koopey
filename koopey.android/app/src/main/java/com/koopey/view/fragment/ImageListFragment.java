package com.koopey.view.fragment;

import android.app.Activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;

import com.koopey.R;
import com.koopey.helper.ImageHelper;
import com.koopey.helper.SerializeHelper;
import com.koopey.adapter.ImageAdapter;
import com.koopey.model.Image;
import com.koopey.model.Images;
import com.koopey.service.GalleryService;
import com.koopey.view.MainActivity;


/**
 * Created by Scott on 29/03/2017.
 */
public class ImageListFragment extends ListFragment implements  GalleryService.GalleryListener,
        PopupMenu.OnMenuItemClickListener, View.OnTouchListener {

    private static final int IMAGE_LIST_FRAGMENT = 260;
    public static final int DEFAULT_IMAGE_COUNT = 4;
    public static final int REQUEST_GALLERY_IMAGE = 197;
    public OnImageListFragmentListener delegate = null;
    private FragmentManager fragmentManager;
    private ImageAdapter imageAdapter;
    private FloatingActionButton btnCreate;
    private GalleryService galleryService;
    private Images images = new Images();
    private boolean showScrollbars = true;
    private boolean showCreateButton = true;
    private boolean showUpdateButton = true;
    private boolean showDeleteButton = true;
    private static final int DEFAULT_IMAGE_SIZE = 512;
    private PopupMenu imagePopupMenu;
    private String imageActionType = "";
    private int imageActionIndex = -1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set controls
        this.btnCreate = (FloatingActionButton) this.getActivity().findViewById(R.id.btnCreate);
        this.images = (Images) this.getActivity().getIntent().getSerializableExtra("images");
        this.showCreateButton = this.getActivity().getIntent().getBooleanExtra("showCreateButton", false);
        this.showUpdateButton = this.getActivity().getIntent().getBooleanExtra("showUpdateButton", false);
        this.showDeleteButton = this.getActivity().getIntent().getBooleanExtra("showDeleteButton", false);
        this.fragmentManager = this.getFragmentManager();

        //Set listeners
        if (this.showCreateButton) {
            this.btnCreate.setVisibility(View.VISIBLE);
            this.btnCreate.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    imageActionType = "create";
                    imageActionIndex = -1;
                    startGalleryRequest();
                }
            });
        } else {
            this.btnCreate.setVisibility(View.GONE);
        }

        //Set adapter
        this.populateImageList();
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.label_images));
        ((MainActivity) getActivity()).hideKeyboard();
        try {
            delegate = (OnImageListFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnImageListFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        galleryService = new GalleryService(requireActivity().getActivityResultRegistry(), getActivity());
        super.onCreate(savedInstanceState);
        if (getActivity().getIntent().hasExtra("images")) {
            this.images = (Images) getActivity().getIntent().getSerializableExtra("images");
        } else if (SerializeHelper.hasFile(this.getActivity(), Images.IMAGES_FILE_NAME)) {
            this.images = (Images) SerializeHelper.loadObject(this.getActivity(), Images.IMAGES_FILE_NAME);
        } else {
            this.images = new Images();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_images, container, false);
    }

    @Override
    public void onDetach() {
        delegate = null;
        super.onDetach();
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        TypedArray a = activity.obtainStyledAttributes(attrs, R.styleable.ParentChildListFragment);
        this.showScrollbars = a.getBoolean(R.styleable.ParentChildListFragment_showScrollbars, true);
        this.showCreateButton = a.getBoolean(R.styleable.ParentChildListFragment_showCreateButton, true);
        this.showUpdateButton = a.getBoolean(R.styleable.ParentChildListFragment_showUpdateButton, true);
        this.showDeleteButton = a.getBoolean(R.styleable.ParentChildListFragment_showDeleteButton, true);
        a.recycle();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (this.showUpdateButton || this.showDeleteButton) {
            try {
                Image image = this.images.get(position);
                if (showCreateButton || showUpdateButton || showDeleteButton) {
                  //  onUpdateOrDeleteImageClick(image);
                    this.imageActionIndex = position;
                    showImagePopupMenu(v);
                } else {
                    showImageReadFragment(image);
                }
            } catch (Exception ex) {
                Log.d(ImageListFragment.class.getSimpleName(), ex.getMessage());
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
          /*  case R.id.nav_image_gallery:
                this.imageActionType = "update";
                this.startGalleryRequest();
                return true;
            case R.id.nav_image_cancel:
                this.imagePopupMenu.dismiss();
                return true;*/
            default:
                return false;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Disallow the touch request for parent scroll on touch of child view
        v.getParent().requestDisallowInterceptTouchEvent(true);
        return false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void showImageReadFragment(Image image) {
      /*  this.getActivity().getIntent().putExtra("images", images);
        this.getFragmentManager().beginTransaction()
                .replace(R.id.toolbar_main_frame, new ImageReadFragment())
                .addToBackStack("fragment_image_read")
                .commit();*/
    }

    public void setVisibility(int visibility) {
        this.btnCreate.setVisibility(visibility);
        this.getListView().setVisibility(visibility);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void populateImageList() {
        if (this.images != null) {
            this.imageAdapter = new ImageAdapter(this.getActivity(), this.images);
            this.setListAdapter(imageAdapter);
            if (this.showScrollbars == false) {
                setListViewHeightBasedOnChildren(this.getListView());
            }
        } else {
            Log.d(ImageListFragment.class.getSimpleName(), "populateImageList()");
        }
    }

    public void showImagePopupMenu(View v) {
        this.imagePopupMenu = new PopupMenu(this.getActivity(), v, Gravity.BOTTOM);
        imagePopupMenu.setOnMenuItemClickListener( this);
        imagePopupMenu.inflate(R.menu.menu_image);
        imagePopupMenu.show();
    }

    public void startGalleryRequest() {
        //Note* return-data = true to return a Bitmap, false to directly save the cropped image
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", DEFAULT_IMAGE_SIZE);
        intent.putExtra("outputY", DEFAULT_IMAGE_SIZE);
        intent.putExtra("return-data", true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY_IMAGE);
    }

    public void setOnImageListFragmentListener(OnImageListFragmentListener delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onImageLoadFromGallery(Bitmap bitmap, String imageType) {
        if (this.imageActionType.equals("create") && this.imageActionIndex == -1) {

            Image tempImage = new Image();
            tempImage.uri = ImageHelper.BitmapToSmallUri(bitmap  );
            tempImage.width = bitmap.getWidth();
            tempImage.height = bitmap.getHeight();
            tempImage.setType("bmp");
            this.images.add(tempImage);
            populateImageList();
        } else     if ( this.imageActionType.equals("update") && this.imageActionIndex >= 0){

            Image tempImage = this.images.get(imageActionIndex);
            tempImage.uri = ImageHelper.BitmapToSmallUri(bitmap  );
            tempImage.width = bitmap.getWidth();
            tempImage.height = bitmap.getHeight();
            tempImage.setType( "bmp");
            this.images.set(tempImage);
            populateImageList();
        }  else  {
            Log.d(ImageListFragment.class.getSimpleName(), "onActivityResult-1");
        }
        this.imageActionType = "none";
        this.imageActionIndex = -1;
    }

    @Override
    public void onImageGalleryError(String error) {

    }

    public interface OnImageListFragmentListener {
        void createImageListFragmentEvent(Image image);

        void updateImageListFragmentEvent(Image image);

        void deleteImageListFragmentEvent(Image image);
    }
}
