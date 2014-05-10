package com.example.amex_test;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;


/**
 * Created by eriese on 4/2/14.
 */
public class ItemForm extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View formRow = inflater.inflate(R.layout.item_form, container, false);
    EditText item = (EditText) formRow.findViewById(R.id.itemDescription);
    item.requestFocus();

    Button plus = (Button) formRow.findViewById(R.id.plusButton);
    Button minus = (Button) formRow.findViewById(R.id.minusButton);
    final EditText numItems = (EditText) formRow.findViewById(R.id.amount);

    plus.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Integer amt = Integer.parseInt(numItems.getText().toString());
        amt += 1;
        numItems.setText(amt.toString());
      }
    });

    minus.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Integer amt = Integer.parseInt(numItems.getText().toString());
        amt -= 1;
        numItems.setText(amt.toString());
      }
    });

    return formRow;
  }

  public void calculateTax(Intent intent) {
    String[] exempt_items = {"book", "chocolate", "pill"};

    EditText itemName = (EditText) getView().findViewById(R.id.itemDescription);
    String itemDescription = itemName.getText().toString();

    Integer taxBase = 10;
    for (String word : exempt_items) {
      if (itemDescription.toLowerCase().contains(word)) {
        taxBase -= 10;
      }
    }
    if (itemDescription.toLowerCase().contains("imported")) {
      taxBase += 5;
    }


    EditText itemAmt = (EditText) getView().findViewById(R.id.amount);
    Integer amt = Integer.parseInt(itemAmt.getText().toString());
    EditText itemPrice = (EditText) getView().findViewById(R.id.price);
    Float price;
    String priceString = itemPrice.getText().toString();

    try{
      price = Float.parseFloat(itemPrice.getText().toString());
    } catch (NumberFormatException e) {
      price = 0.00f;
    }

    Float preRoundTax = taxBase * amt * price / 100;
    Float roundTax = FloatMath.ceil(preRoundTax * 20)/20;
    Float taxCalc = price * amt + roundTax;
    intent.putExtra(getTag() + " item", itemDescription);
    intent.putExtra(getTag() + " amount", amt);
    intent.putExtra(getTag() + " price",price);
    intent.putExtra(getTag() + " total", taxCalc);
  }
}