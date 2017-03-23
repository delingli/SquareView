package com.ldl.squareview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ldl.squareview.widget.SquareFlowView;
import com.ldl.squareview.widget.TipViewBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SquareFlowView squareFlowView;
    private SquareFlowView sflowViewss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        squareFlowView = (SquareFlowView) findViewById(R.id.sflowView);
        sflowViewss = (SquareFlowView) findViewById(R.id.sflowViewss);

        initDada();
    }
private List<TipViewBean> mList=new ArrayList<TipViewBean>();
    private void initDada() {
        TipViewBean itemBean;
        Random random=new Random();
        for(int i=0;i<50;++i){
            itemBean = new TipViewBean();
            itemBean.setNickName(i+"");
            itemBean.setRose(random.nextBoolean());
            mList.add(itemBean);
        }

        squareFlowView.setOnFillDadaListener(mList, new SquareFlowView.OnFillDataListener() {
            @Override
            public boolean isArrangeElement() {
                return false;
            }
        });

        sflowViewss.setOnFillDadaListener(mList, new SquareFlowView.OnFillDataListener() {
            @Override
            public boolean isArrangeElement() {
                return true;
            }
        });

    }

}
