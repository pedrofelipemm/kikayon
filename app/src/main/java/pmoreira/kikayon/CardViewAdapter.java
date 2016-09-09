package pmoreira.kikayon;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pmoreira.kikayon.model.RecordInformation;

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

        Information current = data.get(position);

        TextView description = (TextView) cardView.findViewById(R.id.description);
        description.setText(current.getDescription());

        ImageView image = (ImageView) cardView.findViewById(R.id.image);
        image.setImageDrawable(cardView.getResources().getDrawable(data.get(position).getImageId()));
        image.setContentDescription(current.getDescription());

        if(current instanceof RecordInformation) {
            Date date = ((RecordInformation) current).getDate();
            TextView dateTextView = (TextView) cardView.findViewById(R.id.row_date);
            dateTextView.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        }

        LinearLayout layout = (LinearLayout) cardView.findViewById(R.id.card_body);
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
