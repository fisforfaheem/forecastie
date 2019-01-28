package cz.martykan.forecastie.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.ArrayList;

import cz.martykan.forecastie.R;
import cz.martykan.forecastie.models.Weather;

public class LocationsRecyclerAdapter extends RecyclerView.Adapter<LocationsRecyclerAdapter.LocationsViewHolder> {
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;
    private Context context;
    private ArrayList<Weather> weatherArrayList;

    public LocationsRecyclerAdapter(Context context, ArrayList<Weather> weatherArrayList) {
        this.context = context;
        this.weatherArrayList = weatherArrayList;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocationsViewHolder(inflater.inflate(R.layout.list_location_row, parent, false));
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onBindViewHolder(LocationsViewHolder holder, int position) {
        Typeface weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/weather.ttf");
        Weather weather = weatherArrayList.get(position);

        holder.cityTextView.setText(String.format("%s, %s", weather.getCity(), weather.getCountry()));
        holder.temperatureTextView.setText(weather.getTemperature());
        holder.descriptionTextView.setText(weather.getDescription());
        holder.iconTextView.setText(weather.getIcon());
        holder.iconTextView.setTypeface(weatherFont);

        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.loadUrl("file:///android_asset/map.html?lat=" + weather.getLat()+ "&lon=" + weather.getLon() + "&appid=" + "notneeded");
    }

    @Override
    public int getItemCount() {
        return weatherArrayList.size();
    }

    class LocationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView cityTextView;
        private TextView temperatureTextView;
        private TextView descriptionTextView;
        private TextView iconTextView;
        private WebView webView;

        LocationsViewHolder(View itemView) {
            super(itemView);

            cityTextView = itemView.findViewById(R.id.rowCityTextView);
            temperatureTextView = itemView.findViewById(R.id.rowTemperatureTextView);
            descriptionTextView = itemView.findViewById(R.id.rowDescriptionTextView);
            iconTextView = itemView.findViewById(R.id.rowIconTextView);
            webView = itemView.findViewById(R.id.webView2);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClickListener(view, getAdapterPosition());
            }
        }
    }

    public Weather getItem(int position) {
        return weatherArrayList.get(position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClickListener(View view, int position);
    }

}
