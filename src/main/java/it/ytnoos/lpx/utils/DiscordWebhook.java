/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.utils;

import it.ytnoos.lpx.qn;
import it.ytnoos.lpx.qq;
import it.ytnoos.lpx.we;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;

public class DiscordWebhook {
    private final String L;
    private final List<EmbedObject> embeds = new ArrayList<EmbedObject>();
    private String O;
    private String i;
    private String U;
    private boolean tts;
    private String h;

    public DiscordWebhook(String string) {
        this.L = string;
    }

    public void uf(String string) {
        this.O = string;
    }

    public void u3(String string) {
        this.i = string;
    }

    public void ub(String string) {
        this.U = string;
    }

    public void setTts(boolean bl) {
        this.tts = bl;
    }

    public void p2(Object object) {
        this.embeds.add((EmbedObject)object);
    }

    public void execute() throws IOException {
        Serializable serializable;
        if (this.O == null && this.embeds.isEmpty()) {
            throw new IllegalArgumentException("Set content or add at least one EmbedObject");
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.lv("content", this.O);
        jSONObject.lv("username", this.i);
        jSONObject.lv("avatar_url", this.U);
        jSONObject.lv("tts", this.tts);
        if (!this.embeds.isEmpty()) {
            serializable = new ArrayList();
            for (EmbedObject object2 : this.embeds) {
                Object object;
                Object object3;
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.lv("title", object2.QK());
                jSONObject2.lv("description", object2.QA());
                jSONObject2.lv("url", object2.Qf());
                jSONObject2.lv("timestamp", object2.Qu());
                if (object2.getColor() != null) {
                    object3 = object2.getColor();
                    int n = ((Color)object3).getRed();
                    n = (n << 8) + ((Color)object3).getGreen();
                    n = (n << 8) + ((Color)object3).getBlue();
                    jSONObject2.lv("color", n);
                }
                object3 = object2.getFooter();
                qn n = object2.Qw();
                qq qq2 = object2.QP();
                EmbedObject.Author author = object2.getAuthor();
                List list = object2.getFields();
                if (object3 != null) {
                    object = new JSONObject();
                    ((JSONObject)object).lv("text", ((EmbedObject.Footer)object3).eP());
                    ((JSONObject)object).lv("icon_url", ((EmbedObject.Footer)object3).eo());
                    jSONObject2.lv("footer", object);
                }
                if (n != null) {
                    object = new JSONObject();
                    ((JSONObject)object).lv("url", n.TX());
                    jSONObject2.lv("image", object);
                }
                if (qq2 != null) {
                    object = new JSONObject();
                    ((JSONObject)object).lv("url", qq2.rY());
                    jSONObject2.lv("thumbnail", object);
                }
                if (author != null) {
                    object = new JSONObject();
                    ((JSONObject)object).lv("name", author.aD());
                    ((JSONObject)object).lv("url", author.ad());
                    ((JSONObject)object).lv("icon_url", author.av());
                    jSONObject2.lv("author", object);
                }
                object = new ArrayList();
                for (EmbedObject.Field field : list) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.lv("name", field.eF());
                    jSONObject3.lv("value", field.ek());
                    jSONObject3.lv("inline", field.isInline());
                    object.add(jSONObject3);
                }
                jSONObject2.lv("fields", object.toArray());
                serializable.add(jSONObject2);
            }
            jSONObject.lv("embeds", serializable.toArray());
        }
        serializable = new URL(this.L);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection)((URL)serializable).openConnection();
        httpsURLConnection.addRequestProperty("Content-Type", "application/json");
        httpsURLConnection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setRequestMethod("POST");
        OutputStream outputStream = httpsURLConnection.getOutputStream();
        this.h = jSONObject.toString();
        outputStream.write(jSONObject.toString().getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        httpsURLConnection.getInputStream().close();
        httpsURLConnection.disconnect();
    }

    public String getJson() {
        return this.h;
    }

    static class JSONObject {
        private final HashMap<String, Object> map = new HashMap();

        private JSONObject() {
        }

        void lv(Object object, Object object2) {
            if (object2 != null) {
                this.map.put((String)object, object2);
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            Set<Map.Entry<String, Object>> set = this.map.entrySet();
            stringBuilder.append("{");
            int n = 0;
            for (Map.Entry<String, Object> entry : set) {
                Object object = entry.getValue();
                stringBuilder.append(this.quote(entry.getKey())).append(":");
                if (object instanceof String) {
                    stringBuilder.append(this.quote(String.valueOf(object)));
                } else if (object instanceof Integer) {
                    stringBuilder.append(Integer.valueOf(String.valueOf(object)));
                } else if (object instanceof Boolean) {
                    stringBuilder.append(object);
                } else if (object instanceof JSONObject) {
                    stringBuilder.append(object);
                } else if (object.getClass().isArray()) {
                    stringBuilder.append("[");
                    int n2 = Array.getLength(object);
                    for (int i = 0; i < n2; ++i) {
                        stringBuilder.append(Array.get(object, i).toString()).append(i != n2 - 1 ? "," : "");
                    }
                    stringBuilder.append("]");
                }
                stringBuilder.append(++n == set.size() ? "}" : ",");
            }
            return stringBuilder.toString();
        }

        private String quote(String string) {
            return "\"" + string + "\"";
        }

        private static we a(we we2) {
            return we2;
        }
    }

    public static class EmbedObject {
        private final List<Field> fields = new ArrayList<Field>();
        private String u;
        private String I;
        private String g;
        private Color color;
        private Footer footer;
        private String w;
        private qq l;
        private qn E;
        private Author author;

        public String QK() {
            return this.u;
        }

        public EmbedObject Sp(String string) {
            this.u = string;
            return this;
        }

        public String QA() {
            return this.I;
        }

        public EmbedObject Si(String string) {
            this.I = string.replace("\n", "\\n").replace("\\_", "_");
            return this;
        }

        public String Qf() {
            return this.g;
        }

        public EmbedObject ST(String string) {
            this.g = string;
            return this;
        }

        public Color getColor() {
            return this.color;
        }

        public EmbedObject hH(Color color) {
            this.color = color;
            return this;
        }

        public Footer getFooter() {
            return this.footer;
        }

        public String Qu() {
            return this.w;
        }

        public EmbedObject TS(TemporalAccessor temporalAccessor) {
            this.w = temporalAccessor.toString();
            return this;
        }

        public qq QP() {
            return this.l;
        }

        public EmbedObject SE(String string) {
            this.l = new qq(string);
            return this;
        }

        public qn Qw() {
            return this.E;
        }

        public EmbedObject Su(String string) {
            this.E = new qn(string);
            return this;
        }

        public Author getAuthor() {
            return this.author;
        }

        public List getFields() {
            return this.fields;
        }

        public EmbedObject Jb(String string, String string2) {
            this.footer = new Footer(string, string2);
            return this;
        }

        public EmbedObject gU(String string, String string2, String string3) {
            this.author = new Author(string, string2, string3);
            return this;
        }

        public EmbedObject DG(String string, String string2, boolean bl) {
            this.fields.add(new Field(string, string2, bl));
            return this;
        }

        static class Author {
            private final String L;
            private final String r;
            private final String s;

            private Author(String string, String string2, String string3) {
                this.L = string;
                this.r = string2;
                this.s = string3;
            }

            private String aD() {
                return this.L;
            }

            private String ad() {
                return this.r;
            }

            private String av() {
                return this.s;
            }
        }

        static class Field {
            private final String Q;
            private final String Z;
            private final boolean inline;

            private Field(String string, String string2, boolean bl) {
                this.Q = string;
                this.Z = string2;
                this.inline = bl;
            }

            private String eF() {
                return this.Q;
            }

            private String ek() {
                return this.Z;
            }

            private boolean isInline() {
                return this.inline;
            }
        }

        static class Footer {
            private final String r;
            private final String k;

            private Footer(String string, String string2) {
                this.r = string;
                this.k = string2;
            }

            private String eP() {
                return this.r;
            }

            private String eo() {
                return this.k;
            }
        }
    }
}

