/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowldegelibrary.function;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yidingliu.dev.knowldegelibrary.R;
import com.yidingliu.dev.knowldegelibrary.b.BaseActivity;
import com.yidingliu.dev.knowldegelibrary.b.BaseParentViewHolder;
import com.yidingliu.dev.knowldegelibrary.b.application.ExitApplication;
import com.yidingliu.dev.knowldegelibrary.function.selectorimg.PhotoAlbumActivity;
import com.yidingliu.dev.knowldegelibrary.function.selectorimg.PhotoWallAdapter;
import com.yidingliu.dev.knowldegelibrary.m.SystemActionManager;
import com.yidingliu.dev.knowldegelibrary.tools.ImageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * 选择照片页面
 * Created by Chris Zou on 14-10-15.
 */
public class SelectorImgActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    public static Activity albumActivity = null;

    private ArrayList<String> imgPathList;
    private GridView mPhotoWall;
    private PhotoWallAdapter adapter;

    /**
     * 当前文件夹路径
     */
    private String currentFolder = null;
    /**
     * 当前展示的是否为最近照片
     */
    private boolean isLatest = true;
    /**
     * 第一次跳转至相册页面时，传递最新照片信息
     */
    private boolean firstIn = true;
    /**
     * 拍照保存路径
     */
    private String savePath = "";
    private ArrayList<String> alreadySelectorPaths;
    private int maxCount = 0;

    public static final String ALREADY_PATHS = "alreadyPaths";
    public static final String MAX_COUNT = "maxCount";
    public static final String TITLE = "title";
    public static final int RESULT_CODE = 0x1238;

    @Override
    public int onResultLayoutResId() {
        return R.layout.activity_photo_wall;
    }


    @Override
    public void onInit() {
        imgPathList = new ArrayList<>();
    }

    @Override public void onInitView ( BaseParentViewHolder holder ) {

    }



    @Override
    public void onInitLayout() {
        super.onInitLayout();
        setBackGroundContent ( R.color.colorWhite );
        setRightView(getString(R.string.photo_album), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goAlbum();
            }
        });
    }

    private void setComplete(int selectCount) {
        String text;
        maxCount = getIntent().getIntExtra(MAX_COUNT, 0);
        if (maxCount == 0) {
            text = getString(R.string.sure);
        } else {
            text = String.format(getString(R.string.sure) + "(%d/%d)", selectCount, maxCount);
        }
        setBackValid(text, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(alreadySelectorPaths);
            }
        });
    }

    @Override
    public void onBindData(BaseParentViewHolder viewHolder) {

        imgPathList.add(null);
        imgPathList.addAll(getLatestImagePaths(100));

        mPhotoWall = viewHolder.getView(R.id.photo_wall_grid);
        mPhotoWall.setOnItemClickListener(this);

        adapter = new PhotoWallAdapter(this, imgPathList);
        mPhotoWall.setAdapter(adapter);
        adapter.setOnCheckChangeListener(new PhotoWallAdapter.OnCheckChangeListener() {
            @Override
            public boolean onCheckChange(int position, boolean isChecked, String path) {
                boolean flag = true;
                if (isChecked) {
                    if (!alreadySelectorPaths.contains(path)) {
                        alreadySelectorPaths.add(path);
                    }
                    if (alreadySelectorPaths.size() > maxCount) {
                        showSnackbar("最多可选择" + maxCount + "张");
                        alreadySelectorPaths.remove(alreadySelectorPaths.size() - 1);
                        flag = false;
                    }
                } else {
                    alreadySelectorPaths.remove(path);
                }
                setComplete(alreadySelectorPaths.size());
                return flag;
            }
        });
        startIntent(getIntent());
    }

    /**
     * 获取指定路径下的所有图片文件。
     */
    private ArrayList<String> getAllImagePathsByFolder(String folderPath) {
        File folder = new File(folderPath);
        String[] allFileNames = folder.list();
        if (allFileNames == null || allFileNames.length == 0) {
            return null;
        }

        ArrayList<String> imageFilePaths = new ArrayList<>();
        for (int i = allFileNames.length - 1; i >= 0; i--) {
            if ( ImageUtils.isImage ( allFileNames[i] )) {
                imageFilePaths.add(folderPath + File.separator + allFileNames[i]);
            }
        }

        return imageFilePaths;
    }

    /**
     * 使用ContentProvider读取SD卡最近图片。
     */
    private ArrayList<String> getLatestImagePaths(int maxCount) {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String key_DATA = MediaStore.Images.Media.DATA;

        ContentResolver mContentResolver = getContentResolver();

        // 只查询jpg、png、gif的图片,按最新修改排序
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or "+ key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png", "image/gif"},
                MediaStore.Images.Media.DATE_MODIFIED);

        ArrayList<String> latestImagePaths = null;
        if (cursor != null) {
            //从最新的图片开始读取.
            //当cursor中没有数据时，cursor.moveToLast()将返回false
            if (cursor.moveToLast()) {
                latestImagePaths = new ArrayList<String>();

                while (true) {
                    // 获取图片的路径
                    String path = cursor.getString(0);
                    latestImagePaths.add(path);

                    if (latestImagePaths.size() >= maxCount || !cursor.moveToPrevious()) {
                        break;
                    }
                }
            }
            cursor.close();
        }

        return latestImagePaths;
    }

    //获取当前已选择的图片路径
    private ArrayList<String> getSelectImagePaths() {
        SparseBooleanArray map = adapter.getSelectionMap();
        ArrayList<String> selectedImageList = new ArrayList<>();
        if (map.size() == 0) {
            return selectedImageList;
        }
        for (int i = 0; i < imgPathList.size(); i++) {
            if (map.get(i)) {
                selectedImageList.add(imgPathList.get(i));
            }
        }
        return selectedImageList;
    }

    //从相册页面跳转至此页
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        startIntent(intent);
    }

    private void startIntent(Intent intent) {
        //动画
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        if (intent.hasExtra(ALREADY_PATHS))
            alreadySelectorPaths = intent.getStringArrayListExtra(ALREADY_PATHS);
        if(alreadySelectorPaths==null){
            alreadySelectorPaths=new ArrayList<> (  );
        }
        setComplete(alreadySelectorPaths.size());
        adapter.setAlreadyList(alreadySelectorPaths);
        int code = intent.getIntExtra("code", -1);
        if (code == 100) {
            //某个相册
            String folderPath = intent.getStringExtra("folderPath");
            if (isLatest || (folderPath != null && !folderPath.equals(currentFolder))) {
                currentFolder = folderPath;
                updateView(100, currentFolder);
                isLatest = false;
            }
        } else if (code == 200) {
            //“最近照片”
            if (!isLatest) {
                updateView(200, null);
                isLatest = true;
            }
        }
    }

    /**
     * 根据图片所属文件夹路径，刷新页面
     */
    private void updateView(int code, String folderPath) {
        imgPathList.clear();
        adapter.clearSelectionMap();

        imgPathList.add(null);
        if (code == 100) {   //某个相册
            int lastSeparator = folderPath.lastIndexOf(File.separator);
            String folderName = folderPath.substring(lastSeparator + 1);
            setTitle(folderName);
            imgPathList.addAll(getAllImagePathsByFolder(folderPath));
        } else if (code == 200) {  //最近照片
            setTitle(R.string.latest_image);
            imgPathList.addAll(getLatestImagePaths(100));
        }
        if (imgPathList.size() > 0) {
            //滚动至顶部
            mPhotoWall.smoothScrollToPosition(0);
        }
    }

    /**
     * 点击返回时，跳转至相册页面
     */
    private void goAlbum() {
        Intent intent = new Intent(this, PhotoAlbumActivity.class);
        intent.putExtra("title", "选择相册");
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        //传递“最近照片”分类信息
//        if (firstIn) {
        if (imgPathList != null && imgPathList.size() > 1) {
            intent.putExtra("latest_count", imgPathList.size());
            intent.putExtra("latest_first_img", imgPathList.get(1));
        }
//            firstIn = false;
//        }
        startActivity(intent);
        //动画
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {//第1条数据拍照
            getCameraImg();
            return;
        }
        ArrayList<String> paths= (ArrayList<String>) imgPathList.clone();
        paths.remove(0);
//        ViewImageActivity.startAction(this,currentFolder,paths,alreadySelectorPaths,i);
    }

    private void getCameraImg() {
        String fileName = Calendar.getInstance().getTimeInMillis() + ".jpg";
        savePath = Environment.getExternalStorageDirectory() + File.separator + fileName;
        SystemActionManager.startActionCamera ( this, savePath );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SystemActionManager.CAMERA_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (new File(savePath).exists()) {
                    if (alreadySelectorPaths.size() >= 6) {
                        alreadySelectorPaths.remove(alreadySelectorPaths.size() - 1);
                    }
                    alreadySelectorPaths.add(0, savePath);
                    setResult(alreadySelectorPaths);
                }
            }
        }
    }

    private void setResult(ArrayList<String> paths) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("code", paths != null ? 100 : 101);
        intent.putStringArrayListExtra(ALREADY_PATHS, paths);
        setResult(RESULT_CODE, intent);
        ExitApplication.newInstance ().removeActivity ( albumActivity );
        finish();
    }

    public static void startAction(Activity context, int requestCode, String title, ArrayList<String> alreadyList, int maxCount) {
        Intent intent = new Intent(context, SelectorImgActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(ALREADY_PATHS, alreadyList);
        intent.putExtra(MAX_COUNT, maxCount);
        context.startActivityForResult(intent, requestCode);
    }
}
