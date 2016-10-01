package pmoreira.kikayon.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerViewAdapter;

import java.text.SimpleDateFormat;

import pmoreira.kikayon.R;
import pmoreira.kikayon.model.RecordInformation;
import pmoreira.kikayon.utils.DrawableUtils;

public class CardViewFirebaseAdapter extends FirebaseRecyclerViewAdapter<RecordInformation, CardViewFirebaseAdapter.ViewHolder> {

    private OnClickListener clickListener;

    public CardViewFirebaseAdapter(final Class modelClass, final int modelLayout, final Class viewHolderClass, final Firebase ref, final OnClickListener clickListener) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CardViewFirebaseAdapter.ViewHolder((CardView) inflater.inflate(R.layout.card_view, parent, false));
    }

    @Override
    protected void populateViewHolder(final ViewHolder viewHolder, final RecordInformation model, final int position) {

        CardView cardView = viewHolder.cardView;
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                clickListener.onClick(getRef(position).getKey());
            }
        });

        TextView description = ((TextView) cardView.findViewById(R.id.description));
        description.setText(model.getDescription());

        ImageView image = (ImageView) cardView.findViewById(R.id.image);
        image.setImageDrawable(DrawableUtils.getDrawable(cardView.getContext(), model.getImageId()));
        image.setContentDescription(model.getDescription());

        if (model instanceof RecordInformation) {
            String lastChange = new SimpleDateFormat("yyyy").format(model.getLastChangeLong());
            String login = model.getLogin();
            String authorDate = String.format("(%s, %s)", login, lastChange);

            TextView dateTextView = (TextView) cardView.findViewById(R.id.author_date);
            dateTextView.setText(authorDate);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        public ViewHolder(final CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    public interface OnClickListener {
        void onClick(final String id);
    }
}
