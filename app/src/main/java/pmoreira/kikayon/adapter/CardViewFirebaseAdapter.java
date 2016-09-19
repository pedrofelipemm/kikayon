package pmoreira.kikayon.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerViewAdapter;

import pmoreira.kikayon.R;
import pmoreira.kikayon.model.RecordInformation;

public class CardViewFirebaseAdapter extends FirebaseRecyclerViewAdapter<RecordInformation, CardViewFirebaseAdapter.ViewHolder> {

    public CardViewFirebaseAdapter(final Class modelClass, final int modelLayout, final Class viewHolderClass, final Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    public CardViewFirebaseAdapter(final Class modelClass, final int modelLayout, final Class viewHolderClass, final Firebase ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CardViewFirebaseAdapter.ViewHolder((CardView) inflater.inflate(R.layout.card_view, parent, false));
    }

    @Override
    protected void populateViewHolder(final ViewHolder viewHolder, final RecordInformation model, final int position) {

        ((TextView)viewHolder.cardView.findViewById(R.id.description)).setText(model.getDescription());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        public ViewHolder(final CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

    }
}
