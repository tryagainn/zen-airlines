package com.srgiovine.zenairlines;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.srgiovine.zenairlines.data.ZenDB;
import com.srgiovine.zenairlines.model.Customer;

/**
 * Allows passengers to create a customer account.
 */
public class CreateCustomerAccountActivity extends ZenAirlinesActivity {

    public static final String RESULT_CUSTOMER_ID = "customer_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer_account);
    }

    public void onCreateCustomerAccountButtonClicked(View view) {
        if (!validateEditTexts(R.id.first_name, R.id.last_name, R.id.ssn, R.id.phone_number,
                R.id.email, R.id.street_address, R.id.city, R.id.zip)) {
            return;
        }

        getZenDB().insertCustomerAsync(createCustomerFromViews(), new ZenDB.Callback<Long>() {
            @Override
            public void success(Long newCustomerId) {
                Intent data = new Intent();
                data.putExtra(RESULT_CUSTOMER_ID, newCustomerId);
                setResult(RESULT_OK, data);
                finish();
            }

            @Override
            public void failed() {
                setResult(RESULT_FAILED);
                finish();
            }
        });
    }

    private Customer createCustomerFromViews() {
        return new Customer.Builder()
                .setFirstName(getEditTextValue(R.id.first_name, String.class))
                .setLastName(getEditTextValue(R.id.last_name, String.class))
                .setSsn(getEditTextValue(R.id.ssn, String.class))
                .setPhoneNumber(getEditTextValue(R.id.phone_number, String.class))
                .setEmail(getEditTextValue(R.id.email, String.class))
                .setAddress(getEditTextValue(R.id.street_address, String.class))
                .setCity(getEditTextValue(R.id.city, String.class))
                .setState(getSpinnerValue(R.id.state, String.class))
                .setZip(getEditTextValue(R.id.zip, String.class))
                .createCustomer();
    }

}
