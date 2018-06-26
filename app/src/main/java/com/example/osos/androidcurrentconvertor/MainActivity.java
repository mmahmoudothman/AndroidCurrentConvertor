package com.example.osos.androidcurrentconvertor;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.osos.androidcurrentconvertor.model.Coin;
import com.example.osos.androidcurrentconvertor.remote.CoinService;
import com.example.osos.androidcurrentconvertor.remote.Common;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    CoinService mCoinService;
    RadioButton coin2coin, money2Coin,coin2money;
    MaterialSpinner fromSpinner,toSpinner;
    RadioGroup radioGroup;
    Button btnConvert;
    TextView toTextView,date;
    Calendar calendar;

    String[] money={"USD","EUR","GBP"};
    String[] coin={"BTC","ETH","ETC","XRP","LTC","XMR","DASH","MAID","AUR","XEM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoinService= Common.getCoinService();

        fromSpinner=findViewById(R.id.fromSpinner);
        toSpinner=findViewById(R.id.ToSpinner);
        btnConvert=findViewById(R.id.btn_converter);
        radioGroup=findViewById(R.id.radio_group);
        toTextView=findViewById(R.id.textview);
        date = (TextView) findViewById(R.id.date);
        date.setText(getCurrentMonth().substring(0, 3) + " " + getCurrentDate() + "," + getCurrentYear());


        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalculateValue();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.coin2coin)
                    setCoin2CoinSource();
                else if (i==R.id.money2coin)
                    setMoney2CoinSource();
                else if (i==R.id.coin2Money)
                    setCoin2MoneySource();
            }
        });

        coin2coin =findViewById(R.id.coin2coin);
        coin2money=findViewById(R.id.coin2Money);
        money2Coin =findViewById(R.id.money2coin);

        LoadCoinList();

    }

    private void LoadCoinList() {
        if (coin2coin.isSelected())
            setCoin2CoinSource();
        else if (coin2money.isSelected())
            setCoin2MoneySource();
        else if (money2Coin.isSelected())
            setMoney2CoinSource();
    }

    private void setCoin2MoneySource() {
        fromSpinner.setItems(coin);
        toSpinner.setItems(money);
    }

    private void setMoney2CoinSource() {
        fromSpinner.setItems(money);
        toSpinner.setItems(coin);
    }

    private void setCoin2CoinSource() {
        fromSpinner.setItems(coin);
        toSpinner.setItems(coin);
    }

    private void CalculateValue() {
        final ProgressDialog mDialog=new ProgressDialog(MainActivity.this);
        mDialog.setMessage("Please wait...");
        mDialog.show();

        final String coinName=toSpinner.getItems().get(toSpinner.getSelectedIndex()).toString();
        String fromCoin=fromSpinner.getItems().get(fromSpinner.getSelectedIndex()).toString();

        mCoinService.calculateValue(fromCoin,coinName).enqueue(new Callback<Coin>() {
            @Override
            public void onResponse(Call<Coin> call, Response<Coin> response) {
                mDialog.dismiss();
                if (coinName.equals("BTC"))
                    showData(response.body().getBTC());
                else if (coinName.equals("ETC"))
                    showData(response.body().getETC());
                else if (coinName.equals("ETH"))
                    showData(response.body().getETH());
                else if (coinName.equals("AUR"))
                    showData(response.body().getAUR());
                else if (coinName.equals("DASH"))
                    showData(response.body().getDASH());
                else if (coinName.equals("MAID"))
                    showData(response.body().getMARB());
                else if (coinName.equals("LTC"))
                    showData(response.body().getLTC());
                else if (coinName.equals("XMR"))
                    showData(response.body().getXMR());
                else if (coinName.equals("XEM"))
                    showData(response.body().getXEM());
                else if (coinName.equals("USD"))
                    showData(response.body().getUSD());
                else if (coinName.equals("EUR"))
                    showData(response.body().getEUR());
                else if (coinName.equals("GBP"))
                    showData(response.body().getGBP());

            }

            @Override
            public void onFailure(Call<Coin> call, Throwable t) {
                Log.e("Error",t.getMessage());
                mDialog.dismiss();
            }
        });




    }

    private void showData(String value) {

        if (toSpinner.getItems().get(toSpinner.getSelectedIndex()).equals("BTC"))
            toTextView.setText(value);
        else if (toSpinner.getItems().get(toSpinner.getSelectedIndex()).equals("ETC"))
            toTextView.setText(value);
        else if (toSpinner.getItems().get(toSpinner.getSelectedIndex()).equals("ETH"))
            toTextView.setText(value);
        else if (toSpinner.getItems().get(toSpinner.getSelectedIndex()).equals("XRP"))
            toTextView.setText(value);
        else if (toSpinner.getItems().get(toSpinner.getSelectedIndex()).equals("LTC"))
            toTextView.setText(value);
        else if (toSpinner.getItems().get(toSpinner.getSelectedIndex()).equals("AUR"))
            toTextView.setText(value);
        else if (toSpinner.getItems().get(toSpinner.getSelectedIndex()).equals("DASH"))
            toTextView.setText(value);
        else if (toSpinner.getItems().get(toSpinner.getSelectedIndex()).equals("MAID"))
            toTextView.setText(value);
        else if (toSpinner.getItems().get(toSpinner.getSelectedIndex()).equals("XMR"))
            toTextView.setText(value);
        else if (toSpinner.getItems().get(toSpinner.getSelectedIndex()).equals("XEM"))
            toTextView.setText(value);
        else if (toSpinner.getItems().get(toSpinner.getSelectedIndex()).equals("USD"))
            toTextView.setText("$ "+ value);
        else if (toSpinner.getItems().get(toSpinner.getSelectedIndex()).equals("GBP"))
            toTextView.setText("£ "+value);
        else if (toSpinner.getItems().get(toSpinner.getSelectedIndex()).equals("EUR"))
            toTextView.setText("€ "+value);


    }

    private String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd");
        String strDate = mdformat.format(calendar.getTime());
        String year = strDate.substring(0, 4);
        return year;
    }

    private String getCurrentMonth() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        String month = (dateFormat.format(date));
        String currMonth = null;
        if (month.equals("01")) {
            currMonth = "January";
        } else if (month.equals("02")) {
            currMonth = "February";
        } else if (month.equals("03")) {
            currMonth = "March";
        } else if (month.equals("04")) {
            currMonth = "April";
        } else if (month.equals("05")) {
            currMonth = "May";
        } else if (month.equals("06")) {
            currMonth = "June";
        } else if (month.equals("07")) {
            currMonth = "July";
        } else if (month.equals("08")) {
            currMonth = "August";
        } else if (month.equals("09")) {
            currMonth = "September";
        } else if (month.equals("10")) {
            currMonth = "October";
        } else if (month.equals("11")) {
            currMonth = "November";
        } else if (month.equals("12")) {
            currMonth = "December";
        }
        return currMonth;
    }

    private String getCurrentDate() {
        calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = mdformat.format(calendar.getTime());
        String date = strDate.substring(0, 2);
        return date;
    }
}

