package edu.neu.numad22sp_jingtingxing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Patterns;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class LinkModel {
    String name;
    String url;

    public LinkModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getLinkName() {
        return name;
    }

    public String getLinkUrl() {
        return url;
    }

    public void onLinkUnitClicked(Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public boolean isValid() {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return Patterns.WEB_URL.matcher(url).matches();
    }
}
