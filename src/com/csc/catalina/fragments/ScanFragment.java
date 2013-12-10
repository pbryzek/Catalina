
package com.csc.catalina.fragments;

import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.csc.catalina.R;
import com.csc.catalina.utils.CameraPreview;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

public class ScanFragment extends BaseFragment {

    private static final String LOG_TAG = ScanFragment.class.getName();

    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

    TextView scanText;
    Button scanButton;

    ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    static {
        System.loadLibrary("iconv");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.scan, container, false);
        return rootView;
    }

    private final Runnable doAutoFocus = new Runnable() {
        @Override
        public void run() {
            if (previewing) {
                mCamera.autoFocus(autoFocusCB);
            }
        }
    };

    PreviewCallback previewCb = new PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {
                    scanText.setText("barcode result " + sym.getData());
                    barcodeScanned = true;
                }
            }
        }
    };

    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // askTitle = (EditText) view.findViewById(R.id.askTitle);
        super.onViewCreated(view, savedInstanceState);

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(getActivity(), mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout) view.findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanText = (TextView) view.findViewById(R.id.scanText);

        scanButton = (Button) view.findViewById(R.id.ScanButton);

        scanButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    scanText.setText("Scanning...");
                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                }
            }
        });

        // SlidingUpPanelLayout layout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
        // layout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
        // layout.setAnchorPoint(0.3f);
        // layout.setPanelSlideListener(new PanelSlideListener() {
        //
        // @Override
        // public void onPanelSlide(View panel, float slideOffset) {
        // HomeActivity activity = (HomeActivity) getActivity();
        // if (activity != null) {
        // if (slideOffset < 0.2) {
        // activity.hideActionBar();
        // } else {
        // activity.showActionBar();
        // }
        // }
        // }
        //
        // @Override
        // public void onPanelExpanded(View panel) {
        //
        // }
        //
        // @Override
        // public void onPanelCollapsed(View panel) {
        //
        // }
        //
        // @Override
        // public void onPanelAnchored(View panel) {
        //
        // }
        // });
        // TextView t = (TextView) view.findViewById(R.id.brought_by);
        // t.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

}
