package me.endr.toxic.manager;

import me.endr.toxic.util.hwid.DisplayUtil;
import me.endr.toxic.util.hwid.NoStackTraceThrowable;
import me.endr.toxic.util.hwid.SystemUtil;
import me.endr.toxic.util.hwid.URLReader;

import java.util.ArrayList;
import java.util.List;

public class HWIDManager {

    /**
     * Your pastebin URL goes inside the empty string below.
     * It should be a raw pastebin link, for example: pastebin.com/raw/pasteid
     */

    public static final String pastebinURL = "https://pastebin.com/raw/R1kveEer";

    public static List<String> hwids = new ArrayList<>();

    public static void hwidCheck() {
        hwids = URLReader.readURL();
        boolean isHwidPresent = hwids.contains(SystemUtil.getSystemInfo());
        if (!isHwidPresent) {
            DisplayUtil.Display();
            throw new NoStackTraceThrowable("");
        }
    }
}
