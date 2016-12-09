package com.banjo.chitcalculator;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalculatorActivity extends AppCompatActivity {

    @BindView(R.id.editTextOriginalDuration)
    EditText editTextOriginalDuration;

    @BindView(R.id.editTextActualDuration)
    EditText editTextActualDuration;

    @BindView(R.id.buttonCalculate)
    Button buttonCalculate;

    @BindView(R.id.textResult)
    TextView textResult;

    private ProgressDialog progressDialog;

    public void setLoadingDialog(boolean isLoading) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (isLoading) {
            progressDialog = ProgressDialog.show(this, null, "Calculating...", true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        ButterKnife.bind(this);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AppUtils.hideSoftKeyboard(CalculatorActivity.this);
                calculateAPR(Integer.parseInt(editTextOriginalDuration.getText()
                                                                      .toString()), Integer.parseInt(editTextActualDuration.getText()
                                                                                                                           .toString()));
            }
        });
    }

    private void calculateAPR(final int originalTenure, final int actualTenure) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                final double rate = ChitfundUtil.getRate(originalTenure, actualTenure);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        setLoadingDialog(false);
                        textResult.setText(String.format(Locale.getDefault(), "%.2f %% APR", ChitfundUtil.getAPR(rate)));
                    }
                });
            }
        });

        setLoadingDialog(true);
        thread.start();
    }
}
