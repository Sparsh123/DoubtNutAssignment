package com.example.newsapplication.ui.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;



public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity
        implements BaseFragment.Callback {

    private final String TAG = BaseActivity.class.getName();

    public LatLng currentLatLng;
    @Inject
    public AnalyticsHelper analyticsHelper;
    public GeofenceUtil geofenceUtil;
    protected WebSocketManager webSocketManager = null;
    @Inject
    PreferencesHelper sharedPreferences;
    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    private ProgressDialog mProgressDialog;
    private T mViewDataBinding;
    private V mViewModel;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private BroadcastReceiver geofenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // showNotification();
        }
    };

    /**
     * Method used to show notification for dynamic form.
     */
    private void showNotification() {
        NotificationHelper helper = new NotificationHelper(this);
        helper.notify(19232301, helper.getNotification1(this.getString(R.string.app_name),
                "Please fill the vehicle inspection form with current observations about the vehicle", 1));
    }

    /**
     * Init socket if null
     */
    protected void initSocket() {
        if (webSocketManager == null) {
            String userId = sharedPreferences.getUserDetail().getUserId();
            String t = sharedPreferences.getLoginToken();
            String url = sharedPreferences.getChatUrl();

            Log.e(TAG, "initSocket() called");
            //initialize socket
            webSocketManager = WebSocketManager.getInstance(url, userId, t);
        }
    }

    /**
     * Check if socket is connected the hit connection with callback
     *
     * @param socketListener listener for socket events
     */
    protected void connectSocket(@NonNull WebSocketManager.SocketListener socketListener) {
        if (webSocketManager != null) {
            webSocketManager.addListener(socketListener);
        }
    }

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
//        /*TODO test here for navigation bar color and navigation button color*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (this instanceof MainActivity ||
                    this instanceof ChangeMobileActivity ||
                    this instanceof BuddyFilterActivity ||
                    this instanceof CreateTaskActivity ||
                    this instanceof DynamicFormActivity) {
                getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
            } else if (this instanceof BuddyListingActivity ||
                    this instanceof FleetListingActivity ||
                    this instanceof LoginActivity) {
                if (getIntent().hasExtra(EXTRA_BUDDY_LIST_CALLING_FROM_DASHBOARD_MENU) ||
                        getIntent().hasExtra(EXTRA_FLEET_LIST_CALLING_FROM_DASHBOARD_MENU) ||
                        getIntent().hasExtra(EXTRA_BUDDY_LIST_CALLING_FROM_BOTTOM_SHEET_MENU) ||
                        this instanceof LoginActivity) {
                    getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
                }
            }
        }
        super.onCreate(savedInstanceState);
        performDataBinding();
        geofenceUtil = new GeofenceUtil(BaseActivity.this);
        if (geofenceUtil.getGeofencesAdded()) {
            Log.e(TAG, "----> true");
        } else {
            Log.e(TAG, "----> false");
        }
    }

    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
    }

    @SuppressLint("MissingPermission")
    public void requestCurrentLocation(LocationCallback locationCallback) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    public void removeLocationUpdates(LocationCallback locationCallback) {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String[] permissions) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            String[] permi = new String[listPermissionsNeeded.size()];
            permi = listPermissionsNeeded.toArray(permi);
            ActivityCompat.requestPermissions(this, permi, PERMISSIONS_REQUEST_CODE_MULTIPLE);
            return false;
        }
        return true;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void openActivityOnTokenExpire(Intent intent) {
        //stop tracking
        if (TrackThat.isTracking()) {
            TrackThat.stopTracking();
        }
//        Intent intent = LoginActivity.newIntent(this);
        setFlags(intent);
        startActivity(intent);
        finish();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    protected void setFlags(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= 21) {
                    supportFinishAfterTransition();
                }
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setToolbar(Toolbar mToolbar, String title) {
        if (title != null) {
            mToolbar.setTitle(title);
            mToolbar.setTitleTextColor(Color.WHITE);
            mToolbar.setTitleTextAppearance(this, R.style.CamptonBookTextAppearance);
        }
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void showTimeOutMessage(@NonNull ApiCallback callback) {
        TrackiToast.Message.showShort(this, AppConstants.MSG_REQUEST_TIME_OUT_TYR_AGAIN);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideLoading();
        //overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    public void invalidToken(Intent intent) {
        //clear all data from preferences and open login activity
        sharedPreferences.clear(AppPreferencesHelper.PreferencesKeys.NOT_ALL);
        openActivityOnTokenExpire(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //start listening geofence enter and exit alerts.
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TrackThat.ACTION_GEOFENCE_ENTER);
        intentFilter.addAction(TrackThat.ACTION_GEOFENCE_EXIT);
        LocalBroadcastManager.getInstance(this).registerReceiver(geofenceReceiver, intentFilter);
        /*//if user is on main activity of task activity or create task activity then
        if (this instanceof MainActivity || this instanceof TaskActivity || this instanceof CreateTaskActivity) {
            //      Show battery optimization dialog if user disable this feature.
            CommonUtils.showBatteryOptimizationDialog(this);
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webSocketManager != null) {
            webSocketManager.removeListener();
        }
        //unregister when complete
        LocalBroadcastManager.getInstance(this).unregisterReceiver(geofenceReceiver);
    }

    public void showDialogOK(String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", onClickListener)
                .setNegativeButton("Cancel", onClickListener)
                .create()
                .show();
    }
}

