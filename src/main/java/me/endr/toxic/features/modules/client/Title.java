package me.endr.toxic.features.modules.client;

import me.endr.toxic.features.modules.Module;
import me.endr.toxic.features.setting.Setting;


public class Title extends Module {
    private static Title INSTANCE = new Title();
    public Setting<String> displayTitle = this.register(new Setting("Title", "toxic.club v0.2"));
    public Setting<Boolean> version = this.register(new Setting("Version", true));

    public Title(){
        super("TitleModifier", "Sets the title of your game", Category.CLIENT, true, false, false);
//        this.setInstance(); >> toxic.club v0.2
    }

    public static Title getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Title();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        org.lwjgl.opengl.Display.setTitle(this.displayTitle.getValue());

    }



}