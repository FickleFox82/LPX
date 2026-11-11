/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.check.checks.book;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;
import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.common.check.PlayerCheck;
import it.ytnoos.lpx.we;

public class BookA
extends PlayerCheck<WrapperPlayClientEditBook> {
    private int maxSize;

    public BookA(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public void loadOptions() {
        this.maxSize = this.getInt("max", 15);
    }

    protected CheckResult L3(Object object, WrapperPlayClientEditBook wrapperPlayClientEditBook) {
        int n;
        String string = wrapperPlayClientEditBook.getTitle();
        if (string != null && (n = string.length()) > this.maxSize) {
            this.Fn(wrapperPlayClientEditBook, new FlagInfo[]{FlagInfo.of("title", string), FlagInfo.of("length", n), FlagInfo.of("max", this.maxSize)});
            return CheckResult.DENY;
        }
        return CheckResult.ALLOW;
    }

    private static we a(we we2) {
        return we2;
    }
}

