package com.instanect.contactsManipulation;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.instanect.androidContactsManipulationModule.api.ContactsApi;
import com.instanect.androidContactsManipulationModule.structs.PhoneContactCompleteObject;
import com.instanect.androidContactsManipulationModule.structs.accountType.PhoneContactAccountType;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactEmailData;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactPhoneData;
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactNameData;
import com.instanect.androidContactsManipulationModule.structs.work.PhoneContactWorkData;

import org.fluttercode.datafactory.impl.DataFactory;

import java.util.ArrayList;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    ContactsApi contactsApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsApi = new ContactsApi(this);

        MainActivityPermissionsDispatcher.showCameraWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void showCamera() {
        // insertOps();
        contactsApi.getContacts();
    }


    @OnShowRationale({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("Permission pl")
                .setPositiveButton("ok", (dialog, button) -> request.proceed())
                .setNegativeButton("No", (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void showDeniedForCamera() {
    }

    @OnNeverAskAgain({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void showNeverAskForCamera() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.gen_save) {
            generateAndSave();
        }
        return super.onOptionsItemSelected(item);
    }

    private void generateAndSave() {
        DataFactory df = new DataFactory();
        PhoneContactAccountType phoneContactAccountType = new PhoneContactAccountType();

        phoneContactAccountType.setAccountName("test");
        phoneContactAccountType.setAccountType("instanect.com");


        PhoneContactNameData phoneContactNameData = new PhoneContactNameData();
        String name = "Test " + df.getFirstName();
        phoneContactNameData.setFirstName(name);
        Log.d("Phone Contact", "Generated First name: " + name);
        phoneContactNameData.setLastName(df.getLastName());

        PhoneContactWorkData phoneContactWorkData = new PhoneContactWorkData();
        phoneContactWorkData.setCompany("My Org");
        phoneContactWorkData.setJobTitle("My Title");

        PhoneContactEmailData phoneContactEmailData = new PhoneContactEmailData();
        phoneContactEmailData.setEmail(df.getEmailAddress());
        PhoneContactEmailData phoneContactEmailData1 = new PhoneContactEmailData();
        phoneContactEmailData1.setEmail(df.getEmailAddress());

        PhoneContactPhoneData phoneContactPhoneData = new PhoneContactPhoneData();
        phoneContactPhoneData.setPhoneNumber("4455445556");

        PhoneContactPhoneData phoneContactPhoneData1 = new PhoneContactPhoneData();
        phoneContactPhoneData1.setPhoneNumber("7896786786");

        ArrayList<PhoneContactEmailData> phoneContactEmailDataArrayList = new ArrayList<>();
        phoneContactEmailDataArrayList.add(phoneContactEmailData);
        phoneContactEmailDataArrayList.add(phoneContactEmailData1);

        ArrayList<PhoneContactPhoneData> phoneContactPhoneDataArrayList = new ArrayList<>();
        phoneContactPhoneDataArrayList.add(phoneContactPhoneData);
        phoneContactPhoneDataArrayList.add(phoneContactPhoneData1);

        PhoneContactCompleteObject phoneContactCompleteObject = new PhoneContactCompleteObject();
        phoneContactCompleteObject.setPhoneContactAccountType(phoneContactAccountType);
        phoneContactCompleteObject.setPhoneContactNameData(phoneContactNameData);
        phoneContactCompleteObject.setPhoneContactWorkData(phoneContactWorkData);
        phoneContactCompleteObject.setPhoneContactEmailDataList(phoneContactEmailDataArrayList);
        phoneContactCompleteObject.setPhoneContactPhoneDataList(phoneContactPhoneDataArrayList);


        contactsApi.addContact(phoneContactCompleteObject);
        Toast.makeText(this, "Contact with name : " + name + " added", Toast.LENGTH_LONG);
    }
}
