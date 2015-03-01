/*
 *
 * Copyright (C) 2015 David Meikle <david@logicalspark.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.logicalspark.machinetranslators;

import groovy.json.JsonSlurper;
import org.omegat.util.Language;
import org.omegat.util.WikiGet;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lingo24 Premium Machine Translation Plugin for OmegaT.
 */
public class Lingo24Translate extends org.omegat.core.machinetranslators.BaseTranslate {

    protected static final String LINGO_URL = "https://api.lingo24.com/mt/v1/translate";
    protected static final Pattern RE_UNICODE = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");
    protected static final Pattern RE_HTML  = Pattern.compile("&#([0-9]+);");

    @Override
    protected String getPreferenceName() {
        return "allow_lingo24_translate";
    }

    public String getName() {
        return "Lingo24 Premium MT";
    }

    @Override
    protected String translate(Language sLang, Language tLang, String text) throws Exception {
        String trText = text.length() > 5000 ? text.substring(0, 4997) + "..." : text;

        String prev = getFromCache(sLang, tLang, trText);
        if (prev != null) {
            return prev;
        }

        String lingo24Key = System.getProperty("lingo24.api.key");
        if (lingo24Key == null) {
            return "Lingo24 Premium MT API Key Not Found";
        }

        Map<String, String> params = new TreeMap<String, String>();

        params.put("user_key", lingo24Key);
        params.put("source", sLang.getLanguageCode().toLowerCase());
        params.put("target", tLang.getLanguageCode().toLowerCase());
        params.put("q", trText);

        String v;
        try {
            v = WikiGet.get(LINGO_URL, params, null);
        } catch (Throwable e) {
            return e.getLocalizedMessage();
        }

        while (true) {
            Matcher m = RE_UNICODE.matcher(v);
            if (!m.find()) {
                break;
            }
            String g = m.group();
            char c = (char) Integer.parseInt(m.group(1), 16);
            v = v.replace(g, Character.toString(c));
        }
        v = v.replace("&quot;", "&#34;");
        v = v.replace("&nbsp;", "&#160;");
        v = v.replace("&amp;", "&#38;");
        while (true) {
            Matcher m = RE_HTML.matcher(v);
            if (!m.find()) {
                break;
            }
            String g = m.group();
            char c = (char) Integer.parseInt(m.group(1));
            v = v.replace(g, Character.toString(c));
        }

        JsonSlurper slurper = new JsonSlurper();
        Map map = (Map) slurper.parseText(v);
        String tr = "";

        if (map.containsKey("translation")) {
            tr = (String) map.get("translation");
        }
        tr = cleanSpacesAroundTags(tr, text);

        putToCache(sLang, tLang, trText, tr);
        return tr;
    }
}
