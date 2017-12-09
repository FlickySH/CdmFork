package com.mrcrayfish.device.programs;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Dialog;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.*;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.app.component.TextArea;
import com.mrcrayfish.device.api.app.component.TextField;
import com.mrcrayfish.device.api.app.renderer.ListItemRenderer;
import com.mrcrayfish.device.api.io.File;
import com.mrcrayfish.device.core.Laptop;
import com.mrcrayfish.device.core.io.FileSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

import java.awt.*;
import java.util.function.Predicate;

public class ApplicationNoteStash extends Application
{
    private static final Predicate<File> PREDICATE_FILE_NOTE = file -> !file.isFolder()
            && file.getData().hasKey("title", Constants.NBT.TAG_STRING)
            && file.getData().hasKey("content", Constants.NBT.TAG_STRING);

    private static final Color ITEM_BACKGROUND = new Color(170, 176, 194);
    private static final Color ITEM_SELECTED = new Color(200, 176, 174);
    private static final Color AUTHOR_TEXT = new Color(114, 120, 138);
    private static final Color APP_SCHEME = new Color(244, 169, 84);


    /* Main */
    private Layout layoutMain;
    private Button btnNew;
    private Button btnLoad;
    /* Add Note */
    private Layout layoutAddNote;
    public TextField title;
    public TextArea textArea;
    private Button btnSave;
    private Button btnCancel;
    private Button btnClear;

    /* Load Note */
    private Layout layoutLoadNote;
    private ItemList<Note> listNotes;
    private Button btnLoadSavedNote;
    private Button btnBrowseSavedNote;
    private Button btnDeleteSavedNote;
    private Button btnBackMain;

    /* Note Viewer */
    private Layout layoutViewNote;
    private Label noteTitle;
    private Text noteContent;
    private Button btnBack;

    public ApplicationNoteStash()
    {

    }

    @Override
    public void init()
    {
		/* Main */

        layoutMain = new Layout(140, 75);
        layoutMain.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Gui.drawRect(x, y, x + width, y + 20, APP_SCHEME.getRGB());
            Gui.drawRect(x, y + 20, x + width, y + 21, Color.DARK_GRAY.getRGB());
        });
        layoutMain.setInitListener(() ->
        {
                FileSystem.getApplicationFolder(this, (folder, success) ->
                {
                    folder.search(file -> file.isForApplication(this)).forEach(file ->
                    {
                        listNotes.addItem(Note.fromFile(file));

                    });
                });

        });



        btnNew = new Button(5, 25, "New", Icons.NEW_FILE);
        btnNew.setSize(layoutMain.width-10, 20);
        btnNew.setClickListener((mouseX, mouseY, mouseButton) -> setCurrentLayout(layoutAddNote));
        layoutMain.addComponent(btnNew);

        btnLoad = new Button(5, 50, "Load", Icons.LOAD);
        btnLoad.setSize(layoutMain.width-10, 20);
        btnLoad.setEnabled(true);
        btnLoad.setClickListener((mouseX, mouseY, mouseButton) -> {setCurrentLayout(layoutLoadNote);});
        layoutMain.addComponent(btnLoad);


		/* Add Note */

        layoutAddNote = new Layout(240, 140);
        layoutAddNote.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
            Gui.drawRect(x, y + 20, x + width, y + 21, Color.DARK_GRAY.getRGB());
        });
        title = new TextField(5, 45, layoutAddNote.width-10);
        layoutAddNote.addComponent(title);

        textArea = new TextArea(5, 65, layoutAddNote.width-10, 70);
        textArea.setFocused(true);
        textArea.setPadding(2);
        layoutAddNote.addComponent(textArea);

        btnSave = new Button(5, 25, Icons.SAVE);

        btnSave.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            NBTTagCompound data = new NBTTagCompound();
            data.setString("title", title.getText());
            data.setString("content", textArea.getText());

            Dialog.SaveFile dialog = new Dialog.SaveFile(ApplicationNoteStash.this, data);
            dialog.setFolder(getApplicationFolderPath());
            dialog.setResponseHandler((success, file) ->
            {
                title.clear();
                textArea.clear();
                setCurrentLayout(layoutMain);
                return true;
            });
            openDialog(dialog);
        });
        layoutAddNote.addComponent(btnSave);

        btnCancel = new Button(5, 2, Icons.ARROW_LEFT);
        layoutAddNote.addComponent(btnCancel);

        btnClear = new Button(25,25,Icons.CROSS);
        btnCancel.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            title.setText("");
            textArea.setText("");
            setCurrentLayout(layoutMain);
        });

        layoutAddNote.addComponent(btnClear);


        //Load Note
        layoutLoadNote = new Layout(180, 135);
        layoutLoadNote.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
            Gui.drawRect(x, y + 20, x + width, y + 21, Color.DARK_GRAY.getRGB());
        });
        listNotes = new ItemList<>(5, 25, 100, 5);
        listNotes.setListItemRenderer(new ListItemRenderer<Note>(20)
        {
            @Override
            public void render(Note note, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected) {
                Gui.drawRect(x, y, x + width, y + height, selected ? ITEM_SELECTED.getRGB() : ITEM_BACKGROUND.getRGB());
                mc.fontRenderer.drawString(note.getTitle(), x + 2, y + 2, Color.WHITE.getRGB(), false);

            }
        });
        listNotes.setItemClickListener((picture, index, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                btnLoadSavedNote.setEnabled(true);
                btnDeleteSavedNote.setEnabled(true);
            }
        });
        layoutLoadNote.addComponent(listNotes);


        btnLoadSavedNote = new Button(110, 25, "Load");
        btnLoadSavedNote.setSize(65, 20);
        btnLoadSavedNote.setEnabled(false);
        btnLoadSavedNote.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (listNotes.getSelectedIndex() != -1)
            {
                title.setText(listNotes.getSelectedItem().getTitle());
                textArea.setText(listNotes.getSelectedItem().getContent());
                setCurrentLayout(layoutViewNote);
            }
        });
        layoutLoadNote.addComponent(btnLoadSavedNote);

        btnBrowseSavedNote = new Button(110, 50, "Browse");
        btnBrowseSavedNote.setSize(65, 20);
        btnBrowseSavedNote.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            Dialog.OpenFile dialog = new Dialog.OpenFile(this);
            dialog.setResponseHandler((success, file) ->
            {
                if(file.isForApplication(this))
                {
                    Note note = Note.fromFile(file);
                    title.setText(note.getTitle());
                    textArea.setText(note.getContent());
                    return true;
                }
                else
                {
                    Dialog.Message dialog2 = new Dialog.Message("Invalid file for Note Stash");
                    openDialog(dialog2);
                }
                return false;
            });
            openDialog(dialog);
        });
        layoutLoadNote.addComponent(btnBrowseSavedNote);

        btnDeleteSavedNote = new Button(110, 75, "Delete");
        btnDeleteSavedNote.setSize(65, 20);
        btnDeleteSavedNote.setEnabled(false);

        btnDeleteSavedNote.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if(listNotes.getSelectedIndex() != -1)
            {
                Note note = listNotes.getSelectedItem();
                File file = note.getSource();
                if(file != null)
                {
                    file.delete((o, success) ->
                    {
                        if(success)
                        {
                            listNotes.removeItem(listNotes.getSelectedIndex());
                            btnDeleteSavedNote.setEnabled(false);
                            btnLoadSavedNote.setEnabled(false);
                        }
                        else
                        {
                            //TODO error dialog
                        }
                    });
                }
                else
                {
                    //TODO error dialog
                }
            }
        });
        layoutLoadNote.addComponent(btnDeleteSavedNote);

        btnBackMain = new Button(5, 2, Icons.ARROW_LEFT);
        btnBackMain.setClickListener((mouseX, mouseY, mouseButton) -> setCurrentLayout(layoutMain));
        layoutLoadNote.addComponent(btnBackMain);






		/* View Note */

        layoutViewNote = new Layout(240, 140);
        layoutViewNote.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Gui.drawRect(x, y, x + width, y + 20, Laptop.getSystem().getSettings().getColourScheme().getBackgroundColour());
            Gui.drawRect(x, y + 20, x + width, y + 21, Color.DARK_GRAY.getRGB());
        });

        noteTitle = new Label("", 5, 45);
        layoutViewNote.addComponent(noteTitle);

        noteContent = new Text("", 5, 65, 110);
        layoutViewNote.addComponent(noteContent);

        btnBack = new Button(5, 2, Icons.ARROW_LEFT);
        btnBack.setClickListener((mouseX, mouseY, mouseButton) -> setCurrentLayout(layoutMain));
        layoutViewNote.addComponent(btnBack);

        setCurrentLayout(layoutMain);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {}

    @Override
    public void save(NBTTagCompound tagCompound) {}



    @Override
    public boolean handleFile(File file)
    {
        if(!PREDICATE_FILE_NOTE.test(file))
            return false;

        NBTTagCompound data = file.getData();
        noteTitle.setText(data.getString("title"));
        noteContent.setText(data.getString("content"));
        setCurrentLayout(layoutViewNote);
        return true;
    }

    private static class Note
    {
        private File source;
        private String title;
        private String content;

        public Note(String title, String content)
        {
            this.title = title;
            this.content = content;
        }

        public File getSource()
        {
            return source;
        }

        public String getTitle()
        {
            return title;
        }

        public String getContent()
        {
            return content;
        }

        @Override
        public String toString()
        {
            return title;
        }

        public static Note fromFile(File file)
        {
            Note note = new Note(file.getData().getString("title"), file.getData().getString("content"));
            note.source = file;
            return note;
        }
    }
}
