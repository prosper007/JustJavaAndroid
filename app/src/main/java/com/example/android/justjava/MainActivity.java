/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice();
        boolean whipppedStatus = getWhippedStatus();
        boolean chocolateStatus = getChocolateStatus();
        String name = getName();
        String priceMessage = createOrderSummary(price, whipppedStatus, chocolateStatus, name);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "onungwap@gmail.com");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        emailIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        emailIntent.setData(Uri.parse("mailto:onungwap@gmail.com"));
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
        displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order.
     *
     * //@param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice() {
        boolean whipppedStatus = getWhippedStatus();
        boolean chocolateStatus = getChocolateStatus();
        int price = 5;
        if(whipppedStatus){
            price += 1;
        }
        if(chocolateStatus){
            price += 2;
        }
        price *= quantity;
         return price;
    }

    private String createOrderSummary(int priceOfOrder, boolean whippedStatus, boolean chocolateStatus, String name){
        String orderMessage = getString(R.string.order_name, name)  + "\n";
        orderMessage += "\n" + getString(R.string.order_quantity, quantity) + "\n" ;
        orderMessage += "\n" + getString(R.string.order_whipped_cream, whippedStatus) + "\n";
        orderMessage += "\n"+ getString(R.string.order_chocolate, chocolateStatus) + "\n";
        orderMessage += "\n" + getString(R.string.order_total, NumberFormat.getCurrencyInstance().format(priceOfOrder)) + "\n";
        orderMessage += "\n" + getString(R.string.thank_you) + "\n";
        return orderMessage;
    }

    public boolean getWhippedStatus(){
        CheckBox whippedCheck = findViewById(R.id.whipped_Check);
        return whippedCheck.isChecked();
    }

    public boolean getChocolateStatus(){
        CheckBox chocolateCheck = findViewById(R.id.chocolate_Check);
        return chocolateCheck.isChecked();
    }

    public String getName(){
        EditText name = findViewById(R.id.name);
        return name.getText().toString();
    }

    public void increment(View view) {
        if(quantity < 100) {
            quantity++;
        }
        else{
            CharSequence tooMuchCoffee = getString(R.string.too_much_coffee_toast);
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(this, tooMuchCoffee, duration).show();
        }
        display(quantity);
        int price = calculatePrice();
        displayMessage("$"+price);
    }
    public void decrement(View view) {
        if(quantity > 1) {
            quantity--;
        }
        else{
            CharSequence noCoffee = getString(R.string.too_little_coffee_toast);
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(this, noCoffee, duration).show();
        }
        display(quantity);
        int price = calculatePrice();
        displayMessage("$"+price);

    }
    public void toppingChecked(View view) {
        int price = calculatePrice();
        displayMessage("$"+price);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String message) {
        TextView ordersummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        ordersummaryTextView.setText(message);
    }
}