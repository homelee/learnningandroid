package me.lianzhao.myapplication;

import net.chilicat.m3u8.Element;
import net.chilicat.m3u8.ParseException;
import net.chilicat.m3u8.Playlist;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class HlsHelper {

    public static String getFirstPlaybackUri(String hlsUri) throws IOException, ParseException {

        URL url = new URL(hlsUri);
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        Playlist playlist = Playlist.parse(inputStream);
        List<Element> elements = playlist.getElements();
        Element element = elements.get(0);
        URI elementURI = element.getURI();
        if (element.isMedia()) {
            return getAbsoluteUri(hlsUri, elementURI);
        }
        return getFirstPlaybackUri(getAbsoluteUri(hlsUri, elementURI));
    }

    private static String getAbsoluteUri(String hlsUri, URI uri) {
        String result;
        if (uri.isAbsolute()) {
            result = uri.toString();
        } else {
            String substring = hlsUri.substring(0, hlsUri.lastIndexOf('/'));
            result = substring + "/" + uri.toString();
        }
        return result;
    }
}
