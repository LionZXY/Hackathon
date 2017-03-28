package ru.skafcats.hackathon.adapters;

import android.Manifest;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.squareup.picasso.Picasso;

import java.io.File;

import ru.skafcats.hackathon.MailReportInputActivity;
import ru.skafcats.hackathon.R;
import ru.skafcats.hackathon.helpers.FileHelper;
import ru.skafcats.hackathon.helpers.PermissionHelper;
import ru.skafcats.hackathon.models.MailReport;

/**
 * Created by Nikita Kulikov on 29.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.ViewHolder> {


    private MailReport mailReport = null;
    private MailReportInputActivity activity = null;
    private Context mContext;

    public AttachmentAdapter(Context context, MailReportInputActivity activity, MailReport mailReport) {
        this.mContext = context;
        this.mailReport = mailReport;
        this.activity = activity;
    }

    @Override
    public AttachmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View attachmentView = inflater.inflate(R.layout.item_attachment, parent, false);

        return new ViewHolder(attachmentView);
    }

    @Override
    public void onBindViewHolder(final AttachmentAdapter.ViewHolder holder, int position) {
        if (position < mailReport.getAttachment().size()) {
            File file = mailReport.getAttachment().get(position);
            if (FileHelper.isImage(file)) {
                holder.imageAdd.setVisibility(View.GONE);
                holder.attachmentFile.setVisibility(View.GONE);
                Picasso.with(mContext).load(file).into(holder.image);
            } else {
                holder.imageAdd.setVisibility(View.GONE);
                holder.attachmentFile.setVisibility(View.VISIBLE);
            }
        } else {
            holder.imageAdd.setVisibility(View.VISIBLE);
            holder.attachmentFile.setVisibility(View.GONE);
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PermissionHelper.checkPermissionAndRequest(activity, Manifest.permission.READ_EXTERNAL_STORAGE))
                        new MaterialFilePicker()
                                .withActivity(activity)
                                .withRequestCode(MailReportInputActivity.FILE_SELECT)
                                .withHiddenFiles(true)
                                .start();
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return mailReport.getAttachment().size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View rootView = null;
        ImageView image = null;
        ImageView imageAdd = null;
        ImageView attachmentFile = null;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            image = (ImageView) itemView.findViewById(R.id.attachment_image);
            imageAdd = (ImageView) itemView.findViewById(R.id.attachment_add);
            attachmentFile = (ImageView) itemView.findViewById(R.id.attachment_file);
        }
    }

}