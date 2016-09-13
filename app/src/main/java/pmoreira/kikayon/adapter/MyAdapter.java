package pmoreira.kikayon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import pmoreira.kikayon.R;
import pmoreira.kikayon.model.Information;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private LayoutInflater inflater;

    private List<Information> data = Collections.emptyList();

    public MyAdapter(final Context context, final List<Information> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = inflater.inflate(R.layout.row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Information info = data.get(position);

        holder.setText(info.getDescription());
        holder.setIcon(info.getImageId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView rowIcon;
        private TextView rowtext;

        public MyViewHolder(final View view) {
            super(view);

            rowIcon = (ImageView) view.findViewById(R.id.row_icon);
            rowtext = (TextView) view.findViewById(R.id.row_text);
        }

        public void setText(final String text) {
            rowtext.setText(text);
        }

        public void setIcon(final int iconId) {
            rowIcon.setImageResource(iconId);
        }
    }

}
