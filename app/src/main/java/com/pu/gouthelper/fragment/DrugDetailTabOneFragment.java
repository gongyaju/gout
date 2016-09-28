package com.pu.gouthelper.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.BaseFragment;
import com.pu.gouthelper.bean.DrugDetail;

import org.xutils.view.annotation.ViewInject;

/**
 * 药品说明书
 */
public class DrugDetailTabOneFragment extends BaseFragment {


    @ViewInject(R.id.drug_tv_name)
    private TextView drug_tv_name;
    @ViewInject(R.id.drug_tv_englishname)
    private TextView drug_tv_englishname;
    @ViewInject(R.id.drug_tv_shopname)
    private TextView drug_tv_shopname;
    @ViewInject(R.id.drug_tv_chengfen)
    private TextView drug_tv_chengfen;
    @ViewInject(R.id.drug_tv_syz)
    private TextView drug_tv_syz;
    @ViewInject(R.id.drug_tv_use)
    private TextView drug_tv_use;
    @ViewInject(R.id.drug_tv_jinji)
    private TextView drug_tv_jinji;
    @ViewInject(R.id.drug_tv_adverse)
    private TextView drug_tv_adverse;
    @ViewInject(R.id.drug_tv_interaction)
    private TextView drug_tv_interaction;
    @ViewInject(R.id.drug_tv_toxicology)
    private TextView drug_tv_toxicology;
    @ViewInject(R.id.drug_tv_classify)
    private TextView drug_tv_classify;
    @ViewInject(R.id.drug_tv_appnum)
    private TextView drug_tv_appnum;
    @ViewInject(R.id.drug_tv_manufacturer)
    private TextView drug_tv_manufacturer;

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drug_tab1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();

    }

    public void setData(DrugDetail entity) {
        drug_tv_name.setText(entity.getSubtitle());
        drug_tv_englishname.setText(entity.getEntitle());
        drug_tv_shopname.setText(entity.getSubtitle());
        drug_tv_chengfen.setText(entity.getIngredients());
        drug_tv_syz.setText(entity.getSymptom());
        drug_tv_use.setText(entity.getDosage());
        drug_tv_jinji.setText(entity.getContraindication());
        drug_tv_adverse.setText(entity.getAdverse());
        drug_tv_interaction.setText(entity.getInteraction());
        drug_tv_toxicology.setText(entity.getToxicology());
        drug_tv_classify.setText(entity.getClassify());
        drug_tv_appnum.setText(entity.getAppnum());
        drug_tv_manufacturer.setText(entity.getManufacturer());

    }
}
