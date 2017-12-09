package com.mrcrayfish.device.programs.system;

import com.mrcrayfish.device.api.app.Component;
import com.mrcrayfish.device.api.app.Dialog;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.CheckBox;
import com.mrcrayfish.device.api.app.component.ComboBox;

import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.app.listener.ClickListener;
import com.mrcrayfish.device.api.app.renderer.ItemRenderer;
import com.mrcrayfish.device.core.Laptop;
import com.mrcrayfish.device.core.Settings;
import com.mrcrayfish.device.programs.system.component.Palette;
import com.mrcrayfish.device.programs.system.object.ColourScheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;


import java.awt.*;

public class ApplicationSettings extends SystemApplication
{
	//layout main
	private Button goback;
	private Layout layoutMain;
	private Button gotoPersonalize;

	//layout personalize
	private Layout layoutPersonalize;
	private Button gotoColourSchemes;
	private Button gobackMain;

	//layout color scheme
	private Layout layoutColourScheme;
	private Button gobackPersonalize;
	private Button gotoColoursBackground;
	private Button gotoColoursSecondaryBackground;

	//layout colours
	private Layout layoutColours;
	private Button gobackColourScheme;

	//layout edit colours
	private Button buttonColoursHex;
	private Label labelColours;
	private Button buttonSaveColours;

	public static int layoutIndex;




	public ApplicationSettings()
	{

	}

	@Override
	public void init()
	{
		//layout main

		layoutMain = new Layout(200, 150);
		layoutMain.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
		{
			Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
			Gui.drawRect(x, y + 20, x + width, y + 21, Color.DARK_GRAY.getRGB());
		});

		gotoPersonalize = new Button(5,25,"Personalize", Icons.USER);
		gotoPersonalize.setSize(layoutMain.width-10,20);
		gotoPersonalize.setClickListener(new ClickListener() {
			@Override
			public void onClick(int mouseX, int mouseY, int mouseButton) {
				setCurrentLayout(layoutPersonalize);
			}
		});

		goback = new Button(5, 2, Icons.ARROW_LEFT);
		goback.setToolTip("Go Back", "Go back to the previous window");
		goback.setEnabled(false);
		layoutMain.addComponent(goback);
		layoutMain.addComponent(gotoPersonalize);

		//layout personalize

		layoutPersonalize = new Layout(200,150);
		layoutPersonalize.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
		{
			Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
			Gui.drawRect(x, y + 20, x + width, y + 21, Color.DARK_GRAY.getRGB());
		});

		gobackMain = new Button(5,2, Icons.ARROW_LEFT);
		gobackMain.setClickListener(new ClickListener() {
			@Override
			public void onClick(int mouseX, int mouseY, int mouseButton) {
				setCurrentLayout(layoutMain);
			}
		});

		gotoColourSchemes = new Button(5,25,"Color Scheme", Icons.EDIT);
		gotoColourSchemes.setSize(layoutPersonalize.width-10,20);
		gotoColourSchemes.setClickListener(new ClickListener() {
			@Override
			public void onClick(int mouseX, int mouseY, int mouseButton) {
				setCurrentLayout(layoutColourScheme);
			}
		});

		layoutPersonalize.addComponent(gotoColourSchemes);
		layoutPersonalize.addComponent(gobackMain);


		//layout color scheme
		layoutColourScheme = new Layout(200,150);
		layoutColourScheme.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
		{
			Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
			Gui.drawRect(x, y + 20, x + width, y + 21, Color.DARK_GRAY.getRGB());
		});

		gobackPersonalize = new Button(5,2,Icons.ARROW_LEFT);
		gobackPersonalize.setClickListener(new ClickListener() {
			@Override
			public void onClick(int mouseX, int mouseY, int mouseButton) {
				setCurrentLayout(layoutPersonalize);
			}
		});
		gotoColoursBackground = new Button(5,25,"Background Colour");
		gotoColoursBackground.setSize(layoutColourScheme.width-10,20);
		gotoColoursBackground.setClickListener(new ClickListener() {
			@Override
			public void onClick(int mouseX, int mouseY, int mouseButton) {
				layoutIndex=0;
				setCurrentLayout(layoutColours);
			}
		});
		gotoColoursSecondaryBackground = new Button(5,50,"Secondary Background Colour");
		gotoColoursSecondaryBackground.setSize(layoutColourScheme.width-10,20);
		gotoColoursSecondaryBackground.setClickListener(new ClickListener() {
			@Override
			public void onClick(int mouseX, int mouseY, int mouseButton) {
				layoutIndex=1;
				setCurrentLayout(layoutColours);
			}
		});
		layoutColourScheme.addComponent(gobackPersonalize);
		layoutColourScheme.addComponent(gotoColoursBackground);
		layoutColourScheme.addComponent(gotoColoursSecondaryBackground);


		//layout colours
		layoutColours = new Layout(100,125);
		layoutColours.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
		{
			Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
			Gui.drawRect(x, y + 20, x + width, y + 21, Color.DARK_GRAY.getRGB());
		});
		gobackColourScheme = new Button(5,2,Icons.ARROW_LEFT);
		gobackColourScheme.setClickListener(new ClickListener() {
			@Override
			public void onClick(int mouseX, int mouseY, int mouseButton) {
				setCurrentLayout(layoutColourScheme);
			}

		});
		layoutColours.addComponent(gobackColourScheme);

		buttonSaveColours = new Button(75,100,Icons.CHECK);
		buttonSaveColours.setEnabled(false);
		layoutColours.addComponent(buttonSaveColours);

		ComboBox.Custom<Integer> colourPicker= new ComboBox.Custom<>(5, 25, 62, 62, 90);
		if(layoutIndex == 0){
			colourPicker.setValue(Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
		}else if(layoutIndex==1){
			colourPicker.setValue(Laptop.getSystem().getSettings().getColourScheme().getBackgroundSecondaryColour());
		}
		colourPicker.setItemRenderer(new ItemRenderer<Integer>()
		{
			@Override
			public void render(Integer integer, Gui gui, Minecraft mc, int x, int y, int width, int height)
			{
				if(integer != null)
				{
					Gui.drawRect(x, y, x + width, y + height, integer);
				}
			}
		});
		colourPicker.setChangeListener((oldValue, newValue) ->
		{
			buttonSaveColours.setEnabled(true);
		});

		buttonSaveColours.setClickListener(new ClickListener() {
			@Override
			public void onClick(int mouseX, int mouseY, int mouseButton) {
				ColourScheme scheme = Laptop.getSystem().getSettings().getColourScheme();
				if (layoutIndex == 0) {
					scheme.setBackgroundColour(colourPicker.getValue());
				} else if (layoutIndex == 1) {
					scheme.setBackgroundSecondaryColour(colourPicker.getValue());
				}
				buttonSaveColours.setEnabled(false);
			}
		});
		Layout layout = colourPicker.getLayout();

		Palette palette = new Palette(5, 5, colourPicker);

		layout.addComponent(palette);
		layoutColours.addComponent(colourPicker);

		layoutColours.addComponent(labelColours);
		buttonColoursHex = new Button(5,100,"Hex");
		buttonColoursHex.setSize(60,14);
		layoutColours.addComponent(buttonColoursHex);





		/*//secondary background
		labelBackgroundSecondaryColor = new Label("Secondary Background:",5,45);
		layoutColorScheme.addComponent(labelBackgroundSecondaryColor);
		ComboBox.Custom<Integer> colourPickerBackgroundSecondary = new ComboBox.Custom<>(150, 42, 50, 62, 90);
		colourPickerBackgroundSecondary.setValue(Laptop.getSystem().getSettings().getColourScheme().backgroundSecondaryColour);
		colourPickerBackgroundSecondary.setItemRenderer(new ItemRenderer<Integer>()
		{
			@Override
			public void render(Integer integer, Gui gui, Minecraft mc, int x, int y, int width, int height)
			{
				if(integer != null)
				{
					Gui.drawRect(x, y, x + width, y + height, integer);
				}
			}
		});
		colourPickerBackgroundSecondary.setChangeListener((oldValue, newValue) ->
		{
			buttonSaveColors.setEnabled(true);
		});

		Layout layout1 = colourPickerBackgroundSecondary.getLayout();

		Palette palette1 = new Palette(5, 5, colourPickerBackgroundSecondary);

		layout1.addComponent(palette1);
		layoutColorScheme.addComponent(colourPickerBackgroundSecondary);









		layoutColorScheme.addComponent(buttonSaveColors);
		buttonSaveColors.setClickListener((c, mouseButton) ->
		{
			if(mouseButton == 0)
			{
				ColourScheme colourScheme = Laptop.getSystem().getSettings().getColourScheme();
				colourScheme.setBackgroundColour(colourPickerBackground.getValue());
				colourScheme.setBackgroundSecondaryColour(colourPickerBackgroundSecondary.getValue());
				buttonSaveColors.setEnabled(false);
			}
		});*/
		setCurrentLayout(layoutMain);




	}

	@Override
	public void load(NBTTagCompound tagCompound)
	{

	}

	@Override
	public void save(NBTTagCompound tagCompound) {

	}

}
