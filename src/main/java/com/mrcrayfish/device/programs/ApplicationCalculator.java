package com.mrcrayfish.device.programs;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.app.component.TextField;
import com.mrcrayfish.device.api.app.listener.ClickListener;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;

public class ApplicationCalculator extends Application {

    private Layout layoutMain;
    private Button buttonZero, buttonDot, buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine, buttonTen;
    private Button buttonEquals, buttonPlus, buttonMinus, buttonAsterisk, buttonSlash;
    private Button buttonDel, buttonC;
    private TextField textFieldInput;
    private TextField labelSign;

    private static final Color APP_SCHEME = Color.decode("#FD8C3B");

    private double num1, num2, ans;
    private int op;

    private void detectOperation(){
        switch (op){
            case 1:
                num2 = Double.parseDouble(textFieldInput.getText());
                ans = num1 + num2;
                textFieldInput.setText(Double.toString(ans));
                break;
            case 2:
                num2 = Double.parseDouble(textFieldInput.getText());
                ans = num1 - num2;
                labelSign.setText(labelSign.getText());
                textFieldInput.setText(Double.toString(ans));
                break;
            case 3:
                num2 = Double.parseDouble(textFieldInput.getText());
                ans = num1 * num2;
                textFieldInput.setText(Double.toString(ans));
                break;
            case 4:
                num2 = Double.parseDouble(textFieldInput.getText());
                ans = num1 / num2;
                textFieldInput.setText(Double.toString(ans));
                break;

        }
    }


    @Override
    public void init() {
        layoutMain = new Layout(86, 160);
        layoutMain.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Gui.drawRect(x, y, x + width, y + 20, APP_SCHEME.getRGB());
            Gui.drawRect(x, y + 20, x + width, y + 21, Color.DARK_GRAY.getRGB());
        });

        textFieldInput = new TextField(5,40,layoutMain.width-10);
        textFieldInput.setEnabled(false);
        textFieldInput.setPlaceholder("Calculate");
        layoutMain.addComponent(textFieldInput);

        labelSign = new TextField(5, 26, layoutMain.width-10);
        labelSign.setTextColour(Color.BLACK);
        //labelSign.setShadow(false);
        layoutMain.addComponent(labelSign);

        buttonZero = new Button(5, 140, "0");
        buttonZero.setSize(36,16);
        layoutMain.addComponent(buttonZero);
        buttonZero.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                textFieldInput.setText(textFieldInput.getText() + "0");
            }
        });

        buttonDot = new Button(46, 140, ".");
        buttonDot.setSize(16,16);
        layoutMain.addComponent(buttonDot);
        buttonDot.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                textFieldInput.setText(textFieldInput.getText() + ".");
            }
        });

        buttonOne = new Button(5, 120, "1");
        layoutMain.addComponent(buttonOne);
        buttonOne.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                textFieldInput.setText(textFieldInput.getText() + "1");
            }
        });

        buttonTwo = new Button(26, 120, "2");
        layoutMain.addComponent(buttonTwo);
        buttonTwo.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                textFieldInput.setText(textFieldInput.getText() + "2");
            }
        });

        buttonThree = new Button(46, 120, "3");
        layoutMain.addComponent(buttonThree);
        buttonThree.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                textFieldInput.setText(textFieldInput.getText() + "3");
            }
        });

        buttonFour = new Button(5, 100, "4");
        layoutMain.addComponent(buttonFour);
        buttonFour.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                textFieldInput.setText(textFieldInput.getText() + "4");
            }
        });


        buttonFive = new Button(26, 100, "5");
        layoutMain.addComponent(buttonFive);
        buttonFive.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                textFieldInput.setText(textFieldInput.getText() + "5");
            }
        });


        buttonSix = new Button(46, 100, "6");
        layoutMain.addComponent(buttonSix);
        buttonSix.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                textFieldInput.setText(textFieldInput.getText() + "6");
            }
        });


        buttonSeven = new Button(5, 80, "7");
        layoutMain.addComponent(buttonSeven);
        buttonSeven.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                textFieldInput.setText(textFieldInput.getText() + "7");
            }
        });

        buttonEight = new Button(26, 80, "8");
        layoutMain.addComponent(buttonEight);
        buttonEight.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                textFieldInput.setText(textFieldInput.getText() + "8");
            }
        });


        buttonNine = new Button(46, 80, "9");
        layoutMain.addComponent(buttonNine);
        buttonNine.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                textFieldInput.setText(textFieldInput.getText() + "9");
            }
        });


        buttonEquals = new Button(66, 140, "=");
        layoutMain.addComponent(buttonEquals);
        buttonEquals.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                detectOperation();
                labelSign.setText(labelSign.getText() + num2 + "=");
            }
        });


        buttonPlus = new Button(66, 120, "+");
        layoutMain.addComponent(buttonPlus);
        buttonPlus.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                if(textFieldInput.getText() != ""){
                    num1 = Double.parseDouble(textFieldInput.getText());
                    op = 1;
                    textFieldInput.setText("");
                    labelSign.setText(num1 + "+");
                }
            }
        });


        buttonMinus = new Button(66, 100, "-");
        layoutMain.addComponent(buttonMinus);
        buttonMinus.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                if(textFieldInput.getText() != ""){
                    num1 = Double.parseDouble(textFieldInput.getText());
                    op = 2;
                    textFieldInput.setText("");
                    labelSign.setText(num1 + "-");
                }
            }
        });


        buttonAsterisk = new Button(66, 80, "*");
        buttonAsterisk.setSize(16,16);
        layoutMain.addComponent(buttonAsterisk);
        buttonAsterisk.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                if(textFieldInput.getText() != ""){
                    num1 = Double.parseDouble(textFieldInput.getText());
                    op = 3;
                    textFieldInput.setText("");
                    labelSign.setText(num1 + "*");
                }
            }
        });

        buttonSlash = new Button(66, 60, "/");
        layoutMain.addComponent(buttonSlash);
        buttonSlash.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                if(textFieldInput.getText() != ""){
                    num1 = Double.parseDouble(textFieldInput.getText());
                    op = 4;
                    textFieldInput.setText("");
                    labelSign.setText(num1 + "/");
                }
            }
        });

        buttonDel = new Button(46, 60, "<");
        buttonDel.setSize(16,16);
        layoutMain.addComponent(buttonDel);
        buttonDel.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                int length = textFieldInput.getText().length();
                int number = textFieldInput.getText().length() - 1;
                String store;

                if(length > 0){
                    StringBuilder back = new StringBuilder(textFieldInput.getText());
                    back.deleteCharAt(number);
                    store = back.toString();
                    textFieldInput.setText(store);
                }
            }
        });


        buttonC = new Button(5, 60, "C");
        buttonC.setSize(36,16);
        layoutMain.addComponent(buttonC);
        buttonC.setClickListener(new ClickListener() {
            @Override
            public void onClick(int mouseX, int mouseY, int mouseButton) {
                textFieldInput.setText("");
            }
        });




        setCurrentLayout(layoutMain);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }


}
