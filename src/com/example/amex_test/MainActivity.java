package com.example.amex_test;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
  /**
   * Called when the activity is first created.
   */
  public Integer mFragments = 0;
  // This integer is the key to passing information from multiple instances of the
  // same fragment. By counting fragments and passing the count through the intent to the next activity,
  // I can dynamically tag my fragments, add the tags to the extras coming from each fragment,
  // and then iterate back through them.

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Button addButton = (Button) findViewById(R.id.newRowButton);
    final FragmentManager fragmentManager = getFragmentManager();
    addFormRow(fragmentManager);

    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addFormRow(fragmentManager);
      }
    });

    Button receiptButton = (Button) findViewById(R.id.receiptButton);

    receiptButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        calculateReceipt();
      }
    });
  }
  private void addFormRow(FragmentManager fragmentManager) {
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    ItemForm fragment = new ItemForm();
    String thisTag = "item" + mFragments;
    fragmentTransaction.add(R.id.container, fragment, thisTag);
    fragmentTransaction.commit();
    ++mFragments;
  }

  public void calculateReceipt() {
    ViewGroup container = (ViewGroup) findViewById(R.id.container);
    final Intent intent = new Intent(MainActivity.this,
      DisplayReceipt.class);

    for(int i = 0; i < mFragments; ++i) {
      String tag = "item" + i;
      ItemForm formLine = (ItemForm) getFragmentManager().findFragmentByTag(tag);
      formLine.calculateTax(intent);
    }

    intent.putExtra("number of fragments", mFragments);
    startActivity(intent);
  }
}
