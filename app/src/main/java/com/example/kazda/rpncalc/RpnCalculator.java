package com.example.kazda.rpncalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import java.util.*;


public class RpnCalculator extends Activity {
    Stack<Double> stack;
    Button button[];
    Button button_back;
    Button button_chs;
    Button button_decimal;
    Button button_enter;
    Button button_clr;
    //Button button_eex;
    Button button_divide;
    Button button_multiply;
    Button button_subtract;
    Button button_add;
    TextView textView;
    Boolean lastWasOperator;

    private static final int[] BUTTON_IDS = {
        R.id.button_0,
        R.id.button_1,
        R.id.button_2,
        R.id.button_3,
        R.id.button_4,
        R.id.button_5,
        R.id.button_6,
        R.id.button_7,
        R.id.button_8,
        R.id.button_9,
    };

    protected void setTextValueToTop() {
        double value = stack.peek();
        String s = Double.toString(value);
        textView.setText(s);
    }

    protected void accumulate ( String d ) {
        String s = textView.getText().toString();
        if( lastWasOperator ) {
            s = d;
        } else {
            double f = 0.0;
            try { f = Double.parseDouble(s); }
            catch( NumberFormatException e ) {}
            if (f == 0.0)
                s = d;
            else
                s += d;
        }
        textView.setText(s);
    }

    protected void deccumulate () {
        String s = textView.getText().toString();
        if( s.length() > 0 )
            textView.setText( s.substring(0, s.length()-1) );
    }

    protected void createDigits() {
        button = new Button[10];
        int i = 0;
        for( int id : BUTTON_IDS ) {
            button[i] = (Button) findViewById(id);
            i++;
        }
        button[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accumulate("0");
                lastWasOperator = false;
            }
        });
        button[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accumulate("1");
                lastWasOperator = false;
            }
        });
        button[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accumulate("2");
                lastWasOperator = false;
            }
        });
        button[3].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accumulate("3");
                lastWasOperator = false;
            }
        });
        button[4].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accumulate("4");
                lastWasOperator = false;
            }
        });
        button[5].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accumulate("5");
                lastWasOperator = false;
            }
        });
        button[6].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accumulate("6");
                lastWasOperator = false;
            }
        });
        button[7].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accumulate("7");
                lastWasOperator = false;
            }
        });
        button[8].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accumulate("8");
                lastWasOperator = false;
            }
        });
        button[9].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accumulate("9");
                lastWasOperator = false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD )
//            setRequestedOrientation(SCREEN_ORIENTATION_SENSOR);
        setContentView(R.layout.activity_rpn_calculator);

        stack = new Stack<Double>();
        lastWasOperator = false;
        createDigits();
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("0");

        button_enter = (Button) findViewById(R.id.button_enter);
        button_enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = textView.getText().toString();
                double d = 0.0;
                try {
                    d = Double.valueOf(s);
                }
                catch( NumberFormatException e ) {
                }
                stack.push(d);
                // textView.setText("0");
                lastWasOperator = true;
            }
        });
        button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deccumulate();
                lastWasOperator = false;
            }
        });
        button_clr = (Button) findViewById(R.id.button_clr);
        button_clr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                while( !stack.empty() )
                    stack.pop();
                textView.setText("0");
            }
        });
        button_decimal = (Button) findViewById(R.id.button_decimal);
        button_decimal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accumulate(".");
                lastWasOperator = false;
            }
        });
        button_chs = (Button) findViewById(R.id.button_chs);
        button_chs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = textView.getText().toString();
                if( s.length() == 0 ) {
                    s = "-";
                } else {
                    char c = s.charAt(0);
                    if (c == '-') {
                        s = s.substring(1);
                    } else {
                        s = '-' + s;
                    }
                }
                textView.setText(s);
                lastWasOperator = false;
            }
        });
        button_add = (Button) findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String s = textView.getText().toString();
                    double b = Double.valueOf(s);
                    double a = stack.pop();
                    stack.push(a + b);
                    setTextValueToTop();
                    lastWasOperator = true;
                }
                catch( EmptyStackException e ) {
                }
            }
        });
        button_subtract = (Button) findViewById(R.id.button_subtract);
        button_subtract.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    double a = stack.pop();
                    String s = textView.getText().toString();
                    double b = Double.valueOf(s);
                    stack.push(a - b);
                    setTextValueToTop();
                    lastWasOperator = true;
                } catch (EmptyStackException e) {
                }
            }
        });
        button_multiply = (Button) findViewById(R.id.button_multiply);
        button_multiply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    double a = stack.pop();
                    String s = textView.getText().toString();
                    double b = Double.valueOf(s);
                    stack.push(a * b);
                    setTextValueToTop();
                    lastWasOperator = true;
                } catch (EmptyStackException e) {
                }
            }
        });
        button_divide = (Button) findViewById(R.id.button_divide);
        button_divide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    double a = stack.pop();
                    String s = textView.getText().toString();
                    double b = Double.valueOf(s);
                    stack.push(a / b);
                    setTextValueToTop();
                    lastWasOperator = true;
                }
                catch ( EmptyStackException e ) {
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rpn_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
