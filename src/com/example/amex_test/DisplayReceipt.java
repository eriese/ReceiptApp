package com.example.amex_test;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by eriese on 4/2/14.
 */
public class DisplayReceipt extends Activity {

  private Float mPreTax = 0f;
  private Float mAfterTax = 0f;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.display_receipt);
    final FragmentManager fragmentManager = getFragmentManager();
    Intent intent = getIntent();
    Integer ids = intent.getIntExtra("number of fragments", 0);
    for(int i = 0; i < ids; ++i) {
      String tag = "item" + i;
      addReceiptRow(fragmentManager, intent, tag);
    }
    Float totalTax = mAfterTax - mPreTax;
    DecimalFormat money = new DecimalFormat("0.00");
    money.setRoundingMode(RoundingMode.HALF_UP);

    String clippedTotalTax = money.format(totalTax);
    TextView tax = (TextView) findViewById(R.id.taxTotal);
    tax.setText(clippedTotalTax);

    String clippedAfterTax = money.format(mAfterTax);
    TextView afterTax = (TextView) findViewById(R.id.allTold);
    afterTax.setText(clippedAfterTax);
  }

  public ReceiptRow addReceiptRow(FragmentManager fragmentManager, Intent intent, String tag) {
    Float total = intent.getFloatExtra(tag + " total", 10.25f);
    Integer amount = intent.getIntExtra(tag + " amount", 1);
    String itemDescription = intent.getStringExtra(tag + " item");
    Float price = intent.getFloatExtra(tag + " price", 10.25f);

    mAfterTax += total;
    mPreTax += price * amount;

    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    ReceiptRow fragment = ReceiptRow.newInstance(amount, itemDescription, total);
    fragmentTransaction.add(R.id.receiptContainer, fragment, tag);
    fragmentTransaction.commit();
    return fragment;
  }
}