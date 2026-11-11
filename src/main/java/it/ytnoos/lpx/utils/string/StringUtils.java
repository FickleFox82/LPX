/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.utils.string;

import it.ytnoos.lpx.utils.string.CharSequenceUtils;
import it.ytnoos.lpx.we;

public class StringUtils {
    private StringUtils() {
    }

    public static int length(CharSequence charSequence) {
        return charSequence == null ? 0 : charSequence.length();
    }

    public static boolean J0(CharSequence charSequence, CharSequence charSequence2) {
        return StringUtils.startsWith(charSequence, charSequence2, true);
    }

    public static boolean JA(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence != null && charSequence2 != null) {
            int n = charSequence2.length();
            int n2 = charSequence.length() - n;
            for (int i = 0; i <= n2; ++i) {
                if (!CharSequenceUtils.regionMatches(charSequence, true, i, charSequence2, 0, n)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    public static int countMatches(CharSequence charSequence, CharSequence charSequence2) {
        if (!StringUtils.isEmpty(charSequence) && !StringUtils.isEmpty(charSequence2)) {
            int n = 0;
            int n2 = 0;
            while ((n2 = CharSequenceUtils.indexOf(charSequence, charSequence2, n2)) != -1) {
                ++n;
                n2 += charSequence2.length();
            }
            return n;
        }
        return 0;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    private static boolean startsWith(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        if (charSequence != null && charSequence2 != null) {
            int n = charSequence2.length();
            return n > charSequence.length() ? false : CharSequenceUtils.regionMatches(charSequence, bl, 0, charSequence2, 0, n);
        }
        return charSequence == charSequence2;
    }

    private static we a(we we2) {
        return we2;
    }
}

