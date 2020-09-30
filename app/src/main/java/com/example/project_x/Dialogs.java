package com.example.project_x;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class Dialogs extends DialogFragment {
    String webaddress;
    String name;

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = this.getArguments();
        assert args != null;
        webaddress = args.getString("key");
        name = args.getString("name");
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.olimpiad)
                .setCancelable(true)
                .setNeutralButton(R.string.dialog_text, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goToUrl(webaddress);
                    }
                })
                .setPositiveButton(R.string.registrate, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(), Parsing.class);
                        intent.putExtra("object", webaddress);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}