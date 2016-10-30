package pmoreira.kikayon.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import pmoreira.kikayon.R;
import pmoreira.kikayon.model.RecordInformation;
import pmoreira.kikayon.utils.Constants;
import pmoreira.kikayon.utils.DrawableUtils;
import pmoreira.kikayon.utils.FirebaseUtils;
import pmoreira.kikayon.view.activity.LoginActivity;

public class CardViewFirebaseAdapter extends FirebaseRecyclerAdapter<RecordInformation, CardViewFirebaseAdapter.ViewHolder> {

    private OnClickListener clickListener;

    public CardViewFirebaseAdapter(final Class<RecordInformation> modelClass, final int modelLayout,
                                   final Class<ViewHolder> viewHolderClass, final Query ref,
                                   final OnClickListener clickListener) {
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

        final CardView cardView = viewHolder.cardView;
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                clickListener.onClick(getRef(position).getKey());
            }
        });

        TextView description = ((TextView) cardView.findViewById(R.id.description));
        description.setText(model.getDescription());

        final ImageView image = (ImageView) cardView.findViewById(R.id.image);
//        image.setImageDrawable(cardView.getContext(), android.R.drawable.);
//        image.setImageDrawable(DrawableUtils.getDrawable(cardView.getContext(), model.getImageId()));
        FirebaseUtils.getInstance().getReference(Constants.FIREBASE_LOCATION_IMAGES)
                .child(model.getLogin())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String url = dataSnapshot.getValue(String.class);
                        Picasso.with(cardView.getContext())
                                .load(LoginActivity.decodeUrl(url) != "" ? LoginActivity.decodeUrl(url) : "imageNotLoaded") // TODO:
                                .networkPolicy(NetworkPolicy.OFFLINE)
                                .transform(new CropCircleTransformation())
                                .error(R.drawable.ic_account_circle_white_48dp)
                                .into(image, new Callback.EmptyCallback() {
                                    @Override
                                    public void onError() {
                                        image.setImageDrawable(DrawableUtils.getDrawable(cardView.getContext(), R.drawable.ic_account_circle_white_48dp));
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        image.setContentDescription(model.getDescription());

        if (model instanceof RecordInformation) {
            String lastChange = new SimpleDateFormat("yyyy").format(model.getDateLong());
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
