package comcesar1287.github.www.collie.view;

import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import comcesar1287.github.www.collie.controller.admin.CollieAdminReceiver;
import comcesar1287.github.www.collie.R;
import comcesar1287.github.www.collie.controller.data.SharedPref;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private DevicePolicyManager mDPM;

    private ComponentName mDeviceAdmin;

    private Toolbar toolbar;
    private final static int REQUEST_CODE_ENABLE_ADMIN = 1;

    private RelativeLayout rlMainAlert, reports, schedule, tasks, localization;

    private Boolean mLocationPermissionGranted;

    NavigationView navigationView;
    private SharedPreferences prefs;

    String typeUser ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initToolbar();
        initDrawer();
        initComponent();

        recordUser();
        getData();

        blockUser();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mDPM!=null) {
            //Toast.makeText(this, String.valueOf(mDPM.isAdminActive(mDeviceAdmin)), Toast.LENGTH_SHORT).show();
        }

        blockUser();
        indicationTypeBlock();
    }



    private void verifyIfUserCompletedRegister() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("config")
                        .child(mAuth.getCurrentUser().getUid())
                        .exists()) {
                    rlMainAlert.setVisibility(View.VISIBLE);
                } else{
                    rlMainAlert.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,
                        getResources().getString(R.string.error_unknown_error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            startActivity(new Intent(this, MapsActivity.class));
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.main_image_alert:
                rlMainAlert.setVisibility(View.GONE);
                break;
            case R.id.main_alert:
                SharedPref sharedPref = new SharedPref(this);
                String type = sharedPref.getTypeBlock();

                if(getString(R.string.setup_screen_simple).equals(type)){
                    startActivity(new Intent(this, ConfigBlockSimpleActivity.class));
                }else if(getString(R.string.setup_screen_points).equals(type)){
                    startActivity(new Intent(this, ConfigBlockPointsActivity.class));
                }else if(getString(R.string.setup_screen_time).equals(type)){
                    startActivity(new Intent(this, ConfigBlockTimeActivity.class));
                }
                break;
            case R.id.main_reports:
                startActivity(new Intent(this, ReportsActivity.class));
                break;
            case R.id.main_schedule:
                startActivity(new Intent(this, ScheduleActivity.class));
                break;
            case R.id.main_tasks:
                startActivity(new Intent(this, TasksActivity.class));
                break;
            case R.id.main_localization:
                getLocationPermission();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_ENABLE_ADMIN){
            if(!mDPM.isAdminActive(mDeviceAdmin)){
                //TODO
                //mostrar uma msg bonitinha falando que precisa ativar
            }else{
                //TODO
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    startActivity(new Intent(this, MapsActivity.class));
                }else{
                    Toast.makeText(this, "É necessário dar permissão para a localição para acessar essa opção", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void checkIfAdminIsActive() {
        if(!mDPM.isAdminActive(mDeviceAdmin)){
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdmin);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    getResources().getString(R.string.add_admin_extra_app_text));
            startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
        }
    }

    private void initComponent() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDeviceAdmin = new ComponentName(this, CollieAdminReceiver.class);

        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        ImageView ivMainImageAlert = findViewById(R.id.main_image_alert);
        ivMainImageAlert.setOnClickListener(this);
        rlMainAlert = findViewById(R.id.main_alert);
        rlMainAlert.setOnClickListener(this);

        reports = findViewById(R.id.main_reports);
        reports.setOnClickListener(this);
        schedule = findViewById(R.id.main_schedule);
        schedule.setOnClickListener(this);
        tasks = findViewById(R.id.main_tasks);
        tasks.setOnClickListener(this);
        localization = findViewById(R.id.main_localization);
        localization.setOnClickListener(this);
    }

    private void initDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        indicationTypeBlock();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_screem_main) {

        } else if (id == R.id.nav_edit_profile) {
            Intent intent = new Intent(this, RegisterEditActivity.class);
            intent.putExtra("edit", true);
            startActivity(intent);
        } else if (id == R.id.nav_register_son) {
            Intent intent = new Intent(this, RegisterEmailSonActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_edit_config) {
            SharedPref sharedPref = new SharedPref(this);
            String type = sharedPref.getTypeBlock();

            if(getString(R.string.setup_screen_simple).equals(type)){
                startActivity(new Intent(this, ConfigBlockSimpleActivity.class));
            }else if(getString(R.string.setup_screen_points).equals(type)){
                startActivity(new Intent(this, ConfigBlockPointsActivity.class));
            }else if(getString(R.string.setup_screen_time).equals(type)){
                startActivity(new Intent(this, ConfigBlockTimeActivity.class));
            }
        } else if (id == R.id.nav_change_block) {
            startActivity(new Intent(this, SetupScreenActivity.class));
            finish();
        } else if (id == R.id.nav_list_reports) {
            startActivity(new Intent(this, ReportsActivity.class));
        } else if (id == R.id.nav_list_task) {
            startActivity(new Intent(this, TasksActivity.class));
        } else if (id == R.id.nav_access_for_life) {
            startActivity(new Intent(this, CompletedPeriodActivity.class));
        } else if (id == R.id.nav_exit) {
            signOut();
        } else if (id == R.id.nav_who_are_you) {

        } else if (id == R.id.nav_contact_us) {
            startActivity(new Intent(this, ContactUsActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void indicationTypeBlock(){
        SharedPref sharedPref = new SharedPref(this);
        String type = sharedPref.getTypeBlock();

        if(getString(R.string.setup_screen_simple).equals(type)){
            setTitle(R.string.actionbar_indication_block_simple);
        }else if(getString(R.string.setup_screen_points).equals(type)){
            setTitle(R.string.actionbar_indication_block_points);
        }else if(getString(R.string.setup_screen_time).equals(type)){
            setTitle(R.string.actionbar_indication_block_time);
        }
    }

    private void signOut() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tem certeza que deseja sair?")
                .setPositiveButton(R.string.main_dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mAuth.signOut();
                        startActivity(new Intent(MainActivity.this, CategoryRegisterActivity.class));
                        finish();
                    }
                })
                .setNegativeButton(R.string.main_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    private void blockUser(){
        if (!msg().equals("erro")) {

            if(msg().equals("responsible")){
                invibleForResponsible();
            }
            else if(msg().equals("dependent")){
                invibleForDependent();
            }
            else if(msg().equals("config")){
                verifyIfUserCompletedRegister();
            }
            else if(msg().equals("register")){
                verifyIfUserCompletedRegister();
            }
        }
        else{
            verifyIfUserCompletedRegister();
        }
    }

    private void invibleForResponsible(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_edit_config).setVisible(false);
        nav_Menu.findItem(R.id.nav_change_block).setVisible(false);
        nav_Menu.findItem(R.id.nav_register_son).setVisible(false);
    }

    private void invibleForDependent(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_edit_config).setVisible(false);
        nav_Menu.findItem(R.id.nav_change_block).setVisible(false);
        nav_Menu.findItem(R.id.nav_contact_us).setVisible(false);
        nav_Menu.findItem(R.id.nav_edit_profile).setVisible(false);
        nav_Menu.findItem(R.id.nav_access_for_life).setVisible(false);
        nav_Menu.findItem(R.id.nav_register_son).setVisible(false);

        reports.setVisibility(View.INVISIBLE);
        localization.setVisibility(View.INVISIBLE);

    }

    public String msg(){
        String user;

        prefs = getSharedPreferences("preferencias", Context.MODE_PRIVATE);

        user = prefs.getString("key", "erro");
        return user;

    }

    private void recordUser(){

        if (msg().equals("responsible") || msg().equals("dependet")) {
            prefs = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = prefs.edit();
            ed.putString(typeUser, msg());
            ed.apply();
        }

    }

    public String getData() {

        if (prefs!= null) {
            return prefs.getString(typeUser, "");
        }

        return "";
    }

    public void teste(){
        Toast.makeText(getBaseContext(), getData() ,Toast.LENGTH_SHORT).show();
    }

}