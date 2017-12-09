package com.mrcrayfish.device.api.app.listener;

import com.mrcrayfish.device.api.app.Component;

/**
 * The click listener interface. Used for handling clicks
 * on components.
 * 
 * @author MrCrayfish
 */
public interface ClickListener 
{

	void onClick(int mouseX, int mouseY,int  mouseButton);
}
