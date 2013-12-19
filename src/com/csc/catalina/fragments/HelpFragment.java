package com.csc.catalina.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csc.catalina.CatalinaApplication;
import com.csc.catalina.R;

public class HelpFragment extends BaseFragment {

	private static final String LOG_TAG = HelpFragment.class.getName();

	private RelativeLayout cartImageLayout;
	private RelativeLayout offersImageLayout;
	private RelativeLayout scanImageLayout;
	private RelativeLayout faqImageLayout;

	private LinearLayout imageRows;

	private TextView helpText;
	private ImageView helpIcon;

	private Button backBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.help, container, false);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		scanImageLayout = (RelativeLayout) view
				.findViewById(R.id.scanImageLayout);
		scanImageLayout.setOnClickListener(this);

		cartImageLayout = (RelativeLayout) view
				.findViewById(R.id.cartImageLayout);
		cartImageLayout.setOnClickListener(this);

		offersImageLayout = (RelativeLayout) view
				.findViewById(R.id.offersImageLayout);
		offersImageLayout.setOnClickListener(this);

		faqImageLayout = (RelativeLayout) view
				.findViewById(R.id.faqImageLayout);
		faqImageLayout.setOnClickListener(this);

		imageRows = (LinearLayout) view.findViewById(R.id.imageRows);
		imageRows.setOnClickListener(this);

		helpText = (TextView) view.findViewById(R.id.helpText);

		helpIcon = (ImageView) view.findViewById(R.id.helpIcon);
		helpIcon.setOnClickListener(this);

		backBtn = (Button) view.findViewById(R.id.backBtn);
		backBtn.setOnClickListener(this);

	}

	private void setVisibile(boolean original) {
		if (original) {
			imageRows.setVisibility(View.VISIBLE);
			helpText.setVisibility(View.GONE);
			backBtn.setVisibility(View.GONE);
		} else {
			imageRows.setVisibility(View.GONE);
			helpText.setVisibility(View.VISIBLE);
			backBtn.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == faqImageLayout) {
			setVisibile(false);

			String helpCart = CatalinaApplication.getInstance().getResources()
					.getString(R.string.help_faq);
			helpText.setText(helpCart);
		} else if (v == cartImageLayout) {
			setVisibile(false);

			String helpCart = CatalinaApplication.getInstance().getResources()
					.getString(R.string.help_cart);
			helpText.setText(helpCart);
		} else if (v == offersImageLayout) {
			setVisibile(false);

			String helpOffers = CatalinaApplication.getInstance()
					.getResources().getString(R.string.help_offers);
			helpText.setText(helpOffers);
		} else if (v == scanImageLayout) {
			setVisibile(false);

			String helpScan = CatalinaApplication.getInstance().getResources()
					.getString(R.string.help_scan);
			helpText.setText(helpScan);
		} else if (v == helpIcon) {
			setVisibile(true);
		} else if (v == backBtn) {
			setVisibile(true);
		}
	}

}
