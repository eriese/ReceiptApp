package com.example.amex_test;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by eriese on 4/3/14.
 */
public class ReceiptRow extends Fragment {

  public Integer mNumItems;
  public String mItemName;
  public String mFinalPrice;

  public static ReceiptRow newInstance(int numItems, String itemName, Float finalPrice) {
    ReceiptRow row = new ReceiptRow();
    Bundle args = new Bundle();

    DecimalFormat money = new DecimalFormat("0.00");
    money.setRoundingMode(RoundingMode.HALF_UP);

    String clippedPrice = money.format(finalPrice);

    args.putInt("number of items", numItems);
    args.putString("item name", itemName);
    args.putString("final price", clippedPrice);
    row.setArguments(args);

    return row;
  }

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = getArguments();
    mNumItems = args.getInt("number of items");
    mItemName = args.getString("item name");
    mFinalPrice = args.getString("final price");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View row = inflater.inflate(R.layout.receipt_row, container, false);

    TextView amount = (TextView) row.findViewById(R.id.receiptAmount);
    TextView itemDescription = (TextView) row.findViewById(R.id.receiptDescription);
    TextView pricePlusTax = (TextView) row.findViewById(R.id.receiptPrice);

    amount.setText(mNumItems.toString());
    itemDescription.setText(mItemName);
    pricePlusTax.setText(mFinalPrice);

    return row;
  }

}