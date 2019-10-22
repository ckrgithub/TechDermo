package com.ckr.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author : ckr
 * @date : 2019/10/21 17:43
 * @description :
 */
public class FlexBoxActivity extends AppCompatActivity {
    private static final String TAG = "FlexBoxActivity";
    private RecyclerView recyclerView;
    private String[] data;
    private Queue<TextView> mFlexItemTextViewCaches = new LinkedList<>();
    private LayoutInflater mInflater = null;


    public static void start(Context context) {
        Intent starter = new Intent(context, FlexBoxActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox);
        recyclerView = findViewById(R.id.recyclerView);
        data = getResources().getStringArray(R.array.data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FlexBoxAdapter());
    }


    public class FlexBoxAdapter extends RecyclerView.Adapter<FlexBoxAdapter.FlexBoxHolder> {
        public FlexBoxAdapter() {
        }

        @NonNull
        @Override
        public FlexBoxHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new FlexBoxHolder(LayoutInflater.from(FlexBoxActivity.this.getApplicationContext()).inflate(R.layout.rv_item_flexbox, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull FlexBoxHolder flexBoxHolder, int i) {
            for (String s : data) {
                TextView child = createOrGetCacheFlexItem(flexBoxHolder.flexBoxLayout);
                child.setText(s);
                flexBoxHolder.flexBoxLayout.addView(child);
            }
        }
        @Override
        public void onViewRecycled(@NonNull FlexBoxHolder holder) {
            super.onViewRecycled(holder);
            FlexboxLayout fbl = holder.flexBoxLayout;
            for (int i = 0; i < fbl.getChildCount(); i++) {
                mFlexItemTextViewCaches.offer((TextView) fbl.getChildAt(i));
            }
            fbl.removeAllViews();
        }

        private TextView createOrGetCacheFlexItem(FlexboxLayout flexBoxLayout) {
            TextView tv = mFlexItemTextViewCaches.poll();
            if (tv != null) {
                return tv;
            }
            return createFlexItem(flexBoxLayout);

        }

        private TextView createFlexItem(FlexboxLayout flexBoxLayout) {
            if (mInflater == null) {
                mInflater = LayoutInflater.from(flexBoxLayout.getContext());
            }
            return (TextView) mInflater.inflate(R.layout.rv_item_flexbox_child, flexBoxLayout, false);

        }

        @Override
        public int getItemCount() {
            return 1;
        }

        class FlexBoxHolder extends RecyclerView.ViewHolder {

            private final TextView tvName;
            private final FlexboxLayout flexBoxLayout;

            public FlexBoxHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_name);
                flexBoxLayout = itemView.findViewById(R.id.flexBoxLayout);
            }
        }
    }

}
