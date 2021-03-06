package pmoreira.kikayon.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pmoreira.kikayon.R;
import pmoreira.kikayon.model.Information;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private List<Information> data;
    private onClickListener clickListener;

    public CardViewAdapter(final List<Information> data, final onClickListener clickListener) {
        this.data = data;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder((CardView) inflater.inflate(R.layout.card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Information current = data.get(position);

        final CardView cardView = holder.cardView;
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                clickListener.onClick(position);
            }
        });

        TextView description = (TextView) cardView.findViewById(R.id.description);
        description.setText(current.getDescription());

        ImageView image = (ImageView) cardView.findViewById(R.id.image);
        image.setImageDrawable(cardView.getResources().getDrawable(current.getImageId()));
        image.setContentDescription(current.getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        public ViewHolder(final CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    public interface onClickListener {
        void onClick(final int id);
    }
}
