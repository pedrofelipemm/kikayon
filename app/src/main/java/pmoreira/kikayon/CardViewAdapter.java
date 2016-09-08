package pmoreira.kikayon;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private List<Information> data;

    public CardViewAdapter(final List<Information> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder((CardView) inflater.inflate(R.layout.card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;

        TextView description = (TextView) cardView.findViewById(R.id.description);
        description.setText(data.get(position).getDescription());

        ImageView image = (ImageView) cardView.findViewById(R.id.image);
        image.setImageDrawable(cardView.getResources().getDrawable(data.get(position).getImageId()));
        image.setContentDescription(data.get(position).getDescription());
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
}
