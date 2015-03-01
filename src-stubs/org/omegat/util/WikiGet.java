/*
 * This code is only a stub.
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

package org.omegat.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

public class WikiGet {

    public static String get(String address, Map<String, String> params, Map<String, String> additionalHeaders) throws IOException {
        return null;
    }

    /**
     * HTTP response error storage.
     */
    public static class ResponseError extends IOException {

        public final int code;
        public final String message;

        public ResponseError(HttpURLConnection conn) throws IOException {
            super(conn.getResponseCode() + ": " + conn.getResponseMessage());
            code = conn.getResponseCode();
            message = conn.getResponseMessage();
        }
    }
}