package br.com.prodap.taurusmobile.OpenFile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.prodap.taurusmobile.R;

/**
 * Created by Prodap on 18/10/2016.
 */

public class ViewHolder extends RecyclerView.ViewHolder
{
    public final View mView;
    public final ImageView mIconImageView;
    public final TextView mPathTextView;
    public FileItem mFileItem;

    public ViewHolder(View itemView)
    {
        super(itemView);
        mView = itemView;
        mIconImageView = (ImageView)itemView.findViewById(R.id.open_file_dialog_item_image_view);
        mPathTextView = (TextView)itemView.findViewById(R.id.open_file_dialog_item_text_view);
    }
}
