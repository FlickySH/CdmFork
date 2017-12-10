package com.mrcrayfish.device.programs.system;


import com.mrcrayfish.device.core.io.FileSystem;
import com.mrcrayfish.device.programs.system.component.Explorer;
import net.minecraft.nbt.NBTTagCompound;

public class ApplicationExplorer extends SystemApplication
{
	private Explorer browser;
	
	public ApplicationExplorer()
	{
		this.setDefaultWidth(295);
		this.setDefaultHeight(148);
	}

	@Override
	public void init() 
	{
		browser = new Explorer(0, 0, this, Explorer.Mode.FULL);
		browser.openFolder(FileSystem.DIR_HOME);
		this.addComponent(browser);
	}

	@Override
	public void load(NBTTagCompound tagCompound)
	{

	}

	@Override
	public void save(NBTTagCompound tagCompound) 
	{
		
	}

}
