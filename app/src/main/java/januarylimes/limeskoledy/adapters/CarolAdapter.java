package januarylimes.limeskoledy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import januarylimes.limeskoledy.R;
import januarylimes.limeskoledy.activities.TekstActivity;
import januarylimes.limeskoledy.model.Koleda;

/**
 * Created by kacper on 30.09.2016.
 */
public class CarolAdapter extends RecyclerView.Adapter<CarolAdapter.ViewHolder> {


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.text1);
        }
    }

    private List<Koleda> mCarols;
    private Context mContext;

    public CarolAdapter(Context context, List<Koleda> carols) {
        mCarols = carols;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View carolView = inflater.inflate(R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(carolView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Koleda carol = mCarols.get(position);
        TextView textView = holder.titleTextView;
        textView.setText(carol.getNazwa());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            // http://stackoverflow.com/questions/24885223/why-doesnt-recyclerview-have-onitemclicklistener-and-how-recyclerview-is-dif/24933117#24933117
            @Override
            public void onClick(View v) {
                //przenoszenie do konkretnej koledy
                Intent i = new Intent(getContext(), TekstActivity.class);
                i.putExtra("koleda", carol);
                getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCarols.size();
    }
}