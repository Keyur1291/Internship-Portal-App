package com.android.internshipportal;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class touchHelper3 extends ItemTouchHelper.SimpleCallback {

    companyAdapter adapter;
    public touchHelper3(companyAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(adapter.context, R.style.ThemeOverlay_App_MaterialAlertDialog);
            dialogBuilder.setTitle("Delete Profile");
            dialogBuilder.setIcon(R.drawable.ic_baseline_edit_24);
            dialogBuilder.setMessage("Are you sure want to edit this Company's details?");
            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.updateDatac(position);
                    adapter.notifyDataSetChanged();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialogBuilder.show();
        } else {
            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(adapter.context, R.style.ThemeOverlay_App_MaterialAlertDialog);
            dialogBuilder.setTitle("Delete Profile");
            dialogBuilder.setIcon(R.drawable.ic_baseline_delete_forever_24);
            dialogBuilder.setMessage("Are you sure want to delete this profile? this action can't be reverted.");
            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.deleteData(position);
                    adapter.notifyDataSetChanged();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeRightBackgroundColor(Color.RED)
                .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_forever_24)
                .addSwipeLeftBackgroundColor(Color.GREEN)
                .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24)
                .setSwipeLeftActionIconTint(Color.WHITE)
                .setSwipeRightActionIconTint(Color.WHITE)
                .create()
                .decorate();

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
