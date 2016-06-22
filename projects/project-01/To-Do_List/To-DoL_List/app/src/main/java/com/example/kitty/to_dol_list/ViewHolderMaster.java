package com.example.kitty.to_dol_list;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kitty on 6/22/16.
 */
public class ViewHolderMaster {

    TextView listName;
    Button removeListButton;

    public ViewHolderMaster(View listLayout){
        this.listName = (TextView) listLayout.findViewById(R.id.list_name);
        this.removeListButton = (Button) listLayout.findViewById(R.id.remove_list_button);
    }

}
